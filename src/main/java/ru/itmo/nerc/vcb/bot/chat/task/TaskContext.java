package ru.itmo.nerc.vcb.bot.chat.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.chat.InlineQueryProcessor.ParsedQuery;
import ru.itmo.nerc.vcb.bot.user.UserContextService;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@Getter
@ToString
public class TaskContext {
 
    public static final String TYPE_QUESTION = "question";
    public static final String TYPE_TASK = "task";
    
    private final long id;
    
    // Effectively final
    private volatile long chatId;
    private volatile int messageId;
    private volatile long authorId;
    
    // Variable fields
    private volatile TaskState state;
    private volatile String task;
    private volatile String type;
    private volatile List <String> groups = List.of ();
    
    public TaskContext (User author, Message message, ParsedQuery query) {
        this.chatId = message.getChatId ();
        this.messageId = message.getMessageId ();
        this.authorId = author.getId ();
        
        this.groups = query.getGroups ();
        this.task = query.getTask ();
        this.type = query.getType ();
        this.state = TaskState.CREATED;
        
        this.id = insertAndGetId (chatId, messageId, authorId, state);
        
        // This will cause `persist` and `updateMessage` methods to be called
        setState (TaskState.ENABLED);
    }
    
    public TaskContext (long taskId) {
        this.id = taskId;
        
        loadFromDatabaseAndDo (queryResult -> {
            chatId = queryResult.getLong ("chat_id");
            messageId = queryResult.getInt ("message_id");
            authorId = queryResult.getLong ("author_id");
            
            task = queryResult.getString ("task");
            type = queryResult.getString ("type");
            
            final var stateName = queryResult.getString ("state");
            state = TaskState.parseOrDefault (stateName, TaskState.ENABLED);
            
            final var groupsString = queryResult.getString ("groups");
            groups = groupsString == null ? List.of () : Arrays.asList (groupsString.split (","));
        });
    }
    
    private Long insertAndGetId (long chatId, int messageId, long authorId, TaskState state) {
        return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
            try (final var insertStatement = connection.prepareStatement ("INSERT INTO task (chat_id, message_id, author_id, state) VALUES (?, ?, ?, ?)")) {
                int i = 1;
                insertStatement.setLong (i++, chatId);
                insertStatement.setInt (i++, messageId);
                insertStatement.setLong (i++, authorId);
                insertStatement.setString (i++, state.name ());
                insertStatement.execute ();
            }
            
            try (final var selectStatement = connection.prepareStatement ("SELECT * FROM task WHERE chat_id = ? AND message_id = ?")) {
                int i = 1;
                selectStatement.setLong (i++, chatId);
                selectStatement.setInt (i++, messageId);
                
                try (final var fetchResult = selectStatement.executeQuery ()) {
                    if (fetchResult.next ()) {
                        return fetchResult.getLong ("id");
                    } else {
                        log.error ("Task entry not found!");
                    }
                }
            }
            
            // Impossible
            return null;
        });
    }
    
    private boolean loadFromDatabaseAndDo (FailableConsumer <ResultSet, SQLException> consumer) {
        return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
            final var fetchTaskQuery = String.format ("SELECT * FROM `task` WHERE `id` = %d", id);
            try (
                final var fetchUserStatement = connection.prepareStatement (fetchTaskQuery);
                final var queryResult = fetchUserStatement.executeQuery ();
            ) {
                if (queryResult.next ()) {
                    if (consumer != null) {
                        consumer.accept (queryResult);
                    }
                    
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
    
    private void persist () {
        synchronized (this) {
            DatabaseService.getInstance ().doWrapped (connection -> {
                try (final var insertStatement = connection.prepareStatement ("UPDATE task SET state = ?, task = ?, type = ?, groups = ? WHERE id = ?")) {
                    int i = 1;
                    insertStatement.setString (i++, state.name ());
                    insertStatement.setString (i++, task);
                    insertStatement.setString (i++, type);
                    insertStatement.setString (i++, String.join (",", groups));
                    insertStatement.setLong (i++, id);
                    insertStatement.execute ();
                }
            });
        }
    }
    
    public boolean isQuestion () {
        return TYPE_QUESTION.equals (type);
    }
    
    public boolean isTask () {
        return TYPE_TASK.equals (type);
    }
    
    public TaskContext setState (TaskState state) {
        if (this.state != state && state != null) {
            this.state = state;
            
            persist ();
            updateMessage ();
        }
        
        return this;
    }
    
    private final DateFormat dateFormat = new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss");
    private final DateFormat dateFormatShort = new SimpleDateFormat ("HH:mm:ss");
    
    public void broadcastForGroup (String group) {
        log.info ("Sending broadcast message to `{}` group...", group);
        
        try {
            final var taskStatusMessageService = TaskStatusMessageService.getInstance ();
            
            prepareGroupMessage (group, (text, keyboard) -> {
                for (final var member : UserContextService.getInstance ().findGroupMembers (group)) {
                    if (member.getUserId () == authorId) {
                        continue;
                    }
                    
                    final var broadcastMessage = TelegramBot.getInstance ().sendMessage (member.getPrivateChatId (), cfg -> {
                        cfg.text (text);
                        cfg.replyMarkup (keyboard);
                    });
                    
                    taskStatusMessageService.addMessage (this, broadcastMessage);
                }
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    public void broadcastUpdateForGroup (String group) {
        log.info ("Sending broadcast message update to `{}` group...", group);
        
        try {
            prepareGroupMessage (group, (text, keyboard) -> {
                for (final var member : UserContextService.getInstance ().findGroupMembers (group)) {
                    if (member.getUserId () == authorId) {
                        continue;
                    }
                    
                    final var statusMessage = TaskStatusMessageService.getInstance ().findByTaskAndChat (id, member.getPrivateChatId ());
                    if (statusMessage != null) {
                        TelegramBot.getInstance ().sendMessageEdit (member.getPrivateChatId (), statusMessage.getMessageId (), cfg -> {
                            cfg.text (text);
                            cfg.replyMarkup (keyboard);
                        });
                    }
                }
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private void prepareGroupMessage (String group, FailableBiConsumer <String, InlineKeyboardMarkup, TelegramApiException> doOnReady) throws TelegramApiException {
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        final var taskStatusChangeService = TaskStatusChangeService.getInstance ();
        
        final var chatMember = TelegramBot.getInstance ().getChatMember (chatId, authorId);
        final var sj = prepareShortTaskMessage (chatMember.getUser (), task, type);
        sj.add ("#tasks #tid" + id);
        
        sj.add ("");
        sj.add ("🕵️‍♂️ <b>Текущий статус задачи:</b>");
        final var eventGroup = event.findGroupByName (group);
        sj.add ("");
        
        final var status = taskStatusChangeService.findMostRecent (id, eventGroup.getShortName ());
        //log.debug ("Most recent status change for {} task and {} group : {}", id, group, status);
        
        if (status == null) {
            sj.add ("<i><u>" + eventGroup.getDisplayName () + ":</u></i> 🙈 Не заметили задание");
        } else if (isQuestion ()) {
            final var changeAuthor = TelegramBot.getInstance ().getChatMember (chatId, status.getAuthorId ());
            
            sj.add (
                    "<i><u>" + eventGroup.getDisplayName () + ":</u></i> @" + changeAuthor.getUser ().getUserName ()
                    + " в " + dateFormatShort.format (status.getChangeDate ())
                    );
            sj.add (status.getContent ());
        } else if (isTask ()) {
            final var changeAuthor = TelegramBot.getInstance ().getChatMember (chatId, status.getAuthorId ());
            
            sj.add ("<i><u>" + eventGroup.getDisplayName () + ":</u></i> " + status.getContent ());
            sj.add ("⏰ " + dateFormat.format (status.getChangeDate ()));
            sj.add ("👤 @" + changeAuthor.getUser ().getUserName ());
        }
        
        doOnReady.accept (sj.toString (), prepareMarkup ());
    }
    
    public void updateMessage () {
        log.info ("Updating task message...");
        
        try {
            final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
            final var taskStatusChangeService = TaskStatusChangeService.getInstance ();
            
            final var chatMember = TelegramBot.getInstance ().getChatMember (chatId, authorId);
            
            final var sj = prepareShortTaskMessage (chatMember.getUser (), task, type);
            sj.add ("#tasks #tid" + id);
            
            sj.add ("");
            sj.add ("🕵️‍♂️ <b>Текущий статус задачи:</b>");
            for (final var group : groups) {
                final var eventGroup = event.findGroupByName (group);
                sj.add ("");
                
                final var status = taskStatusChangeService.findMostRecent (id, eventGroup != null ? eventGroup.getShortName () : "");
                //log.debug ("Most recent status change for {} task and {} group : {}", id, group, status);
                
                if (status == null) {
                    sj.add ("<i><u>" + eventGroup.getDisplayName () + ":</u></i> 🙈 Не заметили задание");
                } else if (isQuestion ()) {
                    final var changeAuthor = TelegramBot.getInstance ().getChatMember (chatId, status.getAuthorId ());
                    
                    sj.add (
                        "<i><u>" + eventGroup.getDisplayName () + ":</u></i> @" + changeAuthor.getUser ().getUserName ()
                        + " в " + dateFormatShort.format (status.getChangeDate ())
                    );
                    sj.add (status.getContent ());
                } else if (isTask ()) {
                    final var changeAuthor = TelegramBot.getInstance ().getChatMember (chatId, status.getAuthorId ());
                    
                    sj.add ("<i><u>" + eventGroup.getDisplayName () + ":</u></i> " + status.getContent ());
                    sj.add ("⏰ " + dateFormat.format (status.getChangeDate ()));
                    sj.add ("👤 @" + changeAuthor.getUser ().getUserName ());
                }
            }
            
            final var markup = prepareMarkup ();
            
            TelegramBot.getInstance ().sendMessageEdit (chatId, messageId, cfg -> {
                cfg.text (sj.toString ());
                cfg.replyMarkup (markup);
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private InlineKeyboardMarkup prepareMarkup () {
        final var markup = new InlineKeyboardMarkup ();
        markup.setKeyboard (new ArrayList <> ());
        if (isQuestion ()) {
            
        } else if (isTask ()) {
            final var processRow = new ArrayList <InlineKeyboardButton> ();
            markup.getKeyboard ().add (processRow);
            
            final var inProcessText = "💃 В процессе";
            processRow.add (InlineKeyboardButton.builder ()
                .text (inProcessText)
                .callbackData ("/answertask id " + id + "; answer " + inProcessText)
                .build ());
            
            final var doneText = "✅ Выполнили";
            processRow.add (InlineKeyboardButton.builder ()
                .text (doneText)
                .callbackData ("/answertask id " + id + "; answer " + doneText)
                .build ());
        }
        
        final var commentRow = new ArrayList <InlineKeyboardButton> ();
        markup.getKeyboard ().add (commentRow);
        
        commentRow.add (InlineKeyboardButton.builder ()
            .text ("💭 Ответить в свободной форме")
            .switchInlineQueryCurrentChat ("id " + id + "; answer\n")
            .build ());
        
        return markup;
    }
    
    public static StringJoiner prepareShortTaskMessage (User user, String task, String type) {
        final var sj = new StringJoiner ("\n");
        
        final var isQuestion = TYPE_QUESTION.equals (type);
        final var isTask = TYPE_TASK.equals (type);
        
        if (isQuestion) {
            sj.add ("‼️ <b>Новый вопрос от</b> @" + user.getUserName () + " ‼️");
        } else if (isTask) {
            sj.add ("‼️ <b>Новая задача от</b> @" + user.getUserName () + " ‼️");
        } else {
            throw new IllegalStateException ("Unknown state");
        }
        
        sj.add ("");
        //sj.add ("⏰ " + task);
        sj.add (task);
        
        return sj;
    }
    
}
