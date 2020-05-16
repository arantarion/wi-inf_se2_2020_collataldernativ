package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompanyDAO extends AbstractDAO<Company> implements DAOInterface<Company> {

    public CompanyDAO() throws DatabaseException {
    }

    @Override
    public Company retrieve(int id) throws DatabaseException {
        //language=PostgreSQL
        String sql =
                "SELECT * FROM \"collDB\".\"user\"\n" +
                        "         JOIN \"collDB\".company ON \"user\".\"userID\" = company.\"userID\" " +
                        "         JOIN \"collDB\".address on \"user\".\"userID\" = address.\"userID\" " +
                        "WHERE \"companyID\" = " + id + ";";

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
                        "JOIN \"collDB\".\"user\" ON company.\"userID\" = \"user\".\"userID\" " +
                        "JOIN \"collDB\".address ON \"user\".\"userID\" = address.\"userID\";";
        return execute(query);
    }

    @Override
    public Company create(Company company) throws Exception {
        //TODO ich bin verwirrt
        User user = new UserDAO().create(company);

        //language=PostgreSQL
        String query = "INSERT INTO \"collDB\".company (name, beschreibung, \"companyID\", \"userID\", \"webURL\") " +
                "VALUES ('" + company.getName() + "','" + company.getBeschreibung() +
                "', DEFAULT, '" + user.getUserID() + "', '" + company.getWebURL() + "') " +
                "RETURNING *";

        PreparedStatement preparedStatement = this.getPreparedStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Company company2 = new Company();
            company2.setUserID(resultSet.getInt(1));
            company2.setUserID(resultSet.getInt("userID"));
            company2.setName(resultSet.getString("name"));
            company2.setBeschreibung(resultSet.getString("beschreibung"));
            company2.setcompanyID(resultSet.getInt("companyID"));
            company2.setWebURL(resultSet.getString("webURL"));
            System.out.println("gespeichert");
            return company2;
        } else {
            System.out.println("create(Company company) in CompanyDAO failed");
            return null;
        }
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
            Address address = new AddressDAO().retrieve(resultSet.getInt("addressid"));
            dto.setAdresse(address);
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

}
