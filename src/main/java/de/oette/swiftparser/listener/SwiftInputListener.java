package de.oette.swiftparser.listener;

import de.oette.swiftparser.SwiftMt940Parser;
import de.oette.swiftparser.transaction.SwiftMt940TransactionCollection;
import de.oette.swiftparser.transaction.SwiftMt940TransactionCollectionImpl;
import de.oette.swiftparser.transaction.SwiftMt940TransactionImpl;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.commons.lang.StringUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;

public class SwiftInputListener extends de.oette.swiftparser.SwiftMt940BaseListener
{

    private SwiftMt940TransactionCollectionImpl swiftMt4TransactionCollection;
    private SwiftMt940TransactionImpl currentTransaction;

    public SwiftInputListener()
    {
        swiftMt4TransactionCollection = new SwiftMt940TransactionCollectionImpl();
    }

    public SwiftInputListener( String rawData )
    {
        swiftMt4TransactionCollection = new SwiftMt940TransactionCollectionImpl( rawData );
    }

    @Override
    public void enterTargetAccount( @NotNull SwiftMt940Parser.TargetAccountContext ctx )
    {
        swiftMt4TransactionCollection.setTargetAccount( ctx.getText() );
    }

    @Override
    public void enterFullAccount( @NotNull SwiftMt940Parser.FullAccountContext ctx )
    {
        swiftMt4TransactionCollection.setFullAccount( ctx.getText() );
    }

    @Override
    public void enterBlock60F( @NotNull SwiftMt940Parser.Block60FContext ctx )
    {
        SwiftMt940Parser.SaldoDataContext saldoDataContext = ctx.saldoData();
        if ( saldoDataContext != null )
        {
            SwiftMt940Parser.CurrencyContext currency = saldoDataContext.currency();
            if ( currency != null )
            {
                swiftMt4TransactionCollection.setCurrency( currency.getText() );
            }
        }
    }

    @Override
    public void enterBlock61( @NotNull SwiftMt940Parser.Block61Context ctx )
    {
        SwiftMt940Parser.MoneyWithSeparatorContext moneyWithSeparatorContext = ctx.moneyWithSeparator();
        if ( moneyWithSeparatorContext != null )
        {
            SwiftMt940Parser.MoneyContext money = moneyWithSeparatorContext.money();
            currentTransaction.setAmount( parseMoney( money ) );
        }
    }

    private Money parseMoney( SwiftMt940Parser.MoneyContext moneyContext )
    {
        CurrencyUnit currencyUnit = null;
        if ( swiftMt4TransactionCollection.getCurrency() != null )
        {
            currencyUnit = CurrencyUnit.of( swiftMt4TransactionCollection.getCurrency() );
            BigDecimal amount = moneyMajorToBigDecimal( moneyContext.moneyMajor() )
                    .add( moneyMinorToBigDecimal( moneyContext.moneyMinor() ) );

            return Money.of( currencyUnit, amount );
        }
        else
        {
            return null;
        }

    }

    private BigDecimal moneyMinorToBigDecimal( SwiftMt940Parser.MoneyMinorContext minorAmountContext )
    {
        if ( minorAmountContext != null && !StringUtils.isEmpty( minorAmountContext.getText() ) )
        {
            String text = minorAmountContext.getText();
            if ( text.length() == 1 )
            {
                //Der Cent Betrag ist nur einstellig. Ergänze eine zusätzliche 0.
                text = text + "0";
            }
            return BigDecimal.valueOf( Long.parseLong( text ), 2 );
        }

        //Default
        return BigDecimal.ZERO;
    }


    private BigDecimal moneyMajorToBigDecimal( SwiftMt940Parser.MoneyMajorContext majorAmountContext )
    {
        if ( majorAmountContext != null && !StringUtils.isEmpty( majorAmountContext.getText() ) )
        {
            return BigDecimal.valueOf( Long.parseLong( majorAmountContext.getText() ) );
        }

        //Default
        return BigDecimal.ZERO;
    }

    @Override
    public void enterTransactionBlock( @NotNull SwiftMt940Parser.TransactionBlockContext ctx )
    {
        currentTransaction = new SwiftMt940TransactionImpl( ctx.position );
        swiftMt4TransactionCollection.add( currentTransaction );
    }

    @Override
    public void enterBic( @NotNull SwiftMt940Parser.BicContext ctx )
    {
        currentTransaction.setBIC( removeNewLines( getText( ctx.content() ) ) );
    }

    @Override
    public void enterIban( @NotNull SwiftMt940Parser.IbanContext ctx )
    {
        currentTransaction.setIBAN( removeNewLines( getText( ctx.content() ) ) );
    }

    @Override
    public void enterGcode( @NotNull SwiftMt940Parser.GcodeContext ctx )
    {
        currentTransaction.setGCode( removeNewLines( ctx.getText() ) );
    }

    @Override
    public void enterName1( @NotNull SwiftMt940Parser.Name1Context ctx )
    {
        currentTransaction.setName( removeNewLines( getText( ctx.referenceContent() ) ) );
    }

    @Override
    public void enterName2( @NotNull SwiftMt940Parser.Name2Context ctx )
    {
        String name1 = currentTransaction.getNameNonNull();
        String name2 = removeNewLines( removeNewLines( getText( ctx.referenceContent() ) ) );

        if ( name1 != null && name2 != null )
        {
            currentTransaction.setName( name1 + " " + name2 );
        }
        else if ( name1 == null )
        {
            currentTransaction.setName( name2 );
        }
    }

    public SwiftMt940TransactionCollection getCollection()
    {
        return swiftMt4TransactionCollection;
    }

    @Override
    public void enterCustomerReference( @NotNull SwiftMt940Parser.CustomerReferenceContext ctx )
    {
        if ( ctx.referenceContent() != null )
        {
            String referenceString = removeNewLines( getText( ctx.referenceContent() ) );
            currentTransaction.addCustomerReference( referenceString );
        }
    }

    // === Hilfsfunktionen ===

    private String getText( ParserRuleContext ctx )
    {
        if ( ctx != null )
        {
            return ctx.getText();
        }
        else
        {
            return null;
        }
    }

    private String removeNewLines( String input )
    {
        String output = StringUtils.replace( input, "\n", "" );
        output = StringUtils.replace( output, "\r", "" );
        return output;
    }
}
