package com.javahelps.kiPferL.jpa.kiParameter.lagerFaehigkeit;

import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.lagerFaehigkeit.LagerFaehigKeit;
import com.javahelps.kiPferL.model.kiTableObject.kiPerson.kiUser.KiUser;
import com.javahelps.kiPferL.model.kiTableObject.kiZutat.Zutat;
import com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils;

import javax.persistence.*;
import java.util.List;

public class LagerFaehigkeitJPA {

    public static LagerFaehigKeit findLagerFaehigkeit
            (EntityManagerFactory emf, String lagerDauer, String lagerTemperatur, String lagerBeleuchtung, String lagerLuftFeuchtigkeit) {

        Query query;
        List<LagerFaehigKeit> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("lagerFaehigkeit.findLagerFaehigkeit", LagerFaehigKeit.class);
            query = query.setParameter("lagerDauer", lagerDauer);
            query = query.setParameter("lagerTemperatur", lagerTemperatur);
            query = query.setParameter("lagerBeleuchtung", lagerBeleuchtung);
            query = query.setParameter("lagerLuftFeuchtigkeit", lagerLuftFeuchtigkeit);
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

    public static int findMaxIdLagerFaehigKeit(EntityManagerFactory emf) {

        TypedQuery<LagerFaehigKeit> query;
        List<LagerFaehigKeit> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("lagerFaehigkeit.findMAXidLagerFaehigkeit", LagerFaehigKeit.class);
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
            return Integer.parseInt(resultList.get(0).getId().substring(2));
        } else {
            return 0;
        }
    }

    public static String doInsertLagerFaehigkeit
            (EntityManagerFactory emf, String id, String infoText, String lagerDauer, String lagerTemperatur, String lagerBeleuchtung,
             String lagerLuftFeuchtigKeit, String dateOfCreation, String modul, String program, String method) {

        String temp;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            KiUser kiUser = KiSerializationUtils.doDeSerializationKiUser("kiSerializationFiles/kiUser/kiUser.ser");

            transaction = manager.getTransaction();
            transaction.begin();

            LagerFaehigKeit lagerFaehigKeit = new LagerFaehigKeit();
            lagerFaehigKeit.setId(id);
            lagerFaehigKeit.setPraefix("LF");
            lagerFaehigKeit.setBezeichnung("LagerFaehigKeit" + Integer.parseInt(id.substring(2)));
            lagerFaehigKeit.setInfoText(infoText);
            lagerFaehigKeit.setLagerDauer(lagerDauer);
            lagerFaehigKeit.setLagerTemperatur(lagerTemperatur);
            lagerFaehigKeit.setLagerBeleuchtung(lagerBeleuchtung);
            lagerFaehigKeit.setLagerLuftFeuchtigkeit(lagerLuftFeuchtigKeit);
            lagerFaehigKeit.setDateOfCreation(dateOfCreation);
            lagerFaehigKeit.setModul(modul);
            lagerFaehigKeit.setProgram(program);
            lagerFaehigKeit.setMethod(method);
            lagerFaehigKeit.setGroupID(kiUser.getGroupID());
            lagerFaehigKeit.setUser(kiUser.getUserName());
            lagerFaehigKeit.setStatus('A');

            manager.persist(lagerFaehigKeit);

            temp = "LOG com.javahelps.kiPferL.jpa.kiParameter.lagerFaehigkeit.LagerFaehigkeitJPA.doInsertLagerFaehigKeit: LagerFaehigKeit wurde erfolgreich eingetragen.";
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            temp = "FEHLER com.javahelps.kiPferL.jpa.zutat.ZutatJPA.doInsertLagerFaehigKeit: " + ex.toString();
        } finally {
            manager.close();
        }
        return temp;
    }

    public static LagerFaehigKeit findLagerFaehigkeitById(EntityManagerFactory emf, String id) {

        Query query;
        List<LagerFaehigKeit> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("lagerFaehigkeit.findLagerFaehigkeitById", LagerFaehigKeit.class);
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
}
