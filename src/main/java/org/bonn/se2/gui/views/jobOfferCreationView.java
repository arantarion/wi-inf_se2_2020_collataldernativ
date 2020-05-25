package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.services.util.Configuration;

public class jobOfferCreationView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws Exception {
        Button startseiteButton = new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button logoutButton = new Button("Logout", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button kverwaltenButton = new Button("Kontoverwaltung", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button speicherButton = new Button("Speichern");

        HorizontalLayout h = new HorizontalLayout();
        addComponent(h);
        setComponentAlignment(h, Alignment.TOP_LEFT);
        h.addComponent(startseiteButton);

        HorizontalLayout h2 = new HorizontalLayout();
        addComponent(h2);
        setComponentAlignment(h2, Alignment.TOP_RIGHT);
        h2.addComponent(logoutButton);
        h2.addComponent(kverwaltenButton);

        HorizontalLayout h3 = new HorizontalLayout();
        addComponent(h3);
        setComponentAlignment(h3, Alignment.BOTTOM_LEFT);

//        Integer userID = SessionFunctions.getCurrentUser().getUserID();
//        Integer companyID = (new CompanyDAO().retrieve(userID)).getcompanyID();
//        Grid<JobOffer> grid = new Grid<>();
//        JobOffer liste =  new OfferDAO().retrieve(companyID);
//        grid.setItems(liste);
//        grid.addColumn(JobOffer::getBereich).setCaption("Bereich");
//        grid.addColumn(JobOffer::getKontakt).setCaption("Kontakt");
//        grid.addColumn(JobOffer::getBeschreibung).setCaption("Beschreibung");
//        grid.addColumn(JobOffer::getName).setCaption("Name");
//        grid.addColumn(JobOffer::getCreationDate).setCaption("Erstellungs Datum");
//        grid.addColumn(JobOffer::getBeginDate).setCaption("Anfangs Datum");
//        grid.addColumn(JobOffer::getGehalt).setCaption("Gehalt");
//        grid.setSizeFull();
//        grid.setHeightMode(HeightMode.UNDEFINED);
//        addComponent(grid);


        HorizontalLayout h4 = new HorizontalLayout();
        addComponent(h4);
        setComponentAlignment(h4, Alignment.BOTTOM_RIGHT);
        h4.addComponent(speicherButton);

        startseiteButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
        });

        logoutButton.addClickListener(e -> {
            LoginControl.logoutUser();
        });

        kverwaltenButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.KVERWALTUNG);
        });

    }
}
