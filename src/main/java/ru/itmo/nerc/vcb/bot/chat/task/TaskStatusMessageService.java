package ru.itmo.nerc.vcb.bot.chat.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.meta.api.objects.message.Message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class TaskStatusMessageService {
    
    private static volatile TaskStatusMessageService instance;
    
    public static TaskStatusMessageService getInstance () {
        if (instance == null) {
            synchronized (TaskStatusMessageService.class) {
                if (instance == null) {
                    instance = new TaskStatusMessageService ();
                }
            }
        }
        
        return instance;
    }
    
    private final Map <StatusMessageKey, TaskStatusMessage> key2message = new ConcurrentHashMap <> ();
    
    private record StatusMessageKey (long taskId, long chatId) {
        
    }
    
    public TaskStatusMessage addMessage (TaskContext task, Message message) {
        final var statusMessage = new TaskStatusMessage (task, message);
        key2message.put (new StatusMessageKey (statusMessage.getTaskId (), statusMessage.getChatId ()), statusMessage);
        return statusMessage;
    }
    
    public TaskStatusMessage findByTaskAndChat (long taskId, long chatId) {
        return key2message.computeIfAbsent (new StatusMessageKey (taskId, chatId), __ -> {
            return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
                try (final var selectStatement = connection.prepareStatement ("SELECT * FROM task_status_message WHERE task_id = ? AND chat_id = ?")) {
                    int i = 1;
                    selectStatement.setLong (i++, taskId);
                    selectStatement.setLong (i++, chatId);
                    
                    try (final var queryResult = selectStatement.executeQuery ()) {
                        if (queryResult.next ()) {
                            return new TaskStatusMessage (queryResult);
                        } else {
                            return null;
                        }
                    }
                }
            });
        });
    }
    
}
