package com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiZutat;

import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenListObject;

import java.awt.*;

/**
 * Stellt eine Zeile in der Liste auf der linken Seite da, erbt von SubScreenListObject
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * @since 1.14
 */
public class ZutatListObject  extends SubScreenListObject {

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public ZutatListObject(String pId, String pBezeichnung, String showName, String editName, String deleteName) {
        // Konstruktor der SuperKlasse wird aufgerufen
        super(pId, pBezeichnung, showName, editName, deleteName, new GridLayout(1, 3, 3, 3));
    }
}
