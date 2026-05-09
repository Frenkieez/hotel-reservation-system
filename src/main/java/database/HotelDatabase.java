package database;

import java.time.LocalDate;
import java.util.ArrayList;

import exceptions.InvalidDateException;
import exceptions.InvalidPriceException;
import exceptions.InvalidReservationException;
import model.*;
import enums.Gender;

public class HotelDatabase {

    // Data fields
    private static ArrayList<Admin> admins = new ArrayList<>();
    private static ArrayList<Receptionist> receptionists = new ArrayList<>();
    private static ArrayList<Guest> guests = new ArrayList<>();
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<RoomType> roomTypes = new ArrayList<>();
    private static ArrayList<Amenity> amenities = new ArrayList<>();
    private static ArrayList<Reservation> reservations = new ArrayList<>();
    private static ArrayList<Invoice> invoices = new ArrayList<>();


    // The initial data
    public static void initializeData() throws InvalidPriceException, InvalidDateException, InvalidReservationException {

        admins.clear();
        receptionists.clear();
        guests.clear();
        rooms.clear();
        roomTypes.clear();
        amenities.clear();
        reservations.clear();
        invoices.clear();

        // Amenities
        Amenity wifi = new Amenity("WiFi");
        Amenity tv = new Amenity("TV");
        Amenity miniBar = new Amenity("Mini Bar");
        Amenity ac = new Amenity("Air Conditioning");

        amenities.add(wifi);
        amenities.add(tv);
        amenities.add(miniBar);
        amenities.add(ac);


        // Room types
        RoomType single = new RoomType("Single", 500);
        RoomType dbl = new RoomType("Double", 800);
        RoomType suite = new RoomType("Suite", 1500);

        roomTypes.add(single);
        roomTypes.add(dbl);
        roomTypes.add(suite);

        //Rooms
        Room room101 = new Room(101, single);
        Room room102 = new Room(102, dbl);
        Room room201 = new Room(201, suite);

        // Assigning amenities
        room101.assignAmenity(wifi);
        room101.assignAmenity(ac);

        room102.assignAmenity(wifi);
        room102.assignAmenity(tv);
        room102.assignAmenity(ac);

        room201.assignAmenity(wifi);
        room201.assignAmenity(tv);
        room201.assignAmenity(miniBar);
        room201.assignAmenity(ac);

        rooms.add(room101);
        rooms.add(room102);
        rooms.add(room201);


        // Users
        Admin admin1 = new Admin("admin1", "Admin@123", "1985-03-10", 8);
        Admin admin2 = new Admin("a", "111111", "1985-03-10", 8);

        admins.add(admin1);
        admins.add(admin2);

        Receptionist receptionist1 = new Receptionist("reception1", "Recep@123", "1998-07-14", 8);
        Receptionist receptionist2 = new Receptionist("r", "111111", "1998-07-14", 8);

        receptionists.add(receptionist1);
        receptionists.add(receptionist2);

        Guest guest1 = new Guest("kareem", "Kareem@123",
                LocalDate.of(2004, 5, 12), 5000,
                "Cairo", Gender.MALE);

        Guest guest2 = new Guest("u1", "111111",
                LocalDate.of(2003, 9, 20), 7000,
                "Nasr City", Gender.FEMALE);

        guests.add(guest1);
        guests.add(guest2);


        // Reservations
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


        guest1.addReservation(reservation1);
        guest2.addReservation(reservation2);

        reservations.add(reservation1);
        reservations.add(reservation2);


        // Invoices
        Invoice invoice1 = new Invoice(
                reservation1,
                room101.calculatePrice(
                        LocalDate.of(2026, 5, 1),
                        LocalDate.of(2026, 5, 5)
                )
        );

        Invoice invoice2 = new Invoice(
                reservation2,
                room102.calculatePrice(
                        LocalDate.of(2026, 5, 10),
                        LocalDate.of(2026, 5, 13)
                )
        );

        invoices.add(invoice1);
        invoices.add(invoice2);

        guest1.getInvoices().add(invoice1);
        guest2.getInvoices().add(invoice2);
    }




    // Methods to add something
    public static void addReservation(Reservation reservation) {
        reservations.add(reservation);

        reservation.getGuest().addReservation(reservation);
    }

    public static void addInvoice(Invoice invoice) {
        invoices.add(invoice);

        // keep guest synced
        invoice.getReservation().getGuest().getInvoices().add(invoice);
    }

    public static void addGuest(Guest guest) {
        guests.add(guest);
    }

    public static void addRoom(Room room) {
        rooms.add(room);
    }


    // Getters
    public static ArrayList<Admin> getAdmins() {
        return admins;
    }

    public static ArrayList<Receptionist> getReceptionists() {
        return receptionists;
    }

    public static ArrayList<Guest> getGuests() {
        return guests;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static ArrayList<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public static ArrayList<Amenity> getAmenities() {
        return amenities;
    }

    public static ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public static ArrayList<Invoice> getInvoices() {
        return invoices;
    }
}


