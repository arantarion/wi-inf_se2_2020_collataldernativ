package org.bonn.se2.gui.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Anton Drees
 */

public class AccountOverviewBody extends VerticalLayout {

    public AccountOverviewBody(Student student) {
        try {
            setUp(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public AccountOverviewBody(Company company) throws Exception {
        try {
            setUp(company);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T extends User> void setUp(T dto) throws Exception {
        this.setSizeFull();

        if (dto instanceof Student) {

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
            if (student.getFachsemester() != null) {
                job = createPanel(student.getJob(), "Job");
            } else {
                job = createPanel("Job");
            }
            job.setSizeFull();
            job.setIcon(VaadinIcons.CALENDAR);
            layout.addComponent(job, 0, 2);

            Panel arbeitgeber;
            if (student.getFachsemester() != null) {
                arbeitgeber = createPanel(student.getArbeitgeber(), "Arbeitgeber");
            } else {
                arbeitgeber = createPanel("Arbeitgeber");
            }
            arbeitgeber.setSizeFull();
            arbeitgeber.setIcon(VaadinIcons.CALENDAR);
            layout.addComponent(arbeitgeber, 1, 2);

//        StreamResource doc = Utils.convertToPdf(student.getDoc().getFile(), student.getDoc().getDocTitle());
//        Panel documents = createPanel(doc, student.getDoc().getDocTitle());
//        documents.setSizeFull();
//        documents.setIcon(VaadinIcons.FILE);
//        layout.addComponent(documents,0,3);
        } else {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setWidth("100%");
            this.addComponent(layout);

            Button offerDeletionButton = new Button("Ausgewählte löschen");
            Button jobofferButton = new Button("Neue Stellenanzeige erstellen");

            Company comp = new CompanyDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID());
            int ID = comp.getcompanyID();

            Grid<JobOffer> grid = new Grid<>();
            List<JobOffer> liste = new OfferDAO().retrieveCompanyOffers(ID);
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
                Notification.show(e.getAddedSelection().size()
                        + " items added, "
                        + e.getRemovedSelection().size()
                        + " removed.");

                offerDeletionButton.setEnabled(e.getNewSelection().size() > 0);
                offerDeletionButton.addClickListener(d -> {
                    Set<JobOffer> list = e.getAllSelectedItems();
                    List<JobOffer> s = new ArrayList<>(list);
                    for (int i = 0; i < s.size(); i++) {
                        try {
                            new OfferDAO().delete(s.get(i).getJobofferID());
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    grid.removeAllColumns();
                    try {
                        List<JobOffer> liste2 = new OfferDAO().retrieveCompanyOffers(ID);
                    } catch (DatabaseException databaseException) {
                        databaseException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
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

            //this.setComponentAlignment(grid, Alignment.TOP_CENTER);

            jobofferButton.addClickListener(e -> {
                UI.getCurrent().getNavigator().navigateTo(Configuration.Views.OFFERCREATION);
            });
//            kverwaltenButton.addClickListener(e -> {
//                UI.getCurrent().getNavigator().navigateTo(Configuration.Views.KVERWALTUNG);
//            });

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
            if (filename != null || !filename.equals("")) {
                Link link = new Link(filename + ".pdf", file);
                layout.addComponent(link);
            }
        }

        panel.setContent(layout);
        return panel;
    }

}
