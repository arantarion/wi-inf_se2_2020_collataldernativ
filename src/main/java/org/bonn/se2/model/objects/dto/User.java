package org.bonn.se2.model.objects.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User implements Serializable {

    private String username;
    private String email;
    private String passwort;
    private Address adresse;
    private Integer userID;
    private LocalDate registrationsDatum;

    public User() {
    }

    public User(String username, String email, String passwort, Address adresse, Integer ID, LocalDate registrationsDatum) {
        this.username = username;
        this.email = email;
        this.passwort = passwort;
        this.adresse = adresse;
        this.userID = ID;
        this.registrationsDatum = registrationsDatum;
    }

    public User(String username, String email, String passwort) {
        this.username = username;
        this.email = email;
        this.passwort = passwort;
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
        //TODO: implement HASH instead
        this.passwort = passwort;
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

    //    private List<Role> roles = null;
//    public boolean hasRole(String role) {
//
//        if (this.roles == null) {
//            getRoles();
//        }
//
//        for (Role r: roles){
//            if(r.getBezeichnung().equals(role)) return true;
//        }
//        return false;
//    }
//
//    private void getRoles() {
//
//        this.roles = RoleDAO.getInstance().getRolesForUser(this);
//
//    }

}
