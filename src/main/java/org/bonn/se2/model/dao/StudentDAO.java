package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    public static StudentDAO getInstance() throws DatabaseException {
        if (dao == null) {
            return new StudentDAO();
        }
        return null;
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

    public List<Student> retrieveStudents(String attribute) throws DatabaseException, SQLException {
        Statement statement = this.getStatement();
        ResultSet resultSet;
        //language=PostgreSQL
        final String sql =
                "SELECT * FROM \"collDB\".student " +
                        "WHERE studienfach LIKE '%" + attribute + "%' OR arbeitgeber LIKE '%" + attribute + "%' OR job LIKE '%" + attribute + "%' " +
                        "OR vorname LIKE '%" + attribute + "%' OR nachname LIKE '%" + attribute + "%';";

        resultSet = statement.executeQuery(sql);
        List<Student> liste = new ArrayList<>();
        Student dto;
        try {
            while (resultSet.next()) {
                dto = new Student();
                dto.setStudienfach(resultSet.getString("studienfach"));
                dto.setVorname(resultSet.getString("vorname"));
                dto.setNachname(resultSet.getString("nachname"));
                dto.setGeburtstag(new java.sql.Date(resultSet.getDate("geburtstag").getTime()).toLocalDate());
                dto.setStudentID(resultSet.getInt("studentID"));
                dto.setJob(resultSet.getString("job"));
                dto.setArbeitgeber(resultSet.getString("Arbeitgeber")); //creationDate
                dto.setFachsemester(resultSet.getInt("fachsemester"));
                dto.setUserID(resultSet.getInt("userID"));
                liste.add(dto);
            }
            Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Alle Students mit Attribut: " + attribute + " wurden abgerufen");
        } catch (SQLException e) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "retrieveStudents(String attribute) in StudentDAO failed", e);
        }
        return liste;
    }

    @Override
    public List<Student> retrieveAll() throws DatabaseException {
        //language=PostgreSQL
        String sql =
                "SELECT * FROM \"collDB\".user " +
                        "JOIN \"collDB\".student ON \"user\".\"userID\" = student.\"userID\"";

        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Alle Studenten wurden abgerufen.");
        return execute(sql);
    }

    @Override
    public Student create(Student student) throws DatabaseException, SQLException {
        User user = new UserDAO().create(student);

        //language=PostgreSQL
        final String query =
                "INSERT INTO \"collDB\".student (vorname, nachname, geburtstag, \"userID\")\n" +
                        "VALUES ('" + student.getVorname() + "', '" + student.getNachname() + "', '" + student.getGeburtstag() + "', '" + user.getUserID() + "') " +
                        "RETURNING *";

        PreparedStatement preparedStatement = this.getPreparedStatement(query);

        ResultSet set = preparedStatement.executeQuery();

        if (set.next()) {
            Student student1 = new Student();
            student1.setVorname(set.getString("vorname"));
            student1.setNachname(set.getString("nachname"));
            student1.setGeburtstag(set.getDate("geburtstag").toLocalDate());
            student1.setUserID(set.getInt("userID"));
            student1.setStudentID(set.getInt("studentID"));
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.INFO, "Der Student : " + student1 + " konnte erfoglreich gespeichert werden.");
            return student1;
        } else {
            return null;
        }
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

        } catch (SQLException | InvalidCredentialsException e) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "create(ResultSet resultSet) in StudentDAO failed");
            throw new DatabaseException("create(ResultSet resultSet) in StudentDAO failed");
        }

        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Student: " + dto + " wurde erfolgreich gespeichert.");
        return dto;
    }

    @Override
    public Student update(Student updatedItem) throws DatabaseException {
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
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "failed to update studentData", e);
            throw new DatabaseException("failed to update studentData");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Student wurde erfolgreich geändert.");
        return this.retrieve(updatedItem.getEmail());

    }

    @Override
    public Student delete(Student student) throws DatabaseException {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".user " +
                        "WHERE \"userID\" = ? " +
                        "RETURNING *;";

        List<Student> result = executePrepared(deleteQuery, student.getStudentID());
        if (result.size() < 1) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "delete(Student student) in StudentDAO failed");
            throw new DatabaseException("delete(Student student) failed");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Student: " + student + " wurde erfolgreich gelöscht.");
        return result.get(0);
    }

    public Student delete(int id) throws DatabaseException {
        //language=PostgreSQL
        final String deleteQuery =
                "DELETE FROM \"collDB\".student " +
                        "WHERE \"studentID\" = ? " +
                        "RETURNING *;";

        List<Student> result = executePrepared(deleteQuery, id);
        if (result.size() < 1) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "delete(int ID) in StudentDAO failed");
            throw new DatabaseException("delete(int ID) failed");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "Student mit der ID: " + id + " wurde erfolgreich gelöscht.");
        return result.get(0);
    }
}
