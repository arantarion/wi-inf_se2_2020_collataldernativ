package org.bonn.se2.test.model.dao;

import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Student;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class StudentDAOTest {

    @Test
    public void retrieveAll() {
    }

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