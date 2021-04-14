package com.javahelps.kiPferL.controlling.mainFrame;

import com.javahelps.kiPferL.jpa.kiUser.KiUserJPA;
import com.javahelps.kiPferL.model.kiTableObject.kiPerson.kiUser.KiUser;
import com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils;
import com.javahelps.kiPferL.viewing.establishKiDB.EstablishKiDBFrame;
import com.javahelps.kiPferL.viewing.mainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ActionListener für Login- und StartScreen
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.15
 * @since 1.14
 */
public class MainFrameActionController implements ActionListener {

    private final MainFrame myFrame;
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LoginBildschirm
    private final JTextField loginTextField_1_1;
    private final JPasswordField loginPasswordField_2_1;
    private final JButton loginButton_3_1;
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // StartBildschirm
    private final JButton startButton_0_0, startButton_0_1, startButton_1_0, startButton_1_1, startButton_2_0, startButton_2_1;
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Menüleiste in UnterProgrammen
    private final JLabel label_north;
    private final JButton menueButton_0, menueButton_1, menueButton_2, menueButton_3, menueButton_4, menueButton_5;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public MainFrameActionController
            (MainFrame myFrame, JLabel label_north, JTextField loginTextField_1_1, JPasswordField loginPasswordField_2_1, JButton loginButton_3_1, JButton startButton_0_0,
             JButton startButton_0_1, JButton startButton_1_0, JButton startButton_1_1, JButton startButton_2_0, JButton startButton_2_1, JButton menueButton_0,
             JButton menueButton_1, JButton menueButton_2, JButton menueButton_3, JButton menueButton_4, JButton menueButton_5) {

        this.myFrame = myFrame;
        this.label_north = label_north;
        this.loginTextField_1_1 = loginTextField_1_1;
        this.loginPasswordField_2_1 = loginPasswordField_2_1;
        this.loginButton_3_1 = loginButton_3_1;
        this.startButton_0_0 = startButton_0_0;
        this.startButton_0_1 = startButton_0_1;
        this.startButton_1_0 = startButton_1_0;
        this.startButton_1_1 = startButton_1_1;
        this.startButton_2_0 = startButton_2_0;
        this.startButton_2_1 = startButton_2_1;
        this.menueButton_0 = menueButton_0;
        this.menueButton_1 = menueButton_1;
        this.menueButton_2 = menueButton_2;
        this.menueButton_3 = menueButton_3;
        this.menueButton_4 = menueButton_4;
        this.menueButton_5 = menueButton_5;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // EventHandling
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        // Farbe und Schrift für Warnhinweise setzen
        Font textFontLogin1 = new Font("Arial", Font.BOLD, 18);

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // LoginSeite Form abgesendet
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        if(o == this.loginButton_3_1) {
            // UserDaten überprüfen und ggf. serialisieren, damit sie jederzeit abgefragt werden können (Dokumentationsfelder DB)
            String userName = this.loginTextField_1_1.getText();
            String passWort = String.valueOf(this.loginPasswordField_2_1.getPassword());
            try {
                KiUser kiUser = KiUserJPA.findByUserNameAndPassword(MainFrameWindowAdapter.getEntityManagerFactory(), userName, passWort);
                if(kiUser != null) {
                    // KiUserObject serialisieren, eventuelle FehlerMeldungen löschen
                    System.out.println(KiSerializationUtils.doSerializationKiUser("kiSerializationFiles/kiUser/", "kiUser.ser", kiUser));
                    this.label_north.setText("");
                    // zum StartBildschirm wechseln
                    this.myFrame.changeLayout(1);
                }
                // falsche UserDaten
                else {
                    this.label_north.setFont(textFontLogin1);
                    this.label_north.setForeground(Color.MAGENTA);
                    this.label_north.setHorizontalAlignment(SwingConstants.CENTER);
                    this.label_north.setText("Falsche Login-Angaben. Bitte nochmal überprüfen!");
                }
            } catch (Exception e1) {
                System.out.println("FEHLER 1 controlling.mainFrame.MainFrameActionController.Login: " + e1.toString());
            }
        }

        // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
        // PARAMETER
        // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // StartSeite oder Menü Unterprogramm ParameterButton abgesendet, ParameterBildschirm aufrufen
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o == this.startButton_1_1 || o == this.menueButton_3) {
            this.myFrame.changeLayout(2);
        }
        // *************************************************************************************************************************************************************************
        // StartSeite oder Menü Unterprogramm ZutatenButton abgesendet
        // *************************************************************************************************************************************************************************
        else if(o == this.startButton_1_0 || o == this.menueButton_2) {
            this.myFrame.changeLayout(3);
        }
        // *************************************************************************************************************************************************************************
        // StartSeite oder Menü Unterprogramm RezeptButton abgesendet
        // *************************************************************************************************************************************************************************
        else if(o == this.startButton_0_1 || o == this.menueButton_1) {
            this.myFrame.changeLayout(4);
        }
        // *************************************************************************************************************************************************************************
        // StartSeite oder Menü Unterprogramm KochbuchButton abgesendet
        // *************************************************************************************************************************************************************************
        else if(o == this.startButton_0_0 || o == this.menueButton_0) {
            this.myFrame.changeLayout(5);
        }
        // *************************************************************************************************************************************************************************
        // StartSeite oder Menü Unterprogramm WerkstattButton abgesendet
        // *************************************************************************************************************************************************************************
        else if(o == this.startButton_2_0 || o == this.menueButton_4) {
            new EstablishKiDBFrame();
        }
        // *************************************************************************************************************************************************************************
        // StartSeite oder Menü Unterprogramm LogoutButton abgesendet
        // *************************************************************************************************************************************************************************
        else if(o == this.startButton_2_1 || o == this.menueButton_5) {
            MainFrameWindowAdapter.getEntityManagerFactory().close();
            System.exit(0);
        }
    }
}
