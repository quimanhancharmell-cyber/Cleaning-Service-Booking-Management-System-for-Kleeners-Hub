package KleenersHub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Lahat ng koneksyon sa MySQL ay dito dumadaan. Palitan lang ang USER/PASS kung iba ang sa inyo. */
public class DB {

    public static final String URL = "jdbc:mysql://localhost:3306/kleeners_hub_db?useSSL=false&allowPublicKeyRetrieval=true&tinyInt1isBit=false";
    public static final String USER = "root";
    public static final String PASS = "";

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private static void bind(PreparedStatement ps, Object[] p) throws SQLException {
        for (int i = 0; i < p.length; i++) {
            ps.setObject(i + 1, p[i]);
        }
    }

    public static List<Object[]> query(String sql, Object... p) throws SQLException {
        try (Connection c = get(); PreparedStatement ps = c.prepareStatement(sql)) {
            bind(ps, p);
            try (ResultSet rs = ps.executeQuery()) {
                int n = rs.getMetaData().getColumnCount();
                List<Object[]> out = new ArrayList<>();
                while (rs.next()) {
                    Object[] r = new Object[n];
                    for (int i = 0; i < n; i++) {
                        r[i] = rs.getObject(i + 1);
                    }
                    out.add(r);
                }
                return out;
            }
        }
    }

    public static int update(String sql, Object... p) throws SQLException {
        try (Connection c = get(); PreparedStatement ps = c.prepareStatement(sql)) {
            bind(ps, p);
            return ps.executeUpdate();
        }
    }

    /** INSERT at ibabalik ang bagong ID. */
    public static int insert(String sql, Object... p) throws SQLException {
        try (Connection c = get(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(ps, p);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    public static Object scalar(String sql, Object... p) throws SQLException {
        List<Object[]> r = query(sql, p);
        return r.isEmpty() ? null : r.get(0)[0];
    }

    public static long count(String sql, Object... p) throws SQLException {
        Object o = scalar(sql, p);
        return o == null ? 0 : ((Number) o).longValue();
    }
}
