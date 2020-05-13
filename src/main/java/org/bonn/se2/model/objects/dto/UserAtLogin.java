package org.bonn.se2.model.objects.dto;

public class UserAtLogin extends User {

    private String email;
    private String password;

    public UserAtLogin(String login, String password) {
        this.email = login;
        this.password = password;
    }

    public String getLogin() {
        return email;
    }
    public String getPassword() {
        return password;
    }

}
