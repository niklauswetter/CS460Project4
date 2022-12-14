import java.io.*;
import java.util.*;
import java.sql.*;

/**
 * This program demonstrates how to use the JDBC API to Work with a created database.
 * This program will allow the user to insert, delete and update three relations (Passenger
 * Flight and Staff). The program should check for proper inputs to prevent security breaches.
 * The program will also allow the user to choose from a list of 4 different queries to run.
 * 
 * @author: Carson Chapman
 * @since: 2022-12-05
 * 
 */
public class Prog4
{
    public static void main(String[] args) {

        // Establish IO and Database Objects...
        Scanner userInp = null;
        Connection dbconn = null;
        ResultSet answer = null;
        final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
        String username = "carsonchapman";
        String password = "a4441";

        // Defign the hard-coded parts to the queries...
        String Query1 = "SELECT DISTINCT P.fName, P.lName " + 
                        "FROM niklauswetter.Passenger P " + 
                        "JOIN niklauswetter.Ticket T ON (P.pNo = T.pNo)" +
                        "JOIN niklauswetter.Flight F ON (T.fNo = F.fNo)" +
                        ";";
        String Query2 = "SELECT P.fName, P.lName, p.isStudent " + 
                        "FROM niklauswetter.Flight F " + 
                        "JOIN niklauswetter.Ticket T ON (F.fNo = T.fNo)" +
                        "JOIN niklauswetter.Passenger P ON (P.pNo = T.pNo)";
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

        // Outer user Input Loop that will determin the operation to be performed...
        while (true) {
            Statement stmt = null;
            String tmpQuery = "";
            System.out.println("Please choose from the available options: (Insert, Delete, Update, Query, or Quit)");
            userInp = new Scanner(System.in);
            String input = userInp.nextLine();
            
            // If operation is insert, delete, or update, the program will perform the proper
            // user input checks and then create and execute the query...
            if (input.equals("Insert")) {
                while (true) {
                    tmpQuery = "";
                    System.out.println("Would you like to insert into Flight, Passenger, or Staff?");
                    input = userInp.nextLine();
                    if (input.equals("Flight")) {
                        System.out.println("Please enter the flight number: ");
                        input = userInp.nextLine();
                        try {
                            int fNo = Integer.parseInt(input);
                            tmpQuery = "INSERT INTO niklauswetter.Flight VALUES (" + fNo + ", ";
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid input");
                            continue;
                        }
                        System.out.println("Please enter the crew number: ");
                        input = userInp.nextLine();
                        try {
                            int cNo = Integer.parseInt(input);
                            tmpQuery = tmpQuery + cNo + ", ";
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid input");
                            continue;
                        }
                        System.out.println("Please enter the airline of the flight: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 2) {
                            System.err.println("Invalid input");
                            continue;
                        }
                        tmpQuery = tmpQuery + "\'" + input + "\'" + ", ";
                        System.out.println("Please enter the boarding gate: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 1) {
                            System.err.println("Invalid input");
                            continue;
                        }
                        tmpQuery = tmpQuery + "\'" + input + "\'" + ", ";

                        // Building timestamp bottom up...
                        System.out.println("Please enter the boarding time (TIMESTAMP: mm.dd.yyyy:hh:mm:ss): ");
                        System.out.println("Enter month:");
                        int month = userInp.nextInt();
                        System.out.println("Enter day:");
                        int day = userInp.nextInt();
                        System.out.println("Enter year:");
                        int year = userInp.nextInt();
                        System.out.println("Enter Hour:");
                        int hour = userInp.nextInt();
                        System.out.println("Enter Minute:");
                        int minute = userInp.nextInt();
                        System.out.println("Enter Second:");
                        int second = userInp.nextInt();
                        java.sql.Timestamp bts = new Timestamp( year, month, day, hour, minute, second,0);
                        tmpQuery = tmpQuery + bts + ", ";
                        System.out.println("Please enter the departure time (TIMESTAMP: mm.dd.yyyy:hh:mm:ss): ");
                        System.out.println("Enter month:");
                        month = userInp.nextInt();
                        System.out.println("Enter day:");
                        day = userInp.nextInt();
                        System.out.println("Enter year:");
                        year = userInp.nextInt();
                        System.out.println("Enter Hour:");
                        hour = userInp.nextInt();
                        System.out.println("Enter Minute:");
                        minute = userInp.nextInt();
                        System.out.println("Enter Second:");
                        second = userInp.nextInt();
                        bts = new Timestamp( year, month, day, hour, minute, second,0);
                        tmpQuery = tmpQuery + bts + ", ";
                        System.out.println("Please enter the duration of flight (Integer: Minute): ");
                        int duration = userInp.nextInt();
                        tmpQuery = tmpQuery + duration + ", ";
                        System.out.println("Please enter the departure location: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 1) {
                            System.err.println("Invalid input");
                            continue;
                        }
                        tmpQuery = tmpQuery + "\'" + input + "\'" + ", ";
                        System.out.println("Please enter the arrival location: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 1) {
                            System.err.println("Invalid input");
                            continue;
                        }
                        tmpQuery = tmpQuery + "\'" + input + "\'" + ")";
                        break;
                    } else if (input.equals("Passenger")) {
                        System.out.println("Please enter the passenger number: ");
                        input = userInp.nextLine();
                        tmpQuery = "INSERT INTO niklauswetter.Passenger VALUES (" + input + ", ";
                        System.out.println("Is this passenger a Student? (1=Yes) (0=No)");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            switch (tmp) {
                                case 0:
                                    tmpQuery = tmpQuery + "0, ";
                                    break;
                                case 1:
                                    tmpQuery = tmpQuery + "1, ";
                                    break;
                                default:
                                    System.out.println("Invalid input. Please try again.");
                                    continue;
                            }
                        } catch(Exception e) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        }
                        System.out.println("Is this passenger a Frequent Flyer? (1=Yes) (0=No)");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            switch (tmp) {
                                case 0:
                                    tmpQuery = tmpQuery + "0, ";
                                    break;
                                case 1:
                                    tmpQuery = tmpQuery + "1, ";
                                    break;
                                default:
                                    System.out.println("Invalid input. Please try again.");
                                    continue;
                            }
                        } catch(Exception e) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        }
                        System.out.println("Is this passenger part of the military? (1=Yes) (0=No)");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            switch (tmp) {
                                case 0:
                                    tmpQuery = tmpQuery + "0, ";
                                    break;
                                case 1:
                                    tmpQuery = tmpQuery + "1, ";
                                    break;
                                default:
                                    System.out.println("Invalid input. Please try again.");
                                    continue;
                            }
                        } catch(Exception e) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        }
                        System.out.println("Please enter the passenger's first name: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 2) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        } 
                        tmpQuery = tmpQuery + "\'" + input + "\'" + ", ";
                        System.out.println("Please enter the passenger's last name: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 2) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        } 
                        tmpQuery = tmpQuery + "\'" + input + "\'" + ")";
                        break;
                    } else if (input.equals("Staff")) {
                        System.out.println("Please enter the staff number: ");
                        input = userInp.nextLine();
                        tmpQuery = "INSERT INTO niklauswetter.Staff VALUES (" + input + ", ";
                        System.out.println("Please enter the number of staff member's crew number: ");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            tmpQuery = tmpQuery + tmp + ", ";
                        } catch(Exception e) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        }
                        System.out.println("Please enter the staff members job title (1: Pilot, 2:CoPilot, 3:Cabin, 4:Grounds): ");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            switch (tmp) {
                                case 1:
                                    tmpQuery = tmpQuery + "'Pilot', ";
                                    break;
                                case 2:
                                    tmpQuery = tmpQuery + "'CoPilot', ";
                                    break;
                                case 3:
                                    tmpQuery = tmpQuery + "'Cabin Crew', ";
                                    break;
                                case 4:
                                    tmpQuery = tmpQuery + "'Grounds Crew', ";
                                    break;
                                default:
                                    System.out.println("Invalid input. Please try again.");
                                    continue;
                            }
                        } catch(Exception e) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        }
                        System.out.println("Please enter the staff member's first name: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 2) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        }
                        tmpQuery = tmpQuery + "\'" + input + "\'" + ", ";
                        System.out.println("Please enter the staff member's last name: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 2) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        }
                        tmpQuery = tmpQuery + "\'" + input + "\'" + ")";
                        break;
                    }
                }
                // We now have a created insert statement, we must now execute it
                try {
                    stmt = dbconn.createStatement();
                    System.out.println("Executing Query: " + tmpQuery);
                    stmt.executeQuery(tmpQuery);
                    System.out.println("Query executed successfully.");
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error inserting into database...");
                }
                continue;
            } 
            
            
            else if (input.equals("Delete")) {
                while (true) {
                    System.out.println("Would you like to delete from Flight, Passenger, or Staff?");
                    input = userInp.nextLine();
                    if (input.equals("Flight")) {
                        System.out.println("Please enter the flight number (Integer): ");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            tmpQuery = "DELETE FROM niklauswetter.Flight WHERE fNo = " + tmp + "";
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter an integer.");
                            continue;
                        }
                    } else if (input.equals("Passenger")) {
                        System.out.println("Please enter the passenger number (Integer): ");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            tmpQuery = "DELETE FROM niklauswetter.Passenger WHERE pNo = " + tmp + "";
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter an integer.");
                            continue;
                        }
                    } else if (input.equals("Staff")) {
                        System.out.println("Please enter the staff number (Integer): ");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            tmpQuery = "DELETE FROM niklauswetter.Staff WHERE sNo = " + tmp + "";
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter an integer.");
                            continue;
                        }
                    } else {
                        System.out.println("Please enter a valid option.");
                        continue;
                    }
                }
                // We now have a created delete statement, we must now execute it
                System.out.println("Executing Query: " + tmpQuery);
                try {
                    stmt = dbconn.createStatement();
                    stmt.executeQuery(tmpQuery);
                    System.out.println("Query executed successfully.");
                    stmt.close();
                } catch (Exception e) {
                    System.out.println("error deleting from database...");
                }
                continue;
            } 
            
            
            else if (input.equals("Update")) {
                while (true) {
                    System.out.println("Would you like to update Flight, Passenger, or Staff?");
                    input = userInp.nextLine();
                    if (input.equals("Flight")) {
                        System.out.println("Please enter the flight number (Integer): ");
                        String fNum = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(fNum);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter an integer flight number.");
                            continue;
                        }
                        System.out.println("Please enter the crew number: ");
                        input = userInp.nextLine();
                        tmpQuery = "UPDATE niklauswetter.Flight SET cNo = " + input + ", ";
                        System.out.println("Please enter the flight's airline: ");
                        input = userInp.nextLine();
                        tmpQuery = tmpQuery + "airline = " + input + ", ";
                        System.out.println("Please enter the flight's origin: ");
                    } else if (input.equals("Staff")) {
                        System.out.println("Please enter a valid staff number (integer): ");
                        String sNo = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(sNo);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter an integer staff number.");
                            continue;
                        }
                        System.out.println("Please enter the updated staff member's crew number: ");
                        input = userInp.nextLine();
                        tmpQuery = "UPDATE niklauswetter.Staff SET cNo = " + input + ", ";
                        System.out.println("Please enter the updated staff members's job title (1: Pilot, 2:CoPilot, 3:Cabin, 4:Grounds): ");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            switch (tmp) {
                                case 1:
                                    tmpQuery = tmpQuery + "jobTitle = 'Pilot', ";
                                    break;
                                case 2:
                                    tmpQuery = tmpQuery + "jobTitle = 'CoPilot', ";
                                    break;
                                case 3:
                                    tmpQuery = tmpQuery + "jobTitle = 'Cabin Crew', ";
                                    break;
                                case 4:
                                    tmpQuery = tmpQuery + "jobTitle = 'Grounds Crew', ";
                                    break;
                                default:
                                    System.out.println("Please enter a valid job title.");
                                    continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid job title.");
                            continue;
                        }
                        System.out.println("Please enter the updated staff member's first name: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 2) {
                            System.out.println("Please enter a name not a statement.");
                            continue;
                        }
                        tmpQuery = tmpQuery + "firstName = " + "\'" + input + "\'" +", ";
                        System.out.println("Please enter the updated staff member's last name: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 2) {
                            System.out.println("Please enter a name not a statement.");
                            continue;
                        }
                        tmpQuery = tmpQuery + "lastName = " + "\'" + input + "\'" + " WHERE sNo = " + sNo + "";
                        break;
                    } else if (input.equals("Passenger")) {
                        System.out.println("Please enter a valid passenger number (integer): ");
                        String pNo = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(pNo);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter an integer passenger number.");
                            continue;
                        }
                        System.out.println("Please enter the updated passenger's student Status (1:Yes, 0:No): ");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            switch (tmp) {
                                case 1:
                                    tmpQuery = tmpQuery + "isStudent = '1', ";
                                    break;
                                case 0:
                                    tmpQuery = tmpQuery + "isStudent = '0', ";
                                    break;
                                default:
                                    System.out.println("Please enter a valid student status.");
                                    continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid student status.");
                            continue;
                        }
                        System.out.println("Please enter the updated passenger's Frequent Flyer Status (1:Yes, 0:No): ");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            switch (tmp) {
                                case 1:
                                    tmpQuery = tmpQuery + "isFreqFlyer = '1', ";
                                    break;
                                case 0:
                                    tmpQuery = tmpQuery + "isFreqFlyer = '0', ";
                                    break;
                                default:
                                    System.out.println("Please enter a valid student status.");
                                    continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid student status.");
                            continue;
                        }
                        System.out.println("Please enter the updated passenger's Military Status (1:Yes, 0:No): ");
                        input = userInp.nextLine();
                        try {
                            int tmp = Integer.parseInt(input);
                            switch (tmp) {
                                case 1:
                                    tmpQuery = tmpQuery + "isMilitary = '1', ";
                                    break;
                                case 0:
                                    tmpQuery = tmpQuery + "isMilitary = '0', ";
                                    break;
                                default:
                                    System.out.println("Please enter a valid student status.");
                                    continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid student status.");
                            continue;
                        }
                        System.out.println("Please enter the updated passenger's first name: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 2) {
                            System.out.println("Please enter a name not a statement.");
                            continue;
                        }
                        tmpQuery = tmpQuery + "firstName = " + "\'" + input + "\'" + ", ";
                        System.out.println("Please enter the updated passenger's last name: ");
                        input = userInp.nextLine();
                        if (input.split(" ").length > 2) {
                            System.out.println("Please enter a name not a statement.");
                            continue;
                        }
                        tmpQuery = tmpQuery + "lastName = " + "\'" + input + "\'" + " WHERE pNo = " + pNo + "";
                        break;
                    }
                }
                // We now have a created update statement, we must now execute it
                System.out.println("Executing Query: " + tmpQuery);
                try {
                    stmt = dbconn.createStatement();
                    stmt.executeUpdate(tmpQuery);
                    System.out.println("Query executed successfully.");
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Error executing query.");
                }
                continue;
            } 
            
            
            else if (input.equals("Query")) {
                while (true) {
                    tmpQuery = "";
                    System.out.println("Choose a query (1-4): ");
                    input = userInp.nextLine();
                    try {
                        int tmp = Integer.parseInt(input);
                        switch (tmp) {
                            case 1:
                                tmpQuery = Query1;
                                break;
                            case 2:
                                tmpQuery = Query2;
                                System.out.println("Please enter a valid airline ('Delta', 'Alaska', 'American', 'Southwest'): ");
                                input = userInp.nextLine();
                                if (input.split(" ").length > 1) {
                                    System.out.println("Please enter an airline not a statement.");
                                    continue;
                                }
                                tmpQuery = tmpQuery + "WHERE airline = \'" + input + "\'';";
                                try {
                                    stmt = dbconn.createStatement();
                                    ResultSet rs = stmt.executeQuery(tmpQuery);
                                    if (rs != null) {
                                        System.out.println("Query executed successfully.");
                                        ResultSetMetaData rsmd = rs.getMetaData();
                                        String[] colNames = new String[rsmd.getColumnCount()];
                                        int nameIndex = 0;
                                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                                            if (i == 3) {
                                                System.out.print("Bag Count" + "\t");
                                                colNames[nameIndex] = rsmd.getColumnName(i);
                                            } else {
                                                System.out.print(rsmd.getColumnName(i) + "\t");
                                                colNames[nameIndex] = rsmd.getColumnName(i);
                                            }
                                        }

                                        System.out.println();

                                        while (rs.next()) {
                                            int swt = rs.getInt(colNames[2]);
                                            switch (swt) {
                                                case 1:
                                                    System.out.println(rs.getByte(colNames[0]) + "\t" + rs.getString(colNames[1]) + "3");
                                                    break;
                                                case 0:
                                                    System.out.println(rs.getByte(colNames[0]) + "\t" + rs.getString(colNames[1]) + "2");
                                                    break;
                                                default:
                                                    System.out.print("Error\t");
                                                    break;
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error Evaluating Results");
                                }
                                break;
                            case 3:

                        }
                        
                        break;
                    } catch (Exception e) {
                        System.out.println("Please enter a valid query number.");
                        continue;
                    }
                }
            } 
            
            
            else if (input.equals("Quit")) {
                break;
            } 
            
            
            else {
                System.out.println("Invalid input. Please try again.");
                continue;
            }
        }
        userInp.close();
        try {
            dbconn.close();
        } catch (Exception e) {
            System.out.println("Error closing connection.");
            System.exit(-1);
        }
    }
}