package views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BMWView extends VerticalLayout implements View{
	
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
	}
	
	public void setUp() {
		Button startseite= new Button("Startseite",FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button log= new Button("Login",FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button registrieren= new Button("Registrierung", FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button suche = new Button("Suchen",FontAwesome.SEARCH);
		Button eb= new Button("Email Bewerbung",FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button z= new Button("Zurück", FontAwesome.ARROW_CIRCLE_O_RIGHT);
		  final TextField textField= new TextField();
		
		HorizontalLayout h= new HorizontalLayout();
		addComponent(h);
		setComponentAlignment(h, Alignment.TOP_LEFT);
		h.addComponent(startseite);
		
		 HorizontalLayout h2= new HorizontalLayout();
			addComponent(h2);
			setComponentAlignment(h2, Alignment.TOP_RIGHT);
			h2.addComponent(log);
			h2.addComponent(registrieren);
	     
		
		HorizontalLayout h1= new HorizontalLayout();
		 h1.addComponent(textField);
	     h1.addComponent(new Label("&nbsp", ContentMode.HTML));
	     h1.addComponent(suche);
	     addComponent(h1);
	     setComponentAlignment(h1, Alignment.TOP_CENTER);
	     
	     HorizontalLayout h4= new HorizontalLayout();
	     addComponent(h4);
	     setComponentAlignment(h4, Alignment.MIDDLE_LEFT);
	     Panel panel=new Panel("Stellenanzeigen:");
	     panel.setSizeUndefined();
	     panel.setWidth("300px");
	     panel.setHeight("300px");
	     h4.addComponent(panel);
	     FormLayout content= new FormLayout();
	     content.setSizeUndefined();
	     content.setMargin(true);
	     panel.setContent(content);
			
	     HorizontalLayout h5= new HorizontalLayout();
	     addComponent(h5);
	     setComponentAlignment(h5, Alignment.BOTTOM_LEFT);
	     h5.addComponent(eb);
			
	     HorizontalLayout h6= new HorizontalLayout();
	     addComponent(h6);
	     setComponentAlignment(h6, Alignment.BOTTOM_RIGHT);
	     h6.addComponent(z);
	}
}
