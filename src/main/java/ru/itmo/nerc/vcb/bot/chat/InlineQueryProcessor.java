package ru.itmo.nerc.vcb.bot.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.TelegramChildBot;
import ru.itmo.nerc.vcb.bot.chat.antlr.GrammarUtils;
import ru.itmo.nerc.vcb.bot.chat.antlr.InlineQueryGrammarParser;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContext;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContextService;
import ru.itmo.nerc.vcb.bot.chat.task.TaskStatusChangeService;
import ru.itmo.nerc.vcb.bot.chat.task.TaskUtils;
import ru.itmo.nerc.vcb.bot.user.UserContextService;
import ru.itmo.nerc.vcb.bot.user.UserRole;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class InlineQueryProcessor implements TelegramChildBot {
    
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
    private static final String MESSAGE_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/message-2816681-2338234.png?f=webp&w=128";
    private static final String NEED_HELP_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/need-help-1-890042.png?f=webp&w=128";
    private static final String OK_ICON = "https://cdn.iconscout.com/icon/premium/png-256-thumb/ok-42-837715.png?f=webp&w=128";
    
    private final TaskContextService taskContextService = TaskContextService.getInstance ();
    private final TaskStatusChangeService taskStatusChangeService = TaskStatusChangeService.getInstance ();
    private final UserContextService userContextService = UserContextService.getInstance ();
    
    @Override
    public boolean onUpdateReceived (Update update) throws TelegramApiException {
        final var user = userContextService.findContextForUser (update.getInlineQuery ().getFrom ());
        final var query = update.getInlineQuery ().getQuery ().trim ();
        log.info (" Query: {}", query);
        
        final var parsedQuery = parseQuery (query);
        log.info ("Parsed query: {}", parsedQuery);
                
        final var groupsQuery = parsedQuery.getGroups () == null ? List.<String> of () : parsedQuery.getGroups ();
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        final var decidedGroups = TaskUtils.decideGroups (event, groupsQuery);
        
        final var options = new ArrayList <InlineQueryResult> ();
        if (parsedQuery.getError () != null) {
            options.add (InlineQueryResultArticle.builder ()
                .hideUrl (true)
                .id (UUID.randomUUID ().toString ())
                .title ("Ошибка в запросе")
                .description (parsedQuery.getError ())
                .thumbnailUrl (EXCLAMATION_ICON)
                .inputMessageContent (InputTextMessageContent.builder ()
                    .messageText ("/dropmessage")
                    .build ())
                .build ());
        } else if (parsedQuery.getTask () != null) {
            if (!user.hasPermissions (UserRole.MODERATOR)) {
                options.add (InlineQueryResultArticle.builder ()
                    .hideUrl (true)
                    .id (UUID.randomUUID ().toString ())
                    .title ("Недостаточно прав")
                    .description ("У Вас недостаточно прав для создания задачи")
                    .thumbnailUrl (EXCLAMATION_ICON)
                    .inputMessageContent (InputTextMessageContent.builder ()
                        .messageText ("/dropmessage")
                        .build ())
                    .build ());
            } else {
                final var suggestionMessageBuilder = new StringJoiner ("\n");
                suggestionMessageBuilder.add (parsedQuery.getTask ().length () < 32 ? parsedQuery.getTask () : parsedQuery.getTask ().substring (0, 32));
                suggestionMessageBuilder.add ("Назначенные группы: " + String.join (", ", decidedGroups.a));
                if (!decidedGroups.b.isEmpty ()) {
                    suggestionMessageBuilder.add ("Доступные группы: " + String.join (", ", decidedGroups.b));
                }
                
                final var suggestionMessage = suggestionMessageBuilder.toString ();
                
                for (final var suggestion : QUERY_SUGGESTIONS) {
                    final var command = makeCommandCreateTask (parsedQuery.getTask (), decidedGroups.a, suggestion.getType ());
                    
                    options.add (InlineQueryResultArticle.builder ()
                        .id (UUID.nameUUIDFromBytes (command.getBytes ()).toString ())
                        .description (suggestionMessage)
                        .hideUrl (true)
                        .thumbnailUrl (suggestion.getIcon ())
                        .title (suggestion.getDisplayType ())
                        .inputMessageContent (InputTextMessageContent.builder ()
                            .messageText (command)
                            .build ())
                        .build ());
                }
            }
        } else if (parsedQuery.getAnswer () != null) {
            if (user.getGroup () == null) {
                options.add (InlineQueryResultArticle.builder ()
                    .hideUrl (true)
                    .id (UUID.randomUUID ().toString ())
                    .title ("Невозможно обновить статус задачи")
                    .description ("Вы не подписаны ни на какую группу, поэтому вы не можете обновить статус задачи")
                    .thumbnailUrl (EXCLAMATION_ICON)
                    .inputMessageContent (InputTextMessageContent.builder ()
                        .messageText ("/dropmessage")
                        .build ())
                    .build ());
            } else {
                if (parsedQuery.getId () != null) {
                    final var task = taskContextService.findContext (parsedQuery.getId ());
                    if (task != null) {
                        final var status = taskStatusChangeService.addNoticedChange (task, "👀 Заметили задание", user);
                        if (status != null) {
                            task.updateMessage ();
                            task.broadcastForGroup (user.getGroup ());
                        }
                    }
                }
                
                if (parsedQuery.getId () == null) {
                    options.add (InlineQueryResultArticle.builder ()
                        .hideUrl (true)
                        .id (UUID.randomUUID ().toString ())
                        .title ("Невозможно обновить статус задачи")
                        .description ("Вы не подписаны ни на какую группу, поэтому вы не можете обновить статус задачи")
                        .thumbnailUrl (EXCLAMATION_ICON)
                        .inputMessageContent (InputTextMessageContent.builder ()
                            .messageText ("/dropmessage")
                            .build ())
                        .build ());
                } else if (parsedQuery.getAnswer ().length () == 0) {
                    final var commandSee = "/answertask id " + parsedQuery.getId () + "; answer Уже смотрим\n/dropmessage";
                    options.add (InlineQueryResultArticle.builder ()
                        .hideUrl (true)
                        .id (UUID.nameUUIDFromBytes (commandSee.getBytes ()).toString ())
                        .title ("Ответ на задачу #tid" + parsedQuery.getId ())
                        .description ("Уже смотрим")
                        .thumbnailUrl (EYES_ICON)
                        .inputMessageContent (InputTextMessageContent.builder ()
                            .messageText (commandSee)
                            .build ())
                        .build ());
                    
                    final var commandNeedHelp = "/answertask id " + parsedQuery.getId () + "; answer Нужна помощь ТК\n/dropmessage";
                    options.add (InlineQueryResultArticle.builder ()
                        .hideUrl (true)
                        .id (UUID.nameUUIDFromBytes (commandNeedHelp.getBytes ()).toString ())
                        .title ("Ответ на задачу #tid" + parsedQuery.getId ())
                        .description ("Нужна помощь ТК")
                        .thumbnailUrl (NEED_HELP_ICON)
                        .inputMessageContent (InputTextMessageContent.builder ()
                            .messageText (commandNeedHelp)
                            .build ())
                        .build ());
                    
                    final var commandFixed = "/answertask id " + parsedQuery.getId () + "; answer Починили/исправили\n/dropmessage";
                    options.add (InlineQueryResultArticle.builder ()
                        .hideUrl (true)
                        .id (UUID.nameUUIDFromBytes (commandFixed.getBytes ()).toString ())
                        .title ("Ответ на задачу #tid" + parsedQuery.getId ())
                        .description ("Починили/исправили")
                        .thumbnailUrl (FIXED_ICON)
                        .inputMessageContent (InputTextMessageContent.builder ()
                            .messageText (commandFixed)
                            .build ())
                        .build ());
                    
                    final var commandOk = "/answertask id " + parsedQuery.getId () + "; answer Всё в порядке\n/dropmessage";
                    options.add (InlineQueryResultArticle.builder ()
                        .hideUrl (true)
                        .id (UUID.nameUUIDFromBytes (commandOk.getBytes ()).toString ())
                        .title ("Ответ на задачу #tid" + parsedQuery.getId ())
                        .description ("Всё в порядке")
                        .thumbnailUrl (OK_ICON)
                        .inputMessageContent (InputTextMessageContent.builder ()
                            .messageText (commandOk)
                            .build ())
                        .build ());
                } else {
                    final var command = "/answertask id " + parsedQuery.getId () + "; answer " + parsedQuery.getAnswer () + "\n/dropmessage";
                    
                    options.add (InlineQueryResultArticle.builder ()
                        .hideUrl (true)
                        .id (UUID.nameUUIDFromBytes (command.getBytes ()).toString ())
                        .title ("Ответ на задачу #tid" + parsedQuery.getId ())
                        .description (parsedQuery.getAnswer ())
                        .thumbnailUrl (MESSAGE_ICON)
                        .inputMessageContent (InputTextMessageContent.builder ()
                                .messageText (command)
                                .build ())
                        .build ());
                }
            }
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
        
        private String error;
        
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
    
    @Getter
    @RequiredArgsConstructor
    private class QuerySuggestion {
        
        private final String displayType;
        
        private final String type;
        
        private final String icon;
        
    }
    
    private final List <QuerySuggestion> QUERY_SUGGESTIONS = List.of (
        new QuerySuggestion ("Задача с вопросом", TaskContext.TYPE_QUESTION, "https://cdn.iconscout.com/icon/premium/png-256-thumb/question-mark-2716891-2263063.png?f=webp&w=64"),
        new QuerySuggestion ("Задача на выполнение", TaskContext.TYPE_TASK, "https://cdn.iconscout.com/icon/premium/png-256-thumb/task-71158.png?f=webp&w=64")
    );
    
    @SuppressWarnings ("unused")
    private String makeCommandCreateTaskJSON (String task, List <String> groups, String type) {
        final var convertedGroups = groups.stream ().map (g -> "\"" + g + "\"").collect (Collectors.joining (",", "[", "]"));
        return "/createtask {\"task\":\"" + task + "\", \"groups\":" + convertedGroups + ", \"type\":\"" + type + "\"}";
    }
    
    private String makeCommandCreateTask (String task, List <String> groups, String type) {
        return "/createtask task " + task + "; groups " + String.join (", ", groups) + "; type " + type + "\n/dropmessage";
    }
    
}
