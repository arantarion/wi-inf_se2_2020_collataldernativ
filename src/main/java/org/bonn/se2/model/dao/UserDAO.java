package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;

import java.sql.ResultSet;
import java.util.List;

public class UserDAO extends AbstractDAO<User> implements DAOInterface<User> {


    public UserDAO() throws DatabaseException {
        super();
    }

    @Override
    public User retrieve(int id) throws Exception {
        return null;
    }

    @Override
    public User retrieve(String attribute) throws DatabaseException {
        String selectQuery = "SELECT *\n" +
                "FROM \"**DB NAME**\".\"user\"\n" +
                "         FULL OUTER JOIN \"**DB NAME**\".address ON \"user\".address = address.addressid\n" +
                "WHERE username = ?\n" +
                "OR email = ?;";
        List<User> queryResult = executePrepared(selectQuery, attribute, attribute);
        return queryResult.get(0);
    }

    @Override
    public List<User> retrieveAll() throws Exception {
        String sql = "SELECT * FROM * JOIN *";
        return execute(sql);
    }

    @Override
    public User create(User dto) throws Exception {
        return null;
    }

    @Override
    protected User create(ResultSet resultSet) throws DatabaseException {
        return null;
    }

    @Override
    public User update(User item) throws Exception {
        return null;
    }

    @Override
    public User delete(User item) throws Exception {
        return null;
    }
}
