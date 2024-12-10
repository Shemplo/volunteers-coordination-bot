package ru.itmo.nerc.vcb.bot.chat.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import org.apache.commons.lang3.function.FailableBiConsumer;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.InlineQueryProcessor.ParsedQuery;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.chat.ChatMetaInformation;
import ru.itmo.nerc.vcb.bot.chat.ChatMetaInformationService;
import ru.itmo.nerc.vcb.bot.user.UserContext;
import ru.itmo.nerc.vcb.bot.user.UserContextService;
import ru.itmo.nerc.vcb.cfg.BotEventConfiguration;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;
import ru.itmo.nerc.vcb.db.DatabaseService;
import ru.itmo.nerc.vcb.db.DateUtils;

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
    
    private volatile Long stateEditorId;
    private volatile Date stateChangeDate;
    
    public TaskContext (UserContext author, Message message, ParsedQuery parsedQuery) {
        chatId = message.getChatId ();
        messageId = message.getMessageId ();
        authorId = author.getUserId ();
        
        task = parsedQuery.getTask ();
        type = parsedQuery.getType ();
        state = TaskState.CREATED;
        
        if (parsedQuery.getGroups () == null) {
            final var chatMetaInformationService = ChatMetaInformationService.getInstance ();
            final var defaultGroups = chatMetaInformationService.findOrCreateByChatAndKey (chatId, ChatMetaInformation.KEY_DEFAULT_GROUPS);
            if (defaultGroups.getValue () != null) {
                final var defaultGroupsList = Arrays.asList (defaultGroups.getValue ().split ("\\s*,\\s*"));
                groups = TaskUtils.decideGroups (parsedQuery.getEvent (), defaultGroupsList).a;
            }
        } else {
            groups = parsedQuery.getIncludeGroups ();
        }
        
        id = insertAndGetId (chatId, messageId, authorId, state);
        
        // This will cause `persist` and `updateMessage` methods to be called
        setState (null, TaskState.ENABLED);
    }
    
    public TaskContext (ResultSet queryResult) throws SQLException {
        id = queryResult.getLong ("id");

        chatId = queryResult.getLong ("chat_id");
        messageId = queryResult.getInt ("message_id");
        authorId = queryResult.getLong ("author_id");
        
        task = queryResult.getString ("task");
        type = queryResult.getString ("type");
        
        final var stateName = queryResult.getString ("state");
        state = TaskState.parseOrDefault (stateName, TaskState.ENABLED);
        
        final var groupsString = queryResult.getString ("groups");
        groups = groupsString == null || groupsString.length () == 0 ? List.of () : Arrays.asList (groupsString.split (","));
        
        stateEditorId = queryResult.getLong ("state_editor_id");
        if (queryResult.wasNull ()) {
            stateEditorId = null;
        }
        
        stateChangeDate = queryResult.getDate ("state_change_date");
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
    
    private void persist () {
        synchronized (this) {
            DatabaseService.getInstance ().doWrapped (connection -> {
                try (final var insertStatement = connection.prepareStatement (
                    "UPDATE task SET state = ?, task = ?, type = ?, groups = ?, state_editor_id = ?, state_change_date = ? WHERE id = ?"
                )) {
                    int i = 1;
                    insertStatement.setString (i++, state.name ());
                    insertStatement.setString (i++, task);
                    insertStatement.setString (i++, type);
                    insertStatement.setString (i++, String.join (",", groups));
                    if (stateEditorId == null) {
                        insertStatement.setNull (i++, Types.BIGINT);
                    } else {
                        insertStatement.setLong (i++, stateEditorId);
                    }
                    
                    if (stateChangeDate == null) {
                        insertStatement.setNull (i++, Types.DATE);
                    } else {
                        insertStatement.setDate (i++, new java.sql.Date (stateChangeDate.getTime ()));
                    }
                    
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
    
    public boolean isEnabled () {
        return state == TaskState.CREATED || state == TaskState.ENABLED;
    }
    
    public boolean isDisabled () {
        return state == TaskState.DISABLED;
    }
    
    public TaskContext setState (UserContext editor, TaskState state) {
        if (this.state != state && state != null) {
            this.state = state;
            
            if (editor != null) {
                stateEditorId = editor.getUserId ();
                stateChangeDate = new Date ();
            }
            
            persist ();
            updateMessage ();
        }
        
        return this;
    }
    
    public TaskContext setTask (String task) {
        if (!Objects.equals (this.task, task) && task != null) {
            this.task = task;
            
            persist ();
            updateMessage ();
        }
        
        return this;
    }
    
    public TaskContext setType (String type) {
        if (!Objects.equals (this.type, type) && type != null) {
            this.type = type;
            
            persist ();
            updateMessage ();
        }
        
        return this;
    }
    
    public TaskContext setGroups (List <String> groups) {
        if (!Objects.equals (this.groups, groups) && groups != null) {
            this.groups = List.copyOf (groups);
            
            persist ();
            updateMessage ();
        }
        
        return this;
    }
    
    public void broadcastForGroup (String group) {
        log.info ("Sending broadcast message to `{}` group...", group);
        
        try {
            final var taskUpdatesBroadcast = TaskUpdatesBroadcast.getInstance ();
            
            prepareGroupMessage (group, (text, keyboard) -> {
                for (final var member : UserContextService.getInstance ().findGroupMembers (group)) {
                    if (member.getUserId () == authorId) {
                        continue;
                    }
                    
                    log.info ("Broadcast message to @{}...", member.getUsername ());
                    taskUpdatesBroadcast.sendBroadcastMessage (this, member.getPrivateChatId (), text, keyboard);
                }
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    public void broadcastUpdateForGroup (String group, boolean taskEdited) {
        log.info ("Sending broadcast message update to `{}` group...", group);
        
        try {
            final var taskUpdatesBroadcast = TaskUpdatesBroadcast.getInstance ();
            
            prepareGroupMessage (group, (text, keyboard) -> {
                for (final var member : UserContextService.getInstance ().findGroupMembers (group)) {
                    if (member.getUserId () == authorId) {
                        continue;
                    }
                    
                    log.info ("Broadcast update to @{}...", member.getUsername ());
                    taskUpdatesBroadcast.sendBroadcastUpdate (this, taskEdited, member.getPrivateChatId (), text, keyboard);
                }
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private void prepareGroupMessage (String group, FailableBiConsumer <String, InlineKeyboardMarkup, TelegramApiException> doOnReady) throws TelegramApiException {
        final var userContextService = UserContextService.getInstance ();
        
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        final var author = userContextService.findContextForExistingUser (authorId);
        
        final var sj = prepareShortTaskMessage (author, task, type);
        sj.add ("#tasks #tid" + id);
        
        sj.add ("");
        sj.add ("🕵️‍♂️ <b>Текущий статус задачи:</b>");
        appendGroupStatus (sj, group, event);
        appendStateInformation (sj);
        
        doOnReady.accept (sj.toString (), prepareMarkup (false));
    }
    
    public void updateMessage () {
        log.info ("Updating task message...");
        
        try {
            final var userContextService = UserContextService.getInstance ();
            
            final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
            final var author = userContextService.findContextForExistingUser (authorId);
            
            final var sj = prepareShortTaskMessage (author, task, type);
            sj.add ("#tasks #tid" + id);
            
            sj.add ("");
            sj.add ("🕵️‍♂️ <b>Текущий статус задачи:</b>");
            for (final var group : groups) {
                appendGroupStatus (sj, group, event);
            }
            
            appendStateInformation (sj);
            
            final var markup = prepareMarkup (true);
            
            TelegramBot.getInstance ().sendMessageEdit (chatId, messageId, cfg -> {
                cfg.text (sj.toString ());
                cfg.replyMarkup (markup);
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private StringJoiner appendGroupStatus (StringJoiner sj, String group, BotEventConfiguration event) {
        final var taskStatusChangeService = TaskStatusChangeService.getInstance ();
        final var userContextService = UserContextService.getInstance ();
        
        final var eventGroup = event.findGroupByName (group);
        sj.add ("");
        
        final var status = taskStatusChangeService.findMostRecent (id, eventGroup.getShortName ());
        //log.debug ("Most recent status change for {} task and {} group : {}", id, group, status);
        
        if (status == null) {
            sj.add ("<i><u>" + eventGroup.getDisplayName () + ":</u></i> 🙈 Не заметили задание");
        } else if (isQuestion ()) {
            //final var changeAuthor = TelegramBot.getInstance ().getChatMember (chatId, status.getAuthorId ());
            final var changeAuthor = userContextService.findContextForExistingUser (status.getAuthorId ());
            
            sj.add (
                    "<i><u>" + eventGroup.getDisplayName () + ":</u></i> @" + changeAuthor.getUsername ()
                    + " в " + DateUtils.dateFormatShort.format (status.getChangeDate ())
                    );
            sj.add (status.getContent ());
        } else if (isTask ()) {
            //final var changeAuthor = TelegramBot.getInstance ().getChatMember (chatId, status.getAuthorId ());
            final var changeAuthor = userContextService.findContextForExistingUser (status.getAuthorId ());
            
            sj.add ("<i><u>" + eventGroup.getDisplayName () + ":</u></i> " + status.getContent ());
            sj.add ("⏰ " + DateUtils.dateFormat.format (status.getChangeDate ()));
            sj.add ("👤 @" + changeAuthor.getUsername ());
        }
        
        return sj;
    }
    
    private void appendStateInformation (StringJoiner sj) {
        if (isDisabled ()) {
            sj.add ("");
            
            if (stateEditorId != null && stateChangeDate != null) {
                final var userContextService = UserContextService.getInstance ();
                final var editor = userContextService.findContextForExistingUser (stateEditorId);
                
                sj.add ("⏯️ Задача приостановлена @" + editor.getUsername () + " в " + DateUtils.dateFormatShort.format (stateChangeDate));
            } else {
                sj.add ("⏯️ Задача приостановлена");
            }
        }
    }
    
    private InlineKeyboardMarkup prepareMarkup (boolean forSourceMessage) {
        final var markup = new InlineKeyboardMarkup ();
        markup.setKeyboard (new ArrayList <> ());
        
        if (isEnabled ()) {
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
        }
        
        if (forSourceMessage) {
            final var editorRow = new ArrayList <InlineKeyboardButton> ();
            markup.getKeyboard ().add (editorRow);
            
            editorRow.add (InlineKeyboardButton.builder ()
                .text ("📝 Изменить")
                .switchInlineQueryCurrentChat ("id " + id + "; task " + task + "; groups " + String.join (", ", groups))
                .build ());
            editorRow.add (InlineKeyboardButton.builder ()
                .text (isEnabled () ? "⏯️ Приостановить" : "▶️ Запустить")
                .callbackData ("/activationtask id " + id)
                .build ());
        }
        
        return markup;
    }
    
    public static StringJoiner prepareShortTaskMessage (UserContext user, String task, String type) {
        final var sj = new StringJoiner ("\n");
        
        final var isQuestion = TYPE_QUESTION.equals (type);
        final var isTask = TYPE_TASK.equals (type);
        
        if (isQuestion) {
            sj.add ("‼️ <b>Новый вопрос от</b> @" + user.getUsername () + " ‼️");
        } else if (isTask) {
            sj.add ("‼️ <b>Новая задача от</b> @" + user.getUsername () + " ‼️");
        } else {
            throw new IllegalStateException ("Unknown state");
        }
        
        sj.add ("");
        //sj.add ("⏰ " + task);
        sj.add (task);
        
        return sj;
    }
    
}
