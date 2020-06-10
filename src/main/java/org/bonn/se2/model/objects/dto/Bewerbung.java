package org.bonn.se2.model.objects.dto;

import java.time.LocalDate;
import java.util.Objects;

public class Bewerbung {

    private int bewerbungsID;
    private int jobofferID;
    private int companyID;
    private int studentID;
    private LocalDate bewerbungsdatum;
    private int lebenslauf;
    private int bewerbung;
    private String notes;

    public Bewerbung(int bewerbungsID, int jobofferID, int companyID, int studentID, LocalDate bewerbungsdatum, int lebenslauf, int bewerbung, String notes) {
        this.bewerbungsID = bewerbungsID;
        this.jobofferID = jobofferID;
        this.companyID = companyID;
        this.studentID = studentID;
        this.bewerbungsdatum = bewerbungsdatum;
        this.lebenslauf = lebenslauf;
        this.bewerbung = bewerbung;
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bewerbung bewerbung1 = (Bewerbung) o;
        return bewerbungsID == bewerbung1.bewerbungsID &&
                jobofferID == bewerbung1.jobofferID &&
                companyID == bewerbung1.companyID &&
                studentID == bewerbung1.studentID &&
                lebenslauf == bewerbung1.lebenslauf &&
                bewerbung == bewerbung1.bewerbung &&
                Objects.equals(bewerbungsdatum, bewerbung1.bewerbungsdatum) &&
                notes.equals(bewerbung1.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bewerbungsID, jobofferID, companyID, studentID, bewerbungsdatum, lebenslauf, bewerbung, notes);
    }

    public int getBewerbungsID() {
        return bewerbungsID;
    }

    public void setBewerbungsID(int bewerbungsID) {
        this.bewerbungsID = bewerbungsID;
    }

    public int getJobofferID() {
        return jobofferID;
    }

    public void setJobofferID(int jobofferID) {
        this.jobofferID = jobofferID;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public LocalDate getBewerbungsdatum() {
        return bewerbungsdatum;
    }

    public void setBewerbungsdatum(LocalDate bewerbungsdatum) {
        this.bewerbungsdatum = bewerbungsdatum;
    }

    public int getLebenslauf() {
        return lebenslauf;
    }

    public void setLebenslauf(int lebenslauf) {
        this.lebenslauf = lebenslauf;
    }

    public int getBewerbung() {
        return bewerbung;
    }

    public void setBewerbung(int bewerbung) {
        this.bewerbung = bewerbung;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
