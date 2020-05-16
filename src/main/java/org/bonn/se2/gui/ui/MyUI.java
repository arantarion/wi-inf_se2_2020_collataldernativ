package org.bonn.se2.gui.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.bonn.se2.gui.views.LoginView;
import org.bonn.se2.gui.views.MainView;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.CryptoFunctions;

import javax.servlet.annotation.WebServlet;


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

        navi.addView(Configuration.Views.MAIN, MainView.class);
        navi.addView(Configuration.Views.LOGIN, LoginView.class);

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
