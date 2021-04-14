package com.javahelps.kiPferL.data.establishKiDB;

import com.javahelps.kiPferL.data.KiDataBase;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasse zum Erzeugen und Abspeichern (BasisTabelle KiBase) einer DB-Tabelle
 *
 * @author Susanna Gruber
 * @version Alpha 2.0 2021.01.31
 * @since 1.14
 */
public class KiDBTable {

    private final String bezeichnung, praefix, dateOfCreation, modul, program, method, groupID, user, status;
    private final int numberOfCols;
    private final List<KiDBTableCol> cols;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Parametrisierter Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiDBTable(String pBezeichnung, String pPraefix, int pNumberOfRows) {
        // höchste id aus Tabelle KiBase abfragen und static idCount in KiDBTableCol setzen (darf pro Tabelle nur einmal geschehen, dann wird hochgezählt)
        KiDataBase kiDataBase = new KiDataBase();
        try {
            ResultSet rs = kiDataBase.doSelect("SELECT MAX(id) FROM KiBase;");
            while(rs.next()) {
                KiDBTableCol.setIdCount(Integer.parseInt(rs.getString("MAX(id)").substring(2)));
            }
        } catch(Exception e) {
            System.out.println("FEHLER 1: data.helpMe.establishKiDB.KiDBTableCol.constructor: " + e);
        } finally {
            try {
                kiDataBase.closeConnection();
            } catch(Exception e) {
                System.out.println("FEHLER 2: data.helpMe.establishKiDB.KiDBTableCol.constructor: " + e);
            }
        }
        // Werte werden von ActionListener controlling.helpMe.establishKiDB.EstablishKiDBActionController übergeben
        this.bezeichnung = pBezeichnung;
        this.praefix = pPraefix;
        this.numberOfCols = pNumberOfRows;
        // in dateOfCreation wird das aktuelle Datum eingetragen
        this.dateOfCreation = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        // DokumentationsFelder
        this.modul = "data.helpMe.establishKiDB";
        this.program = "KiDBTable";
        this.method = "doInsertIntoKiBase";
        this.groupID = "GR000001";
        this.user = "Susanna";
        this.status = "A";
        // TabellenSpalten
        this.cols = new ArrayList<>();
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Eigenschaften abfragen oder manipulieren       -->     Zugriff von anderen Klassen aus
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public int getNumberOfCols() {
        return numberOfCols;
    }
    public String getBezeichnung() {
        return bezeichnung;
    }
    public String getPraefix() {
        return praefix;
    }
    public List<KiDBTableCol> getCols() {
        return cols;
    }
    public String getDateOfCreation() {
        return dateOfCreation;
    }
    public String getModul() {
        return modul;
    }
    public String getProgram() {
        return program;
    }
    public String getMethod() {
        return method;
    }
    public String getGroupID() {
        return groupID;
    }
    public String getUser() {
        return user;
    }
    public String getStatus() {
        return status;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // KiTableCols werden hinzugefügt
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String addTableCol(KiDBTableCol pKiDBTableCol) {
        pKiDBTableCol.setColNr(this.cols.size() + 1);
        pKiDBTableCol.setTimeStampChange(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        this.cols.add(pKiDBTableCol);
        return "TabellenSpalte wurde zur Tabelle " + this.bezeichnung + " hinzugefügt.";
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // IN DIE DB-TABELLE KiBase speichern
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String doInsertIntoKiBase() {
        String temp;
        KiDataBase kiDataBase = new KiDataBase();
        try {
            temp = kiDataBase.doUpdateKiBase(this);
        } catch(Exception e) {
            temp = "FEHLER beim Eintrag der Tabelle " + this.bezeichnung + " in die Tabelle KiBase: " + e.toString();
        } finally {
            try {
                kiDataBase.closeConnection();
            } catch (Exception e) {
                temp = "FEHLER beim Schließen der Connection: " + e.toString();
            }
        }
        return temp;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // AUSGABE
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Mehrzeilige Ausgabe der Eigenschaften mit Einrückung(en)
    // Overload (=Überladene Version) von this.toString(): die Anzahl der Tabulatoren, die eingerückt werden soll, wird als KiParameter übergeben
    public String toString(int pEinRueckung) {
        StringBuilder temp = new StringBuilder();

        // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // MethodReference (inner Class) zur Ausgabe von Allergenen und Diättauglichkeiten
        class MyMethodRef {
            private int x;
            public MyMethodRef() {
                this.x = 0;
            }
            public void myMethod(KiDBTableCol kiTableCol) {
                temp.append(kiTableCol.toString(pEinRueckung + 2));
                if (this.x < KiDBTable.this.cols.size() - 1) {
                    temp.append(", \n");
                }
                ++this.x;
            }
        }
        // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        temp.append("\t".repeat(Math.max(0, pEinRueckung)));
        temp.append("KiDBTable{").append
                ("bezeichnung='").append(this.bezeichnung).append
                ("', praefix='").append(this.praefix).append
                ("', numberOfCols=").append(this.numberOfCols).append(", \n");
        temp.append("\t".repeat(Math.max(0, pEinRueckung + 1)));
        temp.append("COLS[");
        if(!this.cols.isEmpty()) {
            temp.append("\t".repeat(Math.max(0, pEinRueckung + 1)));
            temp.append("\n");
            // MethodReference (inner Class MyMethodRef mit Methode myMethod(KiDBTableCol))
            this.cols.forEach(new MyMethodRef() :: myMethod);
        }
        temp.append("],\n");
        temp.append("\t".repeat(Math.max(0, pEinRueckung + 1)));
        temp.append("dateOfCreation='").append(this.dateOfCreation).append
                ("', modul='").append(this.modul).append
                ("', program='").append(this.program).append
                ("', method='").append(this.method).append
                ("', groupID='").append(this.groupID).append
                ("', user='").append(this.user).append
                ("', status='").append(this.status).append("'}");

        return temp.toString();
    }
}
