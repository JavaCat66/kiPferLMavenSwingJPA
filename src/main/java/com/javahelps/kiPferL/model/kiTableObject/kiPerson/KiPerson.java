package com.javahelps.kiPferL.model.kiTableObject.kiPerson;

import com.javahelps.kiPferL.model.kiTableObject.KiTableObject;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Superklasse aller PersonenKlassen / SubKlasse von KiTableObject    -->     wurde im Zuge der Version 2.8 gemeinsam mit einer neuen UnterPackage-Struktur hinzugefügt
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 1.0 2020.12.26      -->     Collections, InnerAnonym, Lambda, MethodReference
 *                                    -->     beide TimeStamps (Creation und Change) werden nicht mehr übergeben, sondern im Konstruktor von KiTableObjekt befüllt
 *                                    -->     Verwendung von StringBuilder.append() statt String.concat(). Weil schneller.
 * version 2.8 2020.12.08      -->     Neue SuperKlasse für PersonenObjekte = SoftwareBenutzer, LagerTeilnehmerInnen
 * @since 1.14
 */
@MappedSuperclass
public class KiPerson extends KiTableObject {
    // ********************************************************************************************************************************************************************************
    // Objekt-Variable deklarieren
    // ********************************************************************************************************************************************************************************
    @Transient
    private String personenGruppe;                                                              // Konstruktor

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Default Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiPerson () {

    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Parametrisierter Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiPerson (String pModul, String pPraefix, int pId, String pProgram, String pMethod, String pGroup, String pUser, String pBezeichnung, String pInfoText,
                     String pPersonenGruppe) {
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Konstruktor Superklasse model.kiTableObject.KiTableObject aufrufen
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        super(pModul, pPraefix, pId, pProgram, pMethod, pGroup, pUser, pBezeichnung, pInfoText);
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Variable mit eingegebenen Parametern definieren
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        this.personenGruppe = pPersonenGruppe;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // AUSGABE
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Einzeilige Ausgabe der Eigenschaften
    @Override
    public String toString() {
        return new StringBuilder().append("KiPerson{ ").append(super.toString()).append
                (", personenGruppe='").append(this.personenGruppe).append("'}").toString();
    }
}
