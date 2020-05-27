package org.bonn.se2.test.services.db;

import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.db.JDBCConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

class JDBCConnectionTest {

    private static JDBCConnection connection = null;

    @BeforeAll
    static void setup() throws DatabaseException {
        connection = JDBCConnection.getInstance();
    }

    @Test
    void getInstanceRegisterDriveOpenConnection() {
        assertNotNull(connection);
    }

    @Test
    void getStatement() {

        Statement statement = null;
        try {
            statement = connection.getStatement();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        assertNotNull(statement);

    }

    @Test
    void getPreparedStatement() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.getPreparedStatement("SELECT * FROM * WHERE *");
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        assertNotNull(preparedStatement);
    }

    @Test
    void closeConnection() throws SQLException {
        connection.closeConnection();
        assertTrue(connection.isClosed());
    }
}