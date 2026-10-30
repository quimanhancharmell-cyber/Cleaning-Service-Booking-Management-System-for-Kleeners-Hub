package KleenersHub;

/**
 * SettingPanel - GUI is editable in the NetBeans Design view (see SettingPanel.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SettingPanel extends javax.swing.JPanel implements Refreshable {

    private final MainFrame frame;
    private final javax.swing.table.DefaultTableModel usersModel;
    private final java.util.List<Integer> userIds = new java.util.ArrayList<>();

    public SettingPanel(MainFrame frame) {
        this.frame = frame;
        initComponents();
        usersModel = (javax.swing.table.DefaultTableModel) tblUsers.getModel();
        UI.styleCard(pnlPassword);
        UI.styleCard(pnlSystem);
        UI.styleCard(pnlUsers);
        UI.styleTable(tblUsers);
        UI.styleFields(pfCurrent, pfNew, pfConfirm, tfSystem);
        UI.handCursor(this);
        scrollMain.getVerticalScrollBar().setUnitIncrement(16);
        scrollMain.getViewport().setOpaque(false);
        lblPwTitle.setText("My Account  -  " + Session.fullName + " (" + Session.role + ")");
        // Only the Admin manages the system name and the Staff accounts.
        pnlSystem.setVisible(Session.isAdmin());
        pnlUsers.setVisible(Session.isAdmin());
    }

    private int selectedUser() {
        int r = tblUsers.getSelectedRow();
        if (r < 0) {
            UI.info(this, "Please select an account from the table first.");
            return -1;
        }
        return userIds.get(r);
    }

    private void loadUsers() {
        try {
            usersModel.setRowCount(0);
            userIds.clear();
            int n = 1;
            for (Object[] r : DB.query("SELECT user_id, username, full_name, role, status FROM users ORDER BY user_id")) {
                userIds.add(((Number) r[0]).intValue());
                usersModel.addRow(new Object[]{n++, r[1], r[2], r[3], r[4]});
            }
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }

    @Override
    public void refresh() {
        if (Session.isAdmin()) {
            try {
                tfSystem.setText(UI.str(DB.scalar("SELECT system_name FROM settings WHERE id=1")));
            } catch (java.sql.SQLException ex) {
                UI.dbError(this, ex);
            }
            loadUsers();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblTitle = new javax.swing.JLabel();
        scrollMain = new javax.swing.JScrollPane();
        pnlWrap = new javax.swing.JPanel();
        pnlStack = new javax.swing.JPanel();
        pnlPassword = new javax.swing.JPanel();
        lblPwTitle = new javax.swing.JLabel();
        pnlPwWrap = new javax.swing.JPanel();
        pnlPwFields = new javax.swing.JPanel();
        lblCurrent = new javax.swing.JLabel();
        pfCurrent = new javax.swing.JPasswordField();
        lblNew = new javax.swing.JLabel();
        pfNew = new javax.swing.JPasswordField();
        lblConfirm = new javax.swing.JLabel();
        pfConfirm = new javax.swing.JPasswordField();
        btnChangePw = new javax.swing.JButton();
        pnlSystem = new javax.swing.JPanel();
        lblSysTitle = new javax.swing.JLabel();
        pnlSysRow = new javax.swing.JPanel();
        tfSystem = new javax.swing.JTextField();
        btnSaveSystem = new javax.swing.JButton();
        pnlUsers = new javax.swing.JPanel();
        lblUsersTitle = new javax.swing.JLabel();
        scrollUsers = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        pnlUserBar = new javax.swing.JPanel();
        btnAddStaff = new javax.swing.JButton();
        btnResetPw = new javax.swing.JButton();
        btnToggle = new javax.swing.JButton();

        setBackground(new java.awt.Color(243, 246, 248));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setLayout(new java.awt.BorderLayout(0, 12));
        lblTitle.setText("Settings");
        lblTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 26));
        add(lblTitle, java.awt.BorderLayout.PAGE_START);
        scrollMain.setOpaque(false);
        pnlWrap.setOpaque(false);
        pnlWrap.setLayout(new java.awt.BorderLayout());
        pnlStack.setOpaque(false);
        pnlStack.setLayout(new java.awt.GridBagLayout());
        pnlPassword.setBackground(new java.awt.Color(255, 255, 255));
        pnlPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16));
        pnlPassword.setLayout(new java.awt.BorderLayout(0, 10));
        lblPwTitle.setText("My Account");
        lblPwTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblPwTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        pnlPassword.add(lblPwTitle, java.awt.BorderLayout.PAGE_START);
        pnlPwWrap.setOpaque(false);
        pnlPwWrap.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        pnlPwFields.setOpaque(false);
        pnlPwFields.setLayout(new java.awt.GridBagLayout());
        lblCurrent.setText("Current Password");
        lblCurrent.setForeground(new java.awt.Color(43, 58, 66));
        lblCurrent.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 12);
        pnlPwFields.add(lblCurrent, gridBagConstraints);
        pfCurrent.setColumns(18);
        pfCurrent.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        pnlPwFields.add(pfCurrent, gridBagConstraints);
        lblNew.setText("New Password");
        lblNew.setForeground(new java.awt.Color(43, 58, 66));
        lblNew.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 12);
        pnlPwFields.add(lblNew, gridBagConstraints);
        pfNew.setColumns(18);
        pfNew.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        pnlPwFields.add(pfNew, gridBagConstraints);
        lblConfirm.setText("Confirm New Password");
        lblConfirm.setForeground(new java.awt.Color(43, 58, 66));
        lblConfirm.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 12);
        pnlPwFields.add(lblConfirm, gridBagConstraints);
        pfConfirm.setColumns(18);
        pfConfirm.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        pnlPwFields.add(pfConfirm, gridBagConstraints);
        btnChangePw.setText("Change Password");
        btnChangePw.setBackground(new java.awt.Color(31, 122, 140));
        btnChangePw.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePw.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnChangePw.setOpaque(true);
        btnChangePw.setFocusPainted(false);
        btnChangePw.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnChangePw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePwActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 4, 0);
        pnlPwFields.add(btnChangePw, gridBagConstraints);
        pnlPwWrap.add(pnlPwFields);
        pnlPassword.add(pnlPwWrap, java.awt.BorderLayout.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        pnlStack.add(pnlPassword, gridBagConstraints);
        pnlSystem.setBackground(new java.awt.Color(255, 255, 255));
        pnlSystem.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16));
        pnlSystem.setLayout(new java.awt.BorderLayout(0, 10));
        lblSysTitle.setText("System Name");
        lblSysTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblSysTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        pnlSystem.add(lblSysTitle, java.awt.BorderLayout.PAGE_START);
        pnlSysRow.setOpaque(false);
        pnlSysRow.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        tfSystem.setColumns(24);
        tfSystem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlSysRow.add(tfSystem);
        btnSaveSystem.setText("Save");
        btnSaveSystem.setBackground(new java.awt.Color(46, 139, 87));
        btnSaveSystem.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveSystem.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnSaveSystem.setOpaque(true);
        btnSaveSystem.setFocusPainted(false);
        btnSaveSystem.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnSaveSystem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveSystemActionPerformed(evt);
            }
        });
        pnlSysRow.add(btnSaveSystem);
        pnlSystem.add(pnlSysRow, java.awt.BorderLayout.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        pnlStack.add(pnlSystem, gridBagConstraints);
        pnlUsers.setBackground(new java.awt.Color(255, 255, 255));
        pnlUsers.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16));
        pnlUsers.setLayout(new java.awt.BorderLayout(0, 10));
        lblUsersTitle.setText("User Accounts (Admin and Staff)");
        lblUsersTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblUsersTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        pnlUsers.add(lblUsersTitle, java.awt.BorderLayout.PAGE_START);
        scrollUsers.setPreferredSize(new java.awt.Dimension(400, 200));
        tblUsers.setRowHeight(30);
        tblUsers.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Username", "Full Name", "Role", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollUsers.setViewportView(tblUsers);
        pnlUsers.add(scrollUsers, java.awt.BorderLayout.CENTER);
        pnlUserBar.setOpaque(false);
        pnlUserBar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        btnAddStaff.setText("+ Add Staff");
        btnAddStaff.setBackground(new java.awt.Color(46, 139, 87));
        btnAddStaff.setForeground(new java.awt.Color(255, 255, 255));
        btnAddStaff.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnAddStaff.setOpaque(true);
        btnAddStaff.setFocusPainted(false);
        btnAddStaff.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnAddStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStaffActionPerformed(evt);
            }
        });
        pnlUserBar.add(btnAddStaff);
        btnResetPw.setText("Reset Password");
        btnResetPw.setBackground(new java.awt.Color(230, 126, 34));
        btnResetPw.setForeground(new java.awt.Color(255, 255, 255));
        btnResetPw.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnResetPw.setOpaque(true);
        btnResetPw.setFocusPainted(false);
        btnResetPw.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnResetPw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetPwActionPerformed(evt);
            }
        });
        pnlUserBar.add(btnResetPw);
        btnToggle.setText("Activate / Deactivate");
        btnToggle.setBackground(new java.awt.Color(108, 122, 131));
        btnToggle.setForeground(new java.awt.Color(255, 255, 255));
        btnToggle.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnToggle.setOpaque(true);
        btnToggle.setFocusPainted(false);
        btnToggle.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToggleActionPerformed(evt);
            }
        });
        pnlUserBar.add(btnToggle);
        pnlUsers.add(pnlUserBar, java.awt.BorderLayout.PAGE_END);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
        pnlStack.add(pnlUsers, gridBagConstraints);
        pnlWrap.add(pnlStack, java.awt.BorderLayout.PAGE_START);
        scrollMain.setViewportView(pnlWrap);
        add(scrollMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnChangePwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePwActionPerformed
        String c = new String(pfCurrent.getPassword()), n = new String(pfNew.getPassword()), k = new String(pfConfirm.getPassword());
        if (n.length() < 4) {
            UI.error(this, "New password must be at least 4 characters.");
            return;
        }
        if (!n.equals(k)) {
            UI.error(this, "New password and confirmation do not match.");
            return;
        }
        try {
            int rows = DB.update("UPDATE users SET password=? WHERE user_id=? AND BINARY password=?", n, Session.userId, c);
            if (rows == 0) {
                UI.error(this, "Current password is incorrect.");
                return;
            }
            pfCurrent.setText("");
            pfNew.setText("");
            pfConfirm.setText("");
            UI.info(this, "Password changed.");
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }//GEN-LAST:event_btnChangePwActionPerformed

    private void btnSaveSystemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveSystemActionPerformed
        String n = tfSystem.getText().trim();
        if (n.isEmpty()) {
            UI.error(this, "System name cannot be empty.");
            return;
        }
        try {
            DB.update("UPDATE settings SET system_name=? WHERE id=1", n);
            frame.setSystemName(n);
            UI.info(this, "System name updated.");
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }//GEN-LAST:event_btnSaveSystemActionPerformed

    private void btnAddStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStaffActionPerformed
        javax.swing.JTextField user = new javax.swing.JTextField(20), name = new javax.swing.JTextField(20);
        javax.swing.JPasswordField pw = new javax.swing.JPasswordField(20);
        int ok = javax.swing.JOptionPane.showConfirmDialog(this,
                new Object[]{"Username:", user, "Full Name:", name, "Password (min. 4 characters):", pw},
                "Add Staff", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (ok != javax.swing.JOptionPane.OK_OPTION) {
            return;
        }
        String u = user.getText().trim(), n = name.getText().trim(), p = new String(pw.getPassword());
        if (u.isEmpty() || n.isEmpty() || p.length() < 4) {
            UI.error(this, "Fill in all fields. Password must be at least 4 characters.");
            return;
        }
        try {
            DB.insert("INSERT INTO users (username, password, full_name, role, status) VALUES (?,?,?,'Staff','Active')", u, p, n);
            loadUsers();
        } catch (java.sql.SQLException ex) {
            UI.error(this, ex.getErrorCode() == 1062 ? "That username already exists." : "Database error:\n" + ex.getMessage());
        }
    }//GEN-LAST:event_btnAddStaffActionPerformed

    private void btnResetPwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetPwActionPerformed
        int id = selectedUser();
        if (id < 0) {
            return;
        }
        javax.swing.JPasswordField pw = new javax.swing.JPasswordField(18);
        int ok = javax.swing.JOptionPane.showConfirmDialog(this, new Object[]{"New password:", pw}, "Reset Password",
                javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (ok != javax.swing.JOptionPane.OK_OPTION) {
            return;
        }
        String p = new String(pw.getPassword());
        if (p.length() < 4) {
            UI.error(this, "Password must be at least 4 characters.");
            return;
        }
        try {
            DB.update("UPDATE users SET password=? WHERE user_id=?", p, id);
            UI.info(this, "Password reset.");
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }//GEN-LAST:event_btnResetPwActionPerformed

    private void btnToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToggleActionPerformed
        int id = selectedUser();
        if (id < 0) {
            return;
        }
        if (id == Session.userId) {
            UI.error(this, "You cannot deactivate your own account.");
            return;
        }
        try {
            DB.update("UPDATE users SET status=IF(status='Active','Inactive','Active') WHERE user_id=?", id);
            loadUsers();
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }//GEN-LAST:event_btnToggleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrollMain;
    private javax.swing.JPanel pnlWrap;
    private javax.swing.JPanel pnlStack;
    private javax.swing.JPanel pnlPassword;
    private javax.swing.JLabel lblPwTitle;
    private javax.swing.JPanel pnlPwWrap;
    private javax.swing.JPanel pnlPwFields;
    private javax.swing.JLabel lblCurrent;
    private javax.swing.JPasswordField pfCurrent;
    private javax.swing.JLabel lblNew;
    private javax.swing.JPasswordField pfNew;
    private javax.swing.JLabel lblConfirm;
    private javax.swing.JPasswordField pfConfirm;
    private javax.swing.JButton btnChangePw;
    private javax.swing.JPanel pnlSystem;
    private javax.swing.JLabel lblSysTitle;
    private javax.swing.JPanel pnlSysRow;
    private javax.swing.JTextField tfSystem;
    private javax.swing.JButton btnSaveSystem;
    private javax.swing.JPanel pnlUsers;
    private javax.swing.JLabel lblUsersTitle;
    private javax.swing.JScrollPane scrollUsers;
    private javax.swing.JTable tblUsers;
    private javax.swing.JPanel pnlUserBar;
    private javax.swing.JButton btnAddStaff;
    private javax.swing.JButton btnResetPw;
    private javax.swing.JButton btnToggle;
    // End of variables declaration//GEN-END:variables
}
