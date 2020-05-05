package org.bonn.se2.gui.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.bonn.se2.model.objects.dto.User;

import javax.servlet.annotation.WebServlet;


@Theme("mytheme")
@Title("Coll@Aldernativ Webapp")
@PreserveOnRefresh
public class MyUI extends UI {

    private User user = null;

    private TextField filterText = new TextField();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        filterText.setCaption("filter by name...");

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        layout.addComponents(filterText, clearFilterTextBtn);
        this.setContent(layout);
        //Navigator navi = new Navigator(this, this);

        //navi.addView(Views.MAIN, MainView.class);
        //navi.addView(Views.LOGIN, LoginView.class);

        //UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
    }

    public MyUI getMyUI() {
        return (MyUI) UI.getCurrent();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
