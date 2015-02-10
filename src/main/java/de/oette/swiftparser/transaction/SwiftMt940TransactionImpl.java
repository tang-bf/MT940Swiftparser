package de.oette.swiftparser.transaction;

import org.apache.commons.lang.StringUtils;
import org.joda.money.Money;

import java.util.LinkedList;
import java.util.List;

/**
 * Ein Eintrag in der ELKO Datei kann mehrere Transaktionen enthalten.
 * Diese Transaktionen werden in dieser Klasse gekapselt.
 */
public class SwiftMt940TransactionImpl implements SwiftMt940Transaction
{
    private int positionInSwiftBlock;
    private String gCode = "";
    private String additionalReference;
    private String bic;
    private String iban;
    private String name;

    private List<String> customerReference = new LinkedList<>();
    private Money amount;

    /**
     * @param positionInSwiftBlock an welcher Stelle im Swift Block steht diese Transaktion? Startposition ist 0.
     */
    public SwiftMt940TransactionImpl( int positionInSwiftBlock )
    {
        this.positionInSwiftBlock = positionInSwiftBlock;
    }

    public void setGCode( String gCode )
    {
        this.gCode = StringUtils.trim( gCode );
    }

    public void setIBAN( String iban )
    {
        this.iban = iban;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Override
    public String getGVCCode()
    {
        return contentOrEmptyString( gCode );
    }

    @Override
    public void setAdditionalReference( String additionalReference )
    {
        this.additionalReference = additionalReference;
    }

    @Override
    public int getPositionInSwiftBlock()
    {
        return positionInSwiftBlock;
    }

    @Override
    public String getAdditionalReference()
    {
        return contentOrEmptyString( additionalReference );
    }

    public void setBIC( String bic )
    {
        this.bic = bic;
    }

    @Override
    public String getBIC()
    {
        return contentOrEmptyString( bic );
    }

    @Override
    public String getIBAN()
    {
        return contentOrEmptyString( iban );
    }

    @Override
    public String getNameNonNull()
    {
        return contentOrEmptyString( this.name );
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    public void addCustomerReference( String referenceString )
    {
        customerReference.add( referenceString );
    }

    @Override
    public List<String> getCustomerReferences()
    {
        return customerReference;
    }

    private static String contentOrEmptyString( String content )
    {
        if ( content != null )
        {
            return content;
        }
        else
        {
            return "";
        }
    }

    @Override
    public String getCustomerReferencesAsString()
    {
        StringBuilder resultBuilder = new StringBuilder();
        for ( String customerReferenceString : customerReference )
        {
            resultBuilder.append( customerReferenceString );
        }
        return resultBuilder.toString();
    }

    public void setAmount( Money amount )
    {
        this.amount = amount;
    }

    public Money getAmount()
    {
        return amount;
    }


    @SuppressWarnings( "all" )
    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        SwiftMt940TransactionImpl that = (SwiftMt940TransactionImpl)o;

        if ( positionInSwiftBlock != that.positionInSwiftBlock )
        {
            return false;
        }
        if ( additionalReference != null? !additionalReference.equals( that.additionalReference ) : that.additionalReference != null )
        {
            return false;
        }
        if ( amount != null? !amount.equals( that.amount ) : that.amount != null )
        {
            return false;
        }
        if ( bic != null? !bic.equals( that.bic ) : that.bic != null )
        {
            return false;
        }
        if ( customerReference != null? !customerReference.equals( that.customerReference ) : that.customerReference != null )
        {
            return false;
        }
        if ( gCode != null? !gCode.equals( that.gCode ) : that.gCode != null )
        {
            return false;
        }
        if ( iban != null? !iban.equals( that.iban ) : that.iban != null )
        {
            return false;
        }
        if ( name != null? !name.equals( that.name ) : that.name != null )
        {
            return false;
        }

        return true;
    }

    @SuppressWarnings( "all" )
    @Override
    public int hashCode()
    {
        int result = positionInSwiftBlock;
        result = 31 * result + ( gCode != null? gCode.hashCode() : 0 );
        result = 31 * result + ( additionalReference != null? additionalReference.hashCode() : 0 );
        result = 31 * result + ( bic != null? bic.hashCode() : 0 );
        result = 31 * result + ( iban != null? iban.hashCode() : 0 );
        result = 31 * result + ( name != null? name.hashCode() : 0 );
        result = 31 * result + ( customerReference != null? customerReference.hashCode() : 0 );
        result = 31 * result + ( amount != null? amount.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString()
    {
        return toString( 0 );
    }

    @Override
    public String toString( int indent )
    {
        String indentation = "";
        for ( int i = 0; i < indent; i++ )
        {
            indentation += "\t";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( indentation ).append( "SwiftMt940TransactionImpl" ).append( "\n" );
        stringBuilder.append( indentation ).append( "{" ).append( "\n" );
        stringBuilder.append( indentation ).append( "\t" ).append( "positionInSwiftBlock=" ).append( positionInSwiftBlock ).append( "\n" );
        stringBuilder.append( indentation ).append( "\t" ).append( "gCode=" ).append( gCode ).append( "\n" );
        stringBuilder.append( indentation ).append( "\t" ).append( "additionalReference=" ).append( additionalReference ).append( "\n" );
        stringBuilder.append( indentation ).append( "\t" ).append( "bic=" ).append( bic ).append( "\n" );
        stringBuilder.append( indentation ).append( "\t" ).append( "iban=" ).append( iban ).append( "\n" );
        stringBuilder.append( indentation ).append( "\t" ).append( "name=" ).append( name ).append( "\n" );
        stringBuilder.append( indentation ).append( "\t" ).append( "customerReference=" ).append( customerReference ).append( "\n" );
        stringBuilder.append( indentation ).append( "\t" ).append( "amount=" ).append( amount ).append( "\n" );
        stringBuilder.append( indentation ).append( "}" ).append( "\n" );

        return stringBuilder.toString();
    }
}
