import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class DataStorage {
    public static List<Workspace> loadWorkspaces() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT w FROM Workspace w", Workspace.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public static void saveWorkspace(Workspace workspace) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (workspace.getId() == 0) {
                em.persist(workspace);
            } else {
                em.merge(workspace);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new CustomException("Failed to save workspace", e);
        } finally {
            em.close();
        }
    }

    public static List<Reservation> getAllReservations() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Reservation r", Reservation.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public static void saveReservation(Reservation reservation) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(reservation);
            reservation.getWorkspace().setAvailable(false);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new CustomException("Failed to save reservation", e);
        } finally {
            em.close();
        }
    }

    public static void cancelReservation(int reservationId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Reservation reservation = em.find(Reservation.class, reservationId);
            if (reservation != null) {
                reservation.getWorkspace().setAvailable(true);
                em.remove(reservation);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new CustomException("Failed to cancel reservation", e);
        } finally {
            em.close();
        }
    }
}