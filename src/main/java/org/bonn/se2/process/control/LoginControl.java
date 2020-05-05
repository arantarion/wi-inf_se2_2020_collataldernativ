package org.bonn.se2.process.control;

import com.vaadin.ui.UI;
import org.bonn.se2.gui.ui.MyUI;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.db.JDBCConnection;
import org.bonn.se2.services.util.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControl {

    public static void checkAuthentication(String user, String pw) throws InvalidCredentialsException, DatabaseException {

        ResultSet set;

        try {
            Statement state = JDBCConnection.getInstance().getStatement();
            //TODO
            set = state.executeQuery("SELECT * " +
                    "FROM realm.user " +
                    "WHERE realm.user.login = \'" + user + "\'" +
                    "AND realm.user.password = \'" + pw + "\'");

        } catch (SQLException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Fehler im SQL Befehl");
        }

        User userDTO = null;

        try {
            if (set.next()) {

                userDTO = new User();
                userDTO.setUsername(set.getString(1));
                //userDTO.setVorname(set.getString(3));

            } else {
                throw new InvalidCredentialsException();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }

        ((MyUI) UI.getCurrent()).setUser(userDTO);

        UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);

    }

    public static void logoutUser() {
        UI.getCurrent().close();
        UI.getCurrent().getPage().setLocation("");
    }

}
