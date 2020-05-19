package org.bonn.se2.test.model.dao;

import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.test.builder.CompanyBuilder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDAOTest {

    static Company company;
    static Company newCompany;
    static Company updateCompany;

    static CompanyDAO companyDAO;

    static int companyID;

    void test_a_company() {

        assertNotEquals(0, company.getcompanyID());

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
    void create() {
        try {
            company = companyDAO.create(newCompany);
            assertNotEquals(0, company.getcompanyID());
            companyID = company.getcompanyID();

            assertEquals("Der GmbH GmbH", company.getName());
            assertEquals("Test Test Test", company.getBeschreibung());
            assertEquals("test.de", company.getWebURL());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void retrieve() {
        try {
            company = companyDAO.retrieve(companyID);

        } catch (Exception e) {
            fail();
        }

    }
}