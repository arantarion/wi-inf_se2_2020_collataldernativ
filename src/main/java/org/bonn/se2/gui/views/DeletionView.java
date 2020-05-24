package org.bonn.se2.gui.views;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import static org.bonn.se2.services.util.CryptoFunctions.hash;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class DeletionView extends VerticalLayout implements View {

    int ID;

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (InvalidCredentialsException e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws DatabaseException, InvalidCredentialsException{

        this.setSizeFull();

        HorizontalLayout layout = new HorizontalLayout();

        Panel panel = new Panel("Wollen Sie ihren Account wirklich löschen?");
        this.addComponent(panel);
        this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        panel.setContent(layout);

        Button yesButton = new Button("Ja");
        layout.addComponent(yesButton);
        layout.setComponentAlignment(yesButton, Alignment.MIDDLE_LEFT);
        yesButton.setWidth("150px");
        Button noButton = new Button("Nein");
        layout.addComponent(noButton);
        layout.setComponentAlignment(noButton, Alignment.MIDDLE_RIGHT);
        noButton.setWidth("150px");


        panel.setSizeUndefined();

        noButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.KVERWALTUNG);
        });

        yesButton.addClickListener(e -> {
            layout.setVisible(false);
            panel.setVisible(false);
            this.setSizeFull();

            final PasswordField passwd = new PasswordField();
            passwd.setCaption("Passwort:");
            passwd.setPlaceholder("Passwort");

            VerticalLayout layout2 = new VerticalLayout();
            layout2.addComponent(passwd);
            layout2.setComponentAlignment(passwd, Alignment.MIDDLE_CENTER);

            Panel panel2 = new Panel("Zur Bestätigung bitte ihr Passwort eingeben.");

            this.addComponent(panel2);
            this.setComponentAlignment(panel2, Alignment.MIDDLE_CENTER);
            panel2.setContent(layout2);

            HorizontalLayout layout3 = new HorizontalLayout();

            Button bestaetigungButton = new Button("Bestätigen");
            layout3.addComponent(bestaetigungButton);
            layout3.setComponentAlignment(bestaetigungButton, Alignment.MIDDLE_LEFT);
            bestaetigungButton.setWidth("150px");
            Button abbruchButton = new Button("Abbruch");
            layout3.addComponent(abbruchButton);
            layout3.setComponentAlignment(abbruchButton, Alignment.MIDDLE_RIGHT);
            abbruchButton.setWidth("150px");

            layout2.addComponent(layout3);

            panel2.setSizeUndefined();

            abbruchButton.addClickListener(d -> {
                UI.getCurrent().getNavigator().navigateTo(Configuration.Views.KVERWALTUNG);
            });

            bestaetigungButton.addClickListener(f ->{
                String passwort = null;
                try {
                    passwort = (new UserDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID())).getPasswort();
                } catch (DatabaseException databaseException) {
                    databaseException.printStackTrace();
                } catch (InvalidCredentialsException invalidCredentialsException) {
                    invalidCredentialsException.printStackTrace();
                }
                if(hash(passwd.getValue()).equals(passwort)){
                    try {
                        ID = (new UserDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID())).getUserID();
                    } catch (DatabaseException databaseException) {
                        databaseException.printStackTrace();
                    } catch (InvalidCredentialsException invalidCredentialsException) {
                        invalidCredentialsException.printStackTrace();
                    }
                    if(SessionFunctions.getCurrentRole() == "student"){
                        try {
                            new StudentDAO().delete(ID);
                            new UserDAO().delete(ID);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        LoginControl.logoutUser();
                    }
                    if(SessionFunctions.getCurrentRole() == "company"){
                        try {
                            new CompanyDAO().delete(ID);
                            new UserDAO().delete(ID);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        LoginControl.logoutUser();
                    }
                }
            });
        });

    }
}
