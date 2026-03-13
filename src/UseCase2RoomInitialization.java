/**
 * Use Case 2 - Room Initialization and Static Availability
 *
 * Demonstrates object modeling using abstraction and inheritance.
 *
 * @author Virat
 * @version 2.1
 */

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("      BOOK MY STAY APP - v2.1");
        System.out.println("======================================");

        // Polymorphic room references
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        System.out.println("\nSingle Room Details:");
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailable);

        System.out.println("\nDouble Room Details:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailable);

        System.out.println("\nSuite Room Details:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailable);

        System.out.println("\nApplication terminated successfully.");
    }
}