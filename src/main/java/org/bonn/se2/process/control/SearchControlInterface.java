package org.bonn.se2.process.control;

import java.util.List;

import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;

public interface SearchControlInterface {
	static SearchControl getInstance() {
		return null;
	}
	
	List<Student> getAllStudents();
	List<Student> getStudentsInput(String attribute);
	
	List<JobOffer> getAllOffers();
	List<JobOffer> getOffersInput(String attribute);

}
