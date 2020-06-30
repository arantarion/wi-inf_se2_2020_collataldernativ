package org.bonn.se2.gui.windows;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.BewerbungsDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Bewerbung;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Marceli Dziadek
 */

public class WatchCanditureWindow extends Window {

    private final JobOffer selectedJobOffer;
    private Bewerbung selectedBewerbung;

    public WatchCanditureWindow(JobOffer selectedJobOffer) {
        this.selectedJobOffer = selectedJobOffer;
        this.setUp();
    }

    private void setUp() {
        VerticalLayout grid = new VerticalLayout();
        grid.setWidth("100%");
        grid.setSpacing(true);
        grid.addStyleName("scrollable");
        grid.addStyleName("gridPadding");
        this.setContent(grid);
        this.setWidth("75%");
        this.center();

        Label allgemein = new Label("Eingegangene Bewerbung für Ihr Jobangebot '" + selectedJobOffer.getName() + "'!");
        grid.addComponent(allgemein);

        Grid<Bewerbung> gridBewerbung = new Grid<>();
        List<Bewerbung> liste = null;
        try {
            liste = new BewerbungsDAO().retrieveCompanyBewerbungJobOffer(selectedJobOffer.getCompanyID(), selectedJobOffer.getJobofferID());
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
        gridBewerbung.setItems(liste);
        gridBewerbung.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridBewerbung.addColumn(Bewerbung::getBewerbungsID).setCaption("BewerbungsID");
        ///gridBewerbung.addColumn(Bewerbung::getStudentID).setCaption("StudentID");
        gridBewerbung.addComponentColumn(Bewerbung -> {
            Label label = null;
            try {
                label = new Label((new StudentDAO().retrieve(Bewerbung.getStudentID())).getUsername());
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            return label;
        }).setCaption("Student");
        gridBewerbung.addColumn(Bewerbung::getNotes).setCaption("Motivationsschreiben");
        gridBewerbung.addColumn(Bewerbung::getBewerbungsdatum).setCaption("Bewerbungsdatum");
        gridBewerbung.setSizeFull();
        gridBewerbung.setHeightMode(HeightMode.UNDEFINED);

        gridBewerbung.addSelectionListener(event -> {
            if (event.getFirstSelectedItem().isPresent()) {
                selectedBewerbung = (event.getFirstSelectedItem().get());
                //TODO
            } else {
                //TODO
            }

        });

        grid.addComponent(gridBewerbung);

        Button submit = new Button("Bewerbung senden");
        Button back = new Button("Zurück zur Hauptseite");

        back.addClickListener(event -> UI.getCurrent().removeWindow(this));
        //submit.addClickListener((Button.ClickListener) event -> this.setVisible(false));

        submit.addClickListener(clickEvent -> {
            //TODO
        });

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(submit);
        buttons.addComponent(back);
        //grid.addComponent(buttons);
        buttons.setComponentAlignment(back, Alignment.MIDDLE_RIGHT);
        buttons.setComponentAlignment(submit, Alignment.MIDDLE_RIGHT);

    }
}
