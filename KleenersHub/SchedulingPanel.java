package KleenersHub;

/**
 * SchedulingPanel - GUI is editable in the NetBeans Design view (see SchedulingPanel.form).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SchedulingPanel extends javax.swing.JPanel implements Refreshable {

    /** First and last START hour. Last start is 4:00 PM so the job is finished by the 5:00 PM time-out. */
    static final int FIRST_HOUR = 8;
    static final int LAST_HOUR = 16;
    static final String SELECT = "-- Select --";
    static final String[] HEADERS = {"No.", "Client", "Service", "Cleaner", "Property", "Date", "Time", "Payment", "Total Estimated Payment", "Status"};

    /** One entry of the Client / Service / Cleaner drop-downs. */
    static class Item {

        final int id;
        final String label;
        final boolean ok;
        String contact = "", address = "";
        java.math.BigDecimal price;

        Item(int id, String label, boolean ok) {
            this.id = id;
            this.label = label;
            this.ok = ok;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private final DatePicker dp = new DatePicker();
    private final java.util.List<Integer> ids = new java.util.ArrayList<>();
    private final javax.swing.table.DefaultTableModel model;
    private boolean adjusting = false;

    public SchedulingPanel() {
        initComponents();
        model = (javax.swing.table.DefaultTableModel) tblBookings.getModel();
        UI.styleCard(pnlForm);
        UI.styleCard(pnlTable);
        UI.styleTable(tblBookings);
        UI.styleFields(tfArea, tfTransport, tfAdditional);
        UI.handCursor(this);
        pnlDate.add(dp, java.awt.BorderLayout.CENTER); // calendar picker (past dates are disabled)

        // The designer only holds a text placeholder; start with real Item objects so the casts below are always safe.
        cbClient.setModel(new javax.swing.DefaultComboBoxModel<Item>(new Item[]{new Item(-1, SELECT, true)}));
        cbService.setModel(new javax.swing.DefaultComboBoxModel<Item>(new Item[]{new Item(-1, SELECT, true)}));
        cbCleaner.setModel(new javax.swing.DefaultComboBoxModel<Item>(new Item[]{new Item(-1, SELECT, true)}));

        UI.decimalOnly(tfArea);
        UI.decimalOnly(tfTransport);
        UI.decimalOnly(tfAdditional);

        javax.swing.ListCellRenderer<Object> rend = new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean sel, boolean focus) {
                java.awt.Component c = super.getListCellRendererComponent(list, value, index, sel, focus);
                if (value instanceof Item && !((Item) value).ok && !sel) {
                    c.setForeground(UI.DANGER); // Not Available = red
                }
                return c;
            }
        };
        cbClient.setRenderer(rend);
        cbService.setRenderer(rend);
        cbCleaner.setRenderer(rend);

        cbClient.addActionListener(e -> {
            if (!adjusting) {
                showClientInfo();
            }
        });
        cbService.addActionListener(e -> {
            if (!adjusting) {
                updateTotal();
            }
        });
        cbTime.addActionListener(e -> {
            if (!adjusting) {
                reloadCleaners();
            }
        });
        dp.addChangeListener(() -> {
            reloadTimes();
            reloadCleaners();
        });
        javax.swing.event.DocumentListener dl = new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTotal();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTotal();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTotal();
            }
        };
        tfArea.getDocument().addDocumentListener(dl);
        tfTransport.getDocument().addDocumentListener(dl);
        tfAdditional.getDocument().addDocumentListener(dl);
    }

    // ---------------- reload of the drop-downs ----------------
    @Override
    public void refresh() {
        if (dp.getDate().isBefore(java.time.LocalDate.now())) {
            dp.setDate(java.time.LocalDate.now());
        }
        reloadClients();
        reloadServices();
        reloadTimes();
        reloadCleaners();
        loadTable();
        updateTotal();
    }

    private int selId(javax.swing.JComboBox cb) {
        Object o = cb.getSelectedItem();
        return o instanceof Item ? ((Item) o).id : -1;
    }

    private void select(javax.swing.JComboBox cb, int id) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            Object it = cb.getItemAt(i);
            if (it instanceof Item && ((Item) it).id == id) {
                cb.setSelectedIndex(i);
                return;
            }
        }
        cb.setSelectedIndex(0);
    }

    private void reloadClients() {
        adjusting = true;
        try {
            int keep = selId(cbClient);
            javax.swing.DefaultComboBoxModel<Item> m = new javax.swing.DefaultComboBoxModel<>();
            m.addElement(new Item(-1, SELECT, true));
            for (Object[] r : DB.query("SELECT client_id, client_name, contact_number, address FROM clients WHERE is_archived=0 ORDER BY client_name")) {
                Item it = new Item(((Number) r[0]).intValue(), UI.str(r[1]), true);
                it.contact = UI.str(r[2]);
                it.address = UI.str(r[3]);
                m.addElement(it);
            }
            cbClient.setModel(m);
            select(cbClient, keep);
            showClientInfo();
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        } finally {
            adjusting = false;
        }
    }

    /** When a client is chosen, the contact number and address are displayed. */
    private void showClientInfo() {
        Object o = cbClient.getSelectedItem();
        Item c = o instanceof Item ? (Item) o : null;
        boolean has = c != null && c.id > 0;
        lblContactValue.setText(has ? c.contact : "-");
        lblAddressValue.setText(has ? c.address : "-");
    }

    private void reloadServices() {
        adjusting = true;
        try {
            int keep = selId(cbService);
            javax.swing.DefaultComboBoxModel<Item> m = new javax.swing.DefaultComboBoxModel<>();
            m.addElement(new Item(-1, SELECT, true));
            for (Object[] r : DB.query("SELECT service_id, service_name, price, availability FROM services WHERE is_archived=0 ORDER BY service_name")) {
                boolean ok = "Available".equals(r[3]);
                Item it = new Item(((Number) r[0]).intValue(),
                        r[1] + " (" + UI.peso(r[2]) + "/sq m) - " + (ok ? "Available" : "Not Available"), ok);
                it.price = new java.math.BigDecimal(r[2].toString());
                m.addElement(it);
            }
            cbService.setModel(m);
            select(cbService, keep);
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        } finally {
            adjusting = false;
        }
    }

    private void reloadTimes() {
        adjusting = true;
        try {
            Object keep = cbTime.getSelectedItem();
            cbTime.removeAllItems();
            java.time.LocalDate d = dp.getDate();
            java.time.LocalTime now = java.time.LocalTime.now();
            for (int h = FIRST_HOUR; h <= LAST_HOUR; h++) {
                java.time.LocalTime t = java.time.LocalTime.of(h, 0);
                if (d.equals(java.time.LocalDate.now()) && !t.isAfter(now)) {
                    continue; // this hour has already passed today
                }
                cbTime.addItem(t.format(UI.TIME_FMT));
            }
            if (keep != null) {
                cbTime.setSelectedItem(keep);
            }
        } finally {
            adjusting = false;
        }
    }

    private java.time.LocalTime selectedTime() {
        Object o = cbTime.getSelectedItem();
        return o == null ? null : java.time.LocalTime.parse(o.toString(), UI.TIME_FMT);
    }

    /** Cleaners that already have a booking at the chosen date + time are shown as Not Available. */
    private void reloadCleaners() {
        adjusting = true;
        try {
            int keep = selId(cbCleaner);
            java.util.Set<Integer> busy = new java.util.HashSet<>();
            java.time.LocalTime t = selectedTime();
            if (t != null) {
                for (Object[] r : DB.query("SELECT DISTINCT cleaner_id FROM bookings WHERE booking_date=? AND booking_time=? "
                        + "AND is_archived=0 AND status<>'Cancelled' AND cleaner_id IS NOT NULL",
                        java.sql.Date.valueOf(dp.getDate()), java.sql.Time.valueOf(t))) {
                    busy.add(((Number) r[0]).intValue());
                }
            }
            javax.swing.DefaultComboBoxModel<Item> m = new javax.swing.DefaultComboBoxModel<>();
            m.addElement(new Item(-1, SELECT, true));
            for (Object[] r : DB.query("SELECT cleaner_id, cleaner_name, position FROM cleaners WHERE is_archived=0 ORDER BY cleaner_name")) {
                int id = ((Number) r[0]).intValue();
                boolean ok = !busy.contains(id);
                m.addElement(new Item(id, r[1] + " (" + r[2] + ") - " + (ok ? "Available" : "Not Available"), ok));
            }
            cbCleaner.setModel(m);
            select(cbCleaner, keep);
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        } finally {
            adjusting = false;
        }
    }

    // ---------------- computation ----------------
    private java.math.BigDecimal dec(javax.swing.JTextField f) {
        String s = f.getText().trim();
        if (s.isEmpty() || s.equals(".")) {
            return java.math.BigDecimal.ZERO;
        }
        try {
            return new java.math.BigDecimal(s);
        } catch (NumberFormatException ex) {
            return java.math.BigDecimal.ZERO;
        }
    }

    private java.math.BigDecimal base() {
        Object o = cbService.getSelectedItem();
        Item s = o instanceof Item ? (Item) o : null;
        if (s == null || s.price == null) {
            return java.math.BigDecimal.ZERO;
        }
        return s.price.multiply(dec(tfArea)).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    /** Total Estimated Payment = (service price x area) + transportation + additional. */
    private java.math.BigDecimal total() {
        return base().add(dec(tfTransport)).add(dec(tfAdditional)).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private void updateTotal() {
        lblTotal.setText(UI.peso(total()));
    }

    // ---------------- Add booking ----------------
    private void addBooking() {
        Object oClient = cbClient.getSelectedItem();
        Item client = oClient instanceof Item ? (Item) oClient : null;
        if (client == null || client.id < 0) {
            UI.error(this, "Please select a client.");
            return;
        }
        if (cbProperty.getSelectedIndex() <= 0) {
            UI.error(this, "Please select the property type.");
            return;
        }
        Object oService = cbService.getSelectedItem();
        Item service = oService instanceof Item ? (Item) oService : null;
        if (service == null || service.id < 0) {
            UI.error(this, "Please select a service.");
            return;
        }
        if (!service.ok) {
            UI.error(this, "This service is not available right now. Please choose another service.");
            return;
        }
        java.time.LocalDate date = dp.getDate();
        if (date.isBefore(java.time.LocalDate.now())) {
            UI.error(this, "You cannot book a past date.");
            return;
        }
        java.time.LocalTime time = selectedTime();
        if (time == null) {
            UI.error(this, "No time slot is left for this date. Please choose another date.");
            return;
        }
        if (time.isAfter(java.time.LocalTime.of(17, 0))) {
            UI.error(this, "Bookings cannot go beyond 5:00 PM.");
            return;
        }
        if (date.equals(java.time.LocalDate.now()) && !time.isAfter(java.time.LocalTime.now())) {
            UI.error(this, "That time has already passed. Please choose a later time.");
            return;
        }
        Object oCleaner = cbCleaner.getSelectedItem();
        Item cleaner = oCleaner instanceof Item ? (Item) oCleaner : null;
        if (cleaner == null || cleaner.id < 0) {
            UI.error(this, "Please select a cleaner.");
            return;
        }
        if (!cleaner.ok) {
            UI.error(this, "This cleaner is not available at the selected date and time.");
            return;
        }
        java.math.BigDecimal area = dec(tfArea);
        if (area.signum() <= 0) {
            UI.error(this, "Please enter the area size (sq m).");
            tfArea.requestFocus();
            return;
        }
        if (cbPayment.getSelectedIndex() <= 0) {
            UI.error(this, "Please select a payment method.");
            return;
        }
        try {
            long clash = DB.count("SELECT COUNT(*) FROM bookings WHERE cleaner_id=? AND booking_date=? AND booking_time=? "
                    + "AND is_archived=0 AND status<>'Cancelled'", cleaner.id, java.sql.Date.valueOf(date), java.sql.Time.valueOf(time));
            if (clash > 0) {
                UI.error(this, "This cleaner was just booked for that time. Please pick another.");
                reloadCleaners();
                return;
            }
            int id = DB.insert("INSERT INTO bookings (client_id, service_id, cleaner_id, area_size, base_service_fee, labor_cost, "
                    + "transportation_fee, materials_fee, additional_fee, total_amount, booking_date, booking_time, status, "
                    + "property_type, payment_method) VALUES (?,?,?,?,?,0,?,0,?,?,?,?,'Pending',?,?)",
                    client.id, service.id, cleaner.id, area, base(), dec(tfTransport), dec(tfAdditional), total(),
                    java.sql.Date.valueOf(date), java.sql.Time.valueOf(time), cbProperty.getSelectedItem(), cbPayment.getSelectedItem());
            resetForm();
            loadTable();
            BookingUtil.showSummary(UI.owner(this), id); // summary window, with Receipt button
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }

    /** Empties the form after a successful booking (automatic - there is no Clear button). */
    private void resetForm() {
        adjusting = true;
        cbClient.setSelectedIndex(0);
        cbProperty.setSelectedIndex(0);
        cbService.setSelectedIndex(0);
        cbPayment.setSelectedIndex(0);
        tfArea.setText("");
        tfTransport.setText("0");
        tfAdditional.setText("0");
        dp.setDate(java.time.LocalDate.now());
        adjusting = false;
        showClientInfo();
        reloadTimes();
        reloadCleaners();
        updateTotal();
    }

    // ---------------- table ----------------
    private static final String LIST_SQL = "SELECT b.booking_id, c.client_name, s.service_name, COALESCE(cl.cleaner_name,'-'), "
            + "b.property_type, b.booking_date, b.booking_time, b.payment_method, b.total_amount, b.status "
            + "FROM bookings b JOIN clients c ON c.client_id=b.client_id JOIN services s ON s.service_id=b.service_id "
            + "LEFT JOIN cleaners cl ON cl.cleaner_id=b.cleaner_id WHERE b.is_archived=? "
            + "ORDER BY b.booking_date DESC, b.booking_time DESC, b.booking_id DESC";

    private void fillTable(javax.swing.table.DefaultTableModel m, java.util.List<Integer> idList, int archived, java.awt.Component parent) {
        try {
            java.util.List<Object[]> rows = DB.query(LIST_SQL, archived);
            m.setRowCount(0);
            idList.clear();
            int n = 1;
            for (Object[] r : rows) {
                idList.add(((Number) r[0]).intValue());
                m.addRow(new Object[]{n++, r[1], r[2], r[3], r[4], UI.dateStr(r[5]), UI.timeStr(r[6]), r[7], UI.peso(r[8]), r[9]});
            }
        } catch (java.sql.SQLException ex) {
            UI.dbError(parent, ex);
        }
    }

    private void loadTable() {
        fillTable(model, ids, 0, this);
    }

    private int selectedId() {
        int r = tblBookings.getSelectedRow();
        if (r < 0) {
            UI.info(this, "Please select a booking from the table first.");
            return -1;
        }
        return ids.get(r);
    }

    private void openArchive() {
        final ArchiveDialog dlg = new ArchiveDialog(UI.owner(this), "Archived Bookings", HEADERS);
        final java.util.List<Integer> aid = new java.util.ArrayList<>();
        fillTable(dlg.getModel(), aid, 1, dlg);
        dlg.onView = r -> BookingUtil.showDetails(dlg, aid.get(r), null);
        dlg.onRestore = r -> {
            if (!UI.confirm(dlg, "Restore this booking?")) {
                return;
            }
            try {
                DB.update("UPDATE bookings SET is_archived=0 WHERE booking_id=?", aid.get(r));
                fillTable(dlg.getModel(), aid, 1, dlg);
                loadTable();
                reloadCleaners();
            } catch (java.sql.SQLException ex) {
                UI.dbError(dlg, ex);
            }
        };
        dlg.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblTitle = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();
        pnlForm = new javax.swing.JPanel();
        lblClient = new javax.swing.JLabel();
        cbClient = new javax.swing.JComboBox();
        lblProperty = new javax.swing.JLabel();
        cbProperty = new javax.swing.JComboBox<>();
        lblContact = new javax.swing.JLabel();
        lblContactValue = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblAddressValue = new javax.swing.JLabel();
        lblService = new javax.swing.JLabel();
        cbService = new javax.swing.JComboBox();
        lblCleaner = new javax.swing.JLabel();
        cbCleaner = new javax.swing.JComboBox();
        lblDate = new javax.swing.JLabel();
        pnlDate = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        cbTime = new javax.swing.JComboBox<>();
        lblArea = new javax.swing.JLabel();
        tfArea = new javax.swing.JTextField();
        lblTransport = new javax.swing.JLabel();
        tfTransport = new javax.swing.JTextField();
        lblAdditional = new javax.swing.JLabel();
        tfAdditional = new javax.swing.JTextField();
        lblPayment = new javax.swing.JLabel();
        cbPayment = new javax.swing.JComboBox<>();
        lblTotalText = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnAddBooking = new javax.swing.JButton();
        pnlTable = new javax.swing.JPanel();
        pnlTableBar = new javax.swing.JPanel();
        lblBookings = new javax.swing.JLabel();
        btnView = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnArchiveList = new javax.swing.JButton();
        scrollTable = new javax.swing.JScrollPane();
        tblBookings = new javax.swing.JTable();

        setBackground(new java.awt.Color(243, 246, 248));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setLayout(new java.awt.BorderLayout(0, 10));
        lblTitle.setText("Schedule Booking");
        lblTitle.setForeground(new java.awt.Color(22, 80, 92));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 26));
        add(lblTitle, java.awt.BorderLayout.PAGE_START);
        pnlMain.setOpaque(false);
        pnlMain.setLayout(new java.awt.BorderLayout(0, 10));
        pnlForm.setBackground(new java.awt.Color(255, 255, 255));
        pnlForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14));
        pnlForm.setLayout(new java.awt.GridBagLayout());
        lblClient.setText("Client");
        lblClient.setForeground(new java.awt.Color(43, 58, 66));
        lblClient.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblClient, gridBagConstraints);
        cbClient.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cbClient.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Select --" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(cbClient, gridBagConstraints);
        lblProperty.setText("Property Type");
        lblProperty.setForeground(new java.awt.Color(43, 58, 66));
        lblProperty.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblProperty, gridBagConstraints);
        cbProperty.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cbProperty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select --", "House", "Apartment", "Condominium", "Office", "Commercial / Store", "Warehouse", "Other" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(cbProperty, gridBagConstraints);
        lblContact.setText("Contact Number");
        lblContact.setForeground(new java.awt.Color(43, 58, 66));
        lblContact.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblContact, gridBagConstraints);
        lblContactValue.setText("-");
        lblContactValue.setForeground(new java.awt.Color(43, 58, 66));
        lblContactValue.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(lblContactValue, gridBagConstraints);
        lblAddress.setText("Address");
        lblAddress.setForeground(new java.awt.Color(43, 58, 66));
        lblAddress.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblAddress, gridBagConstraints);
        lblAddressValue.setText("-");
        lblAddressValue.setForeground(new java.awt.Color(43, 58, 66));
        lblAddressValue.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(lblAddressValue, gridBagConstraints);
        lblService.setText("Service");
        lblService.setForeground(new java.awt.Color(43, 58, 66));
        lblService.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblService, gridBagConstraints);
        cbService.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cbService.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Select --" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(cbService, gridBagConstraints);
        lblCleaner.setText("Cleaner");
        lblCleaner.setForeground(new java.awt.Color(43, 58, 66));
        lblCleaner.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblCleaner, gridBagConstraints);
        cbCleaner.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cbCleaner.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Select --" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(cbCleaner, gridBagConstraints);
        lblDate.setText("Date");
        lblDate.setForeground(new java.awt.Color(43, 58, 66));
        lblDate.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblDate, gridBagConstraints);
        pnlDate.setOpaque(false);
        pnlDate.setLayout(new java.awt.BorderLayout(6, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(pnlDate, gridBagConstraints);
        lblTime.setText("Time");
        lblTime.setForeground(new java.awt.Color(43, 58, 66));
        lblTime.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblTime, gridBagConstraints);
        cbTime.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cbTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8:00 AM" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(cbTime, gridBagConstraints);
        lblArea.setText("Area Size (sq m)");
        lblArea.setForeground(new java.awt.Color(43, 58, 66));
        lblArea.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblArea, gridBagConstraints);
        tfArea.setColumns(10);
        tfArea.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(tfArea, gridBagConstraints);
        lblTransport.setText("Transportation Fee");
        lblTransport.setForeground(new java.awt.Color(43, 58, 66));
        lblTransport.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblTransport, gridBagConstraints);
        tfTransport.setColumns(10);
        tfTransport.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tfTransport.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(tfTransport, gridBagConstraints);
        lblAdditional.setText("Additional Fee");
        lblAdditional.setForeground(new java.awt.Color(43, 58, 66));
        lblAdditional.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblAdditional, gridBagConstraints);
        tfAdditional.setColumns(10);
        tfAdditional.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tfAdditional.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(tfAdditional, gridBagConstraints);
        lblPayment.setText("Payment Method");
        lblPayment.setForeground(new java.awt.Color(43, 58, 66));
        lblPayment.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblPayment, gridBagConstraints);
        cbPayment.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cbPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select --", "Cash", "GCash", "Maya", "Bank Payment", "Credit / Debit Card", "Other" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(cbPayment, gridBagConstraints);
        lblTotalText.setText("Total Estimated Payment");
        lblTotalText.setForeground(new java.awt.Color(43, 58, 66));
        lblTotalText.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 8);
        pnlForm.add(lblTotalText, gridBagConstraints);
        lblTotal.setText("PHP 0.00");
        lblTotal.setForeground(new java.awt.Color(46, 139, 87));
        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 5, 14);
        pnlForm.add(lblTotal, gridBagConstraints);
        btnAddBooking.setText("Add Booking");
        btnAddBooking.setBackground(new java.awt.Color(46, 139, 87));
        btnAddBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnAddBooking.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnAddBooking.setOpaque(true);
        btnAddBooking.setFocusPainted(false);
        btnAddBooking.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnAddBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBookingActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(8, 4, 5, 14);
        pnlForm.add(btnAddBooking, gridBagConstraints);
        pnlMain.add(pnlForm, java.awt.BorderLayout.PAGE_START);
        pnlTable.setBackground(new java.awt.Color(255, 255, 255));
        pnlTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14));
        pnlTable.setLayout(new java.awt.BorderLayout(0, 8));
        pnlTableBar.setOpaque(false);
        pnlTableBar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        lblBookings.setText("Bookings");
        lblBookings.setForeground(new java.awt.Color(22, 80, 92));
        lblBookings.setFont(new java.awt.Font("Segoe UI", 1, 16));
        pnlTableBar.add(lblBookings);
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
        pnlTableBar.add(btnView);
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
        pnlTableBar.add(btnArchive);
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
        pnlTableBar.add(btnArchiveList);
        pnlTable.add(pnlTableBar, java.awt.BorderLayout.PAGE_START);
        tblBookings.setRowHeight(30);
        tblBookings.setFont(new java.awt.Font("Segoe UI", 0, 14));
        tblBookings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Client", "Service", "Cleaner", "Property", "Date", "Time", "Payment", "Total Estimated Payment", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBookings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBookingsMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(tblBookings);
        pnlTable.add(scrollTable, java.awt.BorderLayout.CENTER);
        pnlMain.add(pnlTable, java.awt.BorderLayout.CENTER);
        add(pnlMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBookingActionPerformed
        addBooking();
    }//GEN-LAST:event_btnAddBookingActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        int id = selectedId();
        if (id > 0) {
            BookingUtil.showDetails(UI.owner(this), id, this::loadTable);
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        int id = selectedId();
        if (id < 0) {
            return;
        }
        if (!UI.confirm(this, "Archive this booking?\nYou can restore it anytime from the Archived List.")) {
            return;
        }
        try {
            DB.update("UPDATE bookings SET is_archived=1 WHERE booking_id=?", id);
            loadTable();
            reloadCleaners();
        } catch (java.sql.SQLException ex) {
            UI.dbError(this, ex);
        }
    }//GEN-LAST:event_btnArchiveActionPerformed

    private void btnArchiveListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveListActionPerformed
        openArchive();
    }//GEN-LAST:event_btnArchiveListActionPerformed

    private void tblBookingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBookingsMouseClicked
        if (evt.getClickCount() == 2) {
            int id = selectedId();
            if (id > 0) {
                BookingUtil.showDetails(UI.owner(this), id, this::loadTable);
            }
        }
    }//GEN-LAST:event_tblBookingsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JLabel lblClient;
    private javax.swing.JComboBox cbClient;
    private javax.swing.JLabel lblProperty;
    private javax.swing.JComboBox<String> cbProperty;
    private javax.swing.JLabel lblContact;
    private javax.swing.JLabel lblContactValue;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblAddressValue;
    private javax.swing.JLabel lblService;
    private javax.swing.JComboBox cbService;
    private javax.swing.JLabel lblCleaner;
    private javax.swing.JComboBox cbCleaner;
    private javax.swing.JLabel lblDate;
    private javax.swing.JPanel pnlDate;
    private javax.swing.JLabel lblTime;
    private javax.swing.JComboBox<String> cbTime;
    private javax.swing.JLabel lblArea;
    private javax.swing.JTextField tfArea;
    private javax.swing.JLabel lblTransport;
    private javax.swing.JTextField tfTransport;
    private javax.swing.JLabel lblAdditional;
    private javax.swing.JTextField tfAdditional;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JComboBox<String> cbPayment;
    private javax.swing.JLabel lblTotalText;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JButton btnAddBooking;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JPanel pnlTableBar;
    private javax.swing.JLabel lblBookings;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnArchiveList;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable tblBookings;
    // End of variables declaration//GEN-END:variables
}
