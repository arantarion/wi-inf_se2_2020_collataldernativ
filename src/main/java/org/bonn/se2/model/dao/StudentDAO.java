package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Student;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDAO extends AbstractDAO<Student> implements DAOInterface<Student> {

    protected StudentDAO() throws DatabaseException {
    }

    @Override
    public Student retrieve(int id) {
        final String sql = "SELECT *\n" +
                "FROM \"**DB NAME**\".\"user\"\n" +
                "         JOIN \"**DB NAME**\".student ON \"user\".userid = student.userid\n" +
                "         JOIN \"**DB NAME**\".address ON \"user\".address = address.addressid\n" +
                // LEFT OUTER JOIN ... etc
                "WHERE student.studentid = ?;";

        List<Student> result = null;
        try {
            result = executePrepared(sql, id);
        } catch (DatabaseException e) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result.get(0);
    }

    @Override
    public Student retrieve(String attribute) throws Exception {
        return null;
    }

    @Override
    public List<Student> retrieveAll() throws DatabaseException {
        //TODO
        String sql = "SELECT *\n" +
                "FROM \"**DB Name**\".\"user\"\n" +
                "         INNER JOIN \"**DB NAME**\".student\n" +
                "                    ON \"user\".userid = student.userid\n" +
                "         INNER JOIN \"**DB NAME**\".address\n" +
                "                    ON \"user\".address = address.addressid\n";
        // LEFT OUTER JOIN ...
        return execute(sql);
    }

    @Override
    public Student create(Student dto) throws Exception {
        return null;
    }

    @Override
    protected Student create(ResultSet resultSet) throws DatabaseException {
        return null;
    }

    @Override
    public Student update(Student item) throws Exception {
        return null;
    }

    @Override
    public Student delete(Student item) throws Exception {
        return null;
    }
}
