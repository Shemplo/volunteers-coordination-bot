package ru.itmo.nerc.vcb.cfg;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@NoArgsConstructor
public class BotEventConfiguration {
    
    private String name;
    
    private List <EventGroup> groups;
    
    @ToString
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventGroup {
        
        private String shortName;
        private String displayName;
        
        public String getShortName () {
            return shortName == null ? displayName : shortName;
        }
        
    }
    
    public EventGroup findGroupByName (String name) {
        if (name == null) {
            return null;
        }
        
        for (final var group : groups) {
            if (name.equals (group.getShortName ()) || name.equals (group.getDisplayName ())) {
                return group;
            }
        }
        
        return null;
    }
    
}
