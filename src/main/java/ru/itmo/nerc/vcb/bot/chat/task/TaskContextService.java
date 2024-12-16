package ru.itmo.nerc.vcb.bot.chat.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.meta.api.objects.message.Message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.InlineQueryProcessor.ParsedQuery;
import ru.itmo.nerc.vcb.bot.user.UserContext;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class TaskContextService {
    
    private static volatile TaskContextService instance;
    
    public static TaskContextService getInstance () {
        if (instance == null) {
            synchronized (TaskContextService.class) {
                if (instance == null) {
                    instance = new TaskContextService ();
                }
            }
        }
        
        return instance;
    }
    
    private final Map <Long, TaskContext> id2context = new ConcurrentHashMap <> ();
    
    public TaskContext createContext (UserContext author, Message message, ParsedQuery query) {
        final var task = new TaskContext (author, message, query);
        id2context.put (task.getId (), task);
        return task;
    }
    
    public TaskContext findContext (long taskId) {
        return id2context.computeIfAbsent (taskId, __ -> {
            return DatabaseService.getInstance ().mapWrappedOrNull (connection -> {
                try (final var selectStatement = connection.prepareStatement ("SELECT * FROM task WHERE id = ?")) {
                    int i = 1;
                    selectStatement.setLong (i++, taskId);
                    
                    try (final var queryResult = selectStatement.executeQuery ()) {
                        if (queryResult.next ()) {
                            return new TaskContext (queryResult);
                        } else {
                            return null;
                        }
                    }
                }
            });
        });
    }
    
}
