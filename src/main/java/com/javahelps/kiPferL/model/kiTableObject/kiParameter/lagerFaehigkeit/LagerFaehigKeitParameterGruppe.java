package com.javahelps.kiPferL.model.kiTableObject.kiParameter.lagerFaehigkeit;

/**
 * Enumeration, wird von der Klasse package model.kiTableObject.kiParameter.lagerFaehigKeit.LagerFaehigKeit verwendet
 * LagerFaehigKeit besteht aus 4 einzelnen KiParametern, die fix vordefiniert sind
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 1.0 2020.12.26      -->     Enum erstellt
 * @since 1.14
 */
public enum LagerFaehigKeitParameterGruppe {

    LAGERDAUER("LD", "LagerDauer"),
    LAGERTEMPERATUR("LT", "LagerTemperatur"),
    LAGERBELEUCHTUNG("LB", "LagerBeleuchtung"),
    LAGERLUFTFEUCHTIGKEIT("LL", "LagerLuftFeuchtigKeit");

    private final String kiParameterPraefix, kiParameterGruppe;

    LagerFaehigKeitParameterGruppe(String pKiParameterPraefix, String pKiParameterGruppe) {
        this.kiParameterPraefix = pKiParameterPraefix;
        this.kiParameterGruppe = pKiParameterGruppe;
    }

    public String getKiParameterPraefix() {
        return this.kiParameterPraefix;
    }
    public String getKiParameterGruppe() {
        return this.kiParameterGruppe;
    }
}
