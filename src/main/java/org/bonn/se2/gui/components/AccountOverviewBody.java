package org.bonn.se2.gui.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.objects.dto.*;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Anton Drees
 */

public class AccountOverviewBody extends VerticalLayout {

    public AccountOverviewBody(Student student) {
        try {
            setUp(student);
        } catch (DatabaseException | SQLException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
    }


    public AccountOverviewBody(Company company) {
        try {
            setUp(company);
        } catch (DatabaseException | SQLException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
    }

    public AccountOverviewBody(Admin admin) {
        try {
            setUp(admin);
        } catch (DatabaseException | SQLException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
    }


    private <T extends User> void setUp(T dto) throws DatabaseException, SQLException {
        this.setSizeFull();

        if (dto instanceof Admin) {
            Admin admin = (Admin) dto;

            GridLayout layout = new GridLayout(2, 4);
            layout.setWidth("70%");
            layout.setHeight("100%");
            layout.setSpacing(true);
            this.addComponent(layout);
            this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

            RadioButtonGroup<String> toggle = new RadioButtonGroup<>("Bewerbungen zulassen");
            toggle.setItems("Ja", "Nein");

            this.addComponent(toggle);


        } else if (dto instanceof Student) {

            Student student = (Student) dto;

            GridLayout layout = new GridLayout(2, 4);
            layout.setWidth("70%");
            layout.setHeight("100%");
            layout.setSpacing(true);
            this.addComponent(layout);
            this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

            Label header = new Label("Über " + student.getVollstName());
            header.setWidth("100%");
            layout.addComponent(header, 0, 0, 1, 0);

            Panel studienfach;
            if (student.getStudienfach() != null) {
                studienfach = createPanel(student.getStudienfach(), "Studienfach");
            } else {
                studienfach = createPanel("Studienfach");
            }
            studienfach.setSizeFull();
            studienfach.setIcon(VaadinIcons.BOOK);
            layout.addComponent(studienfach, 0, 1);

            Panel fachsemester;
            if (student.getFachsemester() != null) {
                fachsemester = createPanel(Integer.toString(student.getFachsemester()), "Fachsemester");
            } else {
                fachsemester = createPanel("Fachsemester");
            }
            fachsemester.setSizeFull();
            fachsemester.setIcon(VaadinIcons.CALENDAR);
            layout.addComponent(fachsemester, 1, 1);

            Panel job;
            if (student.getJob() != null) {
                job = createPanel(student.getJob(), "Job");
            } else {
                job = createPanel("Job");
            }
            job.setSizeFull();
            job.setIcon(VaadinIcons.CALENDAR);
            layout.addComponent(job, 0, 2);

            Panel arbeitgeber;
            if (student.getArbeitgeber() != null) {
                arbeitgeber = createPanel(student.getArbeitgeber(), "Arbeitgeber");
            } else {
                arbeitgeber = createPanel("Arbeitgeber");
            }
            arbeitgeber.setSizeFull();
            arbeitgeber.setIcon(VaadinIcons.CALENDAR);
            layout.addComponent(arbeitgeber, 1, 2);

        } else {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setWidth("100%");
            this.addComponent(layout);

            Button offerDeletionButton = new Button("Ausgewählte löschen");
            Button jobofferButton = new Button("Neue Stellenanzeige erstellen");

            Company comp = new CompanyDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID());
            int id = comp.getcompanyID();

            Grid<JobOffer> grid = new Grid<>();
            List<JobOffer> liste = new OfferDAO().retrieveCompanyOffers(id);
            grid.setItems(liste);
            MultiSelectionModel<JobOffer> selectionModel = (MultiSelectionModel<JobOffer>) grid.setSelectionMode(Grid.SelectionMode.MULTI);
            grid.addColumn(JobOffer::getBereich).setCaption("Bereich");
            grid.addColumn(JobOffer::getKontakt).setCaption("Kontakt");
            grid.addColumn(JobOffer::getBeschreibung).setCaption("Beschreibung");
            grid.addColumn(JobOffer::getName).setCaption("Name");
            grid.addColumn(JobOffer::getCreationDate).setCaption("Erstellungs Datum");
            grid.addColumn(JobOffer::getBeginDate).setCaption("Anfangs Datum");
            grid.addColumn(JobOffer::getGehalt).setCaption("Gehalt");
            grid.setSizeFull();
            grid.setHeightMode(HeightMode.UNDEFINED);
            layout.addComponent(grid);

            selectionModel.addMultiSelectionListener(e -> {
                Notification notification = new Notification(e.getAddedSelection().size()
                        + " items added, "
                        + e.getRemovedSelection().size()
                        + " removed.",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setStyleName("mystyle");
                notification.show(Page.getCurrent());

                offerDeletionButton.setEnabled(e.getNewSelection().size() > 0);
                offerDeletionButton.addClickListener(d -> {
                    Set<JobOffer> list = e.getAllSelectedItems();
                    List<JobOffer> s = new ArrayList<>(list);

                    for (JobOffer jobOffer : s) {
                        try {
                            new OfferDAO().delete(jobOffer);
                        } catch (DatabaseException e1) {
                            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e1);
                        }
                    }
                    grid.removeAllColumns();
                    try {
                        List<JobOffer> liste2 = new OfferDAO().retrieveCompanyOffers(id);
                    } catch (DatabaseException | SQLException ex) {
                        Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                                new Throwable().getStackTrace()[0].getMethodName() + " failed", ex);
                    }
                    UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
                });
            });

            HorizontalLayout h3 = new HorizontalLayout();
            addComponent(h3);
            setComponentAlignment(h3, Alignment.BOTTOM_LEFT);
            h3.addComponent(jobofferButton);
            h3.addComponent(offerDeletionButton);
            offerDeletionButton.setEnabled(false);
            this.addComponent(h3);

            jobofferButton.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(Configuration.Views.OFFERCREATION));

        }
    }

    private <T> Panel createPanel(ArrayList<T> list, String name) {
        Panel panel = new Panel(name);
        VerticalLayout layout = new VerticalLayout();
        for (T i : list) {
            layout.addComponent(new Label(VaadinIcons.ARROWS_LONG_RIGHT.getHtml() + " " + i.toString(), ContentMode.HTML));
        }
        panel.setContent(layout);
        return panel;
    }

    private Panel createPanel(String name) {
        Panel panel = new Panel(name);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Hier ist noch nichts"));
        panel.setContent(layout);
        return panel;
    }

    private Panel createPanel(String info, String name) {
        Panel panel = new Panel(name);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label(name + ": " + info));
        panel.setContent(layout);
        return panel;
    }

    private Panel createPanel(StreamResource file, String filename) {
        Panel panel;
        VerticalLayout layout = new VerticalLayout();

        if (file.getBufferSize() <= 0) {
            panel = createPanel("Dokumente");
        } else {
            panel = new Panel("Dokumente");

            if (filename != null && !filename.isEmpty()) {
                Link link = new Link(filename + ".pdf", file);
                layout.addComponent(link);
            } else {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        "Filename is null.");
                return null;
            }
        }

        panel.setContent(layout);
        return panel;
    }

}
