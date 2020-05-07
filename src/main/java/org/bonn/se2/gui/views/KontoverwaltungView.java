package views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class KontoverwaltungView extends VerticalLayout implements View {
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
	}
	
	public void setUp() {
		Button startseiteButton=new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button log= new Button("Logout", FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button profil= new Button("Profil", FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button konto= new Button("Konto löschen", FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button z= new Button("Zurück", FontAwesome.ARROW_CIRCLE_O_LEFT);
		Button s= new Button("Speichern",FontAwesome.ARROW_CIRCLE_O_RIGHT);
		
		TextField palt;
		TextField pneu;
		TextField pneu2;
		
		CheckBox j= new CheckBox("regelmäßig Emails über neue Angebote bekommen");
		CheckBox n= new CheckBox("Alle Benachrichtigungen ausschalten");
		
		HorizontalLayout h= new HorizontalLayout();
		HorizontalLayout h1= new HorizontalLayout();
		HorizontalLayout h2= new HorizontalLayout();
		
		addComponent(h);
		setComponentAlignment(h,Alignment.TOP_LEFT);
		h.addComponent(startseiteButton);
		
		addComponent(h1);
		setComponentAlignment(h1,Alignment.MIDDLE_LEFT);
		j.setValue(false);
		n.setValue(false);
		addComponent(j);
		addComponent(n);
		
		Panel panel=new Panel("Passwort ändern");
		panel.setSizeUndefined();
		addComponent(panel);
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
		FormLayout content= new FormLayout();
		content.addComponent(palt=new TextField("Altes Passwort:"));
		content.addComponent(pneu=new TextField("Neues Passwort:"));
		content.addComponent(pneu2= new TextField("Neues Passwort wiederholen"));
		content.setSizeUndefined();
		
		content.setMargin(true);
		panel.setContent(content);
		
		addComponent(h1);
		setComponentAlignment(h1,Alignment.BOTTOM_LEFT);
		h1.addComponent(konto);
		
		addComponent(h2);
		setComponentAlignment(h2,Alignment.BOTTOM_RIGHT);
		h2.addComponent(z);
		h2.addComponent(s);
		
		s.addClickListener(e -> {
			if((!palt.getValue().equals("")) && (!pneu.getValue().equals("")) && (!pneu2.getValue().equals("")) && pneu.getValue().equals(pneu2.getValue())) {
				 addComponent(new Label("Das Passwort wurde erfolgreich geändert."));
				 //addComponent(startseiteButton);
			}
			else {
				addComponent(new Label("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe"));
			}
	
		});
		
	}
}
