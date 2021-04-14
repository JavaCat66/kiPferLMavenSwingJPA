package com.javahelps.kiPferL.start;

import com.javahelps.kiPferL.viewing.mainFrame.MainFrame;

/**
 * TESTING: Einführung von Hibernate/JPA
 * TESTING: Erzeugung und Verwendung einer Swing-GUI
 * TESTING: Erzeugung und Verwendung von Parametern, Zutaten und Rezepten
 * TESTING: Erzeugung und Verwendung von Adresse, Altersgruppe, Personen, ScoutGroup
 * TESTING: Erzeugung und Verwendung einer DatenBank
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 2.0 2021.01.29      -->     goToDataBase and GUI
 *                                    -->     EstablishKiDB ist ein NebenProjekt, welches die Aufgabe hat, Tabellen in der DatenBank zu erzeugen, ändern und löschen (SWING)
 *                                    -->     MainFrame ist das eigentliche KochBuch-Programm (SWING)
 *                                    -->     Erzeugen der Tabellen KiParameter und KiUser mit EstablishKiDB
 * version Alpha 1.0 2020.12.25      -->     STARTVERSION WIFI-PROJEKT
 *                                   -->     Collections, InnerAnonym, Lambda, MethodReference
 *                                   -->     beide TimeStamps (Creation und Change) werden nicht mehr übergeben, sondern im Konstruktor von KiTableObjekt befüllt
 *                                   -->     Verwendung von StringBuilder.append() statt String.concat(). Weil schneller.
 *                                   -->     neue Module kiAddress, kiAltersGruppe, kiScoutGroup
 *                                   -->     neues Modul kiUtils
 * version 2.8 2020.12.08      -->     neues Modul kiPerson mit kiUser und kiLagerTeilnehmerIn
 *                             -->     Serialisierung von Berechtigungen
 *                             -->     PolyMorphie
 * version 2.5 2020.11.24      -->     neue SubKlasse von model.kiTableObject = model.kiTableObject.kiParameter.lagerFaehigKeit.LagerFaehigKeit
 *                                      wird von model.kiTableObject.kiKochbuch.Zutat verwendet
 *                             -->     static KlassenVariable zb als Counter in model.kiTableObject.kiKochbuch.Zutat und model.kiTableObject.kiKochbuch.Rezept
 * version 2.3 2020.11.18      -->     String-Methoden anwenden
 * version 2.2 2020.11.17      -->     @overriding, @overloading, java.lang.Object.toString()
 * version 2.0 2020.11.04      -->     Testing der Version 2.0 (neue Superklasse model.kiTableObject.KiTableObject und differenziertere Package-Struktur)
 * version 1.0 2020.10.28      -->     Testing der Version 1.0
 * @since 1.14
 */
public class Start {
    public static void main(String[] args) {
        new MainFrame();
    }
}
