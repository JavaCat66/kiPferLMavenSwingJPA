package com.javahelps.kiPferL.viewing.mainFrame.subScreens;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenActionAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.subScreens.SubScreenItemAdapter;
import com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;

import javax.swing.*;
import java.util.List;

/**
 * abstrakte SuperKlasse aller SubScreens, stellt unter anderem abstrakte Methoden zur Verfügung, die in allen den SubKlassen benötigt werden
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.15
 * @since 1.14
 */
public abstract class SubScreen {

    private final String listTitle, formTitle;
    private final KiParameter filterCriterion;
    private SubScreenActionAdapter actionController;
    private SubScreenItemAdapter itemController;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public SubScreen(String listTitle, String formTitle, String filterCriterionS) {

        this.listTitle = listTitle;
        this.formTitle = formTitle;

        // FilterKriteriumsObject = KiParameter erzeugen
        this.filterCriterion = KiParameterJPA.findKiParameterByPraefixParametergruppeAndBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), "PG", "Parametergruppe", filterCriterionS);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // abstrakte Methoden, die in den SubKlassen benötigt werden
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public abstract JPanel createListPanel();
    public abstract JPanel createListPanel(int seite, List<SubScreenListObject> listObjects);
    public abstract JPanel createFormPanel();
    public abstract SubScreenActionAdapter createActionListener();
    public abstract SubScreenItemAdapter createItemListener();
    public abstract void changeLayout(SubScreenActionAdapter actionAdapter, SubScreenItemAdapter itemAdapter);
    public abstract void changeLayout(int seite, SubScreenActionAdapter actionAdapter, SubScreenItemAdapter itemAdapter);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // GETTER und SETTER, damit von den SubKlassen aus auf die Komponenten zugegriffen werden kann
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String getListTitle() {
        return this.listTitle;
    }
    public String getFormTitle() {
        return this.formTitle;
    }
    public KiParameter getFilterCriterion() {
        return this.filterCriterion;
    }
    public SubScreenActionAdapter getActionController() {
        return this.actionController;
    }
    public SubScreenItemAdapter getItemController() {
        return this.itemController;
    }

    public void setActionController(SubScreenActionAdapter actionController) {
        this.actionController = actionController;
    }
    public void setItemController(SubScreenItemAdapter itemController) {
        this.itemController = itemController;
    }
}
