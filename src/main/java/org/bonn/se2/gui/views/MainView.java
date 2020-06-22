package org.bonn.se2.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.bonn.se2.gui.components.NavigationBar;
import org.bonn.se2.gui.windows.SendCanditureWindow;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;

import java.util.List;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees, Jelena Vetmic
 */

public class MainView extends VerticalLayout implements View {
	
	protected JobOffer selectedJobOffer = null;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        if (!SessionFunctions.isLoggedIn()) {
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
        } else {
            try {
                this.setUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setUp() {

        NavigationBar navigationBar = new NavigationBar();
        this.addComponent(navigationBar);
        this.setComponentAlignment(navigationBar, Alignment.TOP_CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        HorizontalLayout h2 = new HorizontalLayout();
        HorizontalLayout h3 = new HorizontalLayout();

        //Erzeugung der Variablen
        Button suche = new Button("Suchen", FontAwesome.SEARCH);
        TextField name = new TextField();
        Label label = new Label("Bitte geben Sie ein Stichwort ein:");
        //Label username = new Label((SessionFunctions.getCurrentUser()).getUsername());
        //Label role = new Label(SessionFunctions.getCurrentRole());


        Label labelText = new Label("Willkommen auf Coll@Aldernativ! der zentralen Schnittstelle zwischen Studenten & Unternehmen."
                + " Hier findet jeder seinen Traumjob.");


        addComponent(labelText);
        setComponentAlignment(labelText, Alignment.MIDDLE_CENTER);
        addComponent(horizontalLayout);
        setComponentAlignment(horizontalLayout, Alignment.TOP_RIGHT);

        //Links oben
        addComponent(h2);
        setComponentAlignment(h2, Alignment.TOP_LEFT);

        //Mitte
        addComponent(horizontalLayout1);
        setComponentAlignment(horizontalLayout1, Alignment.MIDDLE_CENTER);
        horizontalLayout1.addComponent(label);
        horizontalLayout1.addComponent(name);
        horizontalLayout1.addComponent(new Label("&nbsp", ContentMode.HTML)); // Label erstellt, um textfeld und Button zu trennen (Abstand größer ist)
        horizontalLayout1.addComponent(suche);
        
        final Button bewerbenJetzt = new Button("Direkt zur Bewerbung");
        bewerbenJetzt.setEnabled(false);
        if (SessionFunctions.getCurrentRole() == Configuration.Roles.COMPANY){
        	bewerbenJetzt.setVisible(false);
        }
        
        bewerbenJetzt.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
		        
				
				if (MainView.this.selectedJobOffer == null) {
	        		return;
	        	} else {
	            	Window swap = new SendCanditureWindow(selectedJobOffer);
	            	UI.getCurrent().addWindow(swap);
	        	}
				
			}
		});
        Company co = null;
		try {
			co = new CompanyDAO().retrieve(SessionFunctions.getCurrentUser().getUsername());
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final int companyID = co.getcompanyID();

        Grid<JobOffer> grid = new Grid<>();
        List<JobOffer> liste = null;
        try {
        	 if (SessionFunctions.getCurrentRole() == Configuration.Roles.COMPANY){
             	liste = new OfferDAO().retrieveCompanyOffers(companyID);
             } else {
            liste = new OfferDAO().retrieveAll();
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        grid.setItems(liste);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(JobOffer::getBereich).setCaption("Bereich");
        grid.addColumn(JobOffer::getKontakt).setCaption("Kontakt");
        grid.addColumn(JobOffer::getBeschreibung).setCaption("Beschreibung");
        grid.addColumn(JobOffer::getName).setCaption("Name");
        grid.addColumn(JobOffer::getCreationDate).setCaption("Erstellungs Datum");
        grid.addColumn(JobOffer::getBeginDate).setCaption("Anfangs Datum");
        grid.addColumn(JobOffer::getGehalt).setCaption("Gehalt");
        grid.setSizeFull();
        grid.setHeightMode(HeightMode.UNDEFINED);
        addComponent(grid);

        name.addValueChangeListener(d -> {
            if (!name.getValue().equals("")) {
                String attribute = name.getValue();
                grid.removeAllColumns();
                List<JobOffer> liste2 = null;
                try {
                	if (SessionFunctions.getCurrentRole() == Configuration.Roles.COMPANY){
                		liste2 = new OfferDAO().retrieveCompanyOffersbyID(attribute, companyID);
                	} else {
                		liste2 = new OfferDAO().retrieveCompanyOffers(attribute);
                	}
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                grid.setItems(liste2);
                MultiSelectionModel<JobOffer> selectionModel2 = (MultiSelectionModel<JobOffer>) grid.setSelectionMode(Grid.SelectionMode.MULTI);
                grid.addColumn(JobOffer::getBereich).setCaption("Bereich");
                grid.addColumn(JobOffer::getKontakt).setCaption("Kontakt");
                grid.addColumn(JobOffer::getBeschreibung).setCaption("Beschreibung");
                grid.addColumn(JobOffer::getName).setCaption("Name");
                grid.addColumn(JobOffer::getCreationDate).setCaption("Erstellungs Datum");
                grid.addColumn(JobOffer::getBeginDate).setCaption("Anfangs Datum");
                grid.addColumn(JobOffer::getGehalt).setCaption("Gehalt");
                //addComponent(new Label(" erfolgreiche Eingabe! Suche wird gestartet"));
            } else {
                grid.removeAllColumns();
                List<JobOffer> liste3 = null;
                try {
                	if (SessionFunctions.getCurrentRole() == Configuration.Roles.COMPANY){
                     	liste3 = new OfferDAO().retrieveCompanyOffers(companyID);
                     } else {
                    	liste3 = new OfferDAO().retrieveAll();
                     }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                grid.setItems(liste3);
                MultiSelectionModel<JobOffer> selectionModel3 = (MultiSelectionModel<JobOffer>) grid.setSelectionMode(Grid.SelectionMode.MULTI);
                grid.addColumn(JobOffer::getBereich).setCaption("Bereich");
                grid.addColumn(JobOffer::getKontakt).setCaption("Kontakt");
                grid.addColumn(JobOffer::getBeschreibung).setCaption("Beschreibung");
                grid.addColumn(JobOffer::getName).setCaption("Name");
                grid.addColumn(JobOffer::getCreationDate).setCaption("Erstellungs Datum");
                grid.addColumn(JobOffer::getBeginDate).setCaption("Anfangs Datum");
                grid.addColumn(JobOffer::getGehalt).setCaption("Gehalt");
            }
        });
        
        grid.addSelectionListener(event -> {
        	if (event.getFirstSelectedItem().isPresent()) {
        		selectedJobOffer = (event.getFirstSelectedItem().get());
        		bewerbenJetzt.setEnabled(true);
        	} else {
        		return;
        		
        	}
        	
        });

        //Rechts oben
        //horizontalLayout.addComponent(role);
        //horizontalLayout.addComponent(username);

        //Mitte rechts
        addComponent(h3);
        setComponentAlignment(h3, Alignment.MIDDLE_RIGHT);
        
        
       
        
        addComponent(bewerbenJetzt);
        //h3.addComponent(sample);
    }

}
