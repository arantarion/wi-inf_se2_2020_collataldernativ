package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class OfferDAO extends AbstractDAO<JobOffer> implements DAOInterface<JobOffer> {

    public OfferDAO() throws DatabaseException {
    }

    @Override
    public JobOffer retrieve(int id) throws Exception {
        //language=PostgreSQL
        final String sql =
                "SELECT * FROM \"collDB\".joboffer " +
                        "WHERE joboffer.\"jobofferID\" = '" + id + "';";

        List<JobOffer> result = execute(sql);
        if (result.size() < 1) {
            throw new InvalidCredentialsException();
        }
        Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Das Joboffer-Objekt mit der jobofferID: " + id + " wurde abgerufen.");
        return result.get(0);
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

        try {
            while (resultSet.next()) {
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
                liste.add(dto);
            }
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Alle offer mit der companyID: " + id + " wurden abgerufen");
        } catch (Exception e) {
            //throw new DatabaseException("retrieveCompanyOffers(int id) in JobOfferDAO failed");
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "retrieveCompanyOffers(int id) in JobOfferDAO failed", e);
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

        try {
            while (resultSet.next()) {
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
                liste.add(dto);
            }
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Alle offer mit Attribut: " + attribute + " wurden abgerufen");
        } catch (Exception e) {
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "retrieveCompanyOffers(int id) in JobOfferDAO failed", e);
            //throw new DatabaseException("retrieveCompanyOffers(int id) in JobOfferDAO failed");
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
        Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Alle Joboffer wurden abgerufen.");
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
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Joboffer-Objekt: " + dto + " wurde erfolgreich erstellt.");

        } catch (Exception e) {
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "create(ResultSet resultSet) in JobOfferDAO failed", e);
            //throw new DatabaseException("create(ResultSet resultSet) in JobOfferDAO failed");
        }
        return dto;
    }

    @Override
    public JobOffer create(JobOffer dto) throws Exception {
        //language=PostgreSQL
        String insertQuery2 = "INSERT INTO \"collDB\".joboffer (bereich, kontakt, beschreibung, name, \"companyID\", \"creationDate\", \"beginDate\", gehalt) " +
                "VALUES ('" + dto.getBereich() + "','" + dto.getKontakt() + "','" + dto.getBeschreibung() + "', '" + dto.getName() + "', '" + dto.getCompanyID() + "', '" + java.sql.Date.valueOf(LocalDate.now()) + "', '" + dto.getBeginDate() + "', '" + dto.getGehalt() + "') " +
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
            //System.out.println("Offer erfolgreich gespeichert!");
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Joboffer-Objekt: " + offer + "wurde erfolgreich gespeichert.");
            return offer;
        } else {
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "Joboffer-Objekt: " + dto + "konnte nicht richtig gespeichert werden.");
            //System.out.println("JobOffer-Objekt konnte nicht richtig gespeichert werden!");
            return null;
        }

    }

    @Override
    public JobOffer update(JobOffer offer) throws Exception {
        return null;
    }

    @Override
    public JobOffer delete(JobOffer offer) throws Exception {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".joboffer " +
                        "WHERE \"jobofferID\" = ? " +
                        "RETURNING *;";

        List<JobOffer> result = executePrepared(deleteQuery, offer.getJobofferID());
        Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "CompanyOffer : " + offer + "wurde gelöscht.");
        if (result.size() < 1) {
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "delete(Joboffer offer) von " + offer + " failed.");
            throw new DatabaseException("delete(Joboffer offer) failed");
        }
        return result.get(0);
    }

    public void delete(int id) throws Exception {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".joboffer " +
                        "WHERE \"jobofferID\" = ? " +
                        "RETURNING *;";

        Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "CompanyOffer mit der jobOfferID: " + id + "wurde gelöscht.");
    }

    public List<JobOffer> deleteCompanyOffers(int ID) throws Exception {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".joboffer " +
                        "WHERE \"companyID\" = ? " +
                        "RETURNING *;";

        List<JobOffer> result = executePrepared(deleteQuery, ID);
//        if (result.size() < 1) {
//            throw new DatabaseException("delete(User user) failed");
//        }
        Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "CompanyOffers mit der companyID: " + ID + "wurden gelöscht.");
        return result;
    }
}
