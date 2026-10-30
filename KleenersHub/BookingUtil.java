package KleenersHub;

import java.awt.Window;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Loads one booking, builds the detail / receipt text and opens the popups. */
public class BookingUtil {

    public static Map<String, String> load(int id) throws SQLException {
        List<Object[]> r = DB.query("SELECT b.booking_id, c.client_name, c.contact_number, c.address, b.property_type, "
                + "s.service_name, s.price, COALESCE(cl.cleaner_name,'-'), b.booking_date, b.booking_time, b.area_size, "
                + "b.base_service_fee, b.transportation_fee, b.additional_fee, b.total_amount, b.payment_method, b.status "
                + "FROM bookings b JOIN clients c ON c.client_id=b.client_id JOIN services s ON s.service_id=b.service_id "
                + "LEFT JOIN cleaners cl ON cl.cleaner_id=b.cleaner_id WHERE b.booking_id=?", id);
        if (r.isEmpty()) {
            return null;
        }
        Object[] o = r.get(0);
        Map<String, String> m = new LinkedHashMap<>();
        m.put("id", String.valueOf(o[0]));
        m.put("client", UI.str(o[1]));
        m.put("contact", UI.str(o[2]));
        m.put("address", UI.str(o[3]));
        m.put("property", UI.str(o[4]));
        m.put("service", UI.str(o[5]));
        m.put("rate", UI.peso(o[6]) + " per sq m");
        m.put("cleaner", UI.str(o[7]));
        m.put("date", UI.dateStr(o[8]));
        m.put("time", UI.timeStr(o[9]));
        m.put("area", new BigDecimal(o[10].toString()).stripTrailingZeros().toPlainString() + " sq m");
        m.put("base", UI.peso(o[11]));
        m.put("transport", UI.peso(o[12]));
        m.put("additional", UI.peso(o[13]));
        m.put("total", UI.peso(o[14]));
        m.put("payment", UI.str(o[15]));
        m.put("status", UI.str(o[16]));
        return m;
    }

    private static final String[][] DETAIL_ROWS = {
        {"Client", "client"}, {"Contact Number", "contact"}, {"Address", "address"}, {"Property Type", "property"},
        {"Service", "service"}, {"Rate", "rate"}, {"Cleaner", "cleaner"}, {"Date", "date"}, {"Time", "time"},
        {"Area Size", "area"}, {"Service Fee", "base"}, {"Transportation Fee", "transport"},
        {"Additional Fee", "additional"}, {"Total Est. Payment", "total"}, {"Payment Method", "payment"}, {"Status", "status"}
    };

    public static String detailText(Map<String, String> b) {
        StringBuilder sb = new StringBuilder();
        for (String[] d : DETAIL_ROWS) {
            sb.append(String.format("%-20s: %s%n", d[0], b.get(d[1])));
        }
        return sb.toString();
    }

    public static void showSummary(Window owner, int id) {
        try {
            Map<String, String> b = load(id);
            if (b != null) {
                new BookingDialog(owner, b, true, null).setVisible(true);
            }
        } catch (SQLException ex) {
            UI.dbError(owner, ex);
        }
    }

    public static void showDetails(Window owner, int id, Runnable onChanged) {
        try {
            Map<String, String> b = load(id);
            if (b != null) {
                new BookingDialog(owner, b, false, onChanged).setVisible(true);
            }
        } catch (SQLException ex) {
            UI.dbError(owner, ex);
        }
    }

    // ---------- Receipt text ----------
    private static String line(String l, String v) {
        return String.format("%-14s: %s%n", l, v);
    }

    private static String money(String l, String v) {
        return String.format("%-20s%22s%n", l, v);
    }

    public static String receiptText(Map<String, String> b) {
        String bar = "------------------------------------------\n";
        StringBuilder s = new StringBuilder();
        s.append("            KLEENERS HUB\n");
        s.append("      Cleaning Services Receipt\n");
        s.append(bar);
        s.append(line("Receipt No.", String.format("KH-%06d", Integer.parseInt(b.get("id")))));
        s.append(line("Date Issued", LocalDate.now().format(UI.DATE_FMT)));
        s.append(bar);
        s.append(line("Client", b.get("client")));
        s.append(line("Contact", b.get("contact")));
        s.append(line("Address", b.get("address")));
        s.append(line("Property", b.get("property")));
        s.append(bar);
        s.append(line("Service", b.get("service")));
        s.append(line("Rate", b.get("rate")));
        s.append(line("Cleaner", b.get("cleaner")));
        s.append(line("Schedule", b.get("date") + " " + b.get("time")));
        s.append(line("Area", b.get("area")));
        s.append(bar);
        s.append(money("Service fee", b.get("base")));
        s.append(money("Transportation", b.get("transport")));
        s.append(money("Additional", b.get("additional")));
        s.append(bar);
        s.append(money("TOTAL", b.get("total")));
        s.append(bar);
        s.append(line("Payment Method", b.get("payment")));
        s.append(line("Status", b.get("status")));
        s.append(bar);
        s.append("   Thank you for choosing Kleeners Hub!\n");
        return s.toString();
    }
}
