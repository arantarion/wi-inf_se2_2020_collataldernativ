package org.bonn.se2.services.db;

import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.services.util.Configuration;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer generic class
 */

public class JDBCConnection implements JDBCConnectionInterface {

    private static JDBCConnection connection = null;
    private Connection conn;

    private JDBCConnection() throws DatabaseException {
        this.initConnection();
    }

    public static JDBCConnection getInstance() throws DatabaseException {
        if (connection == null) {
            connection = new JDBCConnection();
        }
        return connection;
    }

    @Override
    public void initConnection() throws DatabaseException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException();
        }
        this.openConnection();
    }

    @Override
    public void openConnection() throws DatabaseException {
        try {
            Properties probs = new Properties();
            probs.setProperty("user", Configuration.DB_Credentials.USERNAME);
            probs.setProperty("password", Configuration.DB_Credentials.USERNAME);

            String url = Configuration.DB_Credentials.URL;
            this.conn = DriverManager.getConnection(url, probs);

        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Fehler bei Zugriff auf die Datenbank. Ist eine sichere Verbindung hergestellt?");
        }
    }

    @Override
    public Statement getStatement() throws DatabaseException {
        try {

            if (this.conn.isClosed()) {
                this.openConnection();
            }

            return this.conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public PreparedStatement getPreparedStatement(String sql) throws DatabaseException {
        try {

            if (this.conn.isClosed()) {
                this.openConnection();
            }

            return this.conn.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isClosed() throws SQLException {
        return this.conn.isClosed();
    }

}
