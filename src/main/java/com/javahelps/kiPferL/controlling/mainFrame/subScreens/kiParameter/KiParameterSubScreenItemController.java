package com.javahelps.kiPferL.controlling.mainFrame.subScreens.kiParameter;

import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenItemAdapter;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.viewing.mainFrame.MainFrame;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.List;

/**
 * ItemListener für den Parameter-SubScreen erbt von SubScreenItemAdapter
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.16
 * @since 1.14
 */
public class KiParameterSubScreenItemController extends SubScreenItemAdapter {

    private final MainFrame myFrame;
    // Liste der ParameterGruppen, die ausgewählt werden können
    private final List<KiParameter> parameterGruppen;
    // FormularSpezifische Felder
    private final JTextField kuerzelTextField;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor (auszulesende bzw. zu editierende Komponenten werden übergeben)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiParameterSubScreenItemController
            (MainFrame myFrame, JComboBox<String> selectGruppenBox, JLabel dialogLabel, JLabel idLabel, JTextField bezeichnungTextField, JTextArea infoTextTextArea,
             JTextField kuerzelTextField, List<KiParameter> parameterGruppen) {
        // Aufruf des Konstruktors der SuperKlasse SubScreenItemAdapter, welche den ItemListener implementiert
        super(dialogLabel, idLabel, bezeichnungTextField, infoTextTextArea, selectGruppenBox);

        this.myFrame = myFrame;
        this.parameterGruppen = parameterGruppen;
        this.kuerzelTextField = kuerzelTextField;
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
            this.parameterGruppen.forEach(kiParameter -> {
                if (kiParameter.getBezeichnung().equals(mySelected.trim())) {
                    // wird die aktuelle ParameterGruppe geändert...
                    this.myFrame.getKiParameterSubScreen().setSelectedParameterGruppe(mySelected.trim());
                    this.myFrame.getKiParameterSubScreen().setSelectedParameterGruppenKuerzel(kiParameter.getKuerzel());
                    // -------------------------------------------------------------------------------------------------------------------------------------------------------------
                    // ... FormFelder werden zurückgesetzt...
                    this.kuerzelTextField.setText("");
                    // Fokus in das erste TextFeld stellen (Kuerzel)
                    this.kuerzelTextField.requestFocusInWindow();
                    this.getBezeichnungTextField().setText("");
                    this.getInfoTextTextArea().setText("");
                    this.getDialogLabel().setText("");
                    // -------------------------------------------------------------------------------------------------------------------------------------------------------------
                    // ...und das Layout neu aufgebaut
                    this.myFrame.changeLayout(2);
                }
            });
        }
    }
}
