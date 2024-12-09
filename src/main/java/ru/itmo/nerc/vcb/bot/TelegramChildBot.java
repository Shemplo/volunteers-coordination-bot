package ru.itmo.nerc.vcb.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramChildBot {
    
    boolean onUpdateReceived (Update update) throws TelegramApiException;
    
}
