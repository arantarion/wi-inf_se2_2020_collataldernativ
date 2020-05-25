
package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class StudentDAO extends AbstractDAO<Student> implements DAOInterface<Student> {

    public StudentDAO() throws DatabaseException {
    }

    @Override
    public Student retrieve(int id) throws DatabaseException {
        //language=PostgreSQL
        final String sql =
                "SELECT * FROM \"collDB\".user " +
                        "JOIN \"collDB\".student ON \"user\".\"userID\" = student.\"userID\" " +
                        "WHERE student.\"userID\" = '" + id + "'";

        //List<Student> result = executePrepared(sql, id);
        List<Student> result = execute(sql);

        if (result.size() < 1) {
            throw new DatabaseException("retrieve(int id) did not return a DTO");
        }
        return result.get(0);
    }

    @Override
    public Student retrieve(String attribute) throws DatabaseException {
        //language=PostgreSQL
        final String sql =
                "SELECT * FROM \"collDB\".user " +
                        "JOIN \"collDB\".student ON \"user\".\"userID\" = student.\"userID\" " +
                        //"JOIN \"collDB\".address ON \"user\".\"userID\" = address.\"userID\" " +
                        // LEFT OUTER JOIN ... etc
                        "WHERE username = ? " +
                        "OR email = ?;";

        List<Student> result = executePrepared(sql, attribute, attribute);
        if (result.size() < 1) {
            throw new DatabaseException("retrieve(String attribute) did not return a DTO");
        }
        return result.get(0);
    }

    @Override
    public List<Student> retrieveAll() throws DatabaseException {
        //language=PostgreSQL
        String sql =
                "SELECT * FROM \"collDB\".user " +
                        "INNER JOIN \"collDB\".student ON \"user\".\"userID\" = student.\"userID\" " +
                        "INNER JOIN \"collDB\".address ON \"user\".\"userID\" = address.\"userID\"";
        // LEFT OUTER JOIN ...
        return execute(sql);
    }

    @Override
    public Student create(Student student) throws Exception {
        User user = new UserDAO().create(student);

        //language=PostgreSQL
        final String query =
                "INSERT INTO \"collDB\".student (vorname, nachname, geburtstag, \"userID\")\n" +
                        "VALUES (?,?,?,?) " +
                        "RETURNING \"studentID\"";

        PreparedStatement preparedStatement = this.getPreparedStatement(query);
        preparedStatement.setString(1, student.getVorname());
        preparedStatement.setString(2, student.getNachname());
        preparedStatement.setDate(3, Date.valueOf(student.getGeburtstag()));
        preparedStatement.setInt(4, user.getUserID());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new DatabaseException("create(Student student) in StudentDAO failed");
        return retrieve(resultSet.getInt(1));

//        User user = new UserDAO().create(student);
//
//        final String insertQuery = "INSERT INTO \"collDB\".student (studienfach, vorname, nachname, geburtstag, job, arbeitgeber, fachsemester, \"userID\") " +
//                "VALUES (?,?,?,?,?,?,?,?) " +
//                "RETURNING \"studentID\"";
//
//        //language=PostgreSQL
//        final String que = "INSERT INTO \"collDB\".student (vorname, nachname, geburtstag, \"userID\") " +
//                "VALUES (?,?,?,?) " +
//                "RETURNING \"studentID\"";
//
////        List<Student> result = executePrepared(insertQuery, student.getStudienfach(),
////                student.getVorname(), student.getNachname(),
////                student.getGeburtstag(), student.getJob(),
////                student.getArbeitgeber(), student.getFachsemester(),
////                student.getStudentID());
//
//        List<Student> result = executePrepared(que, student.getVorname(), student.getNachname(), Date.valueOf(student.getGeburtstag()), user.getUserID());
//
//        if (result.size() < 1) {
//            throw new DatabaseException("create(Student student) did not return a DTO");
//        }
//        return result.get(0);

    }

    @Override
    protected Student create(ResultSet resultSet) throws DatabaseException {
        Student dto = new Student();

        try {
            dto.setUserID(resultSet.getInt("userID"));
            dto.setStudentID(resultSet.getInt("studentID"));
            dto.setVorname(resultSet.getString("vorname"));
            dto.setNachname(resultSet.getString("nachname"));
            dto.setGeburtstag(new java.sql.Date(resultSet.getDate("geburtstag").getTime()).toLocalDate());
            dto.setStudienfach(resultSet.getString("studienfach"));
            dto.setFachsemester(resultSet.getInt("fachsemester"));
            dto.setJob(resultSet.getString("job"));

            User user = new UserDAO().retrieve(dto.getUserID());
            dto.setEmail(user.getEmail());
            dto.setUsername(user.getUsername());
            dto.setPwHash(user.getPasswort());

            //Address address = new AddressDAO().retrieve(user.getUserID());
            //dto.setAdresse(address);

        } catch (Exception e) {
            throw new DatabaseException("create(ResultSet resultSet) in StudentDAO failed");
        }
        return dto;
    }

    @Override
    public Student update(Student student) throws Exception {
        return null;
    }

    @Override
    public Student delete(Student student) throws Exception {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".user\n" +
                        "WHERE username = ?\n" +
                        "RETURNING *;";

        List<Student> result = executePrepared(deleteQuery, student.getUsername());
        if (result.size() < 1) {
            throw new DatabaseException("delete(Student student) failed");
        }
        return result.get(0);
    }

    public Student delete(int ID) throws Exception {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".student\n" +
                        "WHERE \"userID\" = ?\n" +
                        "RETURNING *;";

        List<Student> result = executePrepared(deleteQuery, ID);
        if (result.size() < 1) {
            throw new DatabaseException("delete(int ID) failed");
        }
        return result.get(0);
    }
}
