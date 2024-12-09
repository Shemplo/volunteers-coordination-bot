package ru.itmo.nerc.vcb.bot.chat;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupChatContext extends CommonChatContext {
    
    public GroupChatContext (Chat chat) {
        super (chat);
    }
    
    @Override
    public boolean onUpdateReceived (Update update) throws TelegramApiException {
        log.info ("Delegate update to parent chat context...");
        return super.onUpdateReceived (update);
    }
    
}
