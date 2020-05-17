package org.bonn.se2.gui.views;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.gui.ui.MyUI;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.model.objects.dto.UserAtLogin;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;

public class LoginView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        User user = ((MyUI) UI.getCurrent()).getUser();

        if (user != null) {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
        }

        this.setUp();
    }

    private void setUp() {

        this.setSizeFull();

        final TextField userLogin = new TextField();
        userLogin.setCaption("UserID:");
        userLogin.setPlaceholder("E-Mail oder Username");

        final PasswordField passwd = new PasswordField();
        passwd.setCaption("Passwort:");
        passwd.setPlaceholder("Passwort");

        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(userLogin, passwd);

        Panel panel = new Panel("Bitte Login Daten angeben:");

        this.addComponent(panel);
        this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        panel.setContent(layout);

        Button loginButton = new Button("Login", FontAwesome.SEND);
        layout.addComponent(loginButton);
        layout.setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);


        Button registrierungsButton = new Button("Registrierung", FontAwesome.ARROW_CIRCLE_O_RIGHT);
        layout.addComponent(registrierungsButton);
        layout.setComponentAlignment(registrierungsButton, Alignment.MIDDLE_CENTER);

        panel.setSizeUndefined();

        loginButton.addClickListener(e -> {
            String login = userLogin.getValue();
            String password = passwd.getValue();

            try {
                LoginControl.checkAuthentication(new UserAtLogin(login, password));
            } catch (InvalidCredentialsException ex) {
                Notification.show("Die Zugangsdaten sind nicht korrekt", Notification.Type.ERROR_MESSAGE);
                passwd.setValue("");
            } catch (DatabaseException ex) {
                Notification.show("DB-Fehler", ex.getReason(), Notification.Type.ERROR_MESSAGE);
                userLogin.setValue("");
                passwd.setValue("");
            }

        });


        registrierungsButton.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.REGIST);
        });


    }

}
