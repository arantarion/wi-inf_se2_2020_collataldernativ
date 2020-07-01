package org.bonn.se2.gui.windows;

import com.vaadin.ui.*;
import org.bonn.se2.model.dao.ToggleDAO;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Marceli Dziadek
 */

public class ToggleFeatureWindow extends Window {

    public ToggleFeatureWindow() {
        this.setUp();
    }

    private void setUp() {
        GridLayout grid1 = new GridLayout(4, 21);
        grid1.setWidth("100%");
        grid1.setSpacing(true);
        grid1.addStyleName("scrollable");
        grid1.addStyleName("gridPadding");
        this.setContent(grid1);
        this.setWidth("75%");
        this.center();

        Label allgemein = new Label("Toggle Feature");
        grid1.addComponent(allgemein, 1, 0);

        RadioButtonGroup<String> toggle = new RadioButtonGroup<>("Bewerbungen zulassen");
        toggle.setItems("Ja", "Nein");
        ToggleDAO dao = null;

        try {
            dao = new ToggleDAO();
        } catch (DatabaseException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }

        try {
            if (dao.retrieve()) {
                toggle.setSelectedItem("Ja");
            } else if (!dao.retrieve()) {
                toggle.setSelectedItem("Nein");
            } else {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        "Nichts geladen");
            }
        } catch (DatabaseException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }

        toggle.addSelectionListener(item -> {
            if (item == null) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                        "Nichts ausgewählt");

            } else if (item.getValue().equals("Ja")) {

                try {
                    ToggleDAO dao2 = new ToggleDAO();
                    dao2.updateToggle(true);
                } catch (DatabaseException e) {
                    Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                            new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
                }

            } else if (item.getValue().equals("Nein")) {

                try {
                    ToggleDAO dao2 = new ToggleDAO();
                    dao2.updateToggle(false);
                } catch (DatabaseException e) {
                    Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                            new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
                }
            }
        });

        grid1.addComponent(toggle, 1, 2);

        Button back = new Button("ToggleFeature verlassen.");

        back.addClickListener(event ->
                UI.getCurrent().removeWindow(this)
        );

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(back);
        grid1.addComponent(buttons, 3, 10);
        buttons.setComponentAlignment(back, Alignment.MIDDLE_RIGHT);

    }
}
