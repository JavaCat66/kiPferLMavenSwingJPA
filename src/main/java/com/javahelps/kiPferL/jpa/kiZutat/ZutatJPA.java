package com.javahelps.kiPferL.jpa.kiZutat;

import com.javahelps.kiPferL.jpa.kiParameter.lagerFaehigkeit.LagerFaehigkeitJPA;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.lagerFaehigkeit.LagerFaehigKeit;
import com.javahelps.kiPferL.model.kiTableObject.kiPerson.kiUser.KiUser;
import com.javahelps.kiPferL.model.kiTableObject.kiZutat.Zutat;
import com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils;
import com.javahelps.kiPferL.model.kiUtils.kiStringUtils.KiStringUtils;

import javax.persistence.*;
import java.util.List;

public class ZutatJPA {

    public static int findMaxIdZutat(EntityManagerFactory emf, String id) {
        Query query;
        List<Zutat> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("zutat.findAllZutatByEinkaufsKategorieOrderByIdDesc", Zutat.class);
            query = query.setParameter("id", id);
            resultList = (List<Zutat>) query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }

        if(resultList != null && resultList.size() > 0) {
            return Integer.parseInt(resultList.get(0).getId().substring(2));
        } else {
            return 0;
        }
    }

    public static boolean findDoubleZutat(EntityManagerFactory emf, String bezeichnung) {
        TypedQuery<Zutat> query;
        List<Zutat> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("zutat.findDoubleZutat", Zutat.class);
            query = query.setParameter("bezeichnung", bezeichnung);
            resultList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }

        return resultList != null && resultList.size() > 0;
    }

    public static List<KiParameter> findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(EntityManagerFactory emf, String praefix, String parameterGruppe) {
        TypedQuery<KiParameter> query;
        List<KiParameter> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("kiParameter.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung", KiParameter.class);
            query = query.setParameter("praefix", praefix);
            query = query.setParameter("parameterGruppe", parameterGruppe);
            resultList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }

        return resultList;
    }

    public static List<Zutat> findAllZutatByEinkaufsKategorieOrderById(EntityManagerFactory emf, String id) {
        TypedQuery<Zutat> query;
        List<Zutat> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("zutat.findAllZutatByEinkaufsKategorieOrderById", Zutat.class);
            query = query.setParameter("id", id);
            resultList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }

        return resultList;
    }

    public static List<Zutat> findAllZutatByEinkaufsKategorieIdOrderByBezeichnung(EntityManagerFactory emf, String id) {
        TypedQuery<Zutat> query;
        List<Zutat> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("zutat.findAllZutatByEinkaufsKategorieIdOrderByBezeichnung", Zutat.class);
            query = query.setParameter("id", id);
            resultList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }

        return resultList;
    }

    public static String doInsertZutat
            (EntityManagerFactory emf, String id, String praefix, String bezeichnung, String infoText, String einkaufsKategorie, String massEinheitRezept,
             String massEinheitHandel, double kleinsteVerkaufsEinheit, double kommissionierungsEinheit, double kiloGrammAequivalent, int naehrWertProHundertGramm,
             double brotEinheitenProHundertGramm, String lagerDauer, String lagerTemperatur, String lagerBeleuchtung, String lagerLuftFeuchtigkeit,
             String kiParameterAL, String kiParameterDT, String dateOfCreation, String modul, String program, String method) {

        StringBuilder temp = new StringBuilder();
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            LagerFaehigKeit lagerFaehigKeit = LagerFaehigkeitJPA.findLagerFaehigkeit(emf, lagerDauer, lagerTemperatur, lagerBeleuchtung, lagerLuftFeuchtigkeit);
            String lagerFaehigKeitID;
            int i;
            if(lagerFaehigKeit != null) {
                lagerFaehigKeitID = lagerFaehigKeit.getId();
            } else {
                i = LagerFaehigkeitJPA.findMaxIdLagerFaehigKeit(emf);
                lagerFaehigKeitID = KiStringUtils.idIncrement("LF", ++i);
                temp.append(LagerFaehigkeitJPA.doInsertLagerFaehigkeit
                        (emf, lagerFaehigKeitID, "", lagerDauer, lagerTemperatur, lagerBeleuchtung, lagerLuftFeuchtigkeit, dateOfCreation, modul, program, method)).append
                        ("\n");
            }

            KiUser kiUser = KiSerializationUtils.doDeSerializationKiUser("kiSerializationFiles/kiUser/kiUser.ser");

            transaction = manager.getTransaction();
            transaction.begin();

            Zutat zutat = new Zutat();
            zutat.setId(id);
            zutat.setPraefix(praefix);
            zutat.setBezeichnung(bezeichnung);
            zutat.setInfoText(infoText);
            zutat.setEinkaufsKategorie(einkaufsKategorie);
            zutat.setMasseinheitRezept(massEinheitRezept);
            zutat.setMasseinheitHandel(massEinheitHandel);
            zutat.setKleinsteVerkaufsEinheit(kleinsteVerkaufsEinheit);
            zutat.setKommissionierungsEinheit(kommissionierungsEinheit);
            zutat.setKiloGrammAequivalent(kiloGrammAequivalent);
            zutat.setNaehrWertProHundertGramm(naehrWertProHundertGramm);
            zutat.setBrotEinheitenProHundertGramm(brotEinheitenProHundertGramm);
            zutat.setLagerFaehigKeit(lagerFaehigKeitID);
            zutat.setKiParameterAL(kiParameterAL);
            zutat.setKiParameterDT(kiParameterDT);
            zutat.setDateOfCreation(dateOfCreation);
            zutat.setModul(modul);
            zutat.setProgram(program);
            zutat.setMethod(method);
            zutat.setGroupID(kiUser.getGroupID());
            zutat.setUser(kiUser.getUserName());
            zutat.setStatus('A');

            manager.persist(zutat);

            temp.append("LOG com.javahelps.kiPferL.jpa.zutat.ZutatJPA.doInsertZutat: Zutat wurde erfolgreich eingetragen.");
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            temp.append("FEHLER com.javahelps.kiPferL.jpa.zutat.ZutatJPA.doInsertZutat: ").append(ex.toString());
        } finally {
            manager.close();
        }
        return temp.toString();
    }

    public static String doUpdateZutat
            (EntityManagerFactory emf, String id, String bezeichnung, String infoText, String einkaufsKategorie, String massEinheitRezept,
             String massEinheitHandel, double kleinsteVerkaufsEinheit, double kommissionierungsEinheit, double kiloGrammAequivalent, int naehrWertProHundertGramm,
             double brotEinheitenProHundertGramm, String lagerDauer, String lagerTemperatur, String lagerBeleuchtung, String lagerLuftFeuchtigkeit,
             String kiParameterAL, String kiParameterDT, String dateOfCreation, String modul, String program, String method) {

        StringBuilder temp = new StringBuilder();
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            LagerFaehigKeit lagerFaehigKeit = LagerFaehigkeitJPA.findLagerFaehigkeit(emf, lagerDauer, lagerTemperatur, lagerBeleuchtung, lagerLuftFeuchtigkeit);
            String lagerFaehigKeitID;
            int i;
            if(lagerFaehigKeit != null) {
                lagerFaehigKeitID = lagerFaehigKeit.getId();
            } else {
                i = LagerFaehigkeitJPA.findMaxIdLagerFaehigKeit(emf);
                lagerFaehigKeitID = KiStringUtils.idIncrement("LF", ++i);
                temp.append(LagerFaehigkeitJPA.doInsertLagerFaehigkeit
                        (emf, lagerFaehigKeitID, "", lagerDauer, lagerTemperatur, lagerBeleuchtung, lagerLuftFeuchtigkeit, dateOfCreation, modul, program, method)).append
                        ("\n");
            }

            KiUser kiUser = KiSerializationUtils.doDeSerializationKiUser("kiSerializationFiles/kiUser/kiUser.ser");

            transaction = manager.getTransaction();
            transaction.begin();

            Zutat zutat = manager.find(Zutat.class, id);
            if(zutat != null) {
                zutat.setBezeichnung(bezeichnung);
                zutat.setInfoText(infoText);
                zutat.setEinkaufsKategorie(einkaufsKategorie);
                zutat.setMasseinheitRezept(massEinheitRezept);
                zutat.setMasseinheitHandel(massEinheitHandel);
                zutat.setKleinsteVerkaufsEinheit(kleinsteVerkaufsEinheit);
                zutat.setKommissionierungsEinheit(kommissionierungsEinheit);
                zutat.setKiloGrammAequivalent(kiloGrammAequivalent);
                zutat.setNaehrWertProHundertGramm(naehrWertProHundertGramm);
                zutat.setBrotEinheitenProHundertGramm(brotEinheitenProHundertGramm);
                zutat.setLagerFaehigKeit(lagerFaehigKeitID);
                zutat.setKiParameterAL(kiParameterAL);
                zutat.setKiParameterDT(kiParameterDT);
                zutat.setModul(modul);
                zutat.setProgram(program);
                zutat.setMethod(method);
                zutat.setGroupID(kiUser.getGroupID());
                zutat.setUser(kiUser.getUserName());
            }

            manager.persist(zutat);

            temp.append("LOG com.javahelps.kiPferL.jpa.zutat.ZutatJPA.doUpdateZutat: Zutat wurde erfolgreich geändert.");
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            temp.append("FEHLER com.javahelps.kiPferL.jpa.zutat.ZutatJPA.doUpdateZutat: ").append(ex.toString());
        } finally {
            manager.close();
        }
        return temp.toString();
    }

    public static Zutat findByIdAndEinkaufsKategorieId(EntityManagerFactory emf, String einkaufsKategorieId, String id) {
        TypedQuery<Zutat> query;
        List<Zutat> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("zutat.findByIdAndEinkaufsKategorieId", Zutat.class);
            query = query.setParameter("einkaufsKategorieId", einkaufsKategorieId);
            query = query.setParameter("id", id);
            resultList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }

        if(resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public static String deleteZutatByEinkaufsKategorieIdAndId(EntityManagerFactory emf, String einkaufsKategorieId, String id) {

        String temp = "";
        TypedQuery<Zutat> query;
        List<Zutat> resultList;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("zutat.findByIdAndEinkaufsKategorieId", Zutat.class);
            query = query.setParameter("einkaufsKategorieId", einkaufsKategorieId);
            query = query.setParameter("id", id);
            resultList = query.getResultList();
            if(resultList != null && resultList.size() > 0) {
                manager.remove(resultList.get(0));
            }

            temp = "LOG com.javahelps.kiPferL.jpa.zutat.ZutatJPA.deleteZutatByEinkaufsKategorieIdAndId: Zutat wurde erfolgreich gelöscht.";
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                temp = "LOG com.javahelps.kiPferL.jpa.kiParameter.deleteZutatByEinkaufsKategorieIdAndId: " + ex.toString();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }
        return temp;
    }
}
