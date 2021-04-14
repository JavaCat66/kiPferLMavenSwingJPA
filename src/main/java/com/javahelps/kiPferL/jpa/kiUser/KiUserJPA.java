package com.javahelps.kiPferL.jpa.kiUser;

import com.javahelps.kiPferL.model.kiTableObject.kiPerson.kiUser.KiUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Stellt Methoden zur Abfrage der DB-Tabelle KiUser zur Verfügung
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * @since 1.14
 */
public class KiUserJPA {

    public static KiUser findByUserNameAndPassword(EntityManagerFactory emf, String username, String passwort) {
        TypedQuery<KiUser> query;
        List<KiUser> resultList = null;
        EntityManager manager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            query = manager.createNamedQuery("kiUser.findByUserNameAndPassword", KiUser.class);
            query = query.setParameter("userName", username);
            query = query.setParameter("passWort", passwort);
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

        if(resultList != null) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
}
