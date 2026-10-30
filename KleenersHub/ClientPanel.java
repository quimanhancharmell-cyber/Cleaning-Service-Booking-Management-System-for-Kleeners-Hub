package KleenersHub;

/**
 * ClientPanel - GUI is editable in the NetBeans Design view (see ClientPanel.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ClientPanel extends javax.swing.JPanel implements Refreshable {

    private EntityHelper h;
    private Integer editingId = null;

    public ClientPanel() {
        initComponents();
        UI.styleCard(pnlCard);
        UI.styleTable(tblClients);
        UI.styleFields(tfName, tfContact, tfAddress, tfSearch);
        UI.phoneOnly(tfContact);
        UI.handCursor(this);
        btnCancel.setVisible(false);
        h = new EntityHelper(this, tblClients, "Client", "clients", "client_id",
                new String[]{"client_name", "contact_number", "address"},
                new String[]{"Client Name", "Contact Number", "Address"}, -1);
    }

    @Override
    public void refresh() {
        h.load(tfSearch.getText());
    }

    /** Back to "Add" mode with empty fields. */
    private void resetForm() {
        tfName.setText("");
        tfContact.setText("");
        tfAddress.setText("");
        editingId = null;
        btnSave.setText("Add Client");
        btnCancel.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblTitle = new javax.swing.JLabel();
        pnlCard = new javax.swing.JPanel();
        pnlTop = new javax.swing.JPanel();
        pnlFields = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lblContact = new javax.swing.JLabel();
        tfContact = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        tfAddress = new javax.swing.JTextField();
        pnlButtons = new javax.swing.JPanel();
        pnlBtnLeft = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnArchiveList = new javax.swing.JButton();
        pnlSearch = new javax.swing.JPanel();
        lblSearch = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        scrollTable = new javax.swing.JScrollPane();
        tblClients = new javax.swing.JTable();

        setBackground(new java.awt.Color(243, 246, 248));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setLayout(new java.awt.BorderLayout(0, 12));
        lblTitle.setText("Client Management");
        lblTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 26));
        add(lblTitle, java.awt.BorderLayout.PAGE_START);
        pnlCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14));
        pnlCard.setLayout(new java.awt.BorderLayout(0, 12));
        pnlTop.setOpaque(false);
        pnlTop.setLayout(new java.awt.BorderLayout(0, 10));
        pnlFields.setOpaque(false);
        pnlFields.setLayout(new java.awt.GridBagLayout());
        lblName.setText("Client Name");
        lblName.setForeground(new java.awt.Color(43, 58, 66));
        lblName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 6, 10);
        pnlFields.add(lblName, gridBagConstraints);
        tfName.setColumns(20);
        tfName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 6, 14);
        pnlFields.add(tfName, gridBagConstraints);
        lblContact.setText("Contact Number (11 digits)");
        lblContact.setForeground(new java.awt.Color(43, 58, 66));
        lblContact.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 6, 10);
        pnlFields.add(lblContact, gridBagConstraints);
        tfContact.setColumns(20);
        tfContact.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 6, 14);
        pnlFields.add(tfContact, gridBagConstraints);
        lblAddress.setText("Address");
        lblAddress.setForeground(new java.awt.Color(43, 58, 66));
        lblAddress.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 6, 10);
        pnlFields.add(lblAddress, gridBagConstraints);
        tfAddress.setColumns(20);
        tfAddress.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 6, 14);
        pnlFields.add(tfAddress, gridBagConstraints);
        pnlTop.add(pnlFields, java.awt.BorderLayout.CENTER);
        pnlButtons.setOpaque(false);
        pnlButtons.setLayout(new java.awt.BorderLayout(0, 8));
        pnlBtnLeft.setOpaque(false);
        pnlBtnLeft.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        btnSave.setText("Add Client");
        btnSave.setBackground(new java.awt.Color(46, 139, 87));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnSave.setOpaque(true);
        btnSave.setFocusPainted(false);
        btnSave.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        pnlBtnLeft.add(btnSave);
        btnCancel.setText("Cancel Edit");
        btnCancel.setBackground(new java.awt.Color(108, 122, 131));
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnCancel.setOpaque(true);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        pnlBtnLeft.add(btnCancel);
        btnView.setText("View");
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
        pnlBtnLeft.add(btnView);
        btnEdit.setText("Edit");
        btnEdit.setBackground(new java.awt.Color(230, 126, 34));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnEdit.setOpaque(true);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        pnlBtnLeft.add(btnEdit);
        btnArchive.setText("Archive");
        btnArchive.setBackground(new java.awt.Color(192, 57, 43));
        btnArchive.setForeground(new java.awt.Color(255, 255, 255));
        btnArchive.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnArchive.setOpaque(true);
        btnArchive.setFocusPainted(false);
        btnArchive.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnArchive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchiveActionPerformed(evt);
            }
        });
        pnlBtnLeft.add(btnArchive);
        btnArchiveList.setText("Archived List");
        btnArchiveList.setBackground(new java.awt.Color(108, 122, 131));
        btnArchiveList.setForeground(new java.awt.Color(255, 255, 255));
        btnArchiveList.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnArchiveList.setOpaque(true);
        btnArchiveList.setFocusPainted(false);
        btnArchiveList.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnArchiveList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchiveListActionPerformed(evt);
            }
        });
        pnlBtnLeft.add(btnArchiveList);
        pnlButtons.add(pnlBtnLeft, java.awt.BorderLayout.PAGE_START);
        pnlSearch.setOpaque(false);
        pnlSearch.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        lblSearch.setText("Search:");
        lblSearch.setForeground(new java.awt.Color(43, 58, 66));
        lblSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlSearch.add(lblSearch);
        tfSearch.setColumns(22);
        tfSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchKeyReleased(evt);
            }
        });
        pnlSearch.add(tfSearch);
        pnlButtons.add(pnlSearch, java.awt.BorderLayout.PAGE_END);
        pnlTop.add(pnlButtons, java.awt.BorderLayout.PAGE_END);
        pnlCard.add(pnlTop, java.awt.BorderLayout.PAGE_START);
        tblClients.setRowHeight(30);
        tblClients.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tblClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Client Name", "Contact Number", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientsMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(tblClients);
        pnlCard.add(scrollTable, java.awt.BorderLayout.CENTER);
        add(pnlCard, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String name = tfName.getText().trim(), contact = tfContact.getText().trim(), address = tfAddress.getText().trim();
        if (name.isEmpty()) {
            UI.error(this, "Client name is required.");
            tfName.requestFocus();
            return;
        }
        if (!contact.matches("\\d{11}")) {
            UI.error(this, "Contact number must be exactly 11 digits (numbers only).");
            tfContact.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            UI.error(this, "Address is required.");
            tfAddress.requestFocus();
            return;
        }
        try {
            Object[] v = {name, contact, address};
            if (editingId == null) {
                h.insert(v); // the ID number is created automatically by the database
            } else {
                h.update(editingId, v);
            }
            resetForm();
            refresh();
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        resetForm();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        int id = h.selectedId();
        if (id > 0) {
            h.view(id);
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int id = h.selectedId();
        if (id < 0) {
            return;
        }
        try {
            Object[] v = h.fetch(id);
            if (v == null) {
                return;
            }
            tfName.setText(String.valueOf(v[0]));
            tfContact.setText(String.valueOf(v[1]));
            tfAddress.setText(String.valueOf(v[2]));
            editingId = id;
            btnSave.setText("Save Changes");
            btnCancel.setVisible(true);
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        if (h.archiveSelected()) {
            resetForm();
            refresh();
        }
    }//GEN-LAST:event_btnArchiveActionPerformed

    private void btnArchiveListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveListActionPerformed
        h.openArchive(this::refresh);
    }//GEN-LAST:event_btnArchiveListActionPerformed

    private void tfSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyReleased
        refresh();
    }//GEN-LAST:event_tfSearchKeyReleased

    private void tblClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientsMouseClicked
        if (evt.getClickCount() == 2) {
            int id = h.selectedId();
            if (id > 0) {
                h.view(id);
            }
        }
    }//GEN-LAST:event_tblClientsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JPanel pnlFields;
    private javax.swing.JLabel lblName;
    private javax.swing.JTextField tfName;
    private javax.swing.JLabel lblContact;
    private javax.swing.JTextField tfContact;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JTextField tfAddress;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlBtnLeft;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnArchiveList;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextField tfSearch;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable tblClients;
    // End of variables declaration//GEN-END:variables
}
