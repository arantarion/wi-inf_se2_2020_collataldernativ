package org.bonn.se2.test.builder;

import org.bonn.se2.model.objects.dto.Address;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class AddressBuilder {

    private static AddressBuilder instance;
    private String strasse;
    private String hausnummer;
    private String plz;
    private String stadt;
    private String land;

    private AddressBuilder() {
    }

    public static synchronized AddressBuilder getInstance() {
        if (instance == null) {
            instance = new AddressBuilder();
        }
        return instance;
    }

    public AddressBuilder createDefaultAddress() {
        this.strasse = "Musterstraße";
        this.hausnummer = "1AAA";
        this.plz = "11111";
        this.stadt = "Bonn";
        this.land = "Deutschland";
        return this;
    }

    public AddressBuilder withStreet(String strasse) {
        this.strasse = strasse;
        return this;
    }

    public AddressBuilder withHousenumber(String hausnummer) {
        this.hausnummer = hausnummer;
        return this;
    }

    public AddressBuilder withPostalcode(String plz) {
        this.plz = plz;
        return this;
    }

    public AddressBuilder withCity(String stadt) {
        this.stadt = stadt;
        return this;
    }

    public AddressBuilder withCountry(String land) {
        this.land = land;
        return this;
    }

    public AddressBuilder withoutStreet() {
        return withStreet(null);
    }

    public AddressBuilder withoutHousenumber() {
        return withHousenumber(null);
    }

    public AddressBuilder withoutPostalcode() {
        return withPostalcode(null);
    }

    public AddressBuilder withoutCity() {
        return withCity(null);
    }

    public AddressBuilder withouCountry() {
        return withCountry(null);
    }

    public Address get() {
        return done();
    }

    public Address done() {
        Address dto = new Address();
        dto.setStrasse(strasse);
        dto.setHausnummer(hausnummer);
        dto.setPlz(plz);
        dto.setStadt(stadt);
        dto.setLand(land);
        return dto;
    }

}
