package ru.itmo.nerc.vcb.bot.chat;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Deque;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.lang3.function.FailableRunnable;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.InlineQueryProcessor;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.chat.pending.ChatPending;
import ru.itmo.nerc.vcb.bot.chat.pending.ChooseSubscriptionGroupPending;
import ru.itmo.nerc.vcb.bot.chat.pending.CodeAuthenticationPending;
import ru.itmo.nerc.vcb.bot.chat.pending.DelayedCommandProcessingPending;
import ru.itmo.nerc.vcb.bot.chat.task.TaskCommandValidator;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContext;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContextService;
import ru.itmo.nerc.vcb.bot.chat.task.TaskState;
import ru.itmo.nerc.vcb.bot.chat.task.TaskStatusChangeService;
import ru.itmo.nerc.vcb.bot.user.UserContext;
import ru.itmo.nerc.vcb.bot.user.UserContextService;
import ru.itmo.nerc.vcb.bot.user.UserRole;
import ru.itmo.nerc.vcb.cfg.BotEventConfiguration.EventActivity;
import ru.itmo.nerc.vcb.cfg.BotEventConfiguration.EventGroup;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;
import ru.itmo.nerc.vcb.db.DateUtils;

@Slf4j
public class CommonChatContext implements ChatContext {
    
    public static final String ANSWER_TASK_COMMAND = "/answertask";
    
    @Getter
    protected final long chatId;
    
    protected final Deque <ChatPending> pendings = new ConcurrentLinkedDeque <> ();
    
    public CommonChatContext (Chat chat) {
        this.chatId = chat.getId ();
    }
    
    protected final ChatContextService chatContextService = ChatContextService.getInstance ();
    protected final ChatMetaInformationService chatMetaInformationService = ChatMetaInformationService.getInstance ();
    protected final TaskContextService taskContextService = TaskContextService.getInstance ();
    protected final TaskStatusChangeService taskStatusChangeService = TaskStatusChangeService.getInstance ();
    protected final UserContextService userContextService = UserContextService.getInstance ();
    
    private final Pattern TASK_ID_PATTERN = Pattern.compile ("#tasks #tid(\\d+)");
    
    @Override
    public boolean onUpdateReceived (Update update) throws TelegramApiException {
        UserContext user = null;
        
        if (update.hasCallbackQuery ()) {
            user = userContextService.findContextForUser (update.getCallbackQuery ().getFrom ());
            final var message = (Message) update.getCallbackQuery ().getMessage ();
            
            final var callback = update.getCallbackQuery ().getData ();
            log.info ("Callback: {}", callback);
            final var commandFinishIndex = callback.indexOf (' ');
            if (callback.startsWith ("/") && commandFinishIndex != -1) {
                final var command = callback.substring (0, commandFinishIndex);
                final var argument = callback.substring (commandFinishIndex + 1);
                
                try {
                    processCommand (user, message, new Pair <> (command, argument));
                    return true;
                } catch (CommandProcessingException cpe) {
                    processCommandProcessingException (user, message, cpe);
                    return false;
                }
            }
        } else if (update.hasMessage () && update.getMessage ().hasText ()) {
            final var message = update.getMessage ();
            user = userContextService.findContextForUser (message.getFrom ());
            
            final var commands = ChatUtils.fetchCommand (message);
            log.info ("Fetched commands: {}", commands);
            
            if (message.getReplyToMessage () != null && message.getReplyToMessage ().hasText ()) {
                final var replyTo = message.getReplyToMessage ();
                
                final var taskIdMatcher = TASK_ID_PATTERN.matcher (replyTo.getText ());
                if (taskIdMatcher.find ()) {
                    final var taskId = Long.parseLong (taskIdMatcher.group (1));
                    
                    try {
                        processCommand (user, message, new Pair <> (ANSWER_TASK_COMMAND, "id " + taskId + "; answer " + message.getText ()));
                        return true;
                    } catch (CommandProcessingException cpe) {
                        processCommandProcessingException (user, message, cpe);
                        return false;
                    }
                }
            } else if (!commands.isEmpty ()) {
                return processCommandsOneByOne (user, message, commands);
            }
        }
        
        if (pendings.isEmpty ()) {
            return false;
        }
        
        try {
            log.info ("Call udpdate on pending {}", pendings.peek ().getClass ());
            return switch (pendings.peek ().onUpdateReceived (update)) {
                case RESOLVED     -> pollPendings (1);
                case RESOLVED_ALL -> pollPendings (pendings.size ());
                case KEEP         -> false; // Do nothing, pending is not resolved
            };
        } catch (CommandProcessingException cpe) {
            processCommandProcessingException (user, update.getMessage (), cpe);
            return true;
        }
    }
    
    protected boolean processCommandsOneByOne (UserContext user, Message message, Queue <Pair <String, String>> commands) {
        try {
            try {
                while (!commands.isEmpty ()) {
                    final var command = commands.poll ();
                    processCommand (user, message, command);
                }
                
                return true;
            } catch (CommandProcessingException cpe) {
                if (cpe instanceof PendingAddedException) {
                    while (!commands.isEmpty ()) {
                        final var command = commands.poll ();
                        pendings.add (new DelayedCommandProcessingPending (this, user, message, command));
                    }
                    
                    return true;
                } else {
                    processCommandProcessingException (user, message, cpe);
                    return false;
                }
            }
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send Telegram request", tapie);
            return false;
        }
    }
    
    @Override
    public void processCommand (UserContext user, Message message, Pair <String, String> command) throws CommandProcessingException, TelegramApiException {
        switch (command.a) {
            case ANSWER_TASK_COMMAND -> checkAndCall (user, UserRole.PARTICIPANT, () -> answerToTask (user, message, command.b));
            case "/activationtask" -> checkAndCall (user, UserRole.MODERATOR, () -> changeTaskActivation (user, message, command.b));
            case "/createtask" -> checkAndCall (user, UserRole.MODERATOR, () -> createTask (user, message, command.b));
            case "/dropmessage" -> checkAndCall (user, UserRole.UNKNOWN, () -> dropMessage (message));
            case "/edittask" -> checkAndCall (user, UserRole.MODERATOR, () -> editTask (user, message, command.b));
            case "/eventinfo" -> checkAndCall (user, UserRole.PARTICIPANT, () -> showEventInfo (message));
            case "/writemeta" -> checkAndCall (user, UserRole.PARTICIPANT, () -> writeMetainformation (command.b));
            
            default -> TelegramBot.getInstance ().setReactionOnMessage (message, "🤷‍♂️");
        };
    }
    
    protected void processCommandProcessingException (UserContext user, Message message, CommandProcessingException exception) throws TelegramApiException {
        if (message != null) {
            TelegramBot.getInstance ().sendReplyMessage (message, cfg -> {
                cfg.text (exception.getMessage ());
            });
        } else {
            TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.text (exception.getMessage ());
            });
        }
    }
    
    protected boolean checkAndCall (
        UserContext user,
        UserRole requiredRole,
        FailableRunnable <CommandProcessingException> call
    ) throws TelegramApiException, CommandProcessingException {
        if (!user.hasPermissions (requiredRole)) {
            throw new CommandProcessingException ("Недостаточно прав для выполнениия этой операции");
        }
        
        if (pendings.isEmpty () || !pendings.peek ().isBlocking ()) {
            call.run ();
            return true;
        } else {
            TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.text ("Закончите предыдущее действие перед тем как вызвать новую команду ⚠");
            });
            
            return true;
        }
    }
    
    private boolean pollPendings (int amount) throws TelegramApiException {
        boolean resolvedAtLeastOne = false;
        for (int i = 0; i < amount && !pendings.isEmpty (); i++) {
            final var pendign = pendings.poll ();
            pendign.onPendingResolved ();
            resolvedAtLeastOne = true;
            
            log.info ("Pending {} was resolved", pendign.getClass () );
        }
        
        log.info ("Current pending stack: {}", pendings.stream ().map (p -> p.getClass ()).toList ());
        if (!pendings.isEmpty ()) {
            log.info ("Pending is activated", pendings.peek ().getClass ());
            final var resolvedAutomatically = switch (pendings.peek ().onPendigActivated ()) {
                case RESOLVED     -> pollPendings (1);
                case RESOLVED_ALL -> pollPendings (pendings.size ());
                default -> false;
            };
            
            resolvedAtLeastOne = resolvedAtLeastOne || resolvedAutomatically;
        }
        
        return resolvedAtLeastOne;
    }
    
    private void answerToTask (UserContext user, Message message, String query) throws CommandProcessingException {
        final var parsedQuery = InlineQueryProcessor.parseQuery (query);
        TaskCommandValidator.checkError (parsedQuery);
        TaskCommandValidator.checkAnswer (parsedQuery);
        TaskCommandValidator.checkId (parsedQuery);
        final var id = parsedQuery.getId ();
        
        TaskCommandValidator.checkUserGroup (user, id);
        
        final var answer = parsedQuery.getAnswer ();
        
        final var task = taskContextService.findContext (id);
        if (task == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nЗадача не найдена");
        } else if (task.isDisabled ()) {
            throw new CommandProcessingException ("<b>Ответ не был записан:</b>\nЗадача приостановлена и изменить ответ в данный момент нельзя");
        }
        
        taskStatusChangeService.addChange (task, answer, message, user);
        
        task.updateMessage ();
        task.broadcastUpdateForGroup (user.getGroup (), false);
    }
    
    private void createTask (UserContext user, Message message, String query) throws CommandProcessingException {
        final var parsedQuery = InlineQueryProcessor.parseQuery (query);
        TaskCommandValidator.checkError (parsedQuery);
        TaskCommandValidator.checkTask (parsedQuery);
        TaskCommandValidator.checkType (parsedQuery);
        
        final var task = parsedQuery.getTask ();
        final var type = parsedQuery.getType ();
        
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        parsedQuery.setEvent (event);
        
        try {
            final var taskMessage = TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.text (TaskContext.prepareShortTaskMessage (user, task, type).toString ());
            });
            
            final var taskContext = taskContextService.createContext (user, taskMessage, parsedQuery);
            for (final var group : taskContext.getGroups ()) {
                taskContext.broadcastForGroup (group);
            }
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private void changeTaskActivation (UserContext user, Message message, String query) throws CommandProcessingException {
        final var parsedQuery = InlineQueryProcessor.parseQuery (query);
        TaskCommandValidator.checkError (parsedQuery);
        TaskCommandValidator.checkId (parsedQuery);
        
        final var id = parsedQuery.getId ();
        
        final var taskContext = taskContextService.findContext (id);
        if (taskContext == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nЗадача не найдена");
        }
        
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        parsedQuery.setEvent (event);
        
        taskContext.setState (user, taskContext.isEnabled () ? TaskState.DISABLED : TaskState.ENABLED);
        
        for (final var group : taskContext.getGroups ()) {
            taskContext.broadcastUpdateForGroup (group, taskContext.isEnabled ());
        }
    }
    
    private void editTask (UserContext user, Message message, String query) throws CommandProcessingException {
        final var parsedQuery = InlineQueryProcessor.parseQuery (query);
        TaskCommandValidator.checkError (parsedQuery);
        TaskCommandValidator.checkId (parsedQuery);
        TaskCommandValidator.checkTask (parsedQuery);
        TaskCommandValidator.checkType (parsedQuery);
        
        final var task = parsedQuery.getTask ();
        final var type = parsedQuery.getType ();
        final var id = parsedQuery.getId ();
        
        final var taskContext = taskContextService.findContext (id);
        if (taskContext == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nЗадача не найдена");
        }
        
        final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
        parsedQuery.setEvent (event);
        
        final var includeGroups = parsedQuery.getGroupsSplit ().a;
        taskContext.setTask (task).setType (type).setGroups (includeGroups);
        
        for (final var group : taskContext.getGroups ()) {
            taskContext.broadcastUpdateForGroup (group, true);
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
        
        final var sj = new StringJoiner ("\n");
        sj.add ("<b>Событие:</b> " + event.getName ());
        
        if (event.getTimetable () != null && event.getTimetable ().getActivities () != null) {
            Collections.sort (event.getTimetable ().getActivities (), Comparator.comparing (EventActivity::getFrom));
            
            final var now = new Date ();
            final var currentActivity = event.getTimetable ().getActivities ().stream ()
                . filter (activity -> activity.getFrom ().before (now) && activity.getTo ().after (now))
                . findFirst ()
                . orElse (null);
            sj.add ("");
            sj.add ("<b>Текущая активность:</b>\n" + (currentActivity == null ? "отсутсвует" : currentActivity.getActivity ()));
            if (currentActivity != null) {
                sj.add ("<i>До " + DateUtils.dateFormatNoSeconds.format (currentActivity.getTo ()) + "</i>");
            }
            
            final var nextActivity = event.getTimetable ().getActivities ().stream ()
                . filter (activity -> activity.getFrom ().after (now))
                . findFirst ()
                . orElse (null);
            sj.add ("");
            sj.add ("<b>Следующая активность:</b>\n" + (nextActivity == null ? "отсутсвует" : nextActivity.getActivity ()));
            if (nextActivity != null) {
                sj.add ("<i>C   " + DateUtils.dateFormatNoSeconds.format (nextActivity.getFrom ()) + "</i>");
                sj.add ("<i>До " + DateUtils.dateFormatNoSeconds.format (nextActivity.getTo ()) + "</i>");
            }
        }
        
        try {
            TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.text (sj.toString ());
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    public void subscribeToGroup (UserContext user, Message message, String groupName) throws CommandProcessingException {
        if (groupName != null && groupName.length () == 0) {
            try {
                pendings.add (new ChooseSubscriptionGroupPending (this, user, message));
                throw new PendingAddedException (ChooseSubscriptionGroupPending.class);
            } catch (TelegramApiException tapie) {
                log.error ("Failed to add " + CodeAuthenticationPending.class.getSimpleName () + " pending", tapie);
            }
        }
        
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
                    cfg.text ("Вы присединилсь к группе <b>" + group.getDisplayName () + "</b> и будете получать уведомления о задачах, которые ей адресованы");
                } else {
                    cfg.text ("Вы покинули группу и больше не будете получать уведомления");
                }
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private void writeMetainformation (String query) throws CommandProcessingException {
        final var parsedQuery = InlineQueryProcessor.parseQuery (query);
        TaskCommandValidator.checkError (parsedQuery);
        TaskCommandValidator.checkKey (parsedQuery);
        TaskCommandValidator.checkValue (parsedQuery);
        
        final var key = parsedQuery.getKey ();
        final var value = parsedQuery.getValue ();
        
        final var metaInformation = chatMetaInformationService.findOrCreateByChatAndKey (chatId, key);
        metaInformation.setValue ("null".equals (value) ? null : value);
        
        try {
            TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.text ("Записано значение метаинформации для этого чата\n<code>" + key + ": " + value + "</code>");
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
        
    }
    
}
