package ru.itmo.nerc.vcb.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RollingAverage {
    
    private final int window;
    
    private final boolean forward;
    
    private int finished;
    
    private Queue <Double> buffer = new LinkedList <> ();
    private List <Double> average = new ArrayList <> ();
    
    private double sum = 0;
    
    public RollingAverage addValue (double value) {
        while (finished > 0) {
            average.remove (average.size () - 1);
            finished--;
        }
        
        buffer.add (value);
        if (!forward) {
            sum += value;
        }
        
        while (buffer.size () > window) { // actually `if` is enough
            final var tmp = buffer.poll ();
            if (forward) {
                if (finished > 0) {
                    average.set (average.size () - finished, sum / window);
                } else {
                    average.add (sum / window);
                }
            }
            
            sum -= tmp;
        }
        
        if (!forward) {
            average.add (sum / Math.min (window, buffer.size ()));
        } else { // forward
            sum += value;
        }
        
        return this;
    }
    
    public RollingAverage addSequence (@NonNull Iterable <Double> values) {
        values.forEach (this::addValue);
        return this;
    }
    
    public List <Double> getSequence () {
        return List.copyOf (average);
    }
    
    public double getLast () {
        return average.isEmpty () ? 0.0 : average.get (average.size () - 1);
    }
    
    public List <Double> finish () {
        if (!forward || finished > 0) { return getSequence (); }
        
        Double [] rest = buffer.toArray (Double []::new);
        int tmpWindow = rest.length;
        double tmpSum = sum;
        
        for (int i = 0; i < rest.length; i++, tmpWindow--) {
            average.add (tmpSum / tmpWindow);
            tmpSum -= rest [i];
        }
        
        finished = rest.length;
        return getSequence ();
    }
    
}
