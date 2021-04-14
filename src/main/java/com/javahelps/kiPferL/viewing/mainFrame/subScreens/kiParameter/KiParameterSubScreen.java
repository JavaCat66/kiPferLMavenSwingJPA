package com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiParameter;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenActionAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenItemAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.kiParameter.KiParameterSubScreenActionController;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.kiParameter.KiParameterSubScreenItemController;
import com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.viewing.mainFrame.MainFrame;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreen;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenFormPanel;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenListObject;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenListPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * gesamter SubScreen für Parameter, setzt linke Seite (Liste) und rechte Seite (Formular zusammen), erbt von der abstrakten SuperKlasse SubScreen
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.15
 * @since 1.14
 */
public class KiParameterSubScreen extends SubScreen {

    // Frame
    private final MainFrame myFrame;
    // Ausgewählte ParameterGruppe
    private String selectedParameterGruppe, selectedParameterGruppenKuerzel;
    // Liste der ParameterGruppen, die ausgewählt werden können
    private final List<KiParameter> parameterGruppen;
    // die beiden HauptPanel für den SubScreen
    private KiParameterFormPanel formPanel;
    private KiParameterListPanel listPanel;
    // Dimensions
    private final Dimension preferredSizeList;
    private final Dimension preferredSizeListNorthPanel;
    private final Dimension preferredSizeForm;
    private final Dimension preferredSizeFormNorthPanel;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor, setzt das default Panel zusammen
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiParameterSubScreen(MainFrame myFrame, JPanel menuePanel, String listTitle, String formTitle, String filterCriterionS) {
        // Aufruf des SuperKlassenKonstruktors
        super(listTitle, formTitle, filterCriterionS);
        // Frame
        this.myFrame = myFrame;
        // Dimensions
        this.preferredSizeList = new Dimension(this.myFrame.getWidth() / 2 - 104, this.myFrame.getHeight() - menuePanel.getPreferredSize().height);
        this.preferredSizeListNorthPanel = new Dimension(
                this.myFrame.getWidth() / 2 - 104, ((this.myFrame.getHeight() - menuePanel.getPreferredSize().height - 15) / 26) * 4);
        this.preferredSizeForm = new Dimension(this.myFrame.getWidth() / 2 + 104 - 7, this.myFrame.getHeight() - menuePanel.getPreferredSize().height);
        this.preferredSizeFormNorthPanel =
                new Dimension(this.myFrame.getWidth() / 2 + 104 - 7, ((this.myFrame.getHeight() - menuePanel.getPreferredSize().height - 15) / 26) * 5);
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Parameter-Liste erzeugen
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        List<KiParameter> tempList = KiParameterJPA.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), this.getFilterCriterion().getKuerzel(), this.getFilterCriterion().getBezeichnung());
        // SammelMappe
        this.parameterGruppen = new ArrayList<>();
        final int[] x = {0};
        if(tempList != null) {
            tempList.forEach(kiParameter -> {
                // default ParameterGruppe setzen (erster in der Liste)
                if(x[0] == 0) {
                    KiParameterSubScreen.this.selectedParameterGruppe = kiParameter.getBezeichnung();
                    KiParameterSubScreen.this.selectedParameterGruppenKuerzel = kiParameter.getKuerzel();
                    ++x[0];
                }
                KiParameterSubScreen.this.parameterGruppen.add(kiParameter);
            });
        }
        // ************************************************************************************************************************************************************************
        // Panels erzeugen
        // ************************************************************************************************************************************************************************
        this.formPanel = (KiParameterFormPanel) this.createFormPanel();
        this.listPanel = (KiParameterListPanel) this.createListPanel();
        // ************************************************************************************************************************************************************************
        // ActionListener erzeugen und FormuarButton anmelden
        // ************************************************************************************************************************************************************************
        this.setActionController(this.createActionListener());
        this.formPanel.getFormButton().addActionListener(this.getActionController());
        this.listPanel.getListObjects().forEach(subScreenListObject -> {
            subScreenListObject.getShowButton().addActionListener(this.getActionController());
            subScreenListObject.getEditButton().addActionListener(this.getActionController());
            subScreenListObject.getDeleteButton().addActionListener(this.getActionController());
        });
        this.listPanel.getForwardButton().addActionListener(this.getActionController());
        this.listPanel.getBackwardButton().addActionListener(this.getActionController());
        // ************************************************************************************************************************************************************************
        // itemListener erzeugen und ComboBox anmelden
        // ************************************************************************************************************************************************************************
        this.setItemController(this.createItemListener());
        this.getListPanel().getSelectGruppenBox().addItemListener(this.getItemController());
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Getter und Setter, damit auf die Variablen zugegriffen werden kann
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public void setSelectedParameterGruppe(String selectedParameterGruppe) {
        this.selectedParameterGruppe = selectedParameterGruppe;
    }
    public void setSelectedParameterGruppenKuerzel(String selectedParameterGruppenKuerzel) {
        this.selectedParameterGruppenKuerzel = selectedParameterGruppenKuerzel;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Überschriebene Methode aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public JPanel createListPanel() {
        return new KiParameterListPanel
                (this.preferredSizeList, this.preferredSizeListNorthPanel, this.getListTitle(), this.parameterGruppen,
                this.selectedParameterGruppe, this.selectedParameterGruppenKuerzel);
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public JPanel createListPanel(int seite, List<SubScreenListObject> listObjects) {
        return new KiParameterListPanel
                (this.preferredSizeList, this.preferredSizeListNorthPanel, this.getListTitle(), this.parameterGruppen,
                        this.selectedParameterGruppe, this.selectedParameterGruppenKuerzel, seite, listObjects);
    }

    public SubScreenListPanel getListPanel() {
        return this.listPanel;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Überschriebene Methode aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public JPanel createFormPanel() {
        return new KiParameterFormPanel(
                this.preferredSizeForm, this.preferredSizeFormNorthPanel, new GridLayout(5, 2, 20, 3), this.getFormTitle(),
                this.selectedParameterGruppe, this.selectedParameterGruppenKuerzel);
    }

    public SubScreenFormPanel getFormPanel() {
        return this.formPanel;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Überschriebene Methode aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public SubScreenActionAdapter createActionListener() {
        return new KiParameterSubScreenActionController
                (this.myFrame, this.listPanel.getSelectGruppenBox(), this.formPanel.getDialogLabel(), this.formPanel.getIdLabel(),
                this.formPanel.getBezeichnungTextField(), this.formPanel.getInfoTextTextArea(), this.formPanel.getFormButton(), this.formPanel.getKuerzelTextField(),
                this.listPanel, this.formPanel, this.parameterGruppen, this.selectedParameterGruppe, this.selectedParameterGruppenKuerzel);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Überschriebene Methode aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public SubScreenItemAdapter createItemListener() {
        return new KiParameterSubScreenItemController
                (this.myFrame, this.listPanel.getSelectGruppenBox(), this.formPanel.getDialogLabel(), this.formPanel.getIdLabel(),
                 this.formPanel.getBezeichnungTextField(), this.formPanel.getInfoTextTextArea(), this.formPanel.getKuerzelTextField(),
                 this.parameterGruppen);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Methoden zum Wechseln der Bildschirme - überschrieben aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void changeLayout(SubScreenActionAdapter actionAdapter, SubScreenItemAdapter itemAdapter) {
        // Änderung nicht Neuerstelleung
        this.formPanel = (KiParameterFormPanel) this.createFormPanel();
        this.listPanel = (KiParameterListPanel) this.createListPanel();
        // ************************************************************************************************************************************************************************
        // vorherigen ActionListener zerstören und neuen erzeugen, dann FormuarKomponenten neu anmelden
        // ************************************************************************************************************************************************************************
        this.formPanel.getFormButton().removeActionListener(actionAdapter);
        this.listPanel.getListObjects().forEach(subScreenListObject -> {
            subScreenListObject.getShowButton().removeActionListener(actionAdapter);
            subScreenListObject.getEditButton().removeActionListener(actionAdapter);
            subScreenListObject.getDeleteButton().removeActionListener(actionAdapter);
        });
        this.listPanel.getForwardButton().removeActionListener(actionAdapter);
        this.listPanel.getBackwardButton().removeActionListener(actionAdapter);

        this.setActionController(this.createActionListener());
        this.formPanel.getFormButton().addActionListener(this.getActionController());
        this.listPanel.getListObjects().forEach(subScreenListObject -> {
            subScreenListObject.getShowButton().addActionListener(this.getActionController());
            subScreenListObject.getEditButton().addActionListener(this.getActionController());
            subScreenListObject.getDeleteButton().addActionListener(this.getActionController());
        });
        this.listPanel.getForwardButton().addActionListener(this.getActionController());
        this.listPanel.getBackwardButton().addActionListener(this.getActionController());
        // ************************************************************************************************************************************************************************
        // vorherigen ItemListener zerstören, neuen erzeugen und ComboBox anmelden
        // ************************************************************************************************************************************************************************
        this.getListPanel().getSelectGruppenBox().removeItemListener(itemAdapter);

        this.setItemController(this.createItemListener());
        this.getListPanel().getSelectGruppenBox().addItemListener(this.getItemController());
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void changeLayout(int seite, SubScreenActionAdapter actionAdapter, SubScreenItemAdapter itemAdapter) {
        // Änderung nicht Neuerstelleung
        this.formPanel = (KiParameterFormPanel) this.createFormPanel();
        this.listPanel = (KiParameterListPanel) this.createListPanel(seite, this.listPanel.getListObjects());
        // ************************************************************************************************************************************************************************
        // vorherigen ActionListener zerstören und neuen erzeugen, dann FormuarKomponenten neu anmelden
        // ************************************************************************************************************************************************************************
        this.formPanel.getFormButton().removeActionListener(actionAdapter);
        this.listPanel.getListObjects().forEach(subScreenListObject -> {
            subScreenListObject.getShowButton().removeActionListener(actionAdapter);
            subScreenListObject.getEditButton().removeActionListener(actionAdapter);
            subScreenListObject.getDeleteButton().removeActionListener(actionAdapter);
        });
        this.listPanel.getForwardButton().removeActionListener(actionAdapter);
        this.listPanel.getBackwardButton().removeActionListener(actionAdapter);

        this.setActionController(this.createActionListener());
        this.formPanel.getFormButton().addActionListener(this.getActionController());
        this.listPanel.getListObjects().forEach(subScreenListObject -> {
            subScreenListObject.getShowButton().addActionListener(this.getActionController());
            subScreenListObject.getEditButton().addActionListener(this.getActionController());
            subScreenListObject.getDeleteButton().addActionListener(this.getActionController());
        });
        this.listPanel.getForwardButton().addActionListener(this.getActionController());
        this.listPanel.getBackwardButton().addActionListener(this.getActionController());
        // ************************************************************************************************************************************************************************
        // itemListener erzeugen und ComboBox anmelden
        // ************************************************************************************************************************************************************************
        this.getListPanel().getSelectGruppenBox().removeItemListener(itemAdapter);

        this.setItemController(this.createItemListener());
        this.getListPanel().getSelectGruppenBox().addItemListener(this.getItemController());
    }
}
