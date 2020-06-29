package org.bonn.se2.process.control;

import java.util.List;

import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;

public class SearchControlProxy implements SearchControlInterface {
	
private SearchControlProxy() {
		
	}
	
	protected static SearchControlProxy  search = null;
	
	public static SearchControlProxy getInstance() {
		if (search == null) {
			search = new SearchControlProxy();
		}
		return search;
	}
	
	public List<Student> getAllStudents(){
		return SearchControl.getInstance().getAllStudents();
	}
	
	public List<Student> getStudentsInput(String attribute){
		return SearchControl.getInstance().getStudentsInput(attribute);
	}
	
	public List<JobOffer> getAllOffers() {
		return SearchControl.getInstance().getAllOffers();
		
	}
	
	public List<JobOffer> getOffersInput(String attribute){
		return SearchControl.getInstance().getOffersInput(attribute);
	}
	
	public int getCompanyID() {
		return SearchControl.getInstance().getCompanyID();
	}
	
	public String getCompanyName(int id) {
		return SearchControl.getInstance().getCompanyName(id);
	}
}
