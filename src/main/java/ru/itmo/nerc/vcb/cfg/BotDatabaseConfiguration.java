package ru.itmo.nerc.vcb.cfg;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@NoArgsConstructor
public class BotDatabaseConfiguration {
    
    private String filename;
    private String password;
    private String username;
    
}
