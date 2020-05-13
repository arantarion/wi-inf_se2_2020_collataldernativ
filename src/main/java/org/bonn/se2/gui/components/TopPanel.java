package org.bonn.se2.gui.components;

import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se2.gui.ui.MyUI;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.services.util.UIFunctions;

import java.io.File;

public class TopPanel extends HorizontalLayout {

    public TopPanel() {
        this.setSizeFull();

        Label headLabel = new Label("<h2>Coll@Aldernativ - <i> Stepstone für arme</i></h2>", ContentMode.HTML);
        headLabel.setSizeUndefined();

        this.addComponent(headLabel);
        this.setComponentAlignment(headLabel, Alignment.TOP_LEFT);

        HorizontalLayout horLayout = new HorizontalLayout();
        User user = ((MyUI) UI.getCurrent()).getUser();

        String vorname = null;
        if (user != null) {
            vorname = user.getUsername();
        }

        Label logLabel = new Label("Welcome: " + vorname + "!");
        logLabel.setSizeUndefined();

        horLayout.addComponent(logLabel);
        horLayout.setComponentAlignment(logLabel, Alignment.MIDDLE_CENTER);

        MenuBar bar = new MenuBar();
        MenuBar.MenuItem item1 = bar.addItem("Menu", null);

        item1.addItem("Logout", FontAwesome.SIGN_OUT, (MenuBar.Command) menuItem -> LoginControl.logoutUser());

        horLayout.addComponent(bar);
        this.addComponent(horLayout);
        this.setComponentAlignment(horLayout, Alignment.TOP_RIGHT);

    }

    private Image createImage() {
        FileResource resource = new FileResource(new File(
                "/WEB-INF/images/image.png"));
        Image icon = new Image(null, resource);
        icon.addClickListener(e -> UIFunctions.gotoMain());
        return icon;
    }

}
