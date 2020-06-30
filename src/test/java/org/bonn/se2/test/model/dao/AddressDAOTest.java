package org.bonn.se2.test.model.dao;

import org.bonn.se2.model.dao.AddressDAO;
import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Marceli Dziadek
 */

public class AddressDAOTest {

    private static AddressDAO dao;
    private static List<Address> listAddress;

    @BeforeAll
    static void beforeAll() {
        listAddress = new ArrayList<>();
        try {
            dao = new AddressDAO();
        } catch (DatabaseException e) {
            Logger.getLogger("AddressDAOTest").log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
            fail();
        }
    }

    @Test
    @Order(1)
    public void retrieveAll() {
        listAddress = new ArrayList<>();
        try {
            listAddress = dao.retrieveAll();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
            fail();
        }
        assertTrue(listAddress.size() > 0);
    }

    @Test
    @Order(2)
    public void retrieve() {
        Address address = new Address();
        try {
            address = dao.retrieve(7);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
            fail();
        }
        assertEquals(address.getStrasse(), "Waldstr");
        assertEquals(address.getHausnummer(), "99");
        assertEquals(address.getLand(), "Deutschland");
    }

}