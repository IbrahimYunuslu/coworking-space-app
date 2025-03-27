import javax.persistence.*;

public class JPAUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("coworking-space");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}