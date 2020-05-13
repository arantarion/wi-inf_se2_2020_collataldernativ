package views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class ProfilView extends VerticalLayout implements View {
	
	
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
	}
	
	public void setUp() { 
		
		Button startseite= new Button("Startseite",FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button logout= new Button("Logout",FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button kverwalten= new Button("Kontoverwaltung", FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button abbruch= new Button("Abbruch",FontAwesome.ARROW_CIRCLE_O_RIGHT);
		Button speichern= new Button("Speichern",FontAwesome.ARROW_CIRCLE_O_RIGHT);
		
		
		HorizontalLayout h= new HorizontalLayout();
		addComponent(h);
		setComponentAlignment(h, Alignment.TOP_LEFT);
		h.addComponent(startseite);
		
		HorizontalLayout h1= new HorizontalLayout();
		addComponent(h1);
		setComponentAlignment(h1, Alignment.TOP_RIGHT);
		h1.addComponent(logout);
		h1.addComponent(kverwalten);
		
		 Grid<Profil> grid = new Grid<>(Profil.class);
	        grid.setSizeFull();
	        grid.setHeightMode(HeightMode.UNDEFINED);
	        addComponent(grid);
		
	        HorizontalLayout h2= new HorizontalLayout();
	        addComponent(h2);
			setComponentAlignment(h2, Alignment.BOTTOM_LEFT);
	        Panel panel=new Panel("Offene Bewerbungen");
			panel.setSizeUndefined();
			panel.setWidth("300px");
			panel.setHeight("300px");
			h2.addComponent(panel);
			//setComponentAlignment(panel, Alignment.BOTTOM_LEFT);
			FormLayout content= new FormLayout();
			content.setSizeUndefined();
			content.setMargin(true);
			panel.setContent(content);	
	        
	        HorizontalLayout h3= new HorizontalLayout();
	        addComponent(h3);
			setComponentAlignment(h3, Alignment.BOTTOM_RIGHT);
			h3.addComponent(abbruch);
			h3.addComponent(speichern);
	}
	
}
