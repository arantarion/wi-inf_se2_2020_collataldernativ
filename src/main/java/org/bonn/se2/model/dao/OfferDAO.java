package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OfferDAO extends AbstractDAO<JobOffer> implements DAOInterface<JobOffer>  {
    public OfferDAO() throws DatabaseException {
    }

    @Override
    public JobOffer retrieve(int id) throws Exception {
        return null;
    }

    public List<JobOffer> retrieveCompanyOffers(int id) throws DatabaseException, SQLException {
        Statement statement = this.getStatement();
        ResultSet resultSet = null;
        //language=PostgreSQL
        String insert = "SELECT * " +
                "FROM \"collDB\".joboffer " +
                "WHERE \"companyID\" = \'" + id + "\' ";
        resultSet = statement.executeQuery(insert);
        List<JobOffer> liste = new ArrayList<>();
        JobOffer dto = null;

        try{
            while(resultSet.next()){
                dto = new JobOffer();
                dto.setBereich(resultSet.getString("bereich"));
                dto.setKontakt(resultSet.getString("kontakt"));
                dto.setBeschreibung(resultSet.getString("beschreibung"));
                dto.setJobofferID(resultSet.getInt("jobofferID"));
                dto.setName(resultSet.getString("name"));
                dto.setCompanyID(resultSet.getInt("companyID"));
                dto.setCreationDate(new java.sql.Date(resultSet.getDate("creationDate").getTime()).toLocalDate()); //creationDate
                dto.setBeginDate(new java.sql.Date(resultSet.getDate("beginDate").getTime()).toLocalDate());
                dto.setGehalt(resultSet.getString("gehalt"));
                System.out.println(dto);
                liste.add(dto);
            }
        }catch (Exception e) {
            throw new DatabaseException("retrieveCompanyOffers(int id) in JobOfferDAO failed");
        }
        return liste;
    }

    public List<JobOffer> retrieveCompanyOffers(String attribute) throws DatabaseException, SQLException {
        Statement statement = this.getStatement();
        ResultSet resultSet = null;
        //language=PostgreSQL
        String insert = "SELECT * " +
                "FROM \"collDB\".joboffer " +
                "WHERE bereich LIKE \'%" + attribute + "%\' OR kontakt LIKE \'%" + attribute + "%\' OR beschreibung LIKE \'%" + attribute + "%\' " +
                "OR name LIKE \'%" + attribute + "%\' OR gehalt LIKE \'%" + attribute + "%\'";
        resultSet = statement.executeQuery(insert);
        List<JobOffer> liste = new ArrayList<>();
        JobOffer dto = null;

        try{
            while(resultSet.next()){
                dto = new JobOffer();
                dto.setBereich(resultSet.getString("bereich"));
                dto.setKontakt(resultSet.getString("kontakt"));
                dto.setBeschreibung(resultSet.getString("beschreibung"));
                dto.setJobofferID(resultSet.getInt("jobofferID"));
                dto.setName(resultSet.getString("name"));
                dto.setCompanyID(resultSet.getInt("companyID"));
                dto.setCreationDate(new java.sql.Date(resultSet.getDate("creationDate").getTime()).toLocalDate()); //creationDate
                dto.setBeginDate(new java.sql.Date(resultSet.getDate("beginDate").getTime()).toLocalDate());
                dto.setGehalt(resultSet.getString("gehalt"));
                //System.out.println(dto);
                liste.add(dto);
            }
        }catch (Exception e) {
            throw new DatabaseException("retrieveCompanyOffers(int id) in JobOfferDAO failed");
        }
        return liste;
    }

    @Override
    public JobOffer retrieve(String attribute) throws Exception {
        return null;
    }

    @Override
    public List<JobOffer> retrieveAll() throws Exception {
        Statement statement = this.getStatement();
        ResultSet resultSet = null;
        //language=PostgreSQL
        String insert = "SELECT * " +
                "FROM \"collDB\".joboffer " +
                "JOIN \"collDB\".company ON joboffer.\"companyID\" = company.\"companyID\"";
        return execute(insert);
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
        //language=PostgreSQL
        String insertQuery2 = "INSERT INTO \"collDB\".joboffer (bereich, kontakt, beschreibung, name, \"companyID\", \"creationDate\", \"beginDate\", gehalt) " +
                "VALUES ('" + dto.getBereich() + "','" + dto.getKontakt() + "','" + dto.getBeschreibung() + "', '" + dto.getName() + "', '" + dto.getCompanyID() + "', '" + dto.getBeginDate() + "', '" + java.sql.Date.valueOf(LocalDate.now()) + "', '"+ dto.getGehalt() + "') " +
                "RETURNING \"jobofferID\"";
        PreparedStatement pst = this.getPreparedStatement(insertQuery2);
        ResultSet resultSet = pst.executeQuery();
        if (resultSet.next()) {
            JobOffer offer = new JobOffer();
            offer.setBereich(resultSet.getString("bereich"));
            offer.setKontakt(resultSet.getString("kontakt"));
            offer.setBeschreibung(resultSet.getString("beschreibung"));
            offer.setJobofferID(resultSet.getInt("jobofferID"));
            offer.setName(resultSet.getString("name"));
            offer.setCompanyID(resultSet.getInt("companyID"));
            offer.setCreationDate(new java.sql.Date(resultSet.getDate("creationDate").getTime()).toLocalDate());
            offer.setBeginDate(new java.sql.Date(resultSet.getDate("beginDate").getTime()).toLocalDate());
            offer.setGehalt(resultSet.getString("gehalt"));
            System.out.println("Offer erfolgreich gespeichert!");
            return offer;
        } else {
            System.out.println("JobOffer-Objekt konnte nicht richtig gespeichert werden!");
            return null;
        }

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
