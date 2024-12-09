package ru.itmo.nerc.vcb.bot.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.commons.lang3.function.FailableConsumer;
import org.telegram.telegrambots.meta.api.objects.User;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@Getter
@ToString
public class UserContext {
    
    private final long userId;
    
    private volatile String username;
    
    private volatile UserRole role;
    
    private volatile String group;
    
    private volatile long privateChatId;
    
    public UserContext (User user) {
        this.userId = user.getId ();
        
        if (!preloadFromDatabase ()) {
            username = user.getUserName ();
            role = UserRole.UNKNOWN;
            privateChatId = 0L;
            
            persist ();
        }
        
        log.info ("New user context is created: {}", toString ());
    }
    
    private boolean preloadFromDatabase () {
        return loadFromDatabaseAndDo (queryResult -> {
            username = queryResult.getString ("username");
            final var roleName = queryResult.getString ("role");
            role = UserRole.parseOrDefault (roleName, UserRole.UNKNOWN);
            group = queryResult.getString ("subscription_group");
            privateChatId = queryResult.getLong ("private_chat_id");
        });
    }
    
    private boolean loadFromDatabaseAndDo (FailableConsumer <ResultSet, SQLException> consumer) {
        return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
            final var fetchUserQuery = String.format ("SELECT * FROM `user` WHERE `id` = %d", userId);
            try (
                final var fetchUserStatement = connection.prepareStatement (fetchUserQuery);
                final var fetchUserResult = fetchUserStatement.executeQuery ();
            ) {
                if (fetchUserResult.next ()) {
                    if (consumer != null) {
                        consumer.accept (fetchUserResult);
                    }
                    
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
    
    private void persist () {
        synchronized (this) {
            if (loadFromDatabaseAndDo (null)) {
                DatabaseService.getInstance ().doWrapped (connection -> {
                    try (final var insertStatement = connection.prepareStatement ("UPDATE user SET username = ?, role = ?, subscription_group = ?, private_chat_id = ? WHERE id = ?")) {
                        int i = 1;
                        insertStatement.setString (i++, username);
                        insertStatement.setString (i++, role.name ());
                        insertStatement.setString (i++, group);
                        insertStatement.setLong (i++, privateChatId);
                        insertStatement.setLong (i++, userId);
                        insertStatement.execute ();
                    }
                });
            } else {
                DatabaseService.getInstance ().doWrapped (connection -> {
                    try (final var insertStatement = connection.prepareStatement ("INSERT INTO user (id, role, private_chat_id) VALUES (?, ?, ?)")) {
                        int i = 1;
                        insertStatement.setLong (i++, userId);
                        insertStatement.setString (i++, role.name ());
                        insertStatement.setLong (i++, privateChatId);
                        insertStatement.execute ();
                    }
                });
            }
        }
    }
    
    public boolean hasPermissions (UserRole requiredRole) {
        return role.ordinal () >= requiredRole.ordinal ();
    }
    
    public UserContext setUsername (String username) {
        if (!Objects.equals (this.username, username)) {
            this.username = username;
            persist ();
        }
        
        return this;
    }
    
    public UserContext setRole (UserRole role) {
        if (this.role != role) {
            this.role = role;
            persist ();
        }
        
        return this;
    }
    
    public UserContext setGroup (String group) {
        if (!Objects.equals (group, this.group)) {
            this.group = group;
            persist ();
        }
        
        return this;
    }
    
    public UserContext setPrivateChatId (long chatId) {
        if (this.privateChatId != chatId) {
            this.privateChatId = chatId;
            persist ();
        }
        
        return this;
    }
    
}
