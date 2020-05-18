package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.util.Configuration;

import static org.bonn.se2.services.util.CryptoFunctions.hash;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class RegistrierungsView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }

    public void setUp() {
        Button startseiteButton = new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Label labelAllg = new Label("Bitte füllen Sie alle Felder aus:");
        Button rButton = new Button("Weiter", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        //CheckBox
        CheckBox chkU = new CheckBox("Unternehmer");
        CheckBox chkS = new CheckBox("Student");
        HorizontalLayout h1 = new HorizontalLayout();
        TextField vn; //für Nutzername
        TextField em; //für Email
        TextField pw1;//für Passwort
        TextField pw2;//für Passwort wiederholen

        addComponent(h1);
        setComponentAlignment(h1, Alignment.TOP_LEFT);
        h1.addComponent(startseiteButton);
        addComponent(labelAllg);
        chkU.setValue(false);
        chkS.setValue(false);
        addComponent(chkU);
        addComponent(chkS);

        Panel panel = new Panel();
        panel.setSizeUndefined();
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        FormLayout content = new FormLayout();
        content.addComponent(vn = new TextField("Nutzername:"));
        //content.addComponent(new TextField("Adresse:"));
        content.addComponent(em = new TextField("Email:"));
        content.addComponent(pw1 = new PasswordField("Passwort:"));
        content.addComponent(pw2 = new PasswordField("Passwort wiederholen:"));
        content.addComponent(rButton);
        content.setSizeUndefined();

        content.setMargin(true);
        panel.setContent(content);

        rButton.addClickListener(e -> {
            if (!vn.getValue().equals("") && !em.getValue().equals("") && !pw1.getValue().equals("") && !pw2.getValue().equals("") && pw1.getValue().equals(pw2.getValue()) && (chkU.getValue() == true ^ chkS.getValue() == true)) {
                addComponent(new Label("Vielen Dank für die Registrierung. Sie können sich nun einloggen"));
                String pw = hash(pw1.getValue());
                User user = new User(vn.getValue(), em.getValue(), pw);
                try {
                    //User dto = generateUser(user);
                    //User test = new UserDAO().retrieve(dto.getEmail());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (chkU.getValue() == true) {//Nutzer ist Unternehmer
                    UI.getCurrent().getNavigator().navigateTo((Configuration.Views.COMPDAT));

                }
                if (chkS.getValue() == true) {//Nutzer ist Student
                    UI.getCurrent().getNavigator().navigateTo(Configuration.Views.STUDDAT);
                }
                addComponent(startseiteButton);
            } else {
                Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
            }

        });

        startseiteButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
        });
    }

    public static User generateUser(User user) throws Exception {
        User dto = new UserDAO().create(user);

        return dto;
    }


}
