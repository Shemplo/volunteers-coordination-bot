package ru.itmo.nerc.vcb.bot.chat.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.chat.InlineQueryProcessor.ParsedQuery;

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
    
    public TaskContext createContext (User author, Message message, ParsedQuery query) {
        final var task = new TaskContext (author, message, query);
        id2context.put (task.getId (), task);
        return task;
    }
    
    public TaskContext findContext (long taskId) {
        return id2context.computeIfAbsent (taskId, __ -> {
            final var context = new TaskContext (taskId);
            
            return context;
        });
    }
    
}
