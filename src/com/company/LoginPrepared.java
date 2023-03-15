package com.company;

import java.sql.*;

class LoginPrepared {
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
            String sqlString =
                    "select * from users where username = ? and password = ?";
            PreparedStatement stmt = connection.prepareStatement(sqlString);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new SecurityException("User name or password incorrect");
            }
            // Authenticated; proceed
            System.out.println("Klasse LoginPrepared: erfolgreich eingeloggt");
        } finally {
            try {
                connection.close();
            } catch (SQLException x) {
                // forward to handler
            }
        }
    }
}