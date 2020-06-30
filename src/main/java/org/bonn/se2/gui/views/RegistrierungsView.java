package org.bonn.se2.gui.views;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.util.PasswordValidator;
import org.bonn.se2.services.util.SessionFunctions;
import org.bonn.se2.services.util.UIFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees, Henry Weckermann, Maximilian Schubert
 */

public class RegistrierungsView extends VerticalLayout implements View {

    private final Panel auswahlPanel = new Panel("Schritt 1: Registrieren als");
    private final Panel userCreationPanel = new Panel("Schritt 2: Geben Sie Ihre Daten ein");
    private final Panel studentCreationPanel = new Panel("Schritt 3: Geben Sie Ihre persönlichen Daten an");
    private final Panel companyCreationPanel = new Panel("Geben Sie Daten Ihres Unternehmens ein");
    private final Binder<User> binder = new Binder<>();
    private final Binder<Student> studentBinder = new Binder<>();
    private final Binder<Address> addressBinder = new Binder<>();
    private final Binder<Company> companyBinder = new Binder<>();
    boolean isStudent;

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (SessionFunctions.isLoggedIn()) {
            UIFunctions.gotoMain();
        } else {
            createHeader();
            this.setUpStep1();
        }
    }

    public void setUpStep1() {

        auswahlPanel.setVisible(true);

        this.addComponent(auswahlPanel);

        this.setComponentAlignment(auswahlPanel, Alignment.MIDDLE_CENTER);

        auswahlPanel.setWidth(37, Unit.PERCENTAGE);
        auswahlPanel.setHeight(20, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        HorizontalLayout prevButtonLayout = new HorizontalLayout();
        content.addComponent(buttonLayout);
        content.addComponent(prevButtonLayout);
        content.setComponentAlignment(prevButtonLayout, Alignment.MIDDLE_CENTER);

        auswahlPanel.setContent(content);
        buttonLayout.setSizeFull();

        Button studentButton = new Button("Student");
        Button companyButton = new Button("Unternehmen");
        Button backButton = new Button("Zurück", VaadinIcons.ARROW_CIRCLE_LEFT);

        prevButtonLayout.addComponent(backButton);
        prevButtonLayout.setComponentAlignment(backButton, Alignment.MIDDLE_CENTER);

        buttonLayout.setSpacing(false);
        buttonLayout.addComponents(studentButton, companyButton);

        studentButton.setWidth(100, Unit.PERCENTAGE);
        companyButton.setWidth(100, Unit.PERCENTAGE);
        backButton.setWidth(30, Unit.PERCENTAGE);

        studentButton.setHeight("100px");
        companyButton.setHeight("100px");
        backButton.setHeight("40px");

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

        backButton.addClickListener(event ->
                UIFunctions.gotoLogin()
        );

    }

    public void setUpStep2() {

        userCreationPanel.setVisible(true);
        this.addComponent(userCreationPanel);
        this.setComponentAlignment(userCreationPanel, Alignment.MIDDLE_CENTER);
        userCreationPanel.setWidth("550px");

        Button weiterButton1 = new Button("Fortfahren", VaadinIcons.ARROW_RIGHT);
        weiterButton1.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        Button backButton = new Button("Zurück", VaadinIcons.ARROW_LEFT);

        FormLayout content = new FormLayout();

        TextField usernameField = new TextField("Nutzername");
        binder.forField(usernameField).asRequired(new StringLengthValidator("Ihr Nutzername mindestens 5 Buchstaben haben", 5, 1000))
                .bind(User::getUsername, User::setUsername);
        usernameField.setSizeFull();

        TextField emailField = new TextField("E-Mail Adresse");
        binder.forField(emailField).asRequired(new EmailValidator("Bitte geben Sie eine gültige E-Mail Adresse an"))
                .bind(User::getEmail, User::setEmail);
        emailField.setSizeFull();

        PasswordField passwordField = new PasswordField("Passwort");
        binder.forField(passwordField).asRequired(new PasswordValidator())
                .bind(User::getPasswort, User::setPasswort);
        passwordField.setSizeFull();

        PasswordField passwordCheckField = new PasswordField("Passwort wiederholen");
        passwordCheckField.setSizeFull();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addComponents(backButton, weiterButton1);

        content.addComponents(usernameField, emailField, passwordField, passwordCheckField, buttonLayout);

        content.setMargin(true);
        userCreationPanel.setContent(content);

        this.setComponentAlignment(userCreationPanel, Alignment.MIDDLE_CENTER);

        backButton.addClickListener(clickEvent -> {
            userCreationPanel.setVisible(false);

            setUpStep1();
        });

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
                binder.writeBean(myUser);
            } catch (ValidationException exception) {
                showError();
                return;
            }

            if (isStudent) {
                setUpStep3_Student(myUser);
            } else {
                setUpStep3_Company(myUser);
            }

            userCreationPanel.setVisible(false);

        });

    }

    public void setUpStep3_Student(User myUser) {
        studentCreationPanel.setVisible(true);
        this.addComponent(studentCreationPanel);
        this.setComponentAlignment(studentCreationPanel, Alignment.MIDDLE_CENTER);
        studentCreationPanel.setWidth("750px");

        VerticalLayout layout = new VerticalLayout();
        studentCreationPanel.setContent(layout);

        HorizontalLayout nameLayout = new HorizontalLayout();
        nameLayout.setSizeFull();

        HorizontalLayout geburtstagLayout = new HorizontalLayout();
        geburtstagLayout.setSizeFull();

        TextField vorname = new TextField("Vorname:");
        vorname.setRequiredIndicatorVisible(true);
        studentBinder.forField(vorname).asRequired("Bitte geben Sie Ihren Vornamen an")
                .bind(Student::getVorname, Student::setVorname);
        vorname.setSizeFull();

        TextField nachname = new TextField("Nachname");
        nachname.setRequiredIndicatorVisible(true);
        studentBinder.forField(nachname).asRequired("Bitte geben Sie Ihren Nachnamen an")
                .bind(Student::getNachname, Student::setNachname);
        nachname.setSizeFull();

        nameLayout.addComponents(vorname, nachname, new Label("&nbsp", ContentMode.HTML));

        DateField geburtstag = new DateField("Geburtstag");
        geburtstag.setDateFormat("dd.MM.yyyy");
        geburtstag.setPlaceholder("dd.mm.yyyy");
        geburtstag.setParseErrorMessage("Bitte Datum im richtigen Format angeben");
        studentBinder.forField(geburtstag).asRequired("Bitte geben Sie Ihr Geburtsdatum an")
                .bind(Student::getGeburtstag, Student::setGeburtstag);
        geburtstag.setSizeFull();

        geburtstagLayout.addComponents(geburtstag, new Label("&nbsp", ContentMode.HTML), new Label("&nbsp", ContentMode.HTML));
        layout.addComponents(nameLayout, geburtstagLayout);

        addAddress(layout);

        List<Button> list = addButtons(layout);
        Button backButton2 = list.get(0);
        Button completeButton = list.get(1);

        backButton2.addClickListener(clickEvent -> {
            studentCreationPanel.setVisible(false);
            setUpStep2();
        });


        completeButton.addClickListener(clickEvent -> {
            boolean isValidEntry = true;
            Student student = new Student(myUser);
            Address address = new Address();

            try {
                addressBinder.writeBean(address);
            } catch (ValidationException e) {
                isValidEntry = false;
            }

            try {
                studentBinder.writeBean(student);
                student.setAdresse(address);
                StudentDAO studentDAO = new StudentDAO();
                studentDAO.create(student);
                setUpStep4();
            } catch (ValidationException e1) {
                isValidEntry = false;
            } catch (Exception e2) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        new Throwable().getStackTrace()[0].getMethodName() + " failed", e2);
            }

            if (!isValidEntry) {
                showError();
            }
        });

    }

    public void setUpStep3_Company(User myUser) {
        companyCreationPanel.setVisible(true);
        this.addComponent(companyCreationPanel);
        this.setComponentAlignment(companyCreationPanel, Alignment.MIDDLE_CENTER);
        VerticalLayout layout = new VerticalLayout();
        companyCreationPanel.setContent(layout);
        companyCreationPanel.setWidth("900px");

        TextField unternehmensName = new TextField("Name Ihres Unternehmens:");
        companyBinder.forField(unternehmensName)
                .asRequired("Bitte geben Sie den Namen Ihres Unternehmens an.")
                .bind(Company::getName, Company::setName);
        unternehmensName.setSizeFull();
        layout.addComponents(unternehmensName);

        RichTextArea beschreibung = new RichTextArea("Beschreibung Ihres Unternehmens:");
        beschreibung.setSizeFull();
        companyBinder.forField(beschreibung)
                .asRequired("Bitte geben Sie eine kurze Beschreibung Ihres Unternehmens an.")
                .bind(Company::getBeschreibung, Company::setBeschreibung);
        layout.addComponent(beschreibung);

        TextField webURL = new TextField("Website ihres Unternehmens");
        webURL.setSizeFull();
        companyBinder.forField(webURL)
                .bind(Company::getWebURL, Company::setWebURL);
        layout.addComponent(webURL);

        addAddress(layout);

        List<Button> list = addButtons(layout);
        Button backButton3 = list.get(0);
        Button completeButton = list.get(1);

        backButton3.addClickListener(clickEvent -> {
            companyCreationPanel.setVisible(false);
            setUpStep2();
        });


        completeButton.addClickListener(clickEvent -> {
            boolean isValidEntry = true;
            Company company = new Company(myUser);
            Address address = new Address();

            try {
                addressBinder.writeBean(address);
            } catch (ValidationException e) {
                isValidEntry = false;
            }

            try {
                companyBinder.writeBean(company);
                company.setAdresse(address);

                CompanyDAO companyDAO = new CompanyDAO();
                companyDAO.create(company);
                setUpStep4();
            } catch (ValidationException e1) {
                isValidEntry = false;
            } catch (Exception e2) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        new Throwable().getStackTrace()[0].getMethodName() + " failed", e2);
            }

            if (!isValidEntry) {
                showError();
            }

        });

    }

    private void setUpStep4() {
        Notification notification = new Notification("Sie haben sich erfolgreich registriert", Notification.Type.ASSISTIVE_NOTIFICATION);
        notification.setPosition(Position.MIDDLE_CENTER);
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
        notification.setStyleName("mystyle");
        UIFunctions.gotoLogin();
    }

    private void addAddress(Layout layout) {

        HorizontalLayout addressLayout1 = new HorizontalLayout();
        addressLayout1.setSizeFull();
        layout.addComponent(addressLayout1);

        TextField strasse = new TextField("Straße:");
        addressBinder.forField(strasse)
                .asRequired("Bitte geben Sie die Straße an.")
                .bind(Address::getStrasse, Address::setStrasse);
        addressLayout1.addComponent(strasse);
        strasse.setSizeFull();


        TextField hausnummer = new TextField("Hausnummer");
        addressLayout1.addComponent(hausnummer);
        hausnummer.setMaxLength(4);
        addressBinder.forField(hausnummer)
                .asRequired("Bitte geben Sie die Hausnummer an.")
                .bind(Address::getHausnummer, Address::setHausnummer);
        hausnummer.setSizeFull();

        addressLayout1.addComponent(new Label("&nbsp", ContentMode.HTML));


        HorizontalLayout addressLayout2 = new HorizontalLayout();
        addressLayout2.setSizeFull();
        layout.addComponent(addressLayout2);

        TextField plz = new TextField("Postleitzahl:");
        plz.setMaxLength(5);
        addressLayout2.addComponent(plz);
        addressBinder.forField(plz)
                .asRequired("Bitte geben Sie die Postleitzahl an!")
                .bind(Address::getPlz, Address::setPlz);
        plz.setSizeFull();


        TextField stadt = new TextField("Ort:");
        addressLayout2.addComponent(stadt);
        addressBinder.forField(stadt)
                .asRequired("Bitte geben Sie Ihre Stadt an.")
                .bind(Address::getStadt, Address::setStadt);
        stadt.setSizeFull();


        TextField land = new TextField("Land:");
        addressLayout2.addComponent(land);
        addressBinder.forField(land)
                .asRequired("Bitte geben Sie das Land an.")
                .bind(Address::getLand, Address::setLand);
        land.setSizeFull();

    }

    private List<Button> addButtons(Layout layout) {
        HorizontalLayout buttonContainer = new HorizontalLayout();
        layout.addComponent(buttonContainer);
        buttonContainer.setSizeFull();

        Button buttonToStep1 = new Button("Zurück", VaadinIcons.ARROW_LEFT);
        buttonContainer.addComponent(buttonToStep1);
        buttonContainer.setComponentAlignment(buttonToStep1, Alignment.BOTTOM_LEFT);

        Button buttonToStep3 = new Button("Weiter", VaadinIcons.ARROW_RIGHT);
        buttonContainer.addComponent(buttonToStep3);
        buttonContainer.setComponentAlignment(buttonToStep3, Alignment.BOTTOM_RIGHT);

        List<Button> l = new ArrayList<>();
        l.add(buttonToStep1);
        l.add(buttonToStep3);
        return l;
    }

    public void createHeader() {
        ThemeResource themeResource = new ThemeResource("images/logo_hd_3.png");
        Image logo = new Image(null, themeResource);
        logo.setWidth("750px");
        logo.addStyleName("logo");
        logo.addClickListener((MouseEvents.ClickListener) event ->
                UIFunctions.gotoLogin()
        );

        Label platzhalterLabel = new Label("&nbsp", ContentMode.HTML);

        Label labelText = new Label("Willkommen auf Coll@Aldernativ! Der zentralen Schnittstelle zwischen Studenten & Unternehmen."
                + " Hier findet jeder seinen Traumjob.");

        this.addComponent(logo);
        this.addComponent(labelText);

        this.addComponent(platzhalterLabel);
        platzhalterLabel.setHeight("40px");

        this.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(labelText, Alignment.MIDDLE_CENTER);

    }

    public void showError() {
        Notification notification = new Notification("Ein oder mehrere Felder sind ungültig", Notification.Type.ERROR_MESSAGE);
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(4000);
        notification.show(Page.getCurrent());
    }

}
