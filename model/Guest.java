package model;

// imports
import enums.Gender;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// the class
public class Guest {

    // fields
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;
    private List<Reservation> reservations;

    // constructors
    public Guest(String username, String password,
          LocalDate dateOfBirth, double balance,
          String address, Gender gender){

        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        setBalance(balance);
        setAddress(address);
        setGender(gender);
        this.reservations = new ArrayList<>();
//        this.username = username;
//        this.password = password;
//        this.dateOfBirth = dateOfBirth; -----> old assigning (no validation) [all commented out]
//        this.balance = balance;
//        this.address = address;
//        this.gender = gender;
    }

    // setters
    void setUsername(String username) {
        if (username == null || username.isBlank()) return; // validation
        this.username = username;
    }

    void setPassword(String password){
        if (password == null || password.length() < 4) return; // validation
        this.password = password;
    }

    void setDateOfBirth(LocalDate dateOfBirth){
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) return; // validation
        this.dateOfBirth = dateOfBirth;
    }

    void setBalance(double balance){
        if (balance < 0) return; // validation
        this.balance = balance;
    }

    void setAddress(String address){
        if (address == null || address.isBlank()) return; // validation
        this.address = address;
    }

    void setGender(Gender gender){
        if (gender == null) return; // validation
        this.gender = gender;
    }

    // getters
    String getUsername(){ return this.username; }
    String getPassword(){ return this.password; }
    LocalDate getDateofBirth(){ return this.dateOfBirth; }
    double getBalance(){ return this.balance; }
    String getAddress(){ return this.address; }
    Gender getGender(){ return this.gender; }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(this.reservations); // this line returns a list from the type Reservation for a better encapsulation
    }
}
