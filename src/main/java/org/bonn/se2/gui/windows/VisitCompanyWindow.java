package org.bonn.se2.gui.windows;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.CompanyDAO;
import org.bonn.se2.model.dao.RatingDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Rating;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.SessionFunctions;

import java.text.SimpleDateFormat;
import java.util.List;

public class VisitCompanyWindow extends Window {

    static Image profilbild = new Image("nichts ausgewählt");
    private VerticalLayout ratingsly;
    private Button ratingBtn;
    private Company company;
    private RatingDAO dao;

    public VisitCompanyWindow(int companyID) {
        this.setUp(companyID);
    }

    private void setUp(int companyID) {
        company = null;
        try {
            company = new CompanyDAO().retrieveCompany(companyID);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        ratingBtn = new Button("Bewerten");
        try {
            dao = new RatingDAO();
            User user = SessionFunctions.getCurrentUser();
            if (dao.isExist(companyID, user.getUserID())) {
                ratingBtn.setEnabled(false);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ratingBtn.addClickListener(clickEvent -> {
            RatingWindow ratingWindow = new RatingWindow(company, ratingBtn, this);
            UI.getCurrent().addWindow(ratingWindow);
            ratingWindow.setVisible(true);
        });
        ratingsly = new VerticalLayout();
        ratingsly.setStyleName("rating-ly");
        VerticalLayout verticalLayout = new VerticalLayout();
        this.setContent(verticalLayout);
        //this.setWidth("75%");
        this.center();

        GridLayout grid = new GridLayout(4, 32);
        verticalLayout.addComponent(grid);
        //grid.setWidth("95%");
        grid.setColumnExpandRatio(0, 1);
        grid.setColumnExpandRatio(1, 2);
        grid.setColumnExpandRatio(2, 1);
        grid.setColumnExpandRatio(3, 2);
        grid.addComponent(new Label("&nbsp;", ContentMode.HTML), 0, 0);

        Label nameLabel = new Label(company.getName(), ContentMode.HTML);
        nameLabel.setSizeFull();
        grid.addComponent(nameLabel, 0, 1, 1, 1);
        grid.setComponentAlignment(nameLabel, Alignment.MIDDLE_CENTER);

        Link webURL = new Link("Zu unserer Website", new ExternalResource("https://" + company.getWebURL()));
        webURL.setTargetName("_blank");
        webURL.setIcon(VaadinIcons.GLOBE_WIRE);

        Label beschreibungLabel = new Label(company.getBeschreibung());

        grid.addComponent(webURL, 1, 3);
        grid.addComponent(ratingBtn, 3, 3);
        grid.addComponent(beschreibungLabel, 1, 4);
        grid.addComponent(ratingsly, 1, 5);
        setRatings();
    }


    private void setRatings() {
        try {
            ratingsly.removeAllComponents();
            Label title = new Label("Bewertungen:");
            List<Rating> ratings = dao.retrieveAllByCompany(company.getcompanyID());
            if (!ratings.isEmpty()) {
                ratingsly.addComponent(title);
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            for (Rating rating : ratings) {
                VerticalLayout ratingly = new VerticalLayout();
                ratingly.setStyleName("rating-container");
                Label date = new Label("<h3><b>" + simpleDateFormat.format(rating.getDate()) + "</b></h3>", ContentMode.HTML);
                Label comment = new Label("<i>" + rating.getComment() + "</i>", ContentMode.HTML);
                HorizontalLayout starly = new HorizontalLayout();
                for (int i = 0; i < rating.getRating(); ++i) {
                    Button star = new Button();
                    star.setStyleName("rating-btn-fill");
                    star.setEnabled(false);
                    starly.addComponent(star);
                }
                ratingly.addComponent(date);
                ratingly.addComponent(starly);
                ratingly.addComponent(comment);
                ratingsly.addComponent(ratingly);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void refreshRating() {
        setRatings();
    }
}
