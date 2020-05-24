package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import java.util.List;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer
 */

public class ProfilView extends VerticalLayout implements View {


    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws DatabaseException {

        Button startseiteButton = new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button logoutButton = new Button("Logout", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button kverwaltenButton = new Button("Kontoverwaltung", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        //Button abbruchButton = new Button("Abbruch", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        //Button speichernButton = new Button("Speichern", FontAwesome.ARROW_CIRCLE_O_RIGHT);


        HorizontalLayout h = new HorizontalLayout();
        addComponent(h);
        setComponentAlignment(h, Alignment.TOP_LEFT);
        h.addComponent(startseiteButton);

        HorizontalLayout h1 = new HorizontalLayout();
        addComponent(h1);
        setComponentAlignment(h1, Alignment.TOP_RIGHT);
        h1.addComponent(logoutButton);
        h1.addComponent(kverwaltenButton);

        //Grid<User> grid = new Grid<User>(User.class);
        Grid<Student> grid = new Grid<>();
        Student liste =  new StudentDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID());
        grid.setItems(liste);
        grid.addColumn(Student::getVorname).setCaption("Vorname");
        grid.addColumn(Student::getNachname).setCaption("Nachname");
        grid.addColumn(Student::getStudienfach).setCaption("Studienfach");
        grid.addColumn(Student::getFachsemester).setCaption("Fachsemester");
        grid.addColumn(Student::getGeburtstag).setCaption("Geburtstag");
        grid.addColumn(Student::getJob).setCaption("Job");
        grid.addColumn(Student::getArbeitgeber).setCaption("Arbeitgeber");
        grid.addColumn(Student::getEmail).setCaption("Email");
        grid.setSizeFull();
        grid.setHeightMode(HeightMode.UNDEFINED);
        addComponent(grid);

        HorizontalLayout h2 = new HorizontalLayout();
        addComponent(h2);
        setComponentAlignment(h2, Alignment.BOTTOM_LEFT);
        Panel panel = new Panel("Offene Bewerbungen");
        panel.setSizeUndefined();
        panel.setWidth("300px");
        panel.setHeight("300px");
        h2.addComponent(panel);
        //setComponentAlignment(panel, Alignment.BOTTOM_LEFT);
        FormLayout content = new FormLayout();
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);

        HorizontalLayout h3 = new HorizontalLayout();
        addComponent(h3);
        setComponentAlignment(h3, Alignment.BOTTOM_RIGHT);
        //h3.addComponent(abbruchButton);
        //h3.addComponent(speichernButton);

        startseiteButton.addClickListener(e ->{
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
        });

        logoutButton.addClickListener(e -> {
            LoginControl.logoutUser();
        });

        kverwaltenButton.addClickListener(e -> {
           UI.getCurrent().getNavigator().navigateTo(Configuration.Views.KVERWALTUNG);
        });


    }

}
