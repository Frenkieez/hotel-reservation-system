# Hotel Reservation System

## Project Overview
This is a simple Hotel Reservation System built using Java.  
The system simulates how a real hotel works with three main roles:

- Guest
- Receptionist
- Admin

It allows users to make reservations, manage rooms, and handle check-in/check-out operations.

---

## Main Idea

The system works like this:

1. Guest logs in or registers
2. Guest books a room (reservation is created)
3. Receptionist confirms reservation
4. Receptionist checks in the guest
5. Receptionist checks out the guest
6. Invoice is generated and paid
7. Admin manages hotel data (rooms, amenities, room types)

---

## Project Structure

### 1. Models (Core Classes)
These represent real-world objects:

- Guest
- Room
- Reservation
- Invoice
- RoomType
- Amenity
- Staff (Admin / Receptionist)

---

### 2. Database Class
`HotelDatabase` is used as a database using ArrayLists.

It stores:
- All users
- Rooms
- Reservations
- Invoices

---

### 3. Roles

#### Guest
Can:
- View available rooms
- Make reservation
- View reservations
- Cancel reservation
- View invoices
- Pay invoice

---

#### Receptionist
Can:
- View guests
- View rooms
- View reservations
- Confirm reservation
- Check-in guest
- Check-out guest

---

#### Admin
Can:
- Add / update / delete rooms
- Add / update / delete amenities
- Add / update / delete room types
- View all system data

---

## System Flow Example

### Reservation Cycle

1. Guest selects a room
2. System checks availability
3. Reservation is created (PENDING)
4. Receptionist confirms reservation
5. Status becomes CONFIRMED

---

### Check-in Cycle

1. Receptionist selects reservation
2. System checks if it is CONFIRMED
3. Guest is checked in

---

### Check-out Cycle

1. Receptionist selects reservation
2. System calculates total price
3. Invoice is generated
4. Guest is marked as COMPLETED
5. Invoice is sent to guest

---

### Payment Cycle

1. Guest views invoices
2. Selects invoice
3. Enters payment amount
4. Chooses payment method (CASH / CARD / ONLINE)
5. Invoice is marked as PAID if fully paid

---

## Technologies Used

- Java
- OOP Principles (Inheritance, Encapsulation, Polymorphism)
- ArrayLists (as in-memory database)
- LocalDate (for date handling)

---

## OOP Concepts Applied

- Encapsulation → private fields with getters/setters
- Inheritance → Admin & Receptionist extend Staff
- Polymorphism → overridden methods in roles
- Abstraction → Manageable interface for admin operations

---

## Git Workflow

The project was developed using a structured Git workflow with multiple branches:

- Each team member worked on a separate feature branch (4 branches total, one per member)
- A shared `dev` branch was used for integration of all features
- One member was responsible for merging and integrating all branches into `dev`
- After integration and testing, a clean and stable version was pushed to the `main` branch

This workflow ensures:
- Parallel development without conflicts
- Controlled integration of features
- A stable production-ready `main` branch

This structure also shows proper teamwork and version control practice rather than a single-version development process.

Link of github repo : https://github.com/Frenkieez/hotel-reservation-system
Link of YouTube video : https://youtu.be/_AnCpmcxBZs
---

## Notes

- This is not a real database system (no SQL used)
- Data resets when program restarts
