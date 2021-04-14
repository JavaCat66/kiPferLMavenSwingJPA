package com.javahelps.kiPferL.viewing.establishKiDB;

import com.javahelps.kiPferL.controlling.establishKiDB.EstablishKiDBActionController;
import com.javahelps.kiPferL.controlling.establishKiDB.EstablishKiDBFocusController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Frame-Klasse zur Eingabe eines KiDBTable-Objekts (Kopf und Spalten) in die DB-Tabelle KiBase
 *
 * @author Susanna Gruber
 * @version Alpha 2.0 2021.01.31
 * @since 1.14
 */
public class EstablishKiDBFrame extends JFrame {

    private final JPanel panel;
    private final JLabel label_0_1, label_0_2, label_2_1, label_2_2, label_3_1, label_3_2, label_4_1, label_4_2, label_4a_1, label_4a_2, label_5_1, label_6_1, label_7_1,
            label_8_1, label_9a_1, label_9_1, label_10_1, label_11a_1, label_11_1, label_12_1;
    private final JTextField textField_5_2, textField_7_2, textField_8_2, textField_11a_2;
    private final JComboBox<String> comboBox_6_2;
    private final JRadioButton radioButton_9a_2_1, radioButton_9a_2_2, radioButton_9_2_1, radioButton_9_2_2, radioButton_10_2_1, radioButton_10_2_2;
    private final ButtonGroup bottonGroup_9a_2, bottonGroup_9_2, bottonGroup_10_2;
    private final JTextArea textArea_11_2;
    private JTable myTable;
    private final JButton button_12_1, button_12_2, button_12_3, button_12_4;

    private final Border myLineBorder = new LineBorder(new Color(238, 238, 238), 10);

    private final EstablishKiDBActionController myActionController;
    private final EstablishKiDBFocusController myFocusController;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor (1.Form)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public EstablishKiDBFrame() {
        // ButtonFarbe definieren
        Color myButtonColor = new Color(102, 205, 170);
        // Layout-Panel
        this.panel = new JPanel();
        // *************************************************************************************************************************************************************************
        // 1.Form
        // *************************************************************************************************************************************************************************
        JLabel label_1_1 = new JLabel();
        Font myFont = new Font("Calibri", Font.BOLD, 16);
        label_1_1.setFont(myFont);
        label_1_1.setBorder(myLineBorder);

        JLabel label_1_2 = new JLabel();
        label_1_2.setFont(myFont);
        label_1_2.setBorder(myLineBorder);

        this.label_2_1 = new JLabel("TabellenName*: ");
        this.label_2_1.setFont(myFont);
        this.label_2_1.setBorder(myLineBorder);

        JTextField textField_2_2 = new JTextField();
        textField_2_2.setFont(myFont);
        textField_2_2.setBorder(myLineBorder);

        this.label_3_1 = new JLabel("Anzahl Felder*: ");
        this.label_3_1.setFont(myFont);
        this.label_3_1.setBorder(myLineBorder);

        JTextField textField_3_2 = new JTextField();
        textField_3_2.setFont(myFont);
        textField_3_2.setBorder(myLineBorder);

        this.label_4_1 = new JLabel("Praefix (2 Buchstaben)*: ");
        this.label_4_1.setFont(myFont);
        this.label_4_1.setBorder(myLineBorder);

        JTextField textField_4_2 = new JTextField();
        textField_4_2.setFont(myFont);
        textField_4_2.setBorder(myLineBorder);

        this.label_5_1 = new JLabel();
        this.label_5_1.setFont(myFont);
        this.label_5_1.setBorder(myLineBorder);

        JButton button_5_2 = new JButton("Tabelle anlegen");
        button_5_2.setFont(myFont);
        button_5_2.setBorder(myLineBorder);
        button_5_2.setBackground(myButtonColor);
        // *************************************************************************************************************************************************************************
        // Komponenten für das 2.Form initialisieren, damit sie dem ActionListener übergeben werden können
        this.label_0_1 = new JLabel();
        this.label_0_1.setFont(myFont);
        this.label_0_1.setForeground(new Color(0,0,139));
        this.label_0_1.setBorder(myLineBorder);

        this.label_0_2 = new JLabel();
        this.label_0_2.setFont(myFont);
        this.label_0_2.setBorder(myLineBorder);

        this.label_4a_1 = new JLabel("FeldNummer: ");
        this.label_4a_1.setFont(myFont);
        this.label_4a_1.setBorder(myLineBorder);

        this.label_4a_2 = new JLabel();
        this.label_4a_2.setFont(myFont);
        this.label_4a_2.setBorder(myLineBorder);

        this.textField_5_2 = new JTextField();
        this.textField_5_2.setFont(myFont);
        this.textField_5_2.setBorder(myLineBorder);

        this.label_2_2 = new JLabel();
        this.label_2_2.setFont(myFont);
        this.label_2_2.setBorder(myLineBorder);

        this.label_3_2 = new JLabel();
        this.label_3_2.setFont(myFont);
        this.label_3_2.setBorder(myLineBorder);

        this.label_4_2 = new JLabel();
        this.label_4_2.setFont(myFont);
        this.label_4_2.setBorder(myLineBorder);

        this.label_6_1 = new JLabel();
        this.label_6_1.setFont(myFont);
        this.label_6_1.setBorder(myLineBorder);

        String[] fieldTypes = {"Boolean", "Date", "Decimal", "Char", "Int", "Text", "Timestamp", "Varchar"};
        this.comboBox_6_2 = new JComboBox<>(fieldTypes);
        this.comboBox_6_2.setFont(myFont);
        this.comboBox_6_2.setBorder(myLineBorder);
        this.comboBox_6_2.setEditable( true );
        this.comboBox_6_2.setSelectedItem("Varchar");

        this.label_7_1 = new JLabel();
        this.label_7_1.setFont(myFont);
        this.label_7_1.setBorder(myLineBorder);

        this.textField_7_2 = new JTextField();
        this.textField_7_2.setFont(myFont);
        this.textField_7_2.setBorder(myLineBorder);

        this.label_8_1 = new JLabel();
        this.label_8_1.setFont(myFont);
        this.label_8_1.setBorder(myLineBorder);

        this.textField_8_2 = new JTextField();
        this.textField_8_2.setFont(myFont);
        this.textField_8_2.setBorder(myLineBorder);

        this.label_9a_1 = new JLabel();
        this.label_9a_1.setFont(myFont);
        this.label_9a_1.setBorder(myLineBorder);

        this.radioButton_9a_2_1 = new JRadioButton();
        this.radioButton_9a_2_1.setFont(myFont);
        this.radioButton_9a_2_1.setBorder(myLineBorder);
        this.radioButton_9a_2_1.setText("Nein");

        this.radioButton_9a_2_2 = new JRadioButton();
        this.radioButton_9a_2_2.setFont(myFont);
        this.radioButton_9a_2_2.setBorder(myLineBorder);
        this.radioButton_9a_2_2.setText("Ja");

        this.bottonGroup_9a_2 = new ButtonGroup();

        this.label_9_1 = new JLabel();
        this.label_9_1.setFont(myFont);
        this.label_9_1.setBorder(myLineBorder);

        this.radioButton_9_2_1 = new JRadioButton();
        this.radioButton_9_2_1.setFont(myFont);
        this.radioButton_9_2_1.setBorder(myLineBorder);
        this.radioButton_9_2_1.setText("Nein");

        this.radioButton_9_2_2 = new JRadioButton();
        this.radioButton_9_2_2.setFont(myFont);
        this.radioButton_9_2_2.setBorder(myLineBorder);
        this.radioButton_9_2_2.setText("Ja");

        this.bottonGroup_9_2 = new ButtonGroup();

        this.label_10_1 = new JLabel();
        this.label_10_1.setFont(myFont);
        this.label_10_1.setBorder(myLineBorder);

        this.radioButton_10_2_1 = new JRadioButton();
        this.radioButton_10_2_1.setFont(myFont);
        this.radioButton_10_2_1.setBorder(myLineBorder);
        this.radioButton_10_2_1.setText("Nein");

        this.radioButton_10_2_2 = new JRadioButton();
        this.radioButton_10_2_2.setFont(myFont);
        this.radioButton_10_2_2.setBorder(myLineBorder);
        this.radioButton_10_2_2.setText("Ja");

        this.bottonGroup_10_2 = new ButtonGroup();

        this.label_11_1 = new JLabel();
        this.label_11_1.setFont(myFont);
        this.label_11_1.setBorder(myLineBorder);

        this.textArea_11_2 = new JTextArea(5, 30);
        this.textArea_11_2.setFont(myFont);
        //Zeilenumbruch wird eingeschaltet
        this.textArea_11_2.setLineWrap(true);
        //Zeilenumbrüche erfolgen nur nach ganzen Wörtern
        this.textArea_11_2.setWrapStyleWord(true);

        this.label_11a_1 = new JLabel();
        this.label_11a_1.setFont(myFont);
        this.label_11a_1.setBorder(myLineBorder);

        this.textField_11a_2 = new JTextField();
        this.textField_11a_2.setFont(myFont);
        this.textField_11a_2.setBorder(myLineBorder);

        this.label_12_1 = new JLabel();
        this.label_12_1.setFont(myFont);
        this.label_12_1.setBorder(myLineBorder);

        this.button_12_2 = new JButton("TabellenSpalte eintragen");
        this.button_12_2.setFont(myFont);
        this.button_12_2.setBorder(myLineBorder);
        this.button_12_2.setBackground(myButtonColor);
        // *************************************************************************************************************************************************************************
        // Komponenten für das 3.Form und weitere initialisieren, damit sie dem ActionListener übergeben werden können
        this.button_12_1 = new JButton();
        this.button_12_1.setFont(myFont);
        this.button_12_1.setBorder(myLineBorder);
        this.button_12_1.setBackground(myButtonColor);

        this.button_12_3 = new JButton();
        this.button_12_3.setFont(myFont);
        this.button_12_3.setBorder(myLineBorder);
        this.button_12_3.setBackground(myButtonColor);

        this.button_12_4 = new JButton();
        this.button_12_4.setFont(myFont);
        this.button_12_4.setBorder(myLineBorder);
        this.button_12_4.setBackground(myButtonColor);
        // *************************************************************************************************************************************************************************
        // ActionListener erzeugen
        this.myActionController = new EstablishKiDBActionController
                (this, this.label_0_1, this.label_0_2, label_1_1, label_1_2, this.label_2_1, this.label_2_2, this.label_3_1, this.label_3_2, this.label_4_1,
                        this.label_4_2, this.label_4a_2, this.label_5_1, this.label_7_1, this.label_8_1, this.label_11a_1, textField_2_2, textField_3_2, textField_4_2,
                        this.textField_5_2, this.comboBox_6_2, this.textField_7_2, this.textField_8_2, this.radioButton_9a_2_1, this.radioButton_9a_2_2, this.radioButton_9_2_1,
                        this.radioButton_9_2_2, this.radioButton_10_2_1, this.radioButton_10_2_2, this.textField_11a_2, this.textArea_11_2, button_5_2, this.button_12_1,
                        this.button_12_2, this.button_12_3, this.button_12_4);
        // *************************************************************************************************************************************************************************
        // Den ActionListener beim im 1.Form verwendeten Button anmelden
        button_5_2.addActionListener(this.myActionController);
        // *************************************************************************************************************************************************************************
        // FocusListener erzeugen
        this.myFocusController = new EstablishKiDBFocusController();
        // *************************************************************************************************************************************************************************
        // Layout setzen
        this.panel.setLayout(new GridLayout(5,2));
        // Komponenten für das 1.Form auf das Layout-Panel setzen
        this.panel.add(label_1_1);                                    this.panel.add(label_1_2);
        this.panel.add(this.label_2_1);
        this.panel.add(textField_2_2);
        textField_2_2.addFocusListener(myFocusController);                                                 // FocusListener anmelden
        this.panel.add(this.label_3_1);
        this.panel.add(textField_3_2);
        textField_3_2.addFocusListener(myFocusController);                                                 // FocusListener anmelden
        this.panel.add(this.label_4_1);
        this.panel.add(textField_4_2);
        textField_4_2.addFocusListener(myFocusController);                                                 // FocusListener anmelden
        this.panel.add(this.label_5_1);                                this.panel.add(button_5_2);
        // Layout-Panel auf den Rahmen setzen
        this.panel.setOpaque(true);
        this.add(this.panel);
        // RahmenEigenschaften definieren
        this.setTitle("Neue Tabelle für DB KiPferL TABLE KiBase erzeugen (Kopf):");
        this.setLocation(200,50);
        this.setSize(800,250);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        // *************************************************************************************************************************************************************************
        // Rahmen sichtbar machen
        this.setVisible(true);
        // macht, dass bei ENTER das Formular abgeschickt wird und man nicht auf den Button klicken muss
        this.getRootPane().setDefaultButton(button_5_2);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // set      -->     damit die JTable vom ActionController erstellt werden kann (KiDBTableObjekt wird dort erzeugt!)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public void setMyTable(JTable myTable) {
        this.myTable = myTable;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Methode zum Wechseln der Forms
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public void changeLayout(int pFrame) {
        // *************************************************************************************************************************************************************************
        // TabellenKopf wurde erfolgreich eingetragen, das 2.Form wird aufgebaut (wird so oft aufgerufen, wie es TabellenSpalten = KiDBTable.numberOfCols gibt)
        // *************************************************************************************************************************************************************************
        if(pFrame == 1) {
            // FensterTitel wird geändert
            this.setTitle("Neues Feld für Tabelle " + this.label_2_2.getText() + " eintragen:");
            // LayoutPanel unsichtbar machen
            this.panel.setVisible(false);
            // Alle Komponenten vom LayoutPanel löschen
            this.panel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Layout setzen
            this.panel.setLayout(new GridLayout(16,2));
            // Labels neu beschriften
            this.label_4a_2.setText("1");
            this.label_5_1.setText("FeldName*: ");
            this.label_6_1.setText("FeldTyp*: ");
            this.label_7_1.setText("FeldLänge* NICHT bei Date, Timestamp, Boolean: ");
            this.label_8_1.setText("DezimalStellen* NUR bei Gleitkommazahl: ");
            this.label_9a_1.setText("Unsigned* bei numerischen Feldern: ");
            this.label_9_1.setText("Null*: ");
            this.label_10_1.setText("PrimaryKey*: ");
            this.label_11_1.setText("InfoText max. 250 Zeichen: ");
            this.label_11a_1.setText("Default* bei Boolean true/false: ");
            this.label_12_1.setText("");
            // Den ActionListener an den im 2.Form verwendeten Button übergeben
            this.button_12_2.addActionListener(this.myActionController);
            // Layout-Panel wieder mit Komponenten befüllen
            this.panel.add(this.label_0_1);                                this.panel.add(this.label_0_2);
            this.panel.add(this.label_2_1);                                this.panel.add(this.label_2_2);
            this.panel.add(this.label_4_1);                                this.panel.add(this.label_4_2);
            this.panel.add(this.label_3_1);                                this.panel.add(this.label_3_2);
            this.panel.add(this.label_4a_1);                               this.panel.add(this.label_4a_2);
            this.panel.add(this.label_5_1);
            this.textField_5_2.addFocusListener(this.myFocusController);                                        // FocusListener anmelden
            this.panel.add(this.textField_5_2);
            this.panel.add(this.label_6_1);
            this.comboBox_6_2.addFocusListener(this.myFocusController);                                         // FocusListener anmelden
            this.panel.add(this.comboBox_6_2);
            this.panel.add(this.label_7_1);
            this.textField_7_2.addFocusListener(this.myFocusController);                                        // FocusListener anmelden
            this.panel.add(this.textField_7_2);
            this.panel.add(this.label_8_1);
            this.textField_8_2.addFocusListener(this.myFocusController);                                        // FocusListener anmelden
            this.panel.add(this.textField_8_2);
            // radiobuttons brauchen ein eigenes Panel, damit man die Buttons nebeneinander platzieren kann
            // 1.radiobutton-panel für Unsigned (nur für numerische Werte, default true)
            this.panel.add(this.label_9a_1);
            JPanel radioButtonPanel_9a_2 = new JPanel();
            radioButtonPanel_9a_2.setLayout(new GridLayout(1,2));
            this.radioButton_9a_2_1.addFocusListener(this.myFocusController);                                    // FocusListener anmelden
            radioButtonPanel_9a_2.add(this.radioButton_9a_2_1);
            this.radioButton_9a_2_2.addFocusListener(this.myFocusController);                                    // FocusListener anmelden
            radioButtonPanel_9a_2.add(this.radioButton_9a_2_2);
            this.radioButton_9a_2_2.setSelected(true);
            this.panel.add(radioButtonPanel_9a_2);
            this.bottonGroup_9a_2.add(this.radioButton_9a_2_1);
            this.bottonGroup_9a_2.add(this.radioButton_9a_2_2);
            // 2.radiobutton-panel für Null (default false)
            this.panel.add(this.label_9_1);
            JPanel radioButtonPanel_9_2 = new JPanel();
            radioButtonPanel_9_2.setLayout(new GridLayout(1,2));
            this.radioButton_9_2_1.addFocusListener(this.myFocusController);                                    // FocusListener anmelden
            radioButtonPanel_9_2.add(this.radioButton_9_2_1);
            this.radioButton_9_2_1.setSelected(true);
            this.radioButton_9_2_2.addFocusListener(this.myFocusController);                                    // FocusListener anmelden
            radioButtonPanel_9_2.add(this.radioButton_9_2_2);
            this.panel.add(radioButtonPanel_9_2);
            this.bottonGroup_9_2.add(this.radioButton_9_2_1);
            this.bottonGroup_9_2.add(this.radioButton_9_2_2);
            // 3.radiobutton-panel für primaryKey (default false)
            this.panel.add(this.label_10_1);
            JPanel radioButtonPanel_10_2 = new JPanel();
            radioButtonPanel_10_2.setLayout(new GridLayout(1,2));
            this.radioButton_10_2_1.addFocusListener(this.myFocusController);                                   // FocusListener anmelden
            radioButtonPanel_10_2.add(this.radioButton_10_2_1);
            this.radioButton_10_2_1.setSelected(true);
            this.radioButton_10_2_2.addFocusListener(this.myFocusController);                                   // FocusListener anmelden
            radioButtonPanel_10_2.add(this.radioButton_10_2_2);
            this.panel.add(radioButtonPanel_10_2);
            this.bottonGroup_10_2.add(this.radioButton_10_2_1);
            this.bottonGroup_10_2.add(this.radioButton_10_2_2);
            this.panel.add(this.label_11a_1);
            this.textField_11a_2.addFocusListener(this.myFocusController);                                      // FocusListener anmelden
            this.panel.add(this.textField_11a_2);
            // Zeile mit Textarea
            this.panel.add(this.label_11_1);
            // bei Drücken der Tab-Taste springt der Fokus weiter (braucht einen KeyListener statt FocusListener)
            // kopiert von https://kodejava.org/how-do-i-move-focus-from-jtextarea-using-tab-key/
            this.textArea_11_2.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_TAB) {
                        if (e.getModifiersEx() > 0) {
                            EstablishKiDBFrame.this.textArea_11_2.transferFocusBackward();
                        } else {
                            EstablishKiDBFrame.this.textArea_11_2.transferFocus();
                        }
                        e.consume();
                    }
                }
            });
            //Ein JScrollPane, der das Textfeld beinhaltet, wird erzeugt
            JScrollPane scrollpane = new JScrollPane(this.textArea_11_2);
            scrollpane.setBorder(myLineBorder);
            //Scrollpane wird unserem Panel hinzugefügt
            this.panel.add(scrollpane);
            // ButtonZeile
            this.panel.add(this.label_12_1);
            this.panel.add(this.button_12_2);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            this.setSize(800,650);
            // Layout-Panel sichtbar machen
            this.panel.setVisible(true);
            // setzt Fokus in das erste feld
            this.textField_5_2.requestFocusInWindow();
            // macht, dass bei ENTER das Formular abgeschickt wird
            this.getRootPane().setDefaultButton(this.button_12_2);
            // umschreiben
            this.validate();
        }
        // *************************************************************************************************************************************************************************
        // alle TabellenFelder wurden eingetragen und werden zur Kontrolle in einer Tabelle ausgegeben
        // *************************************************************************************************************************************************************************
        else if(pFrame == 2) {
            // FensterTitel wird geändert
            this.setTitle("Tabelle " + this.label_2_2.getText() + " kontrollieren:");
            // LayoutPanel unsichtbar machen
            this.panel.setVisible(false);
            // Alle Komponenten vom LayoutPanel löschen
            this.panel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Layout setzen
            this.panel.setLayout(new BorderLayout());
            this.panel.setOpaque(true);
            // Tabelle, die vom ActionController erstellt und mittels setMyTable(JTable) übertragen wurde, wird in das Panel gesetzt
            // vorher wird die JTable noch bei einem MouseListener angemeldet, der aufgerufen wird, falls eine Zelle doppelgeklickt und somit editierbar ist
            /*this.myTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    editDebugData();
                }
            });*/
            //this.myTable.addFocusListener(myFocusController);                                                   // FocusListener anmelden
            this.panel.add(this.myTable, BorderLayout.CENTER);
            // ButtonZeile
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1,2));
            this.button_12_1.setText("Tabelle verwerfen und neue eingeben");
            this.button_12_1.addActionListener(this.myActionController);                                        // ActionListener anmelden
            this.button_12_3.setText("TabellenDaten in KiBase abspeichern");
            this.button_12_3.addActionListener(this.myActionController);                                        // ActionListener anmelden
            buttonPanel.add(this.button_12_1);
            buttonPanel.add(this.button_12_3);
            this.panel.add(buttonPanel, BorderLayout.SOUTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Layout-Panel sichtbar machen
            this.panel.setVisible(true);
            // umschreiben
            this.validate();
            this.pack();
            this.setResizable(true);
        }
        // *************************************************************************************************************************************************************************
        // alle TabellenFelder wurden eingetragen und werden zur Kontrolle in einer Tabelle ausgegeben
        // *************************************************************************************************************************************************************************
        else if(pFrame == 3) {
            // FensterTitel wird geändert
            this.setTitle("Tabelle " + this.label_2_2.getText() + " wurde in Tabelle KiBase eingetragen:");
            // LayoutPanel unsichtbar machen
            this.panel.setVisible(false);
            // Alle Komponenten vom LayoutPanel löschen
            this.panel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Layout setzen
            this.panel.setLayout(new BorderLayout());
            this.panel.add(this.label_0_1, BorderLayout.CENTER);
            // ButtonZeile
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1,2));
            this.button_12_1.setText("Neue Tabelle eingeben");
            this.button_12_1.addActionListener(this.myActionController);                                        // ActionListener anmelden
            this.button_12_3.setText("DatenBank kiPferL updaten");
            this.button_12_3.addActionListener(this.myActionController);                                        // ActionListener anmelden
            buttonPanel.add(this.button_12_1);
            buttonPanel.add(this.button_12_3);
            this.panel.add(buttonPanel, BorderLayout.SOUTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            this.setSize(800,200);
            // Layout-Panel sichtbar machen
            this.panel.setVisible(true);
            // umschreiben
            this.validate();
        }
        // *************************************************************************************************************************************************************************
        // DB wurde upgedatet
        // *************************************************************************************************************************************************************************
        else if(pFrame == 4) {
            // FensterTitel wird geändert
            this.setTitle("KiPferL-DatenBank wurde upgedatet: ");
            // LayoutPanel unsichtbar machen
            this.panel.setVisible(false);
            // Alle Komponenten vom LayoutPanel löschen
            this.panel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Layout setzen
            this.panel.setLayout(new BorderLayout());
            this.panel.add(this.label_0_1, BorderLayout.CENTER);
            // ButtonZeile
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1,2));
            this.button_12_1.setText("Neue Tabelle eingeben");
            this.button_12_1.addActionListener(this.myActionController);                                        // ActionListener anmelden
            this.button_12_4.setText("Schließen");
            this.button_12_4.addActionListener(this.myActionController);                                        // ActionListener anmelden
            buttonPanel.add(this.button_12_1);
            buttonPanel.add(this.button_12_4);
            this.panel.add(buttonPanel, BorderLayout.SOUTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            this.setSize(800,200);
            // Layout-Panel sichtbar machen
            this.panel.setVisible(true);
            // umschreiben
            this.validate();
        }
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // JTable Zellen editieren
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
//    private void editDebugData() {
//        System.out.println(this.myTable.getSelectedRow());
//        System.out.println(this.myTable.getSelectedColumn());
//    }
}
