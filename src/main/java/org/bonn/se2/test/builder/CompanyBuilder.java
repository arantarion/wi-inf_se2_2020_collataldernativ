package org.bonn.se2.test.builder;

import org.bonn.se2.model.objects.dto.Company;

public class CompanyBuilder extends AbsUserBuilder {

    protected String name, description, website;

    private static CompanyBuilder instance;

    private CompanyBuilder() {
    }

    public static synchronized CompanyBuilder getInstance() {
        if (instance == null) {
            instance = new CompanyBuilder();
        }
        return instance;
    }

    public CompanyBuilder createDefaultCompany() {
        createDefaultUser();

        name = "Der GmbH GmbH";
        description = "Test Test Test";
        website = "test.de";
        return this;
    }

    public CompanyBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CompanyBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CompanyBuilder withWebsite(String website) {
        this.website = website;
        return this;
    }

    @Override
    public Company done() {
        Company dto = new Company();
        dto.setUsername(username);
        dto.setEmail(email);
        dto.setPasswort(password);
        dto.setAdresse(address);
        dto.setID(userid);
        dto.setRegistrationsDatum(registrationdate);
        dto.setName(name);
        dto.setBeschreibung(description);
        dto.setWebURL(website);
        return dto;
    }


}
