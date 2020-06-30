package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.bonn.se2.gui.components.NavigationBar;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class JobOfferCreationView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (DatabaseException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
    }

    public void setUp() throws DatabaseException {
        NavigationBar navigationBar = new NavigationBar();
        this.addComponent(navigationBar);
        this.setComponentAlignment(navigationBar, Alignment.TOP_CENTER);

        Button speicherButton = new Button("Speichern");

        TextField bereich = new TextField("Bereich:");
        TextField kontakt = new TextField("Kontakt:");
        RichTextArea beschreibung = new RichTextArea("Beschreibung:");
        TextField name = new TextField("Name:");
        TextField gehalt = new TextField("Gehalt (Optional):");
        DateField beginDate = new DateField("Start Datum:");

        Company liste = new CompanyDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID());
        int id = liste.getcompanyID();

        Panel panel = new Panel("Stellenangebot Einstellen");
        panel.setSizeUndefined();
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        FormLayout content = new FormLayout();
        content.addComponent(bereich);
        content.addComponent(kontakt);
        content.addComponent(beschreibung);
        content.addComponent(name);
        content.addComponent(gehalt);
        content.addComponent(beginDate);
        content.addComponent(speicherButton);
        content.setSizeUndefined();

        content.setMargin(true);
        panel.setContent(content);


        HorizontalLayout h4 = new HorizontalLayout();
        addComponent(h4);
        setComponentAlignment(h4, Alignment.BOTTOM_RIGHT);

        speicherButton.addClickListener(e -> {
            try {
                if ((!bereich.getValue().equals("")) && !kontakt.getValue().equals("") && !beschreibung.getValue().equals("")) {
                    JobOffer dto = new JobOffer(bereich.getValue(), kontakt.getValue(), beschreibung.getValue(), name.getValue(), gehalt.getValue(), beginDate.getValue());
                    dto.setCompanyID(id);
                    UI.getCurrent().getNavigator().navigateTo(Configuration.Views.PROFIL);
                    UI.getCurrent().getPage().reload();
                    new OfferDAO().create(dto);
                } else {
                    addComponent(new Label("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe"));
                }
            } catch (DatabaseException | SQLException exception) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        new Throwable().getStackTrace()[0].getMethodName() + " failed", exception);
            }

        });
    }
}
