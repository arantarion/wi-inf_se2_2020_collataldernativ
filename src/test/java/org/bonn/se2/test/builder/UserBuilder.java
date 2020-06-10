package org.bonn.se2.test.builder;

import org.bonn.se2.model.objects.dto.User;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

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
