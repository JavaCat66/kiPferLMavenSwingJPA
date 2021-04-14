package com.javahelps.kiPferL.controlling.establishKiDB;

import com.javahelps.kiPferL.data.KiDataBase;
import com.javahelps.kiPferL.data.establishKiDB.KiDBTable;
import com.javahelps.kiPferL.data.establishKiDB.KiDBTableCol;
import com.javahelps.kiPferL.data.establishKiDB.KiDBUpdate;
import com.javahelps.kiPferL.viewing.establishKiDB.EstablishKiDBFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

/**
 * Controller-Klasse zum Frame viewing.helpMe.establishKiDB.EstablishKiDBFrame
 *
 * @author Susanna Gruber
 * @version Alpha 2.0 2021.01.31
 * @since 1.14
 */
public class EstablishKiDBActionController implements ActionListener {

    private EstablishKiDBFrame myFrame;
    private final JLabel label_0_1, label_0_2, label_1_1, label_1_2, label_2_1, label_2_2, label_3_1, label_3_2, label_4_1, label_4_2, label_4a_2, label_5_1, label_7_1, label_8_1, label_11a_1;
    private final JTextField textField_2_2, textField_3_2, textField_4_2, textField_5_2, textField_7_2, textField_8_2, textField_11a_2;
    private final JComboBox<String> comboBox_6_2;
    private final JRadioButton radioButton_9a_2_1, radioButton_9a_2_2, radioButton_9_2_1, radioButton_9_2_2, radioButton_10_2_1, radioButton_10_2_2;
    private final JTextArea textArea_11_2;
    private final JButton button_5_2, button_12_1, button_12_2, button_12_3, button_12_4;
    private KiDBTable myKiDBTable;
    private int colCount;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public EstablishKiDBActionController
            (EstablishKiDBFrame myFrame, JLabel label_0_1, JLabel label_0_2, JLabel label_1_1, JLabel label_1_2, JLabel label_2_1, JLabel label_2_2, JLabel label_3_1,
             JLabel label_3_2, JLabel label_4_1, JLabel label_4_2, JLabel label_4a_2, JLabel label_5_1, JLabel label_7_1, JLabel label_8_1, JLabel label_11a_1, JTextField textField_2_2,
             JTextField textField_3_2, JTextField textField_4_2, JTextField textField_5_2, JComboBox<String> comboBox_6_2, JTextField textField_7_2, JTextField textField_8_2,
             JRadioButton radioButton_9a_2_1, JRadioButton radioButton_9a_2_2, JRadioButton radioButton_9_2_1, JRadioButton radioButton_9_2_2, JRadioButton radioButton_10_2_1,
             JRadioButton radioButton_10_2_2, JTextField textField_11a_2, JTextArea textArea_11_2, JButton button_5_2, JButton button_12_1, JButton button_12_2, JButton button_12_3, JButton button_12_4) {

        this.myFrame = myFrame;
        this.label_0_1 = label_0_1;
        this.label_0_2 = label_0_2;
        this.label_1_1 = label_1_1;
        this.label_1_2 = label_1_2;
        this.label_2_1 = label_2_1;
        this.label_2_2 = label_2_2;
        this.label_3_1 = label_3_1;
        this.label_3_2 = label_3_2;
        this.label_4_1 = label_4_1;
        this.label_4_2 = label_4_2;
        this.label_4a_2 = label_4a_2;
        this.label_5_1 = label_5_1;
        this.label_7_1 = label_7_1;
        this.label_8_1 = label_8_1;
        this.label_11a_1 = label_11a_1;
        this.textField_2_2 = textField_2_2;
        this.textField_3_2 = textField_3_2;
        this.textField_4_2 = textField_4_2;
        this.textField_5_2 = textField_5_2;
        this.comboBox_6_2 = comboBox_6_2;
        this.textField_7_2 = textField_7_2;
        this.textField_8_2 = textField_8_2;
        this.radioButton_9a_2_1 = radioButton_9a_2_1;
        this.radioButton_9a_2_2 = radioButton_9a_2_2;
        this.radioButton_9_2_1 = radioButton_9_2_1;
        this.radioButton_9_2_2 = radioButton_9_2_2;
        this.radioButton_10_2_1 = radioButton_10_2_1;
        this.radioButton_10_2_2 = radioButton_10_2_2;
        this.textField_11a_2 = textField_11a_2;
        this.textArea_11_2 = textArea_11_2;
        this.button_5_2 = button_5_2;
        this.button_12_1 = button_12_1;
        this.button_12_2 = button_12_2;
        this.button_12_3 = button_12_3;
        this.button_12_4 = button_12_4;
        this.colCount = 1;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // actionPerformed
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        // Farbe für Warnhinweise setzen
        Color myColor = new Color(31, 31, 231);
        // *************************************************************************************************************************************************************************
        // 1.Form = TabellenKopf abgesendet
        // *************************************************************************************************************************************************************************
        if(o == this.button_5_2){
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Überprüfung der TextFeldEingaben
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            boolean fehler = false;
            // bezeichnung darf nicht leer sein
            String bezeichnung = this.textField_2_2.getText();
            if(bezeichnung.isEmpty()) {
                fehler = true;
                this.label_2_1.setForeground(myColor);
            } else {
                this.label_2_1.setForeground(Color.BLACK);
            }
            // die anderen beiden TextFelder werden ausgelesen (AnzahlTabellenspalten, Präfix)
            String numberOfColsS = this.textField_3_2.getText();
            int numberOfCols = 0;
            // numberOfCols darf nicht leer und muss numerisch sein
            try {
                numberOfCols = Integer.parseInt(numberOfColsS);
                this.label_3_1.setForeground(Color.BLACK);
            } catch (Exception e1) {
                fehler = true;
                this.label_3_1.setForeground(myColor);
                System.out.println("FEHLER: " + e1.toString());
            }
            // praefix muss 2 Buchstaben enthalten
            String praefix = this.textField_4_2.getText().toUpperCase();
            if(praefix.length() != 2 || !praefix.matches("[a-zA-Z]*")) {
                fehler = true;
                this.label_4_1.setForeground(myColor);
            } else {
                this.label_4_1.setForeground(Color.BLACK);
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // gibt es einen Eingabefehler, wird in den obersten beiden Labels eine Meldung ausgegeben, das Form bleibt gleich
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            if(fehler) {
                this.label_1_1.setText("Fehler!");
                this.label_1_1.setForeground(myColor);
                this.label_1_2.setText("Bitte Eingabe prüfen.");
                this.label_1_2.setForeground(myColor);
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // kein Fehler
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            else {
                // KiDBTable-Objekt erzeugen
                this.myKiDBTable = new KiDBTable(bezeichnung, praefix, numberOfCols);
                // die Labels der ersten 4 Zeilen mit den ausgelesenen Werten füllen
                this.label_2_1.setText("TabellenName: ");
                this.label_2_1.setForeground(Color.BLACK);
                this.label_2_2.setText(bezeichnung);
                this.label_3_1.setText("Anzahl Felder: ");
                this.label_3_1.setForeground(Color.BLACK);
                this.label_3_2.setText(String.valueOf(numberOfCols));
                this.label_4_1.setText("Praefix: ");
                this.label_4_1.setForeground(Color.BLACK);
                this.label_4_2.setText(praefix);
                // 2.Form aufrufen
                this.myFrame.changeLayout(1);
            }
        }
        // *************************************************************************************************************************************************************************
        // 2.Form = TabellenSpalte abgeschickt      -->     wird so lange aufgerufen, bis alle Spalten eingetragen sind (colCount)
        // *************************************************************************************************************************************************************************
        else if(o == this.button_12_2 && this.colCount <= this.myKiDBTable.getNumberOfCols()){
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Überprüfung der TextFeldEingaben
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            boolean fehler = false;
            // FeldName darf nicht leer sein
            String fieldName = this.textField_5_2.getText();
            if(fieldName.isEmpty()) {
                fehler = true;
                this.label_5_1.setForeground(myColor);
            } else {
                this.label_5_1.setForeground(Color.BLACK);
            }
            // die anderen Felder werden ausgelesen
            // FeldTyp muss nicht überprüft werden, weil er aus einer ComboBox kommt
            String fieldType = (String) this.comboBox_6_2.getSelectedItem();
            // Numerische Felder müssen geprüft werden (auf numerischen Wert bzw. PflichtFeld darf nicht leer sein)
            String fieldLengthS = this.textField_7_2.getText();
            int fieldLength = 0;
            // falls FeldLänge leer oder ein nichtnumerischer Wert eingegeben wurde, wird eine Exception ausgelöst
            try {
                fieldLength = Integer.parseInt(fieldLengthS);
                this.label_7_1.setForeground(Color.BLACK);
            } catch (Exception e1) {
                if(fieldType != null && !fieldType.equalsIgnoreCase("Date") && !fieldType.equalsIgnoreCase("Timestamp") &&
                        !fieldType.equalsIgnoreCase("Boolean")) {
                    fehler = true;
                    this.label_7_1.setForeground(myColor);
                    System.out.println("FEHLER: " + e1.toString());
                }
            }
            String decimalPlacesS = this.textField_8_2.getText();
            int decimalPlaces = 0;
            if(!decimalPlacesS.isEmpty() && fieldType != null && fieldType.equals("Decimal")) {
                try {
                    decimalPlaces = Integer.parseInt(decimalPlacesS);
                    this.label_8_1.setForeground(Color.BLACK);
                } catch (Exception e1) {
                    fehler = true;
                    this.label_8_1.setForeground(myColor);
                    System.out.println("FEHLER_1: " + e1.toString());
                }
            } else {
                this.textField_8_2.setText("");
            }
            // RadioButtons müssen nur ausgelesen werden, nicht geprüft
            boolean kiUnsigned = true;
            if(this.radioButton_9a_2_1.isSelected()) {
                kiUnsigned = false;
            }
            boolean kiNull = false;
            if(this.radioButton_9_2_2.isSelected()) {
                kiNull = true;
            }
            boolean primaryKey = false;
            if(this.radioButton_10_2_2.isSelected()) {
                primaryKey = true;
            }
            // Default muss mit true oder false ausgefüllt sein, wenn der Datentyp boolean ist
            String kiDefault = this.textField_11a_2.getText();
            if(fieldType != null && fieldType.equalsIgnoreCase("Boolean")) {
                if(kiDefault.isEmpty() || (!kiDefault.equalsIgnoreCase("false") && !kiDefault.equalsIgnoreCase("true"))) {
                    fehler = true;
                    this.label_11a_1.setForeground(myColor);
                } else {
                    this.label_11a_1.setForeground(Color.BLACK);
                }
            }
            // InfoText muss nicht befüllt sein
            String infoText = this.textArea_11_2.getText().trim();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // gibt es einen Eingabefehler, wird in den obersten beiden Labels eine Meldung ausgegeben, das Form bleibt gleich
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            if(fehler) {
                this.label_0_1.setText("Fehler!");
                this.label_0_2.setText("Bitte Eingabe prüfen.");
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // kein Fehler -->   Formular wird so lange aufgerufen, wie die Tabelle Felder hat, dann erst wird das 3.Form aufgerufen
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            else {
                // KiDBTableCol-Objekt erzeugen und in SammelObjekt von KiDBTable stellen
                KiDBTableCol myKiDBTableCol = new KiDBTableCol(fieldName, fieldType, fieldLength, decimalPlaces, kiUnsigned, kiNull, primaryKey, kiDefault, infoText);
                this.myKiDBTable.addTableCol(myKiDBTableCol);
                // *****************************************************************************************************************************************************************
                // alle spalten eingetragen     -->     JTable erzeugen und befüllen    -->     3.Form aufrufen (Tabelle ausgeben)
                // *****************************************************************************************************************************************************************
                if(this.colCount == this.myKiDBTable.getNumberOfCols()) {
                    System.out.println(this.myKiDBTable.toString(0));
                    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    // JTable erzeugen und befüllen
                    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    // columnNames sind die FeldNamen der Tabelle KiBase + einem Feld "Action" für etwaige Aktionen wie löschen, ändern...
                    String [] columnNames = new String[1];
                    try {
                        KiDataBase db = new KiDataBase();
                        // FeldNamen von KiBase abfragen und in Array columnNames stellen
                        ResultSet rs = db.doSelect("SELECT * FROM KiBase");
                        ResultSetMetaData meta = rs.getMetaData();
                        int size = meta.getColumnCount();
                        columnNames = new String[size];
                        int i;
                        for (i = 1; i <= size; i++) {
                            columnNames[i - 1] = meta.getColumnName(i);
                        }
                        db.closeConnection();
                    } catch (Exception e1) {
                        System.out.println("FEHLER: " + e1.toString());
                    }
                    // 2dimensionales Array für TabellenDaten
                    Object[][] data = new Object[this.myKiDBTable.getNumberOfCols() + 1][columnNames.length];
                    // FeldNamen = Überschriften in die erste Zeile des DatenArrays stellen
                    System.arraycopy(columnNames, 0, data[0], 0, columnNames.length);
                    // Zeilen aus KiDBTable-Objekt und aus List KiDBTable.cols auslesen und in DatenArray stellen
                    ArrayList<KiDBTableCol> cols = (ArrayList<KiDBTableCol>)this.myKiDBTable.getCols();
                    for(int i = 1; i < cols.size() + 1; i++) {
                        KiDBTableCol col = cols.get(i - 1);
                        data[i][0] = col.getId();
                        data[i][1] = this.myKiDBTable.getBezeichnung();
                        data[i][2] = col.getColNr();
                        data[i][3] = this.myKiDBTable.getPraefix();
                        data[i][4] = col.getFieldName();
                        data[i][5] = col.getFieldType();
                        data[i][6] = String.valueOf(col.getFieldLength());
                        data[i][7] = String.valueOf(col.getDecimalPlaces());
                        if(col.isKiUnsigned()) {
                            data[i][8] = "J";
                        } else {
                            data[i][8] = "N";
                        }
                        if(col.isKiNull()) {
                            data[i][9] = "J";
                        } else {
                            data[i][9] = "N";
                        }
                        if(col.isPrimaryKey()) {
                            data[i][10] = "J";
                        } else {
                            data[i][10] = "N";
                        }
                        data[i][11] = col.getKiDefault();
                        data[i][12] = col.getInfoText();
                        data[i][13] = this.myKiDBTable.getDateOfCreation();
                        data[i][14] = col.getTimeStampChange();
                        data[i][15] = this.myKiDBTable.getModul();
                        data[i][16] = this.myKiDBTable.getProgram();
                        data[i][17] = this.myKiDBTable.getMethod();
                        data[i][18] = this.myKiDBTable.getGroupID();
                        data[i][19] = this.myKiDBTable.getUser();
                        data[i][20] = this.myKiDBTable.getStatus();
                    }
                    // JTable aus Daten und Felder erstellen
                    JTable myTable = new JTable(data, columnNames);
                    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    // fertige JTable an Frame übergeben
                    this.myFrame.setMyTable(myTable);
                    // 3.Form aufrufen
                    this.myFrame.changeLayout(2);
                // *****************************************************************************************************************************************************************
                } else {
                    // SpaltenCount erhöhen (so lange, bis alle Spalten eingetragen sind)
                    ++this.colCount;
                    // setzt Fokus zurück in das erste feld
                    this.textField_5_2.requestFocusInWindow();
                    // felder zurücksetzen
                    this.label_5_1.setForeground(Color.BLACK);
                    this.label_7_1.setForeground(Color.BLACK);
                    this.label_8_1.setForeground(Color.BLACK);
                    this.label_11a_1.setForeground(Color.BLACK);
                    this.label_0_1.setText("");
                    this.label_0_2.setText("");
                    this.label_4a_2.setText(String.valueOf(this.colCount));
                    this.textField_5_2.setText("");
                    this.comboBox_6_2.setSelectedItem("Varchar");
                    this.textField_7_2.setText("");
                    this.textField_8_2.setText("");
                    this.radioButton_9a_2_2.setSelected(true);
                    this.radioButton_9_2_1.setSelected(true);
                    this.radioButton_10_2_1.setSelected(true);
                    this.textField_11a_2.setText("");
                    this.textArea_11_2.setText("");
                }
            }
        }
        // *************************************************************************************************************************************************************************
        // 3., 4., 5.Form abgeschickt linker Button = Fenster zerstören, 1.Form wieder aufrufen (neues Frame-Objekt erzeugen)
        // *************************************************************************************************************************************************************************
        else if(o == this.button_12_1) {
            this.myFrame.dispose();
            this.myFrame = new EstablishKiDBFrame();
        }
        // *************************************************************************************************************************************************************************
        // 3.Form abgeschickt rechter Button = Tabelle in KiBase speichern und 4.Form aufrufen
        // *************************************************************************************************************************************************************************
        else if(o == this.button_12_3 && this.button_12_3.getText().equals("TabellenDaten in KiBase abspeichern")) {
            this.label_0_1.setText(this.myKiDBTable.doInsertIntoKiBase());
            // nächsten BildSchirm aufrufen
            this.myFrame.changeLayout(3);
        }
        // *************************************************************************************************************************************************************************
        // 4.Form abgeschickt rechter Button = DatenBankUpdate
        // *************************************************************************************************************************************************************************
        else if(o == this.button_12_3 && this.button_12_3.getText().equals("DatenBank kiPferL updaten")) {
            KiDBUpdate kiDBUpdate = new KiDBUpdate();
            this.label_0_1.setText(kiDBUpdate.doStructureUpdate(""));
            // nächsten BildSchirm aufrufen
            this.myFrame.changeLayout(4);
        }
        // *************************************************************************************************************************************************************************
        // 5.Form abgeschickt rechter Button = Ende Programm
        // *************************************************************************************************************************************************************************
        else if(o == this.button_12_4 && this.button_12_4.getText().equals("Schließen")) {
            System.exit(0);
        }
    }
}
