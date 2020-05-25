package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer
 */

public class BMWView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setUp() throws DatabaseException, SQLException {
        Button startseiteButton = new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button logoutButton = new Button("Logout", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button kverwaltenButton = new Button("Kontoverwaltung", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button jobofferButton = new Button("Neue Stellenanzeige erstellen");
        //Button loginButton = new Button("Login", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        //Button registrieren = new Button("Registrierung", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        //Button suche = new Button("Suchen", FontAwesome.SEARCH);
        //Button eb = new Button("Email Bewerbung", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        //Button z = new Button("Zurück", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        //final TextField textField = new TextField();

        HorizontalLayout h = new HorizontalLayout();
        addComponent(h);
        setComponentAlignment(h, Alignment.TOP_LEFT);
        h.addComponent(startseiteButton);

        HorizontalLayout h2 = new HorizontalLayout();
        addComponent(h2);
        setComponentAlignment(h2, Alignment.TOP_RIGHT);
        h2.addComponent(logoutButton);
        h2.addComponent(kverwaltenButton);
        //h2.addComponent(log);
        //h2.addComponent(registrieren);


        HorizontalLayout h4 = new HorizontalLayout();
        addComponent(h4);
        setComponentAlignment(h4, Alignment.MIDDLE_LEFT);
        Panel panel = new Panel("Stellenanzeigen:");
        panel.setSizeUndefined();
        panel.setWidth("300px");
        panel.setHeight("300px");
        h4.addComponent(panel);
        FormLayout content = new FormLayout();
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);

        HorizontalLayout h3 = new HorizontalLayout();
        addComponent(h3);
        setComponentAlignment(h3, Alignment.BOTTOM_LEFT);
        h3.addComponent(jobofferButton);


        Company comp =  new CompanyDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID());
        int ID = comp.getcompanyID();

//        Grid<Student> grid = new Grid<>();
//        List<JobOffer> liste =  new OfferDAO().retrieveCompanyOffers(ID);
//        System.out.println(liste);
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



        startseiteButton.addClickListener(e ->{
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
        });

        logoutButton.addClickListener(e -> {
            LoginControl.logoutUser();
        });

        kverwaltenButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.KVERWALTUNG);
        });

        jobofferButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.OFFERCREATION);
        });
    }
}
