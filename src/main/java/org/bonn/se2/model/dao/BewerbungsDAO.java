package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Bewerbung;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class BewerbungsDAO extends AbstractDAO<Bewerbung> implements DAOInterface<Bewerbung>{

    public BewerbungsDAO() throws DatabaseException {
    }


    @Override
    public Bewerbung retrieve(int bewerbungsid) throws Exception {
        //language=PostgreSQL
        final String sql =
                "SELECT * FROM \"collDB\".bewerbung " +
                        "WHERE bewerbung.\"bewerbungsID\" = '" + bewerbungsid + "';";

        List<Bewerbung> result = execute(sql);
        if (result.size() < 1) {
            throw new InvalidCredentialsException();
        }
        Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Das Bewerbungs-Objekt mit der bewerbungsID: " + bewerbungsid + " wurde abgerufen.");
        return result.get(0);
    }

    @Override
    public Bewerbung retrieve(String attribute) throws Exception {
        return null;
    }

    @Override
    public List<Bewerbung> retrieveAll() throws Exception {
        final String sql = "SELECT * FROM \"collDB\".bewerbung";
        Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Alle Bewerbungen wurden abgerufen.");
        return execute(sql);
    }

    @Override
    public Bewerbung create(Bewerbung bewerbung) throws Exception {
        String insertQuery2 = "INSERT INTO \"collDB\".bewerbung (\"bewerbungsID\", \"jobofferID\", \"companyID\", \"studentID\", bewerbungsdatum, lebenslauf, bewerbung, notes) " +
                "VALUES ('" + bewerbung.getBewerbungsID() + "','" + bewerbung.getJobofferID() +
                "','" + bewerbung.getCompanyID() + "', '" + bewerbung.getStudentID() +
                "', '" + bewerbung.getBewerbungsdatum() + "', '" + bewerbung.getLebenslauf() +
                "', '" + bewerbung.getBewerbung() + "', '" + bewerbung.getNotes() + "') " +
                "RETURNING \"bewerbungsID\"";
        PreparedStatement pst = this.getPreparedStatement(insertQuery2);
        ResultSet resultSet = pst.executeQuery();
        if (resultSet.next()) {
            Bewerbung offer = new Bewerbung();
            offer.setBewerbungsID(resultSet.getInt("bewerbungsID"));
            offer.setJobofferID(resultSet.getInt("jobofferID"));
            offer.setCompanyID(resultSet.getInt("companyID"));
            offer.setStudentID(resultSet.getInt("studentID"));
            offer.setBewerbungsdatum(new java.sql.Date(resultSet.getDate("bewerbungsdatem").getTime()).toLocalDate());
            offer.setLebenslauf(resultSet.getInt("lebenslauf"));
            offer.setBewerbung(resultSet.getInt("bewerbung"));
            offer.setNotes(resultSet.getString("notes"));
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Bewerbungs-Objekt: " + offer + "wurde erfolgreich gespeichert.");
            return offer;
        } else {
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "Bewerbungs-Objekt: " + bewerbung + "konnte nicht richtig gespeichert werden.");
            return null;
        }
    }

    @Override
    protected Bewerbung create(ResultSet resultSet) throws DatabaseException {
        Bewerbung dto = new Bewerbung();
        try{
            dto.setBewerbungsID(resultSet.getInt("bewerbungsID"));
            dto.setJobofferID(resultSet.getInt("jobofferID"));
            dto.setCompanyID(resultSet.getInt("companyID"));
            dto.setStudentID(resultSet.getInt("studentID"));
            dto.setBewerbungsdatum(new java.sql.Date(resultSet.getDate("bewerbungsdatem").getTime()).toLocalDate());
            dto.setLebenslauf(resultSet.getInt("lebenslauf"));
            dto.setBewerbung(resultSet.getInt("bewerbung"));
            dto.setNotes(resultSet.getString("notes"));
            Logger.getLogger(OfferDAO.class.getName()).log(Level.INFO, "Bewerbungs-Objekt: " + dto + "wurde erfolgreich gespeichert.");
        }catch (Exception e){
            Logger.getLogger(OfferDAO.class.getName()).log(Level.SEVERE, "create(ResultSet resultSet) in BewerbungsDAO failed", e);
        }
        return dto;
    }

    @Override
    public Bewerbung update(Bewerbung bewerbung) throws Exception {
        return null;
    }

    @Override
    public Bewerbung delete(Bewerbung bewerbung) throws Exception {

        //language=PostgreSQL
        final String deleteQuery = " DELETE FROM \"collDB\".bewerbung WHERE \"bewerbungsID\" = " + bewerbung.getBewerbungsID() + " RETURNING * ";

        List<Bewerbung> result = execute(deleteQuery);

        if (result.size() < 1) {
            throw new DatabaseException("delete(Bewerbung bewerbung) failed");
        }
        return result.get(0);
    }

    public Bewerbung delete(int bewerbungsID) throws Exception{
        //language=PostgreSQL
        final String deleteQuery = "DELETE FROM \"collDB\".bewerbung WHERE \"bewerbungsID\" = " + bewerbungsID + " RETURNING *";
        List<Bewerbung> result = execute(deleteQuery);
        if (result.size() < 1){
            throw new DatabaseException("delete(int bewerbungsID) failed");
        }
        return result.get(0);
    }
}
