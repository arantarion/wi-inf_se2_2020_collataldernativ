package org.bonn.se2.model.objects.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class Bewerbung {

    private int bewerbungsID;
    private int jobofferID;
    private int companyID;
    private int studentID;
    private LocalDate bewerbungsdatum;
    private String notes;

    public Bewerbung() {

    }

    public Bewerbung(int jobofferID, int companyID, int studentID, LocalDate bewerbungsdatum, String notes) {
        this.jobofferID = jobofferID;
        this.companyID = companyID;
        this.studentID = studentID;
        this.bewerbungsdatum = bewerbungsdatum;
        this.notes = notes;
    }

    public Bewerbung(int bewerbungsID, int jobofferID, int companyID, int studentID, LocalDate bewerbungsdatum) {
        this.bewerbungsID = bewerbungsID;
        this.jobofferID = jobofferID;
        this.companyID = companyID;
        this.studentID = studentID;
        this.bewerbungsdatum = bewerbungsdatum;
    }

    public Bewerbung(int bewerbungsID, int jobofferID, int companyID, int studentID, LocalDate bewerbungsdatum, String notes) {
        this.bewerbungsID = bewerbungsID;
        this.jobofferID = jobofferID;
        this.companyID = companyID;
        this.studentID = studentID;
        this.bewerbungsdatum = bewerbungsdatum;
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
                Objects.equals(bewerbungsdatum, bewerbung1.bewerbungsdatum) &&
                notes.equals(bewerbung1.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bewerbungsID, jobofferID, companyID, studentID, bewerbungsdatum, notes);
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
