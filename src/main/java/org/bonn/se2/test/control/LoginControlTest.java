package org.bonn.se2.test.control;

import org.bonn.se2.model.objects.dto.UserAtLogin;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer Henry Weckermann
 */

public class LoginControlTest {

    @Test
    void checkAuthentication() {
        UserAtLogin dto = new UserAtLogin("", "");
        assertThrows(InvalidCredentialsException.class, () -> LoginControl.checkAuthentication(dto));
    }

    @Test
    void getRole() {
        UserAtLogin studentDto = new UserAtLogin("HenryW", "123456");
        UserAtLogin companyDto = new UserAtLogin("TestCompany", "123456");

        assertEquals(LoginControl.getRole(companyDto), Configuration.Roles.COMPANY);
        assertEquals(LoginControl.getRole(studentDto), Configuration.Roles.STUDENT);
    }
}