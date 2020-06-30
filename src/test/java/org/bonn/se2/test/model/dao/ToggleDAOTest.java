package org.bonn.se2.test.model.dao;

import org.bonn.se2.model.dao.ToggleDAO;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Marceli Dziadek
 */

class ToggleDAOTest {

    static ToggleDAO dao;
    static Boolean[] boo = {true, false, true, false};
    static int i = 0;

    @BeforeEach
    void beforeEach() {
        try {
            dao = new ToggleDAO();
            dao.updateToggle(boo[i++ % 4]);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void test1() {
        try {
            assertTrue(dao.retrieve());
            assertNotEquals(dao, false);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void test2() {
        try {
            assertFalse(dao.retrieve());
            assertNotEquals(dao, true);
        } catch (DatabaseException e) {
            fail();
        }
    }

}
