package ru.itmo.nerc.vcb.cfg;

import org.telegram.telegrambots.bots.DefaultBotOptions.ProxyType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@NoArgsConstructor
public class BotProxyConfiguration {
    
    private String host;
    private int port;
    private ProxyType type;
    
}
