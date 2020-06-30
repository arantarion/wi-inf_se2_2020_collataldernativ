package org.bonn.se2.process.control;

import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.SessionFunctions;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchControl implements SearchControlInterface {

    private static SearchControlInterface search;

    SearchControl() {

    }

    protected static SearchControl getInstance() {
        if (search == null) {
            search = new SearchControl();
        }
        return (SearchControl) search;
    }

    public List<Student> getAllStudents() {

        try {
            return StudentDAO.getInstance().retrieveAll();
        } catch (DatabaseException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
        return null;
    }

    public List<Student> getStudentsInput(String attribute) {

        try {
            return StudentDAO.getInstance().retrieveStudents(attribute);
        } catch (SQLException | DatabaseException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
        return null;
    }

    public List<JobOffer> getAllOffers() {
        try {
            return new OfferDAO().retrieveAll();
        } catch (DatabaseException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
        return null;

    }

    public List<JobOffer> getOffersInput(String attribute) {
        try {
            return new OfferDAO().retrieveCompanyOffers(attribute);
        } catch (DatabaseException | SQLException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
        return null;
    }

    public int getCompanyID() {
        try {
            return new CompanyDAO().retrieve(SessionFunctions.getCurrentUser().getUsername()).getcompanyID();
        } catch (DatabaseException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
        return 0;
    }

    public String getCompanyName(int id) {
        try {
            return new CompanyDAO().retrieveCompany(id).getName();
        } catch (DatabaseException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
        return null;
    }
}
