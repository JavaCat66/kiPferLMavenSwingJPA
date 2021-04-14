package com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiZutat;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenActionAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenItemAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.kiZutat.ZutatSubScreenActionController;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.kiZutat.ZutatSubScreenItemController;
import com.javahelps.kiPferL.jpa.kiZutat.ZutatJPA;
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
 * gesamter SubScreen für Zutaten, setzt linke Seite (Liste) und rechte Seite (Formular zusammen), erbt von der abstrakten SuperKlasse SubScreen
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * @since 1.14
 */
public class ZutatSubScreen extends SubScreen {

    // Frame
    private final MainFrame myFrame;
    // Ausgewählte ParameterGruppe
    private String selectedEinkaufsKategorie, selectedEinkaufsKategorieKuerzel, selectedEinkaufsKategorieId;
    // Liste der EinkaufsKategorien, die ausgewählt werden können
    private final List<KiParameter> einkaufsKategorien;
    // die beiden HauptPanel für den SubScreen
    private ZutatFormPanel formPanel;
    private ZutatListPanel listPanel;
    // Dimensions
    private final Dimension preferredSizeList, preferredSizeListNorthPanel, preferredSizeForm, preferredSizeFormNorthPanel;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor, setzt das default Panel zusammen
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public ZutatSubScreen(MainFrame myFrame, JPanel menuePanel, String listTitle, String formTitle, String filterCriterionS) {
        // Aufruf des SuperKlassenKonstruktors
        super(listTitle, formTitle, filterCriterionS);
        // Frame
        this.myFrame = myFrame;
        // Dimensions
        this.preferredSizeList =
                new Dimension(this.myFrame.getWidth() / 2 - 104, ((this.myFrame.getHeight() - menuePanel.getPreferredSize().height - 15) / 26) * 26);
        this.preferredSizeListNorthPanel = new Dimension(
                this.myFrame.getWidth() / 2 - 104, ((this.myFrame.getHeight() - menuePanel.getPreferredSize().height - 15) / 26) * 4);
        this.preferredSizeForm =
                new Dimension(this.myFrame.getWidth() / 2 + 104 - 7, ((this.myFrame.getHeight() - menuePanel.getPreferredSize().height - 15) / 26) * 26);
        this.preferredSizeFormNorthPanel =
                new Dimension(this.myFrame.getWidth() / 2 + 104 - 7, ((this.myFrame.getHeight() - menuePanel.getPreferredSize().height - 15) / 26) * 4);
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Parameter-Liste erzeugen
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        List<KiParameter> tempList = ZutatJPA.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), this.getFilterCriterion().getKuerzel(), this.getFilterCriterion().getBezeichnung());
        // SammelMappe
        this.einkaufsKategorien = new ArrayList<>();
        final int[] x = {0};
        if(tempList != null) {
            tempList.forEach(kiParameter -> {
                // default ParameterGruppe setzen (erster in der Liste)
                if(x[0] == 0) {
                    ZutatSubScreen.this.selectedEinkaufsKategorie = kiParameter.getBezeichnung();
                    ZutatSubScreen.this.selectedEinkaufsKategorieKuerzel = kiParameter.getKuerzel();
                    ZutatSubScreen.this.selectedEinkaufsKategorieId = kiParameter.getId();
                    ++x[0];
                }
                ZutatSubScreen.this.einkaufsKategorien.add(kiParameter);
            });
        }
        // ************************************************************************************************************************************************************************
        // Panels erzeugen
        // ************************************************************************************************************************************************************************
        this.formPanel = (ZutatFormPanel) this.createFormPanel();
        this.listPanel = (ZutatListPanel) this.createListPanel();
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
    public void setSelectedEinkaufsKategorie(String selectedEinkaufsKategorie) {
        this.selectedEinkaufsKategorie = selectedEinkaufsKategorie;
    }
    public void setSelectedEinkaufsKategorieId(String selectedEinkaufsKategorieId) {
        this.selectedEinkaufsKategorieId = selectedEinkaufsKategorieId;
    }
    public void setSelectedEinkaufsKategorieKuerzel(String selectedEinkaufsKategorieKuerzel) {
        this.selectedEinkaufsKategorieKuerzel = selectedEinkaufsKategorieKuerzel;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Überschriebene Methode aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public JPanel createListPanel() {
        return new ZutatListPanel
                (this.preferredSizeList, this.preferredSizeListNorthPanel, this.getListTitle(), this.einkaufsKategorien,
                        this.selectedEinkaufsKategorie, this.selectedEinkaufsKategorieId);
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public JPanel createListPanel(int seite, List<SubScreenListObject> listObjects) {
        return new ZutatListPanel
                (this.preferredSizeList, this.preferredSizeListNorthPanel, this.getListTitle(), this.einkaufsKategorien,
                        this.selectedEinkaufsKategorie, this.selectedEinkaufsKategorieId, seite, listObjects);
    }

    public SubScreenListPanel getListPanel() {
        return this.listPanel;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Überschriebene Methode aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public JPanel createFormPanel() {
        return new ZutatFormPanel(
                this.preferredSizeForm, this.preferredSizeFormNorthPanel, new GridLayout(4, 2, 20, 3), this.getFormTitle(),
                this.selectedEinkaufsKategorieKuerzel, this.selectedEinkaufsKategorieId);
    }

    public SubScreenFormPanel getFormPanel() {
        return this.formPanel;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Überschriebene Methode aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public SubScreenActionAdapter createActionListener() {
        return new ZutatSubScreenActionController
                (this.myFrame, this.listPanel.getSelectGruppenBox(), this.formPanel.getDialogLabel(), this.formPanel.getIdLabel(), this.formPanel.getBezeichnungTextField(),
                        this.formPanel.getInfoTextTextArea(), this.formPanel.getFormButton(), this.listPanel, this.formPanel, this.selectedEinkaufsKategorieId,
                        this.selectedEinkaufsKategorieKuerzel);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Überschriebene Methode aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public SubScreenItemAdapter createItemListener() {
        return new ZutatSubScreenItemController
                (this.myFrame, this.listPanel.getSelectGruppenBox(), this.formPanel.getDialogLabel(), this.formPanel.getIdLabel(),
                        this.formPanel.getBezeichnungTextField(), this.formPanel.getInfoTextTextArea(),
                        this.einkaufsKategorien);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Methoden zum Wechseln der Bildschirme - überschrieben aus der SuperKlasse
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void changeLayout(SubScreenActionAdapter actionAdapter, SubScreenItemAdapter itemAdapter) {
        // Änderung nicht Neuerstelleung
        this.formPanel = (ZutatFormPanel) this.createFormPanel();
        this.listPanel = (ZutatListPanel) this.createListPanel();
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
        this.formPanel = (ZutatFormPanel) this.createFormPanel();
        this.listPanel = (ZutatListPanel) this.createListPanel(seite, this.listPanel.getListObjects());
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
