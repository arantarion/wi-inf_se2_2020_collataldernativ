package org.bonn.se2.gui.views;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.PasswordValidator;

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
    boolean isStudent;

    private Binder<User> binder = new Binder<>();
    private Binder<Student> StudentBinder = new Binder<>();
    private Binder<Address> AdressBinder = new Binder<>();
    private Binder<Company> CompanyBinder = new Binder<>();
    private final Panel auswahlPanel = new Panel("Schritt 1: Registrieren als");
    private final Panel userCreationPanel = new Panel("Schritt 2: Geben Sie Ihre Daten ein");
    private final Panel studentCreationPanel = new Panel("Schritt 3: Geben Sie Ihre persönlichen Daten an");

    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        try {
//            this.setUp();
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//        }
        this.setUpStep1();
    }

    public void setUpStep1() {

        auswahlPanel.setVisible(true);

        this.setSizeFull();
        this.addComponent(auswahlPanel);
        this.setComponentAlignment(auswahlPanel, Alignment.MIDDLE_CENTER);

        auswahlPanel.setWidth(37, Unit.PERCENTAGE);
        auswahlPanel.setHeight(20, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        content.addComponent(buttonLayout);

        auswahlPanel.setContent(buttonLayout);
        buttonLayout.setSizeFull();

        Button studentButton = new Button("Student");
        Button companyButton = new Button("Unternehmen");

        buttonLayout.setSpacing(false);
        buttonLayout.addComponents(studentButton, companyButton);

        studentButton.setWidth(100, Unit.PERCENTAGE);
        companyButton.setWidth(100, Unit.PERCENTAGE);

        studentButton.setHeight(90, Unit.PERCENTAGE);
        companyButton.setHeight(90, Unit.PERCENTAGE);

        buttonLayout.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);

        studentButton.addClickListener(event -> {
            auswahlPanel.setVisible(false);
            this.isStudent = true;
            setUpStep2();
        });

        companyButton.addClickListener(event -> {
            auswahlPanel.setVisible(false);
            this.isStudent = false;
            setUpStep2();
        });
    }

    public void setUpStep2() {

        userCreationPanel.setVisible(true);
        this.setSizeUndefined();
        this.addComponent(userCreationPanel);
        this.setComponentAlignment(userCreationPanel, Alignment.MIDDLE_CENTER);

        Button weiterButton1 = new Button("Fortfahren", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        weiterButton1.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        FormLayout content = new FormLayout();
        content.setSizeUndefined();

        TextField usernameField = new TextField("Nutzername");
        binder.forField(usernameField).asRequired(new StringLengthValidator("Ihr Nutzername mindestens 5 Buchstaben haben", 5, 1000))
                .bind(User::getUsername, User::setUsername);

        TextField emailField = new TextField("E-Mail Adresse");
        binder.forField(emailField).asRequired(new EmailValidator("Bitte geben Sie eine gültige E-Mail Adresse an"))
                .bind(User::getEmail, User::setEmail);

        PasswordField passwordField = new PasswordField("Passwort");
        binder.forField(passwordField).asRequired(new PasswordValidator())
                .bind(User::getPasswort, User::setPasswort);

        PasswordField passwordCheckField = new PasswordField("Passwort wiederholen");

        content.addComponents(usernameField, emailField, passwordField, passwordCheckField, weiterButton1);
        content.setSizeUndefined();

        content.setMargin(true);
        userCreationPanel.setContent(content);

        this.setComponentAlignment(userCreationPanel, Alignment.MIDDLE_CENTER);

        weiterButton1.addClickListener(clickEvent -> {
            if (!passwordField.getValue().equals(passwordCheckField.getValue())) {
                Notification notification = new Notification("Die Passwörter stimmen nicht überein.", Notification.Type.ERROR_MESSAGE);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.setDelayMsec(4000);
                notification.show(Page.getCurrent());
                return;
            }

            User myUser = new User();
            try {
                //user = generateUser(usernameField.getValue(), emailField.getValue(), passwordField.getValue());
                binder.writeBean(myUser);
                System.out.println(myUser);
            } catch (ValidationException exception) {
                Notification notification = new Notification("Ein oder mehrere Felder sind ungültig", Notification.Type.ERROR_MESSAGE);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.setDelayMsec(4000);
                notification.show(Page.getCurrent());
                return;
            }

            if (isStudent){
                setUpStep3_Student();
            } else {
                setUpStep3_Company();
            }

            userCreationPanel.setVisible(false);

        });


    }

    public void setUpStep3_Student() {
        studentCreationPanel.setVisible(true);
        this.addComponent(studentCreationPanel);
        this.setComponentAlignment(studentCreationPanel, Alignment.MIDDLE_CENTER);

        VerticalLayout layout = new VerticalLayout();
        studentCreationPanel.setContent(layout);


    }

    public void setUpStep3_Company() {

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
        this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

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
