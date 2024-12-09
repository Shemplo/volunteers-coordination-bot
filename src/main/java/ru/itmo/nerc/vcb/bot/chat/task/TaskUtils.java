package ru.itmo.nerc.vcb.bot.chat.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.antlr.v4.runtime.misc.Pair;

import lombok.experimental.UtilityClass;
import ru.itmo.nerc.vcb.cfg.BotEventConfiguration;

@UtilityClass
public class TaskUtils {
    
    public static Pair <List <String>, List <String>> decideGroups (BotEventConfiguration event, List <String> groups) {
        final var include = new ArrayList <String> ();
        final var exclude = new ArrayList <String> ();
        
        final var groupsSet = new HashSet <String> ();
        for (final var group : groups) {
            final var isExcluded = group.startsWith ("-");
            final var eventGroup = event.findGroupByName (isExcluded ? group.substring (1) : group);
            if (eventGroup != null) {
                groupsSet.add ((isExcluded ? "-" : "") + eventGroup.getShortName ());
            }
        }
        
        //final var allExcluded = !groups.isEmpty () && groups.stream ().allMatch (name -> name.startsWith ("-"));
        final var allExcluded = groups.stream ().allMatch (name -> name.startsWith ("-"));
        for (final var evenGroup : event.getGroups ()) {
            final var isIncluded = groupsSet.contains (evenGroup.getShortName ());
            if ((!allExcluded && !isIncluded) || groupsSet.contains ("-" + evenGroup.getShortName ())) {
                exclude.add (evenGroup.getShortName ());
            } else if (allExcluded || isIncluded) {
                include.add (evenGroup.getShortName ());
            }
        }
        
        return new Pair <> (include, exclude);
    }
    
}
