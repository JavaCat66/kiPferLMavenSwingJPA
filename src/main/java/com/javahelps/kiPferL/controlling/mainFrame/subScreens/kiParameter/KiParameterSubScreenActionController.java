package com.javahelps.kiPferL.controlling.mainFrame.subScreens.kiParameter;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenActionAdapter;
import com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA;
import com.javahelps.kiPferL.model.kiTableObject.KiTableObject;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiUtils.kiStringUtils.KiStringUtils;
import com.javahelps.kiPferL.viewing.mainFrame.MainFrame;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenShowFrame;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiParameter.KiParameterFormPanel;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiParameter.KiParameterListPanel;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiZutat.ZutatListPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ActionListener für den Parameter-SubScreen erbt von SubScreenActionAdapter
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.15
 * @since 1.14
 */
public class KiParameterSubScreenActionController extends SubScreenActionAdapter {

    // HauptBildschirm
    MainFrame myFrame;
    // AusgabeBildschirm
    SubScreenShowFrame showFrame;
    // Liste der ParameterGruppen, die ausgewählt werden können
    private final List<KiParameter> parameterGruppen;
    // Ausgewählte ParameterGruppe
    private final String selectedParameterGruppe, selectedParameterGruppenKuerzel;
    // FormularSpezifische Felder
    private final JTextField kuerzelTextField;
    // div. Font- und ColorObjekte
    private static final Font textFont2 = new Font("Arial", Font.PLAIN, 16), textFont3 = new Font("Arial", Font.BOLD, 16);
    // Rahmen (Konstante)
    private static final Border lineBorderForms = new LineBorder(Color.BLACK, 1), lineBorderForms1 = new LineBorder(Color.RED, 1);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor (auszulesende bzw. zu editierende Komponenten werden übergeben)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiParameterSubScreenActionController
            (MainFrame myFrame, JComboBox<String> selectGruppenBox, JLabel dialogLabel, JLabel idLabel, JTextField bezeichnungTextField,
             JTextArea infoTextTextArea, JButton formButton, JTextField kuerzelTextField,
             KiParameterListPanel listPanel, KiParameterFormPanel formPanel, List<KiParameter> parameterGruppen, String selectedParameterGruppe,
             String selectedParameterGruppenKuerzel) {
        // Aufruf des Konstruktors der SuperKlasse SubScreenActionAdapter, welche den ActionListener implementiert
        super(listPanel, formPanel, selectGruppenBox, dialogLabel, idLabel, bezeichnungTextField, infoTextTextArea, formButton);

        this.myFrame = myFrame;
        this.parameterGruppen = parameterGruppen;
        this.selectedParameterGruppe = selectedParameterGruppe;
        this.selectedParameterGruppenKuerzel = selectedParameterGruppenKuerzel;
        this.kuerzelTextField = kuerzelTextField;
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
            // Überprüfung der TextFeldEingaben
            int fehler = 0;
            String kuerzel = this.kuerzelTextField.getText();
            String bezeichnung = this.getBezeichnungTextField().getText();
            String infoText = this.getInfoTextTextArea().getText();
            if(kuerzel.isEmpty()) {
                fehler = 1;
            } else if(kuerzel.length() > 3) {
                fehler = 2;
            } else if(bezeichnung.isEmpty()) {
                fehler = 3;
            } else if(bezeichnung.length() > 50) {
                fehler = 4;
            } else if(infoText.length() > 250) {
                fehler = 5;
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Fehler bei der Eingabe
            String fehlerText;
            if(fehler > 0) {
                this.getDialogLabel().setForeground(Color.RED);
                this.getDialogLabel().setFont(textFont3);
                if(fehler == 5) {
                    this.kuerzelTextField.setBorder(lineBorderForms);
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms1);
                    this.getInfoTextTextArea().requestFocusInWindow();
                    fehlerText = "InfoText ist zu lang";
                } else if(fehler == 4) {
                    this.kuerzelTextField.setBorder(lineBorderForms);
                    this.getBezeichnungTextField().setBorder(lineBorderForms1);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().requestFocusInWindow();
                    fehlerText = "Bezeichnung ist zu lang";
                } else if(fehler == 3) {
                    this.kuerzelTextField.setBorder(lineBorderForms);
                    this.getBezeichnungTextField().setBorder(lineBorderForms1);
                    this.getBezeichnungTextField().requestFocusInWindow();
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    fehlerText = "Bezeichnung ist leer";
                } else if(fehler == 2) {
                    this.kuerzelTextField.setBorder(lineBorderForms1);
                    this.kuerzelTextField.requestFocusInWindow();
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    fehlerText = "Kürzel ist zu lang";
                } else {
                    this.kuerzelTextField.setBorder(lineBorderForms1);
                    this.kuerzelTextField.requestFocusInWindow();
                    this.getBezeichnungTextField().setBorder(lineBorderForms);
                    this.getInfoTextTextArea().setBorder(lineBorderForms);
                    fehlerText = "Kürzel ist leer";
                }
                // Fehler- bzw. ErfolgsText auf das FehlerLabel setzen (Meldung oder leer)
                ((KiParameterFormPanel)this.getFormPanel()).getDialogLabel().setText(fehlerText);
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // kein Fehler
            else {
                // ID vom ID-AnzeigeLabel abfragen
                int x = Integer.parseInt(this.getIdLabel().getText().substring(2));
                // KiParameter-Objekt erzeugen...
                KiParameter myKiParameter = new KiParameter
                        (this.selectedParameterGruppenKuerzel.toUpperCase(),
                                x,
                                "",
                                "",
                                bezeichnung,
                                infoText,
                                kuerzel.toUpperCase(),
                                this.selectedParameterGruppe);
                // ...und in DB neu eintragen...
                if(!((KiParameterFormPanel)this.getFormPanel()).isFormEdit()) {
                    fehlerText = myKiParameter.doInsertIntoKiParameter();
                }
                // ...oder änderen
                else {
                    fehlerText = myKiParameter.doUpdateKiParameter();
                }
                // *****************************************************************************************************************************************************************
                // SpezialBehandlung bei ParameterGruppe "Parametergruppe"
                String[] finalParameterGruppen;
                if(this.selectedParameterGruppenKuerzel.equals("PG") && this.selectedParameterGruppe.equals("Parametergruppe")) {
                    // Änderung     -->     ParameterGruppe muss gelöscht werden, bevor sie erneut in die Liste gestellt werden kann
                    if(((KiParameterFormPanel)this.getFormPanel()).isFormEdit()) {
                        // zu löschende ParameterGruppe wird mittels bezeichnung ausgefiltert und in eine temporäre Liste gestellt
                        List<KiParameter> tempList =
                                this.parameterGruppen.stream().filter(kiParameter -> kiParameter.getBezeichnung().equals(bezeichnung)).collect(Collectors.toList());
                        // Kontrollliste mit Inhalt        -->     ParameterGruppe aus Liste löschen
                        if (tempList.size() > 0) {
                            this.parameterGruppen.remove(tempList.get(0));
                        }
                    }
                    // neu abgespeicherte Parametergruppe in Liste stellen
                    this.parameterGruppen.add(myKiParameter);
                    // ParameterGruppenListe frisch sortieren (nach Bezeichnung)
                    this.parameterGruppen.sort(Comparator.comparing(KiTableObject::getBezeichnung));
                    // String[] für JComboBox erzeugen
                    final int[] i = {0};
                    finalParameterGruppen = new String[this.parameterGruppen.size()];
                    String[] finalParameterGruppen1 = finalParameterGruppen;
                    this.parameterGruppen.forEach(kiParameter -> {
                        finalParameterGruppen1[i[0]] = "  " + kiParameter.getBezeichnung();
                        ++i[0];
                    });
                    // neue JComboBox erzeugen
                    this.setSelectGruppenBox(new JComboBox<>(finalParameterGruppen));
                    this.getSelectGruppenBox().setFont(this.textFont2);
                    this.getSelectGruppenBox().setEditable(true);
                    this.getSelectGruppenBox().setSelectedItem(this.selectedParameterGruppe);
                }
                // ****************************************************************************************************************************************************************
                // etwaiger ÄnderungsModus wird zurückgesetzt, nächste ID abgefragt
                if(((KiParameterFormPanel)this.getFormPanel()).isFormEdit()) {
                    // boolean wieder auf Neueintrag setzen
                    ((KiParameterFormPanel)this.getFormPanel()).setFormEdit(false);
                    // letzte ID der gewählten ParameterGruppe abfragen
                    x = KiParameterJPA.findMaxIdKiParameter(
                            MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedParameterGruppenKuerzel, this.selectedParameterGruppe);
                }
                // bei Erfolgsmeldung wird die Schriftfarbe auf Grün gesetzt
                if(fehlerText.equals("Parameter wurde eingetragen.") || fehlerText.equals("Parameter wurde geändert.")) {
                    ((KiParameterFormPanel)this.getFormPanel()).getDialogLabel().setForeground(textColorSuccess);
                }
                // neue ID erzeugen und auf das entsprechende Label setzen
                this.getIdLabel().setText(KiStringUtils.idIncrement(this.selectedParameterGruppenKuerzel, ++x));
                // FormFelder zurücksetzen
                this.kuerzelTextField.setText("");
                this.kuerzelTextField.setBorder(lineBorderForms);
                // Fokus in das erste TextFeld stellen (Kuerzel)
                this.kuerzelTextField.requestFocusInWindow();
                this.getBezeichnungTextField().setText("");
                this.getBezeichnungTextField().setBorder(lineBorderForms);
                this.getInfoTextTextArea().setText("");
                this.getInfoTextTextArea().setBorder(lineBorderForms);
                this.getFormButton().setText("Parameter eintragen");
                // Fehler- bzw. ErfolgsText auf das FehlerLabel setzen (Meldung oder leer)
                ((KiParameterFormPanel)this.getFormPanel()).getDialogLabel().setText(fehlerText);
                // ListObjekte neu abfragen
                ((KiParameterListPanel)this.getListPanel()).findListObjects();
                // Layout aktualisieren
                this.myFrame.changeLayout(2, KiParameterListPanel.getSeite());
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // VorwärtsButton abgesendet        -->         eine Seite weiter blättern
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("forward")) {
            KiParameterListPanel.setSeite(KiParameterListPanel.getSeite() + 1);
            this.myFrame.changeLayout(2, KiParameterListPanel.getSeite());
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // RückwärtsButton abgesendet        -->         eine Seite zurück blättern
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("backward")) {
            KiParameterListPanel.setSeite(KiParameterListPanel.getSeite() - 1);
            this.myFrame.changeLayout(2, KiParameterListPanel.getSeite());
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // ListButton show abgesendet       -->     KiParameterDaten aus DB abfragen und am SubScreenShowFrame ausgeben
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("show")) {
            String id = ((JButton) o).getName().substring(4);
            KiParameter kiParameter =
                    KiParameterJPA.findByIdAndParametergruppe
                            (MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedParameterGruppe, id);
            if (kiParameter != null && !kiParameter.getBezeichnung().isEmpty()) {
                this.showFrame = new SubScreenShowFrame();
                this.showFrame.changeLayout(2, this.selectedParameterGruppe, id, this.myFrame);
            }
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // ListButton edit abgesendet       -->     KiParameterDaten aus DB abfragen und in FormularFelder stellen
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("edit")) {
            String id = ((JButton) o).getName().substring(4);
            KiParameter kiParameter = KiParameterJPA.findByIdAndParametergruppe(MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedParameterGruppe, id);
            if (kiParameter != null && !kiParameter.getBezeichnung().isEmpty()) {
                // Editieren wird auf true gesetzt
                ((KiParameterFormPanel)this.getFormPanel()).setFormEdit(true);
                // ParameterDaten werden ausgelesen und in die entsprechenden TextFelder gestellt
                this.getIdLabel().setText(id);
                this.kuerzelTextField.setText(kiParameter.getKuerzel());
                this.getBezeichnungTextField().setText(kiParameter.getBezeichnung());
                this.getInfoTextTextArea().setText(kiParameter.getInfoText());
                this.getFormButton().setText("Parameter ändern");
            }
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // ListButton delete abgesendet       -->     KiParameterDaten aus DB abfragen und am SubScreenShowFrame ausgeben
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(o instanceof JButton && ((JButton) o).getName().startsWith("delete")) {
            String id = ((JButton) o).getName().substring(6);
            KiParameter kiParameter = KiParameterJPA.findByIdAndParametergruppe(MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedParameterGruppe, id);
            if (kiParameter != null && !kiParameter.getBezeichnung().isEmpty()) {
                this.showFrame = new SubScreenShowFrame();
                this.showFrame.changeLayout(12, this.selectedParameterGruppe, id, this.myFrame);
            }
        }
    }
}
