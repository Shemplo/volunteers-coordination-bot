package ru.itmo.nerc.vcb.utils;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

public class StopWatchWrapper {
    
    private final Deque <StopWatch> watches = new LinkedList <> ();
    
    private final StringBuilder result = new StringBuilder ();
    
    public StopWatchWrapper () {
        reset ();
    }
    
    public void start (String task) {
        final var stopWatch = new StopWatch ();
        watches.add (stopWatch);
        
        if (watches.size () > 1) {
            result.append ("\n");
            result.append (" ".repeat ((watches.size () - 1) * 4));
        }
        
        //result.append (" ".repeat ((watches.size () - 1) * 4)).append (task).append (" (");
        result.append (task).append (" (");
        stopWatch.start ();
    }
    
    public void stop () {
        final var stopWatch = watches.poll ();
        if (stopWatch != null) {
            stopWatch.stop ();
            
            result.append (" ".repeat (watches.size () * 4));
            result.append (") ").append (stopWatch.getTime (TimeUnit.MILLISECONDS)).append (" ms\n");
        }
    }
    
    public void reset () {
        result.setLength (0);
        result.append ("StopWatch results:\n");
        
        watches.clear ();
    }
    
    @Override
    public String toString () {
        return result.toString ();
    }
    
}
