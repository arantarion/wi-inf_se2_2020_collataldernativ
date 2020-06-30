package org.bonn.se2.model.objects.dto;

import java.time.LocalDate;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class Stepstone2BewerbungsDTO extends StepstoneBewerbung {

    Stepstone2BewerbungsDTO() {
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public void setStudent(Student student) {
        super.setStudent(student);
    }

    @Override
    public void setOffer(JobOffer offer) {
        super.setOffer(offer);
    }

    @Override
    public void setCompany(Company company) {
        super.setCompany(company);
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
    public int hashCode() {
        return super.hashCode();
    }
}
