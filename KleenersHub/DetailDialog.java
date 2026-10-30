package KleenersHub;

/**
 * DetailDialog - GUI is editable in the NetBeans Design view (see DetailDialog.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DetailDialog extends javax.swing.JDialog {

    public DetailDialog(java.awt.Window parent, String title, String text) {
        super(parent, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        setTitle(title);
        txtDetails.setText(text);
        txtDetails.setCaretPosition(0);
        txtDetails.setMargin(new java.awt.Insets(8, 10, 8, 10));
        UI.handCursor(this);
        getRootPane().setDefaultButton(btnClose);
        setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollDetails = new javax.swing.JScrollPane();
        txtDetails = new javax.swing.JTextArea();
        pnlButtons = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Details");
        getContentPane().setLayout(new java.awt.BorderLayout());
        scrollDetails.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 0, 12));
        scrollDetails.setPreferredSize(new java.awt.Dimension(540, 300));
        txtDetails.setEditable(false);
        txtDetails.setLineWrap(true);
        txtDetails.setWrapStyleWord(true);
        txtDetails.setColumns(40);
        txtDetails.setRows(10);
        txtDetails.setFont(new java.awt.Font("Monospaced", 14, 0));
        scrollDetails.setViewportView(txtDetails);
        getContentPane().add(scrollDetails, java.awt.BorderLayout.CENTER);
        pnlButtons.setOpaque(false);
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 8));
        btnClose.setText("Close");
        btnClose.setBackground(new java.awt.Color(108, 122, 131));
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnClose.setOpaque(true);
        btnClose.setFocusPainted(false);
        btnClose.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        pnlButtons.add(btnClose);
        getContentPane().add(pnlButtons, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollDetails;
    private javax.swing.JTextArea txtDetails;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JButton btnClose;
    // End of variables declaration//GEN-END:variables
}
