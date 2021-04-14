package com.javahelps.kiPferL.data.establishKiDB;

import com.javahelps.kiPferL.data.KiDataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Klasse, die nach Daten aus der BasisTabelle KiBase, in der alle DB-Tabellen eingetragen sind, Tabellen erzeugt, löscht und updatet
 *
 * @author Susanna Gruber
 * @version Alpha 2.0 2021.02.06
 * @since 1.14
 */
public class KiDBUpdate {

    private final StringBuilder temp = new StringBuilder();                                                                   // zur Rückgabe von Fehler- und anderen Texten

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Erzeugen einer Tabelle laut den Angaben aus KiBase
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Zuerst wird eine Tabelle mit dem Namen "tablename_tmp" erzeugt.
    // Dann werden die Daten aus "tablename" eingefuegt.
    // Dann wird die Tabelle "tablename" als bckup gesichert.
    // Dann wird die Tabelle "tablename_tmp" in "tablename umbenannt.
    private void doCreateTable(String kiTable) {
        if (!kiTable.isEmpty() && !kiTable.equalsIgnoreCase("KiBase")) {
            KiDataBase kiDataBase = new KiDataBase();
            try {
                StringBuilder sqlCreate1 = null;
                StringBuilder sqlCreate = new StringBuilder();
                sqlCreate.append("SELECT * FROM KiBase WHERE bezeichnung = '").append(kiTable).append("' ORDER BY ColNr");
                // Die Strukturdefinition aus der KiBase lesen
                ResultSet rs = kiDataBase.doSelect(sqlCreate.toString());
                // Zusammensetzen des CREATE Statement-Strings
                while (rs.next()) {
                    String fldName = rs.getString("FieldName");
                    String fldType = rs.getString("FieldType");
                    int fldLength = rs.getInt("FieldLength");
                    int decimalPlaces = rs.getInt("DecimalPlaces");
                    String kiUnsigned = rs.getString("KiUnsigned");
                    String kiNull = rs.getString("KiNull");
                    String primaryKey = rs.getString("PrimaryKey");
                    String kiDefault = rs.getString("KiDefault");
                    if (sqlCreate1 != null) {
                        sqlCreate1.append(",\n");
                    } else {
                        sqlCreate1 = new StringBuilder();
                    }
                    sqlCreate1.append(fldName).append(" ").append(fldType);
                    if(fldType.equalsIgnoreCase("Varchar") || fldType.equalsIgnoreCase("Char") || fldType.equalsIgnoreCase("Int")) {
                        sqlCreate1.append("(").append(fldLength).append(")");
                    } else if(fldType.equalsIgnoreCase("Decimal")) {
                        sqlCreate1.append("(").append(fldLength).append(",").append(decimalPlaces).append(")");
                    }
                    if(fldType.equalsIgnoreCase("Decimal") || fldType.equalsIgnoreCase("Int")) {
                        if(kiUnsigned.equalsIgnoreCase("J")) {
                            sqlCreate1.append(" UNSIGNED");
                        }
                    }
                    if(kiNull.equalsIgnoreCase("N")) {
                        sqlCreate1.append(" NOT NULL");
                    }
                    if(primaryKey.equalsIgnoreCase("J")) {
                        sqlCreate1.append(" PRIMARY KEY");
                    }
                    if(!kiDefault.isEmpty()) {
                        if(fldType.equalsIgnoreCase("Boolean")) {
                            sqlCreate1.append(" DEFAULT ");
                            sqlCreate1.append(kiDefault);
                        } else {
                            if(!kiDefault.equalsIgnoreCase("false") && !kiDefault.equalsIgnoreCase("true")) {
                                sqlCreate1.append(" DEFAULT ");
                                sqlCreate1.append("'");
                                sqlCreate1.append(kiDefault);
                                sqlCreate1.append("'");
                            }
                        }
                    }
                }
                rs.close();
                // Das vollstaendige Create Statement zusammensetzen.
                sqlCreate.setLength(0);
                if (sqlCreate1 != null) {
                    sqlCreate.append("CREATE TABLE ").append(kiTable).append("_tmp(\n").append(sqlCreate1.toString()).append(") ENGINE=InnoDB;");
                }
                // Bevor die Tabelle mit der _tmp Erweiterung erstellt wird, sicherstellen
                // dass es keine Tabelle mit diesem Namen gibt. -> falls es sie gibt, droppen.
                kiDataBase.doDropTable(kiTable + "_tmp");
                // Die Tabelle mit der Erweiterung _tmp erstellen
                kiDataBase.doUpdate(sqlCreate.toString());

                /*// *****************************************************************************************************************************************************************************
                // indezies setzen
                ResultSet rs1 = kiDataBase.doSelect("SELECT QOTITL, QOT150 FROM fld79 WHERE QOBOBJ='" + kiTable + "'");
                while(rs1.next()) {
                    kiDataBase.doUpdate("CREATE INDEX " + rs1.getString("QOTITL") + " ON " + kiTable + "_tmp(" + rs1.getString("QOT150") + ")");
                }
                // ******************************************************************************************************************************************************************************/

                // Die Originaltabelle in die neu erstellte Tabelle kopieren.
                StringBuilder aktDat = new StringBuilder();
                if (this.doCopyTableContent(kiTable, kiTable + "_tmp" )) {
                    // aktuelles Datum erzeugen
                    GregorianCalendar heute = new GregorianCalendar();
                    aktDat.append(heute.get(Calendar.YEAR));
                    int m = heute.get(Calendar.MONTH) + 1;
                    if(m < 10) {
                        aktDat.append("0").append(m);
                    } else {
                        aktDat.append(m);
                    }
                    int t = heute.get(Calendar.DATE);
                    if(t < 10) {
                        aktDat.append("0").append(t);
                    } else {
                        aktDat.append(t);
                    }
                    int s = heute.get(Calendar.HOUR_OF_DAY);
                    if(s < 10) {
                        aktDat.append("0").append(s);
                    } else {
                        aktDat.append(s);
                    }
                    int min = heute.get(Calendar.MINUTE);
                    if(min < 10) {
                        aktDat.append("0").append(min);
                    } else {
                        aktDat.append(min);
                    }
                    sqlCreate.setLength(0);
                    sqlCreate.append("RENAME TABLE ").append(kiTable).append(" TO ").append(kiTable).append("_bckup").append(aktDat.toString());
                    // Originaltabelle umbenennen und als Backup aufheben.
                    kiDataBase.doUpdate(sqlCreate.toString());
                }
                // Neu angelegte Tabelle auf den Originalnamen umbenennen.
                sqlCreate.setLength(0);
                sqlCreate.append("RENAME TABLE ").append(kiTable).append("_tmp TO ").append(kiTable.toLowerCase());
                kiDataBase.doUpdate(sqlCreate.toString());
            } catch (Exception e) {
                this.temp.append("FEHLER 1 data.helpMe.establishKiDB.doCreateTable: ").append(e);
            } finally {
                try {
                    kiDataBase.closeConnection();
                } catch (Exception e) {
                    this.temp.append("FEHLER 2 data.helpMe.establishKiDB.doCreateTable: ").append(e);
                }
            }
        }
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Kopieren der Dateninhalte vom einer Tabelle in eine andere
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Es werden nur die Dateninhalte von gleichnamigen Feldern kopiert
    private boolean doCopyTableContent(String from_table, String to_table) {
        boolean copy = false;
        KiDataBase kiDataBase = new KiDataBase();
        try {
            Connection con = kiDataBase.establishConnection();
            DatabaseMetaData md = con.getMetaData();
            HashSet<String> hs = new HashSet<>();
            /*
             * Feldliste fuer das INSERT Statement zusammensetzen
             * Es duerfen nur die Felder genommen werden, die in beiden Tabellen existieren.
             * Methode: 	Spaltennamen der from_tabelle in einer Hashmap speichern
             *			Spaltennamen der to_tabelle in einer ResultSet-Schleife lesen und schauen
             *			ob sie in der Hashmap sind -> wenn ja, dann gibt es die Spalte in beiden
             *			Tabellen und diese Spalte wird in die FeldListe fuer das INSERT aufgenommen.
             */
            // Die Info ueber die Spalten einer Tabelle werden in einem ResultSet aus
            // dem MetaDaten-Objekt geliefert
            ResultSet rs = md.getColumns("","",from_table,"%");
            while (rs.next()) {
                // Spaltennamen der from_tabelle in einer HashMap speichern.
                hs.add(rs.getString("COLUMN_NAME"));
            }
            rs.close();
            // die Spaltennamen der to_tabelle holen
            rs = md.getColumns("","",to_table,"%");
            StringBuilder fldList = null;
            while (rs.next()) {
                // wenn die Spalte in der HashMap zu finden ist, dann in die fldListe aufnehmen
                if (hs.contains(rs.getString("COLUMN_NAME"))) {
                    if (fldList != null) {
                        fldList.append(',');
                    } else {
                        fldList = new StringBuilder();
                    }
                    fldList.append(rs.getString("COLUMN_NAME"));
                }
            }
            rs.close();
            // Das INSERT-Statement zusammensetzen
            StringBuilder sqlCreate = new StringBuilder();
            assert fldList != null;
            sqlCreate.append("INSERT INTO ").append(to_table).append(" (").append(fldList).append(") ").append
                                ("SELECT ").append(fldList.toString()).append(" FROM ").append(from_table);
            // Wenn eine fldList zum Kopieren vorhanden ist, dann das INSERT ausfuehren
            if(fldList.length() > 0) {
                try {
                    kiDataBase.doUpdate(sqlCreate.toString());
                }
                catch (Exception e) {
                    this.temp.append("FEHLER 1 data.helpMe.establishKiDB.doCopyTableContent: ").append(e);
                    return false;
                }
            }
            copy = true;
        } catch (Exception e) {
            this.temp.append("FEHLER 2 data.helpMe.establishKiDB.doCopyTableContent: ").append(e);
        } finally {
            try {
                kiDataBase.closeConnection();
            } catch (Exception e) {
                this.temp.append("FEHLER 3 data.helpMe.establishKiDB.doCopyTableContent: ").append(e);
            }
        }
        return copy;
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Prüft die Tabelle mit KiBase, ob sie ein Update braucht
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    /* Spalten die das ResultSet DatabaseMetaData.GetColums(...) liefert.
    TABLE_CAT String => table catalog (may be null)
    TABLE_SCHEM String => table schema (may be null)
    TABLE_NAME String => table name
    COLUMN_NAME String => column name
    DATA_TYPE int => SQL type from java.sql.Types
    TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
    COLUMN_SIZE int => column size. For char or date types this is the maximum number of characters, for numeric or decimal types this is precision.
    BUFFER_LENGTH is not used.
    DECIMAL_DIGITS int => the number of fractional digits
    NUM_PREC_RADIX int => Radix (typically either 10 or 2)
    NULLABLE int => is NULL allowed.
    columnNoNulls - might not allow NULL values
    columnNullable - definitely allows NULL values
    columnNullableUnknown - nullability unknown
    REMARKS String => comment describing column (may be null)
    COLUMN_DEF String => default value (may be null)
    SQL_DATA_TYPE int => unused
    SQL_DATETIME_SUB int => unused
    CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
    ORDINAL_POSITION int => index of column in table (starting at 1)
    IS_NULLABLE String => "NO" means column definitely does not allow NULL values; "YES" means the column might allow NULL values. An empty string means nobody knows.
    SCOPE_CATLOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
    SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
    SCOPE_TABLE String => table name that this the scope of a reference attribure (null if the DATA_TYPE isn't REF)
    SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
    */
    /**
     * Prueft eine Tabelle, ob die Struktur mit der Beschreibung in KiBase
     * uebereinstimmt oder ein Struktur-Update braucht.
     * Methode:	Es werden die Metadaten der existierenden Tabelle gelesen und mit den
     *			Feldbschreibungen in KiBase verglichen.
     *
     *			Ist ein Typ unterschiedlich, bzw gibt es eine Spalte der Tabelle nicht in
     *			der Feldbeschreibung, dann braucht die Tabelle ein Struktur-Update.
     *
     *			Ist die Anzahl der Felder in der Feldbeschreibung größer der Anzahl
     *			der Spalten in der Tabelle, dann braucht die Tabelle ein Struktur-Update.
     */
    private boolean needsStructureUpdate(String kiTable) {
        boolean needsUpdate = false;
        KiDataBase kiDataBase = new KiDataBase();
        try {
            Connection con = kiDataBase.establishConnection();
            DatabaseMetaData md = con.getMetaData();
            // Die Info ueber die Spalten der physischen Tabelle werden in einem ResultSet
            // aus dem MetaDaten-Objekt geliefert und gesammelt
            ResultSet mdrs = md.getColumns("","", kiTable,"%");
            TreeMap<String, TreeMap<String, Object>> colums1 = new TreeMap<>();
            while (mdrs.next()) {
                String fldName = mdrs.getString("COLUMN_NAME");
                TreeMap<String, Object> col = new TreeMap<>();
                col.put("COLUMN_NAME", fldName);
                col.put("TYPE_NAME", mdrs.getString("TYPE_NAME"));
                col.put("COLUMN_SIZE", mdrs.getInt("COLUMN_SIZE"));
                col.put("DECIMAL_DIGITS", mdrs.getInt("DECIMAL_DIGITS"));
                colums1.put(fldName, col);
            }
            // daten aus KiBase werden abgefragt und gesammelt
            StringBuilder sqlCreate = new StringBuilder();
            sqlCreate.append("SELECT FieldName, FieldType, FieldLength, DecimalPlaces FROM KiBase WHERE bezeichnung = '").append(kiTable).append("';");
            ResultSet rs = kiDataBase.doSelect(sqlCreate.toString());
            TreeMap<String, TreeMap<String, Object>> colums2 = new TreeMap<>();
            while(rs.next()) {
                String fldName = rs.getString("FieldName");
                String fldType = rs.getString("FieldType");
                int fldLen = 0;
                int fldDec = 0;
                if(fldType.equals("Varchar")) {
                    fldLen = rs.getInt("FieldLength");
                }
                if(fldType.equals("Decimal")) {
                    fldLen = rs.getInt("FieldLength");
                    fldDec = rs.getInt("DecimalPlaces");
                }
                TreeMap<String, Object> col = new TreeMap<>();
                col.put("COLUMN_NAME", fldName);
                col.put("TYPE_NAME", fldType);
                col.put("COLUMN_SIZE", fldLen);
                col.put("DECIMAL_DIGITS", fldDec);
                colums2.put(fldName, col);
            }
            /* wenn die Anzahl der Spalten der physischen Tabelle nicht mit der Anzahl der Datensaetze aus KiBase uebereinstimmt,
               muss die Tabelle upgedatet werden. */
            if(colums1.size() != colums2.size()) {
                needsUpdate = true;
            }
            /* wenn die Anzahl der Spalten der physischen Tabelle mit der Anzahl der Datensaetze aus KiBase uebereinstimmt,
               werden die Spalten in beiden TreeMaps verglichen und bei Uebereinstimmung von Name und Inhalt geloescht.
               Sind die TreeMaps am Ende nicht leer, muss upgedatet werden. */
            else {
                Iterator<String> iti1 = colums1.keySet().iterator();
                loop1:
                while(iti1.hasNext()) {
                    String colName1 = iti1.next();
                    TreeMap<String, Object> col1 = colums1.get(colName1);
                    String fldType1 = (String)col1.get("TYPE_NAME");
                    int fldLen1  = (Integer) col1.get("COLUMN_SIZE");
                    int fldDec1  = (Integer) col1.get("DECIMAL_DIGITS");
                    for (String colName2 : colums2.keySet()) {
                        // spaltennamen werden verglichen
                        if (colName2.equals(colName1)) {
                            // sind die spaltennamen gleich, wird der inhalt verglichen
                            TreeMap<String, Object> col2 = colums2.get(colName2);
                            String fldType2 = (String) col2.get("TYPE_NAME");
                            int fldLen2 = (Integer) col2.get("COLUMN_SIZE");
                            int fldDec2 = (Integer) col2.get("DECIMAL_DIGITS");
                            /* Hier muss eine Ausnahme behandelt werden:
                               Wenn man in der mySQL einen varchar Typ mit einer Laenge < 4 anlegen will,
                               macht die mySQL einen CHAR daraus -> laut KiBase sollte es ein varchar sein - die
                               Metadaten liefern aber einen char. dh. Obwohl hier ein Unterschied vorliegt braucht die
                               Tabelle kein StrukturUpdate. */
                            if (!fldType2.endsWith(fldType1.toUpperCase())) {
                                break loop1;
                            }
                            // Bei Varchar und Decimal Typen ueberpruefen, ob die Feldlaengen und Dezimalstellen uebereinstimmen
                            else {
                                // bei varchar werden die feldlängen verglichen
                                if (fldType2.equals("Varchar")) {
                                    // die feldlängen werden verglichen
                                    if (fldLen2 == fldLen1) {
                                        // sind die feldlängen gleich, wird die spalte aus beiden treemaps geloescht
                                        // und die innere schleife unterbrochen
                                        colums2.remove(colName2);
                                        break;
                                    }
                                    // sind die feldlängen nicht gleich, wird die äussere schleife unterbrochen und upgedatet
                                    else {
                                        break loop1;
                                    }
                                }
                                // bei decimal werden feldlänge und dezimalstellen verglichen
                                else if (fldType2.equals("Decimal")) {
                                    // die feldlängen werden verglichen
                                    if (fldLen2 == fldLen1) {
                                        // sind die feldlängen gleich, werden die dezimalstellen verglichen
                                        if (fldDec2 == fldDec1) {
                                            // sind die dezimalstellen gleich, wird die spalte aus beiden treemaps geloescht
                                            // und die innere schleife unterbrochen
                                            colums2.remove(colName2);
                                            break;
                                        }
                                        // sind die dezimalstellen nicht gleich, wird die äussere schleife unterbrochen und upgedatet
                                        else {
                                            break loop1;
                                        }
                                    }
                                    // sind die feldlängen nicht gleich, wird die äussere schleife unterbrochen und upgedatet
                                    else {
                                        break loop1;
                                    }
                                }
                                // bei allen anderen typen reicht die übereinstimmung des typs
                                else {
                                    colums2.remove(colName2);
                                    break;
                                }
                            }
                        }
                    }
                }
                // ist die TreeMap am Ende nicht leer, stimmen die Spalten nicht ueberein und die Tabelle muss upgedatet werden.
                if(colums2.size() > 0) {
                    needsUpdate = true;
                }
            }
            // *********************************************************************************************************************************
            // Indizes, werden ueber die Datei fld79 gesteuert
//            if(!needsUpdate)
//            {
//                try
//                {
//                    // zuerst werden die Indizes der realen Tabelle in der DB abgefragt
//                    ResultSet mdrs1 = md.getIndexInfo("", "", kiTable, false, false);
//                    // dann wird abgefragt, ob für die Tabelle Datensaetze in der Datei fld79 existieren
//                    Statement stmt2 = conn.createStatement();
//                    ResultSet rs2 = stmt2.executeQuery
//                            ("SELECT * FROM fld79 WHERE QOBOBJ='" + kiTable + "'");
//                    // in fld79 wurden Daten gefunden
//                    if(rs2.next())
//                    {
//                        // die Indizes, die die reale Tabelle in der DB besitzt, werden gesammelt
//                        TreeMap indizes1 = new TreeMap();
//                        String indexName1 = "";
//                        String indexS1 = "";
//                        StringBuffer index = new StringBuffer();
//                        while(mdrs1.next())
//                        {
//                            if(indexName1.equals(""))
//                            {
//                                indexName1 = mdrs1.getString("INDEX_NAME");
//                            }
//                            if(indexName1.equals(mdrs1.getString("INDEX_NAME")))
//                            {
//                                index.append(mdrs1.getString("COLUMN_NAME") + ",");
//                            }
//                            else
//                            {
//                                indexS1 = index.toString().substring(0, index.toString().length() - 1);
//                                indizes1.put(indexName1, indexS1);
//                                indexName1 = mdrs1.getString("INDEX_NAME");
//                                index = new StringBuffer();
//                                index.append(mdrs1.getString("COLUMN_NAME") + ",");
//                            }
//                        }
//                        if(index.toString().length() - 1 > 0)
//                        {
//                            indexS1 = index.toString().substring(0, index.toString().length() - 1);
//                        }
//                        indizes1.put(indexName1, indexS1);
//                        // Indizes aus fld79 werden abgefragt und gesammelt
//                        Statement stmt1 = conn.createStatement();
//                        ResultSet rs1 = stmt1.executeQuery
//                                ("SELECT QOTITL, QOT150 FROM fld79 WHERE QOBOBJ='" + kiTable + "'");
//                        TreeMap indizes2 = new TreeMap();
//                        while(rs1.next())
//                        {
//                            indizes2.put(rs1.getString("QOTITL"), rs1.getString("QOT150"));
//                        }
//                    /* wenn die Anzahl der Indizes der Tabelle in der DB nicht mit der Anzahl der Datensaetze aus fld79 uebereinstimmt,
//                       muss die Tabelle upgedatet werden. */
//                        if(indizes1.size() != indizes2.size())
//                        {
//                            needsUpdate = true;
//                        }
//                    /* wenn die Anzahl der Indizes der Tabelle in der DB mit der Anzahl der Datensaetze aus fld79 uebereinstimmt,
//                       werden die Indizes in beiden TreeMaps verglichen und bei Uebereinstimmung von Name und Inhalt geloescht.
//                       Sind die TreeMaps am Ende leer, muss nicht upgedatet werden. */
//                        else
//                        {
//                            Iterator iti1 = indizes1.keySet().iterator();
//                            loop1:
//                            while(iti1.hasNext())
//                            {
//                                indexName1 = (String)iti1.next();
//                                indexS1 = (String)indizes1.get(indexName1);
//                                Iterator iti2 = indizes2.keySet().iterator();
//                                while(iti2.hasNext())
//                                {
//                                    String indexName2 = (String)iti2.next();
//                                    // indexnamen werden verglichen
//                                    if(indexName2.equals(indexName1))
//                                    {
//                                        // sind die namen gleich, wird der inhalt verglichen
//                                        String indexS2 = (String)indizes2.get(indexName2);
//                                        if(indexS2.equals(indexS1))
//                                        {
//                                            /* ist der inhalt gleich wird der index in beiden treemaps gelöscht
//                                               und die innere schleife unterbrochen */
//                                            indizes2.remove(indexName2);
//                                            break;
//                                        }
//                                        else
//                                        {
//                                            // ist der inhalt nicht gleich, wird die äussere schleife unterbrochen und upgedatet
//                                            break loop1;
//                                        }
//                                    }
//                                }
//                            }
//                            // ist die TreeMap am Ende nicht leer, stimmen die Indizes nicht ueberein und die Tabelle muss upgedatet werden.
//                            if(indizes2.size() > 0)
//                            {
//                                needsUpdate = true;
//                            }
//                        }
//                    }
//                    // in fld79 wurden keine Eintragungen gefunden
//                    else
//                    {
//                        // wenn die reale Tabelle in der DB trotzdem Indizes hat, muss sie upgedatet werden
//                        if(mdrs1.next())
//                        {
//                            needsUpdate = true;
//                        }
//                    }
//                }
//                catch (Exception exi){;}
//            }
//            // *********************************************************************************************************************************
//            //Statement schliessen
//            try {stmt.close();} catch (Exception e) {;}
        }
        catch (Exception e) {
            this.temp.append("FEHLER 1 data.helpMe.establishKiDB.needsStructureUpdate: ").append(e);
        } finally {
            try {
                kiDataBase.closeConnection();
            } catch (Exception e) {
                this.temp.append("FEHLER 2 data.helpMe.establishKiDB.needsStructureUpdate: ").append(e);
            }
        }
        return needsUpdate;
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Methode, die aufgerufen wird     -->     führt das DB-StrukturUpdate durch (entweder für eine Tabelle, oder für alle aus KiBase --> LeerString wird übergeben)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String doStructureUpdate(String kiTable) {
        KiDataBase kiDataBase = new KiDataBase();
        try {
            //*************************************************************************************************************************
            // zuerst werden die physischen Tabellen mit denen in KiBase verglichen
            Connection con = kiDataBase.establishConnection();
            DatabaseMetaData md = con.getMetaData();
            ResultSet tables = md.getTables("", "", "%", new String[]{"TABLE"});
            PreparedStatement pst = con.prepareStatement("SELECT * FROM KiBase WHERE bezeichnung = ?");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                // KiBase wird ausgeschlossen weil sie in sich selbst nicht existiert und nie gelöscht werden darf!!!
                if (!tableName.equalsIgnoreCase("KiBase")) {
                    pst.setString(1, tableName.toUpperCase());
                    ResultSet rs1 = pst.executeQuery();
                    // gibt es eine physische Tabelle in KiBase nicht, wird sie geloescht
                    if (!rs1.next()) {
                        kiDataBase.doDropTable(tableName);
                    }
                }
            }
            //*************************************************************************************************************************
            StringBuilder sqlCreate = new StringBuilder();
            // Select-Statement abhaengig ob Tabellenname angegeben wurde oder nicht.
            // Wenn kein bestimmter Tabellenname uebergeben wurde, auf jeden Fall KiBase ausschliessen.
            if (kiTable.isEmpty()) {
                sqlCreate.append("SELECT DISTINCT bezeichnung FROM KiBase WHERE bezeichnung <> 'KiBase'");
            } else {
                sqlCreate.append("SELECT DISTINCT bezeichnung FROM KiBase WHERE bezeichnung = '").append(kiTable).append("'");
            }
            ResultSet rs = kiDataBase.doSelect(sqlCreate.toString());
            // Alle gefundenen Tabellennamen in KiBase durchlaufen, falls nicht nur eine angegeben wurde
            while (rs.next()) {
                if (!rs.getString("bezeichnung").equals("") && needsStructureUpdate(rs.getString("bezeichnung"))) {
                    doCreateTable(rs.getString("bezeichnung"));
                }
            }
            this.temp.append("DatenBank kiPferL wurde erfolgreich upgedatet.");
        } catch (Exception e) {
            this.temp.append("FEHLER 1 data.helpMe.establishKiDB.doStructureUpdate: ").append(e);
        } finally {
            try {
                kiDataBase.closeConnection();
            } catch (Exception e) {
                this.temp.append("FEHLER 2 data.helpMe.establishKiDB.doStructureUpdate: ").append(e);
            }
        }
        // LogText wird auf der Konsole und im Frame ausgegeben (-> Fehler oder Erfolg)
        System.out.println(this.temp.toString());
        return this.temp.toString();
    }
}
