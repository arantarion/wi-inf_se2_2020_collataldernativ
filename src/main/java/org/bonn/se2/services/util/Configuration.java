package org.bonn.se2.services.util;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann, Anton Drees
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
        public static final String PROFIL = "profil";
        public static final String KVERWALTUNG = "kontoverwaltung";
        public static final String DELETION = "deletion";
        public static final String OFFERCREATION = "jobOfferCreation";
    }

    public static class ImagePaths {
        public static final String PLACEHOLDER = "images/placeholder.png";
        public static final String CORPORATE = "images/logo.png";
    }
}
