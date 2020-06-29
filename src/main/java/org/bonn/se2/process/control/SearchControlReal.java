package org.bonn.se2.process.control;

import java.sql.SQLException;
import java.util.List;

import org.bonn.se2.model.dao.AbstractDAO;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.dao.ToggleDAO;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.SessionFunctions;

public class SearchControlReal implements SearchControl {
	
	private static SearchControl search;
	
    SearchControlReal() {
		
	}
	
	protected static SearchControlReal getInstance() {
		if (search == null) {
			search = new SearchControlReal();
		}
		return (SearchControlReal) search;
	}
	
	public List<Student> getAllStudents(){
		
		try {
			return StudentDAO.getInstance().retrieveAll();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Student> getStudentsInput(String attribute){
		
		try {
			return StudentDAO.getInstance().retrieveStudents(attribute);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<JobOffer> getAllOffers() {
		try {
			return new OfferDAO().retrieveAll();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public List<JobOffer> getOffersInput(String attribute){
		try {
			return new OfferDAO().retrieveCompanyOffers(attribute);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getCompanyID() {
		try {
			return new CompanyDAO().retrieve(SessionFunctions.getCurrentUser().getUsername()).getcompanyID();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getCompanyName(int id) {
		try {
			return new CompanyDAO().retrieveCompany(id).getName();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
