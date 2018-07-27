package com.mz.mreal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTest {
    public static void main(String[] args) throws SQLException {
        Connection connect = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/uniweb_0000", "root", "root");
        System.out.println("Connection successful");
        connect.close();
    }

}
