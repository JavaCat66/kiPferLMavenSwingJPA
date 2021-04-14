package com.javahelps.kiPferL.jpa.kiParameter;

import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiTableObject.kiPerson.kiUser.KiUser;
import com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Stellt Methoden zur Abfrage der DB-Tabelle KiParameter zur Verfügung
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * @since 1.14
 */
public class KiParameterJPA {

    public static KiParameter findByIdAndParametergruppe(EntityManagerFactory emf, String parameterGruppe, String id) {
        TypedQuery<KiParameter> query;
        List<KiParameter> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("kiParameter.findByIdAndParametergruppe", KiParameter.class);
            query = query.setParameter("parameterGruppe", parameterGruppe);
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

    public static boolean findDoubleKiParameter(EntityManagerFactory emf, String parameterGruppe, String kuerzel, String bezeichnung) {
        TypedQuery<KiParameter> query;
        List<KiParameter> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("kiParameter.findDoubleKiParameter", KiParameter.class);
            query = query.setParameter("parameterGruppe", parameterGruppe);
            query = query.setParameter("kuerzel", kuerzel);
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

    public static String doInsertKiParameter
            (EntityManagerFactory emf, String id, String praefix, String bezeichnung, String parameterGruppe, String kuerzel, String infoText, String dateOfCreation, String modul,
             String program, String method) {

        String temp;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            KiUser kiUser = KiSerializationUtils.doDeSerializationKiUser("kiSerializationFiles/kiUser/kiUser.ser");

            transaction = manager.getTransaction();
            transaction.begin();

            KiParameter kiParameter = new KiParameter();
            kiParameter.setId(id);
            kiParameter.setPraefix(praefix);
            kiParameter.setBezeichnung(bezeichnung);
            kiParameter.setParameterGruppe(parameterGruppe);
            kiParameter.setKuerzel(kuerzel);
            kiParameter.setInfoText(infoText);
            kiParameter.setDateOfCreation(dateOfCreation);
            kiParameter.setModul(modul);
            kiParameter.setProgram(program);
            kiParameter.setMethod(method);
            kiParameter.setGroupID(kiUser.getGroupID());
            kiParameter.setUser(kiUser.getUserName());
            kiParameter.setStatus('A');

            manager.persist(kiParameter);

            temp = "LOG com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA.doInsertKiParameter: KiParameter wurde erfolgreich eingetragen.";
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            temp = "FEHLER com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA.doInsertKiParameter: " + ex.toString();
        } finally {
            manager.close();
        }
        return temp;
    }

    public static String doUpdateKiParameter
            (EntityManagerFactory emf, String id, String bezeichnung, String kuerzel, String infoText, String modul, String program, String method) {

        String temp;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            KiUser kiUser = KiSerializationUtils.doDeSerializationKiUser("kiSerializationFiles/kiUser/kiUser.ser");

            transaction = manager.getTransaction();
            transaction.begin();

            KiParameter kiParameter = manager.find(KiParameter.class, id);
            if(kiParameter != null) {
                kiParameter.setBezeichnung(bezeichnung);
                kiParameter.setKuerzel(kuerzel);
                kiParameter.setInfoText(infoText);
                kiParameter.setModul(modul);
                kiParameter.setProgram(program);
                kiParameter.setMethod(method);
                kiParameter.setGroupID(kiUser.getGroupID());
                kiParameter.setUser(kiUser.getUserName());
            }

            manager.persist(kiParameter);

            temp = "LOG com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA.doUpdateKiParameter: KiParameter wurde erfolgreich geändert.";
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            temp = "FEHLER com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA.doUpdateKiParameter: " + ex.toString();
        } finally {
            manager.close();
        }
        return temp;
    }

    public static int findMaxIdKiParameter(EntityManagerFactory emf, String praefix, String parameterGruppe) {
        TypedQuery<KiParameter> query;
        List<KiParameter> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("kiParameter.findAllKiParameterByPraefixAndParametergruppeOrderByIdDesc", KiParameter.class);
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

        if(resultList != null && resultList.size() > 0) {
            return Integer.parseInt(resultList.get(0).getId().substring(2));
        } else {
            return 0;
        }
    }

    public static List<KiParameter> findAllKiParameterByPraefixAndParametergruppe(EntityManagerFactory emf, String praefix, String parameterGruppe) {
        TypedQuery<KiParameter> query;
        List<KiParameter> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("kiParameter.findAllKiParameterByPraefixAndParametergruppe", KiParameter.class);
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

    public static KiParameter findKiParameterByPraefixParametergruppeAndBezeichnung(EntityManagerFactory emf, String praefix, String parameterGruppe, String bezeichnung) {
        TypedQuery<KiParameter> query;
        List<KiParameter> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("kiParameter.findKiParameterByPraefixParametergruppeAndBezeichnung", KiParameter.class);
            query = query.setParameter("praefix", praefix);
            query = query.setParameter("parameterGruppe", parameterGruppe);
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

        if(resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public static String deleteKiParameterByParametergruppeAndId(EntityManagerFactory emf, String parametergruppe, String id) {

        String temp = "";
        TypedQuery<KiParameter> query;
        List<KiParameter> resultList;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("kiParameter.findByIdAndParametergruppe", KiParameter.class);
            query = query.setParameter("parameterGruppe", parametergruppe);
            query = query.setParameter("id", id);
            resultList = query.getResultList();
            if(resultList != null && resultList.size() > 0) {
                manager.remove(resultList.get(0));
            }

            temp = "LOG com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA.deleteKiParameterByParametergruppeAndId: KiParameter wurde erfolgreich gelöscht.";
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                temp = "LOG com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA.deleteKiParameterByParametergruppeAndId: " + ex.toString();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }
        return temp;
    }
}
