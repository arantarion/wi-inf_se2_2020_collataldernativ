package org.bonn.se2.model.dao;

import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T> {

    private JDBCConnection connection = JDBCConnection.getInstance();

    protected AbstractDAO() throws DatabaseException {
    }

    protected Statement getStatement() throws DatabaseException {
        return connection.getStatement();
    }

    protected PreparedStatement getPreparedStatement(String sql) throws DatabaseException {
        return connection.getPreparedStatement(sql);
    }

    protected List<T> execute(String sql) throws DatabaseException {
        List<T> results = new ArrayList<>();
        Statement statement = this.getStatement();
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            throw new DatabaseException("Es gibt ein Problem mit dem ResultSet");
        }

        try {
            while (resultSet.next()) {
                results.add(create(resultSet));
            }
            return results;
        } catch (SQLException ex) {
            throw new DatabaseException("Es gibt ein Problem beim Iterieren über das ResultSet");
        } finally {
            connection.closeConnection();
        }
    }

    //TODO
    protected List<T> executePrepared(String sql) throws DatabaseException {
        List<T> results = new ArrayList<>();
        PreparedStatement preparedStatement = this.getPreparedStatement(sql);
        return results;
    }

    abstract protected T create(ResultSet resultSet) throws DatabaseException;

}
