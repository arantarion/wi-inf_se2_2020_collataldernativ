package org.bonn.se2.test.model.dao;

import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.test.builder.CompanyBuilder;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyDAOTest2 {

    static Company company;
    static Company newCompany;
    static Company updateCompany;

    static CompanyDAO companyDAO;

    static int companyID;

    void test_a_company(Company comp) {
        assertNotEquals(0, comp.getcompanyID());
        assertEquals("Der GmbH GmbH", comp.getName());
        assertEquals("Test Test Test", comp.getBeschreibung());
        assertEquals("test.de", comp.getWebURL());
    }

    @BeforeAll
    static void beforeAll() {
        try {
            companyDAO = new CompanyDAO();
        } catch (Exception e) {
            fail();
        }
        newCompany = CompanyBuilder.getInstance().createDefaultCompany().done();
    }

    @BeforeEach
    void beforeEach() {
        company = null;
        updateCompany = null;
    }

    @Test
    @Order(1)
    void retrieveAll() {
        List<Company> companies = new ArrayList<>();

        try {
            companies = new CompanyDAO().retrieveAll();
        } catch (Exception e) {
            fail();
        }
        assertTrue(companies.size() > 0);
    }

    @Test
    @Order(2)
    void create() {
        try {
            company = companyDAO.create(newCompany);
            assertNotEquals(0, company.getcompanyID());
            companyID = company.getcompanyID();

            assertEquals("Der GmbH GmbH", company.getName());
            assertEquals("Test Test Test", company.getBeschreibung());
            assertEquals("test.de", company.getWebURL());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(3)
    void retrieve() {
        try {
            company = companyDAO.retrieve(companyID);
            test_a_company(company);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @Order(4)
    void update() {
        try {
            company = companyDAO.retrieve(companyID);
            company.setName("Neue Test GmbH");
            company.setBeschreibung("HIER KÖNNTE IHRE BESCHREIBUNG STEHEN");
            company.setWebURL("somethingfunny.com");

            updateCompany = companyDAO.update(company);

            assertEquals(companyID, updateCompany.getcompanyID());
            assertEquals(company.getName(), "Neue Test GmbH");
            assertEquals(company.getBeschreibung(), "HIER KÖNNTE IHRE BESCHREIBUNG STEHEN");
            assertEquals(company.getWebURL(), "somethingfunny.com");

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @Order(5)
    void delete() {
        try {
            company = companyDAO.retrieve(companyID);
            companyDAO.delete(company);
            assertThrows(Exception.class, () -> companyDAO.retrieve(companyID));
        } catch (Exception e) {
            fail();
        }
    }
}