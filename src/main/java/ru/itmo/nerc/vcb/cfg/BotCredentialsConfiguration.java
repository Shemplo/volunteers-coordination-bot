package ru.itmo.nerc.vcb.cfg;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.itmo.nerc.vcb.bot.user.UserRole;

@ToString
@Getter @Setter
@NoArgsConstructor
public class BotCredentialsConfiguration {
    
    private String botName;
    private String token;
    
    private Map <UserRole, String> roleAuthentication;
    
}
