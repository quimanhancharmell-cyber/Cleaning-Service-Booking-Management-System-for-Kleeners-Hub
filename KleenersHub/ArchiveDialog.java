package KleenersHub;

/**
 * ArchiveDialog - GUI is editable in the NetBeans Design view (see ArchiveDialog.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ArchiveDialog extends javax.swing.JDialog {

    /** Gets the selected table row (0-based) when the user presses Restore / View. */
    public java.util.function.IntConsumer onRestore, onView;

    public ArchiveDialog(java.awt.Window parent, String title, String[] headers) {
        super(parent, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        setTitle(title);
        lblTitle.setText(title);
        tblArchive.setModel(UI.model(headers));
        UI.styleTable(tblArchive);
        UI.handCursor(this);
        setSize(900, 460);
        setMinimumSize(new java.awt.Dimension(600, 320));
        setLocationRelativeTo(parent);
    }

    public javax.swing.table.DefaultTableModel getModel() {
        return (javax.swing.table.DefaultTableModel) tblArchive.getModel();
    }

    private int pick() {
        int r = tblArchive.getSelectedRow();
        if (r < 0) {
            UI.info(this, "Please select a record from the table first.");
        }
        return r;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        scrollArchive = new javax.swing.JScrollPane();
        tblArchive = new javax.swing.JTable();
        pnlButtons = new javax.swing.JPanel();
        btnView = new javax.swing.JButton();
        btnRestore = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Archived");

        lblTitle.setText("Archived");
        lblTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 4, 14));
        getContentPane().add(lblTitle);

        scrollArchive.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 14, 0, 14));

        tblArchive.setRowHeight(30);
        tblArchive.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblArchive.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblArchive.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblArchiveMouseClicked(evt);
            }
        });
        scrollArchive.setViewportView(tblArchive);

        getContentPane().add(scrollArchive);

        pnlButtons.setOpaque(false);
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 8));

        btnView.setText("View");
        btnView.setBackground(new java.awt.Color(31, 122, 140));
        btnView.setForeground(new java.awt.Color(255, 255, 255));
        btnView.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnView.setOpaque(true);
        btnView.setFocusPainted(false);
        btnView.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        pnlButtons.add(btnView);

        btnRestore.setText("Restore");
        btnRestore.setBackground(new java.awt.Color(46, 139, 87));
        btnRestore.setForeground(new java.awt.Color(255, 255, 255));
        btnRestore.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRestore.setOpaque(true);
        btnRestore.setFocusPainted(false);
        btnRestore.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreActionPerformed(evt);
            }
        });
        pnlButtons.add(btnRestore);

        btnClose.setText("Close");
        btnClose.setBackground(new java.awt.Color(108, 122, 131));
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClose.setOpaque(true);
        btnClose.setFocusPainted(false);
        btnClose.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        pnlButtons.add(btnClose);

        getContentPane().add(pnlButtons);
    }// </editor-fold>//GEN-END:initComponents

    private void tblArchiveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblArchiveMouseClicked
        if (evt.getClickCount() == 2 && onView != null && tblArchive.getSelectedRow() >= 0) {
            onView.accept(tblArchive.getSelectedRow());
        }
    }//GEN-LAST:event_tblArchiveMouseClicked

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        int r = pick();
        if (r >= 0 && onView != null) {
            onView.accept(r);
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreActionPerformed
        int r = pick();
        if (r >= 0 && onRestore != null) {
            onRestore.accept(r);
        }
    }//GEN-LAST:event_btnRestoreActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRestore;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JScrollPane scrollArchive;
    private javax.swing.JTable tblArchive;
    // End of variables declaration//GEN-END:variables
}
