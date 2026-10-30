package KleenersHub;

/** Nandito kung sino ang naka-login at ano ang role (Admin o Staff). */
public class Session {

    public static int userId;
    public static String username = "";
    public static String fullName = "";
    public static String role = "";

    public static void set(int id, String user, String name, String r) {
        userId = id;
        username = user;
        fullName = name;
        role = r;
    }

    public static void clear() {
        set(0, "", "", "");
    }

    public static boolean isAdmin() {
        return "Admin".equalsIgnoreCase(role);
    }
}
