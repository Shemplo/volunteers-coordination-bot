package ru.itmo.nerc.vcb.bot.chat.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.meta.api.objects.chat.Chat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.chat.ChatContext;
import ru.itmo.nerc.vcb.bot.chat.CommonChatContext;
import ru.itmo.nerc.vcb.bot.chat.GroupChatContext;
import ru.itmo.nerc.vcb.bot.chat.UserChatContext;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class ChatContextService {
    
    private static volatile ChatContextService instance;
    
    public static ChatContextService getInstance () {
        if (instance == null) {
            synchronized (ChatContextService.class) {
                if (instance == null) {
                    instance = new ChatContextService ();
                }
            }
        }
        
        return instance;
    }
    
    private final Map <Long, ChatContext> id2context = new ConcurrentHashMap <> ();
    
    public ChatContext findContextForChat (Chat chat) {
        return id2context.computeIfAbsent (chat.getId (), __ -> {
            if (chat.isUserChat ()) {
                final var context = new UserChatContext (chat);
                
                return context;
            } else if (chat.isGroupChat () || chat.isSuperGroupChat ()) {
                final var context = new GroupChatContext (chat);
                
                return context;
            } else {
                return new CommonChatContext (chat);
            }
        });
    }
    
}
