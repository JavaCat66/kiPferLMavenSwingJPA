package com.javahelps.kiPferL.controlling.mainFrame.subScreens.kiZutat;

import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenItemAdapter;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.viewing.mainFrame.MainFrame;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiZutat.ZutatFormPanel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.List;

public class ZutatSubScreenItemController extends SubScreenItemAdapter {

    private final MainFrame myFrame;
    // Liste der ParameterGruppen, die ausgewählt werden können
    private final List<KiParameter> einkaufsKategorien;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor (auszulesende bzw. zu editierende Komponenten werden übergeben)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public ZutatSubScreenItemController
    (MainFrame myFrame, JComboBox<String> selectGruppenBox, JLabel dialogLabel, JLabel idLabel, JTextField bezeichnungTextField,
     JTextArea infoTextTextArea, List<KiParameter> einkaufsKategorien) {
        // Aufruf des Konstruktors der SuperKlasse SubScreenItemAdapter, welche den ItemListener implementiert
        super(dialogLabel, idLabel, bezeichnungTextField, infoTextTextArea, selectGruppenBox);

        this.myFrame = myFrame;
        this.einkaufsKategorien = einkaufsKategorien;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // EventHandling
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // ParameterComboBox abgesendet
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        if(o == this.getSelectGruppenBox()) {
            // selektierte ParameterGruppe (KiParameter.parameterGruppe) wird ausgelesen
            String mySelected = (String) e.getItem();
            // die ParameterGruppe durchLoopen und wenn das richtige KiParameter-Objekt gefunden wurde...
            this.einkaufsKategorien.forEach(kiParameter -> {
                if (kiParameter.getBezeichnung().equals(mySelected.trim())) {
                    // wird die aktuelle ParameterGruppe geändert...
                    this.myFrame.getZutatSubScreen().setSelectedEinkaufsKategorie(mySelected.trim());
                    this.myFrame.getZutatSubScreen().setSelectedEinkaufsKategorieId(kiParameter.getId());
                    this.myFrame.getZutatSubScreen().setSelectedEinkaufsKategorieKuerzel(kiParameter.getKuerzel());
                    // -------------------------------------------------------------------------------------------------------------------------------------------------------------
                    // ... FormFelder werden zurückgesetzt...
                    this.getBezeichnungTextField().setText("");
                    // Fokus in das erste TextFeld stellen (Bezeichnung)
                    this.getBezeichnungTextField().requestFocusInWindow();
                    this.getInfoTextTextArea().setText("");
                    this.getDialogLabel().setText("");
                    // -------------------------------------------------------------------------------------------------------------------------------------------------------------
                    // ...und das Layout neu aufgebaut
                    this.myFrame.changeLayout(3);
                }
            });
        }
    }
}
