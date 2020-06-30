package org.bonn.se2.test.model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.sql.ResultSet;

import org.bonn.se2.model.dao.BewerbungsDAO;
import org.bonn.se2.model.objects.dto.Bewerbung;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
@author Coll@Aldernativ
@version 0.1a
@Programmer Marceli Dziadek
*/

class BewerbungDAOTest {
	
	static Bewerbung bewerbung;
	static ResultSet resultset;
	static BewerbungsDAO dao;
	static List<Bewerbung> listeBewerbung;
	static List<Bewerbung> listeBewerbung2;
	static int bewerbungID;
	static int companyID;
	static int jobOfferID;
	static int size;
	static int studentID;
	
	@BeforeAll
	static void beforeAll() {
		try {
			dao = new BewerbungsDAO();
			bewerbungID = dao.retrieveAll().get(0).getBewerbungsID();
			companyID = dao.retrieveAll().get(0).getCompanyID();
			jobOfferID = dao.retrieveAll().get(0).getJobofferID();
			studentID = dao.retrieveAll().get(0).getStudentID();
		} catch (DatabaseException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		
	}
	
	@BeforeEach
	void beforeEach() {
		try {
			size = dao.retrieveAll().size();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void Testretrieve() {
		try {
			bewerbung = dao.retrieve(bewerbungID);
		} catch (Exception e) {
			fail();
		}
		assertEquals(bewerbung.getBewerbungsID(),bewerbungID);
		assertEquals(bewerbung.getJobofferID(), jobOfferID);
		assertEquals(bewerbung.getCompanyID(), companyID);
		
		assertNotNull(bewerbung.getBewerbungsdatum());
		assertNotNull(bewerbung.getNotes());
	}
	
	@Test
	void TestretrieveCompanyBewerbungJobOffer() {
		try {
			listeBewerbung = dao.retrieveCompanyBewerbungJobOffer(companyID, jobOfferID);
			size = listeBewerbung.size();
		} catch (DatabaseException e) {
			fail();
		} catch (SQLException e) {
			fail();
		}
		assertNotNull(listeBewerbung);
		assertFalse(listeBewerbung.isEmpty());
		
		assertEquals(listeBewerbung.get((int)Math.random()*size).getCompanyID(),companyID);
		assertEquals(listeBewerbung.get((int)Math.random()*size).getCompanyID(),companyID);
		assertEquals(listeBewerbung.get((int)Math.random()*size).getCompanyID(),companyID);
		
		assertEquals(listeBewerbung.get((int)Math.random()*size).getJobofferID(),jobOfferID);
		assertEquals(listeBewerbung.get((int)Math.random()*size).getJobofferID(),jobOfferID);
		assertEquals(listeBewerbung.get((int)Math.random()*size).getJobofferID(),jobOfferID);
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsID());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsdatum());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsdatum());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsdatum());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getStudentID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getStudentID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getStudentID());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getNotes());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getNotes());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getNotes());
	}
	
	@Test
	void TestretrieveCompanyBewerbung() {
		try {
			listeBewerbung = dao.retrieveCompanyBewerbung(companyID);
			size = listeBewerbung.size();
		} catch (DatabaseException e) {
			fail();
		} catch (SQLException e) {
			fail();
		}
		assertNotNull(listeBewerbung);
		assertFalse(listeBewerbung.isEmpty());
		
		assertEquals(listeBewerbung.get((int)Math.random()*size).getCompanyID(),companyID);
		assertEquals(listeBewerbung.get((int)Math.random()*size).getCompanyID(),companyID);
		assertEquals(listeBewerbung.get((int)Math.random()*size).getCompanyID(),companyID);
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getJobofferID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getJobofferID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getJobofferID());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsID());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsdatum());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsdatum());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsdatum());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getStudentID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getStudentID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getStudentID());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getNotes());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getNotes());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getNotes());
	}
	
	@Test
	void TestretrieveAll() {
		try {
			listeBewerbung = dao.retrieveAll();
			size = listeBewerbung.size();
		} catch (Exception e) {
			fail();
		}
		assertNotNull(listeBewerbung);
		assertFalse(listeBewerbung.isEmpty());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getCompanyID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getCompanyID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getCompanyID());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getJobofferID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getJobofferID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getJobofferID());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsID());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsdatum());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsdatum());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getBewerbungsdatum());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getStudentID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getStudentID());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getStudentID());
		
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getNotes());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getNotes());
		assertNotNull(listeBewerbung.get((int)Math.random()*size).getNotes());
	}
	
	@Test
	void Testcreate() {
		bewerbung = new Bewerbung();
		bewerbung.setCompanyID(companyID);
		bewerbung.setJobofferID(jobOfferID);
		bewerbung.setBewerbungsID(0);
		bewerbung.setNotes("");
		bewerbung.setStudentID(studentID);
		bewerbung.setBewerbungsdatum(LocalDate.now());
        try {
        	listeBewerbung = dao.retrieveAll();
        	new BewerbungsDAO().create(bewerbung);
        	listeBewerbung2 = dao.retrieveAll();
		} catch (Exception e) {
			fail();
		}
        assertNotEquals(listeBewerbung.size(), listeBewerbung2.size());
    	assertEquals(listeBewerbung.size(), listeBewerbung2.size()-1);
	}
	
	@Test
	void Testdelete() {
		//TODO jeder 2. fall fehlerhaft
		bewerbung = new Bewerbung();
		try {
			bewerbung.setBewerbungsID(dao.retrieveAll().get(size-1).getBewerbungsID());
			listeBewerbung = dao.retrieveAll();
			dao.delete(bewerbung);
			listeBewerbung2 = dao.retrieveAll();
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotEquals(listeBewerbung.size(), listeBewerbung2.size());
    	assertEquals(listeBewerbung.size(), listeBewerbung2.size()+1);
			
	}
	
	@Test
	void Testdelete2() {
		try {
			bewerbungID = dao.retrieveAll().get(size-1).getBewerbungsID();
			System.out.println(bewerbungID);
			listeBewerbung = dao.retrieveAll();
			dao.delete(bewerbungID);
			listeBewerbung2 = dao.retrieveAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotEquals(listeBewerbung.size(), listeBewerbung2.size());
    	assertEquals(listeBewerbung.size(), listeBewerbung2.size()+1);
	}

}
