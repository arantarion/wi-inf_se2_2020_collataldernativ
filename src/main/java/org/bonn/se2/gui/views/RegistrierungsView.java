package views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RegistrierungsView extends VerticalLayout implements View {
	
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
	}
	
	public void setUp() {
		Button startseiteButton= new Button("Startseite", FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Label labelAllg= new Label("Bitte füllen Sie alle Felder aus:");
		Button rButton= new Button("Registrieren",FontAwesome.ARROW_CIRCLE_O_RIGHT);
		//CheckBox
		CheckBox chkU= new CheckBox("Unternehmer");
		CheckBox chkS= new CheckBox("Student");
		HorizontalLayout h1= new HorizontalLayout();
		TextField vn; //für Vorname
		TextField nn; //für Nachname
		TextField em; //für Email
		TextField pw1;//für Passwort
		TextField pw2;//für Passwort wiederholen
		
		addComponent(h1);
		setComponentAlignment(h1, Alignment.TOP_LEFT);
		h1.addComponent(startseiteButton);
		addComponent(labelAllg);
		chkU.setValue(false);
		chkS.setValue(false);
		addComponent(chkU);
		addComponent(chkS);
		
		Panel panel=new Panel();
		panel.setSizeUndefined();
		addComponent(panel);
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
		FormLayout content= new FormLayout();
		content.addComponent(vn=new TextField("Vorname:"));
		content.addComponent(nn=new TextField("Nachname:"));
		//content.addComponent(new TextField("Adresse:"));
		content.addComponent(em=new TextField("Email:"));
		content.addComponent(pw1=new TextField("Passwort:"));
		content.addComponent(pw2=new TextField("Passwort wiederholen:"));
		content.addComponent(rButton);
		content.setSizeUndefined();
		
		content.setMargin(true);
		panel.setContent(content);
		
			rButton.addClickListener(e -> {
				if((!vn.getValue().equals("")) && (!nn.getValue().equals("")) && (!em.getValue().equals("")) && (!pw1.getValue().equals("")) && (!pw2.getValue().equals(""))) {
					 addComponent(new Label("Vielen Dank für die Registrierung. Sie können sich nun einloggen"));
					 //addComponent(startseiteButton);
				}
				else {
					addComponent(new Label("Ungültige Eingabe! Bitte überprüfen Sie Ihre Eingabe"));
				}
		
			});
	}
	
}
