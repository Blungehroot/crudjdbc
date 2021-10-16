package com.crudjdbc.app.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
   private static final String URL = "jdbc:mysql://localhost:3308/crud_db";
   private static final String USER = "root";
   private static final String PASSWORD = "root";
   private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private Connector() {}

    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

