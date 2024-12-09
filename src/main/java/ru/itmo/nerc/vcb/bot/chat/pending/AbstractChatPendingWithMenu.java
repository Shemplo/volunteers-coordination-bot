package ru.itmo.nerc.vcb.bot.chat.pending;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.itmo.nerc.vcb.bot.chat.ChatContext;
import ru.itmo.nerc.vcb.bot.chat.ChatUtils;

public abstract class AbstractChatPendingWithMenu extends AbstractChatPending {
    
    private static final long serialVersionUID = 1L;
    
    protected Message message;
    
    public AbstractChatPendingWithMenu (ChatContext chat) {
        super (chat);
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
