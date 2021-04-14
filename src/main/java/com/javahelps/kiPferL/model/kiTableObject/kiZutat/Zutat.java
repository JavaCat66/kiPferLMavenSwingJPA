package com.javahelps.kiPferL.model.kiTableObject.kiZutat;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.jpa.kiZutat.ZutatJPA;
import com.javahelps.kiPferL.model.kiTableObject.KiTableObject;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.lagerFaehigkeit.LagerFaehigKeit;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Tabellenklasse, vererbt von KiTableObject
 * ZUTAT wird in model.kiTableObject.kiKochBuch.rezept.Rezept verwendet
 * 1 - N Zutaten werden mittels Konstruktor in start.Start.mainFrame() erstellt und an ein Rezept übergeben
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 1.0 2020.12.26      -->     Collections, InnerAnonym, Lambda, MethodReference
 *                                    -->     beide TimeStamps (Creation und Change) werden nicht mehr übergeben, sondern im Konstruktor von KiTableObjekt befüllt
 *                                    -->     Verwendung von StringBuilder.append() statt String.concat(). Weil schneller.
 * version 2.5 2020.11.24      -->     static KlassenVariable zutatID, neue KiParameter
 * version 2.4 2020.11.18      -->     neues PropertyObjekt LagerFaehigKeit (ArrayList<> mit 4 bestimmten KiParametern)
 * version 2.3 2020.11.18      -->     String-Methoden anwenden
 * version 2.2 2020.11.17      -->     @overriding, @overloading, java.lang.Object.toString()
 * version 2.0 2020.11.04      -->     vererbt von der Superklasse KiTableObject
 * version 1.0 2020.10.28
 * @since 1.14
 */
@Entity
@Table(name = "Zutat")
@NamedQueries({
        @NamedQuery(
                name = "zutat.findAllZutatByEinkaufsKategorieOrderByIdDesc",
                query = "SELECT z FROM Zutat z WHERE z.einkaufsKategorie = :id ORDER BY z.id DESC"),
        @NamedQuery(
                name = "zutat.findAllZutatByEinkaufsKategorieOrderById",
                query = "SELECT z FROM Zutat z WHERE z.einkaufsKategorie = :id ORDER BY z.id"),
        @NamedQuery(
                name = "zutat.findDoubleZutat",
                query = "SELECT z FROM Zutat z WHERE z.bezeichnung = :bezeichnung"),
        @NamedQuery(
                name = "zutat.findAllZutatByEinkaufsKategorieIdOrderByBezeichnung",
                query = "SELECT z FROM Zutat z WHERE z.einkaufsKategorie = :id ORDER BY z.bezeichnung"),
        @NamedQuery(
                name = "zutat.findByIdAndEinkaufsKategorieId",
                query = "SELECT z FROM Zutat z WHERE z.einkaufsKategorie = :einkaufsKategorieId AND z.id = :id"),
})
public class Zutat extends KiTableObject {
    // ********************************************************************************************************************************************************************************
    // KlassenVariable deklarieren
    // ********************************************************************************************************************************************************************************
    @Transient
    private static int zutatID = 0;                             // static KlassenVariable zum Hochzählen der ID
    // ********************************************************************************************************************************************************************************
    // ObjektVariable deklarieren
    // ********************************************************************************************************************************************************************************
    @Column(columnDefinition = "varchar(7) not null")
    private String einkaufsKategorie;
    @Transient
    private KiParameter einkaufsKategorieObj;                   // Einkaufskategorie, Pflichtfeld
    @Column(columnDefinition = "varchar(7) not null")
    private String masseinheitRezept;
    @Transient
    private KiParameter masseinheitRezeptObj;                   // Maßeinheit Rezept, Pflichtfeld
    @Column(columnDefinition = "varchar(7) not null")
    private String masseinheitHandel;
    @Transient
    private KiParameter masseinheitHandelObj;                   // Maßeinheit Handel, Pflichtfeld
    @Column(columnDefinition = "decimal(8,2) not null")
    private double kleinsteVerkaufsEinheit;                     // Kleinste Verkaufseinheit, Pflichtfeld
    @Column(columnDefinition = "decimal(8,2) not null")
    private double kommissionierungsEinheit;                    // Kommissionierungseinheit, Pflichtfeld
    @Column(columnDefinition = "decimal(8,2) not null")
    private double kiloGrammAequivalent;                        // Kilogrammäquivalent, Pflichtfeld
    @Column(columnDefinition = "int(5) not null")
    private int naehrWertProHundertGramm;                       // Nährwert kKal, Pflichtfeld
    @Column(columnDefinition = "decimal(8,2) not null")
    private double brotEinheitenProHundertGramm;                // Broteinheiten BE, Pflichtfeld
    @Transient
    private String lagerDauer;
    @Transient
    private String lagerTemperatur;
    @Transient
    private String lagerBeleuchtung;
    @Transient
    private String lagerLuftFeuchtigkeit;
    @Column(columnDefinition = "varchar(7) not null")
    private String lagerFaehigKeit;
    @Transient
    private LagerFaehigKeit lagerFaehigKeitObj;                 // LagerFaehigKeit-Objekt mit 4 KiParameter (LD, LT, LB, LL)
    // mögliche KiParameterGruppen
    @Column(columnDefinition = "varchar(80) null")
    private String kiParameterAL;
    @Transient
    private Set<KiParameter> kiParameterALSet;                  // möglicher Behälter für KiParameter (Allergen) wird im Konstruktor erzeugt (NOT NULL)
    //                                                              und über this.addKiParameter(KiParameter) befüllt

    @Column(columnDefinition = "varchar(80) null")
    private String kiParameterDT;
    @Transient
    private Set<KiParameter> kiParameterDTSet;                  // möglicher Behälter für KiParameter (DiätTauglichkeiten) wird im Konstruktor erzeugt (NOT NULL)
    //                                                              und über this.addKiParameter(KiParameter) befüllt
    @Transient
    private int tempI;                                          // Hilfsvariable

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Default Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public Zutat() {
        super();
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Parametrisierter Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public Zutat(String pMethod, String pGroup, String pUser, String pBezeichnung, KiParameter pEinkaufsKategorie, KiParameter pMasseinheitRezept, KiParameter pMasseinheitHandel,
                 double pKleinsteVerkaufsEinheit, double pKommissionierungsEinheit, double pKilogrammAequivalent, int pNaehrWertProHundertGramm, double pBrotEinheitenProHundertGramm,
                 LagerFaehigKeit pLagerFaehigKeit, String pInfoText) {
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Konstruktor Superklasse model.kiTableObject.KiTableObject aufrufen
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        super("com.javahelps.kiPferL.model.kiTableObject.kiZutat", "ZT", ++zutatID, "Zutat", pMethod, pGroup, pUser, pBezeichnung, pInfoText);
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Variable mit eingegebenen Parametern definieren
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // einkaufsKategorieObj = kiParameter    -->     wird geprüft, falls NULL wird auf default = "EK Maßeinheit keine Angabe KA" gesetzt       -->     DEFAULTWERT=NULL erlaubt!
        if(pEinkaufsKategorie != null) {
            // masseinheitRezeptObj = kiParameter    -->     wird geprüft, falls anderer KiParameter als "ME Maßeinheit", wird auf default = "ME Maßeinheit keine Angabe KA" gesetzt
            if (pEinkaufsKategorie.getPraefix().equals("EK")) {
                this.einkaufsKategorieObj = pEinkaufsKategorie;
            }
            // NOT NULL
            else {
                this.einkaufsKategorieObj = new KiParameter
                        ("EK", 0, pGroup, pUser, "keine Angabe", "Konstruktor Zutat: automatisch erstellter Parameter",
                                "KA", "EinkaufsKategorie");
            }
        }
        // NOT NULL
        else {
            this.einkaufsKategorieObj = new KiParameter
                    ("EK", 0, pGroup, pUser, "keine Angabe", "Konstruktor Zutat: automatisch erstellter Parameter",
                            "KA", "EinkaufsKategorie");
        }
        // masseinheitRezeptObj = kiParameter    -->     wird geprüft, falls NULL wird auf default = "ME Maßeinheit keine Angabe KA" gesetzt       -->     DEFAULTWERT=NULL erlaubt!
        if(pMasseinheitRezept != null) {
            // masseinheitRezeptObj = kiParameter    -->     wird geprüft, falls anderer KiParameter als "ME Maßeinheit", wird auf default = "ME Maßeinheit keine Angabe KA" gesetzt
            if (pMasseinheitRezept.getPraefix().equals("ME")) {
                this.masseinheitRezeptObj = pMasseinheitRezept;
                this.masseinheitRezeptObj.setInfoText("RE");
            }
            // NOT NULL
            else {
                this.masseinheitRezeptObj = new KiParameter
                        ("ME", 0, pGroup, pUser, "keine Angabe", "RE / Konstruktor Zutat: automatisch erstellter Parameter",
                                "KA", "Maßeinheit");
            }
        }
        // NOT NULL
        else {
            this.masseinheitRezeptObj = new KiParameter
                    ("ME", 0, pGroup, pUser, "keine Angabe", "RE / Konstruktor Zutat: automatisch erstellter Parameter",
                            "KA", "Maßeinheit");
        }
        // masseinheitRezeptObj = kiParameter    -->     wird geprüft, falls NULL wird auf default = "ME Maßeinheit keine Angabe KA" gesetzt       -->     DEFAULTWERT=NULL erlaubt!
        if(pMasseinheitHandel != null) {
            // masseinheitHandelObj = kiParameter    -->     wird geprüft, falls anderer KiParameter als "ME Maßeinheit", wird auf default = "ME Maßeinheit keine Angabe KA" gesetzt
            if (pMasseinheitHandel.getPraefix().equals("ME")) {
                this.masseinheitHandelObj = pMasseinheitHandel;
                this.masseinheitHandelObj.setInfoText("HA");
            }
            // NOT NULL
            else {
                this.masseinheitHandelObj = new KiParameter
                        ("ME", 0, pGroup, pUser, "keine Angabe", "HA / Konstruktor Zutat: automatisch erstellter Parameter",
                                "KA", "Maßeinheit");
            }
        }
        // NOT NULL
        else {
            this.masseinheitHandelObj = new KiParameter
                    ("ME", 0, pGroup, pUser, "keine Angabe", "HA / Konstruktor Zutat: automatisch erstellter Parameter",
                            "KA", "Maßeinheit");
        }
        this.kleinsteVerkaufsEinheit = pKleinsteVerkaufsEinheit;
        this.kommissionierungsEinheit = pKommissionierungsEinheit;
        this.kiloGrammAequivalent = pKilogrammAequivalent;
        this.naehrWertProHundertGramm = pNaehrWertProHundertGramm;
        this.brotEinheitenProHundertGramm = pBrotEinheitenProHundertGramm;
        // wird als LagerFaehigKeit NULL übergeben, wird ein leeres LagerFaehigKeit-Objekt erstellt
        if(pLagerFaehigKeit != null) {
            this.lagerFaehigKeitObj = pLagerFaehigKeit;
        } else {
            this.lagerFaehigKeitObj = new LagerFaehigKeit(0, pGroup, pUser, null);
        }
        // Allergene
        this.kiParameterALSet = new TreeSet<>(Comparator.comparing(KiParameter::getKuerzel));
        // DiätTauglichKeiten
        this.kiParameterDTSet = new TreeSet<>(Comparator.comparing(KiParameter::getKuerzel));
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Parametrisierter Konstruktor - Daten aus dem Formular
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public Zutat(int pId, String pGroup, String pUser, String pBezeichnung, String pInfoText, String pEinkaufsKategorieKuerzel, String pEinkaufsKategorieId,
                 String pMasseinheitRezeptID, String pMasseinheitHandelID, double pKleinsteVerkaufsEinheit, double pKommissionierungsEinheit, double pKilogrammAequivalent,
                 int pNaehrWertProHundertGramm, double pBrotEinheitenProHundertGramm, String pLagerFaehigKeitDauer, String pLagerFaehigKeitTemperatur,
                 String pLagerFaehigKeitBeleuchtung, String pLagerFaehigKeitLuftFeuchtigkeit, String pkiParameterAL, String pKiParameterDT) {
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Konstruktor Superklasse model.kiTableObject.KiTableObject aufrufen
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        super("com.javahelps.kiPferL.model.kiTableObject.kiZutat", pEinkaufsKategorieKuerzel, pId, "Zutat", "", pGroup, pUser, pBezeichnung, pInfoText);
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Variable mit eingegebenen Parametern definieren
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        this.einkaufsKategorie = pEinkaufsKategorieId;
        this.masseinheitRezept = pMasseinheitRezeptID;
        this.masseinheitHandel = pMasseinheitHandelID;
        this.kleinsteVerkaufsEinheit = pKleinsteVerkaufsEinheit;
        this.kommissionierungsEinheit = pKommissionierungsEinheit;
        this.kiloGrammAequivalent = pKilogrammAequivalent;
        this.naehrWertProHundertGramm = pNaehrWertProHundertGramm;
        this.brotEinheitenProHundertGramm = pBrotEinheitenProHundertGramm;
        this.lagerDauer = pLagerFaehigKeitDauer;
        this.lagerTemperatur = pLagerFaehigKeitTemperatur;
        this.lagerBeleuchtung = pLagerFaehigKeitBeleuchtung;
        this.lagerLuftFeuchtigkeit = pLagerFaehigKeitLuftFeuchtigkeit;
        this.kiParameterAL = pkiParameterAL;
        this.kiParameterDT = pKiParameterDT;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // GETTER UND SETTER
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String getEinkaufsKategorie() {
        return einkaufsKategorie;
    }
    public void setEinkaufsKategorie(String einkaufsKategorie) {
        this.einkaufsKategorie = einkaufsKategorie;
    }
    public KiParameter getEinkaufsKategorieObj() {
        return einkaufsKategorieObj;
    }
    public void setEinkaufsKategorieObj(KiParameter einkaufsKategorieObj) {
        this.einkaufsKategorieObj = einkaufsKategorieObj;
    }

    public String getMasseinheitRezept() {
        return masseinheitRezept;
    }
    public void setMasseinheitRezept(String masseinheitRezept) {
        this.masseinheitRezept = masseinheitRezept;
    }
    public void setMasseinheitRezeptObj(KiParameter masseinheitRezeptObj) {
        this.masseinheitRezeptObj = masseinheitRezeptObj;
    }
    public KiParameter getMasseinheitRezeptObj() {
        return masseinheitRezeptObj;
    }

    public String getMasseinheitHandel() {
        return masseinheitHandel;
    }
    public void setMasseinheitHandel(String masseinheitHandel) {
        this.masseinheitHandel = masseinheitHandel;
    }
    public KiParameter getMasseinheitHandelObj() {
        return masseinheitHandelObj;
    }
    public void setMasseinheitHandelObj(KiParameter masseinheitHandelObj) {
        this.masseinheitHandelObj = masseinheitHandelObj;
    }

    public double getKleinsteVerkaufsEinheit() {
        return kleinsteVerkaufsEinheit;
    }
    public void setKleinsteVerkaufsEinheit(double kleinsteVerkaufsEinheit) {
        this.kleinsteVerkaufsEinheit = kleinsteVerkaufsEinheit;
    }

    public double getKommissionierungsEinheit() {
        return kommissionierungsEinheit;
    }
    public void setKommissionierungsEinheit(double kommissionierungsEinheit) {
        this.kommissionierungsEinheit = kommissionierungsEinheit;
    }

    public void setKiloGrammAequivalent(double kiloGrammAequivalent) {
        this.kiloGrammAequivalent = kiloGrammAequivalent;
    }
    public double getKiloGrammAequivalent() {
        return kiloGrammAequivalent;
    }

    public void setNaehrWertProHundertGramm(int naehrWertProHundertGramm) {
        this.naehrWertProHundertGramm = naehrWertProHundertGramm;
    }
    public int getNaehrWertProHundertGramm() {
        return naehrWertProHundertGramm;
    }

    public void setBrotEinheitenProHundertGramm(double brotEinheitenProHundertGramm) {
        this.brotEinheitenProHundertGramm = brotEinheitenProHundertGramm;
    }
    public double getBrotEinheitenProHundertGramm() {
        return brotEinheitenProHundertGramm;
    }

    public String getKiParameterAL() {
        return kiParameterAL;
    }
    public void setKiParameterAL(String kiParameterAL) {
        this.kiParameterAL = kiParameterAL;
    }
    public void setKiParameterALSet(Set<KiParameter> kiParameterAL) {
        this.kiParameterALSet = kiParameterAL;
    }
    public Set<KiParameter> getKiParameterALSet() {
        return this.kiParameterALSet;
    }

    public String getKiParameterDT() {
        return kiParameterDT;
    }
    public void setKiParameterDT(String kiParameterDT) {
        this.kiParameterDT = kiParameterDT;
    }
    public void setKiParameterDTSet(Set<KiParameter> kiParameterDT) {
        this.kiParameterDTSet = kiParameterDT;
    }
    public Set<KiParameter> getKiParameterDTSet() {
        return kiParameterDTSet;
    }

    public String getLagerDauer() {
        return lagerDauer;
    }
    public void setLagerDauer(String lagerDauer) {
        this.lagerDauer = lagerDauer;
    }
    public String getLagerTemperatur() {
        return lagerTemperatur;
    }
    public void setLagerTemperatur(String lagerTemperatur) {
        this.lagerTemperatur = lagerTemperatur;
    }
    public String getLagerBeleuchtung() {
        return lagerBeleuchtung;
    }
    public void setLagerBeleuchtung(String lagerBeleuchtung) {
        this.lagerBeleuchtung = lagerBeleuchtung;
    }
    public String getLagerLuftFeuchtigkeit() {
        return lagerLuftFeuchtigkeit;
    }
    public void setLagerLuftFeuchtigkeit(String lagerLuftFeuchtigkeit) {
        this.lagerLuftFeuchtigkeit = lagerLuftFeuchtigkeit;
    }
    public String getLagerFaehigKeit() {
        return lagerFaehigKeit;
    }
    public void setLagerFaehigKeit(String lagerFaehigKeit) {
        this.lagerFaehigKeit = lagerFaehigKeit;
    }
    public LagerFaehigKeit getLagerFaehigKeitObj() {
        return lagerFaehigKeitObj;
    }
    public void setLagerFaehigKeitObj(LagerFaehigKeit lagerFaehigKeit) {
        this.lagerFaehigKeitObj = lagerFaehigKeit;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Neues ZutatObjekt, das per GUI eingetragen wurde, in DB-TABELLE KiParameter speichern
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String doInsertIntoZutat() {
        String temp;
        // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // nicht doppelt    -->     Parameter wird eingetragen, entsprechende Meldung wird zurückgegeben
        if(!ZutatJPA.findDoubleZutat(MainFrameWindowAdapter.getEntityManagerFactory(), this.getBezeichnung())) {
            temp = ZutatJPA.doInsertZutat(
                    MainFrameWindowAdapter.getEntityManagerFactory(), this.getId(), this.getPraefix(), this.getBezeichnung(), this.getInfoText(), this.einkaufsKategorie,
                    this.masseinheitRezept, this.masseinheitHandel, this.kleinsteVerkaufsEinheit, this.kommissionierungsEinheit, this.kiloGrammAequivalent,
                    this.naehrWertProHundertGramm, this.brotEinheitenProHundertGramm, this.lagerDauer, this.lagerTemperatur, this.lagerBeleuchtung, this.lagerLuftFeuchtigkeit,
                    this.kiParameterAL, this.kiParameterDT, this.getDateOfCreation(), this.getModul(), this.getProgram(), "doInsertIntoZutat");
        }
        // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // doppelt    -->     Entsprechende Meldung wird zurückgegeben
        else {
            temp = "Zutat bereits vorhanden.";
        }
        return temp;
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // ZutatObject, das via GUI geändert wurde, wird in der DB-Tabelle KiParameter geändert
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String doUpdateZutat() {
        String temp;
        temp = ZutatJPA.doUpdateZutat(
                MainFrameWindowAdapter.getEntityManagerFactory(), this.getId(), this.getBezeichnung(), this.getInfoText(), this.einkaufsKategorie,
                this.masseinheitRezept, this.masseinheitHandel, this.kleinsteVerkaufsEinheit, this.kommissionierungsEinheit, this.kiloGrammAequivalent,
                this.naehrWertProHundertGramm, this.brotEinheitenProHundertGramm, this.lagerDauer, this.lagerTemperatur, this.lagerBeleuchtung, this.lagerLuftFeuchtigkeit,
                this.kiParameterAL, this.kiParameterDT, this.getDateOfCreation(), this.getModul(), this.getProgram(), "doUpdateZutat");
        return temp;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // KIPARAMETER
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // ********************************************************************************************************************************************************************************
    // KiParameter zur Zutat hinzufügen
    // ********************************************************************************************************************************************************************************
    // durch Sammeln in Set kann jeder KiParameter nur 1x vorhanden sein
    // Rückgabe INFO
    public String addKiParameter(KiParameter pKiParameter) {
        StringBuilder temp = new StringBuilder();
        if(pKiParameter != null) {
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Allergene
            if (pKiParameter.getPraefix().equalsIgnoreCase("AL")) {
                this.kiParameterALSet.add(pKiParameter);
                temp.append("LOG: ").append(pKiParameter.getParameterGruppe()).append(" ID='").append(pKiParameter.getId()).append("' BEZN='").append(pKiParameter.getBezeichnung()).append
                        ("' zur Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' hinzugefügt.");
            }
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // DiätTauglichKeiten
            else if (pKiParameter.getPraefix().equalsIgnoreCase("DT")) {
                this.kiParameterDTSet.add(pKiParameter);
                temp.append("LOG: ").append(pKiParameter.getParameterGruppe()).append(" ID='").append(pKiParameter.getId()).append("' BEZN='").append(pKiParameter.getBezeichnung()).append
                        ("' zur Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' hinzugefügt.");
            }
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // unbekanntes KiParameter-Objekt
            else {
                temp.append("LOG: KiParameter ID='").append(pKiParameter.getId()).append("' BEZN='").append(pKiParameter.getBezeichnung()).append
                        ("' konnte nicht zur Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append
                        ("' hinzugefügt werden. Unbekanntes Präfix '").append(pKiParameter.getId(), 0, 2).append("'.");
            }
        } else {
            temp.append("LOG: KiParameter konnte nicht zur Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' hinzugefügt werden. NULL.");
        }
        return temp.toString();
    }
    // ********************************************************************************************************************************************************************************
    // ALLE KiParameter einer bestimmten ParameterGruppe aus der Zutat abfragen (Allergene, DiätTauglichKeiten)
    // ********************************************************************************************************************************************************************************
    public Set<KiParameter> getKiParameter(String pPraefix) {
        if(pPraefix != null && pPraefix.equalsIgnoreCase("AL")) {
            return this.getKiParameterALSet();
        }
        else if(pPraefix != null && pPraefix.equalsIgnoreCase("DT")) {
            return this.getKiParameterDTSet();
        }
        else {
            return null;
        }
    }
    // ********************************************************************************************************************************************************************************
    // KiParameter aus der Zutat löschen
    // ********************************************************************************************************************************************************************************
    // KiParameter.id wird übergeben                -->             String.equalsIgnoreCase(String) ist NICHT caseSensitiv
    // Unsicher! Kontrolltext wird zurückgegeben
    public String removeKiParameter(String pId) {
        StringBuilder temp = new StringBuilder();
        if(pId != null) {
            // Kontrollliste, ob Zutat vorhanden ist und zum Speichern derselbigen
            List<KiParameter> tempList = new ArrayList<>();
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Allergene
            if(pId.substring(0, 2).equalsIgnoreCase("AL")) {
//                // LAMBDA   -->     KiParameterBehälter (Set<>) in Stream umwandeln, nach KiParameterID filtern, Ergebnis in KontrollList füllen (kann maximal 1 groß sein)
//                this.kiParameterAL.stream().filter(kiParameter -> kiParameter.getId().equalsIgnoreCase(pId)).forEach(kiParameter -> tempList.add(kiParameter));
                // MethodReference
                this.kiParameterALSet.stream().filter(kiParameter -> kiParameter.getId().equalsIgnoreCase(pId)).forEach(tempList::add);
                // Kontrollliste mit Inhalt        -->     KiParameter vorhanden, aus KiParameterBehälter löschen +  Meldung
                if(tempList.size() > 0) {
                    temp.append("LOG: ").append(tempList.get(0).getParameterGruppe()).append(" ID='").append(tempList.get(0).getId()).append("' BEZN='").append
                            (tempList.get(0).getBezeichnung()).append("' wurde aus Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' gelöscht.");
                    this.kiParameterALSet.remove(tempList.get(0));
                }
                // Kontrollliste ohne Inhalt       -->     KiParameter nicht vorhanden, kann nicht gelöscht werden +  Meldung
                else {
                    temp.append("LOG: KiParameter ID='").append(pId.toUpperCase()).append("' konnte nicht aus Zutat ID='").append(this.getId()).append("' BEZN='").append
                            (this.getBezeichnung()).append("' gelöscht werden, weil er entweder nicht existiert oder nicht erkannt wurde.");
                }
            }
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Diättauglichkeit
            else if(pId.substring(0, 2).equalsIgnoreCase("DT")) {
                this.kiParameterDTSet.stream().filter(kiParameter -> kiParameter.getId().equalsIgnoreCase(pId)).forEach(tempList::add);
                if(tempList.size() > 0) {
                    temp.append("LOG: ").append(tempList.get(0).getParameterGruppe()).append(" ID='").append(tempList.get(0).getId()).append("' BEZN='").append
                            (tempList.get(0).getBezeichnung()).append("' wurde aus Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' gelöscht.");
                    this.kiParameterDTSet.remove(tempList.get(0));
                }
                else {
                    temp.append("LOG: KiParameter ID='").append(pId.toUpperCase()).append("' konnte nicht aus Zutat ID='").append(this.getId()).append("' BEZN='").append
                            (this.getBezeichnung()).append("' gelöscht werden, weil er entweder nicht existiert oder nicht erkannt wurde.");
                }
            }
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // unbekanntes Präfix
            else {
                temp.append("LOG: KiParameter ID='").append(pId.toUpperCase()).append("' konnte nicht aus Zutat ID='").append(this.getId()).append("' BEZN='").append
                        (this.getBezeichnung()).append("' gelöscht werden, unbekanntes Präfix.");
            }
        }
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // NULL
        else {
            temp.append("LOG: KiParameter konnte nicht aus Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' gelöscht werden, weil ID=NULL.");
        }
        return temp.toString();
    }
    // ********************************************************************************************************************************************************************************
    // KiParameter-Objekt wird übergeben
    // Unsicher! Kontrolltext wird zurückgegeben
    public String removeKiParameter(KiParameter pKiParameter) {
        StringBuilder temp = new StringBuilder();
        if(pKiParameter != null) {
            List<KiParameter> tempList = new ArrayList<>();
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Allergene
            if (pKiParameter.getId().substring(0, 2).equalsIgnoreCase("AL")) {
//                // LAMBDA
//                this.kiParameterAL.stream().filter(kiParameter -> kiParameter.equals(pKiParameter)).forEach(kiParameter -> tempList.add(kiParameter));
                // MethodReference
                this.kiParameterALSet.stream().filter(kiParameter -> kiParameter.equals(pKiParameter)).forEach(tempList::add);
                // Kontrollliste mit Inhalt        -->     KiParameter vorhanden, aus KiParameterBehälter löschen +  Meldung
                if(tempList.size() > 0) {
                    temp.append("LOG: ").append(tempList.get(0).getParameterGruppe()).append(" ID='").append(tempList.get(0).getId()).append("' BEZN='").append
                            (tempList.get(0).getBezeichnung()).append("' wurde aus Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' gelöscht.");
                    this.kiParameterALSet.remove(tempList.get(0));
                }
                // Kontrollliste ohne Inhalt       -->     KiParameter nicht vorhanden, kann nicht gelöscht werden +  Meldung
                else {
                    temp.append("LOG: ").append(pKiParameter.getParameterGruppe()).append(" ID='").append(pKiParameter.getId()).append("' BEZN='").append
                            (pKiParameter.getBezeichnung()).append("' konnte nicht aus Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append
                            ("' gelöscht werden, weil er entweder nicht existiert oder nicht erkannt wurde.");
                }
            }
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // DiätTauglichKeiten
            else if (pKiParameter.getId().substring(0, 2).equalsIgnoreCase("DT")) {
                this.kiParameterDTSet.stream().filter(kiParameter -> kiParameter.equals(pKiParameter)).forEach(tempList::add);
                if(tempList.size() > 0) {
                    this.kiParameterDTSet.remove(tempList.get(0));
                    temp.append("LOG: ").append(tempList.get(0).getParameterGruppe()).append(" ID='").append(tempList.get(0).getId()).append("' BEZN='").append
                            (tempList.get(0).getBezeichnung()).append("' wurde aus Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' gelöscht.");
                }
                else {
                    temp.append("LOG: ").append(pKiParameter.getParameterGruppe()).append(" ID='").append(pKiParameter.getId()).append("' BEZN='").append
                            (pKiParameter.getBezeichnung()).append("' konnte nicht aus Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append
                            ("' gelöscht werden, weil er entweder nicht existiert oder nicht erkannt wurde.");
                }
            }
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // unbekanntes Präfix
            else {
                temp.append("LOG: KiParameter ID='").append(pKiParameter.getId()).append("' BEZN='").append(pKiParameter.getBezeichnung()).append("' ").append
                        ("konnte nicht aus Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' gelöscht werden, unbekanntes Präfix.");
            }
        }
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // NULL
        else {
            temp.append("LOG: KiParameter konnte nicht aus Zutat ID='").append(this.getId()).append("' BEZN='").append(this.getBezeichnung()).append("' gelöscht werden, weil er NULL ist.");
        }
        return temp.toString();
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // AUSGABE
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Einfache Ausgabe der Eigenschaften
    // Überschriebene Methode model.kiTableObject.KiTableObject.toString()
    // IJ: Systemausgabe automatisch schreiben (in eine leere Zeile klicken ContextMenü/Generate/toString()
    @Override
    public String toString() {
        return new StringBuilder().append("\nZUTAT{ ").append(super.toString()).append(", \nEINKAUFSKATEGORIE=").append(this.einkaufsKategorie).toString();
               /* (", \nEINKAUFSKATEGORIE=").append(this.einkaufsKategorieObj).append
                (", \nMASSEINHEITRezept=").append(this.masseinheitRezeptObj).append
                (", \nMASSEINHEITHandel=").append(this.masseinheitHandelObj).append
                (", \nkleinsteVerkaufsEinheit=").append(this.kleinsteVerkaufsEinheit).append
                (", \nkommissionierungsEinheit=").append(this.kommissionierungsEinheit).append
                (", \nkiloGrammAequivalent=").append(this.kiloGrammAequivalent).append
                (", \nnaehrWertProHundertGramm=").append(this.naehrWertProHundertGramm).append
                (", \nbrotEinheitenProHundertGramm=").append(this.brotEinheitenProHundertGramm).append
                (", \nALLERGENE=").append(this.kiParameterAL).append
                (", \nDIAETTAUGLICHKEITEN=").append(this.kiParameterDT.toString()).append
                (", \nLAGERFAEHIGKEIT=").append(this.lagerFaehigKeit.toString()).append
                (", \ninfoText='").append(this.getInfoText()).append('}').toString();*/
    }
    // ********************************************************************************************************************************************************************************
    // Ausgabe einzeilig tabellarisch (Bezeichnung, Menge+Masseinheit, Allergene, Diätfähigkeit, Info)
    // boolean original sagt, ob das OriginalRezept = true oder ein RezeptNPortionen=false ausgegeben wird
    // int pAnzahlPortionen sagt, wieviele Portionen berechnet werden sollen, wenn es sich NICHT um das OriginalRezept handelt.
    // wird in der Methode model.kiTableObject.kiKochBuch.rezept.Rezept.show() aufgerufen
    public String show(boolean original, int pAnzahlPortionen) {
        // Zur ZahlenFormatierung
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("de", "DE"));
        df.applyPattern("#,###,###,##0.00");

        StringBuilder temp = new StringBuilder();

        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // MethodReference (inner Class) zur Ausgabe der Kürzel von Allergenen und Diättauglichkeiten
        class MyMethodRef {
            private int x;
            private String myPraefix;
            public MyMethodRef(String pPraefix) {
                this.x = 0;
                this.myPraefix = pPraefix;
            }
            public void myMethod(KiParameter kiParameter) {
                temp.append(kiParameter.getKuerzel());
                ++Zutat.this.tempI;
                if (this.x < Zutat.this.getKiParameter(this.myPraefix).size() - 1) {
                    temp.append(", ");
                    ++Zutat.this.tempI;
                }
                ++this.x;
            }
        }
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        temp.append("\n\t").append(this.getBezeichnung());                                                     // bezeichnung
        this.tempI = 56 - this.getBezeichnung().length();
        int tempJ = this.tempI / 4;
        temp.append(" ".repeat(Math.max(0, (this.tempI % 4))));
        temp.append("\t".repeat(Math.max(0, tempJ)));
        // OriginalRezept
        /*if(original) {
            if (this.menge > 0.0 &&
                    !(this.masseinheitRezeptObj.getKuerzel().equals("XX") || this.masseinheitRezeptObj.getKuerzel().equals("KA"))) {    // menge + masseinheitRezeptObj.id
                temp.append(df.format(this.menge)).append(" ").append(this.masseinheitRezeptObj.getKuerzel());
                this.tempI = 32 - (df.format(this.menge) + " " + this.masseinheitRezeptObj.getKuerzel()).length();
            } else {                                                                                           // masseinheitRezeptObj.bezeichnung, wenn menge = 0
                temp.append(this.masseinheitRezeptObj.getBezeichnung());
                this.tempI = 32 - this.masseinheitRezeptObj.getBezeichnung().length();
            }
        }
        // Rezept für N Portionen
        else {
            if (this.mengeProPortion > 0.0 &&
                    !(this.masseinheitRezeptObj.getKuerzel().equals("XX") || this.masseinheitRezeptObj.getKuerzel().equals("KA"))) {    // this.mengeProPortion * pAnzahlPortionen + masseinheitRezeptObj.id
                temp.append(df.format(Math.round(this.mengeProPortion * pAnzahlPortionen * 100.0) / 100.0)).append(" ").append(this.masseinheitRezeptObj.getKuerzel());
                this.tempI = 32 - (df.format(Math.round(this.mengeProPortion * pAnzahlPortionen * 100.0) / 100.0) + " " + this.masseinheitRezeptObj.getKuerzel()).length();
            } else {                                                                                           // masseinheitRezeptObj.bezeichnung, wenn menge = 0
                temp.append(this.masseinheitRezeptObj.getBezeichnung());
                this.tempI = 32 - this.masseinheitRezeptObj.getBezeichnung().length();
            }
        }*/
        tempJ = this.tempI / 4;
        temp.append(" ".repeat(Math.max(0, (this.tempI % 4))));
        temp.append("\t".repeat(Math.max(0, tempJ)));
        if (this.getKiParameterALSet().size() > 0) {                                                              // (falls vorhanden) "Allergene: " + 1-N kiParameter.kuerzel + Leerzeichen
            temp.append("Allergene: ");
            this.tempI = "Allergene: ".length();
            // MethodReference (inner Class MyMethodRef mit Methode myMethod(KiParameter))
            this.kiParameterALSet.forEach(new MyMethodRef("AL") :: myMethod);
        } else {
            this.tempI = 0;
        }
        this.tempI = 32 - this.tempI;
        tempJ = this.tempI / 4;
        temp.append(" ".repeat(Math.max(0, (this.tempI % 4))));
        temp.append("\t".repeat(Math.max(0, tempJ)));
        if (this.getKiParameterDTSet().size() > 0) {                                                              // (falls vorhanden) "Diättauglichkeit: " + 1-N kiParameter.kuerzel + Leerzeichen
            temp.append("Diättauglichkeiten: ");
            this.tempI = "Diättauglichkeiten: ".length();
            // MethodReference (inner Class MyMethodRef mit Methode myMethod(KiParameter))
            this.kiParameterDTSet.forEach(new MyMethodRef("DT") :: myMethod);
        } else {
            this.tempI = 0;
        }
        this.tempI = 44 - this.tempI;
        tempJ = this.tempI / 4;
        temp.append(" ".repeat(Math.max(0, (this.tempI % 4))));
        temp.append("\t".repeat(Math.max(0, tempJ)));
        if(!this.getInfoText().isEmpty()){                                                                     // "Info: " + infoText (falls vorhanden)
            temp.append("Info: ").append(this.getInfoText());
        }

        return temp.toString();
    }
}
