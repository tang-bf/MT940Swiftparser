package de.oette.swiftparser.transaction;


import org.apache.commons.lang.StringUtils;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Ein Eintrag in der ELKO Datei kann mehrere Transaktionen enthalten.
 * Diese Transaktionen werden in dieser Klasse gekapselt.
 */
public class SwiftMt940TransactionCollectionImpl extends AbstractCollection<SwiftMt940Transaction>
        implements SwiftMt940TransactionCollection
{
    private List<SwiftMt940Transaction> list = new ArrayList<>();
    private String currency;
    private String rawDataInput;
    private boolean hasProcessingError = false;
    private boolean hasParserInputError = false;
    private String account;
    private String fullAccount;

    public SwiftMt940TransactionCollectionImpl( String rawDataInput )
    {
        this.rawDataInput = rawDataInput;
    }

    @Override
    public Iterator<SwiftMt940Transaction> iterator()
    {
        return list.iterator();
    }

    @Override
    public int size()
    {
        return list.size();
    }

    @Override
    public boolean add( SwiftMt940Transaction swiftMt4Transaction )
    {
        return list.add( swiftMt4Transaction );
    }

    public void setCurrency( String currency )
    {
        this.currency = currency;
    }

    public String getCurrency()
    {
        return currency;
    }

    @Override
    @SuppressWarnings( "unused" )
    public List<SwiftMt940Transaction> getSwiftTransactionList()
    {
        return list;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "SwiftMt940TransactionCollectionImpl\n" );
        stringBuilder.append( "{\n" );
        for ( SwiftMt940Transaction transaction : list )
        {
            stringBuilder.append( transaction.toString( 2 ) ).append( "\n" );
        }
        stringBuilder.append( "}\n" );
        return stringBuilder.toString();
    }

    @Override
    public String getRawDataInputString()
    {
        return rawDataInput;
    }

    @Override
    public boolean hasError()
    {
        return hasParserInputError() || hasParserInputError();
    }

    @Override
    public boolean hasProcessingError()
    {
        return hasProcessingError;
    }

    @Override
    public boolean hasParserInputError()
    {
        return hasParserInputError;
    }

    @Override
    public void setProcessingError()
    {
        this.hasProcessingError = true;
    }

    @Override
    public void setParserInputError()
    {
        this.hasParserInputError = true;
    }

    public void setTargetAccount( String account )
    {
        this.account = account;
    }

    /**
     * Gibt das Konto zurück im Format 'BLZ/Kontonummer' ohne Länderkürzel
     *
     * @return ...
     */
    @Override
    public String getAccountWithoutCountry()
    {
        return account;
    }

    public void setFullAccount( String text )
    {
        this.fullAccount = text;
    }

    /**
     * Gibt das Konto zurück im Format 'BLZ/Kontonummer' inkl. Länderkürzel
     *
     * @return ...
     */
    @Override
    public String getAccountWithCountry()
    {
        return fullAccount;
    }


    /**
     * Den zweiten Teil der Kontonummer
     *
     * @param includeCountry Soll das Länderkürzel ebenfalls enthalten sein?
     * @return ...
     */
    @Override
    public String getAccountSuffix( boolean includeCountry )
    {
        String tempAccount = account;
        if ( includeCountry )
        {
            tempAccount = fullAccount;
        }

        if ( !StringUtils.isEmpty( tempAccount ) && tempAccount.contains( "/" ) )
        {
            String[] split = StringUtils.split( tempAccount, "/" );
            return split[1];
        }
        else
        {
            return tempAccount;
        }
    }
}
