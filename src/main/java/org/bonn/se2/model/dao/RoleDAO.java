package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Role;
import org.bonn.se2.model.objects.dto.User;
import org.bonn.se2.services.db.JDBCConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoleDAO extends AbstractDAO {

    public static RoleDAO dao = null;

    private RoleDAO() {
    }

    public static RoleDAO getInstance() {
        if (dao == null) {
            dao = new RoleDAO();
        }
        return dao;
    }

    public List<Role> getRolesForUser(User user) {
        Statement statement = this.getStatement();

        ResultSet rs = null;

        try {
            //TODO
            rs = statement.executeQuery("SELECT * FROM realm.user_to_rolle WHERE realm.user_to_rolle.login = \'" + user.getLogin() + "\' ");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (rs == null) return null;

        List<Role> liste = new ArrayList<>();
        Role role;

        try {
            while (rs.next()) {
                role = new Role();
                role.setBezeichnung(rs.getString(2));
                liste.add(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return liste;

    }

}
