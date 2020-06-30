package org.bonn.se2.gui.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se2.gui.views.ProfilView;
import org.bonn.se2.gui.windows.EditCompanyWindow;
import org.bonn.se2.gui.windows.EditStudentWindow;
import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.SessionFunctions;
import org.bonn.se2.services.util.Utils;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Anton Drees
 */


public class AccountOverviewHead extends VerticalLayout {

    private final GridLayout accountOverviewHeadLayout = new GridLayout(4, 5);

    public AccountOverviewHead(Student student) {
        setUp(student);
    }

    public AccountOverviewHead(Company company) {
        setUp(company);
    }

    private <T extends User> void setUp(T dto) {
        this.setWidth("100%");
        this.addStyleName("profile-components");
        this.addStyleNames("profileheader-components");
        this.addComponent(accountOverviewHeadLayout);
        this.setComponentAlignment(accountOverviewHeadLayout, Alignment.MIDDLE_CENTER);
        accountOverviewHeadLayout.setWidth("80%");

        Image profilbild;

        if (dto.getImage() != null && dto.getImage().length > 0) {
            profilbild = Utils.convertToImg(dto.getImage());
        } else {
            ThemeResource themeResource = new ThemeResource(Configuration.ImagePaths.PLACEHOLDER);
            profilbild = new Image("", themeResource);
        }
        profilbild.addStyleName("profileImages");

        Address address;
        Label nameLabel;
        Label ortLabel;

        if (dto instanceof Student) {
            Student student = (Student) dto;
            address = student.getAdresse();

            Label aboutLabel = new Label("Über");
            aboutLabel.setWidth("100%");

            Label studienfachLabel;
            if ((student.getStudienfach()) != null) {
                studienfachLabel = new Label(VaadinIcons.CALC_BOOK.getHtml() + " " + student.getStudienfach(), ContentMode.HTML);
            } else {
                studienfachLabel = new Label(VaadinIcons.CALC_BOOK.getHtml() + " noch kein Studienfach angegeben", ContentMode.HTML);
            }
            studienfachLabel.setWidth("100%");

            Label fachsemLabel = new Label(VaadinIcons.ACADEMY_CAP.getHtml() + " Fachsemester: " + student.getFachsemester(), ContentMode.HTML);
            fachsemLabel.setWidth("100%");

            Label jobLabel;
            if (!(student.getJob() == null && student.getArbeitgeber() == null)) {
                jobLabel = new Label(VaadinIcons.OFFICE.getHtml() + " " + student.getJob() + " bei " + student.getArbeitgeber(), ContentMode.HTML);
            } else {
                jobLabel = new Label(VaadinIcons.OFFICE.getHtml() + " noch kein Job eingetragen", ContentMode.HTML);
            }
            jobLabel.setWidth("100%");

            nameLabel = new Label(student.getVollstName());
            if (address != null) {
                ortLabel = new Label(VaadinIcons.MAP_MARKER.getHtml() + " " + address.getStadt() + ", " + address.getLand());
                accountOverviewHeadLayout.addComponent(ortLabel, 2, 3);
            }

            accountOverviewHeadLayout.addComponent(studienfachLabel, 1, 1);
            accountOverviewHeadLayout.addComponent(fachsemLabel, 1, 2);
            accountOverviewHeadLayout.addComponent(aboutLabel, 2, 0);
            accountOverviewHeadLayout.addComponent(jobLabel, 2, 1);

            if (ProfilView.getMyProfile()) {
                buildEditButton(student);
            }

        } else {
            Company company = (Company) dto;

            Link webURL = new Link("Zu unserer Website", new ExternalResource("https://" + company.getWebURL()));
            webURL.setTargetName("_blank");
            webURL.setIcon(VaadinIcons.GLOBE_WIRE);

            Label beschreibungLabel = new Label(company.getBeschreibung());
            nameLabel = new Label(company.getName());

            accountOverviewHeadLayout.addComponent(webURL, 1, 3);
            accountOverviewHeadLayout.addComponent(beschreibungLabel, 1, 4, 3, 4);

            if (ProfilView.getMyProfile()) {
                buildEditButton(company);
            }

            buildRateButton(company);


        }

        accountOverviewHeadLayout.addComponent(profilbild, 0, 0, 0, 4);
        accountOverviewHeadLayout.setComponentAlignment(profilbild, Alignment.TOP_CENTER);

        accountOverviewHeadLayout.addComponent(nameLabel, 1, 0);

        accountOverviewHeadLayout.setColumnExpandRatio(0, 0.25F);
        accountOverviewHeadLayout.setColumnExpandRatio(1, 4F);
        accountOverviewHeadLayout.setColumnExpandRatio(2, 4F);
        accountOverviewHeadLayout.setColumnExpandRatio(3, 1F);

        for (Component comp : accountOverviewHeadLayout) {
            if (!(comp instanceof Button) && !(comp instanceof Image)) {
                accountOverviewHeadLayout.setComponentAlignment(comp, Alignment.MIDDLE_LEFT);
            }
        }
    }

    private void buildEditButton(User user) {
        Button editButton = new Button(VaadinIcons.EDIT);
        accountOverviewHeadLayout.addComponent(editButton, 3, 0);
        accountOverviewHeadLayout.setComponentAlignment(editButton, Alignment.MIDDLE_CENTER);

        editButton.addClickListener(clickEvent -> {
            if (user instanceof Student) {
                Student student = (Student) user;
                EditStudentWindow window = new EditStudentWindow(student);
                UI.getCurrent().addWindow(window);
                window.setVisible(true);
            } else {
                Company company = (Company) user;
                EditCompanyWindow window = new EditCompanyWindow(company);
                UI.getCurrent().addWindow(window);
                window.setVisible(true);
            }
        });
    }

    private void buildRateButton(Company company) {
        Button rateButton = new Button("Geben Sie eine Bewertung ab");
        accountOverviewHeadLayout.addComponent(rateButton, 3, 1);
        accountOverviewHeadLayout.setComponentAlignment(rateButton, Alignment.MIDDLE_CENTER);

        if (SessionFunctions.getCurrentRole().equals(Configuration.Roles.COMPANY)) {
            rateButton.setEnabled(false);
        }

        rateButton.addClickListener(clickEvent -> {
            //rating window pops up etc.
        });

    }

}
