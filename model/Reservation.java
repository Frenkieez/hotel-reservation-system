package model;

// imports
import enums.ReservationStatus;
import exceptions.InvalidReservationException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


// the class
public class Reservation {

    // fields
    private Guest guest;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private ReservationStatus status;

    // constructors
    public Reservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut){

    if (guest == null || room == null || checkIn == null || checkOut == null ){
        throw new InvalidReservationException("Reservation fields cannot be null!");
    }
    if ( !(checkOut.isAfter(checkIn)) ){
        throw new InvalidReservationException("Check-out must be after check-in!");
    }
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = ReservationStatus.PENDING;

    }

    // calculations logics
    public double calculateTotal(Room room){
        if (room == null) {
            throw new InvalidReservationException("Error, room must NOT be null");
        }
        long days = ChronoUnit.DAYS.between(checkIn, checkOut); // returns a long that has the number of days wanted to reserve
        if (days < 0){
            throw new InvalidReservationException("Error, number of days must be a positive integer");
        }
        return days * room.getPricePerNight(); // price * n days = total
    }

    // Status cycle
    /*
   pending then either (confirmed/canceled) then completed (in case confirmed)
   */
    public void confirm() {
        if (status != ReservationStatus.PENDING) return; // because we only can confirm the pending (waiting) reservation
        status = ReservationStatus.CONFIRMED;
    }
    public void cancel() {
        if (status == ReservationStatus.COMPLETED) return; // if reservation is complete we cannot cancel it
        status = ReservationStatus.CANCELLED;
    }
    public void complete() {
        if (status != ReservationStatus.CONFIRMED) return; // bec only confirmed reservations can be completed
        status = ReservationStatus.COMPLETED;
    }


    /*----- I will skip setters in this class because this class MUST be immutable after creating -----*/

    // getters
    public Guest getGuest() { return guest; }
    public Room getRoom() {
        return room;
    }
    public LocalDate getCheckIn() {
        return checkIn;
    }
    public LocalDate getCheckOut() {
        return checkOut;
    }
    public ReservationStatus getStatus() {
        return status;
    }
}














