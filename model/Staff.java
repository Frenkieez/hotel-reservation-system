package model;

import enums.Role;
import java.time.LocalDate;

/*
 * TEMPORARY VERSION of Staff class
 * Used until we integrate full real classes
 */
public abstract class Staff {

    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;
    protected int workingHours;
    protected Role role;

    public Staff(String username, String password, LocalDate dateOfBirth, int workingHours, Role role) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.workingHours = workingHours;
        this.role = role;
    }

    public String getUsername() { return username; }

    public Role getRole() { return role; }

    public abstract void performDuties();
}