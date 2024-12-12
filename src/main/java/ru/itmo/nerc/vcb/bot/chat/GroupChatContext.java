package ru.itmo.nerc.vcb.bot.chat;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.user.UserContext;

@Slf4j
public class GroupChatContext extends CommonChatContext {
    
    public GroupChatContext (Chat chat) {
        super (chat);
    }
    
    @Override
    protected void processCommandProcessingException (UserContext user, Message message, CommandProcessingException exception) throws TelegramApiException {
        if (user != null && user.getPrivateChatId () != 0L) {
            TelegramBot.getInstance ().sendMessage (user.getPrivateChatId (), cfg -> {
                cfg.text (exception.getMessage ());
            });
        } else {
            log.error ("User tried to do prohibited action. Message: {}", message.toString ());
        }
    }
    
}
