package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Anton Drees
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
                        "WHERE \"user\".\"userID\" = '" + id + "';";

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
                "SELECT * FROM \"collDB\".user ";// +
        // "JOIN \"collDB\".address ON \"user\".\"userID\" = address.\"userID\";";
        return execute(sql);
    }

    @Override
    public User create(User user) throws Exception {
        //language=PostgreSQL
        final String insertQuery = "INSERT INTO \"collDB\".user (username, email, passwort, \"registrationsDatum\") " +
                "VALUES (?, ?, ?, ?) " +
                "RETURNING *";

        List<User> result = executePrepared(insertQuery, user.getUsername(), user.getEmail(), user.getPasswort(), LocalDate.now());
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
            dto.setRegistrationsDatum(resultSet.getDate("registrationsDatum").toLocalDate());

            //Address address = new AddressDAO().getOne(dto.getAddressid());
            //dto.setAddress(address);
            return dto;
        } catch (SQLException e) {
            throw new DatabaseException("couldn't create UserDTO from resultset: " + e.getMessage());
        }
    }

    @Override
    public User update(User user) throws Exception {
        //language=PostgreSQL
        final String updateQuery = "UPDATE \"collDB\".user " +
                "SET (username, email, passwort, bild) = " +
                "('" + user.getUsername() + "', '" + user.getEmail() + "', '" + user.getPasswort() + "', '" + user.getImage() + "') " +
                "WHERE \"userID\" = " + user.getUserID() + " " +
                "RETURNING *;";


        //List<User> result = executePrepared(updateQuery, user.getUsername(), user.getEmail(), user.getPasswort(), user.getImage(), user.getUserID());
        List<User> result = execute(updateQuery);
        if (result.size() < 1) {
            throw new DatabaseException("[" + UserDAO.class.toString() + "] updateOne() did not return a DTO");
        }
        return result.get(0);
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

    public User delete(int ID) throws Exception {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".user\n" +
                        "WHERE \"userID\" = ?\n" +
                        "RETURNING *;";

        List<User> result = executePrepared(deleteQuery, ID);
        if (result.size() < 1) {
            throw new DatabaseException("delete(int ID) failed");
        }
        return result.get(0);
    }
}
