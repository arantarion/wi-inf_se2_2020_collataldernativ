package org.bonn.se2.services.db;

import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public interface JDBCConnectionInterface {

    void initConnection() throws DatabaseException;

    void openConnection() throws DatabaseException;

    Statement getStatement() throws DatabaseException;

    PreparedStatement getPreparedStatement(String sql) throws DatabaseException;

    void closeConnection();

}
