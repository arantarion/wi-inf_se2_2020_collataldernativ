package org.bonn.se2.process.control;

import com.vaadin.ui.UI;
import org.bonn.se2.model.dao.UserDAO;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.model.objects.dto.UserAtLogin;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;
import org.bonn.se2.services.util.Configuration;
import org.bonn.se2.services.util.CryptoFunctions;
import org.bonn.se2.services.util.SessionFunctions;

public class LoginControl {

    public static void checkAuthentication(UserAtLogin loginUser) throws InvalidCredentialsException, DatabaseException {
        User user = new UserDAO().retrieve(loginUser.getEmail());

        if (CryptoFunctions.checkPw(loginUser.getPassword(), user.getPasswort())) {
            SessionFunctions.setCurrentUser(user);
            //SessionFunctions.setCurrentRole(getRole(loginUser))
            UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public static void logoutUser() {
        UI.getCurrent().close();
        UI.getCurrent().getPage().setLocation("");
    }

    static String getRole(UserAtLogin user) {
        // TODO
        // ? mit DAO lösen und einfach in die Exceptions reinlaufen lassen?
        //   Rolle als Attribut hinzufügen?
        return "";
    }

    // Original checkAuth Function
//    public static void checkAuthentication(String user, String pw) throws InvalidCredentialsException, DatabaseException {
//
//        ResultSet set;
//
//        try {
//            Statement state = JDBCConnection.getInstance().getStatement();
//            set = state.executeQuery("SELECT * " +
//                    "FROM realm.user " +
//                    "WHERE realm.user.login = \'" + user + "\'" +
//                    "AND realm.user.password = \'" + pw + "\'");
//
//        } catch (SQLException ex) {
//            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
//            throw new DatabaseException("Fehler im SQL Befehl");
//        }
//
//        User userDTO = null;
//
//        try {
//            if (set.next()) {
//
//                userDTO = new User();
//                userDTO.setUsername(set.getString(1));
//                //userDTO.setVorname(set.getString(3));
//
//            } else {
//                throw new InvalidCredentialsException();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            JDBCConnection.getInstance().closeConnection();
//        }
//
//        ((MyUI) UI.getCurrent()).setUser(userDTO);
//
//        UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
//
//    }

}
