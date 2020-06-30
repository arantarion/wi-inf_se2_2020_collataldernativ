package org.bonn.se2.gui.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.bonn.se2.gui.components.NavigationBar;
import org.bonn.se2.gui.windows.SendCanditureWindow;
import org.bonn.se2.gui.windows.VisitCompanyWindow;
import org.bonn.se2.gui.windows.WatchCanditureWindow;
import org.bonn.se2.model.dao.ToggleDAO;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.SearchControlProxy;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees, Jelena Vetmic
 */

public class MainView extends VerticalLayout implements View {

    protected JobOffer selectedJobOffer = null;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        if (!SessionFunctions.isLoggedIn()) {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
        } else {
            this.setUp();
        }
    }

    public void setUp() {

        NavigationBar navigationBar = new NavigationBar();
        this.addComponent(navigationBar);
        this.setComponentAlignment(navigationBar, Alignment.TOP_CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        HorizontalLayout horizontalLayoutCompany = new HorizontalLayout();
        HorizontalLayout h2 = new HorizontalLayout();
        HorizontalLayout h3 = new HorizontalLayout();
        HorizontalLayout studentLayout = new HorizontalLayout();

        Button suche = new Button("Suchen", VaadinIcons.SEARCH);
        TextField name = new TextField();
        Label label = new Label("Bitte geben Sie ein Stichwort ein:");

        Label labelText = new Label("Willkommen auf Coll@Aldernativ! der zentralen Schnittstelle zwischen Studenten & Unternehmen."
                + " Hier findet jeder seinen Traumjob.");

        addComponent(labelText);
        setComponentAlignment(labelText, Alignment.MIDDLE_CENTER);
        addComponent(horizontalLayout);
        setComponentAlignment(horizontalLayout, Alignment.TOP_RIGHT);

        addComponent(h2);
        setComponentAlignment(h2, Alignment.TOP_LEFT);

        addComponent(horizontalLayoutCompany);
        setComponentAlignment(horizontalLayoutCompany, Alignment.MIDDLE_CENTER);
        horizontalLayoutCompany.addComponent(label);
        horizontalLayoutCompany.addComponent(name);
        horizontalLayoutCompany.addComponent(new Label("&nbsp", ContentMode.HTML)); // Label erstellt, um textfeld und Button zu trennen (Abstand größer ist)
        horizontalLayoutCompany.addComponent(suche);

        final Button bewerbenJetzt = new Button("Direkt zur Bewerbung");
        final Button bewerbenNein = new Button("Bewerbung deaktiviert!");
        final Button bewerbenSehen = new Button("Direkt zu den Bewerbungen");
        bewerbenSehen.setEnabled(false);
        bewerbenJetzt.setEnabled(false);

        bewerbenSehen.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                if (MainView.this.selectedJobOffer == null) {
                    Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                            "MainView.this.selectedJobOffer == null");
                } else {
                    try {
                        if (MainView.this.selectedJobOffer.getCompanyID() == SearchControlProxy.getInstance().getCompanyID()) {
                            Window swap = new WatchCanditureWindow(selectedJobOffer);
                            UI.getCurrent().addWindow(swap);
                        } else {
                            Notification notification = new Notification("Funktion nur für eigene Jobangebote verfügbar.");
                            notification.setPosition(Position.BOTTOM_CENTER);
                            notification.setDelayMsec(4000);
                            notification.show(Page.getCurrent());
                        }

                    } catch (IllegalArgumentException | NullPointerException e) {
                        Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                                new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
                    }
                }
            }
        });


        bewerbenJetzt.addClickListener((Button.ClickListener) event -> {

            if (MainView.this.selectedJobOffer == null) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        "MainView.this.selectedJobOffer == null");
            } else {
                Window swap = new SendCanditureWindow(selectedJobOffer);
                UI.getCurrent().addWindow(swap);
            }

        });

        Grid<JobOffer> grid = new Grid<>();
        List<JobOffer> liste;

        liste = SearchControlProxy.getInstance().getAllOffers();

        grid.setItems(liste);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addComponentColumn(jobOffer -> {
            Button button;
            button = new Button(SearchControlProxy.getInstance().getCompanyName(jobOffer.getCompanyID()));
            button.addClickListener(click -> {
                Window rate = new VisitCompanyWindow(jobOffer.getCompanyID());
                UI.getCurrent().addWindow(rate);
            });
            return button;
        }).setCaption("Unternehmen");

        grid.addColumn(JobOffer::getBereich).setCaption("Bereich");
        grid.addColumn(JobOffer::getKontakt).setCaption("Kontakt");
        grid.addColumn(JobOffer::getBeschreibung).setCaption("Beschreibung");
        grid.addColumn(JobOffer::getName).setCaption("Name");
        grid.addColumn(JobOffer::getCreationDate).setCaption("Erstellungs Datum");
        grid.addColumn(JobOffer::getBeginDate).setCaption("Anfangs Datum");
        grid.addColumn(JobOffer::getGehalt).setCaption("Gehalt");
        grid.setSizeFull();
        grid.setHeightMode(HeightMode.UNDEFINED);
        addComponent(grid);

        name.addValueChangeListener(d -> {
            if (!name.getValue().equals("")) {
                String attribute = name.getValue();
                grid.removeAllColumns();
                List<JobOffer> liste2;

                liste2 = SearchControlProxy.getInstance().getOffersInput(attribute);

                grid.setItems(liste2);
                MultiSelectionModel<JobOffer> selectionModel2 = (MultiSelectionModel<JobOffer>) grid.setSelectionMode(Grid.SelectionMode.MULTI);
                grid.addComponentColumn(jobOffer -> {
                    Button button;
                    button = new Button(SearchControlProxy.getInstance().getCompanyName(jobOffer.getCompanyID()));
                    button.addClickListener(click -> {
                        Window rate = new VisitCompanyWindow(jobOffer.getCompanyID());
                        UI.getCurrent().addWindow(rate);
                    });
                    return button;
                }).setCaption("Unternehmen");

            } else {
                grid.removeAllColumns();
                List<JobOffer> liste3;

                liste3 = SearchControlProxy.getInstance().getAllOffers();

                grid.setItems(liste3);
                MultiSelectionModel<JobOffer> selectionModel3 = (MultiSelectionModel<JobOffer>) grid.setSelectionMode(Grid.SelectionMode.MULTI);
                grid.addComponentColumn(jobOffer -> {
                    Button button;
                    button = new Button(SearchControlProxy.getInstance().getCompanyName(jobOffer.getCompanyID()));
                    button.addClickListener(click -> {
                        Window rate = new VisitCompanyWindow(jobOffer.getCompanyID());
                        UI.getCurrent().addWindow(rate);
                    });
                    return button;
                }).setCaption("Unternehmen");
            }
            grid.addColumn(JobOffer::getBereich).setCaption("Bereich");
            grid.addColumn(JobOffer::getKontakt).setCaption("Kontakt");
            grid.addColumn(JobOffer::getBeschreibung).setCaption("Beschreibung");
            grid.addColumn(JobOffer::getName).setCaption("Name");
            grid.addColumn(JobOffer::getCreationDate).setCaption("Erstellungs Datum");
            grid.addColumn(JobOffer::getBeginDate).setCaption("Anfangs Datum");
            grid.addColumn(JobOffer::getGehalt).setCaption("Gehalt");
        });


        grid.addSelectionListener(event -> {
            if (event.getFirstSelectedItem().isPresent()) {
                selectedJobOffer = (event.getFirstSelectedItem().get());
                if ((SessionFunctions.getCurrentRole().equals(Configuration.Roles.COMPANY)) &&
                        (MainView.this.selectedJobOffer.getCompanyID() == SearchControlProxy.getInstance().getCompanyID())) {
                    bewerbenSehen.setEnabled(true);
                } else {
                    bewerbenSehen.setEnabled(false);
                    bewerbenJetzt.setEnabled(true);
                }
            }
        });

        if (SessionFunctions.getCurrentRole().equals(Configuration.Roles.COMPANY)) {
            addComponent(bewerbenSehen);
        }

        if (SessionFunctions.getCurrentRole().equals(Configuration.Roles.STUDENT)) {
            ToggleDAO dao = null;
            try {
                dao = new ToggleDAO();
            } catch (DatabaseException e) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
            }
            try {
                if (dao.retrieve()) {
                    addComponent(bewerbenJetzt);
                } else if (!dao.retrieve()) {
                    addComponent(bewerbenNein);
                    bewerbenNein.setEnabled(false);
                } else {
                    Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                            "Es wurde nichts geladen");
                }

            } catch (DatabaseException e) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
            }
        }


        if (SessionFunctions.getCurrentRole().equals(Configuration.Roles.COMPANY)) {
            Button sucheStudent = new Button("Suchen", VaadinIcons.SEARCH);
            TextField textStudent = new TextField();
            Label labelStudent = new Label("Bitte geben Sie ein Stichwort ein:");

            addComponent(studentLayout);
            setComponentAlignment(studentLayout, Alignment.MIDDLE_CENTER);
            studentLayout.addComponent(labelStudent);
            studentLayout.addComponent(textStudent);
            studentLayout.addComponent(new Label("&nbsp", ContentMode.HTML));
            studentLayout.addComponent(sucheStudent);

            Grid<Student> gridStudent = new Grid<>();
            List<Student> listeStudent;

            listeStudent = SearchControlProxy.getInstance().getAllStudents();

            gridStudent.setItems(listeStudent);
            gridStudent.setSelectionMode(Grid.SelectionMode.SINGLE);
            gridStudent.addColumn(Student::getVollstName).setCaption("Name");
            gridStudent.addColumn(Student::getStudienfach).setCaption("Bereich");
            gridStudent.addColumn(Student::getArbeitgeber).setCaption("Arbeitgeber");
            gridStudent.addColumn(Student::getJob).setCaption("Rolle");
            gridStudent.setSizeFull();
            gridStudent.setHeightMode(HeightMode.UNDEFINED);
            addComponent(gridStudent);


            textStudent.addValueChangeListener(d -> {
                if (!textStudent.getValue().equals("")) {
                    String attribute = textStudent.getValue();
                    gridStudent.removeAllColumns();
                    List<Student> listeStudent2;

                    listeStudent2 = SearchControlProxy.getInstance().getStudentsInput(attribute);

                    gridStudent.setItems(listeStudent2);


                } else {
                    gridStudent.removeAllColumns();
                    List<Student> listeStudent3;

                    listeStudent3 = SearchControlProxy.getInstance().getAllStudents();

                    gridStudent.setItems(listeStudent3);

                }
                MultiSelectionModel<Student> selectionModel2 = (MultiSelectionModel<Student>) gridStudent.setSelectionMode(Grid.SelectionMode.MULTI);
                gridStudent.addColumn(Student::getVollstName).setCaption("Name");
                gridStudent.addColumn(Student::getStudienfach).setCaption("Bereich");
                gridStudent.addColumn(Student::getArbeitgeber).setCaption("Arbeitgeber");
                gridStudent.addColumn(Student::getJob).setCaption("Rolle");
            });

        }

        addComponent(h3);
        setComponentAlignment(h3, Alignment.MIDDLE_RIGHT);

    }

}
