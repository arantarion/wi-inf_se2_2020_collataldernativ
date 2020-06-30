package org.bonn.se2.gui.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees, Jelena Vetmic
 */

public class RecoveryView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }

    public void setUp() {
        Button startseite = new Button("Startseite", VaadinIcons.ARROW_CIRCLE_RIGHT_O);
        Button abbrechen = new Button("Abbrechen", VaadinIcons.ARROW_CIRCLE_RIGHT_O);
        Button pw = new Button("Passwort senden", VaadinIcons.ARROW_CIRCLE_RIGHT_O);
        TextField email = new TextField("Email:");

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
        content.addComponent(email);
        h2.addComponent(abbrechen);
        content.addComponent(h2);
        h2.addComponent(pw);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);

    }
}
