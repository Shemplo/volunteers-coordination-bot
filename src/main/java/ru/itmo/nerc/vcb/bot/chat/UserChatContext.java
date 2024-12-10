package ru.itmo.nerc.vcb.bot.chat;

import java.util.Objects;
import java.util.StringJoiner;

import org.antlr.v4.runtime.misc.Pair;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.chat.pending.CodeAuthenticationPending;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContext;
import ru.itmo.nerc.vcb.bot.user.UserContext;
import ru.itmo.nerc.vcb.bot.user.UserRole;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;

@Slf4j
public class UserChatContext extends CommonChatContext {
    
    public UserChatContext (Chat chat) {
        super (chat);
    }
    
    @Override
    public boolean onUpdateReceived (Update update) throws TelegramApiException {
        if (update.hasMessage () && update.getMessage ().hasText ()) {
            final var message = update.getMessage ();
            final var commands = ChatUtils.fetchCommand (message);
            log.info ("Fetched commands: {}", commands);
            
            final var user = userContextService.findContextForUser (message.getFrom ());
            
            if (!commands.isEmpty ()) {
                return processCommandsOneByOne (user, message, commands);
            }
        }
        
        log.info ("Delegate update to parent chat context...");
        if (!super.onUpdateReceived (update)) {
            TelegramBot.getInstance ().setReactionOnMessage (update.getMessage (), "🙈");
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public void processCommand (UserContext user, Message message, Pair <String, String> command) throws CommandProcessingException, TelegramApiException {
        user.setPrivateChatId (chatId);
        
        switch (command.a) {
            case "/authenticate" -> checkAndCall (user, UserRole.UNKNOWN, () -> authenticateUser (user, message, command.b));
            case "/help" -> checkAndCall (user, UserRole.UNKNOWN, () -> printHelp (user));
            case "/mygroup" -> checkAndCall (user, UserRole.PARTICIPANT, () -> printUserGroupInfo (user));
            case "/start" -> checkAndCall (user, UserRole.UNKNOWN, () -> printStartMessage ());
            case "/subscribe", "/join" -> checkAndCall (user, UserRole.PARTICIPANT, () -> subscribeToGroup (user, message, command.b));
            case "/unsubscribe", "/leave" -> checkAndCall (user, UserRole.PARTICIPANT, () -> subscribeToGroup (user, message, null));
            
            default -> super.processCommand (user, message, command);
        };
    }
    
    public void authenticateUser (UserContext user, Message message, String code) throws CommandProcessingException {
        if (code.length () == 0) {
            //throw new CommandProcessingException ("Необходимо передать параметер <code>[code]</code>");
            try {
                pendings.add (new CodeAuthenticationPending (this, user));
                throw new PendingAddedException (CodeAuthenticationPending.class);
            } catch (TelegramApiException tapie) {
                log.error ("Failed to add " + CodeAuthenticationPending.class.getSimpleName () + " pending", tapie);
            }
        }
        
        final var credentials = ConfigurationHolder.getConfigurationFromSingleton ().getCredentials ();
        
        for (final var role : UserRole.values ()) {
            if (code != null && Objects.equals (code, credentials.getRoleAuthentication ().get (role))) {
                user.setRole (role);
                
                try {
                    TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                        cfg.text ("Вам назначена новая роль: <b>" + role.getDisplayName () + "</b> 🎉");
                    });
                } catch (TelegramApiException tapie) {
                    log.error ("Failed to send message", tapie);
                }
                
                return;
            }
        }
        
        throw new CommandProcessingException ("Неправильный код. Попробуйте ещё раз 😢");
        
        /*
        try {
            TelegramBot.getInstance ().setReactionOnMessage (message, "👎");
        } catch (TelegramApiException tapie) {
            log.error ("Failed to reaction message", tapie);
        }
        */
    }
    
    private void printHelp (UserContext user) throws CommandProcessingException {
        final var sj = new StringJoiner ("\n");
        sj.add ("<b>Базовые команды:</b>");
        sj.add ("<i>P </i> /help - Показать сообщение с доступными командами");
        sj.add ("<i>P </i> /authenticate <code>[code?]</code> - Сменить свою роль в зависимости от введённого кода");
        //sj.add ("/dropmessage - Удалить сообщение, которое содержит эту команду");
        
        if (user.hasPermissions (UserRole.PARTICIPANT)) {
            sj.add ("");
            sj.add ("<b>Команды для волонтёров:</b>");
            sj.add ("<i>P </i> /subscribe <code>[group name?]</code> - Сменить подписку на получение уведомлений для указанной группы (присоединиться к группе)");
            sj.add ("<i>P </i> /join <code>[group name?]</code> - То же самое, что и <code>subscribe</code>");
            sj.add ("<i>P </i> /unsubscribe - Отменить подписку на получение уведомлений, если она была (покинуть группу)");
            sj.add ("<i>P </i> /leave - То же самое, что и <code>unsubscribe</code>");
            sj.add ("<i>P </i> /mygroup - Показать информацию о группе, на уведомления которой вы подписаны");
            sj.add ("<i>PG</i> /eventinfo - Показать информацию о текущем событии");
            sj.add (
                "<i>PG</i> " + ANSWER_TASK_COMMAND + " <code>[answer parameters]</code> - Отправить ответ на задачу:\n"
                + "* <code>[id]</code> - Идентификатор задачи\n"
                + "* <code>[answer]</code> - Содержание ответа"
            );
        }
        
        if (user.hasPermissions (UserRole.MODERATOR)) {
            sj.add ("");
            sj.add ("<b>Команды для модераторов:</b>");
            sj.add (
                "<i>PG</i> /createtask <code>[task parameters]</code> - Создать задачу (или вопрос) по описанию:\n"
                + "* <code>[task]</code> - содержание задачи (вопрос)\n"
                + "* <code>[type]</code> - <code>" + TaskContext.TYPE_TASK + "</code> или <code>" + TaskContext.TYPE_QUESTION + "</code>\n"
                + "* <code>(halls)</code> - список групп, которые включить/исключить"
            );
            sj.add (
                "<i>PG</i> /edittask <code>[task parameters]</code> - Изменить задачу (или вопрос) по описанию:\n"
                + "* <code>[id]</code> - Идентификатор существующей задачи (вопроса)\n"
                + "* Остальные параметры как у <code>createtask</code>"
            );
            sj.add (
                "<i>PG</i> /activationtask <code>[task parameters]</code> - Приостановить или возобновить задачу (или вопрос) по описанию:\n"
                + "* <code>[id]</code> - Идентификатор существующей задачи (вопроса)"
            );
        }
        
        sj.add ("");
        sj.add ("<i>Буквы перед командой:</i>");
        sj.add ("<i>P</i> - Команда доступна в личных сообщениях");
        sj.add ("<i>G</i> - Команда доступна в групповом чате телеграма");
        
        sj.add ("");
        sj.add ("<i>Прочие обозначения:</i>");
        sj.add ("Символ <code>?</code> у параметра - Если параметр отсутсвует в сообщении, то он будет запрошен в интерактивном режиме");
        
        try {
            TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.text (sj.toString ());
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private void printStartMessage () {
        try {
            TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.text (
                    "Приветствую Вас!\nЭто бот для координации волонтёров на различных мероприятиях. "
                    + "Если вы хотите продолжить работу, то вам могут помочь команды /help и /authenticate"
                );
            });
        } catch (TelegramApiException tapie) {
            log.error ("Failed to send message", tapie);
        }
    }
    
    private void printUserGroupInfo (UserContext user) throws CommandProcessingException {
        if (user.getGroup () == null) {
            try {
                TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                    cfg.text ("Вы не подписаны на уведомления ни какой группы");
                });
            } catch (TelegramApiException tapie) {
                log.error ("Failed to send message", tapie);
            }
        } else {
            final var event = ConfigurationHolder.getConfigurationFromSingleton ().getEvent ();
            final var eventGroup = event.findGroupByName (user.getGroup ());
            if (eventGroup == null) {
                throw new CommandProcessingException ("Неизвестная группа");
            }
            
            final var sb = new StringBuilder ();
            sb.append ("Вы подписаны на уведомления группы <b>").append (eventGroup.getDisplayName ()).append ("</b>\n\n");
            
            final var members = userContextService.findGroupMembers (eventGroup.getShortName ()).stream ()
                . filter (member -> member.getUsername () != null)
                . toList ();
            
            sb.append ("<b>Участники группы (всего ").append (members.size ()).append ("):</b> ");
            for (int i = 0; i < members.size (); i++) {
                sb.append ("@").append (members.get (i).getUsername ());
                
                if (i < members.size () - 1) {
                    sb.append (", ");
                }
            }
            
            try {
                TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                    cfg.text (sb.toString ());
                });
            } catch (TelegramApiException tapie) {
                log.error ("Failed to send message", tapie);
            }
        }
    }
    
}
