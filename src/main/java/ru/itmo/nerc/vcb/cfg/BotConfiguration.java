package ru.itmo.nerc.vcb.cfg;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@NoArgsConstructor
public class BotConfiguration {
    
    private BotCredentialsConfiguration credentials;
    private BotDatabaseConfiguration database;
    private BotEventConfiguration event;
    private BotProxyConfiguration proxy;
    
}
