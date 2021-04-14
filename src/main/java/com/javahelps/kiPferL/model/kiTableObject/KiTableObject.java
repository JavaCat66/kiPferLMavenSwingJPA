package com.javahelps.kiPferL.model.kiTableObject;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Superklasse aller Tabellenklassen    -->     wurde im Zuge der Version 2.0 gemeinsam mit einer differenzierteren Package-Struktur hinzugefügt
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 1.0 2020.12.26      -->     Collections, InnerAnonym, Lambda, MethodReference
 *                                    -->     Verwendung von java.text.DecimalFormat zum formatieren der ID
 *                                    -->     Verwendung von java.text.SimpleDateFormat zur Erzeugung eines Zeitstempels für dateOfCreation und timeStampChange
 *                                            beide TimeStamps werden nun im Konstruktor befüllt und nicht mehr bei der Erstellung des Objekts übergeben
 *                                    -->     Verwendung von StringBuilder.append() statt String.concat(). Weil schneller.
 * version 2.8 2020.12.08      -->     Neue PackageStruktur für PersonenObjekte = kiPferLBenutzer, LagerTeilnehmerInnen, Adresse, Gruppe
 *                             -->     Serialisierung von Berechtigungen
 * version 2.3 2020.11.18      -->     String-Methoden anwenden
 * version 2.2 2020.11.17      -->     @overriding, @overloading, java.lang.Object.toString()
 * version 2.0 2020.11.04
 * @since 1.14
 */
@MappedSuperclass
public class KiTableObject implements Serializable {
    // ****************************************************************************************************************************************************************************
    // Objekt-Variable deklarieren      -->     mit Annotationen DB-Tabellenspalten definieren
    // ****************************************************************************************************************************************************************************
    @Id
    @Column(name = "id", columnDefinition = "varchar(7) unique=true not null")
    private String id;                                    // wird durch Erhöhen der vorhergehenden ID erzeugt ((Präfix Subklasse)00001 - (Präfix Subklasse)n)
    @Column(columnDefinition = "char(2) not null")
    private String praefix;                               // wird in der SubKlasse vergeben
    @Column(columnDefinition = "varchar(50) not null")
    private String bezeichnung;                           // Bezeichnung, wird in den SubObjekten unterschiedlich genutzt
    @Column(columnDefinition = "varchar(250)")
    private String infoText;                              // InfoText, wird in den SubObjekten unterschiedlich genutzt
    @Column(columnDefinition = "date not null")
    private String dateOfCreation;                        // Datumsfeld yyyy-MM-dd, Tag, an dem das Objekt erstellt wurde
    @Version
    private Timestamp timeStampChange;                    // Zeitstempel yyyy-MM-dd HH:mm:ss bei Erstellung/Änderung     -->     @version
    @Column(columnDefinition = "varchar(50) not null")
    private String modul;                                 // Modul = Package
    @Column(columnDefinition = "varchar(50) not null")
    private String program;                               // Program = Klasse
    @Column(columnDefinition = "varchar(50) not null")
    private String method;                                // eintragende Methode
    @Column(columnDefinition = "varchar(7) not null")
    private String groupID;                               // GruppenId des eintragenden Users
    @Column(columnDefinition = "varchar(50) not null")
    private String user;                                  // UserName des eintragenden Users
    @Column(columnDefinition = "char(1) not null")
    private char status;                                  // wird beim Erstellen auf A = Aktiv gesetzt und kann später durch div. Operationen verändert werden

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Parametrisierter Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiTableObject(String pModul, String pPraefix, int pId, String pProgram, String pMethod, String pGroupID, String pUser, String pBezeichnung, String pInfoText) {
        // Präfix setzen
        this.praefix = pPraefix;
        // ID erzeugen
        this.id = this.idIncrement(pId);
        // Variable mit eingegebenem KiParameter definieren
        this.bezeichnung = pBezeichnung;
        this.infoText = pInfoText;
        // Dokumentationsfelder setzen (aus mainFrame String[]args)
        this.dateOfCreation = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //this.timeStampChange = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        this.modul = pModul;
        this.program = pProgram;
        this.method = pMethod;
        this.groupID = pGroupID;
        this.user = pUser;
        // A = Aktiv
        this.status = 'A';
    }

    public KiTableObject() {
        //
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Eigenschaften abfragen oder manipulieren       -->     Zugriff von anderen Klassen aus
    // GETTER und SETTER werden auch für JPA gebraucht
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // inkrementierte ID des Vorobjekts formatieren ((Präfix Subklasse)00001 - (Präfix Subklasse)n) --> wird im Konstruktor aufgerufen
    public String idIncrement(int pId) {
        return this.praefix + new DecimalFormat("00000").format(pId);
    }
    // ****************************************************************************************************************************************************************************
    public String getId() {
        return this.id;
    }
    public String getPraefix() {
        return this.praefix;
    }
    public String getBezeichnung() {
        return this.bezeichnung;
    }
    public String getInfoText() {
        return this.infoText;
    }
    public String getDateOfCreation() {
        return this.dateOfCreation;
    }
    public Timestamp getTimeStampChange() {
        return this.timeStampChange;
    }
    public String getModul() {
        return modul;
    }
    public String getProgram() {
        return program;
    }
    public String getMethod() {
        return method;
    }
    public String getGroupID() {
        return this.groupID;
    }
    public String getUser() {
        return this.user;
    }
    public char getStatus() {
        return status;
    }
    // ****************************************************************************************************************************************************************************
    public void setId(String id) {
        this.id = id;
    }
    public void setPraefix(String praefix) {
        this.praefix = praefix;
    }
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    public void setInfoText(String pInfoText) {
        this.infoText = pInfoText;
    }
    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
    public void setTimeStampChange(Timestamp timeStampChange) {
        this.timeStampChange = timeStampChange;
    }
    public void setModul(String modul) {
        this.modul = modul;
    }
    public void setProgram(String program) {
        this.program = program;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setStatus(char status) {
        this.status = status;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // AUSGABE Konsole
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Einzeilige Ausgabe der Eigenschaften
    // Wird teilweise in den Subklassen überschrieben
    // Überschriebene Methode aus java.lang.Object
    @Override
    public String toString() {
        return new StringBuilder().append("KiTableObject{ ").append
                ("\nid='").append(this.id).append
                ("', \npraefix='").append(this.praefix).append
                ("', \nbezeichnung='").append(this.bezeichnung).append
                ("', \ninfoText='").append(this.infoText).append
                ("', \ndateOfCreation='").append(this.dateOfCreation).append
                ("', \ntimeStampChange='").append(this.timeStampChange).append
                ("', \nmodul='").append(this.modul).append
                ("', \nprogram='").append(this.program).append
                ("', \nmethod='").append(this.method).append
                ("', \ngroupID='").append(this.groupID).append
                ("', \nuser='").append(this.user).append
                ("', \nstatus='").append(this.status).append("'}").toString();
    }
}
