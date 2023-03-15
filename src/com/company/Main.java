package com.company;

/*

in git
Branch Test

DBNavigator => Settings => + Database SQLite, Pfad angeben
DBBrowser => Mit Database verbinden
CREATE TABLE users (
	id  integer primary key NOT NULL,
	username varchar(32) NOT NULL,
	password varchar(50) NOT NULL
)
INSERT INTO users (id, username, password_hash , password)
VALUES( 1, 'user',	'value2' , 'passuser');

=> laufen lassen und commit

Library: => Add Library => Maven => sqlite-jdbc => Auswählen und Pfad setzen => OK
 */

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try
        {
            // Anfällig für SQL-Injection
            Login login = new Login();
            login.doManageUser("user", "passuser");
            login.doPrivilegedAction("user", "' or 1=1 --");

            // Jetzt mit Prepared Statements
            LoginPrepared loginPrepared = new LoginPrepared();
            // loginPrepared.doManageUser("user", "passuser");
            loginPrepared.doPrivilegedAction("user", "passuser");
        }
        catch (SQLException x)
        {
            System.out.print(x);
        }
    }
}
