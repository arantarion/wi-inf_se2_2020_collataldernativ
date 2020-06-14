package org.bonn.se2.test.control;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.bonn.se2.model.objects.dto.UserAtLogin;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer Henry Weckermann
 */
@RunWith(MockitoJUnitRunner.class )
public class LoginControlTest {

    @Mock
    private UI myui; // erzeuge und injiziere ein „UI-Objekt“

    @Mock
    private VaadinSession session;

    @Before
    public void setUp() {
    // Füge simuliertes UI-Object in die UI-Klasse (Test Context) ein
        UI.setCurrent( myui );
    // Wenn Aufruf auf getSession erfolgt, dann liefer das simulierte Objekt
        when ( myui.getSession() ).thenReturn( session );
    }

    //positiv test
    @Test public void testLogin () throws InvalidCredentialsException, DatabaseException {
        UserAtLogin dto = new UserAtLogin("kpierz2s", "Test1234");
        LoginControl.checkAuthentication(dto);
    }

    //negativ Test
    @Test public void checkAuthentication() {
        UserAtLogin dto = new UserAtLogin("", "");
        assertThrows(InvalidCredentialsException.class, () -> LoginControl.checkAuthentication(dto));
    }
    /*
    @Test
    void getRole() {
        UserAtLogin studentDto = new UserAtLogin("HenryW", "123456");
        UserAtLogin companyDto = new UserAtLogin("TestCompany", "123456");

        assertEquals(LoginControl.getRole(companyDto), Configuration.Roles.COMPANY);
        assertEquals(LoginControl.getRole(studentDto), Configuration.Roles.STUDENT);
    }

     */
}