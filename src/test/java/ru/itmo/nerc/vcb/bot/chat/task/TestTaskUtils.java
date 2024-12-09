package ru.itmo.nerc.vcb.bot.chat.task;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ru.itmo.nerc.vcb.cfg.BotEventConfiguration;
import ru.itmo.nerc.vcb.cfg.BotEventConfiguration.EventGroup;

public class TestTaskUtils {
    
    @ParameterizedTest
    @CsvSource ({
        "'', '1, 2, 3, TC', ''",
        "'1', '1', '2, 3, TC'",
        "'1, 2', '1, 2', '3, TC'",
        "'1, -2', '1', '2, 3, TC'",
        "'1, Hall 2', '1, 2', '3, TC'",
        "'1, 2, 3, TC', '1, 2, 3, TC', ''",
        "'1, 2, 1', '1, 2', '3, TC'",
        "'-1', '2, 3, TC', '1'",
        "'-1, -2', '3, TC', '1, 2'",
        "'-TC', '1, 2, 3', 'TC'",
        "'-1, -Hall 2', '3, TC', '1, 2'",
        "'-1, -2, -1', '3, TC', '1, 2'",
    })
    public void testDecideGroups (String input, String include, String exclude) {
        final var event = new BotEventConfiguration ();
        event.setGroups (List.of (
            new EventGroup ("1", "Hall 1"),
            new EventGroup ("2", "Hall 2"),
            new EventGroup ("3", "Hall 3"),
            new EventGroup (null, "TC")
        ));
        
        final var decidedGroups = TaskUtils.decideGroups (event, parseArray (input));
        assertThat (decidedGroups.a).isEqualTo (parseArray (include));
        assertThat (decidedGroups.b).isEqualTo (parseArray (exclude));
    }
    
    private List <String> parseArray (String input) {
        return Arrays.stream (input.split ("\\s*,\\s*")).filter (v -> v.length () > 0).toList ();
    }
    
}
