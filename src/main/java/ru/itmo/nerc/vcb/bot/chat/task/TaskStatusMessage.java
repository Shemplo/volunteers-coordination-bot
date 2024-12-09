package ru.itmo.nerc.vcb.bot.chat.task;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.telegram.telegrambots.meta.api.objects.Message;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@Getter
public class TaskStatusMessage {
    
    private long id;
    
    private long taskId;
    private long chatId;
    private int messageId;
    
    public TaskStatusMessage (TaskContext task, Message message) {
        chatId = message.getChatId ();
        messageId = message.getMessageId ();
        taskId = task.getId ();
        
        id = insertAndGetId (taskId, chatId, messageId);
    }
    
    public TaskStatusMessage (ResultSet queryResult) throws SQLException {
        id = queryResult.getLong ("id");
        taskId = queryResult.getLong ("task_id");
        chatId = queryResult.getLong ("chat_id");
        messageId = queryResult.getInt ("message_id");
    }
    
    private Long insertAndGetId (long taskId, long chatId, int messageId) {
        return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
            try (final var insertStatement = connection.prepareStatement (
                "INSERT INTO task_status_message (task_id, chat_id, message_id) VALUES (?, ?, ?)"
            )) {
                int i = 1;
                insertStatement.setLong (i++, taskId);
                insertStatement.setLong (i++, chatId);
                insertStatement.setInt (i++, messageId);
                insertStatement.execute ();
            }
            
            try (final var selectStatement = connection.prepareStatement (
                "SELECT id FROM task_status_message WHERE task_id = ? AND chat_id = ? AND message_id = ?"
            )) {
                int i = 1;
                selectStatement.setLong (i++, taskId);
                selectStatement.setLong (i++, chatId);
                selectStatement.setInt (i++, messageId);
                
                try (final var fetchResult = selectStatement.executeQuery ()) {
                    if (fetchResult.next ()) {
                        return fetchResult.getLong ("id");
                    } else {
                        log.error ("Task status message entry not found!");
                    }
                }
            }
            
            // Impossible
            return null;
        });
    }
    
}
