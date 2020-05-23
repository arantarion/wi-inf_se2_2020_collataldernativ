package org.bonn.se2.process.control;


import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.time.LocalDate;

import static org.bonn.se2.services.util.CryptoFunctions.hash;

/**
 *
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer Anton Drees
 */

public class RegistrierungsControl {

    public static User generateUser(String username, String email, String password) throws Exception {
        String pw = hash(password);
        User user = new User(username,email,pw);
        return user;
    }

    public static Student generateStudent(String vorname, String nachname, String studienfach, String job, String arbeitgeber, LocalDate bday, Integer fachsemester, User user) throws Exception {
        User usertmp = new UserDAO().create(user);
        Student student = new Student(vorname, nachname, studienfach, job, arbeitgeber, bday, fachsemester, usertmp.getUserID());
        Student dto = new StudentDAO().create(student);
        return dto;
    }

    public static Company generateCompany(String name, String webURL, String beschreibung, String branche, String ansprechpartner, User user) throws Exception {
        User usertmp = new UserDAO().create(user);
        Company company = new Company(name,webURL,beschreibung,branche,ansprechpartner,usertmp.getUserID());
        Company dto = new CompanyDAO().create(company);
        return dto;
    }
}
