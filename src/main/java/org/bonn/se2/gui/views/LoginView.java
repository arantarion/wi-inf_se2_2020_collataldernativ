package org.bonn.se2.gui.views;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.bonn.se2.gui.ui.MyUI;
import org.bonn.se2.model.objects.dto.User;
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

        //Layout auf ges. Bildschirm ausweiten
        this.setSizeFull();

        final TextField userLogin = new TextField();
        userLogin.setCaption("UserID:");

        final PasswordField passwd = new PasswordField();
        passwd.setCaption("Passwort:");

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
        //Panel auf Feldgröße anpassen
        panel.setSizeUndefined();

        loginButton.addClickListener(e -> {
            String login = userLogin.getValue();
            String password = passwd.getValue();

            try {

                LoginControl.checkAuthentication(login, password);

            } catch (InvalidCredentialsException invalidCredentialsException) {

                Notification.show("Dies ist keine gültige Kombination", Notification.Type.ERROR_MESSAGE);
                passwd.setValue("");

            } catch (DatabaseException ex) {
                Notification.show("DB-Fehler", ex.getReason(), Notification.Type.ERROR_MESSAGE);
                userLogin.setValue("");
                passwd.setValue("");
            }

        });

    }

}
