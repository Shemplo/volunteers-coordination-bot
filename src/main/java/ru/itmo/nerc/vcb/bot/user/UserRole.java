package ru.itmo.nerc.vcb.bot.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    
    UNKNOWN ("Аноним"),
    PARTICIPANT ("Волонтёр"),
    MODERATOR ("Модератор"),
    DEVELOPER ("Разработчик")
    
    ;
    
    public static UserRole parseOrDefault (String name, UserRole def) {
        for (final var value : values ()) {
            if (value.name ().equals (name)) {
                return value;
            }
        }
        
        return def;
    }
    
    private final String displayName;
    
}
