package org.bonn.se2.gui.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.bonn.se2.gui.components.NavigationBar;
import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bonn.se2.services.util.CryptoFunctions.hash;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Anton Drees
 */

public class KontoverwaltungView extends VerticalLayout implements View {
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
    }

    public void setUp() {

        NavigationBar navigationBar = new NavigationBar();
        this.addComponent(navigationBar);
        this.setComponentAlignment(navigationBar, Alignment.TOP_CENTER);

        //Button startseiteButton = new Button("Startseite", VaadinIcons.ARROW_CIRCLE_RIGHT);
        //Button log = new Button("Logout", VaadinIcons.ARROW_CIRCLE_RIGHT);
        Button konto = new Button("Konto löschen", VaadinIcons.ARROW_CIRCLE_RIGHT);
        Button s = new Button("Speichern", VaadinIcons.ARROW_CIRCLE_RIGHT);


        PasswordField pwAlt;
        PasswordField pwNeu;
        PasswordField pwNeu2;

        CheckBox j = new CheckBox("regelmäßig Emails über neue Angebote bekommen");
        CheckBox n = new CheckBox("Alle Benachrichtigungen ausschalten");

        HorizontalLayout h = new HorizontalLayout();
        HorizontalLayout h1 = new HorizontalLayout();
        HorizontalLayout h2 = new HorizontalLayout();

        addComponent(h);
        setComponentAlignment(h, Alignment.TOP_LEFT);
        //h.addComponent(startseiteButton);

        addComponent(h1);
        setComponentAlignment(h1, Alignment.MIDDLE_LEFT);
        j.setValue(false);
        n.setValue(false);
        addComponent(j);
        addComponent(n);

        Panel panel = new Panel("Passwort ändern");
        panel.setSizeUndefined();
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        FormLayout content = new FormLayout();
        content.addComponent(pwAlt = new PasswordField("Altes Passwort:"));
        content.addComponent(pwNeu = new PasswordField("Neues Passwort:"));
        content.addComponent(pwNeu2 = new PasswordField("Neues Passwort wiederholen"));
        content.setSizeUndefined();

        content.setMargin(true);
        panel.setContent(content);

        addComponent(h1);
        setComponentAlignment(h1, Alignment.BOTTOM_LEFT);
        h1.addComponent(konto);

        addComponent(h2);
        setComponentAlignment(h2, Alignment.BOTTOM_RIGHT);
        h2.addComponent(s);

        s.addClickListener(e -> {
            String passwort = null;
            try {
                passwort = (new UserDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID())).getPasswort();
            } catch (DatabaseException | InvalidCredentialsException ex) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        new Throwable().getStackTrace()[0].getMethodName() + " failed", ex);
            }
            if ((!pwAlt.getValue().equals("")) && (!pwNeu.getValue().equals("")) && (!pwNeu2.getValue().equals("")) && pwNeu.getValue().equals(pwNeu2.getValue()) && hash(pwAlt.getValue()).equals(passwort)) {
                addComponent(new Label("Das Passwort wurde erfolgreich geändert."));
                //addComponent(startseiteButton);
            } else {
                addComponent(new Label("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe"));
            }

        });

        konto.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.DELETION);
        });

//        startseiteButton.addClickListener(e ->{
//            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
//        });
    }
}
