package org.bonn.se2.services.util;

/**
 *
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer Henry Weckermann
 */

public class Configuration {

    public static class DB_Credentials {
        public static final String USERNAME = "jvetmi2s";
        public static final String PASSWORD = "jvetmi2s";
        public static final String URL = "jdbc:postgresql://dumbo.inf.h-brs.de/jvetmi2s";
    }

    public static class Roles {
        public static final String STUDENT = "student";
        public static final String COMPANY = "company";
        public static final String ADMIN = "admin";
    }

    public static class Views {
        public static final String MAIN = "main";
        public static final String LOGIN = "login";
        public static final String REGIST = "registration";
        public static final String STUDDAT = "studentenDaten";
    }

}
