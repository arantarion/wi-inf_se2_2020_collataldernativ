package org.bonn.se2.model.objects.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer Henry Weckermann
 */

public class Company extends User implements Serializable {

    private String name;
    private String beschreibung;
    private String webURL;
    private Integer bewertung;
    private Integer companyID;

    public Company() {
    }

    public Company(User user) {
        super(user.getUsername(), user.getEmail(), user.getPasswort());
    }

    public Company(String name, String beschreibung, String webURL, Integer bewertung) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.webURL = webURL;
        this.bewertung = bewertung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public Integer getBewertung() {
        return bewertung;
    }

    public void setBewertung(Integer bewertung) {
        this.bewertung = bewertung;
    }

    public Integer getcompanyID() {
        return companyID;
    }

    public void setcompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyID=" + companyID +
                ", name='" + name + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", webURL='" + webURL + '\'' +
                ", bewertung=" + bewertung +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(companyID, company.companyID);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
