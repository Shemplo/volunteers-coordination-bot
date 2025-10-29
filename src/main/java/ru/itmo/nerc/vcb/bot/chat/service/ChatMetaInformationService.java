package ru.itmo.nerc.vcb.bot.chat.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.chat.ChatMetaInformation;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class ChatMetaInformationService {
    
    private static volatile ChatMetaInformationService instance;
    
    public static ChatMetaInformationService getInstance () {
        if (instance == null) {
            synchronized (ChatMetaInformationService.class) {
                if (instance == null) {
                    instance = new ChatMetaInformationService ();
                }
            }
        }
        
        return instance;
    }
    
    private final Map <MetaInformationKey, ChatMetaInformation> key2metaInf = new ConcurrentHashMap <> ();
    
    private record MetaInformationKey (long chatId, String key) {
        
    }
    
    public ChatMetaInformation findOrCreateByChatAndKey (long chatId, String key) {
        return key2metaInf.computeIfAbsent (new MetaInformationKey (chatId, key), __ -> {
            return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
                try (final var selectStatement = connection.prepareStatement ("SELECT * FROM chat_meta_information WHERE chat_id = ? AND information_key = ?")) {
                    int i = 1;
                    selectStatement.setLong (i++, chatId);
                    selectStatement.setString (i++, Base64.getEncoder ().encodeToString (key.getBytes ()));
                    
                    try (final var queryResult = selectStatement.executeQuery ()) {
                        return queryResult.next ()
                             ? new ChatMetaInformation (queryResult)
                             : new ChatMetaInformation (chatId, key);
                    }
                }
            });
        });
    }
    
    public List <ChatMetaInformation> findByKey (String key) {
        // We can't just filter values from `key2metaInf` because it's cache and can be not fulfilled
        return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
            try (final var selectStatement = connection.prepareStatement ("SELECT * FROM chat_meta_information WHERE information_key = ?")) {
                int i = 1;
                selectStatement.setString (i++, Base64.getEncoder ().encodeToString (key.getBytes ()));
                
                final var entries = new ArrayList <ChatMetaInformation> ();
                try (final var queryResult = selectStatement.executeQuery ()) {
                    while (queryResult.next ()) {
                        entries.add (new ChatMetaInformation (queryResult));
                    }
                }
                
                return entries;
            }
        });
    }
    
}
