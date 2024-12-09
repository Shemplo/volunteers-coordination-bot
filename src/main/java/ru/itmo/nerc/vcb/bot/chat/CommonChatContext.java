package ru.itmo.nerc.vcb.bot.chat;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.lang3.function.FailableRunnable;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.chat.pending.ChatPending;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContext;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContextService;
import ru.itmo.nerc.vcb.bot.chat.task.TaskStatusChangeService;
import ru.itmo.nerc.vcb.bot.chat.task.TaskUtils;
import ru.itmo.nerc.vcb.bot.user.UserContext;
import ru.itmo.nerc.vcb.bot.user.UserContextService;
import ru.itmo.nerc.vcb.bot.user.UserRole;
import ru.itmo.nerc.vcb.cfg.BotEventConfiguration.EventActivity;
import ru.itmo.nerc.vcb.cfg.BotEventConfiguration.EventGroup;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;
import ru.itmo.nerc.vcb.utils.DateUtils;

@Slf4j
public class CommonChatContext implements ChatContext {
    
    public static final String ANSWER_TASK_COMMAND = "/answertask";
    
    @Getter
    protected final long chatId;
    
    protected final Deque <ChatPending> pendings = new LinkedList <> ();
    
    public CommonChatContext (Chat chat) {
        this.chatId = chat.getId ();
    }
    
    protected final ChatContextService chatContextService = ChatContextService.getInstance ();
    protected final TaskContextService taskContextService = TaskContextService.getInstance ();
    protected final TaskStatusChangeService taskStatusChangeService = TaskStatusChangeService.getInstance ();
    protected final UserContextService userContextService = UserContextService.getInstance ();
    
    private final Pattern TASK_ID_PATTERN = Pattern.compile ("#tasks #tid(\\d+)");
    
    @Override
    public boolean onUpdateReceived (Update update) throws TelegramApiException {
        if (update.hasCallbackQuery ()) {
            final var user = userContextService.findContextForUser (update.getCallbackQuery ().getFrom ());
            final var message = (Message) update.getCallbackQuery ().getMessage ();
            
            final var callback = update.getCallbackQuery ().getData ();
            log.info ("Callback: {}", callback);
            final var commandFinishIndex = callback.indexOf (' ');
            if (commandFinishIndex == -1) {
                return false;
            }
            
            final var command = callback.substring (0, commandFinishIndex);
            final var argument = callback.substring (commandFinishIndex + 1);
            
            try {
                processCommand (user, message, new Pair <> (command, argument));
                return true;
            } catch (CommandProcessingException cpe) {
                processCommandProcessingException (message, cpe);
                return false;
            }
        } else if (update.hasMessage () && update.getMessage ().hasText ()) {
            final var message = update.getMessage ();
            final var commands = ChatUtils.fetchCommand (message);
            log.info ("Fetched commands: {}", commands);
            
            final var user = userContextService.findContextForUser (message.getFrom ());
            
            if (message.getReplyToMessage () != null && message.getReplyToMessage ().hasText ()) {
                final var replyTo = message.getReplyToMessage ();
                
                final var taskIdMatcher = TASK_ID_PATTERN.matcher (replyTo.getText ());
                if (taskIdMatcher.find ()) {
                    final var taskId = Long.parseLong (taskIdMatcher.group (1));
                    
                    try {
                        processCommand (user, message, new Pair <> (ANSWER_TASK_COMMAND, "id " + taskId + "; answer " + message.getText ()));
                        return true;
                    } catch (CommandProcessingException cpe) {
                        processCommandProcessingException (message, cpe);
                        return false;
                    }
                }
            } else if (!commands.isEmpty ()) {
                try {
                    try {
                        for (final var command : commands) {
                            processCommand (user, message, command);
                        }
                        
                        return true;
                    } catch (CommandProcessingException cpe) {
                        processCommandProcessingException (message, cpe);
                        return false;
                    }
                } catch (TelegramApiException tapie) {
                    log.error ("Failed to send Telegram request", tapie);
                    return false;
                }
            }
        }
        
        return false;
    }
    
    protected void persist () {
        
    }
    
    protected void processCommand (UserContext user, Message message, Pair <String, String> command) throws CommandProcessingException, TelegramApiException {
        switch (command.a) {
            case ANSWER_TASK_COMMAND -> checkAndCall (user, UserRole.PARTICIPANT, () -> answerToTask (user, message, command.b));
            case "/createtask" -> checkAndCall (user, UserRole.MODERATOR, () -> createTask (user, message, command.b));
            case "/dropmessage" -> checkAndCall (user, UserRole.UNKNOWN, () -> dropMessage (message));
            case "/eventinfo" -> checkAndCall (user, UserRole.PARTICIPANT, () -> showEventInfo (message));
            case "/subscribe" -> checkAndCall (user, UserRole.PARTICIPANT, () -> subscribeToGroup (user, message, command.b));
            case "/unsubscribe" -> checkAndCall (user, UserRole.PARTICIPANT, () -> subscribeToGroup (user, message, null));
            
            default -> TelegramBot.getInstance ().setReactionOnMessage (message, "🤷‍♂️");
        };
    }
    
    protected void processCommandProcessingException (Message message, CommandProcessingException exception) throws TelegramApiException {
        TelegramBot.getInstance ().sendReplyMessage (message, cfg -> {
            cfg.text (exception.getMessage ());
        });
    }
    
    protected boolean checkAndCall (
        UserContext user,
        UserRole requiredRole,
        FailableRunnable <CommandProcessingException> call
    ) throws TelegramApiException, CommandProcessingException {
        if (!user.hasPermissions (requiredRole)) {
            throw new CommandProcessingException ("Недостаточно прав для выполнениия этой операции");
        }
        
        call.run ();
        return true;
    }
    
    private void answerToTask (UserContext user, Message message, String query) throws CommandProcessingException {
        final var parsedQuery = InlineQueryProcessor.parseQuery (query);
        final var answer = parsedQuery.getAnswer ();
        final var id = parsedQuery.getId ();
        
        if (parsedQuery.getError () != null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b> " + parsedQuery.getError ());
        } else if (answer == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nОтсутствует необходимое поле <code>[answer]</code>");
        } else if (id == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nОтсутствует необходимое поле <code>[id]</code>");
        } else if (user.getGroup () == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nВы не можете обновить статус задачи, потому что не подписаны ни на какую группу");
        }
        
        final var task = taskContextService.findContext (id);
        if (task == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nЗадача не найдена");
        }
        
        taskStatusChangeService.addChange (task, answer, message, user);
        
        task.updateMessage ();
        task.broadcastUpdateForGroup (user.getGroup ());
    }
    
    private void createTask (UserContext user, Message message, String query) throws CommandProcessingException {
        final var parsedQuery = InlineQueryProcessor.parseQuery (query);
        final var groups = parsedQuery.getGroups ();
        final var task = parsedQuery.getTask ();
        final var type = parsedQuery.getType ();
        
        if (parsedQuery.getError () != null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b> " + parsedQuery.getError ());
        } else if (task == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nОтсутствует необходимое поле <code>[task]</code>");
        } else if (type == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nОтсутствует необходимое поле <code>[type]</code>");
        } else if (!TaskContext.TYPE_TASK.equals (type) && !TaskContext.TYPE_QUESTION.equals (type)) {
            throw new CommandProcessingException (
                "<b>Ошибка в запросе:</b>\nТип задачи может быть только <code>" + TaskContext.TYPE_TASK
                + "</code> или <code>" + TaskContext.TYPE_QUESTION + "</code>"
            );
        }
        
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        final var groupsQuery = groups == null ? List.<String> of () : groups;
        final var decidedGroups = TaskUtils.decideGroups (event, groupsQuery);
        parsedQuery.setGroups (decidedGroups.a);
        
        try {
            final var chatMember = TelegramBot.getInstance ().getChatMember (chatId, user.getUserId ());
            
            final var taskMessage = TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.text (TaskContext.prepareShortTaskMessage (chatMember.getUser (), task, type).toString ());
            });
            
            final var taskEntity = taskContextService.createContext (chatMember.getUser (), taskMessage, parsedQuery);
            for (final var group : parsedQuery.getGroups ()) {
                taskEntity.broadcastForGroup (group);
            }
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private void dropMessage (Message message) {
        try {
            TelegramBot.getInstance ().deleteMessage (message);
        } catch (TelegramApiException tapie) {
            log.error ("Failed to delete message", tapie);
        }
    }
    
    private void showEventInfo (Message message) {
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        Collections.sort (event.getTimetable ().getActivities (), Comparator.comparing (EventActivity::getFrom));
        
        final var sj = new StringJoiner ("\n");
        sj.add ("<b>Событие:</b> " + event.getName ());
        
        final var now = new Date ();
        final var currentActivity = event.getTimetable ().getActivities ().stream ()
            . filter (activity -> activity.getFrom ().before (now) && activity.getTo ().after (now))
            . findFirst ()
            . orElse (null);
        sj.add ("<b>Текущая активность:</b> " + (currentActivity == null ? "отсутсвует" : currentActivity.getActivity ()));
        if (currentActivity != null) {
            sj.add ("<i>До " + DateUtils.dateFormat.format (currentActivity.getTo ()) + "</i>");
        }
        
        final var nextActivity = event.getTimetable ().getActivities ().stream ()
            . filter (activity -> activity.getFrom ().after (now))
            . findFirst ()
            . orElse (null);
        sj.add ("<b>Следующая активность:</b> " + (nextActivity == null ? "отсутсвует" : nextActivity.getActivity ()));
        if (nextActivity != null) {
            sj.add ("<i>C " + DateUtils.dateFormat.format (nextActivity.getFrom ()) + "</i>");
            sj.add ("<i>До " + DateUtils.dateFormat.format (nextActivity.getTo ()) + "</i>");
        }
        
        try {
            TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.text (sj.toString ());
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private void subscribeToGroup (UserContext user, Message message, String groupName) throws CommandProcessingException {
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        final var group = event.findGroupByName (groupName);
        
        if (groupName != null && group == null) {
            final var shortNames = event.getGroups ().stream ()
                . map (EventGroup::getShortName)
                . collect (Collectors.joining (", "));
            
            throw new CommandProcessingException (String.format ("""
            Неизвестная группа, попробуйте указать другое название
            
            Доступные названия групп: %s
            """, shortNames));
        }
        
        user.setGroup (group != null ? group.getShortName () : null);
        
        try {
            TelegramBot.getInstance ().sendReplyMessage (message, cfg -> {
                if (groupName != null) {
                    cfg.text ("Теперь Вы подписаны на уведомления для группы: <b>" + group.getDisplayName () + "</b>");
                } else {
                    cfg.text ("Вы отменили подписку на уведомления для всех групп");
                }
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
}
