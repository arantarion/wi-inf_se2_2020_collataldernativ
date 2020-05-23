package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import static org.bonn.se2.process.control.RegistrierungsControl.*;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class RegistrierungsView extends VerticalLayout implements View {

    int ID;
    User user;
    String auswahl = "";

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws DatabaseException {
        Button startseiteButton = new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Label labelAllg = new Label("Bitte füllen Sie alle Felder aus:");
        Button rButton = new Button("Weiter", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        //CheckBox
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setItems("Student", "Unternehmer");
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
        //addComponent(chkU);
        //addComponent(chkS);
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
        content.addComponent(vn = new TextField("Nutzername:"));
        content.addComponent(em = new TextField("Email:"));
        content.addComponent(pw1 = new PasswordField("Passwort:"));
        content.addComponent(pw2 = new PasswordField("Passwort wiederholen:"));
        content.addComponent(rButton);
        content.setSizeUndefined();

        content.setMargin(true);
        panel.setContent(content);

        rButton.addClickListener(e -> {
            if (!vn.getValue().equals("") && !em.getValue().equals("") && !pw1.getValue().equals("") && !pw2.getValue().equals("") && pw1.getValue().equals(pw2.getValue()) && !auswahl.equals("")) { // && (chkU.getValue() == true ^ chkS.getValue() == true)
                h1.setVisible(false);
                panel.setVisible(false);
                content.setVisible(false);
                chkU.setVisible(false);
                chkS.setVisible(false);
                radioGroup.setVisible(false);
                try {
                    user = generateUser(vn.getValue(), em.getValue(), pw1.getValue());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (auswahl.equals("Unternehmer")) {//Nutzer ist Unternehmer chkU.getValue() == true
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
                    addComponent(labelAllg);

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

                    cButton.addClickListener(f -> {
                        if (!name.getValue().equals("") && !webURL.getValue().equals("") && !beschreibung.getValue().equals("") && !branche.getValue().equals("") && !ansprechpartner.getValue().equals("")) {
                            try {
                                Company dto = generateCompany(name.getValue(), webURL.getValue(), beschreibung.getValue(), branche.getValue(), ansprechpartner.getValue(), user);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        } else {
                            Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
                        }
                    });
                }


                if (auswahl.equals("Student")) {//Nutzer ist Student chkS.getValue() == true
                    Button wButton = new Button("Weiter", FontAwesome.ARROW_CIRCLE_O_RIGHT);
                    HorizontalLayout h2 = new HorizontalLayout();
                    TextField sf; //für Studienfach
                    TextField vm; //für Vorname
                    TextField nn;//für Nachname
                    DateField gb;//für Geburtstag
                    TextField jb; //für Job
                    TextField ag; //für Arbeitgeber
                    TextField fs; //für Fachsemester muss noch auf Integer überprüft werden

                    addComponent(h2);
                    setComponentAlignment(h2, Alignment.TOP_LEFT);
                    h2.addComponent(startseiteButton);
                    addComponent(labelAllg);

                    Panel panel2 = new Panel();
                    panel2.setSizeUndefined();
                    addComponent(panel2);
                    setComponentAlignment(panel2, Alignment.MIDDLE_CENTER);

                    FormLayout content2 = new FormLayout();
                    content2.addComponent(sf = new TextField("Studienfach:"));
                    content2.addComponent(vm = new TextField("Vorname:"));
                    content2.addComponent(nn = new TextField("Nachname:"));
                    content2.addComponent(gb = new DateField("Geburtstag:"));
                    content2.addComponent(jb = new TextField("Job (Optional):"));
                    content2.addComponent(ag = new TextField("Arbeitgeber (Optional):"));
                    content2.addComponent(fs = new TextField("Fachsemester:"));
                    content2.addComponent(wButton);
                    content2.setSizeUndefined();

                    content2.setMargin(true);
                    panel2.setContent(content2);

                    wButton.addClickListener(d -> {
                        if (!vm.getValue().equals("") && !sf.getValue().equals("") && !nn.getValue().equals("") && !gb.getValue().equals("") && !fs.getValue().equals("") && !em.getValue().equals("")) {
                            addComponent(new Label("Vielen Dank für die Registrierung. Sie können sich nun einloggen"));
                            addComponent(startseiteButton);
                            try {
                                Student dto = generateStudent(vm.getValue(), nn.getValue(), sf.getValue(), jb.getValue(), ag.getValue(), gb.getValue(), Integer.parseInt(fs.getValue()), user);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                        } else {
                            Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
                        }
                    });
                    addComponent(startseiteButton);
                }

            } else {
                Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
            }

        });
    }

}
