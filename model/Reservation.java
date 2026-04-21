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

    // some new guard methods (lets us see whether method can have access to change status or not)
    public boolean canConfirm() {
        return status == ReservationStatus.PENDING;
    }

    public boolean canCancel() {
        return status == ReservationStatus.PENDING || status == ReservationStatus.CONFIRMED;
    }

    public boolean canComplete() {
        return status == ReservationStatus.CONFIRMED;
    }

    // Status methods themselves
    public void confirm() throws InvalidDateException {
        if (!canConfirm()) return; // because we only can confirm the pending (waiting) reservation
        if (! (room.isAvailable(checkIn, checkOut)) ) {return;} // if room is not available return
        status = ReservationStatus.CONFIRMED;
        // removed the setAvailable(false); because we now are using the date based one
    }

    public void cancel() {
        if (!canCancel()) return; // if reservation is complete we cannot cancel it
        status = ReservationStatus.CANCELLED;
        // removed the setAvailable(true); because we are now using the data based one
    }

    public void complete() {
        if (!canComplete()) return; // bec only confirmed reservations can be completed
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