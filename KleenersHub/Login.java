package KleenersHub;

/**
 * Login - GUI is editable in the NetBeans Design view (see Login.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Login extends javax.swing.JFrame {

    private final char echo;

    public Login() {
        initComponents();
        echo = pfPass.getEchoChar();
        UI.styleFields(tfUser, pfPass);
        UI.handCursor(this);
        getRootPane().setDefaultButton(btnLogin);
        setSize(900, 620);
        setLocationRelativeTo(null);
    }

    private void doLogin() {
        String u = tfUser.getText().trim();
        String p = new String(pfPass.getPassword());
        if (u.isEmpty() || p.isEmpty()) {
            UI.error(this, "Please enter your username and password.");
            return;
        }
        try {
            java.util.List<Object[]> r = DB.query("SELECT user_id, username, full_name, role FROM users "
                    + "WHERE BINARY username=? AND BINARY password=? AND status='Active'", u, p);
            if (r.isEmpty()) {
                UI.error(this, "Wrong username or password, or the account is inactive.");
                pfPass.setText("");
                return;
            }
            Object[] row = r.get(0);
            Session.set(((Number) row[0]).intValue(), UI.str(row[1]), UI.str(row[2]), UI.str(row[3]));
            new MainFrame().setVisible(true);
            dispose();
        } catch (java.sql.SQLException ex) {
            UI.error(this, "Cannot connect to the database.\n"
                    + "1) Make sure MySQL is running in XAMPP.\n"
                    + "2) Make sure the MySQL Connector/J .jar is added to Libraries.\n\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBg = new javax.swing.JPanel();
        pnlCard = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblSub = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        tfUser = new javax.swing.JTextField();
        lblPass = new javax.swing.JLabel();
        pfPass = new javax.swing.JPasswordField();
        chkShow = new javax.swing.JCheckBox();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kleeners Hub - Login");
        setMinimumSize(new java.awt.Dimension(480, 520));

        pnlBg.setBackground(new java.awt.Color(22, 80, 92));
        pnlBg.setLayout(new java.awt.GridBagLayout());

        pnlCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 44, 30, 44));
        pnlCard.setLayout(new java.awt.GridBagLayout());

        lblTitle.setText("Kleeners Hub");
        lblTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlCard.add(lblTitle);

        lblSub.setText("Cleaning Services Booking System");
        lblSub.setForeground(new java.awt.Color(108, 122, 131));
        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlCard.add(lblSub);

        lblUser.setText("Username");
        lblUser.setForeground(new java.awt.Color(43, 58, 66));
        lblUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCard.add(lblUser);

        tfUser.setColumns(20);
        tfUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCard.add(tfUser);

        lblPass.setText("Password");
        lblPass.setForeground(new java.awt.Color(43, 58, 66));
        lblPass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCard.add(lblPass);

        pfPass.setColumns(20);
        pfPass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCard.add(pfPass);

        chkShow.setText("Show password");
        chkShow.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowActionPerformed(evt);
            }
        });
        pnlCard.add(chkShow);

        btnLogin.setText("LOGIN");
        btnLogin.setBackground(new java.awt.Color(31, 122, 140));
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.setOpaque(true);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        pnlCard.add(btnLogin);

        pnlBg.add(pnlCard);

        getContentPane().add(pnlBg);
    }// </editor-fold>//GEN-END:initComponents

    private void chkShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowActionPerformed
        pfPass.setEchoChar(chkShow.isSelected() ? (char) 0 : echo);
    }//GEN-LAST:event_chkShowActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        doLogin();
    }//GEN-LAST:event_btnLoginActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox chkShow;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPasswordField pfPass;
    private javax.swing.JPanel pnlBg;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JTextField tfUser;
    // End of variables declaration//GEN-END:variables
}
