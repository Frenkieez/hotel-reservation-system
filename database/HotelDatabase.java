package database;

import java.time.LocalDate;
import java.util.ArrayList;

import exceptions.InvalidDateException;
import exceptions.InvalidPriceException;
import exceptions.InvalidReservationException;
import model.*;
import enums.Gender;

public class HotelDatabase {

    public static ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<Receptionist> receptionists = new ArrayList<>();
    public static ArrayList<Guest> guests = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<RoomType> roomTypes = new ArrayList<>();
    public static ArrayList<Amenity> amenities = new ArrayList<>();
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<Invoice> invoices = new ArrayList<>();

    public static void initializeData() throws InvalidPriceException, InvalidDateException, InvalidReservationException {

        admins.clear();
        receptionists.clear();
        guests.clear();
        rooms.clear();
        roomTypes.clear();
        amenities.clear();
        reservations.clear();
        invoices.clear();

        // ======================
        // AMENITIES
        // ======================
        Amenity wifi = new Amenity("WiFi");
        Amenity tv = new Amenity("TV");
        Amenity miniBar = new Amenity("Mini Bar");
        Amenity ac = new Amenity("Air Conditioning");

        amenities.add(wifi);
        amenities.add(tv);
        amenities.add(miniBar);
        amenities.add(ac);

        // ======================
        // ROOM TYPES
        // ======================
        RoomType single = new RoomType("Single", 500);
        RoomType dbl = new RoomType("Double", 800);
        RoomType suite = new RoomType("Suite", 1500);

        roomTypes.add(single);
        roomTypes.add(dbl);
        roomTypes.add(suite);

        // ======================
        // ROOMS
        // ======================
        Room room101 = new Room(101, single);
        Room room102 = new Room(102, dbl);
        Room room201 = new Room(201, suite);

        room101.addAmenity(wifi);
        room101.addAmenity(ac);

        room102.addAmenity(wifi);
        room102.addAmenity(tv);
        room102.addAmenity(ac);

        room201.addAmenity(wifi);
        room201.addAmenity(tv);
        room201.addAmenity(miniBar);
        room201.addAmenity(ac);

        rooms.add(room101);
        rooms.add(room102);
        rooms.add(room201);

        // ======================
        // USERS
        // ======================
        Admin admin1 = new Admin("admin1", "Admin@123", "1985-03-10", 8);
        Admin admin2 = new Admin("1", "111111", "1985-03-10", 8);
        admins.add(admin1);
        admins.add(admin2);


        Receptionist receptionist1 = new Receptionist("reception1", "Recep@123", "1998-07-14", 8);
        receptionists.add(receptionist1);
        Receptionist receptionist2 = new Receptionist("r", "111111", "1998-07-14", 8);
        receptionists.add(receptionist2);

        Guest guest1 = new Guest("kareem", "Kareem@123",
                LocalDate.of(2004, 5, 12), 5000,
                "Cairo", Gender.MALE);

        Guest guest2 = new Guest("u1", "111111",
                LocalDate.of(2003, 9, 20), 7000,
                "Nasr City", Gender.FEMALE);

        guests.add(guest1);
        guests.add(guest2);

        // ======================
        // RESERVATIONS (IMPORTANT FIX)
        // ======================
        Reservation reservation1 = new Reservation(
                guest1,
                room101,
                LocalDate.of(2026, 5, 1),
                LocalDate.of(2026, 5, 5)
        );

        Reservation reservation2 = new Reservation(
                guest2,
                room102,
                LocalDate.of(2026, 5, 10),
                LocalDate.of(2026, 5, 13)
        );

        // FIX: attach reservations to guest (THIS WAS YOUR BUG)
        guest1.viewReservations().add(reservation1);
        guest2.viewReservations().add(reservation2);

        reservations.add(reservation1);
        reservations.add(reservation2);

        // ======================
        // INVOICES
        // ======================
        Invoice invoice1 = new Invoice(reservation1,
                room101.calculatePrice(
                        LocalDate.of(2026, 5, 1),
                        LocalDate.of(2026, 5, 5)
                )
        );

        Invoice invoice2 = new Invoice(reservation2,
                room102.calculatePrice(
                        LocalDate.of(2026, 5, 10),
                        LocalDate.of(2026, 5, 13)
                )
        );

        invoices.add(invoice1);
        invoices.add(invoice2);
    }

    // ======================
    // ADD METHODS
    // ======================
    public static void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.getGuest().viewReservations().add(reservation); // FIX IMPORTANT
    }

    public static void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    public static void addGuest(Guest guest) {
        guests.add(guest);
    }

    public static void addRoom(Room room) {
        rooms.add(room);
    }
}