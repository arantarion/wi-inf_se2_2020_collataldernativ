package org.bonn.se2.test.builder;

import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.model.objects.dto.User;

import java.time.LocalDate;

public abstract class AbsUserBuilder {

    protected String username, email, password;
    protected int userid;
    protected byte[] image;
    protected Address address;
    protected LocalDate registrationdate;

    public AbsUserBuilder createDefaultUser() {
        username = "SuperMuster";
        email = "test@test.de";
        password = "123456";
        address = AddressBuilder.getInstance().createDefaultAddress().get();
        registrationdate = LocalDate.now();
        return this;
    }

    public AbsUserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public AbsUserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public AbsUserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public AbsUserBuilder withUserid(int userid) {
        this.userid = userid;
        return this;
    }

    public AbsUserBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public AbsUserBuilder withRegistrationdate(LocalDate registrationdate) {
        this.registrationdate = LocalDate.from(registrationdate);
        return this;
    }

    public abstract User done();

}
