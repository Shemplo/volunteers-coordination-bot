package ru.itmo.nerc.vcb.bot.chat.pending;

import java.io.Serializable;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.itmo.nerc.vcb.bot.chat.CommandProcessingException;

public interface ChatPending extends Serializable {
    
    ChatPendingResult onUpdateReceived (Update update) throws CommandProcessingException, TelegramApiException;
    
    boolean isBlocking ();
    
    ChatPendingResult onPendigActivated () throws TelegramApiException;
    
    void onPendingResolved () throws TelegramApiException;
    
}
