package ru.itmo.nerc.vcb.bot.chat.pending;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.chat.ChatContext;
import ru.itmo.nerc.vcb.bot.chat.CommandProcessingException;
import ru.itmo.nerc.vcb.bot.chat.UserChatContext;
import ru.itmo.nerc.vcb.bot.user.UserContext;

@Slf4j
public class CodeAuthenticationPending extends AbstractChatPending {
    
    private static final long serialVersionUID = 1L;
    
    @Getter
    private final boolean blocking = true;
    
    public CodeAuthenticationPending (ChatContext chat, UserContext user) throws TelegramApiException {
        super (chat, user);
        
        log.info ("Pending {} was created in chat {}", getClass (), chat.getChatId ());
        onPendigActivated ();
    }
    
    @Override
    public ChatPendingResult onUpdateReceived (Update update) throws CommandProcessingException, TelegramApiException {
        if (update.hasMessage () && update.getMessage ().hasText ()) {
            final var message = update.getMessage ();
            
            if (chat instanceof UserChatContext userChat) {
                userChat.authenticateUser (user, message, message.getText ());
            }
            
            return ChatPendingResult.RESOLVED;
        }
        
        return ChatPendingResult.KEEP;
    }
    
    @Override
    public ChatPendingResult onPendigActivated () throws TelegramApiException {
        TelegramBot.getInstance ().sendMessage (chat.getChatId (), cfg -> {
            cfg.text ("Введите код аутентификации");
        });
        
        return ChatPendingResult.KEEP;
    }
    
    @Override
    public void onPendingResolved () throws TelegramApiException {
        
    }
    
}
