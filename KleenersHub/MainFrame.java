package KleenersHub;

/**
 * MainFrame - GUI is editable in the NetBeans Design view (see MainFrame.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MainFrame extends javax.swing.JFrame {

    private java.awt.CardLayout cards;
    private final java.util.Map<String, Refreshable> screens = new java.util.HashMap<>();
    private final java.util.Map<String, javax.swing.JButton> navButtons = new java.util.LinkedHashMap<>();

    public MainFrame() {
        initComponents();
        cards = (java.awt.CardLayout) pnlContent.getLayout();
        UI.handCursor(this);

        navButtons.put("Dashboard", btnDashboard);
        navButtons.put("Clients", btnClients);
        navButtons.put("Cleaners", btnCleaners);
        navButtons.put("Services", btnServices);
        navButtons.put("Schedule Booking", btnBooking);
        navButtons.put("Booking Records", btnRecords);
        navButtons.put("Settings", btnSettings);

        lblUser.setText(Session.fullName + " (" + Session.role + ")");
        setSystemName(loadSystemName());

        register("Dashboard", new DashboardPanel());
        register("Clients", new ClientPanel());
        register("Services", new ServicePanel());
        register("Schedule Booking", new SchedulingPanel());
        register("Settings", new SettingPanel(this));
        if (Session.isAdmin()) {
            register("Cleaners", new CleanerPanel());
            register("Booking Records", new RecordsPanel());
        } else {
            // STAFF: no Cleaners and no Booking Records - the buttons are removed and the screens are never created.
            pnlNav.remove(btnCleaners);
            pnlNav.remove(btnRecords);
            navButtons.remove("Cleaners");
            navButtons.remove("Booking Records");
        }

        setSize(1280, 760);
        setLocationRelativeTo(null);
        showScreen("Dashboard");
    }

    private <T extends javax.swing.JPanel & Refreshable> void register(String name, T panel) {
        screens.put(name, panel);
        pnlContent.add(panel, name);
    }

    private void showScreen(String name) {
        Refreshable r = screens.get(name);
        if (r == null) {
            return; // not allowed for this user
        }
        r.refresh();
        cards.show(pnlContent, name);
        for (java.util.Map.Entry<String, javax.swing.JButton> en : navButtons.entrySet()) {
            en.getValue().setBackground(en.getKey().equals(name) ? UI.PRIMARY : UI.PRIMARY_DARK);
        }
    }

    private String loadSystemName() {
        try {
            Object o = DB.scalar("SELECT system_name FROM settings WHERE id=1");
            if (o != null && !o.toString().trim().isEmpty()) {
                return o.toString();
            }
        } catch (Exception ex) {
            // use the default name
        }
        return "Kleeners Hub";
    }

    public final void setSystemName(String name) {
        lblSystem.setText(name);
        setTitle(name);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSide = new javax.swing.JPanel();
        pnlTop = new javax.swing.JPanel();
        pnlTitle = new javax.swing.JPanel();
        lblSystem = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        pnlNav = new javax.swing.JPanel();
        btnDashboard = new javax.swing.JButton();
        btnClients = new javax.swing.JButton();
        btnCleaners = new javax.swing.JButton();
        btnServices = new javax.swing.JButton();
        btnBooking = new javax.swing.JButton();
        btnRecords = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        pnlContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kleeners Hub");
        setMinimumSize(new java.awt.Dimension(1000, 650));
        getContentPane().setLayout(new java.awt.BorderLayout());
        pnlSide.setBackground(new java.awt.Color(22, 80, 92));
        pnlSide.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 12, 16, 12));
        pnlSide.setPreferredSize(new java.awt.Dimension(240, 100));
        pnlSide.setLayout(new java.awt.BorderLayout());
        pnlTop.setOpaque(false);
        pnlTop.setLayout(new java.awt.BorderLayout());
        pnlTitle.setOpaque(false);
        pnlTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 18, 0));
        pnlTitle.setLayout(new java.awt.GridLayout(0, 1, 0, 2));
        lblSystem.setText("Kleeners Hub");
        lblSystem.setForeground(new java.awt.Color(255, 255, 255));
        lblSystem.setFont(new java.awt.Font("Segoe UI", 1, 22));
        pnlTitle.add(lblSystem);
        lblUser.setText("User (Role)");
        lblUser.setForeground(new java.awt.Color(191, 214, 220));
        lblUser.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlTitle.add(lblUser);
        pnlTop.add(pnlTitle, java.awt.BorderLayout.PAGE_START);
        pnlNav.setOpaque(false);
        pnlNav.setLayout(new java.awt.GridLayout(0, 1, 0, 4));
        btnDashboard.setText("Dashboard");
        btnDashboard.setBackground(new java.awt.Color(22, 80, 92));
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnDashboard.setOpaque(true);
        btnDashboard.setFocusPainted(false);
        btnDashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 16, 11, 16));
        btnDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });
        pnlNav.add(btnDashboard);
        btnClients.setText("Clients");
        btnClients.setBackground(new java.awt.Color(22, 80, 92));
        btnClients.setForeground(new java.awt.Color(255, 255, 255));
        btnClients.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnClients.setOpaque(true);
        btnClients.setFocusPainted(false);
        btnClients.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 16, 11, 16));
        btnClients.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientsActionPerformed(evt);
            }
        });
        pnlNav.add(btnClients);
        btnCleaners.setText("Cleaners");
        btnCleaners.setBackground(new java.awt.Color(22, 80, 92));
        btnCleaners.setForeground(new java.awt.Color(255, 255, 255));
        btnCleaners.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnCleaners.setOpaque(true);
        btnCleaners.setFocusPainted(false);
        btnCleaners.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 16, 11, 16));
        btnCleaners.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCleaners.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanersActionPerformed(evt);
            }
        });
        pnlNav.add(btnCleaners);
        btnServices.setText("Services");
        btnServices.setBackground(new java.awt.Color(22, 80, 92));
        btnServices.setForeground(new java.awt.Color(255, 255, 255));
        btnServices.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnServices.setOpaque(true);
        btnServices.setFocusPainted(false);
        btnServices.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 16, 11, 16));
        btnServices.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnServices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServicesActionPerformed(evt);
            }
        });
        pnlNav.add(btnServices);
        btnBooking.setText("Schedule Booking");
        btnBooking.setBackground(new java.awt.Color(22, 80, 92));
        btnBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnBooking.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnBooking.setOpaque(true);
        btnBooking.setFocusPainted(false);
        btnBooking.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 16, 11, 16));
        btnBooking.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookingActionPerformed(evt);
            }
        });
        pnlNav.add(btnBooking);
        btnRecords.setText("Booking Records");
        btnRecords.setBackground(new java.awt.Color(22, 80, 92));
        btnRecords.setForeground(new java.awt.Color(255, 255, 255));
        btnRecords.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnRecords.setOpaque(true);
        btnRecords.setFocusPainted(false);
        btnRecords.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 16, 11, 16));
        btnRecords.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordsActionPerformed(evt);
            }
        });
        pnlNav.add(btnRecords);
        btnSettings.setText("Settings");
        btnSettings.setBackground(new java.awt.Color(22, 80, 92));
        btnSettings.setForeground(new java.awt.Color(255, 255, 255));
        btnSettings.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnSettings.setOpaque(true);
        btnSettings.setFocusPainted(false);
        btnSettings.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 16, 11, 16));
        btnSettings.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });
        pnlNav.add(btnSettings);
        pnlTop.add(pnlNav, java.awt.BorderLayout.CENTER);
        pnlSide.add(pnlTop, java.awt.BorderLayout.PAGE_START);
        btnLogout.setText("Logout");
        btnLogout.setBackground(new java.awt.Color(192, 57, 43));
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnLogout.setOpaque(true);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 16, 11, 16));
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        pnlSide.add(btnLogout, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(pnlSide, java.awt.BorderLayout.LINE_START);
        pnlContent.setBackground(new java.awt.Color(243, 246, 248));
        pnlContent.setLayout(new java.awt.CardLayout());
        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        showScreen("Dashboard");
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientsActionPerformed
        showScreen("Clients");
    }//GEN-LAST:event_btnClientsActionPerformed

    private void btnCleanersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanersActionPerformed
        showScreen("Cleaners");
    }//GEN-LAST:event_btnCleanersActionPerformed

    private void btnServicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServicesActionPerformed
        showScreen("Services");
    }//GEN-LAST:event_btnServicesActionPerformed

    private void btnBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookingActionPerformed
        showScreen("Schedule Booking");
    }//GEN-LAST:event_btnBookingActionPerformed

    private void btnRecordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecordsActionPerformed
        showScreen("Booking Records");
    }//GEN-LAST:event_btnRecordsActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        showScreen("Settings");
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        if (UI.confirm(this, "Are you sure you want to log out?")) {
            Session.clear();
            dispose();
            new Login().setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlSide;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JLabel lblSystem;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel pnlNav;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnClients;
    private javax.swing.JButton btnCleaners;
    private javax.swing.JButton btnServices;
    private javax.swing.JButton btnBooking;
    private javax.swing.JButton btnRecords;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnLogout;
    private javax.swing.JPanel pnlContent;
    // End of variables declaration//GEN-END:variables
}
