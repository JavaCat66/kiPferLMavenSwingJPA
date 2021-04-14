package com.javahelps.kiPferL.model.kiTableObject.kiParameter.lagerFaehigkeit;

import com.javahelps.kiPferL.model.kiTableObject.KiTableObject;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;

import javax.persistence.*;

import java.util.Set;

import static com.javahelps.kiPferL.model.kiTableObject.kiParameter.lagerFaehigkeit.LagerFaehigKeitParameterGruppe.*;


/**
 * Tabellenklasse, vererbt von KiTableObject
 * PropertyObjekt, besteht aus 4 bestimmten KiParametern
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * version Alpha 1.0 2020.12.26      -->     Collections, InnerAnonym, Lambda, MethodReference
 *                                    -->     beide TimeStamps (Creation und Change) werden nicht mehr übergeben, sondern im Konstruktor von KiTableObjekt befüllt
 *                                    -->     Verwendung von StringBuilder.append() statt String.concat(). Weil schneller.
 *                                    -->     Enum model.kiTableObject.kiParameter.lagerFaehigKeit.LagerFaehigKeitParameterGruppe für die 4 vorbestimmten KiParameterGruppen wird verwendet
 * version 2.5 2020.11.24      -->     SubKlasse von model.kiTableObject.kiParameter, beinhaltet je 1 KiParameter von 4 bestimmten KiParameterGruppen.
 *                             -->     2 static Variable String[] zum Festlegen der 4 gewünschten kiParameterGruppe und zugehörigem kiParameterPraefix.
 *                             -->     werden keine oder ungültige KiParameter übergeben, werden welche default "KA Keine Angabe" erzeugt und übergeben -> NULL-Werte erlaubt!
 * @since 1.14
 */
@Entity
@Table(name = "LagerFaehigKeit")
@NamedQueries({
        @NamedQuery(
                name = "lagerFaehigkeit.findLagerFaehigkeit",
                query = "SELECT l FROM LagerFaehigKeit l WHERE l.lagerDauer = :lagerDauer AND l.lagerTemperatur = :lagerTemperatur " +
                        "AND l.lagerBeleuchtung = :lagerBeleuchtung AND l.lagerLuftFeuchtigkeit = :lagerLuftFeuchtigkeit"),
        @NamedQuery(
                name = "lagerFaehigkeit.findMAXidLagerFaehigkeit",
                query = "SELECT l FROM LagerFaehigKeit l ORDER BY l.id DESC"),
        @NamedQuery(
                name = "lagerFaehigkeit.findLagerFaehigkeitById",
                query = "SELECT l FROM LagerFaehigKeit l WHERE l.id = :id"),
})
public class LagerFaehigKeit extends KiTableObject {
    // ********************************************************************************************************************************************************************************
    // Objekt-Variable deklarieren
    // ********************************************************************************************************************************************************************************
    @Transient
    private Set<KiParameter> lagerParameterRef;
    @Column(columnDefinition = "varchar(7) null")
    private String lagerDauer;
    @Column(columnDefinition = "varchar(7) null")
    private String lagerTemperatur;
    @Column(columnDefinition = "varchar(7) null")
    private String lagerBeleuchtung;
    @Column(columnDefinition = "varchar(7) null")
    private String lagerLuftFeuchtigkeit;

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Default Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public LagerFaehigKeit() {
        super();
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Parametrisierter Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public LagerFaehigKeit(int pId, String pGroup, String pUser, KiParameter[] pKiParameter) {
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Konstruktor Superklasse model.kiTableObject.KiTableObject aufrufen
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        super("com.javahelps.kiPferL.model.kiTableObject.kiParameter.lagerFaehigKeit", "LF", pId, "LagerFaehigKeit", "constructor", pGroup, pUser,
                "LagerFaehigKeit", "LagerDauer/LD,LagerTemperatur/LT,LagerBeleuchtung/LB,LagerLuftFeuchtigKeit/LL");
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // KiParameter werden alle auf Gültigkeit (NOT NULL, richtiges Präfix, richtige ParameterGruppe) geprüft
        // falls unrichtig, wird ein DEFAULT-Wert zugewiesen (ID = Präfix + 00000, "keine Angabe KA")       -->     NULL erlaubt!
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // LagerFaehigKeitsParameter werden über Enum LagerFaehigKeitParameterGruppe abgefragt
        int i = 0;
        for(LagerFaehigKeitParameterGruppe p : values()) {
            KiParameter temp = pKiParameter[i];
            if(temp != null) {
                if(temp.getPraefix().equals(p.getKiParameterPraefix()) && temp.getParameterGruppe().equals(p.getKiParameterGruppe())) {
                    this.lagerParameterRef.add(temp);
                }
                else {
                    this.lagerParameterRef.add(new KiParameter
                            (p.getKiParameterPraefix(), 0, pGroup, pUser, "keine Angabe", "", "XX", p.getKiParameterGruppe()));
                }
            }
            else {
                this.lagerParameterRef.add(new KiParameter
                        (p.getKiParameterPraefix(), 0, pGroup, pUser, "keine Angabe", "", "XX", p.getKiParameterGruppe()));
            }
            i++;
        }
    }


    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // GETTER UND SETTER
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public Set<KiParameter> getLagerParameterRef() {
        return lagerParameterRef;
    }
    public void setLagerParameterRef(Set<KiParameter> lagerParameterRef) {
        this.lagerParameterRef = lagerParameterRef;
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

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // AUSGABE
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Einzeilige Ausgabe der Eigenschaften
    // Überschriebene Methode model.kiTableObject.KiTableObject.toString()
    // IJ: Systemausgabe automatisch schreiben (in eine leere Zeile klicken ContextMenü/Generate/toString()
    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();

        temp.append("\nLagerFaehigKeit{ ").append(super.toString()).append
                (", \nKIPARAMETER{");
        this.lagerParameterRef.forEach(kiParameter -> {
            temp.append("\n");
            temp.append(kiParameter.toString());
        });
        temp.append("}");

        return temp.toString();
    }
}
