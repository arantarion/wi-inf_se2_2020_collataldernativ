package org.bonn.se2.model.objects.dto;

import org.json.JSONObject;

import java.time.LocalDate;

public class Stepstone2BewerbungsDTO extends StepstoneBewerbung {

    Stepstone2BewerbungsDTO() {
    }

    @Override
    public String toString() {
        return toJSON().toString();
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
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("student", getStudent() != null ? getStudent().toJSON() : "");
        obj.put("vacancy", getOffer() != null ? getOffer().toJSON() : "");
        obj.put("company", getCompany() != null ? getCompany().toJSON() : "");
        obj.put("coverletter", getNotes());
        obj.put("candidatureDate", getBewerbungsdatum() != null ? getBewerbungsdatum() : "");
        return obj;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
