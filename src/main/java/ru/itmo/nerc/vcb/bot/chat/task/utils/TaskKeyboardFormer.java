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

	public static InlineKeyboardMarkup makeKeyboard (TaskContext task, boolean forSourceMessage, List <TaskStatusChange> groupsWithStatus) {
        final var markup = new InlineKeyboardMarkup (new ArrayList <> ());
        
        if (task.isEnabled ()) {
            if (task.isCheck ()) {
                final var processRow = new InlineKeyboardRow ();
                markup.getKeyboard ().add (processRow);
                
                processRow.add (makeDoneAnswer (task));
            } else if (task.isQuestion ()) {
                final var commentRow = new InlineKeyboardRow ();
                markup.getKeyboard ().add (commentRow);
                
                commentRow.add (makeFreeFormAnswer (task));
            } else if (task.isTask ()) {
                final var processRow = new InlineKeyboardRow ();
                markup.getKeyboard ().add (processRow);
                
                processRow.add (makeInProgressAnswer (task));
                processRow.add (makeDoneAnswer (task));
                
                final var commentRow = new InlineKeyboardRow ();
                markup.getKeyboard ().add (commentRow);
                
                commentRow.add (makeFreeFormAnswer (task));
            }
        }
        
        if (forSourceMessage) {
            final var editorRow = new InlineKeyboardRow ();
            markup.getKeyboard ().add (editorRow);
            
            editorRow.add (InlineKeyboardButton.builder ()
                .text ("📝 Изменить")
                .switchInlineQueryCurrentChat ("id %d; task %s; groups %s".formatted (task.getId (), task.getTask(), String.join (", ", task.getGroups())))
                .build ());
            editorRow.add (InlineKeyboardButton.builder ()
                .text (task.isEnabled () ? "⏯️ Приостановить" : "▶️ Запустить")
                .callbackData ("/activationtask id " + task.getId())
                .build ());
            
            final var pingRow = new InlineKeyboardRow ();
            markup.getKeyboard ().add (pingRow);
            
            for (final var group : task.getGroups()) {
                pingRow.add (InlineKeyboardButton.builder ()
                    .text ("!" + group)
                    .callbackData ("/pingtask id %d; groups %s".formatted (task.getId(), group))
                    .build ());
            }
            
            if (!groupsWithStatus.isEmpty ()) {
                final var userContextService = UserContextService.getInstance ();
                
                final var replyRow = new InlineKeyboardRow ();
                markup.getKeyboard ().add (replyRow);
                
                for (final var status : groupsWithStatus) {
                    final var changeAuthor = userContextService.findContextForExistingUser (status.getAuthorId ());
                    replyRow.add (InlineKeyboardButton.builder ()
                        .text (status.getGroup ())
                        .switchInlineQueryCurrentChat ("@%s, вопрос по задаче #tid%d\n".formatted(changeAuthor.getUsername (), task.getId()))
                        .build ());
                }
            }
        }
        
        return markup;
    }
	
	private static InlineKeyboardButton makeDoneAnswer (TaskContext task) {
        final var doneText = "✅ Выполнили";
        return InlineKeyboardButton.builder ()
             . text (doneText)
			 . callbackData("/answertask id %d; answer %s".formatted(task.getId(), doneText))
             . build ();
    }
    
    private static InlineKeyboardButton makeInProgressAnswer (TaskContext task) {
        final var doneText = "💃 В процессе";
        return InlineKeyboardButton.builder ()
             . text (doneText)
			 . callbackData ("/answertask id %d; answer %s".formatted (task.getId (), doneText))
             . build ();
    }
    
    private static InlineKeyboardButton makeFreeFormAnswer (TaskContext task) {
        return InlineKeyboardButton.builder ()
             . text ("💭 Ответить в свободной форме")
			 . switchInlineQueryCurrentChat ("id %d; answer\n".formatted (task.getId ()))
             . build ();
    }
	
}
