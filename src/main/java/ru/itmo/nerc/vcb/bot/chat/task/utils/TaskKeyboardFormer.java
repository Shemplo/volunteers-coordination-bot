package ru.itmo.nerc.vcb.bot.chat.task.utils;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import ru.itmo.nerc.vcb.bot.chat.task.TaskContext;
import ru.itmo.nerc.vcb.bot.chat.task.TaskStatusChange;
import ru.itmo.nerc.vcb.bot.user.UserContextService;

public class TaskKeyboardFormer {

    public static InlineKeyboardMarkup makeKeyboard(TaskContext task, boolean forSourceMessage, List<TaskStatusChange> groupsWithStatus, TaskStatusChange recentStatusChange) {
        final var markup = new InlineKeyboardMarkup(new ArrayList<>());

        if (task.isEnabled()) {
            if (task.isCheck()) {
                final var processRow = new InlineKeyboardRow();
                markup.getKeyboard().add(processRow);

                processRow.add(makeAnswer(task, TaskContext.STATE_CHECK_IN_PROCESS));
                processRow.add(makeAnswer(task, TaskContext.STATE_CHECK_OK));
                processRow.add(makeAnswer(task, TaskContext.STATE_CHECK_FAILED));
            } else if (task.isQuestion()) {
                final var commentRow = new InlineKeyboardRow();
                markup.getKeyboard().add(commentRow);

                commentRow.add(makeFreeFormAnswer(task, recentStatusChange));
            } else if (task.isTask()) {
                final var processRow = new InlineKeyboardRow();
                markup.getKeyboard().add(processRow);

                processRow.add(makeInProgressAnswer(task));
                processRow.add(makeDoneAnswer(task));

                final var commentRow = new InlineKeyboardRow();
                markup.getKeyboard().add(commentRow);

                commentRow.add(makeFreeFormAnswer(task, recentStatusChange));
            }
        }

        if (forSourceMessage) {
            final var editorRow = new InlineKeyboardRow();
            markup.getKeyboard().add(editorRow);

            if (!task.isCancelled()) {
                if (!task.isFinalized()) {
                    editorRow.add(InlineKeyboardButton.builder().text("📝 Изменить").switchInlineQueryCurrentChat("id %d; task %s; groups %s".formatted(task.getId(), task.getTask(), String.join(", ", task.getGroups()))).build());
                    editorRow.add(InlineKeyboardButton.builder().text(task.isEnabled() ? "⏯️ Приостановить" : "▶️ Запустить").callbackData("/activationtask id " + task.getId()).build());
                }
                editorRow.add(InlineKeyboardButton.builder().text(task.isFinalized() ? "️↪\uFE0F Перезапустить" : "\uD83D\uDC4C Завершить").callbackData("/finallizationtask id " + task.getId()).build());
            }
            if (!task.isFinalized()) {
                editorRow.add(InlineKeyboardButton.builder().text(task.isCancelled() ? "▶️ Восстановить" : "❌ Отменить").callbackData("/cancellationtask id " + task.getId()).build());
            }

            if (task.isEnabled()) {
                final var pingRow = new InlineKeyboardRow();
                markup.getKeyboard().add(pingRow);

                for (final var group : task.getGroups()) {
                    pingRow.add(InlineKeyboardButton.builder().text("!" + group).callbackData("/pingtask id %d; groups %s".formatted(task.getId(), group)).build());
                }
            }

            if (!groupsWithStatus.isEmpty()) {
                final var userContextService = UserContextService.getInstance();

                final var replyRow = new InlineKeyboardRow();
                markup.getKeyboard().add(replyRow);

                for (final var status : groupsWithStatus) {
                    final var changeAuthor = userContextService.findContextForExistingUser(status.getAuthorId());
                    replyRow.add(InlineKeyboardButton.builder().text(status.getGroup()).switchInlineQueryCurrentChat("@%s, вопрос по задаче #tid%d\n".formatted(changeAuthor.getUsername(), task.getId())).build());
                }
            }
        }

        return markup;
    }

    private static InlineKeyboardButton makeDoneAnswer(TaskContext task) {
        String stateDone = TaskContext.STATE_DONE;
        return makeAnswer(task, stateDone);
    }

    private static InlineKeyboardButton makeInProgressAnswer(TaskContext task) {
        return makeAnswer(task, TaskContext.STATE_IN_PROCESS);
    }

    private static InlineKeyboardButton makeAnswer(TaskContext task, String stateDone) {
        return InlineKeyboardButton.builder().text(stateDone).callbackData("/answertask id %d; answer %s".formatted(task.getId(), stateDone)).build();
    }

    private static InlineKeyboardButton makeFreeFormAnswer(TaskContext task, TaskStatusChange recentStatusChange) {
        var recentAnswer = recentStatusChange != null && recentStatusChange.getContent() != null ? recentStatusChange.getContent() : "";
        return InlineKeyboardButton.builder().text("💭 Ответить в свободной форме").switchInlineQueryCurrentChat("id %d; answer %s".formatted(task.getId(), recentAnswer)).build();
    }

}
