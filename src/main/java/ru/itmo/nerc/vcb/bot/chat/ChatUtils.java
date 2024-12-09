package ru.itmo.nerc.vcb.bot.chat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.antlr.v4.runtime.misc.Pair;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.experimental.UtilityClass;
import ru.itmo.nerc.vcb.bot.TelegramBot;

@UtilityClass
public class ChatUtils {
    
    public static Queue <Pair <String, String>> fetchCommand (Message message) {
        if (!message.hasEntities ()) {
            return new LinkedList <> ();
        }
        
        final var botUsername = "@" + TelegramBot.getInstance ().getBotUsername ();
        
        final var commands = new ArrayList <MessageEntity> ();
        for (final var entity : message.getEntities ()) {
            if ("bot_command".equals (entity.getType ())) {
                if (entity.getText ().contains ("@") && entity.getText ().endsWith (botUsername)) {
                    entity.setText (entity.getText ().substring (0, entity.getLength () - botUsername.length ()));
                    commands.add (entity);
                } else {
                    commands.add (entity);
                }
            }
        }
        
        commands.add (new MessageEntity ("", message.getText ().length (), 0));
        
        final var parsed = new LinkedList <Pair <String, String>> ();
        for (int i = 0; i < commands.size () - 1; i++) {
            final var current = commands.get (i);
            final var next = commands.get (i + 1);
            
            final var argument = message.getText ().substring (
                current.getOffset () + current.getLength (),
                next.getOffset ()
            );
            
            parsed.add (new Pair <> (current.getText (), argument.trim ()));
        }
        
        return parsed;
    }
    
    public static SendMessage prepareHTML (long chatId, String content) {
        final var message = new SendMessage ();
        message.setChatId (String.valueOf (chatId));
        message.setParseMode ("HTML");
        message.setText (content);
        
        return message;
    }
    
    public static void hideInlineMarkup (long chatId, int messageId) throws TelegramApiException {
        final var edit = new EditMessageReplyMarkup ();
        edit.setMessageId (messageId);
        edit.setChatId (chatId);
        edit.setReplyMarkup (null);
        
        TelegramBot.getInstance ().execute (edit);
    }
    
}
