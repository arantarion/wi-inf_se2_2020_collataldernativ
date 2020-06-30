package org.bonn.se2.process.control;

import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;

import java.util.List;

public interface SearchControlInterface {

    static SearchControlInterface getInstance() {
        return null;
    }

    List<Student> getAllStudents();

    List<Student> getStudentsInput(String attribute);

    List<JobOffer> getAllOffers();

    List<JobOffer> getOffersInput(String attribute);

}
