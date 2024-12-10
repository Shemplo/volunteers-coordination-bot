package ru.itmo.nerc.vcb.bot.chat.task;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.function.FailableRunnable;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.utils.thread.RunnableActivity;
import ru.itmo.nerc.vcb.utils.thread.SupervisedRunnable;
import ru.itmo.nerc.vcb.utils.thread.ThreadsPool;

@Slf4j
public class TaskUpdatesBroadcast implements SupervisedRunnable {
    
    private static volatile TaskUpdatesBroadcast instance;
    
    public static TaskUpdatesBroadcast getInstance () {
        if (instance == null) {
            synchronized (TaskUpdatesBroadcast.class) {
                if (instance == null) {
                    instance = new TaskUpdatesBroadcast ();
                }
            }
        }
        
        return instance;
    }
    
    private final Queue <FailableRunnable <TelegramApiException>> tasksQueue = new ConcurrentLinkedQueue <> ();
    private final TaskStatusMessageService taskStatusMessageService = TaskStatusMessageService.getInstance ();
    
    private TaskUpdatesBroadcast () {
        new ThreadsPool (1, 32, "tu-broadcast-", () -> this);
    }
    
    @Override
    public void run () {
        while (!Thread.interrupted ()) {
            try {
                final var task = tasksQueue.poll ();
                if (task != null) {
                    task.run ();
                } else {
                    Thread.sleep (100L);
                }
            } catch (InterruptedException ie) {
                // Its' okay
            } catch (TelegramApiException tapie) {
                log.error ("Failed to send broadcast message or update", tapie);
            }
        }
    }
    
    @Override
    public RunnableActivity getActivityAndReset () {
        return tasksQueue.isEmpty () ? RunnableActivity.IDLE: RunnableActivity.RUNNING;
    }
    
    public void sendBroadcastMessage (TaskContext task, long chatId, String text, InlineKeyboardMarkup keyboard) {
        tasksQueue.add (() -> {
            final var message = TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.replyMarkup (keyboard);
                cfg.text (text);
            });
            
            taskStatusMessageService.addMessage (task, message);
        });
    }
    
    public void sendBroadcastUpdate (TaskContext task, boolean taskEdited, long chatId, String text, InlineKeyboardMarkup keyboard) {
        tasksQueue.add (() -> {
            final var statusMessage = TaskStatusMessageService.getInstance ().findByTaskAndChat (task.getId (), chatId);
            final var bot = TelegramBot.getInstance ();
            
            if (statusMessage != null) {
                bot.sendMessageEdit (chatId, statusMessage.getMessageId (), cfg -> {
                    cfg.replyMarkup (keyboard);
                    cfg.text (text);
                });
            }
            
            if (taskEdited) {
                bot.sendMessage (chatId, cfg -> {
                    cfg.text ("‼️ Задача #tid" + task.getId () + " была изменена. Проверьте, что ответ группы, в которой вы состоите, актуален и соответствует изменению");
                });
            }
        });
    }
    
}
