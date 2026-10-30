package KleenersHub;

/**
 * BookingDialog - GUI is editable in the NetBeans Design view (see BookingDialog.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BookingDialog extends javax.swing.JDialog {

    private final java.util.Map<String, String> data;
    private final Runnable onChanged;

    /** summary=true: shows the "Booking added successfully" summary (no status editing). */
    public BookingDialog(java.awt.Window parent, java.util.Map<String, String> data, boolean summary, Runnable onChanged) {
        super(parent, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        this.data = data;
        this.onChanged = onChanged;
        initComponents();
        setTitle(summary ? "Booking Summary" : "Booking Details");
        if (summary) {
            lblHeader.setText("Booking added successfully!");
            lblHeader.setForeground(UI.OK);
            pnlStatus.setVisible(false);
            btnReceipt.setText("View Receipt");
        }
        txtDetails.setText(BookingUtil.detailText(data));
        txtDetails.setCaretPosition(0);
        txtDetails.setMargin(new java.awt.Insets(8, 10, 8, 10));
        cbStatus.setSelectedItem(data.get("status"));
        UI.handCursor(this);
        pack();
        setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblHeader = new javax.swing.JLabel();
        scrollDetails = new javax.swing.JScrollPane();
        txtDetails = new javax.swing.JTextArea();
        pnlFoot = new javax.swing.JPanel();
        pnlStatus = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        btnSaveStatus = new javax.swing.JButton();
        pnlBtns = new javax.swing.JPanel();
        btnReceipt = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Booking Details");

        lblHeader.setText("Booking Details");
        lblHeader.setForeground(new java.awt.Color(22, 80, 92));
        lblHeader.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 10, 4, 10));
        getContentPane().add(lblHeader);

        scrollDetails.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 14, 0, 14));
        scrollDetails.setPreferredSize(new java.awt.Dimension(560, 430));

        txtDetails.setEditable(false);
        txtDetails.setLineWrap(true);
        txtDetails.setWrapStyleWord(true);
        txtDetails.setColumns(44);
        txtDetails.setRows(18);
        txtDetails.setFont(new java.awt.Font("Monospaced", 0, 0)); // NOI18N
        scrollDetails.setViewportView(txtDetails);

        getContentPane().add(scrollDetails);

        pnlFoot.setOpaque(false);
        pnlFoot.setLayout(new java.awt.BorderLayout());

        pnlStatus.setOpaque(false);
        pnlStatus.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 8));

        lblStatus.setText("Status:");
        lblStatus.setForeground(new java.awt.Color(43, 58, 66));
        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlStatus.add(lblStatus);

        cbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Confirmed", "Completed", "Cancelled" }));
        pnlStatus.add(cbStatus);

        btnSaveStatus.setText("Save Status");
        btnSaveStatus.setBackground(new java.awt.Color(46, 139, 87));
        btnSaveStatus.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSaveStatus.setOpaque(true);
        btnSaveStatus.setFocusPainted(false);
        btnSaveStatus.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnSaveStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveStatusActionPerformed(evt);
            }
        });
        pnlStatus.add(btnSaveStatus);

        pnlFoot.add(pnlStatus);

        pnlBtns.setOpaque(false);
        pnlBtns.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 8));

        btnReceipt.setBackground(new java.awt.Color(31, 122, 140));
        btnReceipt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReceipt.setForeground(new java.awt.Color(255, 255, 255));
        btnReceipt.setText("Receipt");
        btnReceipt.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnReceipt.setFocusPainted(false);
        btnReceipt.setOpaque(true);
        btnReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReceiptActionPerformed(evt);
            }
        });
        pnlBtns.add(btnReceipt);

        btnClose.setBackground(new java.awt.Color(108, 122, 131));
        btnClose.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Close");
        btnClose.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnClose.setFocusPainted(false);
        btnClose.setOpaque(true);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        pnlBtns.add(btnClose);

        pnlFoot.add(pnlBtns);

        getContentPane().add(pnlFoot);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveStatusActionPerformed
        try {
            DB.update("UPDATE bookings SET status=? WHERE booking_id=?", cbStatus.getSelectedItem(), Integer.parseInt(data.get("id")));
            data.put("status", String.valueOf(cbStatus.getSelectedItem()));
            txtDetails.setText(BookingUtil.detailText(data));
            UI.info(this, "Status updated.");
            if (onChanged != null) {
                onChanged.run();
            }
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }//GEN-LAST:event_btnSaveStatusActionPerformed

    private void btnReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceiptActionPerformed
        new ReceiptDialog(this, BookingUtil.receiptText(data)).setVisible(true);
    }//GEN-LAST:event_btnReceiptActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnReceipt;
    private javax.swing.JButton btnSaveStatus;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnlBtns;
    private javax.swing.JPanel pnlFoot;
    private javax.swing.JPanel pnlStatus;
    private javax.swing.JScrollPane scrollDetails;
    private javax.swing.JTextArea txtDetails;
    // End of variables declaration//GEN-END:variables
}
