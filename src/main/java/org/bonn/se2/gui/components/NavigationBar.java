/*
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer
 *
 *
 */

package org.bonn.se2.gui.components;

import com.vaadin.event.MouseEvents;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.UIFunctions;

public class NavigationBar extends HorizontalLayout {

    public NavigationBar() {

        Image logo = createImage();

        this.addComponent(logo);
        this.setComponentAlignment(logo, Alignment.MIDDLE_LEFT);

        MenuBar menuBar = new MenuBar();
        MenuBar.MenuItem help = menuBar.addItem("Profil verwalten", clickEvent ->
                UI.getCurrent().getNavigator().navigateTo(Configuration.Views.PROFIL));
        help.setIcon(VaadinIcons.PENCIL);

        MenuBar.MenuItem logout = menuBar.addItem("Logout", clickEvent -> LoginControl.logoutUser());
        logout.setIcon(VaadinIcons.SIGN_OUT);

        this.addComponent(menuBar);
        this.setComponentAlignment(menuBar, Alignment.MIDDLE_RIGHT);
    }

    private Image createImage() {
        ThemeResource themeResource = new ThemeResource("images/logo.png");
        Image logo = new Image(null, themeResource);
        logo.setWidth("250px");
        logo.addClickListener((MouseEvents.ClickListener) event -> {
            UIFunctions.gotoMain();
        });
        return logo;
    }

}
