package database;

import exceptions.InvalidPriceException;
import exceptions.InvalidReservationException;
import model.*;
import enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelDatabase {

    public static List<Guest> guests = new ArrayList<>();
    public static List<Room> rooms = new ArrayList<>();
    public static List<Reservation> reservations = new ArrayList<>();
    public static List<Invoice> invoices = new ArrayList<>();
    public static List<Amenity> amenities = new ArrayList<>();                                                          // edited by 3elba (+next line)
    public static List<RoomType> roomTypes = new ArrayList<>();

    public static List<Staff> staffMembers = new ArrayList<>();

    public static void initializeData() throws InvalidReservationException, InvalidPriceException {

        // ---------- ROOM TYPES ----------
        RoomType single = new RoomType("Single", 100);
        RoomType doubleRoom = new RoomType("Double", 180);

        roomTypes.add(single);                                                                                          // edited by 3elba(+next line)
        roomTypes.add(doubleRoom);

        // ---------- AMENITIES ----------
        Amenity wifi = new Amenity("WiFi");
        Amenity tv = new Amenity("TV");

        amenities.add(wifi);                                                                                            // edited by 3elba(+next line)
        amenities.add(tv);

        // ---------- ROOMS ----------
        Room room1 = new Room(101, single);
        Room room2 = new Room(102, doubleRoom);

        // attach amenities to rooms                                                                                    // edited by 3elba
        room1.getAmenities().add(new Amenity("WiFi"));
        room1.getAmenities().add(new Amenity("TV"));

        room2.getAmenities().add(new Amenity("WiFi"));
        room2.getAmenities().add(new Amenity("TV"));


        rooms.add(room1);
        rooms.add(room2);

        // ---------- GUESTS ----------
        Guest g1 = new Guest("user1", "123456", LocalDate.of(2000,1,1), 1000, "Cairo", Gender.MALE);
        Guest g2 = new Guest("user2", "123456", LocalDate.of(1999,5,5), 500, "Giza", Gender.FEMALE);

        guests.add(g1);
        guests.add(g2);

        // ---------- RESERVATIONS ----------
        Reservation r1 = new Reservation(g1, room1,
                LocalDate.now(),
                LocalDate.now().plusDays(2));

        reservations.add(r1);

//        // ---------- INVOICES ----------
//        Invoice inv1 = new Invoice(r1);
//        inv1.generateInvoice();
//
//        invoices.add(inv1);

        // ---------- STAFF ----------
        Admin admin = new Admin("admin", "admin123",
                "1990-01-01", 40);

        Receptionist rec = new Receptionist("receptionist", "rec123",
                "1995-01-01", 35);

        staffMembers.add(admin);
        staffMembers.add(rec);
    }
}