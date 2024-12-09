package ru.itmo.nerc.vcb.utils.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.utils.RollingAverage;

@Slf4j
public class ThreadsPool implements Runnable {
    
    private final ThreadGroup group;
    private final Thread maintainer;
    
    private final Queue <ThreadWrapper> threads = new LinkedList <> ();
    
    private final Supplier <SupervisedRunnable> taskSupplier;
    
    private final String poolPrefix;
    
    private int minThreads, maxThreads;
    
    private final boolean [] runningThreads;
    
    private RollingAverage actityHistory = new RollingAverage (50, false);
    
    public ThreadsPool (int minThreads, int maxThreads, String poolPrefix, Supplier <SupervisedRunnable> taskSupplier) {
        this.maxThreads = maxThreads;
        this.minThreads = minThreads;
        this.poolPrefix = poolPrefix;
        this.taskSupplier = taskSupplier;
        
        runningThreads = new boolean [maxThreads];
        
        group = new ThreadGroup (poolPrefix + "group");
        maintainer = new Thread (group, this, poolPrefix + "maintaner");
        maintainer.setDaemon (true);
        maintainer.start ();
    }
    
    private record ThreadWrapper (Thread worker, SupervisedRunnable task) {
        
    }

    @Override
    public void run () {
        while (!Thread.interrupted ()) {
            final var iterator = threads.iterator ();
            int activeThreads = 0;
            
            while (iterator.hasNext ()) {
                final var thread = iterator.next ();
                final var worker = thread.worker ();
                if (!worker.isAlive ()) {
                    log.info ("Thread `{}` accidentally stopped", worker.getName ());
                    final var wi = Integer.parseInt (worker.getName ().substring (poolPrefix.length ()));
                    runningThreads [wi] = false;
                    iterator.remove ();
                } else {
                    activeThreads += thread.task ().getActivityAndReset () == RunnableActivity.RUNNING ? 1 : 0;
                }
            }
            
            actityHistory.addValue (activeThreads - threads.size () / 2.0);
            int desiredThreads = threads.size () + (int) Math.round (actityHistory.getLast ());
            int threadsNeed = Math.max (Math.min (desiredThreads, maxThreads), minThreads) - threads.size ();
            
            for (int wi = 0; wi < runningThreads.length && threadsNeed > 0; wi++) {
                if (!runningThreads [wi]) {
                    final var task = taskSupplier.get ();
                    final var thread = new Thread (group, task, poolPrefix + wi);
                    threads.add (new ThreadWrapper (thread, task));
                    thread.start ();
                    
                    log.info ("Create thread `{}` because threads need: {}", thread.getName (), threadsNeed);
                    runningThreads [wi] = true;
                    threadsNeed--;
                }
            }
            
            while (threadsNeed < 0) {
                final var worker = threads.poll ().worker ();
                if (tryStopThread (worker)) {
                    log.info ("Stop thread `{}` because threads need: {}", worker.getName (), threadsNeed);
                    threadsNeed++;
                }
            }
            
            try {
                Thread.sleep (250L);
            } catch (InterruptedException ie) {
                return;
            }
        }
    }
    
    private boolean tryStopThread (Thread thread) {
        try {
            thread.interrupt ();
            thread.join ();
            
            return true;
        } catch (InterruptedException ie) {
            ie.printStackTrace ();
            return false;
        }
    }
    
    public void shutdown () {
        log.info ("Stopping `{}` threads pool...", poolPrefix);
        
        try {
            maintainer.interrupt ();
            maintainer.join ();
        } catch (InterruptedException ie) {
            ie.printStackTrace ();
        }
        
        log.info ("Maintainer thread in `{}` threads poll is stopped", poolPrefix);
        log.info ("Stopping working threads in `{}` threads pool...", poolPrefix);
        
        for (final var thread : threads) {
            tryStopThread (thread.worker ());
        }
        
        log.info ("Worker threads in `{}` threads poll are stopped", poolPrefix);
    }
    
}
