package ru.itmo.nerc.vcb.bot.chat.task;

public enum TaskState {

    CREATED, ENABLED, DISABLED, CANCELLED, FINALIZED

    ;

    public static TaskState parseOrDefault(String name, TaskState def) {
        for (final var value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }

        return def;
    }


    public String getPresentation() {
        return switch (this) {
            case ENABLED, CREATED -> "активна";
            case DISABLED -> "приостановлена";
            case CANCELLED -> "отменена";
            case FINALIZED -> "завершена";
        };
    }
}
