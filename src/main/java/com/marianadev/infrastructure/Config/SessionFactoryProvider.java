package com.marianadev.infrastructure.Config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {
     public static SessionFactory getSessionFactory() {
        Configuration config = new Configuration();
        config.configure(); // Reads hibernate.cfg.xml
        return config.buildSessionFactory();
    }


    public static void shutdown() {
        getSessionFactory().close();
    }
}
