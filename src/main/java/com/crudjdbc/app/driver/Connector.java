package com.crudjdbc.app.driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static final Properties prop = new Properties();
    private static Connector instance;
    private Connection connection;

    private Connector() {
        try {
            prop.load(Connector.class.getResourceAsStream("/config.properties"));
            Class.forName(prop.getProperty("db.driver.class"));
            this.connection = DriverManager.getConnection(prop.getProperty("db.url"),
                    prop.getProperty("db.user"), prop.getProperty("db.pass"));
        } catch (ClassNotFoundException | IOException | SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connector getInstance() {
        if (instance == null) {
            instance = new Connector();
        }
        return instance;
    }
}

