package ru.itmo.nerc.vcb.bot.chat;

import org.antlr.v4.runtime.misc.Pair;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.itmo.nerc.vcb.bot.TelegramChildBot;
import ru.itmo.nerc.vcb.bot.user.UserContext;

public interface ChatContext extends TelegramChildBot {
    
    long getChatId ();
    
    void processCommand (UserContext user, Message message, Pair <String, String> command) throws CommandProcessingException, TelegramApiException;
    
}
