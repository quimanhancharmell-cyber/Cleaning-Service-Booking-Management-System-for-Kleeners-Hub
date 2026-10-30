package KleenersHub;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AttributeSet;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/** Mga kulay, font at maliliit na helper para pare-pareho ang itsura ng buong system. */
public class UI {

    public static final Color PRIMARY = new Color(0x1F7A8C);
    public static final Color PRIMARY_DARK = new Color(0x16505C);
    public static final Color BG = new Color(0xF3F6F8);
    public static final Color DANGER = new Color(0xC0392B);
    public static final Color WARN = new Color(0xE67E22);
    public static final Color OK = new Color(0x2E8B57);
    public static final Color GRAY = new Color(0x6C7A83);
    public static final Color TEXT = new Color(0x2B3A42);
    public static final Color LINE = new Color(0xD5DDE2);

    public static final Font BASE = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font TITLE = new Font("Segoe UI", Font.BOLD, 26);

    public static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
    public static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    // ---------- components ----------
    public static JButton button(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(BOLD);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(8, 16, 8, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(BASE);
        l.setForeground(TEXT);
        return l;
    }

    public static JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setFont(TITLE);
        l.setForeground(PRIMARY_DARK);
        return l;
    }

    public static JTextField field(int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(BASE);
        f.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(0xB8C4CA)), new EmptyBorder(5, 8, 5, 8)));
        return f;
    }

    public static JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(new LineBorder(LINE), new EmptyBorder(14, 16, 14, 16)));
        return p;
    }

    // ---------- helpers for the NetBeans-designed forms ----------
    /** White card look: thin outline + the padding that was set in the Design view. */
    public static void styleCard(JPanel p) {
        p.setBorder(BorderFactory.createCompoundBorder(new LineBorder(LINE), p.getBorder()));
    }

    /** Card with a coloured strip on the left (dashboard cards). */
    public static void accent(JPanel p, Color c) {
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(new LineBorder(LINE), new javax.swing.border.MatteBorder(0, 6, 0, 0, c)), p.getBorder()));
    }

    /** Hand cursor on every button inside a container. */
    public static void handCursor(java.awt.Container c) {
        for (Component ch : c.getComponents()) {
            if (ch instanceof JButton) {
                ch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            if (ch instanceof java.awt.Container) {
                handCursor((java.awt.Container) ch);
            }
        }
    }

    /** Light outline for text fields so they match the theme. */
    public static void styleFields(JTextField... fs) {
        for (JTextField f : fs) {
            f.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(0xB8C4CA)), new EmptyBorder(5, 8, 5, 8)));
        }
    }

    // ---------- tables ----------
    public static DefaultTableModel model(String... cols) {
        return new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
    }

    public static void styleTable(JTable t) {
        t.setRowHeight(30);
        t.setFont(BASE);
        t.setForeground(TEXT);
        t.setSelectionBackground(new Color(0xCDE8EE));
        t.setSelectionForeground(TEXT);
        t.setGridColor(new Color(0xE3E8EB));
        t.setShowVerticalLines(false);
        t.setFillsViewportHeight(true);
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader h = t.getTableHeader();
        h.setReorderingAllowed(false);
        h.setBackground(PRIMARY);
        h.setForeground(Color.WHITE);
        h.setFont(BOLD);
        h.setPreferredSize(new Dimension(100, 34));
        if (t.getColumnCount() > 0 && "No.".equals(t.getColumnName(0))) {
            t.getColumnModel().getColumn(0).setPreferredWidth(45);
            t.getColumnModel().getColumn(0).setMaxWidth(60);
        }
    }

    // ---------- input rules ----------
    private static void restrict(final JTextField f, final String regex) {
        ((AbstractDocument) f.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int off, String s, AttributeSet a) throws BadLocationException {
                replace(fb, off, 0, s, a);
            }

            @Override
            public void replace(FilterBypass fb, int off, int len, String text, AttributeSet a) throws BadLocationException {
                if (text == null) {
                    text = "";
                }
                Document d = fb.getDocument();
                String cur = d.getText(0, d.getLength());
                String next = cur.substring(0, off) + text + cur.substring(off + len);
                if (!next.matches(regex)) {
                    flashError(f);
                    return;
                }
                super.replace(fb, off, len, text, a);
            }
        });
    }

    /** Numbers lang at hanggang 11 digits lang. Pag lumagpas, hindi papasok at mag-aalarm. */
    public static void phoneOnly(JTextField f) {
        restrict(f, "\\d{0,11}");
        f.setToolTipText("11 digits only");
    }

    public static void decimalOnly(JTextField f) {
        restrict(f, "\\d{0,8}(\\.\\d{0,2})?");
    }

    public static void flashError(final JComponent c) {
        Toolkit.getDefaultToolkit().beep();
        if (c.getClientProperty("origBorder") == null) {
            c.putClientProperty("origBorder", c.getBorder());
        }
        c.setBorder(new LineBorder(DANGER, 2));
        Timer t = new Timer(700, e -> c.setBorder((Border) c.getClientProperty("origBorder")));
        t.setRepeats(false);
        t.start();
    }

    // ---------- dialogs ----------
    public static void error(Component p, String m) {
        JOptionPane.showMessageDialog(p, m, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void info(Component p, String m) {
        JOptionPane.showMessageDialog(p, m, "Kleeners Hub", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(Component p, String m) {
        return JOptionPane.showConfirmDialog(p, m, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static void dbError(Component p, SQLException ex) {
        error(p, "Database error:\n" + ex.getMessage());
    }

    public static Window owner(Component c) {
        return c instanceof Window ? (Window) c : javax.swing.SwingUtilities.getWindowAncestor(c);
    }

    // ---------- formatting ----------
    public static String peso(Object o) {
        if (o == null) {
            return "PHP 0.00";
        }
        BigDecimal b = o instanceof BigDecimal ? (BigDecimal) o : new BigDecimal(o.toString());
        return String.format(Locale.ENGLISH, "PHP %,.2f", b);
    }

    public static String timeStr(Object o) {
        if (o instanceof java.sql.Time) {
            return ((java.sql.Time) o).toLocalTime().format(TIME_FMT);
        }
        return o == null ? "" : o.toString();
    }

    public static String dateStr(Object o) {
        if (o instanceof java.sql.Date) {
            return ((java.sql.Date) o).toLocalDate().format(DATE_FMT);
        }
        return o == null ? "" : o.toString();
    }

    public static String str(Object o) {
        return o == null ? "" : o.toString();
    }
}
