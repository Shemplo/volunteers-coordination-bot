package ru.itmo.nerc.vcb.bot.chat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Objects;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.nerc.vcb.db.DatabaseService;

@Slf4j
@Getter
@ToString
public class ChatMetaInformation {

    public static final String KEY_DEFAULT_GROUPS = "default groups";

    private final long id;

    private final long chatId;
    private final Integer messageThreadId;
    private final String key;
    private volatile String value;

    public ChatMetaInformation(long chatId, Integer messageThreadId, String key) {
        this.chatId = chatId;
        this.messageThreadId = messageThreadId;
        this.key = key;
        id = insertAndGetId(chatId, key);
    }

    public ChatMetaInformation(ResultSet queryResult) throws SQLException {
        id = queryResult.getLong("id");
        chatId = queryResult.getLong("chat_id");
        var threadId = queryResult.getInt("message_thread_id");
        messageThreadId = threadId == 0 ? null : threadId;
        key = queryResult.getString("information_key");
        value = queryResult.getString("value");
    }

    private Long insertAndGetId(long chatId, String key) {
        return DatabaseService.getInstance().mapWrappedOrNull(connection -> {
            final var base64e = Base64.getEncoder();

            try (final var insertStatement = connection.prepareStatement(
                    "INSERT INTO chat_meta_information (chat_id, information_key, message_thread_id) VALUES (?, ?, ?)"
            )) {
                int i = 1;
                insertStatement.setLong(i++, chatId);
                insertStatement.setString(i++, base64e.encodeToString(key.getBytes()));
                insertStatement.setInt(i++, messageThreadId == null ? 0 : messageThreadId);
                insertStatement.execute();
            }

            try (final var selectStatement = connection.prepareStatement(
                    "SELECT id FROM chat_meta_information WHERE chat_id = ? AND information_key = ? AND message_thread_id = ?"
            )) {
                int i = 1;
                selectStatement.setLong(i++, chatId);
                selectStatement.setString(i++, base64e.encodeToString(key.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                selectStatement.setInt(i++, messageThreadId == null ? 0 : messageThreadId);

                try (final var fetchResult = selectStatement.executeQuery()) {
                    if (fetchResult.next()) {
                        return fetchResult.getLong("id");
                    } else {
                        log.error("Chat meta information entry not found!");
                    }
                }
            }

            // Impossible
            return null;
        });
    }

    public void persist() {
        DatabaseService.getInstance().doWrapped(connection -> {
            try (final var insertStatement = connection.prepareStatement(
                    "UPDATE chat_meta_information SET value = ? WHERE id = ?"
            )) {
                int i = 1;
                insertStatement.setString(i++, value);
                insertStatement.setLong(i++, id);
                insertStatement.execute();
            }
        });
    }

    public ChatMetaInformation setValue(String value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;

            persist();
        }

        return this;
    }

}
