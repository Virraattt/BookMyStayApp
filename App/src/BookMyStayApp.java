import java.util.*;
class Reservation {
    private String id;
    private String guestName;
    private String roomType;
    public Reservation(String id, String guestName, String roomType) {
        this.id = id;
        this.guestName = guestName;
        this.roomType = roomType;
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
}
class AddOnService {
    private String name;
    private double cost;
    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
    public String getName() {
        return name;
    }
    public double getCost() {
        return cost;
    }
}
class AddOnServiceManager {
    private Map<String, List<AddOnService>> serviceMap;
    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }
    public void addService(String reservationId, AddOnService service) {
        serviceMap.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }
    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (AddOnService s : getServices(reservationId)) {
            total += s.getCost();
        }
        return total;
    }
    public void displayServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);
        for (AddOnService s : services) {
            System.out.println(s.getName() + " " + s.getCost());
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        Reservation r1 = new Reservation("R1", "Amit", "Single");

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService("R1", new AddOnService("Breakfast", 200));
        manager.addService("R1", new AddOnService("AirportPickup", 500));
        manager.addService("R1", new AddOnService("ExtraBed", 300));

        manager.displayServices("R1");

        System.out.println(manager.calculateTotalCost("R1"));
    }
}