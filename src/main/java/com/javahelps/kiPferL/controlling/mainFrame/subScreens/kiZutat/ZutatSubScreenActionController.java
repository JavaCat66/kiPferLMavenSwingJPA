package com.javahelps.kiPferL.controlling.mainFrame.subScreens.kiZutat;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenActionAdapter;
import com.javahelps.kiPferL.jpa.kiParameter.lagerFaehigkeit.LagerFaehigkeitJPA;
import com.javahelps.kiPferL.jpa.kiZutat.ZutatJPA;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.lagerFaehigkeit.LagerFaehigKeit;
import com.javahelps.kiPferL.model.kiTableObject.kiZutat.Zutat;
import com.javahelps.kiPferL.model.kiUtils.kiStringUtils.KiStringUtils;
import com.javahelps.kiPferL.viewing.mainFrame.MyComponentItem;
import com.javahelps.kiPferL.viewing.mainFrame.MainFrame;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenShowFrame;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiZutat.ZutatFormPanel;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiZutat.ZutatListPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ZutatSubScreenActionController extends SubScreenActionAdapter {

    // HauptBildschirm
    MainFrame myFrame;
    // AusgabeBildschirm
    SubScreenShowFrame showFrame;
    // Ausgewählte ParameterGruppe
    private final String selectedEinkaufsKategorieId, selectedEinkaufsKategorieKuerzel;
    // Rahmen (Konstante)
    private static final Border lineBorderForms = new LineBorder(Color.BLACK, 1), lineBorderForms1 = new LineBorder(Color.RED, 1);
    // div. Font- und ColorObjekte
    private static final Font textFont3 = new Font("Arial", Font.BOLD, 16);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor (auszulesende bzw. zu editierende Komponenten werden übergeben)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public ZutatSubScreenActionController
        (MainFrame myFrame, JComboBox<String> selectGruppenBox, JLabel dialogLabel, JLabel idLabel, JTextField bezeichnungTextField, JTextArea infoTextTextArea,
         JButton formButton, ZutatListPanel listPanel, ZutatFormPanel formPanel, String selectedEinkaufsKategorieId,
         String selectedEinkaufsKategorieKuerzel) {
        // Aufruf des Konstruktors der SuperKlasse SubScreenActionAdapter, welche den ActionListener implementiert
        super(listPanel, formPanel, selectGruppenBox, dialogLabel, idLabel, bezeichnungTextField, infoTextTextArea, formButton);

        this.myFrame = myFrame;
        this.selectedEinkaufsKategorieId = selectedEinkaufsKategorieId;
        this.selectedEinkaufsKategorieKuerzel = selectedEinkaufsKategorieKuerzel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        // Farbe und Schrift für Warnhinweise setzen
        Color textColorSuccess = new Color(34,139,34);

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // FormularButton abgesendet        -->         in DB abspeichern oder ändern
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        if(o == this.getFormButton()) {
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Überprüfung der Eingaben
            int fehler = 0;

            String bezeichnung = this.getBezeichnungTextField().getText();
            String infoText = this.getInfoTextTextArea().getText();
            String masseinheitRezept =
                    ((MyComponentItem<String>) ((ZutatFormPanel) this.getFormPanel()).getMasseinheitRezept().getSelectedItem()).getKeyID();
            String masseinheitHandel =
                    ((MyComponentItem<String>) ((ZutatFormPanel) this.getFormPanel()).getMasseinheitHandel().getSelectedItem()).getKeyID();
            String lagerFaehigkeitDauer =
                    ((MyComponentItem<String>) ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitDauer().getSelectedItem()).getKeyID();
            String lagerFaehigkeitTemperatur =
                    ((MyComponentItem<String>) ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitTemperatur().getSelectedItem()).getKeyID();
            String lagerFaehigkeitLicht =
                    ((MyComponentItem<String>) ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitLicht().getSelectedItem()).getKeyID();
            String lagerFaehigkeitLuftfeuchtigkeit =
                    ((MyComponentItem<String>) ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitLuftfeuchtigkeit().getSelectedItem()).getKeyID();
            String kleinsteVerkaufsEinheit = ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().getText();
            double kleinsteVerkaufsEinheitD = 0.0;
            String kommissionierungsEinheit = ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().getText();
            double kommissionierungsEinheitD = 0.0;
            String kiloGrammAequivalent = ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().getText();
            double kiloGrammAequivalentD = 0.0;
            String naehrWertProHundertGramm = ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().getText();
            int naehrWertProHundertGrammI = 0;
            String brotEinheitenProHundertGramm = ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().getText();
            double brotEinheitenProHundertGrammD = 0.0;
            ArrayList<String> kiParameterAL = new ArrayList<>();
            if (!((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().isSelectionEmpty()) {
                int minIndex = ((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().getMinSelectionIndex();
                int maxIndex = ((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().isSelectedIndex(i)) {
                        kiParameterAL.add(((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().getModel().getElementAt(i).getKeyID());
                    }
                }
            }
            StringBuilder kiParameterALS = new StringBuilder();
            kiParameterAL.forEach(s -> {
                kiParameterALS.append(s);
                kiParameterALS.append(",");
            });
            ArrayList<String> kiParameterDT = new ArrayList<>();
            if (!((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().isSelectionEmpty()) {
                int minIndex = ((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().getMinSelectionIndex();
                int maxIndex = ((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().isSelectedIndex(i)) {
                        kiParameterDT.add(((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().getModel().getElementAt(i).getKeyID());
                    }
                }
            }
            StringBuilder kiParameterDTS = new StringBuilder();
            kiParameterDT.forEach(s -> {
                kiParameterDTS.append(s);
                kiParameterDTS.append(",");
            });

            if(bezeichnung.isEmpty()) {
                fehler = 1;
            } else if(bezeichnung.length() > 50) {
                fehler = 2;
            } else if(infoText.length() > 250) {
                fehler = 3;
            }
            if(kleinsteVerkaufsEinheit.isEmpty()) {
                fehler = 4;
            } else {
                try {
                   if(kleinsteVerkaufsEinheit.contains(",")) {
                       kleinsteVerkaufsEinheit = kleinsteVerkaufsEinheit.replace(",", ".");
                   }
                   kleinsteVerkaufsEinheitD = Double.parseDouble(kleinsteVerkaufsEinheit);
                } catch (NumberFormatException nume) {
                    fehler = 5;
                }
            }
            if(kommissionierungsEinheit.isEmpty()) {
                fehler = 6;
            } else {
                try {
                    if(kommissionierungsEinheit.contains(",")) {
                        kommissionierungsEinheit = kommissionierungsEinheit.replaceAll(",", ".");
                    }
                    kommissionierungsEinheitD = Double.parseDouble(kommissionierungsEinheit);
                } catch (NumberFormatException nume) {
                    fehler = 7;
                }
            }
            if(kiloGrammAequivalent.isEmpty()) {
                fehler = 8;
            } else {
                try {
                    if(kiloGrammAequivalent.contains(",")) {
                        kiloGrammAequivalent = kiloGrammAequivalent.replaceAll(",", ".");
                    }
                    kiloGrammAequivalentD = Double.parseDouble(kiloGrammAequivalent);
                } catch (NumberFormatException nume) {
                    fehler = 9;
                }
            }
            if(naehrWertProHundertGramm.isEmpty()) {
                fehler = 10;
            } else {
                try {
                    if(naehrWertProHundertGramm.contains(",")) {
                        naehrWertProHundertGramm = naehrWertProHundertGramm.replaceAll(",", ".");
                    }
                    naehrWertProHundertGrammI = (int) Math.round(Double.parseDouble(naehrWertProHundertGramm));
                } catch (NumberFormatException nume) {
                    fehler = 11;
                }
            }
            if(brotEinheitenProHundertGramm.isEmpty()) {
                fehler = 12;
            } else {
                try {
                    if(brotEinheitenProHundertGramm.contains(",")) {
                        brotEinheitenProHundertGramm = brotEinheitenProHundertGramm.replaceAll(",", ".");
                    }
                    brotEinheitenProHundertGrammD = Double.parseDouble(brotEinheitenProHundertGramm);
                } catch (NumberFormatException nume) {
                    fehler = 13;
                }
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Fehler bei der Eingabe
            String fehlerText;
            if(fehler > 0) {
                this.getDialogLabel().setForeground(Color.RED);
                this.getDialogLabel().setFont(textFont3);
                if(fehler == 13) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().requestFocusInWindow();
                    fehlerText = "BE/100g bitte numerisch eingeben";
                } else if (fehler == 12) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().requestFocusInWindow();
                    fehlerText = "BE/100g ist leer";
                } else if (fehler == 11) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "kCal/100g bitte numerisch eingeben";
                } else if (fehler == 10) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "kCal/100g ist leer";
                } else if (fehler == 9) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "kgÄquivalent bitte numerisch eingeben";
                } else if (fehler == 8) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "kgÄquivalent ist leer";
                } else if (fehler == 7) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "KommissionierungsEH bitte numerisch eingeben";
                } else if (fehler == 6) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "KommissionierungsEH ist leer";
                } else if (fehler == 5) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "Kleinste VerkaufsEH bitte numerisch eingeben";
                } else if (fehler == 4) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "Kleinste VerkaufsEH ist leer";
                } else if (fehler == 3) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms1);
                    this.getInfoTextTextArea().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().requestFocusInWindow();
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "InfoText ist zu lang";
                } else if (fehler == 2) {
                    this.getBezeichnungTextField().setBorder(lineBorderForms1);
                    this.getBezeichnungTextField().requestFocusInWindow();
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms1);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "Bezeichnung ist zu lang";
                } else {
                    this.getBezeichnungTextField().setBorder(lineBorderForms1);
                    this.getBezeichnungTextField().requestFocusInWindow();
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                    ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                    fehlerText = "Bezeichnung ist leer";
                }
                // Fehler- bzw. ErfolgsText auf das FehlerLabel setzen (Meldung oder leer)
                ((ZutatFormPanel)this.getFormPanel()).getDialogLabel().setText(fehlerText);
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // kein Fehler
            else {
                // ID vom ID-AnzeigeLabel abfragen
                int x = Integer.parseInt(this.getIdLabel().getText().substring(2));
                // Zutat-Objekt erzeugen...
                Zutat myZutat = new Zutat
                    (x,
                    "",
                    "",
                    bezeichnung,
                    infoText,
                    this.selectedEinkaufsKategorieKuerzel.toUpperCase(),
                    this.selectedEinkaufsKategorieId,
                    masseinheitRezept,
                    masseinheitHandel,
                    kleinsteVerkaufsEinheitD,
                    kommissionierungsEinheitD,
                    kiloGrammAequivalentD,
                    naehrWertProHundertGrammI,
                    brotEinheitenProHundertGrammD,
                    lagerFaehigkeitDauer,
                    lagerFaehigkeitTemperatur,
                    lagerFaehigkeitLicht,
                    lagerFaehigkeitLuftfeuchtigkeit,
                    kiParameterALS.toString(),
                    kiParameterDTS.toString());
                // ...und in DB neu eintragen...
                if(!((ZutatFormPanel)this.getFormPanel()).isFormEdit()) {
                    fehlerText = myZutat.doInsertIntoZutat();
                }
                // ...oder änderen
                else {
                    fehlerText = myZutat.doUpdateZutat();
                }
                // ****************************************************************************************************************************************************************
                // etwaiger ÄnderungsModus wird zurückgesetzt, nächste ID abgefragt
                if(((ZutatFormPanel)this.getFormPanel()).isFormEdit()) {
                    // boolean wieder auf Neueintrag setzen
                    ((ZutatFormPanel)this.getFormPanel()).setFormEdit(false);
                    // letzte ID der gewählten ParameterGruppe abfragen
                    x = ZutatJPA.findMaxIdZutat(
                            MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedEinkaufsKategorieId);
                }
                // bei Erfolgsmeldung wird die Schriftfarbe auf Grün gesetzt
                if(fehlerText.equals("Zutat wurde eingetragen.") || fehlerText.equals("Zutat wurde geändert.")) {
                    ((ZutatFormPanel)this.getFormPanel()).getDialogLabel().setForeground(textColorSuccess);
                }
                // neue ID erzeugen und auf das entsprechende Label setzen
                this.getIdLabel().setText(KiStringUtils.idIncrement(this.selectedEinkaufsKategorieKuerzel, ++x));
                // FormFelder zurücksetzen
                this.getBezeichnungTextField().setText("");
                this.getBezeichnungTextField().setBorder(lineBorderForms);
                // Fokus in das erste TextFeld stellen (Bezeichnung)
                this.getBezeichnungTextField().requestFocusInWindow();
                this.getInfoTextTextArea().setText("");
                this.getInfoTextTextArea().setBorder(lineBorderForms);
                ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setText("");
                ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setBorder(lineBorderForms);
                ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setText("");
                ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setBorder(lineBorderForms);
                ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setText("");
                ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setBorder(lineBorderForms);
                ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setText("");
                ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setBorder(lineBorderForms);
                ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setText("");
                ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setBorder(lineBorderForms);
                ((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().clearSelection();
                ((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().clearSelection();
                this.getFormButton().setText("Zutat eintragen");
                // Fehler- bzw. ErfolgsText auf das FehlerLabel setzen (Meldung oder leer)
                ((ZutatFormPanel)this.getFormPanel()).getDialogLabel().setText(fehlerText);
                // ListObjekte neu abfragen
                ((ZutatListPanel)this.getListPanel()).findListObjects();
                // Layout aktualisieren
                this.myFrame.changeLayout(3, ZutatListPanel.getSeite());
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // VorwärtsButton abgesendet        -->         eine Seite weiter blättern
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("forward")) {
            ZutatListPanel.setSeite(ZutatListPanel.getSeite() + 1);
            this.myFrame.changeLayout(3, ZutatListPanel.getSeite());
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // RückwärtsButton abgesendet        -->         eine Seite zurück blättern
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("backward")) {
            ZutatListPanel.setSeite(ZutatListPanel.getSeite() - 1);
            this.myFrame.changeLayout(3, ZutatListPanel.getSeite());
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // ListButton show abgesendet       -->     KiParameterDaten aus DB abfragen und am SubScreenShowFrame ausgeben
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("show")) {
            String id = ((JButton) o).getName().substring(4);
            Zutat zutat = ZutatJPA.findByIdAndEinkaufsKategorieId(MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedEinkaufsKategorieId, id);
            if (zutat != null && !zutat.getBezeichnung().isEmpty()) {
                this.showFrame = new SubScreenShowFrame();
                this.showFrame.changeLayout(3, this.selectedEinkaufsKategorieId, id, this.myFrame);
            }
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // ListButton edit abgesendet       -->     KiParameterDaten aus DB abfragen und in FormularFelder stellen
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("edit")) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
            ((DecimalFormat) numberFormat).applyPattern("##0.##");
            String id = ((JButton) o).getName().substring(4);
            Zutat zutat = ZutatJPA.findByIdAndEinkaufsKategorieId(MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedEinkaufsKategorieId, id);
            if (zutat != null && !zutat.getBezeichnung().isEmpty()) {
                // Editieren wird auf true gesetzt
                ((ZutatFormPanel)this.getFormPanel()).setFormEdit(true);
                // ParameterDaten werden ausgelesen und in die entsprechenden TextFelder gestellt
                this.getIdLabel().setText(id);
                this.getBezeichnungTextField().setText(zutat.getBezeichnung());
                this.getInfoTextTextArea().setText(zutat.getInfoText());
                // Allergene
                String[] temp = zutat.getKiParameterAL().split(",", -2);
                int[] tempi = new int[temp.length];
                for(int i = 0; i < temp.length - 1; i++) {
                    for (int j = 0; j < ((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().getModel().getSize(); j++) {
                        if (((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().getModel().getElementAt(j).getKeyID().equals(temp[i])) {
                            tempi[i] = j;
                            break;
                        }
                    }
                }
                ((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                ((ZutatFormPanel)this.getFormPanel()).getKiParameterAL().setSelectedIndices(tempi);
                // Diättauglichkeiten
                temp = zutat.getKiParameterDT().split(",", -2);
                tempi = new int[temp.length];
                for(int i = 0; i < temp.length - 1; i++) {
                    for (int j = 0; j < ((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().getModel().getSize(); j++) {
                        if (((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().getModel().getElementAt(j).getKeyID().equals(temp[i])) {
                            tempi[i] = j;
                            break;
                        }
                    }
                }
                ((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                ((ZutatFormPanel)this.getFormPanel()).getKiParameterDT().setSelectedIndices(tempi);
                // Maßeinheit Rezept
                for(int i = 0; i < ((ZutatFormPanel)this.getFormPanel()).getMasseinheitRezept().getModel().getSize(); i++) {
                    MyComponentItem<String> myComponentItem = ((ZutatFormPanel)this.getFormPanel()).getMasseinheitRezept().getModel().getElementAt(i);
                    if(myComponentItem.getKeyID().equals(zutat.getMasseinheitRezept())) {
                        ((ZutatFormPanel)this.getFormPanel()).getMasseinheitRezept().setSelectedItem(myComponentItem);
                    }
                }
                // Maßeinheit Handel
                for(int i = 0; i < ((ZutatFormPanel)this.getFormPanel()).getMasseinheitHandel().getModel().getSize(); i++) {
                    MyComponentItem<String> myComponentItem = ((ZutatFormPanel)this.getFormPanel()).getMasseinheitHandel().getModel().getElementAt(i);
                    if(myComponentItem.getKeyID().equals(zutat.getMasseinheitHandel())) {
                        ((ZutatFormPanel)this.getFormPanel()).getMasseinheitHandel().setSelectedItem(myComponentItem);
                    }
                }
                // Numerische Werte
                ((ZutatFormPanel)this.getFormPanel()).getKleinsteVerkaufsEinheit().setText(numberFormat.format(zutat.getKleinsteVerkaufsEinheit()));
                ((ZutatFormPanel)this.getFormPanel()).getKommissionierungsEinheit().setText(numberFormat.format(zutat.getKommissionierungsEinheit()));
                ((ZutatFormPanel)this.getFormPanel()).getKiloGrammAequivalent().setText(numberFormat.format(zutat.getKiloGrammAequivalent()));
                ((ZutatFormPanel)this.getFormPanel()).getNaehrWertProHundertGramm().setText(numberFormat.format(zutat.getNaehrWertProHundertGramm()));
                ((ZutatFormPanel)this.getFormPanel()).getBrotEinheitenProHundertGramm().setText(numberFormat.format(zutat.getBrotEinheitenProHundertGramm()));
                // Lagerfähigkeit
                LagerFaehigKeit myLagerFaehigKeit =
                        LagerFaehigkeitJPA.findLagerFaehigkeitById(MainFrameWindowAdapter.getEntityManagerFactory(), zutat.getLagerFaehigKeit());
                // LagerfähigkeitDauer
                for(int i = 0; i < ((ZutatFormPanel)this.getFormPanel()).getLagerfaehigkeitDauer().getModel().getSize(); i++) {
                    if(myLagerFaehigKeit.getLagerDauer() != null) {
                        MyComponentItem<String> myComponentItem = ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitDauer().getModel().getElementAt(i);
                        if (myComponentItem.getKeyID().equals(myLagerFaehigKeit.getLagerDauer())) {
                            ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitDauer().setSelectedItem(myComponentItem);
                        }
                    }
                }
                // LagerfähigkeitTemperatur
                for(int i = 0; i < ((ZutatFormPanel)this.getFormPanel()).getLagerfaehigkeitTemperatur().getModel().getSize(); i++) {
                    if(myLagerFaehigKeit.getLagerTemperatur() != null) {
                        MyComponentItem<String> myComponentItem = ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitTemperatur().getModel().getElementAt(i);
                        if (myComponentItem.getKeyID().equals(myLagerFaehigKeit.getLagerTemperatur())) {
                            ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitTemperatur().setSelectedItem(myComponentItem);
                        }
                    }
                }
                // LagerfähigkeitLicht
                for(int i = 0; i < ((ZutatFormPanel)this.getFormPanel()).getLagerfaehigkeitLicht().getModel().getSize(); i++) {
                    if(myLagerFaehigKeit.getLagerBeleuchtung() != null) {
                        MyComponentItem<String> myComponentItem = ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitLicht().getModel().getElementAt(i);
                        if (myComponentItem.getKeyID().equals(myLagerFaehigKeit.getLagerBeleuchtung())) {
                            ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitLicht().setSelectedItem(myComponentItem);
                        }
                    }
                }
                // LagerfähigkeitLuftFeuchtigKeit
                for(int i = 0; i < ((ZutatFormPanel)this.getFormPanel()).getLagerfaehigkeitLuftfeuchtigkeit().getModel().getSize(); i++) {
                    if(myLagerFaehigKeit.getLagerLuftFeuchtigkeit() != null) {
                        MyComponentItem<String> myComponentItem = ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitLuftfeuchtigkeit().getModel().getElementAt(i);
                        if (myComponentItem.getKeyID().equals(myLagerFaehigKeit.getLagerLuftFeuchtigkeit())) {
                            ((ZutatFormPanel) this.getFormPanel()).getLagerfaehigkeitLuftfeuchtigkeit().setSelectedItem(myComponentItem);
                        }
                    }
                }
                // Buttontext ändern
                this.getFormButton().setText("Zutat ändern");
            }
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // ListButton delete abgesendet       -->     KiParameterDaten aus DB abfragen und am SubScreenShowFrame ausgeben
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("delete")) {
            String id = ((JButton) o).getName().substring(6);
            Zutat zutat = ZutatJPA.findByIdAndEinkaufsKategorieId(MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedEinkaufsKategorieId, id);
            if (zutat != null && !zutat.getBezeichnung().isEmpty()) {
                this.showFrame = new SubScreenShowFrame();
                this.showFrame.changeLayout(13, this.selectedEinkaufsKategorieId, id, this.myFrame);
            }
        }
    }
}
