package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.gui.ui.MyUI;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.util.Configuration;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class CompanyDatenEingabeView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }

    public void setUp() {
        Button startseiteButton = new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Label labelAllg = new Label("Bitte füllen Sie alle Felder aus:");
        Button rButton = new Button("Registrieren", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        HorizontalLayout h1 = new HorizontalLayout();
        TextField cn; //für Name
        TextField url; //für url
        TextField be;//für Beschreibung
        TextField ba;//für Branche
        TextField ap; //für Ansprechpartner

        addComponent(h1);
        setComponentAlignment(h1, Alignment.TOP_LEFT);
        h1.addComponent(startseiteButton);
        addComponent(labelAllg);

        Panel panel = new Panel();
        panel.setSizeUndefined();
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        FormLayout content2 = new FormLayout();
        content2.addComponent(cn = new TextField("Firmenname:"));
        content2.addComponent(url = new TextField("WebURL:"));
        content2.addComponent(be = new TextField("Beschreibung:"));
        content2.addComponent(ba = new TextField("Branche (Optional):"));
        content2.addComponent(ap = new TextField("Ansprechpartner (Optional):"));
        content2.addComponent(rButton);
        content2.setSizeUndefined();

        content2.setMargin(true);
        panel.setContent(content2);

        rButton.addClickListener(e -> {
            if (!cn.getValue().equals("") && !url.getValue().equals("") && !be.getValue().equals("")) {
                addComponent(new Label("Vielen Dank für die Registrierung. Sie können sich nun einloggen"));
                addComponent(startseiteButton);
                Company user = new Company(cn.getValue(),be.getValue(),url.getValue(),1);
                //User user = ((MyUI) UI.getCurrent()).getUser();
                System.out.println(user);
            } else {
                Notification.show("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe.", Notification.Type.ERROR_MESSAGE);
            }

        });

        startseiteButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
        });
    }

    public static User generateCompany(Company user) throws Exception {
        Company dto = new CompanyDAO().create(user);

        return dto;
    }
}
