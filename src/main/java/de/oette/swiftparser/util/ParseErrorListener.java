package de.oette.swiftparser.util;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.BitSet;

public class ParseErrorListener implements ANTLRErrorListener
{
    private static final Logger LOGGER = LogManager.getLogger( ParseErrorListener.class );

    @Override
    public void syntaxError( @NotNull Recognizer<?, ?> recognizer, @Nullable Object offendingSymbol, int line, int charPositionInLine, @NotNull String msg, @Nullable RecognitionException e )
    {
        LOGGER.error( "Fehler im aktuellen Block in Zeile " + line + " an Position " + charPositionInLine );
        throw new ParserException();
    }

    @Override
    public void reportAmbiguity( @NotNull Parser recognizer, @NotNull DFA dfa, int startIndex, int stopIndex, boolean exact, @Nullable BitSet ambigAlts, @NotNull ATNConfigSet configs )
    {
        //tue nix
    }

    @Override
    public void reportAttemptingFullContext( @NotNull Parser recognizer, @NotNull DFA dfa, int startIndex, int stopIndex, @Nullable BitSet conflictingAlts, @NotNull ATNConfigSet configs )
    {
        //tue nix
    }

    @Override
    public void reportContextSensitivity( @NotNull Parser recognizer, @NotNull DFA dfa, int startIndex, int stopIndex, int prediction, @NotNull ATNConfigSet configs )
    {
        //tue nix
    }
}
