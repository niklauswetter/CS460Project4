import java.io.*;
import java.util.*;
import java.sql.*;

public class Prog4
{
    public static void main(String[] args) {

        Scanner userInp = null;
        Connection dbconn = null;
        Statement stmt = null;
        ResultSet answer = null;
        final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
        String username = "";
        String password = "";

        String Query1 = "SELECT DISTINCT fName, lName FROM ccnw.Passenger WHERE binHistory = \"4\";";
        String Query2 = "";
        String Query3 = "";
        String Query4 = "";

        // Load the (Oracle) JDBC driver by initializing its base
        // class, 'oracle.jdbc.OracleDriver'.
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
                System.err.println("*** ClassNotFoundException:  "
                    + "Error loading Oracle JDBC driver.  \n"
                    + "\tPerhaps the driver is not on the Classpath?");
                System.exit(-1);
        }

        // Make and return a database connection to the user's
        // Oracle database
        try {
                dbconn = DriverManager.getConnection(oracleURL,username,password);
        } catch (SQLException e) {
                System.err.println("*** SQLException:  "
                    + "Could not open JDBC connection.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                System.exit(-1);
        }

        while (true) {
            System.out.println("Please choose from the available options: (Insert, Delete, Update, Query, or Quit)");
            userInp = new Scanner(System.in);
            String input = userInp.nextLine();
            if (input == "Insert") {
                System.out.println("Would you like to insert into Flight, Passenger, or Staff?");
                input = userInp.nextLine();
            } else if (input == "Delete") {
                System.out.println("Would you like to delete from Flight, Passenger, or Staff?");
                input = userInp.nextLine();
                continue;
            } else if (input == "Update") {
                System.out.println("Would you like to update Flight, Passenger, or Staff?");
                input = userInp.nextLine();
                continue;
            } else if (input == "Query") {
                System.out.println("Choose a query (1-4): ");
                input = userInp.nextLine();
                continue;
            } else if (input == "Quit") {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
                continue;
            }
        }
        userInp.close();
        try {
            dbconn.close();
        } catch (Exception e) {
            return;
        }
    }
}