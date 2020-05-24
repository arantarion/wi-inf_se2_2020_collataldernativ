package org.bonn.se2.gui.views;

import static org.bonn.se2.services.util.CryptoFunctions.hash;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.SessionFunctions;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer
 */

public class KontoverwaltungView extends VerticalLayout implements View {
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (DatabaseException | InvalidCredentialsException e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws DatabaseException, InvalidCredentialsException {
        Button startseiteButton = new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button log = new Button("Logout", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button profil = new Button("Profil", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button konto = new Button("Konto löschen", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button z = new Button("Zurück", FontAwesome.ARROW_CIRCLE_O_LEFT);
        Button s = new Button("Speichern", FontAwesome.ARROW_CIRCLE_O_RIGHT);

        String passwort =  (new UserDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID())).getPasswort();
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
        h.addComponent(startseiteButton);

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
        h2.addComponent(z);
        h2.addComponent(s);

        s.addClickListener(e -> {
            if ((!pwAlt.getValue().equals("")) && (!pwNeu.getValue().equals("")) && (!pwNeu2.getValue().equals("")) && pwNeu.getValue().equals(pwNeu2.getValue()) && hash(pwAlt.getValue()).equals(passwort)) {
                addComponent(new Label("Das Passwort wurde erfolgreich geändert."));
                System.out.println(passwort);
                //addComponent(startseiteButton);
            } else {
                addComponent(new Label("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe"));
            }

        });

    }
}
