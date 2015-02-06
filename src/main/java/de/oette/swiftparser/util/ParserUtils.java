package de.oette.swiftparser.util;

import de.oette.swiftparser.SwiftMt940Lexer;
import de.oette.swiftparser.SwiftMt940Parser;
import de.oette.swiftparser.listener.SwiftInputListener;
import de.oette.swiftparser.transaction.SwiftMt940TransactionCollection;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

public class ParserUtils
{
    /**
     * Bereite den Parser vor, mit Hilfe eines Tokenstreams aus einer Datei
     *
     * @param resourceLocation ...
     * @return ...
     * @throws IOException
     */
    public static SwiftMt940Parser prepareSwiftParser( String resourceLocation ) throws IOException
    {
        TokenStream tokenStream = createSwiftTokenStreamFromResource( resourceLocation );
        return new SwiftMt940Parser( tokenStream );
    }

    /**
     * Bereite den Parser vor mit Hilfe eines vorhandenen Tokenstream
     *
     * @param tokenStream ...
     * @return ...
     */
    public static SwiftMt940Parser prepareSwiftParser( TokenStream tokenStream )
    {
        return new SwiftMt940Parser( tokenStream );
    }

    /**
     * Erzeuge einen Tokenstream aus einer Datei
     *
     * @param resourceLocation ...
     * @return ...
     * @throws IOException
     */
    public static TokenStream createSwiftTokenStreamFromResource( String resourceLocation ) throws IOException
    {
        File file = ResourceUtils.getFile( resourceLocation );
        String inputString = FileUtils.readFileToString( file );
        return createSwiftTokenStreamFromString( inputString );
    }

    /**
     * Erzeuge einen Tokenstream aus einem String
     *
     * @param inputString ...
     * @return ...
     */
    public static TokenStream createSwiftTokenStreamFromString( String inputString )
    {
        inputString = inputString.replaceAll( "\\?\r", "?" );
        inputString = inputString.replaceAll( "\\?\n", "?" );
        CharStream input = new ANTLRInputStream( inputString );
        SwiftMt940Lexer lexer = new SwiftMt940Lexer( input );
        return new CommonTokenStream( lexer );
    }

    /**
     * Parse den EingabeString und erzeuge die Transaktionsausgabe
     *
     * @param inputString die Rohdaten als Eingabe
     * @return Die Collection der eingelesenen Transaktionen
     */
    public static SwiftMt940TransactionCollection parseSwiftInput( String inputString )
    {
        return parseSwiftInput( inputString, new SwiftInputListener( inputString ) );
    }

    /**
     * Parse den EingabeString und erzeuge die Transaktionsausgabe
     *
     * @param inputString die Rohdaten als Eingabe
     * @return Die Collection der eingelesenen Transaktionen
     */
    public static SwiftMt940TransactionCollection parseSwiftInput( String inputString, SwiftInputListener inputListener )
    {
        TokenStream tokenStream = createSwiftTokenStreamFromString( inputString );
        SwiftMt940Parser parser = prepareSwiftParser( tokenStream );
        doParse( parser, inputListener, true );
        return inputListener.getCollection();
    }


    /**
     * Benutze den Standard Treewalker um das Parsen des Tokenstreams auszuführen.
     * Die Methoden des Listeners werden während des Parsens aufgerufen.
     * Das Endergebnis kann im Anschluss beim Listener abgefragt werden.
     *
     * @param parser        ...
     * @param inputListener ...
     * @param haltOnErrors  Falls gesetzt, wird bei jedem Fehler eine Exception geworfen. Wenn nicht, dann werden Fehler ignoriert.
     */
    public static void doParse( SwiftMt940Parser parser, ParseTreeListener inputListener, boolean haltOnErrors )
    {
        if ( haltOnErrors )
        {
            ParseErrorListener listener = new ParseErrorListener();
            parser.addErrorListener( listener );
        }
        ParseTree parseTree = parser.root();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk( inputListener, parseTree );
    }

}
