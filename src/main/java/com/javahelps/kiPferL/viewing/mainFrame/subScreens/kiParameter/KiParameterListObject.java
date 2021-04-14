package com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiParameter;

import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenListObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Stellt eine Zeile in der Liste auf der linken Seite da, erbt von SubScreenListObject
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.17
 * @since 1.14
 */
public class KiParameterListObject extends SubScreenListObject {

    private final JLabel kuerzel;
    // Fonts (Konstante)
    private static final Font textFont2 = new Font("Arial", Font.PLAIN, 16);
    // Rahmen (Konstante)
    private static final EmptyBorder listEmptyBorder = new EmptyBorder(0, 4, 0, 4);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiParameterListObject(String pId, String pBezeichnung, String showName, String editName, String deleteName, String pKuerzel) {
        // Konstruktor der SuperKlasse wird aufgerufen
        super(pId, pBezeichnung, showName, editName, deleteName, new GridLayout(1, 3, 3, 3));

        this.kuerzel = new JLabel(pKuerzel);
        this.kuerzel.setFont(textFont2);
        this.kuerzel.setBorder(KiParameterListObject.listEmptyBorder);
    }

    public JLabel getKuerzel() {
        return this.kuerzel;
    }
}
