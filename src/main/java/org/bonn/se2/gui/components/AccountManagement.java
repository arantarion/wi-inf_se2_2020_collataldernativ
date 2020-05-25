/*
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer
 *
 *
 */

package org.bonn.se2.gui.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.util.Utils;

import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;

public class AccountManagement extends VerticalLayout {

    public AccountManagement(Student student){
        setUp(student);
    }

    public AccountManagement(Company company){
        setUp(company);
    }

    private <T extends User> void setUp(T dto) throws Exception {
        this.setSizeFull();

        if (dto instanceof Student) {

            Student student = (Student) dto;

            GridLayout layout = new GridLayout(2, 4);
            layout.setWidth("70%");
            layout.setHeight("100%");
            layout.setSpacing(true);
            this.addComponent(layout);
            this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

            Label header = new Label("Über " + student.getVollstName());
            header.setWidth("100%");
            layout.addComponent(header, 0, 0, 1, 0);

            Panel studienfach;
            if (student.getStudienfach() != null) {
                studienfach = createPanel(student.getStudienfach(), "Studienfach");
            } else {
                studienfach = createPanel("Studienfach");
            }
            studienfach.setSizeFull();
            studienfach.setIcon(VaadinIcons.BOOK);
            layout.addComponent(studienfach, 0, 1);

            Panel fachsemester;
            if (student.getFachsemester() != null) {
                fachsemester = createPanel(Integer.toString(student.getFachsemester()), "Fachsemester");
            } else {
                fachsemester = createPanel("Fachsemester");
            }
            fachsemester.setSizeFull();
            fachsemester.setIcon(VaadinIcons.CALENDAR);
            layout.addComponent(fachsemester, 1, 1);

            Panel job;
            if (student.getFachsemester() != null) {
                job = createPanel(student.getJob(), "Job");
            } else {
                job = createPanel("Job");
            }
            job.setSizeFull();
            job.setIcon(VaadinIcons.CALENDAR);
            layout.addComponent(job, 0, 2);

            Panel arbeitgeber;
            if (student.getFachsemester() != null) {
                arbeitgeber = createPanel(student.getArbeitgeber(), "Arbeitgeber");
            } else {
                arbeitgeber = createPanel("Arbeitgeber");
            }
            arbeitgeber.setSizeFull();
            arbeitgeber.setIcon(VaadinIcons.CALENDAR);
            layout.addComponent(arbeitgeber, 1, 2);

//        StreamResource doc = Utils.convertToPdf(student.getDoc().getFile(), student.getDoc().getDocTitle());
//        Panel documents = createPanel(doc, student.getDoc().getDocTitle());
//        documents.setSizeFull();
//        documents.setIcon(VaadinIcons.FILE);
//        layout.addComponent(documents,0,3);
        } else {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setWidth("100%");
            this.addComponent(layout);

            Label placeholder = new Label("Hier sollten dann die Jobangebote stehen");
            this.addComponent(placeholder);
            this.setComponentAlignment(placeholder, Alignment.MIDDLE_CENTER);

        }
    }

    private <T> Panel createPanel(ArrayList<T> list, String name) {
        Panel panel = new Panel(name);
        VerticalLayout layout = new VerticalLayout();
        for (T i : list){
            layout.addComponent(new Label(VaadinIcons.ARROWS_LONG_RIGHT.getHtml() + " " + i.toString(), ContentMode.HTML));
        }
        panel.setContent(layout);
        return panel;
    }

    private Panel createPanel(String name) {
        Panel panel = new Panel(name);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Hier ist noch nichts"));
        panel.setContent(layout);
        return panel;
    }

    private Panel createPanel(String info, String name) {
        Panel panel = new Panel(name);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label(name + ": " + info));
        panel.setContent(layout);
        return panel;
    }
    private Panel createPanel(StreamResource file, String filename) {
        Panel panel;
        VerticalLayout layout = new VerticalLayout();

        if (file.getBufferSize() <= 0){
            panel = createPanel("Dokumente");
        } else {
            panel = new Panel("Dokumente");
            if(filename != null || !filename.equals("")){
                Link link = new Link(filename + ".pdf", file);
                layout.addComponent(link);
            }
        }

        panel.setContent(layout);
        return panel;
    }

}
