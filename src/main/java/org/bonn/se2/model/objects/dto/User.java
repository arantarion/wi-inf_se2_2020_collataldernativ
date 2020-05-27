package org.bonn.se2.model.objects.dto;

import org.bonn.se2.services.util.CryptoFunctions;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class User implements Serializable {

    private Integer userID;
    private String username;
    private String email;
    private String passwort;
    private Address adresse;
    private byte[] image;
    private LocalDate registrationsDatum;

    public User() {
    }

    public User(String username, String email, String passwort, Address adresse, Integer ID, LocalDate registrationsDatum, byte[] image) {
        this.username = username;
        this.email = email;
        this.passwort = passwort;
        this.adresse = adresse;
        this.userID = ID;
        this.registrationsDatum = registrationsDatum;
        this.image = image.clone();
    }

    public User(String username, String email, String passwort) {
        this.username = username;
        this.email = email;
        this.passwort = passwort;
    }

    public User(String username, String email, String passwort, byte[] image, Integer userID) {
        this.username = username;
        this.email = email;
        this.passwort = passwort;
        this.image = image.clone();
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = CryptoFunctions.hash(passwort);
    }

    public void setPwHash(String passwort) {
        this.passwort = passwort;
    }

    public Address getAdresse() {
        return adresse;
    }

    public void setAdresse(Address adresse) {
        this.adresse = adresse;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public byte[] getImage() {
        return image != null ? image.clone() : null;
    }

    public void setImage(byte[] image) {
        if (image == null) return;
        this.image = image.clone();
    }

    public LocalDate getRegistrationsDatum() {
        return registrationsDatum;
    }

    public void setRegistrationsDatum(LocalDate registrationsDatum) {
        this.registrationsDatum = LocalDate.from(registrationsDatum);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwort='" + passwort + '\'' +
                ", userID=" + userID +
                ", pic='" + Arrays.toString(image) + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPasswort(), user.getPasswort()) &&
                Objects.equals(getUserID(), user.getUserID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
