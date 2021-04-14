package com.javahelps.kiPferL.viewing.mainFrame.subScreens;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * SuperKlasse aller ListObjekte (Liste linke Seite) eines UnterProgramms, enthält alle Elemente, die in allen SubScreens verwendet werden
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.15
 * @since 1.14
 */
public class SubScreenListObject {

    private final JLabel id, bezeichnung;
    private final JButton showButton, editButton, deleteButton;
    private final JPanel listButtonPanel;
    // Fonts (Konstante)
    private static final Font buttonFont = new Font("Code2000", Font.PLAIN, 24);
    private static final Font textFont2 = new Font("Arial", Font.PLAIN, 16);
    // Colors (Konstante)
    private static final Color buttonColor = new Color(204,255,204);
    // Rahmen (Konstante)
    private static final Border lineBorderForms = new LineBorder(Color.BLACK, 1);
    private static final EmptyBorder listEmptyBorder = new EmptyBorder(0, 4, 0, 4);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public SubScreenListObject(String pId, String pBezeichnung, String showName, String editName, String deleteName, LayoutManager listButtonPanelLayoutManager) {

        this.id = new JLabel(pId);
        this.id.setFont(textFont2);

        this.bezeichnung = new JLabel(pBezeichnung);
        this.bezeichnung.setFont(textFont2);
        this.bezeichnung.setBorder(listEmptyBorder);

        int uniCodePoint = Integer.parseInt("1F441",16);
        this.showButton = new JButton(new String(Character.toChars(uniCodePoint)));
        this.showButton.setFont(buttonFont);
        this.showButton.setName(showName);
        this.showButton.setBackground(buttonColor);
        this.showButton.setBorder(lineBorderForms);

        uniCodePoint = Integer.parseInt("1F589",16);
        this.editButton = new JButton(new String(Character.toChars(uniCodePoint)));
        this.editButton.setFont(buttonFont);
        this.editButton.setName(editName);
        this.editButton.setBackground(buttonColor);
        this.editButton.setBorder(lineBorderForms);

        uniCodePoint = Integer.parseInt("1F5D1",16);
        this.deleteButton = new JButton(new String(Character.toChars(uniCodePoint)));
        this.deleteButton.setFont(buttonFont);
        this.deleteButton.setName(deleteName);
        this.deleteButton.setBackground(buttonColor);
        this.deleteButton.setBorder(lineBorderForms);

        this.listButtonPanel = new JPanel(listButtonPanelLayoutManager);
        this.listButtonPanel.setBorder(listEmptyBorder);
        this.listButtonPanel.setBackground(Color.WHITE);
        this.listButtonPanel.add(this.showButton);
        this.listButtonPanel.add(this.editButton);
        this.listButtonPanel.add(this.deleteButton);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // GETTER, damit auf die Komponenten von den SubKlassen aus zugegriffen werden kann
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public JLabel getId() {
        return this.id;
    }
    public JLabel getBezeichnung() {
        return this.bezeichnung;
    }
    public JButton getShowButton() {
        return this.showButton;
    }
    public JButton getEditButton() {
        return this.editButton;
    }
    public JButton getDeleteButton() {
        return this.deleteButton;
    }
    public JPanel getListButtonPanel() {
        return this.listButtonPanel;
    }
}
