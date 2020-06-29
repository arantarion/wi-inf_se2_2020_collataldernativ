package org.bonn.se2.gui.windows;

import com.vaadin.ui.*;
import org.bonn.se2.model.dao.BewerbungsDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.dao.ToggleDAO;
import org.bonn.se2.model.objects.dto.Bewerbung;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.FileUploader;
import org.bonn.se2.services.util.SessionFunctions;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Marceli Dziadek
 */

public class ToggleFeatureWindow extends Window {

    public ToggleFeatureWindow() {
        this.setUp();
    }

    private void setUp() {
        GridLayout grid = new GridLayout(4, 21);
        grid.setWidth("100%");
        grid.setSpacing(true);
        grid.addStyleName("scrollable");
        grid.addStyleName("gridPadding");
        this.setContent(grid);
        this.setWidth("75%");
        this.center();

        Label allgemein = new Label("Toggle Feature");
        grid.addComponent(allgemein, 1, 0);
    	
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
					dao2.updateToggle(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        	}
        		
        });
        	
        
        grid.addComponent(toggle, 1, 2);

        Button back = new Button("ToggleFeature verlassen.");

        back.addClickListener(event -> {
            UI.getCurrent().removeWindow(this);
        });

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(back);
        grid.addComponent(buttons, 3, 10);
        buttons.setComponentAlignment(back, Alignment.MIDDLE_RIGHT);

    }
}
