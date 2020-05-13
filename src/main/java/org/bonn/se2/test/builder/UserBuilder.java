package org.bonn.se2.test.builder;

import org.bonn.se2.model.objects.dto.User;

public class UserBuilder extends AbsUserBuilder {

    @Override
    public User done() {
       User user = new User();
       user.setUsername(username);
       user.setAdresse(address);
       user.setEmail(email);
       user.setPasswort(password);
       user.setRegistrationsDatum(registrationdate);

       return user;
    }
}
