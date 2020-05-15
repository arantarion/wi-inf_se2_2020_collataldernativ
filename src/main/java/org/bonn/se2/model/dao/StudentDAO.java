package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class StudentDAO extends AbstractDAO<Student> implements DAOInterface<Student> {

    public StudentDAO() throws DatabaseException {
    }

    @Override
    public Student retrieve(int id) throws DatabaseException {
        final String sql =
                "SELECT * FROM \"collDB\".\"user\"\n" +
                        "JOIN \"collDB\".student ON \"user\".userID = student.userID\n" +
                        "JOIN \"collDB\".address ON \"user\".userID = address.userID\n" +
                        // LEFT OUTER JOIN ... etc
                        "WHERE student.studentID = ?;";

        List<Student> result = executePrepared(sql, id);
        if (result.size() < 1) {
            throw new DatabaseException("retrieve(int id) did not return a DTO");
        }
        return result.get(0);
    }

    @Override
    public Student retrieve(String attribute) throws DatabaseException {
        final String sql =
                "SELECT * FROM \"collDB\".\"user\"\n" +
                        "JOIN \"collDB\".student ON \"user\".userID = student.userID\n" +
                        "JOIN \"collDB\".address ON \"user\".userID = address.userID\n" +
                        // LEFT OUTER JOIN ... etc
                        "WHERE username = ?\n" +
                        "OR email = ?;";

        List<Student> result = executePrepared(sql, attribute, attribute);
        if (result.size() < 1) {
            throw new DatabaseException("retrieve(String attribute) did not return a DTO");
        }
        return result.get(0);
    }

    @Override
    public List<Student> retrieveAll() throws DatabaseException {
        String sql =
                "SELECT * FROM \"collDB\".\"user\"\n" +
                        "INNER JOIN \"collDB\".student ON \"user\".userID = student.userID\n" +
                        "INNER JOIN \"collDB\".address ON \"user\".userID = address.userID\n";
        // LEFT OUTER JOIN ...
        return execute(sql);
    }

    @Override
    public Student create(Student student) throws Exception {
        User user = new UserDAO().create(student);

        final String query =
                "INSERT INTO \"CollDB\".student (vorname, nachname, geburtstag, userID)\n" +
                        "VALUES (?,?,?,?)\n" +
                        "RETURNING studentid";
        
        PreparedStatement preparedStatement = this.getPreparedStatement(query);
        preparedStatement.setString(1, student.getVorname());
        preparedStatement.setString(2, student.getNachname());
        preparedStatement.setDate(3, Date.valueOf(student.getGeburtstag()));
        preparedStatement.setInt(4, user.getUserID());
        ResultSet set = preparedStatement.executeQuery();
        if (!set.next())
            throw new DatabaseException("[" + StudentDAO.class.toString() + "] Student has not been created!");
        return retrieve(set.getInt(1));
    }

    @Override
    protected Student create(ResultSet resultSet) throws DatabaseException {
        return null;
    }

    @Override
    public Student update(Student student) throws Exception {
        return null;
    }

    @Override
    public Student delete(Student student) throws Exception {
        final String deleteQuery =
                "DELETE FROM \"CollDB\".user\n" +
                        "WHERE username = ?\n" +
                        "RETURNING *;";

        List<Student> result = executePrepared(deleteQuery, student.getUsername());
        if (result.size() < 1) {
            throw new DatabaseException("delete(Student student) failed");
        }
        return result.get(0);
    }
}
