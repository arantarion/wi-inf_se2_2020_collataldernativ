package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Bewerbung;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.DontUseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class BewerbungsDAO extends AbstractDAO<Bewerbung> implements DAOInterface<Bewerbung> {

    public BewerbungsDAO() throws DatabaseException {
    }

    @Override
    public Bewerbung retrieve(int bewerbungsid) throws DatabaseException, InvalidCredentialsException {
        //language=PostgreSQL
        final String sql =
                "SELECT * FROM \"collDB\".bewerbung " +
                        "WHERE bewerbung.\"bewerbungsID\" = '" + bewerbungsid + "';";

        List<Bewerbung> result = execute(sql);
        if (result.size() < 1) {
            throw new InvalidCredentialsException();
        }
        String loggerMsg = "Das Bewerbungs-Objekt mit der bewerbungsID: " + bewerbungsid + " wurde abgerufen.";
        Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, loggerMsg);
        return result.get(0);
    }

    public List<Bewerbung> retrieveCompanyBewerbungJobOffer(int companyid, int jobofferid) throws DatabaseException, SQLException {
        Statement statement = this.getStatement();
        ResultSet resultSet;

        //language=PostgreSQL
        final String insert = "SELECT * FROM \"collDB\".bewerbung WHERE \"companyID\" = '" + companyid + "' AND \"jobofferID\" = '" + jobofferid + "' ";

        resultSet = statement.executeQuery(insert);
        List<Bewerbung> liste = new ArrayList<>();
        Bewerbung bewerbung;

        try {
            while (resultSet.next()) {
                bewerbung = new Bewerbung();
                bewerbung.setBewerbungsID(resultSet.getInt("bewerbungsID"));
                bewerbung.setJobofferID(resultSet.getInt("jobofferID"));
                bewerbung.setCompanyID(resultSet.getInt("companyID"));
                bewerbung.setStudentID(resultSet.getInt("studentID"));
                bewerbung.setBewerbungsdatum(new java.sql.Date(resultSet.getDate("bewerbungsdatum").getTime()).toLocalDate());
                bewerbung.setNotes(resultSet.getString("notes"));
                liste.add(bewerbung);
            }
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Alle offer mit der companyID: " + companyid + " und der jobofferid: " + jobofferid + "wurden abgerufen");
        } catch (SQLException e) {
            String loggerMsg = "retrieveCompanyOffers(int companyid, int jobofferid) in JobOfferDAO failed";
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, loggerMsg, e);
        }
        return liste;
    }

    public List<Bewerbung> retrieveCompanyBewerbung(int companyid) throws DatabaseException, SQLException {
        Statement statement = this.getStatement();
        ResultSet resultSet;
        //language=PostgreSQL
        final String insert = "SELECT * " +
                "FROM \"collDB\".bewerbung " +
                "WHERE \"companyID\" = '" + companyid + "' ";
        resultSet = statement.executeQuery(insert);
        List<Bewerbung> liste = new ArrayList<>();
        Bewerbung offer;

        try {
            while (resultSet.next()) {
                offer = new Bewerbung();
                offer.setBewerbungsID(resultSet.getInt("bewerbungsID"));
                offer.setJobofferID(resultSet.getInt("jobofferID"));
                offer.setCompanyID(resultSet.getInt("companyID"));
                offer.setStudentID(resultSet.getInt("studentID"));
                offer.setBewerbungsdatum(new java.sql.Date(resultSet.getDate("bewerbungsdatum").getTime()).toLocalDate());
                offer.setNotes(resultSet.getString("notes"));
                liste.add(offer);
            }
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Alle offer mit der companyID: " + companyid + " wurden abgerufen");
        } catch (SQLException e) {
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "retrieveCompanyOffers(int id) in JobOfferDAO failed", e);
        }
        return liste;
    }

    @Override
    public Bewerbung retrieve(String attribute) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public List<Bewerbung> retrieveAll() throws DatabaseException {
        final String sql = "SELECT * FROM \"collDB\".bewerbung";
        Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Alle Bewerbungen wurden abgerufen.");
        return execute(sql);
    }

    @Override
    public Bewerbung create(Bewerbung bewerbung) throws DatabaseException, SQLException {
        final String insertQuery2 = "INSERT INTO \"collDB\".bewerbung ( \"jobofferID\", \"companyID\", \"studentID\", bewerbungsdatum, notes) " +
                "VALUES ('" + bewerbung.getJobofferID() +
                "','" + bewerbung.getCompanyID() + "', '" + bewerbung.getStudentID() +
                "', '" + bewerbung.getBewerbungsdatum() + "', '" + bewerbung.getNotes() + "') " +
                "RETURNING *";
        PreparedStatement pst = this.getPreparedStatement(insertQuery2);
        ResultSet resultSet = pst.executeQuery();
        if (resultSet.next()) {
            Bewerbung offer = new Bewerbung();
            offer.setBewerbungsID(resultSet.getInt("bewerbungsID"));
            offer.setJobofferID(resultSet.getInt("jobofferID"));
            offer.setCompanyID(resultSet.getInt("companyID"));
            offer.setStudentID(resultSet.getInt("studentID"));
            offer.setBewerbungsdatum(new java.sql.Date(resultSet.getDate("bewerbungsdatum").getTime()).toLocalDate());
            offer.setNotes(resultSet.getString("notes"));
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Bewerbungs-Objekt: " + offer + "wurde erfolgreich gespeichert.");
            return offer;
        } else {
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "Bewerbungs-Objekt: " + bewerbung + "konnte nicht richtig gespeichert werden.");
            return null;
        }
    }

    @Override
    protected Bewerbung create(ResultSet resultSet) {
        Bewerbung dto = new Bewerbung();
        try {
            dto.setBewerbungsID(resultSet.getInt("bewerbungsID"));
            dto.setJobofferID(resultSet.getInt("jobofferID"));
            dto.setCompanyID(resultSet.getInt("companyID"));
            dto.setStudentID(resultSet.getInt("studentID"));
            dto.setBewerbungsdatum(new java.sql.Date(resultSet.getDate("bewerbungsdatum").getTime()).toLocalDate());
            dto.setNotes(resultSet.getString("notes"));
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Bewerbungs-Objekt: " + dto + "wurde erfolgreich gespeichert.");
        } catch (SQLException e) {
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "create(ResultSet resultSet) in BewerbungsDAO failed", e);
        }
        return dto;
    }

    @Override
    public Bewerbung update(Bewerbung bewerbung) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public Bewerbung delete(Bewerbung bewerbung) throws DatabaseException {

        //language=PostgreSQL
        final String deleteQuery = " DELETE FROM \"collDB\".bewerbung WHERE \"bewerbungsID\" = " + bewerbung.getBewerbungsID() + " RETURNING * ";

        List<Bewerbung> result = execute(deleteQuery);

        if (result.size() < 1) {
            throw new DatabaseException("delete(Bewerbung bewerbung) failed");
        }
        return result.get(0);
    }

    public Bewerbung delete(int bewerbungsID) throws DatabaseException {
        //language=PostgreSQL
        final String deleteQuery = "DELETE FROM \"collDB\".bewerbung WHERE \"bewerbungsID\" = " + bewerbungsID + " RETURNING *";
        List<Bewerbung> result = execute(deleteQuery);
        if (result.size() < 1) {
            throw new DatabaseException("delete(int bewerbungsID) failed");
        }
        return result.get(0);
    }
}
