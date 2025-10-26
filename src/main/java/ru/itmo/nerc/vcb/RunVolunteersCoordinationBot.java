package ru.itmo.nerc.vcb;

import java.sql.SQLException;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.bot.TelegramBot;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
public class RunVolunteersCoordinationBot {
    
    public static void main (String ... args) {
        try {
            DatabaseService.getInstance ();
        } catch (RuntimeException re) {
            if (re.getCause () instanceof SQLException sqle) {
                log.error ("Failed to initialize database", sqle);
                System.exit (1);
            }
        }
        
        log.info ("Starting VC bot...");
        final var configuration = ConfigurationHolder.getConfigurationFromSingleton ();
        final var botToken = configuration.getCredentials ().getToken ();
        
        try {
            @SuppressWarnings ("resource")
            final var application = new TelegramBotsLongPollingApplication ();
            application.registerBot (botToken, TelegramBot.getInstance ());
            
            log.info ("Starting VC bot... DONE");
        } catch (TelegramApiException tapie) {
            log.error ("Failed to start VC bot", tapie);
        } catch (Exception e) {
            log.error ("Failed to stop VC bot", e);
        }
    }
    
}
