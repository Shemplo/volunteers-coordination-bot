package ru.itmo.nerc.vcb.bot.chat.pending;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.itmo.nerc.vcb.bot.chat.ChatContext;
import ru.itmo.nerc.vcb.bot.chat.ChatUtils;
import ru.itmo.nerc.vcb.bot.user.UserContext;

public abstract class AbstractChatPendingWithMenu extends AbstractChatPending {
    
    private static final long serialVersionUID = 1L;
    
    protected Message message;
    
    public AbstractChatPendingWithMenu (ChatContext chat, UserContext user) {
        super (chat, user);
    }
    
    @Override
    public void onPendingResolved () throws TelegramApiException {
        hideMarkup ();
    }
    
    protected void hideMarkup () throws TelegramApiException {
        if (message != null) {
            ChatUtils.hideInlineMarkup (chat.getChatId (), message.getMessageId ());
        }
    }
    
}
