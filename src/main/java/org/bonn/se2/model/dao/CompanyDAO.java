package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Company;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.util.List;

public class CompanyDAO extends AbstractDAO<Company> implements DAOInterface<Company> {

    //TODO fix Exceptions
    //TODO complete classes
    //TODO maybe fix delete

    public CompanyDAO() throws DatabaseException {
    }

    @Override
    public Company retrieve(int id) throws DatabaseException {
        String sql =
                "SELECT * FROM \"CollDB\".\"user\"\n" +
                        "         JOIN \"CollDB\".company ON \"user\".userID = company.userID\n" +
                        "         JOIN \"CollDB\".address on \"user\".userID = address.userID\n" +
                        "WHERE companyID = " + id + ";";

        List<Company> result = execute(sql);
        if (result.size() < 1) {
            throw new DatabaseException("retrieve(int id) failed");
        }
        return result.get(0);
    }

    @Override
    public Company retrieve(String attribute) throws DatabaseException {
        String query =
                "SELECT * FROM \"CollDB\".company\n" +
                        "JOIN \"CollDB\".user ON company.userID = \"user\".userID\n" +
                        "JOIN \"CollDB\".address ON \"user\".userID = address.userID\n" +
                        "WHERE username = '" + attribute + "'\n" +
                        "OR email = '" + attribute + "'\n" +
                        "OR companyname = '" + attribute + "';";

        List<Company> list = execute(query);
        if (list.size() < 1) {
            throw new DatabaseException("retrieve(String attribute) failed");
        }
        return list.get(0);
    }

    @Override
    public List<Company> retrieveAll() throws DatabaseException {
        String query =
                "SELECT * FROM \"CollDB\".company\n" +
                        "JOIN \"CollDB\".\"user\" ON company.userID = \"user\".userID\n" +
                        "JOIN \"CollDB\".address ON \"user\".userID = address.userID;";
        return execute(query);
    }

    @Override
    public Company create(Company dto) throws Exception {
        return null;
    }

    @Override
    protected Company create(ResultSet resultSet) throws DatabaseException {
        return null;
    }

    @Override
    public Company update(Company item) throws Exception {
        return null;
    }

    @Override
    public Company delete(Company company) throws DatabaseException {
        final String deleteQuery =
                "DELETE FROM \"CollDB\".user\n" +
                        "WHERE username = ?\n" +
                        "RETURNING *;";

        List<Company> result = executePrepared(deleteQuery, company.getUsername());
        if (result.size() < 1) {
            throw new DatabaseException("delete(Company company) failed");
        }
        return result.get(0);
    }

}
