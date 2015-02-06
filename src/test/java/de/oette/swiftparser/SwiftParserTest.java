package de.oette.swiftparser;

import de.oette.swiftparser.listener.SwiftInputListener;
import de.oette.swiftparser.transaction.SwiftMt940Transaction;
import de.oette.swiftparser.transaction.SwiftMt940TransactionCollectionImpl;
import de.oette.swiftparser.util.ParserUtils;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.fest.assertions.Assertions.assertThat;

public class SwiftParserTest
{
    @Test
    public void testInputListener() throws IOException
    {
        SwiftInputListener classUnderTest = prepareParser( "classpath:swift/swiftReaderTest.txt" );

        SwiftMt940TransactionCollectionImpl collection = classUnderTest.getCollection();
        SwiftMt940Transaction[] transactions = collection.toArray( new SwiftMt940Transaction[collection.size()] );

        assertThat( collection.getAccountWithoutCountry() ).isEqualTo( "10040000/0190826800" );
        assertThat( collection.getCurrency() ).isEqualTo( "EUR" );
        assertThat( transactions[0].getBIC() ).isEqualTo( "10040000" );
        assertThat( transactions[0].getAmount() ).isEqualTo( Money.of( CurrencyUnit.EUR, BigDecimal.valueOf( 115614, 2 ) ) );


        assertThat( transactions[1].getBIC() ).isEqualTo( "SOLADEST600" );
        assertThat( transactions[1].getAmount() ).isEqualTo( Money.of( CurrencyUnit.EUR, BigDecimal.valueOf( 7144, 2 ) ) );
        assertThat( transactions[1].getIBAN() ).isEqualTo( "DE50600501010006916129" );
        assertThat( transactions[1].getNameNonNull() ).isEqualTo( "Angela Euchner" );
        assertThat( transactions[1].getCustomerReferences().get( 0 ) ).isEqualTo( "EREF+NOTPROVIDED" );
        assertThat( transactions[1].getCustomerReferences().get( 1 ) ).isEqualTo( "KREF+NSCT140218000238000000" );
        assertThat( transactions[1].getCustomerReferences().get( 2 ) ).isEqualTo( "0000000000001" );
        assertThat( transactions[1].getCustomerReferences().get( 3 ) ).isEqualTo( "SVWZ+DG0516057Y6" );


        assertThat( transactions[2].getBIC() ).isEqualTo( "PBNKDEFF" );
        assertThat( transactions[2].getAmount() ).isEqualTo( Money.of( CurrencyUnit.EUR, BigDecimal.valueOf( 13990, 2 ) ) );
        assertThat( transactions[2].getIBAN() ).isEqualTo( "DE31760100850562820856" );
        assertThat( transactions[2].getNameNonNull() ).isEqualTo( "Stefan Landherr" );
        assertThat( transactions[2].getCustomerReferences().get( 0 ) ).isEqualTo( "EREF+ZV01001563717110000000" );
        assertThat( transactions[2].getCustomerReferences().get( 1 ) ).isEqualTo( "02" );
        assertThat( transactions[2].getCustomerReferences().get( 2 ) ).isEqualTo( "KREF+NSCT140218000238000000" );
        assertThat( transactions[2].getCustomerReferences().get( 3 ) ).isEqualTo( "0000000000001" );
        assertThat( transactions[2].getCustomerReferences().get( 4 ) ).isEqualTo( "SVWZ+DG0517625N1" );


        assertThat( transactions[3].getBIC() ).isEqualTo( "PBNKDEFF" );
        assertThat( transactions[3].getAmount() ).isEqualTo( Money.of( CurrencyUnit.EUR, BigDecimal.valueOf( 24990, 2 ) ) );
        assertThat( transactions[3].getIBAN() ).isEqualTo( "DE61500100600096600607" );
        assertThat( transactions[3].getNameNonNull() ).isEqualTo( "CHRISTIANE EICKEL" );
        assertThat( transactions[3].getCustomerReferences().get( 0 ) ).isEqualTo( "EREF+ZV01001563650953000000" );
        assertThat( transactions[3].getCustomerReferences().get( 1 ) ).isEqualTo( "02" );
        assertThat( transactions[3].getCustomerReferences().get( 2 ) ).isEqualTo( "KREF+NSCT140218000238000000" );
        assertThat( transactions[3].getCustomerReferences().get( 3 ) ).isEqualTo( "0000000000001" );
        assertThat( transactions[3].getCustomerReferences().get( 4 ) ).isEqualTo( "SVWZ+DG0519819D9" );
    }

    private SwiftInputListener prepareParser( String resourceLocation ) throws IOException
    {
        SwiftInputListener classUnderTest = new SwiftInputListener( "" );
        SwiftMt940Parser parser = ParserUtils.prepareSwiftParser( resourceLocation );

        //Parse alle Elemente und führe die Listener Klasse aus
        ParseTree parseTree = parser.root();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk( classUnderTest, parseTree );
        return classUnderTest;
    }

}
