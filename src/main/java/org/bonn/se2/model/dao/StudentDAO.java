
package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                        "WHERE student.\"userID\" = '" + id + "'" + " OR student.\"studentID\" = '" + id + "';";

        List<Student> result = execute(sql);

        if (result.size() < 1) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "retrieve(int id) did not return a DTO.");
            throw new DatabaseException("retrieve(int id) did not return a DTO");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Der Student mit der userID: " + id + " wurde erfolgreich abgerufen.");
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
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "retrieve(String attribute) did not return a DTO.");
            throw new DatabaseException("retrieve(String attribute) did not return a DTO");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Der Student mit dem Attribut: " + attribute + " wurde erfolgreich abgerufen.");
        return result.get(0);
    }
    
    public List<Student> retrieveStudents(String attribute) throws DatabaseException {
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
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "retrieve(String attribute) did not return a DTO.");
            throw new DatabaseException("retrieve(String attribute) did not return a DTO");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Der Student mit dem Attribut: " + attribute + " wurde erfolgreich abgerufen.");
        return result;
    }

    @Override
    public List<Student> retrieveAll() throws DatabaseException {
        //language=PostgreSQL
        String sql =
                "SELECT * FROM \"collDB\".user " +
                        "JOIN \"collDB\".student ON \"user\".\"userID\" = student.\"userID\"";
        // LEFT OUTER JOIN ...
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Alle Studenten wurden abgerufen.");
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

        if (!resultSet.next()) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "create(Student student) in StudentDAO failed");
            throw new DatabaseException("create(Student student) in StudentDAO failed");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Student: " + student + " wurde erfolgreich erstellt.");
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
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "create(ResultSet resultset) in StudentDAO failed");
            throw new DatabaseException("create(ResultSet resultSet) in StudentDAO failed");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Student: " + dto + " wurde erfolgreich gespeichert.");
        return dto;
    }

    @Override
    public Student update(Student updatedItem) throws Exception {
        UserDAO userDAO = new UserDAO();

        userDAO.update(new User(updatedItem.getUsername(), updatedItem.getEmail(), updatedItem.getPasswort(), updatedItem.getImage(), updatedItem.getUserID()));

        //language=PostgreSQL
        String queryStudent = "UPDATE \"collDB\".student " +
                "SET (studienfach, fachsemester) = (?, ?) " +
                "WHERE \"userID\" = '" + updatedItem.getUserID() + "';";
        try {
            PreparedStatement pst = this.getPreparedStatement(queryStudent);
            pst.setString(1, updatedItem.getStudienfach());
            pst.setInt(2, updatedItem.getFachsemester());
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "failed to update studentdata", e);
            throw new DatabaseException("failed to udpate studentdata");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Student wurde erfolgreich geändert.");
        return this.retrieve(updatedItem.getEmail());

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
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "delete(Student student) in StudentDAO failed");
            throw new DatabaseException("delete(Student student) failed");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Student: " + student + " wurde erfolgreich gelöscht.");
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
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "delelte(int ID) in StudentDAO failed");
            throw new DatabaseException("delete(int ID) failed");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "Studen mit der ID: " + ID + " wurde erfolgreich gelöscht.");
        return result.get(0);
    }
}
