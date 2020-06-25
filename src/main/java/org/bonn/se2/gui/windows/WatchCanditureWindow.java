package org.bonn.se2.gui.windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import javax.swing.Icon;

import org.bonn.se2.gui.ui.MyUI;
import org.bonn.se2.gui.views.MainView;
import org.bonn.se2.gui.views.ProfilView;
import org.bonn.se2.model.dao.BewerbungsDAO;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.DocumentDAO;
import org.bonn.se2.model.dao.OfferDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.model.objects.dto.Bewerbung;
import org.bonn.se2.model.objects.dto.Document;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.FileUploader;
import org.bonn.se2.services.util.PasswordValidator;
import org.bonn.se2.services.util.SessionFunctions;
import org.bonn.se2.services.util.Utils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Marceli Dziadek
 */

public class WatchCanditureWindow extends Window {
	
    private final JobOffer selectedJobOffer;
    private Bewerbung selectedBewerbung;

    public WatchCanditureWindow(JobOffer selectedJobOffer) {
        this.selectedJobOffer = selectedJobOffer;
        this.setUp();
    }

    private void setUp() {
        VerticalLayout grid = new VerticalLayout();
        grid.setWidth("100%");
        grid.setSpacing(true);
        grid.addStyleName("scrollable");
        grid.addStyleName("gridPadding");
        this.setContent(grid);
        this.setWidth("75%");
        this.center();

        Label allgemein = new Label("Eingegangene Bewerbung für Ihr Jobangebot '" + selectedJobOffer.getName() + "'!");
        grid.addComponent(allgemein);

        Grid<Bewerbung> gridBewerbung = new Grid<>();
        List<Bewerbung> liste = null;
        try {
            liste = new BewerbungsDAO().retrieveCompanyBewerbungJobOffer(selectedJobOffer.getCompanyID(),selectedJobOffer.getJobofferID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        gridBewerbung.setItems(liste);
        gridBewerbung.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridBewerbung.addColumn(Bewerbung::getBewerbungsID).setCaption("BewerbungsID");
        gridBewerbung.addColumn(Bewerbung::getStudentID).setCaption("StudentID");
        gridBewerbung.addColumn(Bewerbung::getNotes).setCaption("Motivationsschreiben");
        gridBewerbung.addColumn(Bewerbung::getBewerbungsdatum).setCaption("Bewerbungsdatum");
        gridBewerbung.setSizeFull();
        gridBewerbung.setHeightMode(HeightMode.UNDEFINED);
        
        gridBewerbung.addSelectionListener(event -> {
        	if (event.getFirstSelectedItem().isPresent()) {
        		selectedBewerbung = (event.getFirstSelectedItem().get());
        		//TODO
        	} else {
        		return;
        		
        	}
        	
        });
        
        grid.addComponent(gridBewerbung);

        Button submit = new Button("Bewerbung senden");
        Button back = new Button("Zurück zur Hauptseite");

        back.addClickListener(event -> {
            UI.getCurrent().removeWindow(this);
        });
        //submit.addClickListener((Button.ClickListener) event -> this.setVisible(false));

        submit.addClickListener(clickEvent -> {
        	//TODO
        });

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(submit);
        buttons.addComponent(back);
        //grid.addComponent(buttons);
        buttons.setComponentAlignment(back, Alignment.MIDDLE_RIGHT);
        buttons.setComponentAlignment(submit, Alignment.MIDDLE_RIGHT);

    }
}
