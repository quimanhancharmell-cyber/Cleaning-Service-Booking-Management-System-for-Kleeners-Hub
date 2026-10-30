package KleenersHub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/** Calendar na date picker. Ang mga araw na lumipas na ay naka-disable, ngayon at future lang ang pwede. */
public class DatePicker extends JPanel {

    private LocalDate date = LocalDate.now();
    private final JTextField txt = UI.field(10);
    private final List<Runnable> listeners = new ArrayList<>();

    public DatePicker() {
        super(new BorderLayout(6, 0));
        setOpaque(false);
        txt.setEditable(false);
        txt.setBackground(Color.WHITE);
        txt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JButton b = UI.button("Calendar", UI.PRIMARY);
        add(txt, BorderLayout.CENTER);
        add(b, BorderLayout.EAST);
        b.addActionListener(e -> openCalendar());
        txt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openCalendar();
            }
        });
        updateText();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate d) {
        date = d;
        updateText();
    }

    public void addChangeListener(Runnable r) {
        listeners.add(r);
    }

    private void updateText() {
        txt.setText(date.format(UI.DATE_FMT));
    }

    private void openCalendar() {
        final LocalDate today = LocalDate.now();
        final JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), "Select Date", Dialog.ModalityType.APPLICATION_MODAL);
        final YearMonth[] shown = {YearMonth.from(date.isBefore(today) ? today : date)};

        final JLabel month = new JLabel("", SwingConstants.CENTER);
        month.setFont(UI.BOLD);
        final JButton prev = new JButton("<");
        final JButton next = new JButton(">");
        JPanel head = new JPanel(new BorderLayout());
        head.add(prev, BorderLayout.WEST);
        head.add(month, BorderLayout.CENTER);
        head.add(next, BorderLayout.EAST);

        final JPanel grid = new JPanel(new GridLayout(7, 7, 3, 3));
        final Runnable[] render = new Runnable[1];
        render[0] = () -> {
            grid.removeAll();
            month.setText(shown[0].getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + shown[0].getYear());
            for (String d : new String[]{"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"}) {
                JLabel l = new JLabel(d, SwingConstants.CENTER);
                l.setFont(UI.BOLD);
                grid.add(l);
            }
            int offset = shown[0].atDay(1).getDayOfWeek().getValue() % 7; // Sunday = 0
            int cells = 0;
            for (int i = 0; i < offset; i++) {
                grid.add(new JLabel(""));
                cells++;
            }
            for (int day = 1; day <= shown[0].lengthOfMonth(); day++) {
                final LocalDate d = shown[0].atDay(day);
                JButton b = new JButton(String.valueOf(day));
                b.setMargin(new Insets(6, 6, 6, 6));
                b.setFont(UI.BASE);
                b.setEnabled(!d.isBefore(today));
                if (d.equals(date)) {
                    b.setBackground(UI.PRIMARY);
                    b.setForeground(Color.WHITE);
                } else if (d.equals(today)) {
                    b.setBackground(new Color(0xCDE8EE));
                }
                b.addActionListener(e -> {
                    date = d;
                    updateText();
                    dlg.dispose();
                    for (Runnable r : listeners) {
                        r.run();
                    }
                });
                grid.add(b);
                cells++;
            }
            while (cells < 42) {
                grid.add(new JLabel(""));
                cells++;
            }
            prev.setEnabled(shown[0].isAfter(YearMonth.from(today)));
            grid.revalidate();
            grid.repaint();
        };
        prev.addActionListener(e -> {
            shown[0] = shown[0].minusMonths(1);
            render[0].run();
        });
        next.addActionListener(e -> {
            shown[0] = shown[0].plusMonths(1);
            render[0].run();
        });
        render[0].run();

        JPanel root = new JPanel(new BorderLayout(0, 8));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.add(head, BorderLayout.NORTH);
        root.add(grid, BorderLayout.CENTER);
        JLabel hint = new JLabel("Past dates are disabled", SwingConstants.CENTER);
        hint.setForeground(UI.GRAY);
        root.add(hint, BorderLayout.SOUTH);
        dlg.setContentPane(root);
        dlg.setSize(380, 360);
        dlg.setResizable(false);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }
}
