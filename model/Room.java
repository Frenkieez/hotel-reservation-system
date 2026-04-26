package model;
import database.HotelDatabase;
import enums.ReservationStatus;
import exceptions.InvalidDateException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Room {

    private int roomNumber;
    private RoomType type;
    private ArrayList<Amenity> amenities = new ArrayList<>();

    // Constructor
    public Room(int roomNumber, RoomType type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.amenities = new ArrayList<>();
    }

    // Check availability
    public boolean isAvailable(LocalDate newIn, LocalDate newOut) throws InvalidDateException {
        for (Reservation r : HotelDatabase.getReservations()){
               // if room = this room and status of this room is confirmed :
            if (r.getRoom() == this &&
                    (r.getStatus() == ReservationStatus.CONFIRMED ||
                            r.getStatus() == ReservationStatus.PENDING)){
                // checking if there is overlapping in availability
                // if NOT [new_checkOut is before current checkIn OR new_checkIn is after current checkOut] (if not correct way of reservation)
                if (! (newOut.isBefore(r.getCheckIn()) || newIn.isAfter(r.getCheckOut())) ){
                    return false;
                }


            }

        }
        return true; // if all these are not wrong, room is available now
    }

    // Calculate price
    public double calculatePrice(LocalDate in, LocalDate out) throws InvalidDateException {
        long days = ChronoUnit.DAYS.between(in, out);

        if (days <= 0) {
            throw new InvalidDateException("Check-out date must be after check-in date.");
        }

        double basePrice = days * type.getPricePerNight();
        double extra = amenities.size() * 20;

        return basePrice + extra;
    }

    public void assignAmenity(Amenity amenity) {
        if (amenity == null) return;

        if (!amenities.contains(amenity)) {
            amenities.add(amenity);
        }
    }

    // Getters
    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }


    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }
}