package ru.itmo.nerc.vcb.bot.chat.pending;

import java.io.Serializable;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ChatPending extends Serializable {
    
    ChatPendingResult onUpdateReceived (Update update) throws TelegramApiException;
    
    boolean isBlocking ();
    
    void onPendigActivated () throws TelegramApiException;
    
    void onPendingResolved () throws TelegramApiException;
    
}
