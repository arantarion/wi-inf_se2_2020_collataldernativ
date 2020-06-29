package org.bonn.se2.gui.windows;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;
import org.bonn.se2.services.util.Utils;

public class VisitCompanyWindow extends Window {

    static Image profilbild = new Image("nichts ausgewählt");

    public VisitCompanyWindow(Company dto) {
        this.setUp(dto);
    }

    private void setUp(Company company) {
        VerticalLayout verticalLayout = new VerticalLayout();
        this.setContent(verticalLayout);
        this.setWidth("75%");
        this.center();

        GridLayout grid = new GridLayout(4, 32);
        verticalLayout.addComponent(grid);
        grid.setWidth("95%");
        grid.setColumnExpandRatio(0, 1);
        grid.setColumnExpandRatio(1, 2);
        grid.setColumnExpandRatio(2, 1);
        grid.setColumnExpandRatio(3, 2);
        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 0);

        Label nameLabel = new Label("Firmierung", ContentMode.HTML);
        TextField nameField = new TextField();
        nameField.setWidth("90%");
        nameField.setPlaceholder(company.getName());
        grid.addComponent(nameLabel, 0, 1, 1, 1);
        grid.addComponent(nameField, 2, 1, 3, 1);
        nameField.setWidth("90%");
        grid.setComponentAlignment(nameLabel, Alignment.MIDDLE_CENTER);
    }
}
