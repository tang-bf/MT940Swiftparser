package de.oette.swiftparser.transaction;

import org.joda.money.Money;

import java.util.List;

/**
 * Eine Transaktion innerhalb eines Transaktionsblocks
 *
 * @see SwiftMt940TransactionCollection
 */
public interface SwiftMt940Transaction
{

    /**
     * Gibt den Geschäftsvorfallcode zurück.
     * Siehe docs Ordner, geschaeftsvorfallcodes.pdf
     *
     * @return ...
     */
    public String getGVCCode();

    /**
     * Setze einen weiteren Reference String, der beim Herausschreiben der Transaktionen
     * eingefügt wird.
     *
     * @param additionalReference ...
     */
    public void setAdditionalReference( String additionalReference );

    /**
     * Einen weiterer Reference String, der beim Herausschreiben der Transaktionen
     * einfügt wird.
     *
     * @return ...
     */
    public String getAdditionalReference();

    /**
     * Bank Identification Code
     *
     * @return ...
     */
    public String getBIC();

    /**
     * International Bank Account Number
     *
     * @return ...
     */
    public String getIBAN();

    /**
     * Name des Einzahlers
     *
     * @return ...
     */
    public String getNameNonNull();

    /**
     * Die Liste der Referenz Strings
     *
     * @return ...
     */
    public List<String> getCustomerReferences();

    /**
     * Alle Reference Strings als ein String
     *
     * @return ...
     */
    public String getCustomerReferencesAsString();

    /**
     * Transaktions Betrag
     *
     * @return ...
     */
    public Money getAmount();

    int getPositionInSwiftBlock();

    String getName();

    /**
     * Customized String Method
     *
     * @param indent tab indent level
     * @return ...
     */
    String toString( int indent );
}
