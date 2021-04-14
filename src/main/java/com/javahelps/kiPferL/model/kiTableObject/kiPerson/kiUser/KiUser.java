package com.javahelps.kiPferL.model.kiTableObject.kiPerson.kiUser;

import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiTableObject.kiPerson.KiPerson;
import com.javahelps.kiPferL.model.kiUtils.kiStringUtils.KiStringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bildet einen KiUser ab / SubKlasse von model.kiTableObject.kiPerson.KiPerson
 *      -->     geändert in version Alpha 2.0: SubObjekte SuperAdmin, Admin, ReadAndWriteUser und ReadOnlyUser werden durch int berechtigung abgebildet,
 *              Interface UserGroup wird nicht mehr benötigt.
 *      -->     wurde im Zuge der Version 2.8 gemeinsam mit einer neuen UnterPackage-Struktur hinzugefügt
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 2.0 2020.01.09      -->     SubObjekte SuperAdmin, Admin, ReadAndWriteUser und ReadOnlyUser werden durch int berechtigung abgebildet,
 *                                              Interface UserGroup wird nicht mehr benötigt.
 *                                    -->     Beim Login über die StartSeite des von viewing.mainFrame.MainFrame (Konstruktor) wird ein KiUserObject erstellt und serialisiert
 *                                              --> damit von überall auf die UserDaten zugegriffen werden kann
 * version Alpha 1.0 2020.12.26      -->     Collections, InnerAnonym, Lambda, MethodReference
 *                                   -->     beide TimeStamps (Creation und Change) werden nicht mehr übergeben, sondern im Konstruktor von KiTableObjekt befüllt
 *                                   -->     Verwendung von StringBuilder.append() statt String.concat(). Weil schneller.
 * version 3.0 2020.12.14      -->     Methode showBerechtigungen wird in den SubKlassen überschrieben, Interface KiUserGroup wird als InnerAnonymClass in SubKlassen implementiert
 * version 2.8 2020.12.08      -->     Neue SuperKlasse für UserObjekte = SoftwareBenutzer implementiert KiUserGroup mit der abstrakten Methode logIn()
 * @since 1.14
 */
@Entity
@Table(name = "KiUser")
@NamedQuery(
        name = "kiUser.findByUserNameAndPassword",
        query = "SELECT k FROM KiUser k WHERE k.userName = :userName AND k.passWort = :passWort"
)
public class KiUser extends KiPerson implements Serializable {
    // ********************************************************************************************************************************************************************************
    // KlassenVariable deklarieren
    // ********************************************************************************************************************************************************************************
    @Transient
    private static int userID = 0;                                          // static KlassenVariable zum Hochzählen der ID
    // ********************************************************************************************************************************************************************************
    // Objekt-Variable deklarieren
    // ********************************************************************************************************************************************************************************
    @Column(columnDefinition = "varchar(50) not null")
    private String userName;                                                // Konstruktor
    @Column(columnDefinition = "varchar(50) not null")
    private String passWort;                                                // Konstruktor
    @Column(columnDefinition = "varchar(100) not null")
    private String emailAdresse;                                            // Konstruktor
    @Column(columnDefinition = "varchar(50) not null")
    private String telNr;                                                   // Konstruktor
    @Column(columnDefinition = "int(2) unsigned not null")
    private int berechtigung;                                               // Konstruktor
    @Transient
    private List<KiParameter> berechtigungen;                               // werden in den Konstruktoren der SubKlassen automatisch vergeben, je nach UserTyp

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Default Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiUser() {
        super();
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Parametrisierter Konstruktor
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiUser(String pProgram, String pMethod, String pGroup, String pUser, String pBezeichnung, String pInfoText, String pUserName, String pPassWort, String pEmailAdresse,
                  String pTelNr, int pBerechtigung) {
        super("model.kiTableObject.kiPerson.kiUser", "US", ++userID, pProgram, pMethod, pGroup, pUser, pBezeichnung, pInfoText, "KiUser");
        this.userName = pUserName;
        this.passWort = pPassWort;
        if(KiStringUtils.verifyEmailAddress(pEmailAdresse)) {
            this.emailAdresse = pEmailAdresse;
        }
        this.telNr = pTelNr;
        this.berechtigung = pBerechtigung;
        // Berechtigungen aus berechtigungen.ser auslesen
        /*List<KiParameter> temp = KiSerializationUtils.doDeSerializationKiParameter("kiSerializationFiles/kiParameter/berechtigungen.ser");
        // ****************************************************************************************************************************************************************************
        // ALLE berechtigungen <= this.berechtigung werden übergeben
        // ****************************************************************************************************************************************************************************
        if (temp != null && temp.size() != 0) {
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // LambdaExpression zu ArrayList.stream().filter(Predicate<? super E> action).forEach(Consumer<? super E> action)
            // --> siehe model.kiTableObject.kiPerson.kiUser.kiUserGroup.ReadAndWriteUser
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            this.setBerechtigungen(temp.stream().filter(kiParameter -> Integer.parseInt(kiParameter.getInfoText()) <= this.berechtigung).collect(Collectors.toList()));
        } else {*/
            this.setBerechtigungen(new ArrayList<>());
        //}
        this.login();
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // GETTER UND SETTER
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public void setUserName(String pUsername) {
        this.userName = pUsername;
    }
    public String getUserName() {
        return userName;
    }

    public String getPassWort() {
        return passWort;
    }
    public void setPassWort(String passWort) {
        this.passWort = passWort;
    }

    public String getEmailAdresse() {
        return emailAdresse;
    }
    public void setEmailAdresse(String emailAdresse) {
        this.emailAdresse = emailAdresse;
    }

    public String getTelNr() {
        return telNr;
    }
    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    public int getBerechtigung() {
        return berechtigung;
    }
    public void setBerechtigung(int berechtigung) {
        this.berechtigung = berechtigung;
    }

    public void setBerechtigungen(List<KiParameter> pBerechtigungen) {
        this.berechtigungen = pBerechtigungen;
    }
    public List<KiParameter> getBerechtigungen() {
        return this.berechtigungen;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // AUSGABE
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Einzeilige Ausgabe der Eigenschaften
    @Override
    public String toString() {
        return new StringBuilder().append("KiUser{ ").append(super.toString()).append
                (", userName='").append(this.userName).append
                ("', infoText='").append(this.getInfoText()).append
                ("', passWort='").append(this.passWort).append
                ("', emailAdresse='").append(this.emailAdresse).append
                ("', telNr='").append(this.telNr).append
                ("', berechtigung='").append(this.berechtigung).append
                ("', BERECHTIGUNGEN='").append(this.berechtigungen).append("'}").toString();
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // AUSGABE DER BERECHTIGUNGEN, WIRD IN SUBOBJEKTEN ÜBERSCHRIEBEN
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public String login() {
        StringBuilder temp = new StringBuilder();

        temp.append("Mitglied ");
        temp.append(this.getBezeichnung().toUpperCase());
        temp.append(" MitgliedsName ");
        temp.append(this.userName.toUpperCase());
        temp.append(" PfadFinderInnenGruppe ");
        temp.append(this.getGroupID());
        temp.append(" hat sich eingeloggt. Er/Sie/Es darf folgende Bereiche betreten: \n");
        for (KiParameter a : this.berechtigungen) {
            temp.append(a.show());
        }
        temp.append("\n");

        return temp.toString();
    }
}
