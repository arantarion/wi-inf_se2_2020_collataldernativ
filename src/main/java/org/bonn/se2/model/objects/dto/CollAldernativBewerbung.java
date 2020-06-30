package org.bonn.se2.model.objects.dto;

import java.time.LocalDate;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class CollAldernativBewerbung extends Bewerbung {

    CollAldernativBewerbung() {
    }

    @Override
    public void setJobofferID(int jobOfferID) {
        super.setJobofferID(jobOfferID);
    }

    @Override
    public void setCompanyID(int companyID) {
        super.setCompanyID(companyID);
    }

    @Override
    public void setStudentID(int studentID) {
        super.setStudentID(studentID);
    }

    @Override
    public void setNotes(String notes) {
        super.setNotes(notes);
    }

    @Override
    public void setBewerbungsdatum(LocalDate bewerbungsdatum) {
        super.setBewerbungsdatum(bewerbungsdatum);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
