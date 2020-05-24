package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.util.List;

public class OfferDAO extends AbstractDAO<JobOffer> implements DAOInterface<JobOffer>  {
    public OfferDAO() throws DatabaseException {
    }



    @Override
    public JobOffer retrieve(int id) throws Exception {
        return null;
    }

    @Override
    public JobOffer retrieve(String attribute) throws Exception {
        return null;
    }

    @Override
    public List<JobOffer> retrieveAll() throws Exception {
        return null;
    }

    @Override
    protected JobOffer create(ResultSet resultSet) throws DatabaseException {
        JobOffer dto = new JobOffer();

        try {
            dto.setBereich(resultSet.getString("bereich"));
            dto.setKontakt(resultSet.getString("kontakt"));
            dto.setBeschreibung(resultSet.getString("beschreibung"));
            dto.setJobofferID(resultSet.getInt("jobofferID"));
            dto.setName(resultSet.getString("name"));
            dto.setCompanyID(resultSet.getInt("companyID"));
            dto.setCreationDate(new java.sql.Date(resultSet.getDate("creationDate").getTime()).toLocalDate());
            dto.setBeginDate(new java.sql.Date(resultSet.getDate("beginDate").getTime()).toLocalDate());
            dto.setGehalt(resultSet.getString("gehalt"));


        } catch (Exception e) {
            throw new DatabaseException("create(ResultSet resultSet) in JobOfferDAO failed");
        }
        return dto;
    }

    @Override
    public JobOffer create(JobOffer dto) throws Exception {
        return null;
    }

    @Override
    public JobOffer update(JobOffer item) throws Exception {
        return null;
    }

    @Override
    public JobOffer delete(JobOffer item) throws Exception {
        return null;
    }
}
