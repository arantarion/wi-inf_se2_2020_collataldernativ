package org.bonn.se2.model.objects.dto;

import java.io.Serializable;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class UserAtLogin extends User implements Serializable {

    private String email;
    private String password;

    public UserAtLogin(String login, String password) {
        this.email = login;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
