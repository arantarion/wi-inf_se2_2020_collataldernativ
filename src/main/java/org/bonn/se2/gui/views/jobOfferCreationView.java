package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

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

        TextField bereich;
        TextField kontakt;
        TextField beschreibung;
        TextField name;
        TextField gehalt;

        Company liste = new CompanyDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID());
        int ID = liste.getcompanyID();

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

        Panel panel = new Panel("Stellenangebot Einstellen");
        panel.setSizeUndefined();
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        FormLayout content = new FormLayout();
        content.addComponent(bereich = new TextField("Bereich:"));
        content.addComponent(kontakt = new TextField("Kontakt:"));
        content.addComponent(beschreibung = new TextField("Beschreibung"));
        content.addComponent(name = new TextField("Name"));
        content.addComponent(gehalt = new TextField("Gehalt"));
        content.addComponent(speicherButton);
        content.setSizeUndefined();

        content.setMargin(true);
        panel.setContent(content);


        HorizontalLayout h4 = new HorizontalLayout();
        addComponent(h4);
        setComponentAlignment(h4, Alignment.BOTTOM_RIGHT);

        startseiteButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
        });

        logoutButton.addClickListener(e -> {
            LoginControl.logoutUser();
        });

        kverwaltenButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.KVERWALTUNG);
        });

        speicherButton.addClickListener(e -> {
            try {
                JobOffer dto = new JobOffer(bereich.getValue(), kontakt.getValue(), beschreibung.getValue(), name.getValue(), gehalt.getValue());
                dto.setCompanyID(ID);
                System.out.println(dto);
                new OfferDAO().create(dto);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });

    }
}
