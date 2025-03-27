import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DataStorage {
    public static List<Workspace> loadWorkspaces() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Workspace> query = em.createQuery("SELECT w FROM Workspace w", Workspace.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static void saveWorkspace(Workspace workspace) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (workspace.getId() == 0) {
                em.persist(workspace);
            } else {
                em.merge(workspace);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static List<Reservation> getAllReservations() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> query = em.createQuery("SELECT r FROM Reservation r", Reservation.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static void saveReservation(Reservation reservation) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reservation);
            reservation.getWorkspace().setAvailable(false);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void cancelReservation(int reservationId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Reservation reservation = em.find(Reservation.class, reservationId);
            if (reservation != null) {
                reservation.getWorkspace().setAvailable(true);
                em.remove(reservation);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}