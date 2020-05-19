package org.bonn.se2.test.model.dao;

import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.test.builder.UserBuilder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    User testUser;
    User updateUser;
    static User newUser;
    static int testUserID;
    static UserDAO userDAO;

    void test_a_User(User user) {
        assertNotEquals(0, user.getUserID());
        assertEquals("SuperMuster", user.getUsername());
        assertEquals("test@test.de", user.getEmail());
        assertEquals("123456", user.getPasswort());
    }

    @BeforeAll
    static void beforeAll() {
        try {
            userDAO = new UserDAO();
        } catch (DatabaseException e) {
            fail();
        }
        newUser = new UserBuilder().createDefaultUser().done();
    }

    @BeforeEach
    void beforeEach() {
        testUser = null;
        updateUser = null;
    }

    @Test
    public void retrieveAll() {
        List<User> users = new ArrayList<>();

        try {
            users = userDAO.retrieveAll();
        } catch (Exception e) {
            fail();
        }
        assertTrue(users.size() > 0);

    }

    @Test
    public void create() {
        try {
            testUser = userDAO.create(newUser);
            assertNotEquals(0, testUser.getUserID());
            testUserID = testUser.getUserID();
            test_a_User(testUser);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void retrieve() {
        try {
            testUser = userDAO.retrieve(testUserID);
            test_a_User(testUser);
        } catch (Exception e) {
            fail();
        }
    }

//    @Test
//    void update() {
//    try {
//        testUser = userDAO.retrieve(testUserID);
//        testUser.setUsername("mein ganz ganz toller test user");
//        testUser.setEmail("blablabla@bla.de");
//        updateUser = userDAO.update(testUser);
//
//        assertEquals(testUserID, updateUser.getUserID());
//        assertEquals("mein ganz ganz toller test user", updateUser.getUsername());
//        assertEquals("blablabla@bla.de", updateUser.getEmail());
//
//    } catch (Exception e) {
//        fail();
//    }

    @Test
    public void delete() {
        try {
            testUser = userDAO.retrieve(testUserID);
            userDAO.delete(testUser);
            assertThrows(InvalidCredentialsException.class, () -> userDAO.retrieve(testUserID));

        } catch (Exception e) {
            fail();
        }
    }

}