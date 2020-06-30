package org.bonn.se2.gui.components;

import com.vaadin.event.MouseEvents;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.bonn.se2.gui.views.ProfilView;
import org.bonn.se2.gui.windows.ToggleFeatureWindow;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.ProfilControl;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;
import org.bonn.se2.services.util.UIFunctions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */


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
        if (SessionFunctions.getCurrentUser().getUserID() == 200) {
            MenuBar.MenuItem toggle = menuBar.addItem("ToggleFeature", clickEvent -> toggle());
        }

        if (SessionFunctions.getCurrentRole().equals(Configuration.Roles.STUDENT)) {
            MenuBar.MenuItem profile = menuBar.addItem("Mein Profil verwalten", userProfile);
            profile.setIcon(VaadinIcons.USER);
        } else {
            MenuBar.MenuItem profile = menuBar.addItem("Mein Profil verwalten", companyProfile);
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

    private void toggle() {
        try {
            Window swap = new ToggleFeatureWindow();
            UI.getCurrent().addWindow(swap);
        } catch (IllegalArgumentException | NullPointerException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    new Throwable().getStackTrace()[0].getMethodName() + " failed", e);
        }
    }

    private Image createImage() {
        ThemeResource themeResource = new ThemeResource("images/logo_hd_3.png");
        Image logo = new Image(null, themeResource);
        logo.setWidth("230px");
        logo.addStyleName("logo");
        logo.addClickListener((MouseEvents.ClickListener) event -> {
            UIFunctions.gotoMain();
        });
        return logo;
    }

}
