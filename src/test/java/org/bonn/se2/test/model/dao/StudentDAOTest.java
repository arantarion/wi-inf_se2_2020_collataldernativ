package org.bonn.se2.test.model.dao;

import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentDAOTest {

    static Student newStudent;
    static StudentDAO studentDAO;
    Student testStudent;
    Student updateStudent;

    @BeforeAll
    static void beforeAll() {
        try {
            studentDAO = new StudentDAO();
        } catch (DatabaseException e) {
            fail();
        }
        newStudent = new Student("Harry", "Potter", "Zauberei", "Arbeitslos", "-", LocalDate.now(), 13);
        newStudent.setUsername("hpotter");
        newStudent.setEmail("testDAO@test.de");
        newStudent.setPasswort("123456");
        newStudent.setUserID((int) (Math.random() * 2378));
    }

    @BeforeEach
    void beforeEach() {
        testStudent = null;
        updateStudent = null;
    }

    @Order(1)
    @Test
    public void retrieveAll() {
        List<Student> liste = new ArrayList<>();

        try {
            liste = studentDAO.retrieveAll();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(liste.size() > 0);
    }


    @Order(2)
    @Test
    public void retrieve() {
        Student dto = new Student();
        try {
            dto = new StudentDAO().retrieve("Zaapman");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Henry", dto.getVorname());
        assertEquals("Weckermann", dto.getNachname());
    }
}