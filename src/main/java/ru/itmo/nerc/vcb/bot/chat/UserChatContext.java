package ru.itmo.nerc.vcb.bot.chat;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.Pair;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.chat.ex.CommandProcessingException;
import ru.itmo.nerc.vcb.bot.chat.ex.PendingAddedException;
import ru.itmo.nerc.vcb.bot.chat.pending.CodeAuthenticationPending;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContext;
import ru.itmo.nerc.vcb.bot.chat.utils.ChatUtils;
import ru.itmo.nerc.vcb.bot.user.UserContext;
import ru.itmo.nerc.vcb.bot.user.UserRole;
import ru.itmo.nerc.vcb.cfg.BotEventConfiguration.EventGroup;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;

@Slf4j
public class UserChatContext extends CommonChatContext {

    public UserChatContext(Chat chat) {
        super(chat);
    }

    @Override
    public boolean onUpdateReceived(Update update) throws TelegramApiException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final var message = update.getMessage();
            final var commands = ChatUtils.fetchCommand(message);
            log.info("Fetched commands: {}", commands);

            final var user = userContextService.findContextForUser(message.getFrom());

            if (!commands.isEmpty()) {
                return processCommandsOneByOne(user, message, commands);
            }
        }

        log.info("Delegate update to parent chat context...");
        if (!super.onUpdateReceived(update)) {
            TelegramBot.getInstance().setReactionOnMessage(update.getMessage(), "🙈");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void processCommand(UserContext user, Message message, Pair<String, String> command) throws CommandProcessingException, TelegramApiException {
        user.setPrivateChatId(chatId);

        switch (command.a) {
            case "/authenticate" ->
                checkAndCall(user, UserRole.UNKNOWN, () -> authenticateUser(user, message, command.b));
            case "/help" -> checkAndCall(user, UserRole.UNKNOWN, () -> printHelp(user));
            case "/mygroup" -> checkAndCall(user, UserRole.PARTICIPANT, () -> printUserGroupInfo(user));
            case "/start" -> checkAndCall(user, UserRole.UNKNOWN, this::printStartMessage);
            case "/subscribe", "/join" ->
                checkAndCall(user, UserRole.PARTICIPANT, () -> subscribeToGroup(user, message, command.b));
            case "/unsubscribe", "/leave" ->
                checkAndCall(user, UserRole.PARTICIPANT, () -> subscribeToGroup(user, message, null));
            case "/groupsinfo" -> checkAndCall(user, UserRole.MODERATOR, this::printGroupsInfo);
            case "/groupsclear" -> checkAndCall(user, UserRole.MODERATOR, this::clearGroups);

            default -> super.processCommand(user, message, command);
        }
    }

    public void authenticateUser(UserContext user, Message message, String code) throws CommandProcessingException {
        if (code.length() == 0) {
            try {
                pendings.add(new CodeAuthenticationPending(this, user));
                throw new PendingAddedException(CodeAuthenticationPending.class);
            } catch (TelegramApiException tapie) {
                log.error("Failed to add " + CodeAuthenticationPending.class.getSimpleName() + " pending", tapie);
            }
        }

        final var credentials = ConfigurationHolder.getConfigurationFromSingleton().getCredentials();

        for (final var role : UserRole.values()) {
            if (code != null && Objects.equals(code, credentials.getRoleAuthentication().get(role))) {
                user.setRole(role);

                try {
                    TelegramBot.getInstance().sendMessage(chatId, cfg -> {
                        cfg.text("Вам назначена новая роль: <b>" + role.getDisplayName() + "</b> 🎉");
                    });
                } catch (TelegramApiException tapie) {
                    log.error("Failed to send message", tapie);
                }

                return;
            }
        }

        throw new CommandProcessingException("Неправильный код. Попробуйте ещё раз 😢");
    }

    private void printHelp(UserContext user) {
        final var sj = new StringJoiner("\n");
        sj.add("<b>Базовые команды:</b>");
        sj.add("");
        sj.add("/help — <i>[P]</i> Показать сообщение с доступными командами");
        sj.add("");
        sj.add("/authenticate <code>[code?]</code> — <i>[P]</i> Сменить свою роль в зависимости от введённого кода");

        if (user.hasPermissions(UserRole.PARTICIPANT)) {
            sj.add("");
            sj.add("<b>Команды для волонтёров:</b>");
            sj.add("");
            sj.add("/subscribe (/join) <code>[group name?]</code> — <i>[P]</i> Сменить подписку на получение уведомлений для указанной группы (присоединиться к группе)");
            sj.add("");
            sj.add("/unsubscribe (/leave) — <i>[P]</i> Отменить подписку на получение уведомлений, если она была (покинуть группу)");
            sj.add("");
            sj.add("/mygroup — <i>[P]</i> Показать информацию о группе, на уведомления которой вы подписаны");
            sj.add("");
            sj.add("/eventinfo — <i>[PG]</i> Показать информацию о текущем событии");
            sj.add("");
            sj.add(ANSWER_TASK_COMMAND + " <code>[...parameters]</code> — <i>[PG]</i> Отправить ответ на задачу:");
            sj.add("    * <code>[id]</code>: Идентификатор задачи");
            sj.add("    * <code>[answer]</code>: Содержание ответа");
        }

        if (user.hasPermissions(UserRole.MODERATOR)) {
            sj.add("");
            sj.add("<b>Команды для модераторов:</b>");
            sj.add("");
            sj.add("/groupsinfo — <i>[P]</i> Просмотреть составы всех групп");
            sj.add("");
            sj.add("/groupsclear — <i>[P]</i> Очистить составы всех групп");
            sj.add("");
            sj.add("/createtask <code>[...parameters]</code> - <i>[PG]</i> Создать задачу по описанию:");
            sj.add("    * <code>[task]</code>: содержание задачи (вопрос)");
            sj.add("    * <code>[type]</code>: <code>" + TaskContext.TYPE_TASK + "</code>, <code>" + TaskContext.TYPE_QUESTION + "</code> или <code>" + TaskContext.TYPE_CHECK + "</code>");
            sj.add("    * <code>[...groups]</code>: список групп, которые включить/исключить");
            sj.add("");
            sj.add("/edittask <code>[...parameters]</code> — <i>[PG]</i> Изменить задачу по описанию:");
            sj.add("    * <code>[id]</code>: Идентификатор существующей задачи");
            sj.add("    * Остальные параметры как у <code>/createtask</code>");
            sj.add("");
            sj.add("/pingtask <code>[...parameters]</code> — <i>[PG]</i> Напомнить группам о задаче (обновить статус или комментарий):");
            sj.add("    * <code>[id]</code>: Идентификатор существующей задачи");
            sj.add("    * <code>[...groups]</code>: список групп, которым необходимо напомнить");
            sj.add("");
            sj.add("/activationtask <code>[...parameters]</code> — <i>[PG]</i> Приостановить или возобновить задачу по описанию:");
            sj.add("    * <code>[id]</code>: Идентификатор существующей задачи");
            sj.add("");
            sj.add("/writemeta <code>[...parameters]</code> — <i>[PG]</i> Записать метаинформацию для текущего чата или топика:");
            sj.add("    * <code>[key]</code>: Ключ метаинформации, например, <code>default groups</code> для привязки к холлу");
            sj.add("    * <code>[value]</code>: Значение метаинформации, например, список холлов");
        }

        sj.add("");
        sj.add("<b>Прочие обозначения:</b>");
        sj.add("");
        sj.add("<i>Буквы перед описаанием команды:</i>");
        sj.add("<i>P</i> — Команда доступна в личных сообщениях");
        sj.add("<i>G</i> — Команда доступна в групповом чате телеграма");

        sj.add("");
        sj.add("<i>Символы в описании команд:</i>");
        sj.add("Символ <code>?</code> у параметра — Если параметр отсутсвует в сообщении, то он будет запрошен в интерактивном режиме");
        sj.add("Символ <code>...</code> у параметра — Вместо этого параметра ожидается список параметров (которые перечислены ниже), разделённых <code>;</code>");

        try {
            TelegramBot.getInstance().sendMessage(chatId, cfg -> {
                cfg.text(sj.toString());
            });
        } catch (TelegramApiException tapie) {
            log.error("Failed to send message", tapie);
        }
    }

    private void printStartMessage() {
        try {
            TelegramBot.getInstance().sendMessage(chatId, cfg -> {
                cfg.text(
                        "Приветствую Вас!\nЭто бот для координации волонтёров на различных мероприятиях. " + "Если вы хотите продолжить работу, то вам могут помочь команды /help и /authenticate"
                );
            });
        } catch (TelegramApiException tapie) {
            log.error("Failed to send message", tapie);
        }
    }

    private void printUserGroupInfo(UserContext user) throws CommandProcessingException {
        if (user.getGroup() == null) {
            try {
                TelegramBot.getInstance().sendMessage(chatId, cfg -> {
                    cfg.text("Вы не состоите ни в какой группе");
                });
            } catch (TelegramApiException tapie) {
                log.error("Failed to send message", tapie);
            }
        } else {
            final var event = ConfigurationHolder.getConfigurationFromSingleton().getEvent();
            final var eventGroup = event.findGroupByName(user.getGroup());
            if (eventGroup == null) {
                throw new CommandProcessingException("Неизвестная группа");
            }

            final var sb = new StringBuilder();
            sb.append("Вы состоите в группе <b>").append(eventGroup.getDisplayName()).append("</b> и будете получать все уведомления о задачах, назначенных этой группе\n\n");

            final var members = userContextService.findGroupMembers(eventGroup.getShortName()).stream().filter(member -> member.getUsername() != null).toList();

            sb.append("<b>Участники группы (всего ").append(members.size()).append("):</b> ");
            for (int i = 0; i < members.size(); i++) {
                sb.append("@").append(members.get(i).getUsername());

                if (i < members.size() - 1) {
                    sb.append(", ");
                }
            }

            try {
                TelegramBot.getInstance().sendMessage(chatId, cfg -> {
                    cfg.text(sb.toString());
                });
            } catch (TelegramApiException tapie) {
                log.error("Failed to send message", tapie);
            }
        }
    }

    private void printGroupsInfo() {
        final var event = ConfigurationHolder.getConfigurationFromSingleton().getEvent();
        final var groups = event.getGroups().stream().map(EventGroup::getShortName).toList();
        final var group2members = userContextService.findGroupsMembers(groups).stream().filter(member -> member.getUsername() != null).collect(Collectors.groupingBy(UserContext::getGroup));

        final var sb = new StringBuilder();
        for (final var group : event.getGroups()) {
            final var members = group2members.getOrDefault(group.getShortName(), List.<UserContext>of());

            sb.append("<b><u>").append(group.getDisplayName()).append("</u> (всего ").append(members.size()).append("):</b> ");
            for (int i = 0; i < members.size(); i++) {
                sb.append("@").append(members.get(i).getUsername());

                if (i < members.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n\n");
        }

        try {
            TelegramBot.getInstance().sendMessage(chatId, cfg -> {
                cfg.text(sb.toString());
            });
        } catch (TelegramApiException tapie) {
            log.error("Failed to send message", tapie);
        }
    }

    private void clearGroups() {
        final var event = ConfigurationHolder.getConfigurationFromSingleton().getEvent();
        final var groups = event.getGroups().stream().map(EventGroup::getShortName).toList();
        for (final var member : userContextService.findGroupsMembers(groups)) {
            if (member.getUsername() != null) {
                member.setGroup(null);

                try {
                    TelegramBot.getInstance().sendMessage(member.getPrivateChatId(), cfg -> {
                        cfg.text("Составы групп были распущены. Вы больше не будете получать уведомления");
                    });
                } catch (TelegramApiException tapie) {
                    log.error("Failed to send message", tapie);
                }
            }
        }
    }

}
