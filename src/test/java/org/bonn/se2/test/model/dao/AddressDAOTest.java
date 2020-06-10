package org.bonn.se2.test.model.dao;

import org.bonn.se2.model.dao.AddressDAO;
import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.test.builder.AddressBuilder;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class AddressDAOTest {
//
//    private static Address newAddress;
//    private Address testAddress, updatedAddress;
//    private static AddressDAO dao;
//    private static int testAddressId;
//
//    public static int getTestAddressId() {
//        return testAddressId;
//    }
//
//    public static void setTestAddressId(int testAddressId) {
//        AddressDAOTest.testAddressId = testAddressId;
//    }
//
//    @BeforeAll
//    static void beforeAll() {
//        try {
//            dao = new AddressDAO();
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//        }
//        newAddress = AddressBuilder.getInstance()
//                .createDefaultAddress()
//                .withStreet("Straße")
//                .withHousenumber("1")
//                .withPostalcode("12345")
//                .withCity("Stadt")
//                .withCountry("Land")
//                .get();
//    }
//
//    @BeforeEach
//    void setUp() {
//        testAddress = null;
//        updatedAddress = null;
//    }
//
//    @Test
//    @Order(1)
//    public void retrieveAll() {
//        List<Address> dtos = new ArrayList<>();
//        try {
//            dtos = dao.retrieveAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//        assertTrue(dtos.size() > 0);
//    }
//
//    @Test
//    @Order(2)
//    public void create() {
//        try {
//            assertNull(testAddress);
//            testAddress = dao.create(newAddress);
//            assertNotEquals(0, testAddress.getID());
//            setTestAddressId(testAddress.getID());
//            testDTO(testAddress);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//    @Test
//    @Order(3)
//    public void retrieve() {
//        try {
//            assertNull(testAddress);
//            testAddress = dao.retrieve(getTestAddressId());
//            testDTO(testAddress);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//    @Test
//    @Order(4)
//    public void updateOne() {
//        assertNull(testAddress);
//        try {
//            testAddress = dao.retrieve(getTestAddressId());
//            testAddress.setStrasse("Andere Straße");
//            testAddress.setHausnummer("2B");
//            testAddress.setPlz("54321");
//            updatedAddress = dao.update(testAddress);
//            assertEquals("Andere Straße", updatedAddress.getStrasse());
//            assertEquals("2B", updatedAddress.getHausnummer());
//            assertEquals("54321", updatedAddress.getPlz());
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//            fail();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    @Order(5)
//    public void deleteOne() throws Exception {
//        assertNull(testAddress);
//        try {
//            testAddress = dao.retrieve(getTestAddressId());
//            dao.delete(testAddress);
//            assertThrows(DatabaseException.class, () -> dao.retrieve(getTestAddressId()));
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//    private void testDTO(Address dto) {
//        assertNotEquals(0, dto.getID());
//        assertEquals("Straße", dto.getStrasse());
//        assertEquals("1", dto.getHausnummer());
//        assertEquals("12345", dto.getPlz());
//        assertEquals("Stadt", dto.getStadt());
//        assertEquals("Land", dto.getLand());
//    }
//}