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

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrease(String type, int count) {
        inventory.put(type, getAvailability(type) - count);
    }

    public void increase(String type, int count) {
        inventory.put(type, getAvailability(type) + count);
    }

    public void display() {
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}

class BookingService {
    private RoomInventory inventory;
    private Map<String, List<String>> reservationRooms;
    private Map<String, Reservation> confirmed;
    private Stack<String> releasedRoomIds;
    private int counter;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.reservationRooms = new HashMap<>();
        this.confirmed = new HashMap<>();
        this.releasedRoomIds = new Stack<>();
        this.counter = 1;
    }

    private String generateRoomId(String type) {
        return type.substring(0, 1).toUpperCase() + counter++;
    }

    public void confirm(Reservation r) {
        if (inventory.getAvailability(r.getRoomType()) >= r.getQuantity()) {
            List<String> rooms = new ArrayList<>();
            for (int i = 0; i < r.getQuantity(); i++) {
                String id = generateRoomId(r.getRoomType());
                rooms.add(id);
            }
            inventory.decrease(r.getRoomType(), r.getQuantity());
            reservationRooms.put(r.getId(), rooms);
            confirmed.put(r.getId(), r);
            System.out.println("Confirmed " + r.getId());
        } else {
            System.out.println("Failed " + r.getId());
        }
    }

    public void cancel(String reservationId) {
        if (!confirmed.containsKey(reservationId)) {
            System.out.println("Invalid cancellation " + reservationId);
            return;
        }

        Reservation r = confirmed.get(reservationId);
        List<String> rooms = reservationRooms.get(reservationId);

        for (String roomId : rooms) {
            releasedRoomIds.push(roomId);
        }

        inventory.increase(r.getRoomType(), rooms.size());

        confirmed.remove(reservationId);
        reservationRooms.remove(reservationId);

        System.out.println("Cancelled " + reservationId);
    }

    public void displayReleased() {
        while (!releasedRoomIds.isEmpty()) {
            System.out.println(releasedRoomIds.pop());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);

        BookingService service = new BookingService(inventory);

        Reservation r1 = new Reservation("R1", "Amit", "Single", 2);

        service.confirm(r1);

        inventory.display();

        service.cancel("R1");

        inventory.display();

        service.displayReleased();

        service.cancel("R1");
    }
}