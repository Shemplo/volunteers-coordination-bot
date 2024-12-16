package ru.itmo.nerc.vcb.cfg;

import java.net.Proxy.Type;

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
    private Type type;
    
}
