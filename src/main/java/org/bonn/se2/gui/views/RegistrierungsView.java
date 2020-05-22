package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;

import static org.bonn.se2.gui.views.StudentDatenEingabeView.generateStudent;
import static org.bonn.se2.services.util.CryptoFunctions.hash;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class RegistrierungsView extends VerticalLayout implements View {

    int ID ;

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
                    User dto = generateUser(user);
                    ID = dto.getUserID();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (chkU.getValue() == true) {//Nutzer ist Unternehmer
                    UI.getCurrent().getNavigator().navigateTo((Configuration.Views.COMPDAT));

                }
                if (chkS.getValue() == true) {//Nutzer ist Student
                    Button wButton = new Button("Weiter", FontAwesome.ARROW_CIRCLE_O_RIGHT);
                    HorizontalLayout h2 = new HorizontalLayout();
                    TextField sf; //für Studienfach
                    TextField vm; //für Vorname
                    TextField nn;//für Nachname
                    DateField gb;//für Geburtstag
                    TextField jb; //für Job
                    TextField ag; //für Arbeitgeber
                    TextField fs; //für Fachsemester muss noch auf Integer überprüft werden

                    addComponent(h1);
                    setComponentAlignment(h1, Alignment.TOP_LEFT);
                    h1.addComponent(startseiteButton);
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
                    //UI.getCurrent().getNavigator().navigateTo(Configuration.Views.STUDDAT);

                    wButton.addClickListener(d -> {
                        if (!vm.getValue().equals("") && !sf.getValue().equals("") && !nn.getValue().equals("") && !gb.getValue().equals("") && !fs.getValue().equals("") && !em.getValue().equals("")) {
                            addComponent(new Label("Vielen Dank für die Registrierung. Sie können sich nun einloggen"));
                            addComponent(startseiteButton);
                            try {
                                Student user2 = new Student(vm.getValue(), nn.getValue(), sf.getValue(), jb.getValue(), ag.getValue(), gb.getValue(), Integer.parseInt(fs.getValue()), ID);
                                User dto = generateStudent(user2);
                                System.out.println(ID);
                                System.out.println(user2);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                        } else {
                            Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
                        }
                    });
                    addComponent(startseiteButton);
                } else {
                    Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
                }

            }

        });
    }

    public static User generateUser(User user) throws Exception {
        User dto = new UserDAO().create(user);

        return dto;
    }
    public static User generateStudent(Student user) throws Exception {
        Student dto = new StudentDAO().create(user);

        return dto;
    }
    public static User generateCompany(Company user) throws Exception {
        Company dto = new CompanyDAO().create(user);

        return dto;
    }
}
