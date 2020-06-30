package org.bonn.se2.test.builder;

import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;

import java.time.LocalDate;

public class JobOfferBuilder {

    private static JobOfferBuilder instance = null;
    private String title;
    private String description;
    private Company company;
    private int vacancyId;
    private LocalDate date;

    private JobOfferBuilder() {
    }

    public static synchronized JobOfferBuilder getInstance() {
        if (instance == null) {
            instance = new JobOfferBuilder();
        }
        return instance;
    }

    public JobOfferBuilder createDefaultVacancy() {
        title = "Entwickler (w/m/d)";
        description = "Lorem ipsum";
        company = new Company();
        return this;
    }

    public JobOfferBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public JobOfferBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public JobOfferBuilder withCompany(Company company) {
        this.company = company;
        return this;
    }

    public JobOfferBuilder withVacancyId(int vacancyId) {
        this.vacancyId = vacancyId;
        return this;
    }

    public JobOfferBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public JobOfferBuilder withoutTitle() {
        return withTitle("");
    }

    public JobOfferBuilder withoutDescription() {
        return withDescription("");
    }

    public JobOfferBuilder withoutCompany() {
        return withCompany(null);
    }

    public JobOfferBuilder withoutVacancyId() {
        return withVacancyId(0);
    }

    public JobOfferBuilder withoutDate() {
        return withDate(null);
    }


    public JobOffer get() {
        return done();
    }

    public JobOffer done() {
        JobOffer dto = new JobOffer();
        dto.setName(title);
        dto.setBeschreibung(description);
        dto.setJobofferID(vacancyId);
        dto.setBeginDate(date);
        return dto;
    }

}
