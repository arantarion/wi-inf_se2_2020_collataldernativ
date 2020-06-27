package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Anton Drees
 */

public class CompanyDAO extends AbstractDAO<Company> implements DAOInterface<Company> {

    public CompanyDAO() throws DatabaseException {
    }

    @Override
    public Company retrieve(int id) throws DatabaseException {
        //language=PostgreSQL
        String sql =
                "SELECT * FROM \"collDB\".user " +
                        "JOIN \"collDB\".company ON \"user\".\"userID\" = company.\"userID\" " +
                        "WHERE company.\"userID\" = '" + id + "'";

        List<Company> result = execute(sql);
        if (result.size() < 1) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, "retrieve(int id) failed.");
            throw new DatabaseException("retrieve(int id) failed");
        }
        Logger.getLogger(CompanyDAO.class.getName()).log(Level.INFO, "Die Company mit der userID: " + id + " wurde abgerufen.");
        return result.get(0);
    }

    public Company retrieveCompany(int companyID) throws DatabaseException{
        //language=PostgreSQL
        String sql =
                "SELECT * FROM \"collDB\".company " +
                        "WHERE company.\"companyID\" = '" + companyID + "'";

        List<Company> result = execute(sql);
        if (result.size() < 1) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, "retrieve(int companyid) failed.");
            throw new DatabaseException("retrieve(int companyid) failed");
        }
        Logger.getLogger(CompanyDAO.class.getName()).log(Level.INFO, "Die Company mit der companyID: " + companyID + " wurde abgerufen.");
        return result.get(0);
    }

    @Override
    public Company retrieve(String attribute) throws DatabaseException {
        //language=PostgreSQL
        String query =
                "SELECT * FROM \"collDB\".company\n" +
                        "JOIN \"collDB\".user ON company.\"userID\" = \"user\".\"userID\" " +
                        //"JOIN \"collDB\".address ON \"user\".\"userID\" = address.\"userID\" " +
                        "WHERE username = '" + attribute + "' " +
                        "OR email = '" + attribute + "' " +
                        "OR name = '" + attribute + "';";

        List<Company> list = execute(query);
        Logger.getLogger(CompanyDAO.class.getName()).log(Level.INFO, "Die Company mit dem Attribut: " + attribute + " wurde abgerufen.");
        if (list.size() < 1) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, "retrieve(String attribute) failed");
            throw new DatabaseException("retrieve(String attribute) failed");
        }
        return list.get(0);
    }

    @Override
    public List<Company> retrieveAll() throws DatabaseException {
        //language=PostgreSQL
        String query =
                "SELECT * FROM \"collDB\".company " +
                        "JOIN \"collDB\".\"user\" ON company.\"userID\" = \"user\".\"userID\" "; //+
        //"JOIN \"collDB\".address ON \"user\".\"userID\" = address.\"userID\";";
        Logger.getLogger(CompanyDAO.class.getName()).log(Level.INFO, "Es wurden alle Companys abgerufen.");
        return execute(query);
    }

    @Override
    public Company create(Company company) throws Exception {

        User user = new UserDAO().create(company);

        //language=PostgreSQL
        String query = "INSERT INTO \"collDB\".company (name, beschreibung, \"companyID\", \"userID\", \"webURL\") " +
                "VALUES ('" + company.getName() + "','" + company.getBeschreibung() + "', DEFAULT, '" + user.getUserID() + "', '" + company.getWebURL() + "') " +
                "RETURNING *";
        PreparedStatement pst = this.getPreparedStatement(query);
        ResultSet set = pst.executeQuery();
        if (set.next()) {
            Company company2 = new Company();
            company2.setUserID(set.getInt(1));
            company2.setName(set.getString("name"));
            company2.setBeschreibung(set.getString("beschreibung"));
            company2.setUserID(set.getInt("userID"));
            company2.setcompanyID(set.getInt("companyID"));
            company2.setWebURL(set.getString("webURL"));
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.INFO, "Die Company : " + company + " konnte erfoglreich gespeichert werden.");
            return company;
        } else {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, "Company-Objekt " + company + " konnte nicht richtig gespeichert werden!");
            return null;
        }


//        final String insertQuery2 = "INSERT INTO \"collDB\".company (name, \"webURL\", beschreibung, branche, ansprechpartner, \"userID\", bewertung) " +
//                "VALUES (?,?,?,?,?,?,?) " +
//                "RETURNING \"companyID\"";
//
//        List<Company> result = executePrepared(insertQuery2, company.getName(),company.getWebURL(),company.getBeschreibung(),company.getBranche(),company.getAnsprechpartner(),company.getcompanyID(),company.getBewertung());
//        if (result.size() < 1) {
//            throw new DatabaseException("create(Company company) did not return a DTO");
//        }
//        return result.get(0);
    }

    @Override
    protected Company create(ResultSet resultSet) throws DatabaseException {

        Company dto = new Company();

        try {
            dto.setName(resultSet.getString("name"));
            dto.setBeschreibung(resultSet.getString("beschreibung"));
            dto.setcompanyID(resultSet.getInt("companyID"));
            dto.setWebURL(resultSet.getString("webURL"));
            dto.setUserID(resultSet.getInt("userID"));
            dto.setUsername(resultSet.getString("username"));
            dto.setEmail(resultSet.getString("email"));
            dto.setAnsprechpartner(resultSet.getString("ansprechpartner"));
            dto.setBranche(resultSet.getString("branche"));
            dto.setBewertung(resultSet.getInt("bewertung"));
            //Address address = new AddressDAO().retrieve(resultSet.getInt("addressid"));
            //dto.setAdresse(address);
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.INFO, "Die Company : " + dto + " konnte erfolgreich gespeichert werden.");
        } catch (SQLException e) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, "create(ResultSet resultset) in CompanyDAO failed", e);
            //throw new DatabaseException("create(ResultSet resultSet) in CompanyDAO failed");
        }
        return dto;
    }

    @Override
    public Company update(Company updatedItem) throws Exception {
        Company companyNewDto = new Company();
        Address address = updatedItem.getAdresse();
        companyNewDto.setAdresse(address);

        String query;

        if (updatedItem.getPasswort() == null) {
            //language=PostgreSQL
            query = "UPDATE \"collDB\".\"user\"\n" +
                    "SET (username, email, bild) = (?, ?, ?) " +
                    "WHERE \"userID\" = '" + updatedItem.getUserID() + "';";

            try {
                PreparedStatement pst = this.getPreparedStatement(query);
                pst.setString(1, updatedItem.getUsername());
                pst.setString(2, updatedItem.getEmail());
                pst.setBytes(3, updatedItem.getImage());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException();
            }
        } else {
            //language=PostgreSQL
            query = "UPDATE \"collDB\".\"user\" " +
                    "SET (username, email, passwort, bild) = (?, ?, ?, ?) " +
                    "WHERE \"userID\" = '" + updatedItem.getUserID() + "';";

            try {
                PreparedStatement pst = this.getPreparedStatement(query);
                pst.setString(1, updatedItem.getUsername());
                pst.setString(2, updatedItem.getEmail());
                pst.setString(3, updatedItem.getPasswort());
                pst.setBytes(4, updatedItem.getImage());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException();
            }
        }

        //language=PostgreSQL
        String queryCompany = "UPDATE \"collDB\".company\n" +
                "SET (name, beschreibung, \"webURL\") = (?, ?, ?) " +
                "WHERE \"userID\" = ?;";

        try {
            PreparedStatement pst = this.getPreparedStatement(queryCompany);
            pst.setString(1, updatedItem.getName());
            pst.setString(2, updatedItem.getBeschreibung());
            pst.setString(3, updatedItem.getWebURL());
            pst.setInt(4, updatedItem.getUserID());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException();
        } finally {
            connection.closeConnection();
        }

        return retrieve(updatedItem.getcompanyID());

    }

    @Override
    public Company delete(Company company) throws DatabaseException {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".user " +
                        "WHERE username = ? " +
                        "RETURNING *;";

        List<Company> result = executePrepared(deleteQuery, company.getUsername());
        if (result.size() < 1) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, "delete(Company company) failed");
            throw new DatabaseException("delete(Company company) failed");
        }
        Logger.getLogger(CompanyDAO.class.getName()).log(Level.INFO, "Die Company : " + company + " wurde erfoglreich gelöscht.");
        return result.get(0);
    }

    public Company delete(int ID) throws Exception {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".company\n" +
                        "WHERE \"userID\" = ?\n" +
                        "RETURNING *;";

        List<Company> result = executePrepared(deleteQuery, ID);
        if (result.size() < 1) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, "delete(int ID) failed");
            throw new DatabaseException("delete(int ID) failed");
        }
        Logger.getLogger(CompanyDAO.class.getName()).log(Level.INFO, "Die Company mit der userID: " + ID + " wurde erfoglreich gelöscht.");
        return result.get(0);
    }

}
