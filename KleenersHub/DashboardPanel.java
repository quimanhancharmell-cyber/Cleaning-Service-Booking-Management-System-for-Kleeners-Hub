package KleenersHub;

/**
 * DashboardPanel - GUI is editable in the NetBeans Design view (see DashboardPanel.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DashboardPanel extends javax.swing.JPanel implements Refreshable {

    private final javax.swing.table.DefaultTableModel scheduleModel;
    private final javax.swing.table.DefaultTableModel cleanerModel;

    public DashboardPanel() {
        initComponents();
        scheduleModel = (javax.swing.table.DefaultTableModel) tblSchedule.getModel();
        cleanerModel = (javax.swing.table.DefaultTableModel) tblCleaners.getModel();
        UI.accent(pnlCard1, UI.PRIMARY);
        UI.accent(pnlCard2, UI.OK);
        UI.accent(pnlCard3, UI.WARN);
        UI.accent(pnlCard4, new java.awt.Color(0x8E44AD));
        UI.styleCard(pnlSchedule);
        UI.styleCard(pnlCleaners);
        UI.styleTable(tblSchedule);
        UI.styleTable(tblCleaners);
        pnlCleaners.setVisible(Session.isAdmin()); // only Admin sees the cleaners list
    }

    /** "Today's Bookings" counts only bookings dated TODAY, so it becomes 0 again on a new day. */
    @Override
    public void refresh() {
        java.time.LocalDate today = java.time.LocalDate.now();
        lblWelcome.setText("Welcome, " + Session.fullName + "  |  "
                + today.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", java.util.Locale.ENGLISH)));
        java.sql.Date d = java.sql.Date.valueOf(today);
        try {
            java.util.List<Object[]> rows = DB.query("SELECT b.booking_time, c.client_name, s.service_name, COALESCE(cl.cleaner_name,'-'), b.status, b.total_amount "
                    + "FROM bookings b JOIN clients c ON c.client_id=b.client_id JOIN services s ON s.service_id=b.service_id "
                    + "LEFT JOIN cleaners cl ON cl.cleaner_id=b.cleaner_id "
                    + "WHERE b.booking_date=? AND b.is_archived=0 AND b.status<>'Cancelled' ORDER BY b.booking_time", d);
            scheduleModel.setRowCount(0);
            java.math.BigDecimal sum = java.math.BigDecimal.ZERO;
            int n = 1;
            for (Object[] r : rows) {
                java.math.BigDecimal amt = new java.math.BigDecimal(r[5].toString());
                sum = sum.add(amt);
                scheduleModel.addRow(new Object[]{n++, UI.timeStr(r[0]), r[1], r[2], r[3], r[4], UI.peso(amt)});
            }
            lblToday.setText(String.valueOf(rows.size()));
            lblPay.setText(UI.peso(sum));
            lblClients.setText(String.valueOf(DB.count("SELECT COUNT(*) FROM clients WHERE is_archived=0")));
            lblServices.setText(String.valueOf(DB.count("SELECT COUNT(*) FROM services WHERE is_archived=0 AND availability='Available'")));

            if (Session.isAdmin()) {
                java.util.List<Object[]> cl = DB.query("SELECT cl.cleaner_name, COUNT(b.booking_id) FROM cleaners cl "
                        + "LEFT JOIN bookings b ON b.cleaner_id=cl.cleaner_id AND b.booking_date=? AND b.is_archived=0 AND b.status<>'Cancelled' "
                        + "WHERE cl.is_archived=0 GROUP BY cl.cleaner_id, cl.cleaner_name ORDER BY cl.cleaner_name", d);
                cleanerModel.setRowCount(0);
                for (Object[] r : cl) {
                    long c = ((Number) r[1]).longValue();
                    cleanerModel.addRow(new Object[]{r[0], c == 0 ? "Available" : c + " booking(s)"});
                }
            }
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        pnlBody = new javax.swing.JPanel();
        pnlStats = new javax.swing.JPanel();
        pnlCard1 = new javax.swing.JPanel();
        lblCardTitle1 = new javax.swing.JLabel();
        lblToday = new javax.swing.JLabel();
        pnlCard2 = new javax.swing.JPanel();
        lblCardTitle2 = new javax.swing.JLabel();
        lblPay = new javax.swing.JLabel();
        pnlCard3 = new javax.swing.JPanel();
        lblCardTitle3 = new javax.swing.JLabel();
        lblClients = new javax.swing.JLabel();
        pnlCard4 = new javax.swing.JPanel();
        lblCardTitle4 = new javax.swing.JLabel();
        lblServices = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();
        pnlSchedule = new javax.swing.JPanel();
        pnlScheduleTitle = new javax.swing.JLabel();
        scrollSchedule = new javax.swing.JScrollPane();
        tblSchedule = new javax.swing.JTable();
        pnlCleaners = new javax.swing.JPanel();
        pnlCleanersTitle = new javax.swing.JLabel();
        scrollCleaners = new javax.swing.JScrollPane();
        tblCleaners = new javax.swing.JTable();

        setBackground(new java.awt.Color(243, 246, 248));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setLayout(new java.awt.BorderLayout(0, 14));
        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout());
        lblTitle.setText("Dashboard");
        lblTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 26));
        pnlHeader.add(lblTitle, java.awt.BorderLayout.PAGE_START);
        lblWelcome.setText("Welcome");
        lblWelcome.setForeground(new java.awt.Color(108, 122, 131));
        lblWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlHeader.add(lblWelcome, java.awt.BorderLayout.PAGE_END);
        add(pnlHeader, java.awt.BorderLayout.PAGE_START);
        pnlBody.setOpaque(false);
        pnlBody.setLayout(new java.awt.BorderLayout());
        pnlStats.setOpaque(false);
        pnlStats.setLayout(new java.awt.GridLayout(1, 4, 14, 0));
        pnlCard1.setBackground(new java.awt.Color(255, 255, 255));
        pnlCard1.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16));
        pnlCard1.setLayout(new java.awt.BorderLayout(0, 6));
        lblCardTitle1.setText("Today's Bookings");
        lblCardTitle1.setForeground(new java.awt.Color(108, 122, 131));
        lblCardTitle1.setFont(new java.awt.Font("Segoe UI", 1, 14));
        pnlCard1.add(lblCardTitle1, java.awt.BorderLayout.PAGE_START);
        lblToday.setText("0");
        lblToday.setForeground(new java.awt.Color(43, 58, 66));
        lblToday.setFont(new java.awt.Font("Segoe UI", 1, 26));
        pnlCard1.add(lblToday, java.awt.BorderLayout.CENTER);
        pnlStats.add(pnlCard1);
        pnlCard2.setBackground(new java.awt.Color(255, 255, 255));
        pnlCard2.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16));
        pnlCard2.setLayout(new java.awt.BorderLayout(0, 6));
        lblCardTitle2.setText("Today's Est. Payment");
        lblCardTitle2.setForeground(new java.awt.Color(108, 122, 131));
        lblCardTitle2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        pnlCard2.add(lblCardTitle2, java.awt.BorderLayout.PAGE_START);
        lblPay.setText("0");
        lblPay.setForeground(new java.awt.Color(43, 58, 66));
        lblPay.setFont(new java.awt.Font("Segoe UI", 1, 26));
        pnlCard2.add(lblPay, java.awt.BorderLayout.CENTER);
        pnlStats.add(pnlCard2);
        pnlCard3.setBackground(new java.awt.Color(255, 255, 255));
        pnlCard3.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16));
        pnlCard3.setLayout(new java.awt.BorderLayout(0, 6));
        lblCardTitle3.setText("Active Clients");
        lblCardTitle3.setForeground(new java.awt.Color(108, 122, 131));
        lblCardTitle3.setFont(new java.awt.Font("Segoe UI", 1, 14));
        pnlCard3.add(lblCardTitle3, java.awt.BorderLayout.PAGE_START);
        lblClients.setText("0");
        lblClients.setForeground(new java.awt.Color(43, 58, 66));
        lblClients.setFont(new java.awt.Font("Segoe UI", 1, 26));
        pnlCard3.add(lblClients, java.awt.BorderLayout.CENTER);
        pnlStats.add(pnlCard3);
        pnlCard4.setBackground(new java.awt.Color(255, 255, 255));
        pnlCard4.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16));
        pnlCard4.setLayout(new java.awt.BorderLayout(0, 6));
        lblCardTitle4.setText("Available Services");
        lblCardTitle4.setForeground(new java.awt.Color(108, 122, 131));
        lblCardTitle4.setFont(new java.awt.Font("Segoe UI", 1, 14));
        pnlCard4.add(lblCardTitle4, java.awt.BorderLayout.PAGE_START);
        lblServices.setText("0");
        lblServices.setForeground(new java.awt.Color(43, 58, 66));
        lblServices.setFont(new java.awt.Font("Segoe UI", 1, 26));
        pnlCard4.add(lblServices, java.awt.BorderLayout.CENTER);
        pnlStats.add(pnlCard4);
        pnlBody.add(pnlStats, java.awt.BorderLayout.PAGE_START);
        pnlMain.setOpaque(false);
        pnlMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 0, 0, 0));
        pnlMain.setLayout(new java.awt.GridBagLayout());
        pnlSchedule.setBackground(new java.awt.Color(255, 255, 255));
        pnlSchedule.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14));
        pnlSchedule.setLayout(new java.awt.BorderLayout(0, 8));
        pnlScheduleTitle.setText("Today's Schedule");
        pnlScheduleTitle.setForeground(new java.awt.Color(22, 80, 92));
        pnlScheduleTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        pnlSchedule.add(pnlScheduleTitle, java.awt.BorderLayout.PAGE_START);
        tblSchedule.setRowHeight(30);
        tblSchedule.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tblSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Time", "Client", "Service", "Cleaner", "Status", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollSchedule.setViewportView(tblSchedule);
        pnlSchedule.add(scrollSchedule, java.awt.BorderLayout.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 7);
        pnlMain.add(pnlSchedule, gridBagConstraints);
        pnlCleaners.setBackground(new java.awt.Color(255, 255, 255));
        pnlCleaners.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14));
        pnlCleaners.setPreferredSize(new java.awt.Dimension(320, 100));
        pnlCleaners.setLayout(new java.awt.BorderLayout(0, 8));
        pnlCleanersTitle.setText("Cleaners Today");
        pnlCleanersTitle.setForeground(new java.awt.Color(22, 80, 92));
        pnlCleanersTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        pnlCleaners.add(pnlCleanersTitle, java.awt.BorderLayout.PAGE_START);
        tblCleaners.setRowHeight(30);
        tblCleaners.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tblCleaners.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cleaner", "Today"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollCleaners.setViewportView(tblCleaners);
        pnlCleaners.add(scrollCleaners, java.awt.BorderLayout.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 0);
        pnlMain.add(pnlCleaners, gridBagConstraints);
        pnlBody.add(pnlMain, java.awt.BorderLayout.CENTER);
        add(pnlBody, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel pnlBody;
    private javax.swing.JPanel pnlStats;
    private javax.swing.JPanel pnlCard1;
    private javax.swing.JLabel lblCardTitle1;
    private javax.swing.JLabel lblToday;
    private javax.swing.JPanel pnlCard2;
    private javax.swing.JLabel lblCardTitle2;
    private javax.swing.JLabel lblPay;
    private javax.swing.JPanel pnlCard3;
    private javax.swing.JLabel lblCardTitle3;
    private javax.swing.JLabel lblClients;
    private javax.swing.JPanel pnlCard4;
    private javax.swing.JLabel lblCardTitle4;
    private javax.swing.JLabel lblServices;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSchedule;
    private javax.swing.JLabel pnlScheduleTitle;
    private javax.swing.JScrollPane scrollSchedule;
    private javax.swing.JTable tblSchedule;
    private javax.swing.JPanel pnlCleaners;
    private javax.swing.JLabel pnlCleanersTitle;
    private javax.swing.JScrollPane scrollCleaners;
    private javax.swing.JTable tblCleaners;
    // End of variables declaration//GEN-END:variables
}
