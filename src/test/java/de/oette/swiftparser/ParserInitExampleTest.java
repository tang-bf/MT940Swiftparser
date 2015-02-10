package de.oette.swiftparser;

import de.oette.swiftparser.listener.SwiftInputListener;
import de.oette.swiftparser.transaction.SwiftMt940TransactionCollection;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

/**
 * The content of this test ist part of the documentation
 * <p/>
 * It shows you how to initiate the parser without the ParserUtils.class
 */
public class ParserInitExampleTest
{
    @Test
    public void initParser()
    {
        /**
         * Initialize the input stream and the parser
         */
        CharStream input = new ANTLRInputStream( ":20:00ZQYDSR63Y4OGWA\n:61:1402190218D1156,14NCMZ100207602400//99100/074\n:86:833?00CASH CONCENTRATING BUCHUNG?1099100?20ACMS W/100 2076024 00\n-" );
        SwiftMt940Lexer lexer = new SwiftMt940Lexer( input );
        CommonTokenStream tokenStream = new CommonTokenStream( lexer );
        SwiftMt940Parser parser = new SwiftMt940Parser( tokenStream );

        /**
         * You can use the SwiftInputListener or create your own to parse the document
         * You have to implement the interface de.oette.swiftparser.SwiftMt940BaseListener
         */
        SwiftInputListener listener = new SwiftInputListener();

        //Initiate a parse tree and walk along the tree. The ParseTreeWalker calls the listener methods
        ParseTree parseTree = parser.root();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk( listener, parseTree );

        /**
         * If you use the SwiftInputListener then you can fetch the transaction collection
         */
        SwiftMt940TransactionCollection collection = listener.getCollection();
        System.out.println( collection );

    }
}
