package org.bonn.se2.gui.windows;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.bonn.se2.gui.views.ProfilView;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.util.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Anton Drees
 */

public class EditStudentWindow extends Window {

    private static final ThemeResource resource = new ThemeResource(Configuration.ImagePaths.PLACEHOLDER);
    private static final Panel profilBildPanel = new Panel();
    private static Image profilbild = new Image("Kein Bild ausgewählt", resource);
    private final Binder<User> binder = new Binder<>();
    private final Binder<Address> addressBinder = new Binder<>();
    private final Student student;

    public EditStudentWindow(Student dto) {
        this.student = dto;
        this.setUp();
    }

    public static void refreshProfilePic(Image image) {
        if (image != null) {
            profilbild = image;
            profilbild.setCaption("");
        }
        profilbild.setWidth("200px");
        profilBildPanel.setContent(profilbild);
        profilBildPanel.setWidth("200px");
        profilBildPanel.addStyleName("profilepicpanel");
    }

    private void setUp() {

        Address address = this.student.getAdresse();
        GridLayout grid = new GridLayout(4, 21);
        grid.setWidth("100%");
        grid.setSpacing(true);
        grid.addStyleName("scrollable");
        grid.addStyleName("gridPadding");
        this.setContent(grid);
        this.setWidth("75%");
        this.center();

        Label allgemein = new Label("Allgemeines");
        grid.addComponent(allgemein, 0, 0);

        Label emailLabel = new Label("E-Mail-Adresse");
        TextField emailTf = new TextField();
        emailTf.setPlaceholder(this.student.getEmail());
        emailTf.setWidth("100%");
        grid.addComponent(emailLabel, 0, 1);
        grid.addComponent(emailTf, 1, 1, 3, 1);
        grid.setComponentAlignment(emailLabel, Alignment.MIDDLE_CENTER);
        binder.forField(emailTf)
                .asRequired(new EmailValidator("Keine gültige Email-Adresse."))
                .bind(User::getEmail, User::setEmail);
        emailTf.setRequiredIndicatorVisible(false);

        Label pwLabel = new Label("Passwort");
        Label pwLabel2 = new Label("Wiederholen");
        PasswordField pwTf = new PasswordField();
        PasswordField pwTf2 = new PasswordField();

        pwTf.setWidth("100%");
        pwTf2.setWidth("100%");
        grid.addComponent(pwLabel, 0, 2);
        grid.addComponent(pwLabel2, 2, 2);
        grid.addComponent(pwTf, 1, 2);
        grid.addComponent(pwTf2, 3, 2);
        grid.setComponentAlignment(pwLabel, Alignment.MIDDLE_CENTER);
        grid.setComponentAlignment(pwLabel2, Alignment.MIDDLE_CENTER);

        binder.forField(pwTf).asRequired(new PasswordValidator())
                .bind(User::getPasswort, User::setPasswort);

        pwTf.setRequiredIndicatorVisible(false);
        pwTf2.setRequiredIndicatorVisible(false);

        Label fachLabel = new Label("Studiengang");
        TextField fachTf = new TextField();
        fachTf.setPlaceholder("Keine Angaben vorhanden");
        if (this.student.getStudienfach() != null && !(this.student.getStudienfach().isEmpty())) {
            fachTf.setValue(this.student.getStudienfach());
        }
        fachTf.setWidth("100%");
        grid.addComponent(fachLabel, 0, 3);
        grid.addComponent(fachTf, 1, 3, 3, 3);
        grid.setComponentAlignment(fachLabel, Alignment.MIDDLE_CENTER);


        Label fachsemester = new Label("Fachsemester");
        TextField fachsemesterTF = new TextField();
        fachsemesterTF.setPlaceholder("Keine Angaben vorhanden");
        if (this.student.getFachsemester() != null && !(Integer.toString(this.student.getFachsemester()).isEmpty()) && !(this.student.getFachsemester() == 0)) {
            fachsemesterTF.setPlaceholder(Integer.toString(student.getFachsemester()));
        }
        fachsemesterTF.setWidth("100%");
        grid.addComponent(fachsemester, 0, 4);
        grid.addComponent(fachsemesterTF, 1, 4, 3, 4);
        grid.setComponentAlignment(fachsemester, Alignment.MIDDLE_CENTER);


        FileUploader receiver;
        receiver = new FileUploader();
        Label label_photo = new Label("Profilbild");
        Upload upload_photo = new Upload("", receiver);
        upload_photo.setButtonCaption("Hochladen");
        upload_photo.addSucceededListener(receiver);
        upload_photo.setAcceptMimeTypes("image/*");

        if (student.getImage() != null && student.getImage().length > 0) {
            refreshProfilePic(Utils.convertToImg(student.getImage()));
        } else {
            refreshProfilePic(new Image("", resource));
        }

        GridLayout picLayout = new GridLayout(1, 2);
        picLayout.setSpacing(false);
        picLayout.addComponent(profilBildPanel, 0, 0);
        picLayout.addComponent(upload_photo, 0, 1);
        picLayout.setComponentAlignment(upload_photo, Alignment.MIDDLE_RIGHT);

        grid.addComponent(label_photo, 0, 5);
        grid.addComponent(picLayout, 1, 5);
        grid.setComponentAlignment(label_photo, Alignment.MIDDLE_CENTER);
        grid.setSpacing(true);

//TODO
//        Label laddress = new Label("Adresse");
//        grid.addComponent(laddress, 0, 6);
//        Label lstreet = new Label("Straße");
//        TextField tfstreet = new TextField();
//        addressBinder.forField(tfstreet)
//                .asRequired("Bitte geben Sie Ihre Straße an.")
//                .bind(Address::getStrasse, Address::setStrasse);
//        tfstreet.setRequiredIndicatorVisible(false);
//        tfstreet.setWidth("100%");
//        tfstreet.setValue(address.getStrasse());
//        grid.addComponent(lstreet, 0, 7);
//        grid.addComponent(tfstreet, 1, 7, 1, 7);
//        grid.setComponentAlignment(lstreet, Alignment.MIDDLE_CENTER);
//
//TODO
//        Label lhousenumber = new Label("Hausnummer");
//        TextField tfhousenumber = new TextField();
//        addressBinder.forField(tfhousenumber)
//                .asRequired("Bitte geben Sie Ihre Hausnummer an.")
//                .bind(Address::getHausnummer, Address::setHausnummer);
//        tfhousenumber.setRequiredIndicatorVisible(false);
//        tfhousenumber.setValue(address.getHausnummer());
//        grid.addComponent(lhousenumber, 2, 7);
//        grid.addComponent(tfhousenumber, 3, 7);
//        grid.setComponentAlignment(lhousenumber, Alignment.MIDDLE_CENTER);
//
//TODO
//        Label lpostalcode = new Label("Postleitzahl");
//        TextField tfpostalcode = new TextField();
//        addressBinder.forField(tfpostalcode)
//                .asRequired("Bitte geben Sie Ihre Straße an.")
//                .bind(Address::getPlz, Address::setPlz);
//        tfpostalcode.setRequiredIndicatorVisible(false);
//        tfpostalcode.setValue(address.getPlz());
//        grid.addComponent(lpostalcode, 0, 8);
//        grid.addComponent(tfpostalcode, 1, 8);
//        grid.setComponentAlignment(lpostalcode, Alignment.MIDDLE_CENTER);
//
//TODO
//        Label lcity = new Label("Stadt");
//        TextField tfcity = new TextField();
//        addressBinder.forField(tfcity)
//                .asRequired("Bitte geben Sie Ihre Stadt ein.")
//                .bind(Address::getStadt, Address::setStadt);
//        tfcity.setRequiredIndicatorVisible(false);
//        tfcity.setValue(address.getStadt());
//        grid.addComponent(lcity, 2, 8);
//        grid.addComponent(tfcity, 3, 8);
//        grid.setComponentAlignment(lcity, Alignment.MIDDLE_CENTER);
//
//TODO
//        Label lcountry = new Label("Land");
//        TextField tfcountry = new TextField();
//        addressBinder.forField(tfcountry)
//                .asRequired("Bitte geben Sie Ihr Land ein.")
//                .bind(Address::getLand, Address::setLand);
//        tfcountry.setRequiredIndicatorVisible(false);
//        tfcountry.setValue(address.getLand());
//        grid.addComponent(lcountry, 0, 9);
//        grid.addComponent(tfcountry, 1, 9);
//        grid.setComponentAlignment(lcountry, Alignment.MIDDLE_CENTER);


        Label lDocument = new Label("Lebenslauf");
        grid.addComponent(lDocument, 0, 10);

        Upload upload_doc = new Upload("", receiver);
        upload_doc.setButtonCaption("Hochladen");
        upload_doc.addSucceededListener(receiver);
        upload_doc.setAcceptMimeTypes("application/pdf");
        grid.addComponent(upload_doc, 1, 11);
        grid.setComponentAlignment(upload_doc, Alignment.MIDDLE_LEFT);

        Label lDocNotice = new Label("Nur PDF Dateien möglich");
        grid.addComponent(lDocNotice, 0, 11);
        grid.setComponentAlignment(lDocNotice, Alignment.MIDDLE_CENTER);

        Button delete = new Button("Profil löschen");
        Button submit = new Button("Speichern");

        delete.addClickListener(event -> {
            UI.getCurrent().removeWindow(this);
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.DELETION);
        });
        //submit.addClickListener((Button.ClickListener) event -> this.setVisible(false));

        submit.addClickListener(clickEvent -> {
            boolean isValid = true;
            Student studentDTO = new Student();

            studentDTO.setUsername(student.getUsername());

            studentDTO.setUserID(student.getUserID());

            studentDTO.setEmail(emailTf.getValue());


            if (pwTf.getValue().equals("")) {
                studentDTO.setPwHash(student.getPasswort());
            } else if (pwTf.getValue().equals(pwTf2.getValue())) {
                studentDTO.setPasswort(pwTf.getValue());
            } else {
                isValid = false;
            }


            studentDTO.setStudienfach(fachTf.getValue());
            studentDTO.setFachsemester(Integer.parseInt(fachsemesterTF.getValue()));


//            Address a = new Address();
//            if (!tfcity.getValue().equals("")) {
//                a.setStadt(tfcity.getValue());
//
//            } else {
//                isValid = false;
//            }
//            if (!tfcountry.getValue().equals("")) {
//                a.setLand(tfcountry.getValue());
//            } else {
//                isValid = false;
//            }
//            if (!tfhousenumber.getValue().equals("")) {
//                a.setHausnummer(tfhousenumber.getValue());
//            } else {
//                isValid = false;
//            }
//            if (!tfpostalcode.getValue().equals("")) {
//                a.setPlz(tfpostalcode.getValue());
//            } else {
//                isValid = false;
//            }
//            if (!tfstreet.getValue().equals("")) {
//                a.setStrasse(tfstreet.getValue());
//            } else {
//                isValid = false;
//            }

//            a.setAddressid(Session.getCurrentUser().getAddressid());
            studentDTO.setImage(SessionFunctions.getCurrentUser().getImage());
//    studentDTO.setAddress(a);

            try {
                if (isValid) {
                    StudentDAO add = new StudentDAO();
                    Student dto = add.update(studentDTO);
                    SessionFunctions.setCurrentUser(dto);
                    ProfilView.setStudent(dto);

                    //Page.getCurrent().reload();
                    this.close();
                    Notification notification = new Notification("Erfolgreich gespeichert");
                    notification.setPosition(Position.MIDDLE_CENTER);
                    notification.setDelayMsec(3000);
                    notification.show(Page.getCurrent());
                }


            } catch (ValidationException e) {
                isValid = false;
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
            }

            if (!isValid) {
                Notification notification = new Notification("Fehler");
                notification.setPosition(Position.MIDDLE_CENTER);
                notification.setDelayMsec(3000);
                notification.show(Page.getCurrent());
            }

        });

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(delete);
        buttons.addComponent(submit);
        grid.addComponent(buttons, 3, 20);
        buttons.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);
        buttons.setComponentAlignment(submit, Alignment.MIDDLE_RIGHT);

    }

}
