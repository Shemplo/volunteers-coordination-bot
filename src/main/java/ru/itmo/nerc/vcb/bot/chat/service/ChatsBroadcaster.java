package ru.itmo.nerc.vcb.bot.chat.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import org.apache.commons.lang3.function.FailableRunnable;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.bot.chat.task.TaskContext;
import ru.itmo.nerc.vcb.bot.chat.task.service.TaskStatusMessageService;
import ru.itmo.nerc.vcb.utils.thread.RunnableActivity;
import ru.itmo.nerc.vcb.utils.thread.SupervisedRunnable;
import ru.itmo.nerc.vcb.utils.thread.ThreadsPool;

@Slf4j
public class ChatsBroadcaster implements SupervisedRunnable {
    
    private static volatile ChatsBroadcaster instance;
    
    public static ChatsBroadcaster getInstance () {
        if (instance == null) {
            synchronized (ChatsBroadcaster.class) {
                if (instance == null) {
                    instance = new ChatsBroadcaster ();
                }
            }
        }
        
        return instance;
    }
    
    private final Queue <FailableRunnable <TelegramApiException>> tasksQueue = new ConcurrentLinkedQueue <> ();
    private final TaskStatusMessageService taskStatusMessageService = TaskStatusMessageService.getInstance ();
    
    @SuppressWarnings ("unused")
    private final ThreadsPool threadsPool;
    
    private ChatsBroadcaster () {
        // TODO Shutdown it gracefully (but actually it's a daemon)
        threadsPool = new ThreadsPool (1, 32, "chats-broadcaster-", () -> this);
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
    
    public void sendMessage (long chatId, String text, InlineKeyboardMarkup keyboard, Consumer <Message> onMessageSent) {
    	tasksQueue.add (() -> {
            final var message = TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.replyMarkup (keyboard);
                cfg.text (text);
            });
            
            onMessageSent.accept(message);
        });
    }
    
    @Deprecated
    public void sendBroadcastMessage (TaskContext task, long chatId, String text, InlineKeyboardMarkup keyboard) {
        tasksQueue.add (() -> {
            final var message = TelegramBot.getInstance ().sendMessage (chatId, cfg -> {
                cfg.replyMarkup (keyboard);
                cfg.text (text);
            });
            
            taskStatusMessageService.addMessage (task, message);
        });
    }
    
    @Deprecated
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
                final var messageThatEdited = "‼️ Задача #tid%d была изменена. Проверьте, что ответ группы, в которой вы состоите, актуален и соответствует изменению".formatted (task.getId ());
                if (statusMessage == null) {
                    bot.sendMessage (chatId, cfg -> {
                        cfg.text (messageThatEdited);
                    });
                } else {
                    bot.sendReplyMessage (chatId, statusMessage.getMessageId (), cfg -> {
                        cfg.text (messageThatEdited);
                    });
                }
            }
        });
    }
    
}
