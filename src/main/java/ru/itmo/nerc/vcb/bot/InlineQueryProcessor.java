package ru.itmo.nerc.vcb.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.antlr.v4.runtime.misc.Pair;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.chat.ChatMetaInformation;
import ru.itmo.nerc.vcb.bot.chat.ChatMetaInformationService;
import ru.itmo.nerc.vcb.bot.chat.antlr.GrammarUtils;
import ru.itmo.nerc.vcb.bot.chat.antlr.InlineQueryGrammarParser;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContext;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContextService;
import ru.itmo.nerc.vcb.bot.chat.task.TaskStatusChangeService;
import ru.itmo.nerc.vcb.bot.chat.task.TaskUtils;
import ru.itmo.nerc.vcb.bot.user.UserContextService;
import ru.itmo.nerc.vcb.bot.user.UserRole;
import ru.itmo.nerc.vcb.cfg.BotEventConfiguration;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;
import ru.itmo.nerc.vcb.utils.thread.RunnableActivity;
import ru.itmo.nerc.vcb.utils.thread.SupervisedRunnable;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class InlineQueryProcessor implements TelegramChildBot, SupervisedRunnable {
    
    private static volatile InlineQueryProcessor instance;
    
    public static InlineQueryProcessor getInstance () {
        if (instance == null) {
            synchronized (InlineQueryProcessor.class) {
                if (instance == null) {
                    instance = new InlineQueryProcessor ();
                }
            }
        }
        
        return instance;
    }
    
    private static final String EXCLAMATION_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/exclamation-mark-2095976-1766339.png?f=webp&w=128";
    private static final String EYES_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/eyes-6169080-5448298.png?f=webp&w=128";
    private static final String FIXED_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/fixed-9886456-8051306.png?f=webp&w=128";
    private static final String LABEL_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/label-2456302-2035261.png?f=webp&w=128";
    private static final String MESSAGE_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/message-2816681-2338234.png?f=webp&w=128";
    private static final String NEED_HELP_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/need-help-1-890042.png?f=webp&w=128";
    private static final String OK_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/ok-42-837715.png?f=webp&w=128";
    
    private final ChatMetaInformationService chatMetaInformationService = ChatMetaInformationService.getInstance ();
    private final TaskContextService taskContextService = TaskContextService.getInstance ();
    private final TaskStatusChangeService taskStatusChangeService = TaskStatusChangeService.getInstance ();
    private final UserContextService userContextService = UserContextService.getInstance ();
    
    private final Queue <Update> updatesQueue = new ConcurrentLinkedQueue <> ();
    
    @Override
    public void run () {
        while (!Thread.interrupted ()) {
            try {
                final var update = updatesQueue.poll ();
                if (update != null) {
                    onUpdateReceived (update);
                } else {
                    Thread.sleep (100L);
                }
            } catch (InterruptedException ie) {
                // Its' okay
            } catch (TelegramApiException tapie) {
                log.error ("Failed to handle inline query", tapie);
            }
        }
    }
    
    @Override
    public RunnableActivity getActivityAndReset () {
        return updatesQueue.isEmpty () ? RunnableActivity.IDLE: RunnableActivity.RUNNING;
    }
    
    public void addInlineQueryToQueue (Update update) {
        updatesQueue.add (update);
    }
    
    @Override
    public boolean onUpdateReceived (Update update) throws TelegramApiException {
        final var user = userContextService.findContextForUser (update.getInlineQuery ().getFrom ());
        final var query = update.getInlineQuery ().getQuery ().trim ();
        log.info ("Query: {}", query);
        
        final var parsedQuery = parseQuery (query);
        log.info ("Parsed query: {}", parsedQuery);
                
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        parsedQuery.setEvent (event);
        
        final var options = new ArrayList <InlineQueryResult> ();
        if (parsedQuery.getError () != null) {
            options.add (prepareErrorResult ("Ошибка в запросе", parsedQuery.getError ()));
        } else if (parsedQuery.getTask () != null) {
            if (!user.hasPermissions (UserRole.MODERATOR)) {
                options.add (prepareErrorResult ("Недостаточно прав", "У Вас недостаточно прав для создания задачи"));
            } else {
                log.info ("Query groups: {}", parsedQuery.getGroups ());
                
                if (parsedQuery.getGroups () == null) {
                    final var defaultGroups = chatMetaInformationService.findByKey (ChatMetaInformation.KEY_DEFAULT_GROUPS).stream ()
                        . map (ChatMetaInformation::getValue)
                        . filter (Objects::nonNull)
                        . distinct ()
                        . toList ();
                    
                    if (defaultGroups.isEmpty ()) {
                        final var decidedGroups = parsedQuery.getGroupsSplit ();
                        options.addAll (prepareTasksResult (parsedQuery, decidedGroups));
                    } else {
                        for (final var preset : defaultGroups) {
                            final var defaultGroupsList = Arrays.asList (preset.split ("\\s*,\\s*"));
                            final var decidedGroups = TaskUtils.decideGroups (event, defaultGroupsList);
                            options.addAll (prepareTasksResult (parsedQuery, decidedGroups));
                        }
                    }
                } else {
                    final var decidedGroups = parsedQuery.getGroupsSplit ();
                    options.addAll (prepareTasksResult (parsedQuery, decidedGroups));
                }
            }
        } else if (parsedQuery.getAnswer () != null) {
            if (user.getGroup () == null) {
                options.add (prepareErrorResult ("Невозможно обновить статус задачи", "Вы не состоите ни в какой группе"));
            } else {
                if (parsedQuery.getId () == null) {
                    options.add (prepareErrorResult ("Невозможно обновить статус задачи", "Не указан идентификатор (id)"));
                } else if (parsedQuery.getId () != null) {
                    final var task = taskContextService.findContext (parsedQuery.getId ());
                    if (task != null && task.isEnabled ()) {
                        final var status = taskStatusChangeService.addNoticedChange (task, "👀 Заметили задание", user);
                        if (status != null) {
                            task.updateMessage ();
                            task.broadcastForGroup (user.getGroup ());
                        }
                    }
                    
                    if (task == null) {
                        options.add (prepareErrorResult ("Невозможно обновить статус задачи", "Указан неверный идентификатор (id)"));
                    } else if (task.isDisabled ()) {
                        options.add (prepareErrorResult ("Невозможно обновить статус задачи", "Задача приостановлена"));
                    } else if (parsedQuery.getAnswer ().length () == 0) {
                        options.add (prepareAnswerResult (parsedQuery.getId (), "Уже смотрим", EYES_ICON));
                        options.add (prepareAnswerResult (parsedQuery.getId (), "Нужна помощь ТК", NEED_HELP_ICON));
                        options.add (prepareAnswerResult (parsedQuery.getId (), "Починили/исправили", FIXED_ICON));
                        options.add (prepareAnswerResult (parsedQuery.getId (), "Всё в порядке", OK_ICON));
                    } else {
                        options.add (prepareAnswerResult (parsedQuery.getId (), parsedQuery.getAnswer (), MESSAGE_ICON));
                    }
                }
            }
        } else if (parsedQuery.getKey () != null) {
            options.add (prepareMetaInformationResult (parsedQuery.getKey (), parsedQuery.getValue ()));
        }
        
        TelegramBot.getInstance ().sendInlineQueryResult (update.getInlineQuery (), options);
        return true;
    }
    
    @ToString
    @Getter @Setter
    @NoArgsConstructor
    public static class ParsedQuery {
        
        private Long id;
        
        private String task;
        
        private String type;
        
        private List <String> groups;
        
        private String answer;
        
        private String key;
        
        private String value;
        
        private String error;
        
        @JsonIgnore
        private BotEventConfiguration event;
        
        public Pair <List <String>, List <String>> getGroupsSplit () {
            return TaskUtils.decideGroups (event, groups == null ? List.of () : groups);
        }
        
        public List <String> getIncludeGroups () {
            return getGroupsSplit ().a;
        }
        
    }
    
    public static ParsedQuery parseQuery (String query) {
        final var parser = GrammarUtils.parse (InlineQueryGrammarParser.class, query);
        //parser.setTrace (true);
        final var json = parser.inlineQuery ().json;
        log.info ("Task in JSON format: {}", json);
        
        try {
            final var mapper = new ObjectMapper ();
            mapper.configure (JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature (), true);
            
            return mapper.readerFor (ParsedQuery.class).readValue (json);
        } catch (JsonProcessingException jpe) {
            final var parsedQuery = new ParsedQuery ();
            parsedQuery.setError (jpe.getMessage ());
            
            return parsedQuery;
        }
    }
    
    private InlineQueryResult prepareErrorResult (String title, String error) {
        return InlineQueryResultArticle.builder ()
             . id (UUID.randomUUID ().toString ())
             . title (title)
             . description (error)
             . thumbnailUrl (EXCLAMATION_ICON)
             . inputMessageContent (InputTextMessageContent.builder ()
                 .messageText ("/dropmessage")
                 .build ())
             . build ();
    }
    
    private List <InlineQueryResult> prepareTasksResult (ParsedQuery parsedQuery, Pair <List <String>, List <String>> decidedGroups) {
        final var task = parsedQuery.getTask ();
        final var suggestionMessage = prepareTaskPreviewMessage (task, decidedGroups.a, decidedGroups.b);
        
        final var options = new ArrayList <InlineQueryResult> ();
        for (final var suggestion : TASK_QUERY_SUGGESTIONS) {
            if (parsedQuery.getId () == null) {
                options.add (prepareTaskResult (suggestion, suggestionMessage, task, decidedGroups.a));
            } else {
                final var taskId = parsedQuery.getId ();
                options.add (prepareTaskEditResult (suggestion, suggestionMessage, taskId, task, decidedGroups.a));
            }
        }
        
        return options;
    }
    
    private InlineQueryResult prepareTaskResult (TaskQuerySuggestion suggestion, String suggestionMessage, String task, List <String> include) {
        final var command = "/createtask task " + task + "; groups " + String.join (", ", include) + "; type " + suggestion.getType () + "\n/dropmessage";
        
        return InlineQueryResultArticle.builder ()
             . id (UUID.nameUUIDFromBytes (command.getBytes ()).toString ())
             . description (suggestionMessage)
             . thumbnailUrl (suggestion.getIcon ())
             . title (suggestion.getDisplayType ())
             . inputMessageContent (InputTextMessageContent.builder ()
                 .messageText (command)
                 .build ())
             . build ();
    }
    
    private InlineQueryResult prepareTaskEditResult (TaskQuerySuggestion suggestion, String suggestionMessage, long taskId, String task, List <String> include) {
        final var command = "/edittask id " + taskId + "; task " + task + "; groups " + String.join (", ", include) + "; type " + suggestion.getType () + "\n/dropmessage";
        
        return InlineQueryResultArticle.builder ()
             . id (UUID.nameUUIDFromBytes (command.getBytes ()).toString ())
             . description (suggestionMessage)
             . thumbnailUrl (suggestion.getIcon ())
             . title (suggestion.getDisplayEditType ())
             . inputMessageContent (InputTextMessageContent.builder ()
                 .messageText (command)
                 .build ())
             . build ();
    }
    
    private InlineQueryResult prepareAnswerResult (long taskId, String answer, String icon) {
        final var command = "/answertask id " + taskId + "; answer " + answer + "\n/dropmessage";
        return InlineQueryResultArticle.builder ()
             . hideUrl (true)
             . id (UUID.nameUUIDFromBytes (command.getBytes ()).toString ())
             . title ("Ответ на задачу #tid" + taskId)
             . description (answer)
             . thumbnailUrl (icon)
             . inputMessageContent (InputTextMessageContent.builder ()
                 .messageText (command)
                 .build ())
             . build ();
    }
    
    private InlineQueryResult prepareMetaInformationResult (String key, String value) {
        final var command = "/writemeta key " + key + "; value " + value + "\n/dropmessage";
        return InlineQueryResultArticle.builder ()
             . id (UUID.nameUUIDFromBytes (command.getBytes ()).toString ())
             . title ("Изменить значение метаинформации")
             . description (key + ": " + value)
             . thumbnailUrl (LABEL_ICON)
             . inputMessageContent (InputTextMessageContent.builder ()
                 .messageText (command)
                 .build ())
             . build ();
    }
    
    private String prepareTaskPreviewMessage (String task, List <String> include, List <String> exclude) {
        final var suggestionMessageBuilder = new StringJoiner ("\n");
        suggestionMessageBuilder.add (task.length () < 28 ? task : task.substring (0, 28) + "...");
        
        String groupsString = "Группы: " + String.join (", ", include);
        if (!exclude.isEmpty ()) {
            groupsString += " / " + String.join (", ", exclude);
        }
        
        suggestionMessageBuilder.add (groupsString);
        
        return suggestionMessageBuilder.toString ();
    }
    
    @Getter
    @RequiredArgsConstructor
    private class TaskQuerySuggestion {
        
        private final String displayType;
        
        private final String displayEditType;
        
        private final String type;
        
        private final String icon;
        
    }
    
    private final List <TaskQuerySuggestion> TASK_QUERY_SUGGESTIONS = List.of (
        new TaskQuerySuggestion (
            "Задача с вопросом", "Сделать задачей с вопросом",
            TaskContext.TYPE_QUESTION, "https://cdn.iconscout.com/icon/premium/png-256-thumb/question-mark-2716891-2263063.png?f=webp&w=64"
        ),
        new TaskQuerySuggestion (
            "Задача на выполнение", "Сделать задачей на выполнение",
            TaskContext.TYPE_TASK, "https://cdn.iconscout.com/icon/premium/png-256-thumb/task-71158.png?f=webp&w=64"
        )
    );
    
}
