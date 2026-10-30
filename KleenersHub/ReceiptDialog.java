package KleenersHub;

/**
 * ReceiptDialog - GUI is editable in the NetBeans Design view (see ReceiptDialog.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ReceiptDialog extends javax.swing.JDialog {

    public ReceiptDialog(java.awt.Window parent, String text) {
        super(parent, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        txtReceipt.setText(text);
        txtReceipt.setCaretPosition(0);
        txtReceipt.setMargin(new java.awt.Insets(14, 18, 14, 18));
        UI.handCursor(this);
        pack();
        setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollReceipt = new javax.swing.JScrollPane();
        txtReceipt = new javax.swing.JTextArea();
        pnlButtons = new javax.swing.JPanel();
        btnPrint = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Receipt");
        getContentPane().setLayout(new java.awt.BorderLayout());
        scrollReceipt.setPreferredSize(new java.awt.Dimension(480, 620));
        txtReceipt.setEditable(false);
        txtReceipt.setLineWrap(false);
        txtReceipt.setWrapStyleWord(true);
        txtReceipt.setColumns(44);
        txtReceipt.setRows(30);
        txtReceipt.setFont(new java.awt.Font("Monospaced", 13, 0));
        txtReceipt.setBackground(new java.awt.Color(255, 254, 248));
        scrollReceipt.setViewportView(txtReceipt);
        getContentPane().add(scrollReceipt, java.awt.BorderLayout.CENTER);
        pnlButtons.setOpaque(false);
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 8));
        btnPrint.setText("Print");
        btnPrint.setBackground(new java.awt.Color(31, 122, 140));
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnPrint.setOpaque(true);
        btnPrint.setFocusPainted(false);
        btnPrint.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        pnlButtons.add(btnPrint);
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

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        try {
            txtReceipt.print();
        } catch (java.awt.print.PrinterException ex) {
            UI.error(this, "Cannot print: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollReceipt;
    private javax.swing.JTextArea txtReceipt;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnClose;
    // End of variables declaration//GEN-END:variables
}
