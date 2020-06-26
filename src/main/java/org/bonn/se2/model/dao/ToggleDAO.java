package org.bonn.se2.model.dao;

import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToggleDAO extends AbstractDAO<Boolean> implements DAOInterface<Boolean> {

    public ToggleDAO() throws DatabaseException {
    }

    public Boolean retrieve() throws DatabaseException {

//        final String sql =
//                "SELECT 'erlaubeBewerbung' FROM \"collDB\".Toggle " +
//                "';";
        //language=PostgreSQL
        final String sql = "SELECT * FROM \"collDB\".toggle";

        List<Boolean> result = execute(sql);

        if (result.size() < 1) {
            Logger.getLogger(ToggleDAO.class.getName()).log(Level.SEVERE, "retrieve did not return a Boolean.");
            throw new DatabaseException("retrieve(int id) did not return a DTO");
        }
        Logger.getLogger(ToggleDAO.class.getName()).log(Level.INFO, "Der Wahrheitswert für die Togglebar wurde erfolgreich abgerufen.");
        return result.get(1);
    }

    public void updateToggle(Boolean updatedItem) throws Exception {

        //language=PostgreSQL
        String queryStudent = "UPDATE \"collDB\".Toggle " +
                "SET \"erlaubeBewerbung\" = " + updatedItem + ";";
        try {
            PreparedStatement pst = this.getPreparedStatement(queryStudent);
            pst.setBoolean(1, updatedItem);
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "failed to update Toggledata", e);
            throw new DatabaseException("failed to update Toggledata");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Toggle wurde erfolgreich geändert.");

    }

    @Override
    public Boolean retrieve(int id) throws Exception {
        System.out.println("retrieve int");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean retrieve(String attribute) throws Exception {
        System.out.println("retrieve string");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean create(Boolean dto) throws Exception {
        System.out.println("create boo");
        return null;
    }

    @Override
    public Boolean update(Boolean item) throws Exception {
        System.out.println("update boo");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean delete(Boolean item) throws Exception {
        System.out.println("delte");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Boolean> retrieveAll() throws Exception {
        System.out.println("retrieve all");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Boolean create(ResultSet resultSet) throws DatabaseException {
        System.out.println("create rrs");
        return null;
    }

}
