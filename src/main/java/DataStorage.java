import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {

    private static String WORKSPACE_FILE = "data/workspaces.dat";
    private static String STATE_FILE = "data/state.dat";

    public static void setWorkspaceFileForTesting(String path) {
        WORKSPACE_FILE = path;
    }

    public static void setStateFileForTesting(String path) {
        STATE_FILE = path;
    }

    public static void saveWorkspaces(List<Workspace> workspaces) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(WORKSPACE_FILE))) {
            oos.writeObject(workspaces);
        } catch (Exception e) {
            System.out.println("Error saving workspaces: " + e.getMessage());
        }
    }

    public static List<Workspace> loadWorkspaces() {
        List<Workspace> workspaces = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(WORKSPACE_FILE))) {
            workspaces = (List<Workspace>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing workspace file found. Starting with an empty list.");
        } catch (Exception e) {
            System.out.println("Error loading workspaces: " + e.getMessage());
        }
        return workspaces;
    }

    public static void saveState(List<Reservation> reservations, int reservationCounter) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STATE_FILE))) {
            oos.writeObject(reservations);
            oos.writeObject(Integer.valueOf(reservationCounter));
        } catch (Exception e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public static State loadState() {
        List<Reservation> reservations = new ArrayList<>();
        int reservationCounter = 1;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STATE_FILE))) {
            reservations = (List<Reservation>) ois.readObject();
            reservationCounter = ((Integer) ois.readObject()).intValue();
        } catch (FileNotFoundException e) {
            System.out.println("No existing state file found. Starting with an empty list.");
        } catch (Exception e) {
            System.out.println("Error loading state: " + e.getMessage());
        }
        return new State(reservations, reservationCounter);
    }
}