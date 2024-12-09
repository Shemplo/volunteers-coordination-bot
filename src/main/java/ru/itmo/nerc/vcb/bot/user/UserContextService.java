package ru.itmo.nerc.vcb.bot.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.telegram.telegrambots.meta.api.objects.User;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class UserContextService {
    
    private static volatile UserContextService instance;
    
    public static UserContextService getInstance () {
        if (instance == null) {
            synchronized (UserContextService.class) {
                if (instance == null) {
                    instance = new UserContextService ();
                }
            }
        }
        
        return instance;
    }
    
    private final Map <Long, UserContext> id2context = new ConcurrentHashMap <> ();
    
    public UserContext findContextForUser (User user) {
        final var userContext = id2context.computeIfAbsent (user.getId (), __ -> {
            final var context = new UserContext (user);
            
            return context;
        });
        
        if (user.getUserName () != null) {
            userContext.setUsername (user.getUserName ());
        }
        
        return userContext;
    }
    
    public UserContext findContextForExistingUser (long userId) {
        final var tmpUser = new User ();
        tmpUser.setId (userId);
        
        return findContextForUser (tmpUser);
    }
    
    public List <UserContext> findGroupMembers (String group) {
        return findGroupsMembers (List.of (group));
    }
    
    public List <UserContext> findGroupsMembers (List <String> groups) {
        return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
            final var inFilter = groups.stream ().collect (Collectors.joining ("','", "('", "')"));
            try (final var selectStatement = connection.prepareStatement ("SELECT id FROM user WHERE subscription_group IN " + inFilter)) {
                //int i = 1;
                //selectStatement.setArray (i++, connection.createArrayOf ("VARCHAR", groups.toArray (String []::new)));
                
                final var members = new ArrayList <UserContext> ();
                try (final var queryResult = selectStatement.executeQuery ()) {
                    while (queryResult.next ()) {
                        final var tmpUser = new User ();
                        tmpUser.setId (queryResult.getLong ("id"));
                        members.add (findContextForUser (tmpUser));
                    }
                }
                
                return members;
            }
        });
    }
    
}
