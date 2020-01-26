package com;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class SessionManager {
    private volatile static Session session;

    public static Session getSession() {
        if (session == null) {
            synchronized (SessionManager.class) {
                if (session == null) {
                    session = new Configuration()
                            .configure()
                            .buildSessionFactory()
                            .openSession();
                    session.setDefaultReadOnly(true);
                }
            }
        }

        return session;
    }
}
