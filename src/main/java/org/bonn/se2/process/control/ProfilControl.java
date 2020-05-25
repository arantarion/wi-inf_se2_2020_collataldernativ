/*
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer
 *
 *
 */

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

public class ProfilControl {

    public static void companyProfile() {
        User user = SessionFunctions.getCurrentUser();

        if (user == null) {
            Notification notification = new Notification("User nicht gefunden", Notification.Type.ERROR_MESSAGE);
            notification.setPosition(Position.BOTTOM_CENTER);
            notification.setDelayMsec(4000);
            notification.show(Page.getCurrent());
        }

        try {
            if (ProfilView.getMyProfile()) {
                CompanyDAO companyDAO = new CompanyDAO();
                Company company = companyDAO.retrieve(user.getUsername());
                ProfilView.setCompany(company);
            }
            UIFunctions.gotoProfile();
        } catch (DatabaseException e) {
            e.printStackTrace();
            Notification notification = new Notification("Unternehmen nicht gefunden", Notification.Type.ERROR_MESSAGE);
            notification.setPosition(Position.BOTTOM_CENTER);
            notification.setDelayMsec(4000);
            notification.show(Page.getCurrent());
        }

    }

    public static void studentProfile() {
        User user = SessionFunctions.getCurrentUser();
        if (user == null) {
            Notification notification = new Notification("User nicht gefunden", Notification.Type.ERROR_MESSAGE);
            notification.setPosition(Position.BOTTOM_CENTER);
            notification.setDelayMsec(4000);
            notification.show(Page.getCurrent());
        }

        try {
            if (ProfilView.getMyProfile()) {
                StudentDAO studentDAO = new StudentDAO();
                System.out.println(user);
                Student student = studentDAO.retrieve(user.getEmail());
                ProfilView.setStudent(student);
            }
            UIFunctions.gotoProfile();
        } catch (Exception e) {
            e.printStackTrace();
            Notification notification = new Notification("Student nicht gefunden", Notification.Type.ERROR_MESSAGE);
            notification.setPosition(Position.BOTTOM_CENTER);
            notification.setDelayMsec(4000);
            notification.show(Page.getCurrent());
        }

    }

}
