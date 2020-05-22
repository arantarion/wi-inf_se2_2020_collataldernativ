package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.gui.ui.MyUI;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class StudentDatenEingabeView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }

    public void setUp() {

        //Component.setVisible(false);
        Button startseiteButton = new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Label labelAllg = new Label("Bitte füllen Sie alle Felder aus:");
        Button rButton = new Button("Registrieren", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        HorizontalLayout h1 = new HorizontalLayout();
        TextField sf; //für Studienfach
        TextField vm; //für Vorname
        TextField nn;//für Nachname
        DateField gb;//für Geburtstag
        TextField jb; //für Job
        TextField ag; //für Arbeitgeber
        TextField fs; //für Fachsemester muss noch auf Integer überprüft werden
        TextField em; // Für dirty ID erstellung

        addComponent(h1);
        setComponentAlignment(h1, Alignment.TOP_LEFT);
        h1.addComponent(startseiteButton);
        addComponent(labelAllg);

        Panel panel = new Panel();
        panel.setSizeUndefined();
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        FormLayout content2 = new FormLayout();
        content2.addComponent(em = new TextField("Email wiederholen:"));
        content2.addComponent(sf = new TextField("Studienfach:"));
        content2.addComponent(vm = new TextField("Vorname:"));
        content2.addComponent(nn = new TextField("Nachname:"));
        content2.addComponent(gb = new DateField("Geburtstag:"));
        content2.addComponent(jb = new TextField("Job (Optional):"));
        content2.addComponent(ag = new TextField("Arbeitgeber (Optional):"));
        content2.addComponent(fs = new TextField("Fachsemester:"));
        content2.addComponent(rButton);
        content2.setSizeUndefined();

        content2.setMargin(true);
        panel.setContent(content2);

        rButton.addClickListener(e -> {
            if (!vm.getValue().equals("") && !sf.getValue().equals("") && !nn.getValue().equals("") && !gb.getValue().equals("") && !fs.getValue().equals("") && !em.getValue().equals("")) {
                addComponent(new Label("Vielen Dank für die Registrierung. Sie können sich nun einloggen"));
                addComponent(startseiteButton);
                try {
                    User test = new UserDAO().retrieve(em.getValue());
                    Student user = new Student(vm.getValue(),nn.getValue(),sf.getValue(),jb.getValue(),ag.getValue(),gb.getValue(),Integer.parseInt(fs.getValue()),test.getUserID());
                    User dto = generateStudent(user);
                    System.out.println(user);
                } catch (DatabaseException databaseException) {
                    databaseException.printStackTrace();
                } catch (InvalidCredentialsException invalidCredentialsException) {
                    invalidCredentialsException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            } else {
                Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
            }

        });

        startseiteButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
        });
    }

    public static User generateStudent(Student user) throws Exception {
        Student dto = new StudentDAO().create(user);

        return dto;
    }
}
