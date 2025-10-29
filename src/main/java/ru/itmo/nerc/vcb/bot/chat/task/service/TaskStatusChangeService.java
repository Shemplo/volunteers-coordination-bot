package ru.itmo.nerc.vcb.bot.chat.task.service;

import java.util.Date;

import org.telegram.telegrambots.meta.api.objects.message.Message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContext;
import ru.itmo.nerc.vcb.bot.chat.task.TaskStatusChange;
import ru.itmo.nerc.vcb.bot.user.UserContext;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class TaskStatusChangeService {
    
    private static volatile TaskStatusChangeService instance;
    
    public static TaskStatusChangeService getInstance () {
        if (instance == null) {
            synchronized (TaskStatusChangeService.class) {
                if (instance == null) {
                    instance = new TaskStatusChangeService ();
                }
            }
        }
        
        return instance;
    }
    
    public TaskStatusChange addChange (TaskContext task, String content, Message message, UserContext user) {
        final var change = TaskStatusChange.builder ()
            . changeDate (new Date ())
            . chatId (message != null ? message.getChatId () : null)
            . content (content)
            . messageId (message != null ? message.getMessageId () : null)
            . taskId (task.getId ())
            . user (user)
            . build ();
        change.persist ();
        
        return change;
    }
    
    public TaskStatusChange addNoticedChange (TaskContext task, String content, UserContext user) {
        return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
            try (
                final var selectStatement = connection.prepareStatement (
                    "SELECT * FROM task_status_change WHERE task_id = ? AND subscription_group = ?"
                );
            ) {
                int i = 1;
                selectStatement.setLong (i++, task.getId ());
                selectStatement.setString (i++, user.getGroup ());
                
                try (final var queryResult = selectStatement.executeQuery ()) {
                    if (queryResult.next ()) {
                        return null;
                    } else {
                        final var change = TaskStatusChange.builder ()
                            . changeDate (new Date ())
                            . content (content)
                            . taskId (task.getId ())
                            . user (user)
                            . build ();
                        change.persist ();
                        
                        return change;
                    }
                }
            }
        });
    }
    
    public TaskStatusChange findMostRecent (long taskId, String group) {
        return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
            try (
                final var selectStatement = connection.prepareStatement (
                    "SELECT * FROM task_status_change WHERE task_id = ? AND subscription_group = ? "
                    + "ORDER BY change_date DESC LIMIT 1"
                );
            ) {
                int i = 1;
                selectStatement.setLong (i++, taskId);
                selectStatement.setString (i++, group);
                
                try (final var queryResult = selectStatement.executeQuery ()) {
                    if (queryResult.next ()) {
                        return new TaskStatusChange (queryResult);
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
}
