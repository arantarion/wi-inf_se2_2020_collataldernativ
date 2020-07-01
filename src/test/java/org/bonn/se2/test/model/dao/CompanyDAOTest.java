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
public class CompanyDAOTest {

    static Company company;
    static Company newCompany;
    static Company updateCompany;

    static CompanyDAO companyDAO;

    int companyID;

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
            assertEquals(0, company.getcompanyID());
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
            company = companyDAO.retrieveCompany(42);
            assertNotNull(company);
        } catch (Exception e) {
            fail();
        }
    }

}