package org.bonn.se2.process.control;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import org.bonn.se2.gui.views.ProfilView;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.SessionFunctions;
import org.bonn.se2.services.util.UIFunctions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class ProfilControl {

    public static User getCurrentUser() {
        return SessionFunctions.getCurrentUser();
    }

    public static void companyProfile() {
        User user = getCurrentUser();

        if (user == null) {
            Notification notification = new Notification("User nicht gefunden", Notification.Type.ERROR_MESSAGE);
            notification.setPosition(Position.BOTTOM_CENTER);
            notification.setDelayMsec(4000);
            notification.show(Page.getCurrent());
        }

        try {
            if (ProfilView.getMyProfile()) {
                CompanyDAO companyDAO = new CompanyDAO();
                Company company = null;
                if (user != null) {
                    company = companyDAO.retrieve(user.getUsername());
                }
                ProfilView.setCompany(company);
            }
            UIFunctions.gotoProfile();
        } catch (DatabaseException e) {
            Logger.getLogger(String.valueOf(ProfilControl.class)).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
            Notification notification = new Notification("Unternehmen nicht gefunden", Notification.Type.ERROR_MESSAGE);
            notification.setPosition(Position.BOTTOM_CENTER);
            notification.setDelayMsec(4000);
            notification.show(Page.getCurrent());
        }

    }

    public static void studentProfile() {
        User user = getCurrentUser();
        if (user == null) {
            Notification notification = new Notification("User nicht gefunden", Notification.Type.ERROR_MESSAGE);
            notification.setPosition(Position.BOTTOM_CENTER);
            notification.setDelayMsec(4000);
            notification.show(Page.getCurrent());
        }

        try {
            if (ProfilView.getMyProfile()) {
                StudentDAO studentDAO = new StudentDAO();
                Student student = null;
                if (user != null) {
                    student = studentDAO.retrieve(user.getEmail());
                }
                ProfilView.setStudent(student);
            }
            UIFunctions.gotoProfile();
        } catch (DatabaseException e) {
            Logger.getLogger(String.valueOf(ProfilControl.class)).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
            Notification notification = new Notification("Student nicht gefunden", Notification.Type.ERROR_MESSAGE);
            notification.setPosition(Position.BOTTOM_CENTER);
            notification.setDelayMsec(4000);
            notification.show(Page.getCurrent());
        }

    }

}
