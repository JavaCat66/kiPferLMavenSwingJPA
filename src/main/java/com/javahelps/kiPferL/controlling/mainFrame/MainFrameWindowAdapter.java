package com.javahelps.kiPferL.controlling.mainFrame;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameWindowAdapter extends WindowAdapter {

    private final static EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("JavaHelps");

    @Override
    public void windowClosing(WindowEvent e) {
        ENTITY_MANAGER_FACTORY.close();
        System.exit(0);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }
}
