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

    public synchronized boolean allocate(String type, int count) {
        int available = inventory.getOrDefault(type, 0);
        if (available >= count) {
            inventory.put(type, available - count);
            return true;
        }
        return false;
    }

    public synchronized void display() {
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public synchronized void add(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation poll() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

class BookingProcessor implements Runnable {
    private BookingRequestQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingRequestQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;
            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                r = queue.poll();
            }

            if (r != null) {
                boolean success = inventory.allocate(r.getRoomType(), r.getQuantity());
                if (success) {
                    System.out.println(Thread.currentThread().getName() + " Confirmed " + r.getId());
                } else {
                    System.out.println(Thread.currentThread().getName() + " Failed " + r.getId());
                }
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.add(new Reservation("R1", "Amit", "Single", 1));
        queue.add(new Reservation("R2", "Priya", "Single", 1));
        queue.add(new Reservation("R3", "Rahul", "Single", 1));

        Thread t1 = new Thread(new BookingProcessor(queue, inventory));
        Thread t2 = new Thread(new BookingProcessor(queue, inventory));
        Thread t3 = new Thread(new BookingProcessor(queue, inventory));

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.display();
    }
}