package ru.itmo.nerc.vcb.bot.chat.pending;

import org.antlr.v4.runtime.misc.Pair;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import ru.itmo.nerc.vcb.bot.chat.ChatContext;
import ru.itmo.nerc.vcb.bot.chat.CommandProcessingException;
import ru.itmo.nerc.vcb.bot.user.UserContext;

public class DelayedCommandProcessingPending extends AbstractChatPending {
    
    private static final long serialVersionUID = 1L;

    private final Pair <String, String> command;
    
    @Getter
    private final boolean blocking = false;
    
    private final Message message;
    
    public DelayedCommandProcessingPending (ChatContext chat, UserContext user, Message message, Pair <String, String> command) {
        super (chat, user);
        
        this.command = command;
        this.message = message;
    }

    @Override
    public ChatPendingResult onUpdateReceived (Update update) throws CommandProcessingException, TelegramApiException {
        return ChatPendingResult.RESOLVED;
    }

    @Override
    public ChatPendingResult onPendigActivated () throws TelegramApiException {
        try {
            chat.processCommand (user, message, command);
            return ChatPendingResult.RESOLVED;
        } catch (CommandProcessingException cpe) {
            cpe.printStackTrace ();
        }
        
        return ChatPendingResult.RESOLVED;
    }

    @Override
    public void onPendingResolved () throws TelegramApiException {
        
    }
    
}
