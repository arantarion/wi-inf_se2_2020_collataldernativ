package org.bonn.se2.model.dao;

import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.DontUseException;

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

        final String sql = "SELECT * FROM \"collDB\".toggle";

        List<Boolean> result = execute(sql);

        if (result.size() < 1) {
            Logger.getLogger(ToggleDAO.class.getName()).log(Level.SEVERE, "retrieve did not return a Boolean.");
            throw new DatabaseException("retrieve(int id) did not return a DTO");
        }
        Logger.getLogger(ToggleDAO.class.getName()).log(Level.INFO, "Der Wahrheitswert für die Togglebar wurde erfolgreich abgerufen.");
        return result.get(0);
    }

    public void updateToggle(Boolean updatedItem) throws DatabaseException {

        //language=PostgreSQL
        String queryToggle = "UPDATE \"collDB\".Toggle " +
                "SET \"erlaubeBewerbung\" = " + updatedItem + ";";
        try {
            PreparedStatement pst = this.getPreparedStatement(queryToggle);
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "failed to update Toggledata", e);
            throw new DatabaseException("failed to update Toggledata");
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.INFO, "Toggle wurde erfolgreich geändert.");

    }

    @Override
    public Boolean retrieve(int id) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public Boolean retrieve(String attribute) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public Boolean create(Boolean dto) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public Boolean update(Boolean item) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public Boolean delete(Boolean item) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public List<Boolean> retrieveAll() throws DontUseException {
        throw new DontUseException();
    }

    @Override
    protected Boolean create(ResultSet resultSet) {
        try {
            return resultSet.getBoolean("erlaubeBewerbung");
        } catch (SQLException e) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, "create(ResultSet) failed!", e);
        }
        return null;
    }

}
