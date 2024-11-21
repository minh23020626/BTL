package com.example.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountDB {

    static String user = "root";
    static String password = "21012005";
    static String url = "jdbc:mysql://localhost/book";
    static String driver = "com.mysql.cj.jdbc.Driver";
    public static int id;
    public static String ISBN;

    public static Connection getCon() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }


}
