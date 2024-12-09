package ru.itmo.nerc.vcb.utils.thread;


public interface SupervisedRunnable extends Runnable {
    
    RunnableActivity getActivityAndReset ();
    
}
