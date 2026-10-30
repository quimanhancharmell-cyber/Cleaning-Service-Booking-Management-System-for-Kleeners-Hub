package KleenersHub;

/**
 * RecordsPanel - GUI is editable in the NetBeans Design view (see RecordsPanel.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RecordsPanel extends javax.swing.JPanel implements Refreshable {

    private final javax.swing.table.DefaultTableModel model;
    private final java.util.List<Integer> ids = new java.util.ArrayList<>();

    public RecordsPanel() {
        if (!Session.isAdmin()) {
            throw new IllegalStateException("Booking Records is for Admin only.");
        }
        initComponents();
        model = (javax.swing.table.DefaultTableModel) tblRecords.getModel();
        UI.styleCard(pnlCard);
        UI.styleTable(tblRecords);
        UI.styleFields(tfSearch);
        UI.handCursor(this);
    }

    private void openSelected() {
        int r = tblRecords.getSelectedRow();
        if (r < 0) {
            UI.info(this, "Please select a record from the table first.");
            return;
        }
        BookingUtil.showDetails(UI.owner(this), ids.get(r), this::refresh);
    }

    @Override
    public void refresh() {
        try {
            StringBuilder sql = new StringBuilder("SELECT b.booking_id, c.client_name, c.contact_number, s.service_name, "
                    + "COALESCE(cl.cleaner_name,'-'), b.property_type, b.booking_date, b.booking_time, b.payment_method, "
                    + "b.total_amount, b.status, b.is_archived "
                    + "FROM bookings b JOIN clients c ON c.client_id=b.client_id JOIN services s ON s.service_id=b.service_id "
                    + "LEFT JOIN cleaners cl ON cl.cleaner_id=b.cleaner_id WHERE 1=1");
            java.util.List<Object> p = new java.util.ArrayList<>();
            String st = String.valueOf(cbStatus.getSelectedItem());
            if (!"All".equals(st)) {
                sql.append(" AND b.status=?");
                p.add(st);
            }
            String q = tfSearch.getText().trim();
            if (!q.isEmpty()) {
                sql.append(" AND (c.client_name LIKE ? OR s.service_name LIKE ? OR cl.cleaner_name LIKE ?)");
                for (int i = 0; i < 3; i++) {
                    p.add("%" + q + "%");
                }
            }
            sql.append(" ORDER BY b.booking_date DESC, b.booking_time DESC, b.booking_id DESC");
            java.util.List<Object[]> rows = DB.query(sql.toString(), p.toArray());
            model.setRowCount(0);
            ids.clear();
            java.math.BigDecimal sum = java.math.BigDecimal.ZERO;
            int n = 1;
            for (Object[] r : rows) {
                ids.add(((Number) r[0]).intValue());
                boolean archived = "true".equals(String.valueOf(r[11])) || "1".equals(String.valueOf(r[11]));
                if (!archived && !"Cancelled".equals(r[10])) {
                    sum = sum.add(new java.math.BigDecimal(r[9].toString()));
                }
                model.addRow(new Object[]{n++, r[1], r[2], r[3], r[4], r[5], UI.dateStr(r[6]), UI.timeStr(r[7]),
                    r[8], UI.peso(r[9]), r[10], archived ? "Yes" : "No"});
            }
            lblSummary.setText("Records: " + rows.size() + "   |   Total Estimated Payment (active): " + UI.peso(sum));
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        pnlCard = new javax.swing.JPanel();
        pnlBar = new javax.swing.JPanel();
        pnlBarTop = new javax.swing.JPanel();
        btnView = new javax.swing.JButton();
        pnlBarBottom = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        lblSearch = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        lblSummary = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        tblRecords = new javax.swing.JTable();

        setBackground(new java.awt.Color(243, 246, 248));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setLayout(new java.awt.BorderLayout(0, 12));
        lblTitle.setText("Booking Records");
        lblTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 26));
        add(lblTitle, java.awt.BorderLayout.PAGE_START);
        pnlCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14));
        pnlCard.setLayout(new java.awt.BorderLayout(0, 10));
        pnlBar.setOpaque(false);
        pnlBar.setLayout(new java.awt.BorderLayout(0, 8));
        pnlBarTop.setOpaque(false);
        pnlBarTop.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        btnView.setText("View Details");
        btnView.setBackground(new java.awt.Color(31, 122, 140));
        btnView.setForeground(new java.awt.Color(255, 255, 255));
        btnView.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnView.setOpaque(true);
        btnView.setFocusPainted(false);
        btnView.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        pnlBarTop.add(btnView);
        pnlBar.add(pnlBarTop, java.awt.BorderLayout.PAGE_START);
        pnlBarBottom.setOpaque(false);
        pnlBarBottom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        lblStatus.setText("Status:");
        lblStatus.setForeground(new java.awt.Color(43, 58, 66));
        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlBarBottom.add(lblStatus);
        cbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Pending", "Confirmed", "Completed", "Cancelled" }));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });
        pnlBarBottom.add(cbStatus);
        lblSearch.setText("Search:");
        lblSearch.setForeground(new java.awt.Color(43, 58, 66));
        lblSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlBarBottom.add(lblSearch);
        tfSearch.setColumns(18);
        tfSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchKeyReleased(evt);
            }
        });
        pnlBarBottom.add(tfSearch);
        pnlBar.add(pnlBarBottom, java.awt.BorderLayout.CENTER);
        lblSummary.setText(" ");
        lblSummary.setForeground(new java.awt.Color(108, 122, 131));
        lblSummary.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlBar.add(lblSummary, java.awt.BorderLayout.PAGE_END);
        pnlCard.add(pnlBar, java.awt.BorderLayout.PAGE_START);
        tblRecords.setRowHeight(30);
        tblRecords.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tblRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Client", "Contact", "Service", "Cleaner", "Property", "Date", "Time", "Payment", "Total Estimated Payment", "Status", "Archived"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRecords.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRecordsMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(tblRecords);
        pnlCard.add(scrollTable, java.awt.BorderLayout.CENTER);
        add(pnlCard, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        openSelected();
    }//GEN-LAST:event_btnViewActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        refresh();
    }//GEN-LAST:event_cbStatusActionPerformed

    private void tfSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyReleased
        refresh();
    }//GEN-LAST:event_tfSearchKeyReleased

    private void tblRecordsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRecordsMouseClicked
        if (evt.getClickCount() == 2) {
            openSelected();
        }
    }//GEN-LAST:event_tblRecordsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JPanel pnlBar;
    private javax.swing.JPanel pnlBarTop;
    private javax.swing.JButton btnView;
    private javax.swing.JPanel pnlBarBottom;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextField tfSearch;
    private javax.swing.JLabel lblSummary;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable tblRecords;
    // End of variables declaration//GEN-END:variables
}
