package ru.itmo.nerc.vcb.bot.chat.ex;

import ru.itmo.nerc.vcb.bot.chat.pending.ChatPending;

public class PendingAddedException extends CommandProcessingException {
    
    private static final long serialVersionUID = 1L;

    public PendingAddedException (Class <? extends ChatPending> pending) {
        super (pending.getName ());
    }
    
}
