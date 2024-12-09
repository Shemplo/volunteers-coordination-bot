package ru.itmo.nerc.vcb.bot.chat.antlr;

import java.lang.reflect.InvocationTargetException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrammarUtils {
    
    public static <L extends Lexer, P extends Parser> P parse (Class <L> lexerType, Class <P> parserType, String input) {
        try {
            final var lexerConstructor = lexerType.getConstructor (CharStream.class);
            final var lexer = lexerConstructor.newInstance (CharStreams.fromString (input));
            
            final var parserConstructor = parserType.getConstructor (TokenStream.class);
            return parserConstructor.newInstance (new CommonTokenStream (lexer));
        } catch (
            NoSuchMethodException
            | SecurityException
            | InstantiationException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException e
        ) {
            log.error ("Failed to create parser of type " + parserType.getName () + " (message: " + e.getMessage () + ")", e);
            return null;
        }
    }
    
    @SuppressWarnings ("unchecked")
    public static <P extends Parser> P parse (Class <P> parserType, String input) {
        final var lexerClassName = parserType.getName ().replaceAll ("Parser($|\\$)", "Lexer");
        final var classLoader = GrammarUtils.class.getClassLoader ();
        try {
            final var lexerType = (Class <Lexer>) classLoader.loadClass (lexerClassName);
            return parse (lexerType, parserType, input);
        } catch (ClassNotFoundException cnfe) {
            log.error ("Lexer type " + lexerClassName + " is not defined (message: " + cnfe.getMessage () + ")", cnfe);
            return null;
        }
    }
    
}
