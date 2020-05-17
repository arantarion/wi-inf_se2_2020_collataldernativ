
package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer Henry Weckermann
 */

public class UserDAO extends AbstractDAO<User> implements DAOInterface<User> {


    public UserDAO() throws DatabaseException {
        super();
    }

    @Override
    public User retrieve(int id) throws DatabaseException, InvalidCredentialsException {
        //language=PostgreSQL
        final String sql =
                "SELECT * FROM \"collDB\".\"user\" " +
                        "FULL OUTER JOIN \"collDB\".address ON \"user\".\"userID\" = address.\"userID\" " +
                        "WHERE \"userID\" = '" + id + "';";

        List<User> result = execute(sql);
        if (result.size() < 1) {
            throw new InvalidCredentialsException();
        }
        return result.get(0);
    }

    @Override
    public User retrieve(String attribute) throws DatabaseException, InvalidCredentialsException {
        //language=PostgreSQL
        String sql =
                "SELECT * FROM \"collDB\".user " +
                        "WHERE \"collDB\".user.username = '" + attribute + "' " +
                        "OR \"collDB\".user.email = '" + attribute + "'";
        //"FULL OUTER JOIN \"collDB\".address ON \"user\".userID = address.userID\n" +

        List<User> result = execute(sql);
        if (result.size() < 1) {
            throw new InvalidCredentialsException();
        }
        return result.get(0);
    }

    @Override
    public List<User> retrieveAll() throws Exception {
        //language=PostgreSQL
        final String sql =
                "SELECT * FROM \"collDB\".user " +
                        "JOIN \"collDB\".address ON \"user\".\"userID\" = address.\"userID\";";
        return execute(sql);
    }

    @Override
    public User create(User user) throws Exception {
        //language=PostgreSQL
        final String insertQuery = "INSERT INTO \"collDB\".user (username, email, passwort) " +
                "VALUES (?, ?, ?) " +
                "RETURNING *";

        List<User> result = executePrepared(insertQuery, user.getUsername(), user.getEmail(), user.getPasswort());
        if (result.size() < 1) {
            throw new DatabaseException("create(User user) did not return a DTO");
        }
        return result.get(0);
    }

    @Override
    protected User create(ResultSet resultSet) throws DatabaseException {
        User dto;
        try {
            dto = new User(resultSet.getString("username"),
                    resultSet.getString("email"),
                    resultSet.getString("passwort"));
            dto.setUserID(resultSet.getInt("userID"));
            //dto.setRegistrationsDatum(resultSet.getDate("registrationsDatum").toLocalDate());

            //Address address = new AddressDAO().getOne(dto.getAddressid());
            //dto.setAddress(address);
            return dto;
        } catch (SQLException e) {
            throw new DatabaseException("couldn't create UserDTO from resultset: " + e.getMessage());
        }
    }

    @Override
    public User update(User user) throws Exception {
        return null;
    }

    @Override
    public User delete(User user) throws Exception {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".user " +
                        "WHERE username = ? " +
                        "RETURNING *;";

        List<User> result = executePrepared(deleteQuery, user.getUsername());
        if (result.size() < 1) {
            throw new DatabaseException("delete(User user) failed");
        }
        return result.get(0);
    }
}
