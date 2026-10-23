package ru.itmo.nerc.vcb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseService {

    private static volatile DatabaseService instance;

    public static DatabaseService getInstance() {
        if (instance == null) {
            synchronized (DatabaseService.class) {
                if (instance == null) {
                    instance = new DatabaseService();

                    // This is hack only for boot time
                    instance.initialize();
                }
            }
        }

        return instance;
    }

    @SuppressWarnings("resource")
    private void initialize() {
        doWrapped(connection -> {
            // User
            connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS user (
                            id INTEGER PRIMARY KEY,
                            username VARCHAR(255) NULL,
                            role VARCHAR(16) NOT NULL,
                            subscription_group VARCHAR(32) NULL,
                            private_chat_id BIGINT NOT NULL
                        )
                    """).execute();
            connection.prepareStatement("""
                        CREATE INDEX IF NOT EXISTS group_id ON user (subscription_group)
                    """).execute();

            // Task
            connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS task (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            chat_id BIGINT NOT NULL,
                            message_id INT NOT NULL,
                            author_id BIGINT NOT NULL,
                            state VARCHAR(16) NOT NULL,
                            task TEXT NULL,
                            type VARCHAR(16) NULL,
                            groups VARCHAR(255) NULL,
                            state_editor_id BIGINT NULL,
                            state_change_date DATETIME NULL
                        )
                    """).execute();
            connection.prepareStatement("""
                        CREATE UNIQUE INDEX IF NOT EXISTS tg_id ON task (chat_id, message_id)
                    """).execute();

            // Task status change
            connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS task_status_change (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            task_id BIGINT NOT NULL,
                            content TEXT NULL,
                            subscription_group VARCHAR(255) NOT NULL,
                            chat_id BIGINT NULL,
                            message_id INT NULL,
                            change_date DATETIME NOT NULL,
                            author_id BIGINT NOT NULL
                        )
                    """).execute();
            connection.prepareStatement("""
                        CREATE INDEX IF NOT EXISTS date_id ON task_status_change (change_date)
                    """).execute();

            // Task status message
            connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS task_status_message (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            task_id BIGINT NOT NULL,
                            chat_id BIGINT NOT NULL,
                            message_id INT NOT NULL
                        )
                    """).execute();

            // Meta information
            connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS chat_meta_information (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            chat_id BIGINT NOT NULL,
                            information_key VARCHAR(255) NOT NULL,
                            value TEXT NULL
                        )
                    """).execute();
            addMessageThreads(connection);
        });
    }

    void addMessageThreads(Connection connection) throws SQLException {
        final var taskColumns = connection.prepareStatement("SELECT name FROM pragma_table_info('task') WHERE name = 'message_thread_id'").executeQuery();
        final var taskHasThread = taskColumns.next();
        if (!taskHasThread) {
            connection.prepareStatement("ALTER TABLE task ADD COLUMN message_thread_id INTEGER NOT NULL DEFAULT 0").execute();
        }

        final var statusMessageColumns = connection.prepareStatement("SELECT name FROM pragma_table_info('task_status_message') WHERE name = 'message_thread_id'").executeQuery();
        final var statusMessageHasThread = statusMessageColumns.next();
        if (!statusMessageHasThread) {
            connection.prepareStatement("ALTER TABLE task_status_message ADD COLUMN message_thread_id INTEGER NOT NULL DEFAULT 0").execute();
        }

        final var metaInformationColumns = connection.prepareStatement("SELECT name FROM pragma_table_info('chat_meta_information') WHERE name = 'message_thread_id'").executeQuery();
        final var metaInformationHasThread = metaInformationColumns.next();
        if (!metaInformationHasThread) {
            connection.prepareStatement("ALTER TABLE chat_meta_information ADD COLUMN message_thread_id INTEGER NOT NULL DEFAULT 0").execute();
        }

        connection.prepareStatement("DROP INDEX IF EXISTS unique_id").execute();
        connection.prepareStatement("DROP INDEX IF EXISTS fast_all_id").execute();
        connection.prepareStatement("""
                    CREATE UNIQUE INDEX IF NOT EXISTS task_status_message_unique_id ON task_status_message (task_id, chat_id, message_thread_id)
                """).execute();
        connection.prepareStatement("""
                    CREATE UNIQUE INDEX IF NOT EXISTS task_status_message_fast_all_id ON task_status_message (task_id, chat_id, message_thread_id, message_id)
                """).execute();
        connection.prepareStatement("""
                    CREATE UNIQUE INDEX IF NOT EXISTS chat_meta_information_unique_id ON chat_meta_information (chat_id, message_thread_id, information_key)
                """).execute();
    }

    public Connection openConnection() throws SQLException {
        final var configuration = ConfigurationHolder.getConfigurationFromSingleton().getDatabase();
        if (configuration != null) {
            final var url = String.format("jdbc:sqlite:%s", configuration.getFilename());
            if (configuration.getUsername() != null && configuration.getPassword() != null) {
                final var password = configuration.getPassword();
                final var username = configuration.getUsername();

                return DriverManager.getConnection(url, username, password);
            } else {
                return DriverManager.getConnection(url);
            }
        } else {
            return null;
        }
    }

    public void doWrapped(FailableConsumer<Connection, SQLException> consumer) {
        try (final var connection = openConnection()) {
            consumer.accept(connection);
        } catch (SQLException sqle) {
            log.error("Failed to execute SQL request", sqle);
        }
    }

    public <R> R mapWrappedOrNull(FailableFunction<Connection, R, SQLException> function) {
        try (final var connection = openConnection()) {
            return function.apply(connection);
        } catch (SQLException sqle) {
            log.error("Failed to execute SQL request", sqle);
            return null;
        }
    }

}
