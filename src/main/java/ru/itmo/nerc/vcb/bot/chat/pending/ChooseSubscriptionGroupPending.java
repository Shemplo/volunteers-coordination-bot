package ru.itmo.nerc.vcb.bot.chat.pending;

import java.util.ArrayList;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.chat.ChatContext;
import ru.itmo.nerc.vcb.bot.chat.CommonChatContext;
import ru.itmo.nerc.vcb.bot.chat.ex.CommandProcessingException;
import ru.itmo.nerc.vcb.bot.user.UserContext;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;

public class ChooseSubscriptionGroupPending extends AbstractChatPendingWithMenu {

    private static final long serialVersionUID = 1L;
    
    private static final String CALLBACK_PREFIX = "group";
    
    @Getter
    private final boolean blocking = false;
    
    private final Message initialMessage;
    
    public ChooseSubscriptionGroupPending (ChatContext chat, UserContext user, Message initialMessage) throws TelegramApiException {
        super (chat, user);
        
        this.initialMessage = initialMessage;
        onPendigActivated ();
    }

    @Override
    public ChatPendingResult onUpdateReceived (Update update) throws CommandProcessingException, TelegramApiException {
        if (update.hasCallbackQuery ()) {
            final var callback = update.getCallbackQuery ();
            if (callback.getData ().startsWith (CALLBACK_PREFIX)) {
                final var groupName = callback.getData ().substring (CALLBACK_PREFIX.length () + 1);
                if (chat instanceof CommonChatContext ccc) {
                    ccc.subscribeToGroup (user, message, groupName);
                }
                
                return ChatPendingResult.RESOLVED;
            }
        }
        
        return ChatPendingResult.KEEP;
    }

    @Override
    public ChatPendingResult onPendigActivated () throws TelegramApiException {
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        
        final var keyboard = new InlineKeyboardMarkup (new ArrayList <> ());
        for (final var group : event.getGroups ()) {
            final var row = new InlineKeyboardRow ();
            keyboard.getKeyboard ().add (row);
            
            row.add (InlineKeyboardButton.builder ()
                .text (group.getDisplayName ())
                .callbackData (CALLBACK_PREFIX + " " + group.getShortName ())
                .build ());
        }
        
        message = TelegramBot.getInstance ().sendReplyMessage (initialMessage, cfg -> {
            cfg.replyMarkup (keyboard);
            cfg.text ("Выберите группу, к которой хотите присоединиться");
        });
        
        return ChatPendingResult.KEEP;
    }
    
}
