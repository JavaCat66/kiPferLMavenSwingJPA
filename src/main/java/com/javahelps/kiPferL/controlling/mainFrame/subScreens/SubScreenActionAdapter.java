package com.javahelps.kiPferL.controlling.mainFrame.subScreens;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * SuperKlasse aller SubScreenActionListener
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.15
 * @since 1.14
 */
public class SubScreenActionAdapter implements ActionListener {

    private final JPanel listPanel;
    private final JPanel formPanel;
    private final JLabel dialogLabel;
    private final JLabel idLabel;
    private final JTextField bezeichnungTextField;
    private final JTextArea infoTextTextArea;
    private JComboBox<String> selectGruppenBox;
    private final JButton formButton;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor (auszulesende bzw. zu editierende Komponenten werden übergeben       -->     solche, die in jedem SubScreen vorkommen)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public SubScreenActionAdapter
            (JPanel listPanel, JPanel formPanel, JComboBox<String> selectGruppenBox, JLabel dialogLabel, JLabel idLabel, JTextField bezeichnungTextField,
             JTextArea infoTextTextArea, JButton formButton) {

        this.listPanel = listPanel;
        this.formPanel = formPanel;
        this.selectGruppenBox = selectGruppenBox;
        this.dialogLabel = dialogLabel;
        this.idLabel = idLabel;
        this.bezeichnungTextField = bezeichnungTextField;
        this.infoTextTextArea = infoTextTextArea;
        this.formButton = formButton;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // GETTER und SETTER, damit von den SubKlassen aus auf die Variablen zugegriffen werden kann
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public JPanel getFormPanel() {
        return formPanel;
    }
    public JPanel getListPanel() {
        return listPanel;
    }
    public JComboBox<String> getSelectGruppenBox() {
        return selectGruppenBox;
    }
    public JLabel getDialogLabel() {
        return dialogLabel;
    }
    public JLabel getIdLabel() {
        return idLabel;
    }
    public JTextField getBezeichnungTextField() {
        return bezeichnungTextField;
    }
    public JTextArea getInfoTextTextArea() {
        return infoTextTextArea;
    }
    public JButton getFormButton() {
        return formButton;
    }

    public void setSelectGruppenBox(JComboBox<String> selectGruppenBox) {
        this.selectGruppenBox = selectGruppenBox;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // "Leere" überschriebene Methode aus dem ActionListener, wird in den SubKlassen nochmals mit Inhalt überschrieben
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }
}
