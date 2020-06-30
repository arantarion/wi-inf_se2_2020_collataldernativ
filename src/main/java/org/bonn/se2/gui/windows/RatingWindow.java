package org.bonn.se2.gui.windows;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se2.model.dao.RatingDAO;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.Rating;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.SessionFunctions;

import java.util.ArrayList;
import java.util.List;

public class RatingWindow extends Window implements Button.ClickListener {

    private final Company company;

    private final List<Button> stars;

    private TextArea commentArea;

    private int numberOfStars;

    private final Button rateBtn;

    private final VisitCompanyWindow head;


    public RatingWindow(Company company, Button rateBtn, VisitCompanyWindow head) {
        this.company = company;
        this.rateBtn = rateBtn;
        this.head = head;
        this.stars = new ArrayList<>();
        setUp();
    }

    private void setUp() {

        Label title = new Label("<h2> Bewerten sie die Firma: " + company.getName() + "</h2>", ContentMode.HTML);
        VerticalLayout ratingLayout = new VerticalLayout();

        Label ratingLbl = new Label("Geben Sie Ihre Bewertung ein:");

        Label commentLbl = new Label("Kommentar abgeben");

        commentArea = new TextArea();

        Button cancel = new Button("Cancel");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                RatingWindow.this.cancel();
            }
        });

        Button save = new Button("Save");
        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                RatingWindow.this.save();
            }
        });

        HorizontalLayout btnLayout = new HorizontalLayout();

        HorizontalLayout ratingStarLayout = new HorizontalLayout();

        initStarElements(ratingStarLayout);

        btnLayout.addComponent(cancel);
        btnLayout.addComponent(save);
        btnLayout.setMargin(true);

        ratingLayout.addComponent(title);
        ratingLayout.setComponentAlignment(title, Alignment.TOP_CENTER);
        ratingLayout.addComponent(ratingLbl);
        ratingLayout.addComponent(ratingStarLayout);
        ratingLayout.addComponent(commentLbl);
        ratingLayout.addComponent(commentArea);
        ratingLayout.addComponent(btnLayout);

        this.setContent(ratingLayout);
        this.setWidth("50%");
        this.center();

    }

    private void save() {
        if (numberOfStars == 0) {
            Notification.show("Bitte wählen sie mind. 1 Stern.");
        } else {
            try {
                RatingDAO dao = new RatingDAO();
                Rating rating = new Rating();
                rating.setUserId(SessionFunctions.getCurrentUser().getUserID());
                rating.setCompanyId(company.getcompanyID());
                rating.setRating(numberOfStars);
                rating.setComment(commentArea.getValue());
                dao.create(rating);
                Notification.show("SUCCESS", "Bewertung erfolgreich gesendet!", Notification.Type.HUMANIZED_MESSAGE);
                this.cancel();
                this.setDisable();
                head.refreshRating();
            } catch (DatabaseException e) {
                e.printStackTrace();
                Notification.show("ERROR", "Datenbank fehler!", Notification.Type.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                Notification.show("ERROR", "Datenbank fehler!", Notification.Type.ERROR_MESSAGE);
            }
        }

    }

    private void cancel() {
        this.close();
    }

    private void initStarElements(Layout layout) {
        for (int i = 1; i <= 5; ++i) {
            Button star = new Button();
            star.setId(Integer.toString(i));
            star.setStyleName("rating-btn-not-fill");
            star.addClickListener(this::buttonClick);
            layout.addComponent(star);
            stars.add(star);
        }

    }

    public void setDisable() {
        rateBtn.setEnabled(false);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        Button selectedStar = clickEvent.getButton();
        int id = Integer.parseInt(selectedStar.getId());
        numberOfStars = id;
        for (int i = 0; i < id; ++i) {
            Button star = stars.get(i);
            String style = star.getStyleName();
            if (style.equals("rating-btn-not-fill")) {
                star.removeStyleName("rating-btn-not-fill");
                star.addStyleName("rating-btn-fill");
            }
        }

        for (int i = stars.size() - 1; i >= id; --i) {
            Button star = stars.get(i);
            String style = star.getStyleName();
            if (style.equals("rating-btn-fill")) {
                star.removeStyleName("rating-btn-fill");
                star.addStyleName("rating-btn-not-fill");
            }
        }


    }
}
