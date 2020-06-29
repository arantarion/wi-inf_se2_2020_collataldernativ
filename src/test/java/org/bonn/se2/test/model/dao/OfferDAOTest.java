package org.bonn.se2.test.model.dao;

import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
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
public class OfferDAOTest {

    static OfferDAO offerDAO;
    static JobOffer newOffer;
    static int offerID;

    JobOffer testOffer;

    @BeforeAll
    static void beforeAll() {
        try {
            offerDAO = new OfferDAO();
        } catch (DatabaseException e) {
            fail();
        }
        newOffer = new JobOffer("Informatik", "Herr Weckermann", "Wir suchen einen Software-Tester", "Tester gesucht", "1400€", LocalDate.now());
        newOffer.setCompanyID(46);
    }

    void testAnOffer(JobOffer myOffer) {
        assertNotEquals(0, myOffer.getJobofferID());
        assertEquals("Informatik", myOffer.getBereich());
        assertEquals("Herr Weckermann", myOffer.getKontakt());
        assertEquals("Tester gesucht", myOffer.getName());
        assertEquals("1400€", myOffer.getGehalt());
    }

    @BeforeEach
    void beforeEach() {
        testOffer = null;
    }


    @Order(1)
    @Test
    public void retrieveAll() {
        List<JobOffer> jobOffers = new ArrayList<>();

        try {
            jobOffers = offerDAO.retrieveAll();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(jobOffers.size() > 0);
    }

    @Order(2)
    @Test
    public void create() {
        try {
            testOffer = offerDAO.create(newOffer);
            assertNotEquals(0, testOffer.getJobofferID());
            offerID = testOffer.getJobofferID();
            testAnOffer(testOffer);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(3)
    public void retrieve() {
        try {
            testOffer = offerDAO.retrieve(offerID);
            testAnOffer(testOffer);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @Order(4)
    public void retrieveCompanyOffers() {
        List<JobOffer> jobOffersCompany = new ArrayList<>();

        try {
            jobOffersCompany = offerDAO.retrieveCompanyOffers(46);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(jobOffersCompany.size() > 0);
    }

    @Test
    @Order(5)
    public void retrieveCompanyOffersAttribute() {
        List<JobOffer> jobOfferAttribute = new ArrayList<>();

        try {
            jobOfferAttribute = offerDAO.retrieveCompanyOffers("Informatik");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(jobOfferAttribute.size() > 0);
    }

    @Test
    @Order(6)
    public void delete() {
        try {
            testOffer = offerDAO.retrieve(offerID);
            offerDAO.delete(testOffer);
            assertThrows(InvalidCredentialsException.class, () -> offerDAO.retrieve(offerID));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}