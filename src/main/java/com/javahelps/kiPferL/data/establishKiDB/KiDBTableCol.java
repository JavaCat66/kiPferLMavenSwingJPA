package com.javahelps.kiPferL.data.establishKiDB;

import java.text.DecimalFormat;

/**
 * Klasse zum Erzeugen einer DBTabellenspalte mit allen Ihren Eigenschaften, die in der DB zum Erzeugen einer KiDBTable gebraucht werden (BasisTabelle KiBase)
 *
 * @author Susanna Gruber
 * @version Alpha 2.0 2021.01.31
 * @since 1.14
 */
public class KiDBTableCol {

    private static int idCount;

    private final String id, fieldName, fieldType , kiDefault, infoText;
    private String timeStampChange;
    private int colNr;
    private final int fieldLength, decimalPlaces;
    private final boolean kiUnsigned, kiNull, primaryKey;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Parametrisierter Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiDBTableCol
    (String pFieldName, String pFieldType, int pFieldLength, int pDezimalPlaces, boolean pKiUnsigned, boolean pKiNull, boolean pPrimaryKey, String pKiDefault, String pInfoText) {
        // idCount wird pro Tabelle einmal gesetzt (KiBase MAX(id)) und dann hochgezählt
        this.id = "DB" + new DecimalFormat("00000").format(++KiDBTableCol.idCount);
        // Werte werden von ActionListener controlling.helpMe.establishKiDB.EstablishKiDBActionController übergeben
        this.fieldName = pFieldName;
        this.fieldType = pFieldType;
        this.fieldLength = pFieldLength;
        this.decimalPlaces = pDezimalPlaces;
        this.kiUnsigned = pKiUnsigned;
        this.kiNull = pKiNull;
        this.primaryKey = pPrimaryKey;
        this.kiDefault = pKiDefault;
        this.infoText = pInfoText;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Eigenschaften abfragen oder manipulieren       -->     Zugriff von anderen Klassen aus
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // zum Hochzählen der ID    -->     wird pro Tablle einmal gesetzt (Konstruktor data.helpMe.establishKiDB.KiDBTable)
    public static void setIdCount(int pIdCount) {
        KiDBTableCol.idCount = pIdCount;
    }
    public void setColNr(int pColNr) {
        this.colNr = pColNr;
    }
    public void setTimeStampChange(String pTimeStampChange) {
        this.timeStampChange = pTimeStampChange;
    }

    public String getId() {
        return this.id;
    }
    public int getColNr() {
        return this.colNr;
    }
    public String getFieldName() {
        return this.fieldName;
    }
    public String getFieldType() {
        return this.fieldType;
    }
    public int getFieldLength() {
        return this.fieldLength;
    }
    public int getDecimalPlaces() {
        return this.decimalPlaces;
    }
    public boolean isKiUnsigned() {
        return kiUnsigned;
    }
    public boolean isKiNull() {
        return this.kiNull;
    }
    public boolean isPrimaryKey() {
        return this.primaryKey;
    }
    public String getKiDefault() {
        return this.kiDefault;
    }
    public String getInfoText() {
        return this.infoText;
    }
    public String getTimeStampChange() {
        return this.timeStampChange;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // AUSGABE
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Mehrzeilige Ausgabe der Eigenschaften mit Einrückung(en)
    // Overload (=Überladene Version) von this.toString(): die Anzahl der Tabulatoren, die eingerückt werden soll, wird als KiParameter übergeben
    public String toString(int pEinRueckung) {
        StringBuilder temp = new StringBuilder();

        temp.append("\t".repeat(Math.max(0, pEinRueckung)));
        temp.append("KiDBTableCol{").append("\n");
        temp.append("\t".repeat(Math.max(0, pEinRueckung + 1)));
        temp.append("id='").append(this.id).append
                ("', colNr='").append(this.colNr).append
                ("', fieldName='").append(this.fieldName).append
                ("', fieldType='").append(this.fieldType).append
                ("', fieldLength='").append(this.fieldLength).append
                ("', decimalPlaces='").append(this.decimalPlaces).append
                ("', kiUnsigned='").append(this.kiUnsigned).append
                ("', kiNull='").append(this.kiNull).append
                ("', primaryKey='").append(this.primaryKey).append
                ("', kiDefault='").append(this.kiDefault).append
                ("', infoText='").append(this.infoText).append
                ("', timeStampChange='").append(this.timeStampChange).append("'}");

        return temp.toString();
    }
}
