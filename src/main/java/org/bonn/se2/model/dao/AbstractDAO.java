package org.bonn.se2.model.dao;

import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class AbstractDAO {

    protected Statement getStatement(){
        Statement statement;

        try {
            statement = JDBCConnection.getInstance().getStatement();
        } catch (DatabaseException e) {
            return null;
        }
        return statement;
    }

    protected PreparedStatement getPreparedStatement(String sql) {
        PreparedStatement statement;

        try {
            statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        } catch (DatabaseException e) {
            return null;
        }
        return statement;
    }

}
