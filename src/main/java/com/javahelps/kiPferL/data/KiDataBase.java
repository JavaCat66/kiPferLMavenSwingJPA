package com.javahelps.kiPferL.data;

import com.javahelps.kiPferL.data.establishKiDB.KiDBTable;
import com.javahelps.kiPferL.data.establishKiDB.KiDBTableCol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Klasse, die Methoden bzgl. DatenBank zur Verfügung stellt
 *
 * @author Susanna Gruber
 * @version Alpha 2.0 2021.01.31
 * @since 1.14
 */
public class KiDataBase {

    private Connection con;

    // *********************************************************************************************************************************************************************************
    // Connection aufbauen
    public Connection establishConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        String connection_path = "jdbc:mysql://localhost/kiPferL?user=root";
        this.con = DriverManager.getConnection(connection_path);
        System.out.println("LOG: Connection wurde aufgebaut.");
        return this.con;
    }
    // *********************************************************************************************************************************************************************************
    // SELECT
    public ResultSet doSelect(String query) throws Exception {
        if(this.con == null) {
            this.con = this.establishConnection();
        }
        assert this.con != null;
        ResultSet rs = this.con.createStatement().executeQuery(query);
        System.out.println("LOG: Abfrage \"" + query + "\" wurde durchgeführt.");
        return rs;
    }
    // *********************************************************************************************************************************************************************************
    // UPDATE bzw. INSERT
    public void doUpdate(String update) throws Exception {
        if(this.con == null) {
            this.con = this.establishConnection();
        }
        assert this.con != null;
        this.con.createStatement().executeUpdate(update);
        System.out.println("LOG: Update \"" + update + "\" wurde durchgeführt.");
    }
    // *********************************************************************************************************************************************************************************
    // DELETE
    public void doDelete(String tableBezeichnung, String...conditions) throws Exception {
        if(this.con == null) {
            this.con = this.establishConnection();
        }
        assert this.con != null;
        StringBuilder deleteUpdate = new StringBuilder();
        deleteUpdate.append("DELETE FROM ");
        deleteUpdate.append(tableBezeichnung);
        deleteUpdate.append(" WHERE ");
        for(int i = 0; i < conditions.length; i++) {
            deleteUpdate.append(conditions[i]);
            if(i < conditions.length - 1) {
                deleteUpdate.append(" AND ");
            }
        }
        deleteUpdate.append(";");
        this.con.createStatement().executeUpdate(deleteUpdate.toString());
        System.out.println("LOG: Löschung \"" + deleteUpdate.toString() + "\" wurde durchgeführt.");
    }
    // *********************************************************************************************************************************************************************************
    // DROP TABLE
    public void doDropTable(String tableBezeichnung) throws Exception {
        if(!tableBezeichnung.equalsIgnoreCase("KiBase")) {
            if (this.con == null) {
                this.con = this.establishConnection();
            }
            assert this.con != null;
            StringBuilder drop = new StringBuilder();
            drop.append("DROP TABLE IF EXISTS ");
            drop.append(tableBezeichnung);
            drop.append(";");
            this.con.createStatement().executeUpdate(drop.toString());
            System.out.println("LOG: Tabelle " + tableBezeichnung + " wurde gelöscht.");
        } else {
            System.out.println("FEHLER: KiBase darf NIE gelöscht werden!");
        }
    }
    // *********************************************************************************************************************************************************************************
    // Eigene Methode zum Befüllen der Tabelle KiBase mit der übergebenen data.helpMe.establishKiDB.KiDBTable
    public String doUpdateKiBase(KiDBTable pKiDBTable) throws Exception {
        boolean update = false;
        if(this.con == null) {
            this.con = this.establishConnection();
        }
        assert this.con != null;
        // abfragen, ob die Tabelle schon eingetragen ist
        ResultSet rs = this.doSelect("SELECT * FROM KiBase WHERE bezeichnung = '" + pKiDBTable.getBezeichnung() + "';");
        // falls die Tabelle eingetragen ist, werden alle Sätze gelöscht
        if(rs.next()) {
            update = true;
            String[] conditions = {"bezeichnung = '" + pKiDBTable.getBezeichnung() + "'"};
            this.doDelete("KiBase", conditions);
        }
        // INSERT
        StringBuilder myInsert = new StringBuilder();
        myInsert.append
            ("INSERT INTO KiBase(id, bezeichnung, ColNr, praefix, FieldName, FieldType, FieldLength, DecimalPlaces, KiUnsigned, KiNull, PrimaryKey, KiDefault, ").append(
                    "infoText, dateOfCreation, timeStampChange) ");
        myInsert.append("VALUES(?, '");
        myInsert.append(pKiDBTable.getBezeichnung());
        myInsert.append("', ?, '");
        myInsert.append(pKiDBTable.getPraefix());
        myInsert.append("', ?, ?, ?, ?, ?, ?, ?, ?, ?, '");
        myInsert.append(pKiDBTable.getDateOfCreation());
        myInsert.append("', ?);");
        PreparedStatement pst = con.prepareStatement(myInsert.toString());
        ArrayList<KiDBTableCol> cols = (ArrayList)pKiDBTable.getCols();
        for(KiDBTableCol col : cols) {
            pst.setString(1, col.getId());
            pst.setInt(2, col.getColNr());
            pst.setString(3, col.getFieldName());
            pst.setString(4, col.getFieldType());
            pst.setInt(5, col.getFieldLength());
            pst.setInt(6, col.getDecimalPlaces());
            if(col.isKiUnsigned()) {
                if(col.getFieldType().equalsIgnoreCase("Decimal") || col.getFieldType().equalsIgnoreCase("Int")) {
                    pst.setString(7, "J");
                } else {
                    pst.setString(7, "N");
                }
            } else {
                pst.setString(7, "N");
            }
            if(col.isKiNull()) {
                pst.setString(8, "J");
            } else {
                pst.setString(8, "N");
            }
            if(col.isPrimaryKey()) {
                pst.setString(9, "J");
            } else {
                pst.setString(9, "N");
            }
            pst.setString(10, col.getKiDefault());
            pst.setString(11, col.getInfoText());
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp myJavaTimestampObject = new java.sql.Timestamp(calendar.getTime().getTime());
            pst.setTimestamp(12, myJavaTimestampObject);
            pst.executeUpdate();
        }
        // KontrollText (Konsole)
        if(update) {
            return new StringBuilder().append("LOG: Tabelle ").append(pKiDBTable.getBezeichnung()).append(" wurde in der Tabelle KiBase geändert.").toString();
        } else {
            return new StringBuilder().append("LOG: Tabelle ").append(pKiDBTable.getBezeichnung()).append(" wurde in die Tabelle KiBase eingetragen.").toString();
        }
    }
    // *********************************************************************************************************************************************************************************
    // Connection schließen
    public void closeConnection() throws Exception {
        if(this.con != null) {
            con.close();
            System.out.println("LOG: Connection wurde geschlossen.");
        }
    }
}
