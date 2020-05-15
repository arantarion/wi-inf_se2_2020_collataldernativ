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
        final String selectQuery =
                "SELECT * FROM \"collDB\".\"user\"\n" +
                        "FULL OUTER JOIN \"collDB\".address ON \"user\".userID = address.userID\n" +
                        "WHERE userID = ?;";
        List<User> result = executePrepared(selectQuery, id);
        if (result.size() < 1) {
            throw new InvalidCredentialsException();
        }
        return result.get(0);
    }

    @Override
    public User retrieve(String attribute) throws DatabaseException, InvalidCredentialsException {
        String selectQuery =
                "SELECT * FROM \"collDB\".\"user\"\n" +
                        "FULL OUTER JOIN \"collDB\".address ON \"user\".userID = address.userID\n" +
                        "WHERE username = ?\n" +
                        "OR email = ?;";
        List<User> result = executePrepared(selectQuery, attribute, attribute);
        if (result.size() < 1) {
            throw new InvalidCredentialsException();
        }
        return result.get(0);
    }

    @Override
    public List<User> retrieveAll() throws Exception {
        String sql =
                "SELECT * FROM \"CollDB\".\"user\"\n" +
                        "JOIN \"CollDB\".address ON \"user\".userID = address.userID;";
        return execute(sql);
    }

    @Override
    public User create(User user) throws Exception {
        // Adresse fehlt!
        final String insertQuery = "INSERT INTO \"collDB\".user (username, email, passwort)\n" +
                "VALUES (?, ?, ?)\n" +
                "RETURNING *";

        List<User> result = executePrepared(insertQuery, user.getUsername(), user.getEmail(), user.getPasswort());
        if (result.size() < 1) {
            throw new DatabaseException("create(User user) did not return a DTO");
        }
        return result.get(0);
    }

    @Override
    protected User create(ResultSet resultSet) throws DatabaseException {
        return null;
    }

    @Override
    public User update(User user) throws Exception {
        return null;
    }

    @Override
    public User delete(User user) throws Exception {
        final String deleteQuery =
                "DELETE FROM \"collDB\".user\n" +
                        "WHERE username = ?\n" +
                        "RETURNING *;";

        List<User> result = executePrepared(deleteQuery, user.getUsername());
        if (result.size() < 1) {
            throw new DatabaseException("delete(User user) failed");
        }
        return result.get(0);
    }
}
