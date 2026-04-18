package model;

// imports
import enums.ReservationStatus;
import exceptions.InvalidDateException;
import exceptions.InvalidReservationException;
import java.time.LocalDate;


// the class
public class Reservation {

    // fields
    private Guest guest;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private ReservationStatus status;

    // constructors
    public Reservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) throws InvalidReservationException{

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

    // calculations
    public double calculateTotal() throws InvalidReservationException, InvalidDateException {
        if (room == null) {
            throw new InvalidReservationException("Error, room must NOT be null");
        }
        return room.calculatePrice(checkIn, checkOut); // removed logic of calculations because it is part of RoomType class
    }

    // Status cycle
    /*
   pending then either (confirmed/canceled) then completed (in case confirmed)
   */

    public void confirm() throws InvalidDateException {
        if (status != ReservationStatus.PENDING) return; // because we only can confirm the pending (waiting) reservation
        if (! (room.isAvailable(checkIn, checkOut)) ) {return;} // if room is not available return
        status = ReservationStatus.CONFIRMED;
        room.setAvailable(false); // after confirming a reservation, the room becomes NOT available for any other reservations
    }

    public void cancel() {
        if (status == ReservationStatus.COMPLETED) return; // if reservation is complete we cannot cancel it
        status = ReservationStatus.CANCELLED;
        room.setAvailable(true); // after cancelling the reservation, the room becomes empty for any new reservations
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