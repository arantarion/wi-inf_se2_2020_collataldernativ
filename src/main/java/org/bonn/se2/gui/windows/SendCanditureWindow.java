package org.bonn.se2.gui.windows;

import com.vaadin.ui.*;
import org.bonn.se2.model.dao.BewerbungsDAO;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Bewerbung;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.FileUploader;
import org.bonn.se2.services.util.SessionFunctions;

import java.time.LocalDate;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Marceli Dziadek
 */

public class SendCanditureWindow extends Window {

    private final JobOffer selectedJobOffer;

    public SendCanditureWindow(JobOffer selectedJobOffer) {
        this.selectedJobOffer = selectedJobOffer;
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

        Label allgemein = new Label("Ihre Bewerbung für das Jobangebot '" + selectedJobOffer.getName() + "'!");
        grid.addComponent(allgemein, 1, 0);

        // Motivationsschreiben
        Label motivationLabel = new Label("Motivationsschreiben (optional)");
        TextField motivationTf = new TextField();
        motivationTf.setHeight("200%");
        motivationTf.setPlaceholder("");
        motivationTf.setWidth("100%");
        grid.addComponent(motivationLabel, 0, 3);
        grid.addComponent(motivationTf, 1, 3, 3, 3);
        grid.setComponentAlignment(motivationLabel, Alignment.MIDDLE_CENTER);

        //Uploader
        FileUploader receiver;
        receiver = new FileUploader();
        /*
        //Anschreiben
        Label label_anschreiben = new Label("Anschreiben");
        Upload upload_anschreiben = new Upload("", receiver);
        upload_anschreiben.setButtonCaption("Hochladen");
        upload_anschreiben.addSucceededListener(receiver);
        upload_anschreiben.setAcceptMimeTypes("application/pdf");

        GridLayout anschreibenLayout = new GridLayout(1, 2);
        anschreibenLayout.setSpacing(true);
        anschreibenLayout.addComponent(upload_anschreiben, 0, 0);
        anschreibenLayout.setComponentAlignment(upload_anschreiben, Alignment.MIDDLE_RIGHT);

        grid.addComponent(label_anschreiben, 0, 5);
        grid.addComponent(anschreibenLayout, 1, 5);
        grid.setComponentAlignment(label_anschreiben, Alignment.MIDDLE_CENTER);
        grid.setSpacing(true);
        
        //Lebenslauf
        Label label_lebenslauf = new Label("Lebenslauf");
        Upload upload_lebenslauf = new Upload("", receiver);
        upload_lebenslauf.setButtonCaption("Hochladen");
        upload_lebenslauf.addSucceededListener(receiver);
        upload_lebenslauf.setAcceptMimeTypes("application/pdf");
               
        Button use_lebenslauf = new Button("'Document' verwenden",VaadinIcons.FILE);
        
        use_lebenslauf.addClickListener(event -> {
            //Hochgeladenes Objekt wieder löschen, falls for
        });
        
        
        Button no_lebenslauf = new Button("Kein Lebenslauf vorhanden.",VaadinIcons.FILE);
        no_lebenslauf.setEnabled(false);
        
        
        GridLayout lebenslaufLayout = new GridLayout(2,2); //123
        lebenslaufLayout.setSpacing(true);
        lebenslaufLayout.addComponent(upload_lebenslauf, 0, 0);
        lebenslaufLayout.setComponentAlignment(upload_lebenslauf, Alignment.TOP_CENTER);
        
        //TODO if Doa Lebelslauf findet einen Lebenslauf {
        
        //lebenslaufLayout.addComponent(use_lebenslauf, 1, 0);
        //lebenslaufLayout.setComponentAlignment(use_lebenslauf, Alignment.MIDDLE_CENTER);
        
        
        // else 
        lebenslaufLayout.addComponent(no_lebenslauf, 1, 0);
        lebenslaufLayout.setComponentAlignment(no_lebenslauf, Alignment.MIDDLE_CENTER);
        
        grid.addComponent(label_lebenslauf, 0, 11);
        grid.addComponent(lebenslaufLayout, 1, 11);
        grid.setComponentAlignment(label_lebenslauf, Alignment.MIDDLE_CENTER);
        grid.setSpacing(true);
*/

        Button submit = new Button("Bewerbung senden");
        Button back = new Button("Zurück zur Hauptseite");

        back.addClickListener(event -> {
            UI.getCurrent().removeWindow(this);
        });
        //submit.addClickListener((Button.ClickListener) event -> this.setVisible(false));

        submit.addClickListener(clickEvent -> {
            //TODO
            int bewerbungsid = 0;
//        	String path = "" ;//receiver.getFile().getName();
//            FileInputStream input = null;
//    		try {
//    			input = new FileInputStream(path);
//    		} catch (FileNotFoundException e2) {
//    			// TODO Auto-generated catch block
//    			e2.printStackTrace();
//    		}
//            byte[] data = null;
//    		try {
//    			data = FileUploader.toByteArray(input);
//    		} catch (IOException e2) {
//    			// TODO Auto-generated catch block
//    			e2.printStackTrace();
//    		}
    		/*
    		System.out.println(input.toString());
    		System.out.println(path);
    		System.out.println(data);
        	*/
            //Done
            //DocumentDAO lebenslauf = DocumentDAO.create(upload_anschreiben);
            int studentID = 0;
            try {
                Student st = new StudentDAO().retrieve(SessionFunctions.getCurrentUser().getUsername());
                studentID = st.getStudentID();
            } catch (DatabaseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println(bewerbungsid + " " + selectedJobOffer.getJobofferID() + " " + selectedJobOffer.getCompanyID() + " " + studentID + " " + LocalDate.now() + " " + motivationTf.getValue());
            Bewerbung bewerbungDTO = new Bewerbung(bewerbungsid, selectedJobOffer.getJobofferID(), selectedJobOffer.getCompanyID(), studentID, LocalDate.now(), motivationTf.getValue());
            try {
                new BewerbungsDAO().create(bewerbungDTO);
                BewerbungsDAO bewerbungDAO = new BewerbungsDAO();
                close();
            } catch (DatabaseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(submit);
        buttons.addComponent(back);
        grid.addComponent(buttons, 3, 15);
        buttons.setComponentAlignment(back, Alignment.MIDDLE_RIGHT);
        buttons.setComponentAlignment(submit, Alignment.MIDDLE_RIGHT);

    }
}
