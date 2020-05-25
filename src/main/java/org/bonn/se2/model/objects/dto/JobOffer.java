package org.bonn.se2.model.objects.dto;

import java.time.LocalDate;
import java.util.Objects;

public class JobOffer {

    private String bereich;
    private String kontakt;
    private String beschreibung;
    private int jobofferID;
    private String name;
    private int companyID;
    private LocalDate creationDate;
    private LocalDate beginDate;
    private String gehalt;


    public JobOffer(){
    }

    public JobOffer(String bereich, String kontakt, String beschreibung) {
        this.bereich = bereich;
        this.kontakt = kontakt;
        this.beschreibung = beschreibung;
    }

    public JobOffer(String bereich, String kontakt, String beschreibung, String name, String gehalt, LocalDate beginDate) {
        this.bereich = bereich;
        this.kontakt = kontakt;
        this.beschreibung = beschreibung;
        this.name = name;
        this.gehalt = gehalt;
        this.beginDate = beginDate;
    }

    public void setBereich(String bereich) {
        this.bereich = bereich;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setJobofferID(int jobofferID) {
        this.jobofferID = jobofferID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public void setGehalt(String gehalt) {
        this.gehalt = gehalt;
    }

    public String getBereich() {
        return bereich;
    }

    public String getKontakt() {
        return kontakt;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public int getJobofferID() {
        return jobofferID;
    }

    public String getName() {
        return name;
    }

    public int getCompanyID() {
        return companyID;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public String getGehalt() {
        return gehalt;
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
