package com.javahelps.kiPferL.model.kiTableObject.kiParameter;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA;
import com.javahelps.kiPferL.model.kiTableObject.KiTableObject;

import javax.persistence.*;

/**
 * Tabellenklasse, vererbt von KiTableObject
 * PARAMETER werden hauptsächlich von anderen Objekten (zb LagerFaehigKeit, Zutat, Rezept) verwendet
 * 0 - N KiParameter können mittels Konstruktor in start.Start.mainFrame() erstellt und an ein Objekt übergeben werden
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 2.0 2021.02.05      -->     SWING-GUI und MySQL-DB werden hinzugefügt, neue Methoden zum Eintragen und Ändern von Parametern in der DatenBank
 * version Alpha 1.0 2020.12.26      -->     Collections, InnerAnonym, Lambda, MethodReference
 *                                   -->     beide TimeStamps (Creation und Change) werden nicht mehr übergeben, sondern im Konstruktor von KiTableObjekt befüllt
 *                                   -->     Verwendung von StringBuilder.append() statt String.concat(). Weil schneller.
 * version 2.8 2020.12.08      -->     Neue PackageStruktur für PersonenObjekte = SoftwareBenutzer, LagerTeilnehmerInnen
 *                              -->     Serialisierung von Berechtigungen
 * version 2.3 2020.11.18      -->     String-Methoden anwenden
 * version 2.2 2020.11.17      -->     @overriding, @overloading, .toString()
 *                                     wurde von allergen.Allergen zu kiParameter.KiParameter umbenannt und kann verschiedene KiParameter darstellen
 * version 2.0 2020.11.04      -->     vererbt von der Superklasse model.kiTableObject.KiTableObject
 * version 1.0 2020.10.28
 * @since 1.14
 */
@Entity
@Table(name = "KiParameter")
@NamedQueries({
        @NamedQuery(
                name = "kiParameter.findByIdAndParametergruppe",
                query = "SELECT k FROM KiParameter k WHERE k.parameterGruppe = :parameterGruppe AND k.id = :id"),
        @NamedQuery(
                name = "kiParameter.findDoubleKiParameter",
                query = "SELECT k FROM KiParameter k WHERE k.parameterGruppe = :parameterGruppe AND (k.kuerzel = :kuerzel OR k.bezeichnung = :bezeichnung)"),
        @NamedQuery(
                name = "kiParameter.findAllKiParameterByPraefixAndParametergruppeOrderByIdDesc",
                query = "SELECT k FROM KiParameter k WHERE k.praefix = :praefix AND k.parameterGruppe = :parameterGruppe ORDER BY k.id desc"),
        @NamedQuery(
                name = "kiParameter.findAllKiParameterByPraefixAndParametergruppe",
                query = "SELECT k FROM KiParameter k WHERE k.praefix = :praefix AND k.parameterGruppe = :parameterGruppe"),
        @NamedQuery(
                name = "kiParameter.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung",
                query = "SELECT k FROM KiParameter k WHERE k.praefix = :praefix AND k.parameterGruppe = :parameterGruppe ORDER BY k.bezeichnung"),
        @NamedQuery(
                name = "kiParameter.findKiParameterByPraefixParametergruppeAndBezeichnung",
                query = "SELECT k FROM KiParameter k WHERE k.praefix = :praefix AND k.parameterGruppe = :parameterGruppe AND k.bezeichnung = :bezeichnung"),
        @NamedQuery(
                name = "kiParameter.findKiParameterByPraefixParametergruppeAndKuerzel",
                query = "SELECT k FROM KiParameter k WHERE k.praefix = :praefix AND k.parameterGruppe = :parameterGruppe AND k.kuerzel = :kuerzel"),
})
public class KiParameter extends KiTableObject {
    // ***************************************************************************************************************************************************************************
    // Objekt-Variable deklarieren
    // ***************************************************************************************************************************************************************************
    @Column(columnDefinition = "varchar(50) not null")
    private String parameterGruppe;                             // Konstruktor
    @Column(columnDefinition = "varchar(3) not null")
    private String kuerzel;                                     // Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Parametrisierter Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiParameter
    (String pPraefix, int pId, String pGroup, String pUser, String pBezeichnung, String pInfoText, String pKuerzel, String pParameterGruppe) {
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Konstruktor Superklasse model.kiTableObject.KiTableObject aufrufen
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
        super("com.javahelps.kiPferL.model.kiTableObject.kiParameter", pPraefix, pId, "KiParameter", "", pGroup, pUser, pBezeichnung, pInfoText);
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Variable mit eingegebenen Parametern definieren
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
        this.parameterGruppe = pParameterGruppe;
        this.kuerzel = pKuerzel;
    }                                    // Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Default Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiParameter() {
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Default Konstruktor Superklasse model.kiTableObject.KiTableObject aufrufen
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
        super();
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Eigenschaften abfragen oder manipulieren       -->     Zugriff von anderen Klassen aus
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String getParameterGruppe() {
        return this.parameterGruppe;
    }
    public void setParameterGruppe(String parameterGruppe) {
        this.parameterGruppe = parameterGruppe;
    }

    public String getKuerzel() {
        return this.kuerzel;
    }
    public void setKuerzel(String kuerzel) {
        this.kuerzel = kuerzel;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Neues KiParameterObjekt, das per GUI eingetragen wurde, in DB-TABELLE KiParameter speichern
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String doInsertIntoKiParameter() {
        String temp;
        // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // nicht doppelt    -->     Parameter wird eingetragen, entsprechende Meldung wird zurückgegeben
        if(!KiParameterJPA.findDoubleKiParameter(MainFrameWindowAdapter.getEntityManagerFactory(), this.parameterGruppe, this.kuerzel, this.getBezeichnung())) {
            temp = KiParameterJPA.doInsertKiParameter(
                    MainFrameWindowAdapter.getEntityManagerFactory(), this.getId(), this.getPraefix(), this.getBezeichnung(), this.parameterGruppe, this.kuerzel, this.getInfoText(), this.getDateOfCreation(),
                        this.getModul(), this.getProgram(), "doInsertIntoKiParameter");
        }
        // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // doppelt    -->     Entsprechende Meldung wird zurückgegeben
        else {
            temp = "Parameter bereits vorhanden.";
        }
        return temp;
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // KiParameterObject, das via GUI geändert wurde, wird in der DB-Tabelle KiParameter geändert
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String doUpdateKiParameter() {
        String temp;
        temp = KiParameterJPA.doUpdateKiParameter(
                MainFrameWindowAdapter.getEntityManagerFactory(), this.getId(), this.getBezeichnung(), this.kuerzel, this.getInfoText(), this.getModul(),
                this.getProgram(), "doUpdateKiParameter");
        return temp;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // AUSGABE
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Einzeilige Ausgabe der Eigenschaften
    // Überschriebene Methode model.kiTableObject.KiTableObject.toString()
    // IJ: Systemausgabe automatisch schreiben (in eine leere Zeile klicken ContextMenü/Generate/toString())
    @Override
    public String toString() {
        return new StringBuilder().append("KiParameter{ ").append(super.toString()).append
                (", \nparameterGruppe='").append(this.parameterGruppe).append
                ("', \nkuerzel='").append(this.kuerzel).append("'}").toString();
    }
    // ****************************************************************************************************************************************************************************
    // Einzeilige Ausgabe eingerückt (für kiKochBuch.rezept.Rezept.show(boolean, int))
    public String show() {
        return "\n\t" + this.kuerzel + " / " + this.getBezeichnung();
    }
    // ****************************************************************************************************************************************************************************
    // Einzeilige Ausgabe eingerückt mit rezeptSpezifischem Infotext (für kiKochBuch.rezept.Rezept.show(boolean, int))
    public String show(String pInfoText) {
        return new StringBuilder().append("\n\t").append(this.kuerzel).append(" / ").append(this.getBezeichnung()).append("   -->   ").append(pInfoText).toString();
    }
}
