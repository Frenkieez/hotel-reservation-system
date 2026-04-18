package database;

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

    public static List<Staff> staffMembers = new ArrayList<>();

    public static void initializeData() {

        // ---------- ROOM TYPES ----------
        RoomType single = new RoomType("Single", 100);
        RoomType doubleRoom = new RoomType("Double", 180);

        // ---------- AMENITIES ----------
        Amenity wifi = new Amenity("WiFi");
        Amenity tv = new Amenity("TV");

        List<Amenity> basicAmenities = new ArrayList<>();
        basicAmenities.add(wifi);
        basicAmenities.add(tv);

        // ---------- ROOMS ----------
        Room room1 = new Room(101, single, basicAmenities, true);
        Room room2 = new Room(102, doubleRoom, basicAmenities, true);

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

        // ---------- INVOICES ----------
        Invoice inv1 = new Invoice(r1);
        inv1.generateInvoice();

        invoices.add(inv1);

        // ---------- STAFF ----------
        Admin admin = new Admin("admin", "admin123",
                LocalDate.of(1990,1,1), 40, Role.ADMIN);

        Receptionist rec = new Receptionist("rec", "rec123",
                LocalDate.of(1995,1,1), 35, Role.RECEPTIONIST);

        staffMembers.add(admin);
        staffMembers.add(rec);
    }
}