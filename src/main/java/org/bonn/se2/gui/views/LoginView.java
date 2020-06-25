package org.bonn.se2.gui.views;

import org.bonn.se2.gui.ui.MyUI;
import org.bonn.se2.model.dao.ToggleDAO;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.model.objects.dto.UserAtLogin;
import org.bonn.se2.process.control.LoginControl;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Anton Drees
 */

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
    	//TODO TOGGLE
    	/*
    	RadioButtonGroup<String> toggle = new RadioButtonGroup<>("Bewerbungen zulassen");
        toggle.setItems("Ja", "Nein");
        ToggleDAO dao = null;
		try {
			dao = new ToggleDAO();
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			if( dao.retrieve() == true) {
				toggle.setSelectedItem("Ja"); 
			} else if (dao.retrieve() == false) {
				toggle.setSelectedItem("Nein"); 
			} else {
				System.out.println("Nix geladen");
			}
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        toggle.addSelectionListener(item -> {
        	if(item == null) {
        		System.out.println("Nix gewählt");
        	} else if (item.getValue() == "Ja"){
        		try {
        			ToggleDAO dao2 = new ToggleDAO();
					dao2.updateToggle(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	} else if (item.getValue() == "Nein") {
				try {
					ToggleDAO dao2 = new ToggleDAO();
					dao2.updateToggle(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        	} else {
        		
        	}
        		
        });
        	
        
        this.addComponent(toggle);*/

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
                Notification notification = new Notification("Die Zugangsdaten sind nicht korrekt", Notification.Type.ERROR_MESSAGE);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.setDelayMsec(4000);
                notification.show(Page.getCurrent());
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
