/**
 * Room - Abstract representation of a hotel room.
 * Defines common attributes shared across all room types.
 *
 * @author Virat
 * @version 2.1
 */

public abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int beds, int size, double price) {
        this.numberOfBeds = beds;
        this.squareFeet = size;
        this.pricePerNight = price;
    }

    /**
     * Displays room details.
     */
    public void displayRoomDetails() {

        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: ₹" + pricePerNight);
    }
}