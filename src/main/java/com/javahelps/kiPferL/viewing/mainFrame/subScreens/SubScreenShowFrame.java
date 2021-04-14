package com.javahelps.kiPferL.viewing.mainFrame.subScreens;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA;
import com.javahelps.kiPferL.jpa.kiParameter.lagerFaehigkeit.LagerFaehigkeitJPA;
import com.javahelps.kiPferL.jpa.kiZutat.ZutatJPA;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.lagerFaehigkeit.LagerFaehigKeit;
import com.javahelps.kiPferL.model.kiTableObject.kiZutat.Zutat;
import com.javahelps.kiPferL.viewing.mainFrame.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Extrafenster zur Ausgabe von Details der Einträge von SubProgrammen
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.17
 * @since 1.14
 */
public class SubScreenShowFrame extends JFrame {

    private final JPanel mainPanel;
    private JLabel showLabel;
    private final JButton deleteButton;

    private static final Font textFont2 = new Font("Arial", Font.PLAIN, 16), textFont3 = new Font("Arial", Font.BOLD, 16);
    private static final Color buttonColor = new Color(204,255,204);
    private static final Border lineBorderForms = new LineBorder(Color.BLACK, 1);
    private static final EmptyBorder panellEmptyBorder = new EmptyBorder(10, 10, 10, 10);

    public SubScreenShowFrame() {

        this.deleteButton = new JButton();
        this.deleteButton.setBackground(buttonColor);
        this.deleteButton.setFont(textFont3);
        this.deleteButton.setBorder(lineBorderForms);

        this.showLabel = new JLabel();
        this.showLabel.setOpaque(false);
        this.showLabel.setFont(textFont2);

        this.mainPanel = new JPanel();
        this.mainPanel.setBackground(Color.WHITE);
        this.mainPanel.add(showLabel);

        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);

        // RahmenEigenschaften definieren
        this.setTitle("");
        this.setLocation(400,60);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        // *************************************************************************************************************************************************************************
        // Rahmen sichtbar machen
        this.setVisible(true);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Methode zum Editieren des Screens
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public void changeLayout(int pFrame, String gruppeOrId, String id, MainFrame myFrame) {

        StringBuilder text = new StringBuilder();
        text.append("<HTML><TABLE><TR><TD width=570pt><TABLE width=100% cellpadding=5 cellspacing=0>");
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Parameter
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // alle Infos ausgeben
        if(pFrame == 2) {
            this.setTitle("Ausgabe Parameter:");
            KiParameter myParameter = KiParameterJPA.findByIdAndParametergruppe(MainFrameWindowAdapter.getEntityManagerFactory(), gruppeOrId, id);
            if(myParameter != null) {
                text.append("<TR><TD width=30%><B>Präfix:</B></TD><TD>");
                text.append(myParameter.getPraefix());
                text.append("</TD></TR><TR><TD><B>Parametergruppe:</B></TD><TD>");
                text.append(myParameter.getParameterGruppe());
                text.append("</TD></TR><TR><TD><B>ID:</B></TD><TD>");
                text.append(myParameter.getId());
                text.append("</TD></TR><TR><TD><B>Kürzel:</B></TD><TD>");
                text.append(myParameter.getKuerzel());
                text.append("</TD></TR><TR><TD><B>Bezeichnung:</B></TD><TD>");
                text.append(myParameter.getBezeichnung());
                text.append("</TD></TR><TR><TD><B>Infotext:</B></TD><TD>");
                text.append(myParameter.getInfoText());
                text.append("</TD></TR><TR><TD><B>Eingabedatum:</B></TD><TD>");
                text.append(myParameter.getDateOfCreation());
                text.append("</TD></TR><TR><TD><B>Änderung:</B></TD><TD>");
                text.append(myParameter.getTimeStampChange());
                text.append("</TD></TR><TR><TD><B>Modul:</B></TD><TD>");
                text.append(myParameter.getModul());
                text.append("</TD></TR><TR><TD><B>Programm:</B></TD><TD>");
                text.append(myParameter.getProgram());
                text.append("</TD></TR><TR><TD><B>Method:</B></TD><TD>");
                text.append(myParameter.getMethod());
                text.append("</TD></TR><TR><TD><B>GruppenID:</B></TD><TD>");
                text.append(myParameter.getGroupID());
                text.append("</TD></TR><TR><TD><B>Mitglied:</B></TD><TD>");
                text.append(myParameter.getUser());
                text.append("</TD></TR><TR><TD><B>Status:</B></TD><TD>");
                text.append(myParameter.getStatus());
                text.append("</TD></TR></TABLE></TD></TR></TABLE></HTML>");
            }
            this.setSize(600,600);
            this.showLabel.setText(text.toString());
        }
        // Parameter löschen
        else if(pFrame == 12) {
            this.setTitle("Parameter löschen:");
            // HauptPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // TextLabel neu erstellen
            this.showLabel = new JLabel();
            this.showLabel.setText("Wollen Sie den Parameter wirklich löschen?");
            this.showLabel.setHorizontalAlignment(SwingConstants.CENTER);
            this.showLabel.setOpaque(false);
            this.showLabel.setFont(textFont2);
            // Text auf Button schreiben
            this.deleteButton.setText("Parameter löschen");
            this.deleteButton.addActionListener(e -> {
                System.out.println(KiParameterJPA.deleteKiParameterByParametergruppeAndId(MainFrameWindowAdapter.getEntityManagerFactory(), gruppeOrId, id));
                // -------------------------------------------------------------------------------------------------------------------------------------------------------------
                // Layout neu aufbauen
                myFrame.changeLayout(2);
                // Fenster schließen
                SubScreenShowFrame.this.dispose();
            });
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // neues Layout für das HauptPanel
            this.mainPanel.setLayout(new GridLayout(2,1));
            this.mainPanel.setBorder(panellEmptyBorder);
            this.mainPanel.add(this.showLabel);
            this.mainPanel.add(this.deleteButton);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // macht, dass bei ENTER das Formular abgeschickt wird und man nicht auf den Button klicken muss
            this.getRootPane().setDefaultButton(this.deleteButton);
            // neue Größe
            this.setSize(600,120);
            // umschreiben
            this.validate();
        }
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Zutat
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // alle Infos ausgeben
        if(pFrame == 3) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
            ((DecimalFormat) numberFormat).applyPattern("##0.##");
            this.setTitle("Ausgabe Zutat:");
            Zutat myZutat = ZutatJPA.findByIdAndEinkaufsKategorieId(MainFrameWindowAdapter.getEntityManagerFactory(), gruppeOrId, id);
            if(myZutat != null) {
                text.append("<TR><TD width=30%><B>ID:</B></TD><TD>");
                text.append(myZutat.getId());
                text.append("</TD></TR><TR><TD><B>Präfix:</B></TD><TD>");
                text.append(myZutat.getPraefix());
                text.append("</TD></TR><TR><TD><B>Bezeichnung:</B></TD><TD>");
                text.append(myZutat.getBezeichnung());
                text.append("</TD></TR><TR><TD><B>Infotext:</B></TD><TD>");
                text.append(myZutat.getInfoText());
                KiParameter myKiParameter;
                StringBuilder tempi = new StringBuilder();
                String[] temp = myZutat.getKiParameterAL().split(",", -2);
                for(int i = 0; i < temp.length - 1; i++) {
                    myKiParameter =
                            KiParameterJPA.findByIdAndParametergruppe
                                    (MainFrameWindowAdapter.getEntityManagerFactory(), "Allergen", temp[i]);
                    tempi.append(myKiParameter.getKuerzel());
                    if(i < temp.length - 2) {
                        tempi.append(", ");
                    }
                }
                text.append("</TD></TR><TR><TD><B>Allergene:</B></TD><TD>");
                text.append(tempi.toString());
                tempi = new StringBuilder();
                temp = myZutat.getKiParameterDT().split(",", -2);
                for(int i = 0; i < temp.length - 1; i++) {
                    myKiParameter =
                            KiParameterJPA.findByIdAndParametergruppe
                                    (MainFrameWindowAdapter.getEntityManagerFactory(), "DiätTauglichkeit", temp[i]);
                    tempi.append(myKiParameter.getBezeichnung());
                    if(i < temp.length - 2) {
                        tempi.append(", ");
                    }
                }
                text.append("</TD></TR><TR><TD><B>Diättauglichkeiten:</B></TD><TD>");
                text.append(tempi.toString());
                myKiParameter =
                        KiParameterJPA.findByIdAndParametergruppe
                                (MainFrameWindowAdapter.getEntityManagerFactory(), "Maßeinheit", myZutat.getMasseinheitRezept());
                text.append("</TD></TR><TR><TD><B>Masseinheit Rezept:</B></TD><TD>");
                text.append(myKiParameter.getBezeichnung());
                myKiParameter =
                        KiParameterJPA.findByIdAndParametergruppe
                                (MainFrameWindowAdapter.getEntityManagerFactory(), "Maßeinheit", myZutat.getMasseinheitHandel());
                text.append("</TD></TR><TR><TD><B>Masseinheit Handel:</B></TD><TD>");
                text.append(myKiParameter.getBezeichnung());
                text.append("</TD></TR><TR><TD><B>Kleinste Verkaufseinheit:</B></TD><TD>");
                text.append(numberFormat.format(myZutat.getKleinsteVerkaufsEinheit()));
                text.append("</TD></TR><TR><TD><B>Kommissionierungseinheit:</B></TD><TD>");
                text.append(numberFormat.format(myZutat.getKommissionierungsEinheit()));
                text.append("</TD></TR><TR><TD><B>Kilogrammäquivalent:</B></TD><TD>");
                text.append(numberFormat.format(myZutat.getKiloGrammAequivalent()));
                text.append("</TD></TR><TR><TD><B>Nährwert pro 100g:</B></TD><TD>");
                text.append(myZutat.getNaehrWertProHundertGramm());
                text.append("</TD></TR><TR><TD><B>Broteinheiten pro 100g:</B></TD><TD>");
                text.append(numberFormat.format(myZutat.getBrotEinheitenProHundertGramm()));
                LagerFaehigKeit myLagerFaehigKeit =
                        LagerFaehigkeitJPA.findLagerFaehigkeitById(MainFrameWindowAdapter.getEntityManagerFactory(), myZutat.getLagerFaehigKeit());
                tempi = new StringBuilder();
                if(myLagerFaehigKeit != null) {
                    myKiParameter =
                            KiParameterJPA.findByIdAndParametergruppe
                                    (MainFrameWindowAdapter.getEntityManagerFactory(), "Lagerdauer", myLagerFaehigKeit.getLagerDauer());
                    if (myKiParameter != null && !myKiParameter.getBezeichnung().equals("Keine Angabe")) {
                        tempi.append(myKiParameter.getBezeichnung());
                        tempi.append(" ");
                    }
                    myKiParameter =
                            KiParameterJPA.findByIdAndParametergruppe
                                    (MainFrameWindowAdapter.getEntityManagerFactory(), "Lagertemperatur", myLagerFaehigKeit.getLagerTemperatur());
                    if (myKiParameter != null && !myKiParameter.getBezeichnung().equals("Keine Angabe")) {
                        tempi.append(myKiParameter.getBezeichnung());
                        tempi.append(" ");
                    }
                    myKiParameter =
                            KiParameterJPA.findByIdAndParametergruppe
                                    (MainFrameWindowAdapter.getEntityManagerFactory(), "LagerBeleuchtung", myLagerFaehigKeit.getLagerBeleuchtung());
                    if (myKiParameter != null && !myKiParameter.getBezeichnung().equals("Keine Angabe")) {
                        tempi.append(myKiParameter.getBezeichnung());
                        tempi.append(" ");
                    }
                    myKiParameter =
                            KiParameterJPA.findByIdAndParametergruppe
                                    (MainFrameWindowAdapter.getEntityManagerFactory(), "LagerLuftFeuchtigkeit", myLagerFaehigKeit.getLagerLuftFeuchtigkeit());
                    if (myKiParameter != null && !myKiParameter.getBezeichnung().equals("Keine Angabe")) {
                        tempi.append(myKiParameter.getBezeichnung());
                        tempi.append(" ");
                    }
                }
                text.append("</TD></TR><TR><TD><B>Lagerfähigkeit:</B></TD><TD>");
                text.append(tempi.toString());
                text.append("</TD></TR><TR><TD><B>Eingabedatum:</B></TD><TD>");
                text.append(myZutat.getDateOfCreation());
                text.append("</TD></TR><TR><TD><B>Änderung:</B></TD><TD>");
                text.append(myZutat.getTimeStampChange());
                text.append("</TD></TR><TR><TD><B>Modul:</B></TD><TD>");
                text.append(myZutat.getModul());
                text.append("</TD></TR><TR><TD><B>Programm:</B></TD><TD>");
                text.append(myZutat.getProgram());
                text.append("</TD></TR><TR><TD><B>Method:</B></TD><TD>");
                text.append(myZutat.getMethod());
                text.append("</TD></TR><TR><TD><B>GruppenID:</B></TD><TD>");
                text.append(myZutat.getGroupID());
                text.append("</TD></TR><TR><TD><B>Mitglied:</B></TD><TD>");
                text.append(myZutat.getUser());
                text.append("</TD></TR><TR><TD><B>Status:</B></TD><TD>");
                text.append(myZutat.getStatus());
                text.append("</TD></TR></TABLE></TD></TR></TABLE></HTML>");
            }
            // neue Größe
            this.setSize(600,720);
            this.showLabel.setText(text.toString());
        }
        // Zutat löschen
        else if(pFrame == 13) {
            this.setTitle("Zutat löschen:");
            // HauptPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // TextLabel neu erstellen
            this.showLabel = new JLabel();
            this.showLabel.setText("Wollen Sie die Zutat wirklich löschen?");
            this.showLabel.setHorizontalAlignment(SwingConstants.CENTER);
            this.showLabel.setOpaque(false);
            this.showLabel.setFont(textFont2);
            // Text auf Button schreiben
            this.deleteButton.setText("Zutat löschen");
            this.deleteButton.addActionListener(e -> {
                System.out.println(ZutatJPA.deleteZutatByEinkaufsKategorieIdAndId(MainFrameWindowAdapter.getEntityManagerFactory(), gruppeOrId, id));
                // -------------------------------------------------------------------------------------------------------------------------------------------------------------
                // Layout neu aufbauen
                myFrame.changeLayout(3);
                // Fenster schließen
                SubScreenShowFrame.this.dispose();
            });
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // neues Layout für das HauptPanel
            this.mainPanel.setLayout(new GridLayout(2,1));
            this.mainPanel.setBorder(panellEmptyBorder);
            this.mainPanel.add(this.showLabel);
            this.mainPanel.add(this.deleteButton);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // macht, dass bei ENTER das Formular abgeschickt wird und man nicht auf den Button klicken muss
            this.getRootPane().setDefaultButton(this.deleteButton);
            // neue Größe
            this.setSize(600,120);
            // umschreiben
            this.validate();
        }
    }
}
