package ru.itmo.nerc.vcb.bot.chat;


public class CommandProcessingException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public CommandProcessingException (String reason) {
        super (reason);
    }
    
}
