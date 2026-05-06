
package gui;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/railway",
                "root",
                "Supra@123"
            );
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}