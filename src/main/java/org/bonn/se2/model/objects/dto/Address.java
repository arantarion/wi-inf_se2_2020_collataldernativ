package org.bonn.se2.model.objects.dto;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {

    private Integer ID;
    private String strasse;
    private String hausnummer;
    private String plz;
    private String stadt;
    private String land;

    public Address() {
    }

    public Address(String strasse, String hausnummer, String plz, String stadt, String land) {
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.plz = plz;
        this.stadt = stadt;
        this.land = land;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getStrasse(), address.getStrasse()) &&
                Objects.equals(getHausnummer(), address.getHausnummer()) &&
                Objects.equals(getPlz(), address.getPlz()) &&
                Objects.equals(getStadt(), address.getStadt()) &&
                Objects.equals(getLand(), address.getLand());
    }

    @Override
    public String toString() {
        return "Address{" +
                "strasse='" + strasse + '\'' +
                ", hausnummer='" + hausnummer + '\'' +
                ", plz='" + plz + '\'' +
                ", stadt='" + stadt + '\'' +
                ", land='" + land + '\'' +
                '}';
    }
}
