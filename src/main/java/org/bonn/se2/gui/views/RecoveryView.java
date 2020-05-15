package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

public class RecoveryView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }

    public void setUp() {
        Button startseite = new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button abbrechen = new Button("Abbrechen", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        Button pw = new Button("Passwort senden", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        TextField email;

        HorizontalLayout h1 = new HorizontalLayout();
        addComponent(h1);
        setComponentAlignment(h1, Alignment.TOP_LEFT);
        h1.addComponent(startseite);

        Panel panel = new Panel();
        panel.setSizeUndefined();
        panel.setWidth("500px");
        panel.setHeight("400px");
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        HorizontalLayout h2 = new HorizontalLayout();
        addComponent(h2);
        setComponentAlignment(h2, Alignment.MIDDLE_CENTER);


        FormLayout content = new FormLayout();
        //content.addComponent(h2);
        content.addComponent(email = new TextField("Email:"));
        h2.addComponent(abbrechen);
        content.addComponent(h2);
        h2.addComponent(pw);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);

    }

}
