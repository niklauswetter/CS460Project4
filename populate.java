import java.io.*;
import java.util.*;
import java.sql.*; 

/**
 * This class will populate the database with the sample data
 * This class will execute one long squence of SQL statements to populate the database with correct data
 * The order in which the data is created is as follows...
 * Create Crew Member -> Create Flights ->  Create Passengers -> 
 * Generate Tickets (using numbers from Flights and Passengers) 
 * 
 */
public class populate {

    private final String[] tables = {"Flight", "Passenger", "Staff", "Ticket", "Crew"};
    private final String[] names = {};

    public static void main() {
        final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle"; // Magic lectura -> aloe access spell
        String username = "";
        String password = "";

        String[] fNames = {"Mark", "Michael", "John", "James", "Robert", "David", "William", "Cindy", 
                           "Lucy", "Katherine", "Mary", "Jennifer", "Lisa", "Sarah", "Jessica", "Ashley", 
                           "Elizabeth", "Melissa", "Samantha", "Stephanie", "Michelle", "Kimberly", "Angela", 
                           "Emily", "Laura", "Rebecca", "Amanda", "Amy", "Anna", "Brianna", "Brittany", "Christina"};

        String[] lNames = {"Martin", "Crawford", "Harris", "Hernandez", "Gonzalez", "Robinson", "Perez", 
                           "Jackson", "Campbell", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", 
                           "Allen", "Driscoll", "Smith", "Whitlock", "Madden", "Baker", "Gibson", "Mills", 
                           "Diaz", "Wood", "Wright", "Rivera", "King", "Scott", "Green", "Bryant", "Adams", 
                           "Bennett", "Carter", "Collins"};

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

        Connection dbconn = null;

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

        Statement stmt = null;

        ArrayList<Object> records = new ArrayList<Object>();

        // For 4 Crews per Airline, create StaffRecords for every crew member
        int staffNo = 0;
        int insertIndex = 0;
        for (int cNo = 0; cNo<16; cNo++) {
            Random rand = new Random();
            records.add(insertIndex, new StaffRecord(staffNo, cNo, "Pilot", fNames[rand.nextInt(fNames.length)], lNames[rand.nextInt(lNames.length)]));
            insertIndex++;
            staffNo++;
            records.add(insertIndex, new StaffRecord(staffNo, cNo, "Co-Pilot", fNames[rand.nextInt(fNames.length)], lNames[rand.nextInt(lNames.length)]));
            insertIndex++;
            staffNo++;
            for (int i = 0; i<4; i++) {
                records.add(insertIndex, new StaffRecord(staffNo, cNo,"Cabin Crew", fNames[rand.nextInt(fNames.length)], lNames[rand.nextInt(lNames.length)]));
                insertIndex++;
                staffNo++;
            }
            for (int i = 0; i<4; i++) {
                records.add(insertIndex, new StaffRecord(staffNo, cNo,"Grounds Crew", fNames[rand.nextInt(fNames.length)], lNames[rand.nextInt(lNames.length)]));
                insertIndex++;
                staffNo++;
            }

        }

    }

    public class DataRecord {

        public class FlightRecord extends DataRecord {
            public int flightNo;
            public int crewNo;
            public String airline;
            public String bGate;
            public String bTime;
            public String dTime;
            public String duration;
            public String dLoc;
            public String aLoc;
    
            public FlightRecord(int fNo, int cNo, String a, String bG, String bT, String dT, String d, String dL, String aL) {
                flightNo = fNo;
                crewNo = cNo;
                airline = a;
                bGate = bG;
                bTime = bT;
                dTime = dT;
                duration = d;
                dLoc = dL;
                aLoc = aL;
            }
    
            public String toInsert() {
                return "INSERT INTO ccnw_Flight VALUES (" + flightNo + ", " + crewNo + ", " + airline + ", " + bGate + ", " + bTime + ", " + dTime + ", " + duration + ", " + dLoc + ", " + aLoc + ")";
            }
        }
    
        public class PassengerRecord extends DataRecord {
            public int passengerNo;
            public boolean isStudent;
            public boolean isFreqFlyer;
            public boolean isMilitary;
            public int binHistory;
            public String fName;
            public String lName;
    
            public PassengerRecord(int pNo, boolean s, boolean f, boolean m, int b, String fN, String lN) {
                passengerNo = pNo;
                isStudent = s;
                isFreqFlyer = f;
                isMilitary = m;
                binHistory = b;
                fName = fN;
                lName = lN;
            }
    
            public String toInsert() {
                return "INSERT INTO ccnw_Passenger VALUES (" + passengerNo + ", " + isStudent + ", " + isFreqFlyer + ", " + isMilitary + ", " + binHistory + ", " + fName + ", " + lName + ")";
            }
        }
    
        public class StaffRecord extends DataRecord {
            public int staffNo;
            public int crewNo;
            public String jobTitle;
            public String fName;
            public String lName;
    
            public StaffRecord(int sNo, int cNo, String jT, String fN, String lN) {
                staffNo = sNo;
                crewNo = cNo;
                jobTitle = jT;
                fName = fN;
                lName = lN;
            }
    
            public String toInsert() {
                return "INSERT INTO ccnw_Staff VALUES (" + staffNo + ", " + crewNo + ", " + jobTitle + ", " + fName + ", " + lName + ")";
            }
        }
        
        public class TicketRecord extends DataRecord {
            public int pNo;
            public int fNo;
    
            public TicketRecord(int p, int f) {
                pNo = p;
                fNo = f;
            }
    
            public String toInsert() {
                return "INSERT INTO ccnw_Ticket VALUES (" + pNo + ", " + fNo + ")";
            }
    
        }
    }
}
