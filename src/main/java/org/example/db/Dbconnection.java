package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnection {
    private static Dbconnection dbConnection;
    private Connection connection;
    public static String email;

   private Dbconnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/frozen_project",
                "root",
                "1234" +
                        ""
        );
    }
    public static Dbconnection getInstance() throws SQLException {
        Dbconnection Dbconnection;
        return (null == dbConnection) ? Dbconnection = new Dbconnection() : dbConnection;
    }
    public Connection getConnection() {
        return connection;
    }
    }



