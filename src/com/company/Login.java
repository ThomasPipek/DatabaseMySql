package com.company;

import java.sql.*;

class Login {
    public Connection getConnection() throws SQLException {
        String dbConnection = "jdbc:sqlite:Database.db";
        return DriverManager.getConnection(dbConnection);
    }

    public void doPrivilegedAction( String username, String password) throws SQLException {
        Connection connection = getConnection();
        if (connection == null) {
            // handle error
        }
        try {
            String sqlString = "SELECT * FROM users WHERE username = '"
                    + username +
                    "' AND password = '" + password + "'";
            // String sqlString = "SELECT * FROM users WHERE username = '"
            //        + username +
            //        "' AND password_hash = '" + pwd + "'";
            System.out.println(sqlString);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlString);
            if (!rs.next()) {
                throw new SecurityException("User name or password incorrect");
            }
            // Authenticated; proceed
            System.out.println("Klasse Login: erfolgreich eingeloggt");
        } finally {
            try {
                connection.close();
            } catch (SQLException x) {
                // forward to handler
            }
        }
    }

    public void doManageUser( String username, String password) throws SQLException {
        Connection connection = getConnection();
        if (connection == null) {
            // handle error
        }
        try {
            Statement stmt = connection.createStatement();
            String sqlString = "CREATE TABLE if not exists users (\n" +
                    "\tid  integer primary key NOT NULL,\n" +
                    "\tusername varchar(32) NOT NULL,\n" +
                    "\tpassword varchar(50) NOT NULL\n" +
                    ")";
            stmt.execute(sqlString);

            sqlString = "SELECT * FROM users WHERE username = '" + username + "';";
            ResultSet rs = stmt.executeQuery(sqlString);
            if (!rs.next()) {
                // User existiert nicht
                System.out.println("Klasse Login: User exisitiert nicht => insert");
                sqlString = "insert into users (username, password) values ('" + username + "', '" + password + "');";
                stmt = connection.createStatement();
                boolean result = stmt.execute(sqlString);
            }
            else {
                // User existiert
                System.out.println("Klasse Login: User exisitiert => update");
                sqlString = "update users set password='" + password + "' where username = '" + username + "';";
                stmt = connection.createStatement();
                boolean result = stmt.execute(sqlString);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException x) {
                // forward to handler
            }
        }
    }
}
