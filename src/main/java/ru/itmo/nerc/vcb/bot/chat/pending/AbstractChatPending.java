package ru.itmo.nerc.vcb.bot.chat.pending;

import lombok.RequiredArgsConstructor;
import ru.itmo.nerc.vcb.bot.chat.ChatContext;
import ru.itmo.nerc.vcb.bot.user.UserContext;

@RequiredArgsConstructor
public abstract class AbstractChatPending implements ChatPending {
    
    private static final long serialVersionUID = 1L;
    
    protected final ChatContext chat;
    
    protected final UserContext user;
    
}
