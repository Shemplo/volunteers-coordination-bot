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
    
    private TelegramBot (DefaultBotOptions options) {
        super (options, ConfigurationHolder.getConfigurationFromSingleton ().getCredentials ().getToken ());
    }
    
    @Override
    public String getBotUsername () {
        return ConfigurationHolder.getConfigurationFromSingleton ().getCredentials ().getBotName ();
    }
    
    private final ChatContextService chatContextService = ChatContextService.getInstance ();
    private final InlineQueryProcessor inlineQueryProcessor = InlineQueryProcessor.getInstance ();
    
    @Override
    public void onUpdateReceived (Update update) {
        log.info ("Received update: {}", update);
        
        try {
            if (update.hasInlineQuery ()) {
                inlineQueryProcessor.onUpdateReceived (update);
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
        
        /*
        if (update.getInlineQuery () != null) {
            try {
                execute (new AnswerInlineQuery (update.getInlineQuery ().getId (), List.of (
                    InlineQueryResultArticle.builder ()
                        .hideUrl (true)
                        .id (UUID.randomUUID ().toString ())
                        .title ("Недостаточно прав")
                        .description ("У Вас недостаточно прав для создания задачи")
                        .inputMessageContent (InputTextMessageContent.builder ()
                            .messageText ("/dropmessage")
                            .build ())
                        .build (),
                    InlineQueryResultArticle.builder ()
                        .hideUrl (true)
                        .id (UUID.randomUUID ().toString ())
                        .title ("Задача с вопросом")
                        .description ("Сколько команд отсутствует в холле?\nХоллы: 1, 3, 4, 5")
                        .inputMessageContent (InputTextMessageContent.builder ()
                            .messageText ("Сколько команд отсутствует в холле?\n\nH1: 🙈\nH2: 🐌\nH3: ✅")
                            .build ())
                        .build (),
                    InlineQueryResultArticle.builder ()
                        .hideUrl (true)
                        .id (UUID.randomUUID ().toString ())
                        .title ("Задача на выполнение")
                        .description ("Сколько команд отсутствует в холле?")
                        .inputMessageContent (InputTextMessageContent.builder ()
                            .messageText ("/createtask {\"task\":\"Сколько команд отсутствует в холле?\", \"halls\":[], \"type\":\"task\"}")
                            .build ())
                        .build ()
                )));
            } catch (TelegramApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        //} else if (update.getChannelPost () != null) {
        } else if (update.getMessage () != null && update.getMessage ().getViaBot () != null) {
            try {
                execute (DeleteMessage.builder ()
                    .messageId (update.getMessage ().getMessageId ())
                    .chatId (update.getMessage ().getChatId ())
                    .build ());
                execute (SendMessage.builder ()
                    .chatId (update.getMessage ().getChatId ())
                    .parseMode ("HTML")
                    .replyMarkup (InlineKeyboardMarkup.builder ()
                        .keyboardRow (List.of (
                            InlineKeyboardButton.builder ()
                                .switchInlineQueryCurrentChat ("")
                                .text ("Начать (или ответить)")
                                .build ()
                        ))
                        .build ())
                    .text (update.getMessage ().getText ())
                    .build ());
            } catch (TelegramApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        */
    }
    
    public boolean deleteMessage (Message message) throws TelegramApiException {
        return execute (DeleteMessage.builder ()
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
        execute (message.build ());
    }
    
    public Message sendReplyMessage (Message replyTo, Consumer <SendMessageBuilder> configurer) throws TelegramApiException {
        final var message = SendMessage.builder ()
            .chatId (replyTo.getChatId ())
            .replyToMessageId (replyTo.getMessageId ())
            .parseMode ("HTML");
        
        configurer.accept (message);
        return execute (message.build ());
    }
    
    public boolean sendInlineQueryResult (InlineQuery query, Collection <? extends InlineQueryResult> results) throws TelegramApiException {
        return execute (AnswerInlineQuery.builder ()
            .inlineQueryId (query.getId ())
            .results (results)
            .build ());
    }
    
    public boolean setReactionOnMessage (Message message, String emoji) throws TelegramApiException {
        return execute (SetMessageReaction.builder ()
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
