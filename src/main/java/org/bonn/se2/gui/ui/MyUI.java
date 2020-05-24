package org.bonn.se2.gui.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.bonn.se2.gui.views.*;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.util.Configuration;
import sun.security.krb5.Config;

import javax.servlet.annotation.WebServlet;
import java.sql.PreparedStatement;

/**
 * The Main UI class of the project this will get started
 * All available views must be added to the navigator object
 * to be accessible.
 *
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer generic class
 * @Theme specifies the CSS theme
 * @Title specifies the tab name of the application.
 * @PreservOnRefresh saves the session when the website is refreshed
 */
@Theme("mytheme")
@Title("Coll@Aldernativ Webapp")
@PreserveOnRefresh
public class MyUI extends UI {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Navigator navi = new Navigator(this, this);

        /*
         * Please add new views to the Configuration -> Views class and use them from there
         */
        navi.addView(Configuration.Views.MAIN, MainView.class);
        navi.addView(Configuration.Views.LOGIN, LoginView.class);
        navi.addView(Configuration.Views.REGIST, RegistrierungsView.class);
        navi.addView(Configuration.Views.PROFIL, ProfilView.class);
        navi.addView(Configuration.Views.KVERWALTUNG, KontoverwaltungView.class);
        navi.addView(Configuration.Views.DELETION, DeletionView.class);
        navi.addView(Configuration.Views.COMPPROFIL, BMWView.class);
        navi.addView(Configuration.Views.OFFERCREATION, jobOfferCreationView.class);

        UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
    }

    public MyUI getMyUI() {
        return (MyUI) UI.getCurrent();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
