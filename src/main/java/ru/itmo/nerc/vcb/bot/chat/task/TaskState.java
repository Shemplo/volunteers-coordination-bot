package ru.itmo.nerc.vcb.bot.chat.task;

public enum TaskState {
    
    CREATED,
    ENABLED,
    DISABLED
    
    ;
    
    public static TaskState parseOrDefault (String name, TaskState def) {
        for (final var value : values ()) {
            if (value.name ().equals (name)) {
                return value;
            }
        }
        
        return def;
    }
    
}
