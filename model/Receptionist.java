package model;

import enums.Role;
import java.time.LocalDate;

/*
 * TEMPORARY VERSION of Receptionist class
 * Used until we integrate full real classes
 */
public class Receptionist extends Staff {

    public Receptionist(String username, String password, LocalDate dob, int hours, Role role) {
        super(username, password, dob, hours, role);
    }

    public void checkInGuest(Reservation reservation) {
        System.out.println("Guest checked in (temp)");
    }

    public void checkOutGuest(Reservation reservation) {
        System.out.println("Guest checked out (temp)");
    }

    @Override
    public void performDuties() {
        System.out.println("Receptionist duties (temp)");
    }
}