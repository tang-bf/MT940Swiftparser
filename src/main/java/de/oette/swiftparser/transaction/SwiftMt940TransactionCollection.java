package de.oette.swiftparser.transaction;

import java.util.Collection;
import java.util.List;

/**
 * Das Swift Dateiformat enthält mehrere Transaktionen pro Eintrag. Deshalb gibt es diese Hilfsklasse
 * Enthält ca. 1 - 5 Swifttransaktionen
 */
public interface SwiftMt940TransactionCollection extends Collection<SwiftMt940Transaction>
{
    /**
     * Die Rohdaten aus dem Parser
     *
     * @return ...
     */
    public String getRawDataInputString();

    /**
     * Gibt an, ob ein Lese- oder Verarbeitungsfehler aufgetreten ist. Falls ja,
     * dann sollten nur die Rohdaten in die Ausgabe geschrieben werden.
     *
     * @return ...
     */
    public boolean hasError();

    /**
     * Gibt an, ob ein Verarbeitungsfehler aufgetreten ist. Falls ja,
     * dann sollten nur die Rohdaten in die Ausgabe geschrieben werden.
     *
     * @return ...
     */
    public boolean hasProcessingError();

    /**
     * Gibt an, ob ein Lesefehler aufgetreten ist. Falls ja,
     * dann sollten nur die Rohdaten in die Ausgabe geschrieben werden.
     *
     * @return ...
     */
    public boolean hasParserInputError();

    /**
     * Markiere die Collection Verarbeitung als fehlgeschlagen, weil beim Prozessieren ein Fehler aufgetreten ist
     */
    public void setProcessingError();

    /**
     * Markiere die Collection Verarbeitung als fehlgeschlagen, weil beim Einlesen der Daten ein Fehler aufgetreten ist
     */
    public void setParserInputError();

    @SuppressWarnings( "unused" )
    List<SwiftMt940Transaction> getSwiftTransactionList();

    String getAccountWithoutCountry();

    String getAccountWithCountry();

    String getAccountSuffix( boolean includeCountry );
}
