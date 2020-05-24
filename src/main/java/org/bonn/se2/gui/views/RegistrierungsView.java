package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import jdk.tools.jaotc.Main;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.model.objects.dto.UserAtLogin;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;

import static org.bonn.se2.process.control.RegistrierungsControl.*;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class RegistrierungsView extends VerticalLayout implements View {

    User user;
    String auswahl = "";

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }

    public void setUp() {
        Button startseiteButton = new Button("Login", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Label labelAllg = new Label("Bitte füllen Sie alle Felder aus:");
        Button rButton = new Button("Weiter", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setItems("Student", "Unternehmer");
        HorizontalLayout h1 = new HorizontalLayout();
        TextField nutzername; //für Nutzername
        TextField email; //für Email
        TextField pw1;//für Passwort
        TextField pw2;//für Passwort wiederholen

        addComponent(h1);
        setComponentAlignment(h1, Alignment.TOP_LEFT);
        h1.addComponent(startseiteButton);
        addComponent(labelAllg);
        addComponent(radioGroup);

        radioGroup.addValueChangeListener(event -> {
            this.auswahl = event.getValue();
            System.out.println(auswahl);
        });

        Panel panel = new Panel();
        panel.setSizeUndefined();
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        FormLayout content = new FormLayout();
        content.addComponent(nutzername = new TextField("Nutzername:"));
        content.addComponent(email = new TextField("Email:"));
        content.addComponent(pw1 = new PasswordField("Passwort:"));
        content.addComponent(pw2 = new PasswordField("Passwort wiederholen:"));
        content.addComponent(rButton);
        content.setSizeUndefined();

        content.setMargin(true);
        panel.setContent(content);

        startseiteButton.addClickListener(s -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
        });

        rButton.addClickListener(e -> {
            if (!nutzername.getValue().equals("") && !email.getValue().equals("") && !pw1.getValue().equals("") && !pw2.getValue().equals("") && pw1.getValue().equals(pw2.getValue()) && !auswahl.equals("")) {
                h1.setVisible(false);
                panel.setVisible(false);
                content.setVisible(false);
                radioGroup.setVisible(false);
                try {
                    user = generateUser(nutzername.getValue(), email.getValue(), pw1.getValue());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (auswahl.equals("Unternehmer")) {//Nutzer ist Unternehmer chkU.getValue() == true
                    setUpCompany();
                }

                if (auswahl.equals("Student")) {//Nutzer ist Student chkS.getValue() == true
                    setUpStudent();
                }

            } else {
                Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
            }

        });
    }

    public void setUpCompany(){
        Button startseiteButton = new Button("Login", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button cButton = new Button("Weiter", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        HorizontalLayout h3 = new HorizontalLayout();
        TextField name;
        TextField webURL;
        TextField beschreibung;
        TextField branche;
        TextField ansprechpartner;

        addComponent(h3);
        setComponentAlignment(h3, Alignment.TOP_LEFT);
        h3.addComponents(startseiteButton);

        Panel panel3 = new Panel();
        panel3.setSizeUndefined();
        addComponent(panel3);
        setComponentAlignment(panel3, Alignment.MIDDLE_CENTER);

        FormLayout content3 = new FormLayout();
        content3.addComponent(name = new TextField("Name:"));
        content3.addComponent(webURL = new TextField("URL:"));
        content3.addComponent(beschreibung = new TextField("Beschreibung (Optional):"));
        content3.addComponent(branche = new TextField("Branche:"));
        content3.addComponent(ansprechpartner = new TextField("Ansprechpartner (Optional):"));
        content3.addComponent(cButton);
        content3.setSizeUndefined();

        content3.setMargin(true);
        panel3.setContent(content3);

        startseiteButton.addClickListener(s -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
        });

        cButton.addClickListener(f -> {
            if (!name.getValue().equals("") && !webURL.getValue().equals("") && !beschreibung.getValue().equals("") && !branche.getValue().equals("") && !ansprechpartner.getValue().equals("")) {
                try {
                    generateCompany(name.getValue(), webURL.getValue(), beschreibung.getValue(), branche.getValue(), ansprechpartner.getValue(), user);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
            }
        });

    }

    public void setUpStudent(){
        Button startseiteButton = new Button("Login", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button wButton = new Button("Weiter", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        HorizontalLayout h2 = new HorizontalLayout();
        TextField studienfach; //für Studienfach
        TextField vorname; //für Vorname
        TextField nachname;//für Nachname
        DateField geburtstag;//für Geburtstag
        TextField job; //für Job
        TextField arbeitgeber; //für Arbeitgeber
        TextField fachsemester; //für Fachsemester muss noch auf Integer überprüft werden

        addComponent(h2);
        setComponentAlignment(h2, Alignment.TOP_LEFT);
        h2.addComponent(startseiteButton);

        Panel panel2 = new Panel();
        panel2.setSizeUndefined();
        addComponent(panel2);
        setComponentAlignment(panel2, Alignment.MIDDLE_CENTER);

        FormLayout content2 = new FormLayout();
        content2.addComponent(studienfach = new TextField("Studienfach:"));
        content2.addComponent(vorname = new TextField("Vorname:"));
        content2.addComponent(nachname = new TextField("Nachname:"));
        content2.addComponent(geburtstag = new DateField("Geburtstag:"));
        content2.addComponent(job = new TextField("Job (Optional):"));
        content2.addComponent(arbeitgeber = new TextField("Arbeitgeber (Optional):"));
        content2.addComponent(fachsemester = new TextField("Fachsemester:"));
        content2.addComponent(wButton);
        content2.setSizeUndefined();

        content2.setMargin(true);
        panel2.setContent(content2);

        startseiteButton.addClickListener(s -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
        });

        wButton.addClickListener(d -> {
            if (!vorname.getValue().equals("") && !studienfach.getValue().equals("") && !nachname.getValue().equals("") && !geburtstag.getValue().equals("") && !fachsemester.getValue().equals("")) {
                addComponent(new Label("Vielen Dank für die Registrierung. Sie können sich nun einloggen"));
                addComponent(startseiteButton);
                try {
                    generateStudent(vorname.getValue(), nachname.getValue(), studienfach.getValue(), job.getValue(), arbeitgeber.getValue(), geburtstag.getValue(), Integer.parseInt(fachsemester.getValue()), user);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            } else {
                Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
            }
        });
        addComponent(startseiteButton);
    }

}
