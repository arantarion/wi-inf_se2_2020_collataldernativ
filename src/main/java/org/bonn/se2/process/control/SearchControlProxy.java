package org.bonn.se2.process.control;

import java.util.List;

import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;

public class SearchControlProxy implements SearchControl{
	
	private static SearchControl search;
	
private SearchControlProxy() {		
	}
	
	
	
	public static SearchControlReal getInstance() {
		if (search == null) {
			search = new SearchControlReal();
		}
		return (SearchControlReal) search;
	}
	
	public List<Student> getAllStudents(){
		return SearchControlReal.getInstance().getAllStudents();
	}
	
	public List<Student> getStudentsInput(String attribute){
		return SearchControlReal.getInstance().getStudentsInput(attribute);
	}
	
	public List<JobOffer> getAllOffers() {
		return SearchControlReal.getInstance().getAllOffers();
		
	}
	
	public List<JobOffer> getOffersInput(String attribute){
		return SearchControlReal.getInstance().getOffersInput(attribute);
	}
	
	public int getCompanyID() {
		return SearchControlReal.getInstance().getCompanyID();
	}
	
	public String getCompanyName(int id) {
		return SearchControlReal.getInstance().getCompanyName(id);
	}
}
