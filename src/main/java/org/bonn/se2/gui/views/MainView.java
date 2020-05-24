package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.*;
import org.bonn.se2.gui.ui.MyUI;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.bonn.se2.process.control.LoginControl.getRole;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer
 */

public class MainView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        if (!SessionFunctions.isLoggedIn()) {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
        } else {
            try {
                this.setUp();
            } catch (DatabaseException | InvalidCredentialsException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUp() throws DatabaseException, InvalidCredentialsException {
        //final VerticalLayout layout = new VerticalLayout();
        //Erzeugung der HorizontalLayouts
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        HorizontalLayout h2 = new HorizontalLayout();
        HorizontalLayout h3 = new HorizontalLayout();

        //Erzeugung der Variablen
        Button profilButton = new Button("Profil", FontAwesome.AUTOMOBILE);
        Button logoutButton = new Button("Logout", FontAwesome.COFFEE);
        Button suche = new Button("Suchen", FontAwesome.SEARCH);
        TextField name = new TextField();
        Label label = new Label("Bitte geben Sie ein Stichwort ein:");
        Label username = new Label((SessionFunctions.getCurrentUser()).getUsername());
        Label role = new Label(SessionFunctions.getCurrentRole());

        //Kalender
        InlineDateTimeField sample = new InlineDateTimeField();
        sample.setValue(LocalDateTime.now());
        sample.setLocale(Locale.GERMANY);
        sample.setResolution(DateTimeResolution.MINUTE);

        Label labelText = new Label("Willkommen auf Coll@Aldernativ! der zentralen Schnittstelle zwischen Studenten & Unternehmen."
                + " Hier findet jeder seinen Traumjob.");


        addComponent(labelText);
        setComponentAlignment(labelText, Alignment.MIDDLE_CENTER);
        addComponent(horizontalLayout);
        setComponentAlignment(horizontalLayout, Alignment.TOP_RIGHT);

        //Links oben
        addComponent(h2);
        setComponentAlignment(h2, Alignment.TOP_LEFT);

        //Mitte
        addComponent(horizontalLayout1);
        setComponentAlignment(horizontalLayout1, Alignment.MIDDLE_CENTER);
        horizontalLayout1.addComponent(label);
        horizontalLayout1.addComponent(name);
        horizontalLayout1.addComponent(new Label("&nbsp", ContentMode.HTML)); // Label erstellt, um textfeld und Button zu trennen (Abstand größer ist)
        horizontalLayout1.addComponent(suche);

        //Rechts oben
        horizontalLayout.addComponent(role);
        horizontalLayout.addComponent(username);
        horizontalLayout.addComponent(profilButton);
        horizontalLayout.addComponent(logoutButton);
        horizontalLayout.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);

        //Mitte rechts
        addComponent(h3);
        setComponentAlignment(h3, Alignment.MIDDLE_RIGHT);
        h3.addComponent(sample);

        suche.addClickListener(e -> {
            if (!name.getValue().equals("")) {
                addComponent(new Label(" erfolgreiche Eingabe! Suche wird gestartet"));
            } else {
                addComponent(new Label("Geben Sie etwas ein, damit die Suche gestartet werden kann"));
            }
        });


        profilButton.addClickListener(e -> {
            if((SessionFunctions.getCurrentRole()).equals("student")){
                UI.getCurrent().getNavigator().navigateTo(Configuration.Views.PROFIL);
            }
            if((SessionFunctions.getCurrentRole()).equals("company")){
                UI.getCurrent().getNavigator().navigateTo(Configuration.Views.COMPPROFIL);
            }

        });

        logoutButton.addClickListener(e -> {
            LoginControl.logoutUser();
        });


    }
}
