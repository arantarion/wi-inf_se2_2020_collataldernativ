package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.bonn.se2.gui.components.NavigationBar;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class jobOfferCreationView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            this.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws Exception {
        NavigationBar navigationBar = new NavigationBar();
        this.addComponent(navigationBar);
        this.setComponentAlignment(navigationBar, Alignment.TOP_CENTER);

        Button speicherButton = new Button("Speichern");

        TextField bereich;
        TextField kontakt;
        RichTextArea beschreibung;
        TextField name;
        TextField gehalt;
        DateField beginDate;

        Company liste = new CompanyDAO().retrieve((SessionFunctions.getCurrentUser()).getUserID());
        int ID = liste.getcompanyID();

        Panel panel = new Panel("Stellenangebot Einstellen");
        panel.setSizeUndefined();
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        FormLayout content = new FormLayout();
        content.addComponent(bereich = new TextField("Bereich:"));
        content.addComponent(kontakt = new TextField("Kontakt:"));
        content.addComponent(beschreibung = new RichTextArea("Beschreibung:"));
        content.addComponent(name = new TextField("Name:"));
        content.addComponent(gehalt = new TextField("Gehalt (Optional):"));
        content.addComponent(beginDate = new DateField("Start Datum:"));
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
                    dto.setCompanyID(ID);
                    UI.getCurrent().getNavigator().navigateTo(Configuration.Views.PROFIL);
                    new OfferDAO().create(dto);
                } else {
                    addComponent(new Label("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe"));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });

    }
}
