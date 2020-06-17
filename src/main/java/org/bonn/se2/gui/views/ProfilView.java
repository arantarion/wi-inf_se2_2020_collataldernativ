package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
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
    public GridLayout layout;

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
            try {
                this.setUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setUp() throws Exception {

        //this.setSizeFull();

        layout = new GridLayout(1, 3); //
        layout.setSpacing(true);
        layout.setSizeFull();
        this.addComponent(layout);

        layout.setColumnExpandRatio(0, 0.5f);

        NavigationBar navigationBar = new NavigationBar();
        //navigationBar.setHeight("100%");
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

    private void setUpCompany() throws Exception {

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


//        Button startseiteButton = new Button("Startseite", VaadinIcons.HOME);
//        Button logoutButton = new Button("Logout", VaadinIcons.SIGN_OUT);
//        Button kverwaltenButton = new Button("Kontoverwaltung", VaadinIcons.PENCIL);
//        //Button abbruchButton = new Button("Abbruch", FontAwesome.ARROW_CIRCLE_O_RIGHT);
//        //Button speichernButton = new Button("Speichern", FontAwesome.ARROW_CIRCLE_O_RIGHT);
//
//
//        HorizontalLayout h = new HorizontalLayout();
//        addComponent(h);
//        setComponentAlignment(h, Alignment.TOP_LEFT);
//        h.addComponent(startseiteButton);
//
//        HorizontalLayout h1 = new HorizontalLayout();
//        addComponent(h1);
//        setComponentAlignment(h1, Alignment.TOP_RIGHT);
//        h1.addComponent(logoutButton);
//        h1.addComponent(kverwaltenButton);
//
//        //Grid<User> grid = new Grid<User>(User.class);
//        Grid<Student> grid = new Grid<>();
//        Student liste = new StudentDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID());
//        grid.setItems(liste);
//        grid.addColumn(Student::getVorname).setCaption("Vorname");
//        grid.addColumn(Student::getNachname).setCaption("Nachname");
//        grid.addColumn(Student::getStudienfach).setCaption("Studienfach");
//        grid.addColumn(Student::getFachsemester).setCaption("Fachsemester");
//        grid.addColumn(Student::getGeburtstag).setCaption("Geburtstag");
//        grid.addColumn(Student::getJob).setCaption("Job");
//        grid.addColumn(Student::getArbeitgeber).setCaption("Arbeitgeber");
//        grid.addColumn(Student::getEmail).setCaption("Email");
//        grid.setSizeFull();
//        grid.setHeightMode(HeightMode.UNDEFINED);
//        addComponent(grid);
//
//        HorizontalLayout h2 = new HorizontalLayout();
//        addComponent(h2);
//        setComponentAlignment(h2, Alignment.BOTTOM_LEFT);
//        Panel panel = new Panel("Offene Bewerbungen");
//        panel.setSizeUndefined();
//        panel.setWidth("300px");
//        panel.setHeight("300px");
//        h2.addComponent(panel);
//        //setComponentAlignment(panel, Alignment.BOTTOM_LEFT);
//        FormLayout content = new FormLayout();
//        content.setSizeUndefined();
//        content.setMargin(true);
//        panel.setContent(content);
//
//        HorizontalLayout h3 = new HorizontalLayout();
//        addComponent(h3);
//        setComponentAlignment(h3, Alignment.BOTTOM_RIGHT);
//        //h3.addComponent(abbruchButton);
//        //h3.addComponent(speichernButton);
//
//        startseiteButton.addClickListener(e -> {
//            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
//        });
//
//        logoutButton.addClickListener(e -> {
//            LoginControl.logoutUser();
//        });
//
//        kverwaltenButton.addClickListener(e -> {
//            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.KVERWALTUNG);
//        });

