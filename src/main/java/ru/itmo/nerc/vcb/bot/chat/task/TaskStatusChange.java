package ru.itmo.nerc.vcb.bot.chat.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.user.UserContext;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@Getter
@ToString
public class TaskStatusChange {
    
    private final long id;
    
    private final long taskId;
    private volatile String content;
    private volatile String group;
    
    private volatile Long chatId;
    private volatile Integer messageId;
    private final Date changeDate;
    private final long authorId;
    
    @Builder
    public TaskStatusChange (long taskId, String content, Long chatId, Integer messageId, Date changeDate, UserContext user) {
        this.authorId = user.getUserId ();
        this.group = user.getGroup ();
        this.changeDate = changeDate;
        this.chatId = chatId;
        this.content = content;
        this.messageId = messageId;
        this.taskId = taskId;
        
        id = insertAndGetId (taskId, group, changeDate, authorId);
    }
    
    public TaskStatusChange (ResultSet queryResult) throws SQLException {
        authorId = queryResult.getLong ("author_id");
        changeDate = queryResult.getDate ("change_date");
        
        content = queryResult.getString ("content");
        group = queryResult.getString ("subscription_group");
        id = queryResult.getLong ("id");
        taskId = queryResult.getLong ("task_id");
        
        chatId = queryResult.getLong ("chat_id");
        if (queryResult.wasNull ()) {
            chatId = null;
        }
        
        messageId = queryResult.getInt ("message_id");
        if (queryResult.wasNull ()) {
            messageId = null;
        }
    }
    
    private Long insertAndGetId (long taskId, String group, Date changeDate, long authorId) {
        return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
            try (final var insertStatement = connection.prepareStatement (
                "INSERT INTO task_status_change (task_id, subscription_group, change_date, author_id) VALUES (?, ?, ?, ?)"
            )) {
                int i = 1;
                insertStatement.setLong (i++, taskId);
                insertStatement.setString (i++, group);
                insertStatement.setDate (i++, new java.sql.Date (changeDate.getTime ()));
                insertStatement.setLong (i++, authorId);
                insertStatement.execute ();
            }
            
            try (final var selectStatement = connection.prepareStatement (
                "SELECT id FROM task_status_change WHERE task_id = ? AND change_date = ? AND author_id = ?"
            )) {
                int i = 1;
                selectStatement.setLong (i++, taskId);
                selectStatement.setDate (i++, new java.sql.Date (changeDate.getTime ()));
                selectStatement.setLong (i++, authorId);
                
                try (final var fetchResult = selectStatement.executeQuery ()) {
                    if (fetchResult.next ()) {
                        return fetchResult.getLong ("id");
                    } else {
                        log.error ("Task status change entry not found!");
                    }
                }
            }
            
            // Impossible
            return null;
        });
    }
    
    public void persist () {
        DatabaseService.getInstance ().doWrapped (connection -> {
            try (final var insertStatement = connection.prepareStatement (
                "UPDATE task_status_change SET content = ?, subscription_group = ?, chat_id = ?, message_id = ? WHERE id = ?"
            )) {
                int i = 1;
                insertStatement.setString (i++, content);
                insertStatement.setString (i++, group);
                
                if (chatId != null) {
                    insertStatement.setLong (i++, chatId);
                } else {
                    insertStatement.setNull (i++, Types.BIGINT);
                }
                
                if (messageId != null) {
                    insertStatement.setInt (i++, messageId);
                } else {
                    insertStatement.setNull (i++, Types.INTEGER);
                }
                
                insertStatement.setLong (i++, id);
                insertStatement.execute ();
            }
        });
    }
    
}
