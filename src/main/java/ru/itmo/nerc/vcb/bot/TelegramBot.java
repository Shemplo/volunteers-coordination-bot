package ru.itmo.nerc.vcb.bot;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.reactions.SetMessageReaction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText.EditMessageTextBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionTypeEmoji;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.chat.ChatContextService;
import ru.itmo.nerc.vcb.bot.chat.InlineQueryProcessor;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;
import ru.itmo.nerc.vcb.utils.thread.ThreadsPool;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private static volatile TelegramBot instance;
    
    public static TelegramBot getInstance () {
        if (instance == null) {
            synchronized (TelegramBot.class) {
                if (instance == null) {
                    final var options = new DefaultBotOptions ();
                    options.setGetUpdatesTimeout (20);
                    options.setGetUpdatesLimit (40);
                    
                    final var configuration = ConfigurationHolder.getConfigurationFromSingleton ();
                    final var proxy = configuration.getProxy ();
                    
                    if (proxy != null) {
                        options.setProxyHost (proxy.getHost ());
                        options.setProxyPort (proxy.getPort ());
                        options.setProxyType (proxy.getType ());
                        
                        log.info ("{} proxy is set to {}:{}", proxy.getType (), proxy.getHost (), proxy.getPort ());
                    } else {
                        log.info ("No proxy will be used");
                    }
                    
                    instance = new TelegramBot (options);
                }
            }
        }
        
        return instance;
    }
    
    private final ChatContextService chatContextService = ChatContextService.getInstance ();
    private final InlineQueryProcessor inlineQueryProcessor = InlineQueryProcessor.getInstance ();
    
    private TelegramBot (DefaultBotOptions options) {
        super (options, ConfigurationHolder.getConfigurationFromSingleton ().getCredentials ().getToken ());
        
        // It's a daemon that will be destroyed with main thread
        new ThreadsPool (1, 32, "iq-processor-", () -> inlineQueryProcessor);
    }
    
    @Override
    public String getBotUsername () {
        return ConfigurationHolder.getConfigurationFromSingleton ().getCredentials ().getBotName ();
    }
    
    @Override
    public void onUpdateReceived (Update update) {
        log.info ("Received update: {}", update);
        
        try {
            if (update.hasInlineQuery ()) {
                // This will process updates asynchronously
                inlineQueryProcessor.addInlineQueryToQueue (update);
                
                //inlineQueryProcessor.onUpdateReceived (update);
            } else if (update.hasCallbackQuery ()) {
                final var maybeMessage = update.getCallbackQuery ().getMessage ();
                if (!(maybeMessage instanceof Message)) {
                    return;
                }
                
                final var message = (Message) maybeMessage;
                final var context = chatContextService.findContextForChat (message.getChat ());
                
                if (context != null) {
                    context.onUpdateReceived (update);
                }
            } else if (update.hasMessage ()) {
                final var chat = update.getMessage ().getChat ();
                final var context = chatContextService.findContextForChat (chat);
                
                if (context != null) {
                    context.onUpdateReceived (update);
                }
            }
        } catch (TelegramApiException tapie) {
            tapie.printStackTrace ();
        }
    }
    
    public void deleteMessage (Message message) throws TelegramApiException {
        executeAsync (DeleteMessage.builder ()
            .messageId (message.getMessageId ())
            .chatId (message.getChatId ())
            .build ());
    }
    
    public ChatMember getChatMember (long chatId, long userId) throws TelegramApiException {
        return execute (GetChatMember.builder ()
            .chatId (chatId)
            .userId (userId)
            .build ());
    }
    
    public Message sendMessage (long chatId, Consumer <SendMessageBuilder> configurer) throws TelegramApiException {
        final var message = SendMessage.builder ()
            .chatId (chatId)
            .parseMode ("HTML");
        
        configurer.accept (message);
        return execute (message.build ());
    }
    
    public void sendMessageEdit (long chatId, int messageId, Consumer <EditMessageTextBuilder> configurer) throws TelegramApiException {
        final var message = EditMessageText.builder ()
            .chatId (chatId)
            .messageId (messageId)
            .parseMode ("HTML");
        
        configurer.accept (message);
        executeAsync (message.build ());
    }
    
    public Message sendReplyMessage (Message replyTo, Consumer <SendMessageBuilder> configurer) throws TelegramApiException {
        final var message = SendMessage.builder ()
            .chatId (replyTo.getChatId ())
            .replyToMessageId (replyTo.getMessageId ())
            .parseMode ("HTML");
        
        configurer.accept (message);
        return execute (message.build ());
    }
    
    public void sendReplyMessageAsync (Message replyTo, Consumer <SendMessageBuilder> configurer) throws TelegramApiException {
        final var message = SendMessage.builder ()
            .chatId (replyTo.getChatId ())
            .replyToMessageId (replyTo.getMessageId ())
            .parseMode ("HTML");
        
        configurer.accept (message);
        executeAsync (message.build ());
    }
    
    public boolean sendInlineQueryResult (InlineQuery query, Collection <? extends InlineQueryResult> results) throws TelegramApiException {
        return execute (AnswerInlineQuery.builder ()
            .inlineQueryId (query.getId ())
            .results (results)
            .build ());
    }
    
    public void setReactionOnMessage (Message message, String emoji) throws TelegramApiException {
        executeAsync (SetMessageReaction.builder ()
            .chatId (message.getChatId ())
            .messageId (message.getMessageId ())
            .reactionTypes (List.of (
                ReactionTypeEmoji.builder ()
                    .emoji (emoji)
                    .build ()
            ))
            .build ());
    }
    
}
