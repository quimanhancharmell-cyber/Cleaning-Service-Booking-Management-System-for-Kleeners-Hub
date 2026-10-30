package KleenersHub;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Shared logic for Client / Cleaner / Service screens: load table (No. always starts at 1),
 * View popup, Archive (instead of Delete) and the Archived list with Restore.
 */
public class EntityHelper {

    private final Component owner;
    private final JTable tbl;
    private final DefaultTableModel model;
    private final String singular, table, idCol;
    private final String[] cols, labels;
    private final int moneyIdx;
    private final List<Integer> ids = new ArrayList<>();

    public EntityHelper(Component owner, JTable tbl, String singular, String table, String idCol,
            String[] cols, String[] labels, int moneyIdx) {
        this.owner = owner;
        this.tbl = tbl;
        this.model = (DefaultTableModel) tbl.getModel();
        this.singular = singular;
        this.table = table;
        this.idCol = idCol;
        this.cols = cols;
        this.labels = labels;
        this.moneyIdx = moneyIdx;
    }

    private String display(int i, Object v) {
        if (v == null) {
            return "";
        }
        return i == moneyIdx ? UI.peso(v) : v.toString();
    }

    private String[] headers() {
        String[] h = new String[labels.length + 1];
        h[0] = "No.";
        System.arraycopy(labels, 0, h, 1, labels.length);
        return h;
    }

    private void fill(DefaultTableModel m, List<Integer> idList, int archived, String q) {
        try {
            StringBuilder sql = new StringBuilder("SELECT ").append(idCol);
            for (String c : cols) {
                sql.append(", ").append(c);
            }
            sql.append(" FROM ").append(table).append(" WHERE is_archived=?");
            List<Object> params = new ArrayList<>();
            params.add(archived);
            if (q != null && !q.isEmpty()) {
                sql.append(" AND (");
                for (int i = 0; i < cols.length; i++) {
                    sql.append(i > 0 ? " OR " : "").append(cols[i]).append(" LIKE ?");
                    params.add("%" + q + "%");
                }
                sql.append(")");
            }
            sql.append(" ORDER BY ").append(idCol);
            List<Object[]> rows = DB.query(sql.toString(), params.toArray());
            m.setRowCount(0);
            idList.clear();
            int n = 1; // numbering always starts again at 1
            for (Object[] r : rows) {
                idList.add(((Number) r[0]).intValue());
                Object[] row = new Object[cols.length + 1];
                row[0] = n++;
                for (int i = 0; i < cols.length; i++) {
                    row[i + 1] = display(i, r[i + 1]);
                }
                m.addRow(row);
            }
        } catch (SQLException ex) {
            UI.dbError(owner, ex);
        }
    }

    public void load(String search) {
        fill(model, ids, 0, search == null ? "" : search.trim());
    }

    /** Database id of the selected row, or -1 (and a message) if nothing is selected. */
    public int selectedId() {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            UI.info(owner, "Please select a " + singular.toLowerCase() + " from the table first.");
            return -1;
        }
        return ids.get(r);
    }

    public String selectedName() {
        int r = tbl.getSelectedRow();
        return r < 0 ? "" : String.valueOf(model.getValueAt(r, 1));
    }

    public Object[] fetch(int id) throws SQLException {
        List<Object[]> r = DB.query("SELECT " + String.join(", ", cols) + " FROM " + table + " WHERE " + idCol + "=?", id);
        return r.isEmpty() ? null : r.get(0);
    }

    public void view(Component parent, int id) {
        try {
            Object[] v = fetch(id);
            if (v == null) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cols.length; i++) {
                sb.append(String.format("%-18s: %s%n", labels[i], display(i, v[i])));
            }
            new DetailDialog(UI.owner(parent), singular + " Details", sb.toString()).setVisible(true);
        } catch (SQLException ex) {
            UI.dbError(parent, ex);
        }
    }

    public void view(int id) {
        view(owner, id);
    }

    public int insert(Object[] vals) throws SQLException {
        StringBuilder ph = new StringBuilder();
        for (int i = 0; i < cols.length; i++) {
            ph.append(i > 0 ? ",?" : "?");
        }
        return DB.insert("INSERT INTO " + table + " (" + String.join(", ", cols) + ") VALUES (" + ph + ")", vals);
    }

    public void update(int id, Object[] vals) throws SQLException {
        StringBuilder set = new StringBuilder();
        for (int i = 0; i < cols.length; i++) {
            set.append(i > 0 ? ", " : "").append(cols[i]).append("=?");
        }
        Object[] p = new Object[vals.length + 1];
        System.arraycopy(vals, 0, p, 0, vals.length);
        p[vals.length] = id;
        DB.update("UPDATE " + table + " SET " + set + " WHERE " + idCol + "=?", p);
    }

    /** Archive (not delete) the selected row. */
    public boolean archiveSelected() {
        int id = selectedId();
        if (id < 0) {
            return false;
        }
        if (!UI.confirm(owner, "Archive " + singular.toLowerCase() + " \"" + selectedName() + "\"?\n"
                + "You can restore it anytime from the Archived List.")) {
            return false;
        }
        try {
            DB.update("UPDATE " + table + " SET is_archived=1 WHERE " + idCol + "=?", id);
            return true;
        } catch (SQLException ex) {
            UI.dbError(owner, ex);
            return false;
        }
    }

    /** Archived pop-up with View and Restore. onChanged runs after a restore so the main table refreshes. */
    public void openArchive(Runnable onChanged) {
        final ArchiveDialog dlg = new ArchiveDialog(UI.owner(owner), "Archived " + singular + "s", headers());
        final List<Integer> aid = new ArrayList<>();
        fill(dlg.getModel(), aid, 1, "");
        dlg.onView = r -> view(dlg, aid.get(r));
        dlg.onRestore = r -> {
            if (!UI.confirm(dlg, "Restore this " + singular.toLowerCase() + "?")) {
                return;
            }
            try {
                DB.update("UPDATE " + table + " SET is_archived=0 WHERE " + idCol + "=?", aid.get(r));
                fill(dlg.getModel(), aid, 1, "");
                if (onChanged != null) {
                    onChanged.run();
                }
            } catch (SQLException ex) {
                UI.dbError(dlg, ex);
            }
        };
        dlg.setVisible(true);
    }
}
