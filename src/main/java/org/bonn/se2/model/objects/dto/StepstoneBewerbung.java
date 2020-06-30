package org.bonn.se2.model.objects.dto;

import java.time.LocalDate;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class StepstoneBewerbung extends Bewerbung {

    private Student student;
    private JobOffer offer;
    private Company company;
    private String notes;
    private LocalDate bewerbungsdatum;

    public StepstoneBewerbung() {

    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public JobOffer getOffer() {
        return offer;
    }

    public void setOffer(JobOffer offer) {
        this.offer = offer;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getBewerbungsdatum() {
        return bewerbungsdatum;
    }

    public void setBewerbungsdatum(LocalDate bewerbungsdatum) {
        this.bewerbungsdatum = bewerbungsdatum;
    }

    @Override
    public String toString() {
        return "StepstoneBewerbung{" +
                "student=" + student +
                ", offer=" + offer +
                ", company=" + company +
                ", notes='" + notes + '\'' +
                ", bewerbungsdatum=" + bewerbungsdatum +
                '}';
    }

}
