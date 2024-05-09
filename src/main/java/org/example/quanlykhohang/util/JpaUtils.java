package org.example.quanlykhohang.util;

import jakarta.persistence.*;

public class JpaUtils {
    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager() {
        if (factory == null || !factory.isOpen()) {
            factory = Persistence.createEntityManagerFactory("quanlykhohang2");
        }
        return factory.createEntityManager();
    }
}
