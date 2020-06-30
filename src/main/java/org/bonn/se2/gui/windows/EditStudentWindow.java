package org.bonn.se2.gui.windows;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.bonn.se2.gui.views.ProfilView;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
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
        Label labelPhoto = new Label("Profilbild");
        Upload uploadPhoto = new Upload("", receiver);
        uploadPhoto.setButtonCaption("Hochladen");
        uploadPhoto.addSucceededListener(receiver);
        uploadPhoto.setAcceptMimeTypes("image/*");

        if (student.getImage() != null && student.getImage().length > 0) {
            refreshProfilePic(Utils.convertToImg(student.getImage()));
        } else {
            refreshProfilePic(new Image("", resource));
        }

        GridLayout picLayout = new GridLayout(1, 2);
        picLayout.setSpacing(false);
        picLayout.addComponent(profilBildPanel, 0, 0);
        picLayout.addComponent(uploadPhoto, 0, 1);
        picLayout.setComponentAlignment(uploadPhoto, Alignment.MIDDLE_RIGHT);

        grid.addComponent(labelPhoto, 0, 5);
        grid.addComponent(picLayout, 1, 5);
        grid.setComponentAlignment(labelPhoto, Alignment.MIDDLE_CENTER);
        grid.setSpacing(true);

        Label lDocument = new Label("Lebenslauf");
        grid.addComponent(lDocument, 0, 10);

        Upload uploadDocument = new Upload("", receiver);
        uploadDocument.setButtonCaption("Hochladen");
        uploadDocument.addSucceededListener(receiver);
        uploadDocument.setAcceptMimeTypes("application/pdf");
        grid.addComponent(uploadDocument, 1, 11);
        grid.setComponentAlignment(uploadDocument, Alignment.MIDDLE_LEFT);

        Label lDocNotice = new Label("Nur PDF Dateien möglich");
        grid.addComponent(lDocNotice, 0, 11);
        grid.setComponentAlignment(lDocNotice, Alignment.MIDDLE_CENTER);

        Button delete = new Button("Profil löschen");
        Button submit = new Button("Speichern");

        delete.addClickListener(event -> {
            UI.getCurrent().removeWindow(this);
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.DELETION);
        });


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

            studentDTO.setImage(SessionFunctions.getCurrentUser().getImage());

            try {
                if (isValid) {
                    StudentDAO add = new StudentDAO();
                    Student dto = add.update(studentDTO);
                    SessionFunctions.setCurrentUser(dto);
                    ProfilView.setStudent(dto);

                    this.close();
                    Notification notification = new Notification("Erfolgreich gespeichert");
                    notification.setPosition(Position.MIDDLE_CENTER);
                    notification.setDelayMsec(3000);
                    notification.show(Page.getCurrent());
                }


            } catch (DatabaseException e) {
                isValid = false;
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
