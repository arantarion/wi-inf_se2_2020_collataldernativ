package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.bonn.se2.gui.components.AccountOverviewBody;
import org.bonn.se2.gui.components.AccountOverviewHead;
import org.bonn.se2.gui.components.NavigationBar;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;
import org.bonn.se2.services.util.UIFunctions;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class ProfilView extends VerticalLayout implements View {

    private static Student student;
    private static Company company;
    private static boolean myProfile = true;
    private GridLayout layout;

    public static Student getStudent() {
        return ProfilView.student;
    }

    public static void setStudent(Student dto) {
        ProfilView.student = dto;
    }

    public static Company getCompany() {
        return ProfilView.company;
    }

    public static void setCompany(Company dto) {
        ProfilView.company = dto;
    }

    public static boolean getMyProfile() {
        return ProfilView.myProfile;
    }

    public static void setMyProfile(boolean profile) {
        ProfilView.myProfile = profile;
    }

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!SessionFunctions.isLoggedIn()) {
            UIFunctions.gotoLogin();
        } else {
            this.setUp();
        }
    }

    public void setUp() {

        layout = new GridLayout(1, 3); //
        layout.setSpacing(true);
        layout.setSizeFull();
        this.addComponent(layout);

        layout.setColumnExpandRatio(0, 0.5f);

        NavigationBar navigationBar = new NavigationBar();

        layout.addComponent(navigationBar, 0, 0);
        layout.setComponentAlignment(navigationBar, Alignment.TOP_CENTER);

        if (SessionFunctions.getCurrentRole().equals(Configuration.Roles.STUDENT) && ProfilView.getMyProfile()) {
            setUpStudent();
        } else {
            setUpCompany();
        }
    }

    private void setUpStudent() {

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidth("100%");
        AccountOverviewHead headStudent = new AccountOverviewHead(ProfilView.getStudent());
        headerLayout.addComponent(headStudent);

        layout.addComponent(headerLayout, 0, 1);

        HorizontalLayout bodyLayout = new HorizontalLayout();
        bodyLayout.setWidth("100%");
        AccountOverviewBody bodyStudent = new AccountOverviewBody(ProfilView.getStudent());
        bodyLayout.addComponent(bodyStudent);
        layout.addComponent(bodyLayout, 0, 2);

    }

    private void setUpCompany() {

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidth("100%");
        AccountOverviewHead headCompany = new AccountOverviewHead(ProfilView.getCompany());
        headerLayout.addComponent(headCompany);
        layout.addComponent(headerLayout, 0, 1);

        HorizontalLayout bodyLayout = new HorizontalLayout();
        bodyLayout.setWidth("100%");
        AccountOverviewBody bodyCompany = new AccountOverviewBody(ProfilView.getCompany());
        bodyLayout.addComponent(bodyCompany);
        layout.addComponent(bodyLayout, 0, 2);

    }
}
