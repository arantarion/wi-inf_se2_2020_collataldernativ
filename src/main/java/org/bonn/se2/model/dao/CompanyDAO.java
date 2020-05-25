
package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
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
            throw new DatabaseException("retrieve(int id) failed");
        }
        return result.get(0);
    }

    @Override
    public Company retrieve(String attribute) throws DatabaseException {
        //language=PostgreSQL
        String query =
                "SELECT * FROM \"collDB\".company\n" +
                        "JOIN \"collDB\".user ON company.\"userID\" = \"user\".\"userID\" " +
                        "JOIN \"collDB\".address ON \"user\".\"userID\" = address.\"userID\" " +
                        "WHERE username = '" + attribute + "' " +
                        "OR email = '" + attribute + "' " +
                        "OR name = '" + attribute + "';";

        List<Company> list = execute(query);
        if (list.size() < 1) {
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
            System.out.println("Company erfolgreich gespeichert!");
            return company;
        } else {
            System.out.println("Company-Objekt konnte nicht richtig gespeichert werden!");
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
        System.out.println("1 zeile");
        Company dto = new Company();
        System.out.println("hahaha noobs");
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
            //Address address = new AddressDAO().retrieve(resultSet.getInt("addressid"));
            //dto.setAdresse(address);
        } catch (SQLException e) {
            throw new DatabaseException("create(ResultSet resultSet) in CompanyDAO failed");
        }
        return dto;
    }

    @Override
    public Company update(Company item) throws Exception {
        return null;
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
            throw new DatabaseException("delete(Company company) failed");
        }
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
            throw new DatabaseException("delete(int ID) failed");
        }
        return result.get(0);
    }

}
