package de.oette.swiftparser;

import de.oette.swiftparser.util.ParserUtils;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Prints the resulting syntax tree
 */
public class PrintParseTreeTest
{
    @Test
    public void printParseTree() throws IOException
    {
        SwiftMt940Parser parser = ParserUtils.prepareSwiftParser( "classpath:swift/swiftReaderTest.txt" );
        parser.setBuildParseTree( true );
        ParseTree parseTree = parser.root();

        String output = parseTree.toStringTree( parser );
        output = output.replaceAll( "(block)", "\n\t(block)" );
        output = output.replaceAll( "reference", "\n\t\treference" );

        System.out.println( output );

        assertThat( output ).contains( "gcode (content 1 6 6)" );
        assertThat( output ).contains( "gcode (content 8 3 3)" );
    }
}
