package gui;

import java.sql.Connection;

public class TestDB {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if(con != null) {
            System.out.println("Connected");
        } else {
            System.out.println("Failed");
        }
    }
}