package ru.itmo.nerc.vcb.bot.chat;

import ru.itmo.nerc.vcb.bot.TelegramChildBot;

public interface ChatContext extends TelegramChildBot {
    
    long getChatId ();
    
}
