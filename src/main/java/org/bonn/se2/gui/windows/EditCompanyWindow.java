/*
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer
 *
 *
 */

package org.bonn.se2.gui.windows;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se2.gui.views.ProfilView;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.FileUploader;
import org.bonn.se2.services.util.SessionFunctions;
import org.bonn.se2.services.util.UIFunctions;


public class EditCompanyWindow extends Window {

    private Company company;

    private Binder<User> userBinder = new Binder<>();
    private Binder<Address> addressBinder = new Binder<>();
    private Binder<Company> companyBinder = new Binder<>();

    static Image profilbild = new Image("nichts ausgewählt");

    public static void setProfilbild(FileResource file) {
        profilbild.setSource(file);
    }

    public EditCompanyWindow(Company company) {
        this.company = company;
        this.setUp();
    }

    private void setUp() {
        Address address = this.company.getAdresse();

        VerticalLayout verticalLayout = new VerticalLayout();
        this.setContent(verticalLayout);
        this.setWidth("75%");
        this.center();

        GridLayout grid = new GridLayout(4, 32);
        verticalLayout.addComponent(grid);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0, 1);
        grid.setColumnExpandRatio(1, 2);
        grid.setColumnExpandRatio(2, 1);
        grid.setColumnExpandRatio(3, 2);

        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 0);

        Label nameLabel = new Label("Firmierung", ContentMode.HTML);
        TextField nameField = new TextField();
        nameField.setWidth("90%");

        companyBinder.forField(nameField)
                .asRequired(new StringLengthValidator("Mind. 4 Zeichen", 4, 1000))
                .bind(Company::getName, Company::setName);

        nameField.setPlaceholder(company.getName());
        grid.addComponent(nameLabel, 0, 1, 1, 1);
        grid.addComponent(nameField, 2, 1, 3, 1);
        grid.setComponentAlignment(nameLabel, Alignment.MIDDLE_CENTER);

        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 2);

        Label compDescLabel = new Label("Beschreibung", ContentMode.HTML);
        RichTextArea compDesc = new RichTextArea();
        compDesc.setValue(company.getBeschreibung());

        grid.addComponent(compDescLabel, 0, 3);
        grid.addComponent(compDesc, 0, 4, 3, 4);
        compDesc.setWidth("90%");
        grid.setComponentAlignment(compDesc, Alignment.MIDDLE_CENTER);

        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 5);

        Label emailLabel = new Label("E-Mail", ContentMode.HTML);
        TextField emailText = new TextField();
        companyBinder.forField(emailText)
                .asRequired(new EmailValidator("Keine gültige E-Mail Adresse"))
                .bind(User::getEmail, User::setEmail);
        emailText.setPlaceholder(company.getEmail());
        grid.addComponent(emailLabel, 0, 6);
        grid.addComponent(emailText, 0, 7, 1, 7);
        emailText.setWidth("90%");

        Label websiteLabel = new Label("Website", ContentMode.HTML);
        TextField websiteField = new TextField();
        websiteField.setValue(company.getWebURL());
        grid.addComponent(websiteLabel, 2, 6);
        grid.addComponent(websiteField, 2, 7, 3, 7);
        websiteField.setWidth("90%");

        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 8);

        Label passwortLabel = new Label("Passwort ändern?", ContentMode.HTML);
        grid.addComponent(passwortLabel, 0, 9, 3, 9);

        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 10);

        Label passwortLabel2 = new Label("Neues Passwort", ContentMode.HTML);
        TextField passwortField = new TextField();
        grid.addComponent(passwortLabel2, 0, 11);
        grid.addComponent(passwortField, 0, 12, 1, 12);
        passwortField.setWidth("90%");

        Label passwortLabel3 = new Label("Wiederholen Sie das Passwort", ContentMode.HTML);
        TextField passwortField2 = new TextField();

        grid.addComponent(passwortLabel3, 2, 11);
        grid.addComponent(passwortField2, 2, 12, 3, 12);
        passwortField2.setWidth("90%");

        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 13);


        Label addressLabel = new Label("ADDRESSE", ContentMode.HTML);
        grid.addComponent(addressLabel, 0, 14, 3, 14);

        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 15);

        //TODO

        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 21);

        FileUploader receiver = new FileUploader();
        Upload foto = new Upload("", receiver);

        foto.setButtonCaption("Foto hochladen");
        foto.addSucceededListener(receiver);

        grid.addComponent(foto, 0, 22);
        grid.setComponentAlignment(foto, Alignment.BOTTOM_CENTER);

        Button deleteButton = new Button("Profil löschen", VaadinIcons.TRASH);
        grid.addComponent(deleteButton, 2, 22);
        grid.setComponentAlignment(deleteButton, Alignment.BOTTOM_CENTER);
        deleteButton.setWidth("100%");

        deleteButton.addClickListener(clickEvent -> {
            UI.getCurrent().removeWindow(this);
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.DELETION);
        });

        Button saveButton = new Button("Speichern", VaadinIcons.ARCHIVES);
        grid.addComponent(saveButton, 3, 22);
        grid.setComponentAlignment(saveButton, Alignment.BOTTOM_RIGHT);
        saveButton.setWidth("100%");

        saveButton.addClickListener(clickEvent -> {

            boolean correct = true;

            Company tmp = new Company();
            tmp.setUserID(this.company.getUserID());
            tmp.setUsername(this.company.getUsername());
            tmp.setEmail(emailText.getValue());

            if (!passwortField.getValue().equals("") && passwortField.getValue().equals(passwortField2.getValue())) {
                tmp.setPasswort(passwortField.getValue());
            } else {
                Notification notification = new Notification("Die Passwörter stimmen nicht überein.", Notification.Type.ERROR_MESSAGE);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.setDelayMsec(4000);
                notification.show(Page.getCurrent());
                return;
            }

            tmp.setImage(SessionFunctions.getCurrentUser().getImage());

            //TODO
//            Address a = new Address();
//            a.setStrasse(.getValue());
//            a.setHausnummer(.getValue());
//            a.setPlz(.getValue());
//            a.setStadt(.getValue());
//            a.setLand(.getValue());

            tmp.setName(nameField.getValue());
            tmp.setBeschreibung(compDesc.getValue());
            tmp.setWebURL(websiteField.getValue());

            try {
                companyBinder.writeBean(tmp);
                CompanyDAO companyDAO = new CompanyDAO();
                Company returnValue = companyDAO.update(tmp);
            } catch (Exception e) {
                correct = false;
                e.printStackTrace();
            }

            if (correct) {
                this.close();
                Notification notification = new Notification("Daten erfolgreich gespeichert", Notification.Type.ASSISTIVE_NOTIFICATION);
                notification.setPosition(Position.MIDDLE_CENTER);
                notification.setDelayMsec(4000);
                notification.show(Page.getCurrent());
            } else {
                Notification notification = new Notification("Fehler beim Speichern", Notification.Type.WARNING_MESSAGE);
                notification.setPosition(Position.MIDDLE_CENTER);
                notification.setDelayMsec(4000);
                notification.show(Page.getCurrent());
            }

            ProfilView.setCompany(tmp);
            UIFunctions.gotoProfile();

        });

    }

}
