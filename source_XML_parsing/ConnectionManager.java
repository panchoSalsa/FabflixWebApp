import java.util.*;
import java.sql.*; 

public class ConnectionManager {
    private static String driverName = "com.mysql.jdbc.Driver";
    // uncomment for my use
    // private static String url = "jdbc:mysql:///moviedb"; 
    private static String url = "jdbc:mysql:///moviedb4"; 
    //private static String url = "jdbc:mysql:///moviedb_project_3_grading";
    private static String username = "classta";   
    private static String password = "classta";


    // uncomment for my use
    // private static String username = "franco";   
    // private static String password = "zot";
    
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName(driverName).newInstance();
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (Exception ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection."); 
            }
        } catch (Exception ex) {
            // log an exception. for example:
            System.out.println("Driver not found."); 
        }
        return con;
    }
}