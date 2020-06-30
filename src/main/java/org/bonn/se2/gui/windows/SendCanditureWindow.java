package org.bonn.se2.gui.windows;

import com.vaadin.ui.*;
import org.bonn.se2.model.dao.BewerbungsDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Bewerbung;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.SessionFunctions;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Marceli Dziadek
 */

public class SendCanditureWindow extends Window {

    private final JobOffer selectedJobOffer;

    public SendCanditureWindow(JobOffer selectedJobOffer) {
        this.selectedJobOffer = selectedJobOffer;
        this.setUp();
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

        Label allgemein = new Label("Ihre Bewerbung für das Jobangebot '" + selectedJobOffer.getName() + "'!");
        grid.addComponent(allgemein, 1, 0);

        // Motivationsschreiben
        Label motivationLabel = new Label("Motivationsschreiben (optional)");
        TextField motivationTf = new TextField();
        motivationTf.setHeight("200%");
        motivationTf.setPlaceholder("");
        motivationTf.setWidth("100%");
        grid.addComponent(motivationLabel, 0, 3);
        grid.addComponent(motivationTf, 1, 3, 3, 3);
        grid.setComponentAlignment(motivationLabel, Alignment.MIDDLE_CENTER);

        Button submit = new Button("Bewerbung senden");
        Button back = new Button("Zurück zur Hauptseite");

        back.addClickListener(event -> UI.getCurrent().removeWindow(this));

        submit.addClickListener(clickEvent -> {

            int bewerbungsid = 0;

            int studentID = 0;
            try {
                Student st = new StudentDAO().retrieve(SessionFunctions.getCurrentUser().getUsername());
                studentID = st.getStudentID();
            } catch (DatabaseException e1) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        new Throwable().getStackTrace()[0].getMethodName() + " failed", e1);
            }

            Bewerbung bewerbungDTO = new Bewerbung(bewerbungsid, selectedJobOffer.getJobofferID(), selectedJobOffer.getCompanyID(), studentID, LocalDate.now(), motivationTf.getValue());
            try {
                new BewerbungsDAO().create(bewerbungDTO);
                BewerbungsDAO bewerbungDAO = new BewerbungsDAO();
                close();
            } catch (DatabaseException | SQLException e) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
                close();
            }


        });

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(submit);
        buttons.addComponent(back);
        grid.addComponent(buttons, 3, 15);
        buttons.setComponentAlignment(back, Alignment.MIDDLE_RIGHT);
        buttons.setComponentAlignment(submit, Alignment.MIDDLE_RIGHT);

    }
}
