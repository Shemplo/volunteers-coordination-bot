package ru.itmo.nerc.vcb.bot.chat.pending;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.chat.ChatContext;

@Slf4j
public class CodeAuthenticationPending extends AbstractChatPending {
    
    private static final long serialVersionUID = 1L;
    
    @Getter
    private final boolean blocking = true;
    
    public CodeAuthenticationPending (ChatContext chat) throws TelegramApiException {
        super (chat);
        
        log.info ("Pending {} was created in chat {}", getClass (), chat.getChatId ());
        onPendigActivated ();
    }
    
    @Override
    public ChatPendingResult onUpdateReceived (Update update) throws TelegramApiException {
        return ChatPendingResult.RESOLVED;
    }
    
    @Override
    public void onPendigActivated () throws TelegramApiException {
        
    }
    
    @Override
    public void onPendingResolved () throws TelegramApiException {
        
    }
    
}
