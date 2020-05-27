package org.bonn.se2.gui.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.bonn.se2.gui.components.NavigationBar;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.LoginControl;
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
 * @Programmer
 */

public class BMWView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws Exception {
        NavigationBar navigationBar = new NavigationBar();
        this.addComponent(navigationBar);
        this.setComponentAlignment(navigationBar, Alignment.TOP_CENTER);

        //Button startseiteButton = new Button("Startseite", VaadinIcons.ARROW_CIRCLE_RIGHT);
        //Button logoutButton = new Button("Logout", VaadinIcons.ARROW_CIRCLE_RIGHT);
        Button kverwaltenButton = new Button("Kontoverwaltung", VaadinIcons.ARROW_CIRCLE_RIGHT);
        Button jobofferButton = new Button("Neue Stellenanzeige erstellen");
        Button offerDeletionButton = new Button("Ausgewählte löschen");

        HorizontalLayout h = new HorizontalLayout();
        addComponent(h);
        setComponentAlignment(h, Alignment.TOP_LEFT);
        //h.addComponent(startseiteButton);

        HorizontalLayout h2 = new HorizontalLayout();
        addComponent(h2);
        setComponentAlignment(h2, Alignment.TOP_RIGHT);
        //h2.addComponent(logoutButton);
        h2.addComponent(kverwaltenButton);


        HorizontalLayout h4 = new HorizontalLayout();
        addComponent(h4);
        setComponentAlignment(h4, Alignment.MIDDLE_LEFT);

        Company comp = new CompanyDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID());
        int ID = comp.getcompanyID();

        Grid<JobOffer> grid = new Grid<>();
        List<JobOffer> liste =  new OfferDAO().retrieveCompanyOffers(ID);
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
        addComponent(grid);

        selectionModel.addMultiSelectionListener(e -> {
            Notification.show(e.getAddedSelection().size()
                    + " items added, "
                    + e.getRemovedSelection().size()
                    + " removed.");

           offerDeletionButton.setEnabled(e.getNewSelection().size() > 0);
            offerDeletionButton.addClickListener(d -> {
                Set<JobOffer> list = e.getAllSelectedItems();
                List<JobOffer> s = new ArrayList<>(list);
                for(int i = 0; i< s.size(); i++){
                    try {
                        new OfferDAO().delete(s.get(i).getJobofferID());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                grid.removeAllColumns();
                try {
                    List<JobOffer> liste2 =  new OfferDAO().retrieveCompanyOffers(ID);
                } catch (DatabaseException databaseException) {
                    databaseException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
            });
        });


        FormLayout content = new FormLayout();
        content.setSizeUndefined();
        content.setMargin(true);


        HorizontalLayout h3 = new HorizontalLayout();
        addComponent(h3);
        setComponentAlignment(h3, Alignment.BOTTOM_LEFT);
        h3.addComponent(jobofferButton);
        h3.addComponent(offerDeletionButton);
        offerDeletionButton.setEnabled(false);


        kverwaltenButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.KVERWALTUNG);
        });

        jobofferButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.OFFERCREATION);
        });
    }
}
