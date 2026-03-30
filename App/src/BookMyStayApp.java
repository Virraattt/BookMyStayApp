import java.util.*;

class Reservation {
    private String id;
    private String guestName;
    private String roomType;
    private int quantity;

    public Reservation(String id, String guestName, String roomType, int quantity) {
        this.id = id;
        this.guestName = guestName;
        this.roomType = roomType;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getQuantity() {
        return quantity;
    }
}

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return new ArrayList<>(history);
    }
}

class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void displayAllBookings() {
        for (Reservation r : history.getAll()) {
            System.out.println(r.getId() + " " + r.getGuestName() + " " + r.getRoomType() + " " + r.getQuantity());
        }
    }

    public void roomTypeSummary() {
        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : history.getAll()) {
            summary.put(
                    r.getRoomType(),
                    summary.getOrDefault(r.getRoomType(), 0) + r.getQuantity()
            );
        }

        for (Map.Entry<String, Integer> e : summary.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.add(new Reservation("R1", "Amit", "Single", 1));
        history.add(new Reservation("R2", "Priya", "Double", 2));
        history.add(new Reservation("R3", "Rahul", "Suite", 1));

        BookingReportService reportService = new BookingReportService(history);

        reportService.displayAllBookings();

        reportService.roomTypeSummary();
    }
}