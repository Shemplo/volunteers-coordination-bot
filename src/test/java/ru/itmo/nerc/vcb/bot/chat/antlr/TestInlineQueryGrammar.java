package ru.itmo.nerc.vcb.bot.chat.antlr;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestInlineQueryGrammar {
    
    @ParameterizedTest
    @CsvSource ({
        "'', '{}'",
        "'aaa; bbb; ccc', '{}'",
        "'aaa; bbb; ccc;', '{}'",
        "'task', '{\"task\":\"\"}'",
        "'task Скажите, как у вас дела?', '{\"task\":\"Скажите, как у вас дела?\"}'",
        "'task Скажите, как у вас дела?;', '{\"task\":\"Скажите, как у вас дела?\"}'",
        "'task Скажите, как у вас дела?; halls 1, 2, 3', '{\"task\":\"Скажите, как у вас дела?\",\"groups\":[\"1\",\"2\",\"3\"]}'",
        "'halls 1, 2, 3; task Скажите, как у вас дела?', '{\"groups\":[\"1\",\"2\",\"3\"],\"task\":\"Скажите, как у вас дела?\"}'",
        "'aaa; task Скажите, как у вас дела?', '{\"task\":\"Скажите, как у вас дела?\"}'",
        "'aaa; task Скажите, как у вас дела?;', '{\"task\":\"Скажите, как у вас дела?\"}'",
        "'task Скажите, как у вас дела?; aaa', '{\"task\":\"Скажите, как у вас дела?\"}'",
        "'task Скажите, как у вас дела?; aaa;', '{\"task\":\"Скажите, как у вас дела?\"}'",
        "'task Скажите, как у вас дела?; aaa; halls 1, 2, 3', '{\"task\":\"Скажите, как у вас дела?\",\"groups\":[\"1\",\"2\",\"3\"]}'",
        "'task Скажите, как у вас дела?; aaa; halls 1, 2, 3;', '{\"task\":\"Скажите, как у вас дела?\",\"groups\":[\"1\",\"2\",\"3\"]}'",
        "'task Скажите, как у вас дела?; halls 1, 2, 3; aaa;', '{\"task\":\"Скажите, как у вас дела?\",\"groups\":[\"1\",\"2\",\"3\"]}'",
        "'aaa; bbb; task Скажите, как у вас дела?; halls 1, 2, 3', '{\"task\":\"Скажите, как у вас дела?\",\"groups\":[\"1\",\"2\",\"3\"]}'",
        "'aaa; task Скажите, как у вас дела?; bbb; halls 1, 2, 3', '{\"task\":\"Скажите, как у вас дела?\",\"groups\":[\"1\",\"2\",\"3\"]}'",
        "'aaa; task Скажите, как у вас дела?; halls 1, 2, 3; bbb', '{\"task\":\"Скажите, как у вас дела?\",\"groups\":[\"1\",\"2\",\"3\"]}'",
        "'aaa bbb; task Скажите, как у вас дела?; halls 1, 2, 3', '{\"task\":\"Скажите, как у вас дела?\",\"groups\":[\"1\",\"2\",\"3\"]}'",
        "'task What is your name?', '{\"task\":\"What is your name?\"}'",
        "'task What is your name?; halls -1', '{\"task\":\"What is your name?\",\"groups\":[\"-1\"]}'",
        "'task How many halls do you have?', '{\"task\":\"How many halls do you have?\"}'",
        "'task How many halls do you have?; groups -1', '{\"task\":\"How many halls do you have?\",\"groups\":[\"-1\"]}'",
        "'task How many halls do you have?; groups -1; type question', '{\"task\":\"How many halls do you have?\",\"groups\":[\"-1\"],\"type\":\"question\"}'",
    })
    public void testParserAndConverter (String input, String expected) {
        final var parser = GrammarUtils.parse (InlineQueryGrammarParser.class, input);
        //parser.setTrace (true);
        
        final var json = parser.inlineQuery ().json;
        assertThat (json).isEqualTo (expected);
    }
    
}
