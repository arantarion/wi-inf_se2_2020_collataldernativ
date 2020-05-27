package org.bonn.se2.model.objects.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class JobOffer implements Serializable {

    private String bereich;
    private String kontakt;
    private String beschreibung;
    private int jobofferID;
    private String name;
    private int companyID;
    private LocalDate creationDate;
    private LocalDate beginDate;
    private String gehalt;


    public JobOffer() {
    }

    public JobOffer(String bereich, String kontakt, String beschreibung) {
        this.bereich = bereich;
        this.kontakt = kontakt;
        this.beschreibung = beschreibung.replace("<br>", "");
    }

    public JobOffer(String bereich, String kontakt, String beschreibung, String name, String gehalt, LocalDate beginDate) {
        this.bereich = bereich;
        this.kontakt = kontakt;
        this.beschreibung = beschreibung.replace("<br>", "");
        this.name = name;
        this.gehalt = gehalt;
        this.beginDate = beginDate;
    }

    public String getBereich() {
        return bereich;
    }

    public void setBereich(String bereich) {
        this.bereich = bereich;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung.replace("<br>", "");
    }

    public int getJobofferID() {
        return jobofferID;
    }

    public void setJobofferID(int jobofferID) {
        this.jobofferID = jobofferID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public String getGehalt() {
        return gehalt;
    }

    public void setGehalt(String gehalt) {
        this.gehalt = gehalt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobOffer jobOffer = (JobOffer) o;
        return jobofferID == jobOffer.jobofferID &&
                companyID == jobOffer.companyID &&
                bereich.equals(jobOffer.bereich) &&
                kontakt.equals(jobOffer.kontakt) &&
                beschreibung.equals(jobOffer.beschreibung) &&
                Objects.equals(name, jobOffer.name) &&
                Objects.equals(creationDate, jobOffer.creationDate) &&
                Objects.equals(beginDate, jobOffer.beginDate) &&
                Objects.equals(gehalt, jobOffer.gehalt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bereich, kontakt, beschreibung, jobofferID, name, companyID, creationDate, beginDate, gehalt);
    }

    @Override
    public String toString() {
        return "JobOffer{" +
                "bereich='" + bereich + '\'' +
                ", kontakt='" + kontakt + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", jobofferID=" + jobofferID +
                ", name='" + name + '\'' +
                ", companyID=" + companyID +
                ", creationDate=" + creationDate +
                ", beginDate=" + beginDate +
                ", gehalt='" + gehalt + '\'' +
                '}';
    }
}
