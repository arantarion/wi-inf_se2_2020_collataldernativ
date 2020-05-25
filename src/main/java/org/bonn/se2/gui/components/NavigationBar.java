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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import org.bonn.se2.gui.views.ProfilView;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.ProfilControl;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;
import org.bonn.se2.services.util.UIFunctions;

public class NavigationBar extends HorizontalLayout {

    public NavigationBar() {

        Image logo = createImage();

        this.addComponent(logo);
        this.setComponentAlignment(logo, Alignment.MIDDLE_LEFT);

        MenuBar menuBar = new MenuBar();

        MenuBar.Command userProfile = clickEvent -> {
            ProfilView.setMyProfile(true);
            ProfilControl.studentProfile();
        };

        MenuBar.Command companyProfile = clickEvent -> {
            ProfilView.setMyProfile(true);
            ProfilControl.companyProfile();
        };

        if (SessionFunctions.getCurrentRole().equals(Configuration.Roles.STUDENT)) {
            MenuBar.MenuItem profile = menuBar.addItem("Mein Profil", userProfile);
            profile.setIcon(VaadinIcons.USER);
        } else {
            MenuBar.MenuItem profile = menuBar.addItem("Mein Profil - Company", companyProfile);
            profile.setIcon(VaadinIcons.USER);
        }

//        MenuBar menuBar = new MenuBar();
//        MenuBar.MenuItem verwaltung = menuBar.addItem("Profil verwalten", clickEvent ->
//                ProfilControl.studentProfile());
//        verwaltung.setIcon(VaadinIcons.PENCIL);

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
