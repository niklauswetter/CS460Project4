import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TableLoader {

    static final String DB_URL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
    static final String USER = "niklauswetter";
    static final String PASS = "a0513";

    public static void main(String[] args){
        if(args.length != 4){
            System.out.println("Provide all four file paths please.");
            System.exit(1);
        }
        String flightPath = args[0];
        String staffPath = args[1];
        String passengerPath = args[2];
        String ticketPath = args[3];
        Scanner keyboard = new Scanner(System.in);
        System.out.printf(
                "Flight path:%s\n" +
                "Staff path:%s\n" +
                "Passenger path:%s\n" +
                "Ticket path:%s\n"
                ,flightPath,staffPath,passengerPath,ticketPath);
        System.out.println("Is this correct? [y/n]");
        String answer = keyboard.nextLine();
        if(!answer.equals("y")){
            System.out.println("Exiting upon user request.");
            System.exit(0);
        }

        //Files are loaded and paths are correct

        File flightFile = new File(flightPath);
        File staffFile = new File(staffPath);
        File passengerFile = new File(passengerPath);
        File ticketFile = new File(ticketPath);
        int batchSize = 20;
        Connection connection = null;

        try{
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO niklauswetter.Flight " +
                    "(fNo, cNo, airline, bGate, bTime, dTime, duration, dLoc, aLoc) " +
                    "VALUES (?, ?, ?, ?, to_date(?, 'MM.DD.YYYY:HH24:MI:SS'), to_date(?, 'MM.DD.YYYY:HH24:MI:SS'), ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(flightFile));
            String lineText = null;
            int count = 0;
            lineReader.readLine(); //skip header

            while((lineText = lineReader.readLine()) != null){
                String[] data = lineText.split(",");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.DD.YYYY:HH24:mm:ss");
                LocalDateTime d1 = LocalDateTime.parse(data[4], formatter);
                LocalDateTime d2 = LocalDateTime.parse(data[5], formatter);

                java.sql.Date sqlD1 = java.sql.Date.valueOf(d1.toLocalDate());
                java.sql.Date sqlD2 = java.sql.Date.valueOf(d2.toLocalDate());

                java.sql.Time sqlT1 = java.sql.Time.valueOf(d1.toLocalTime());
                java.sql.Time sqlT2 = java.sql.Time.valueOf(d2.toLocalTime());

                statement.setInt(1, Integer.parseInt(data[0]));
                statement.setInt(2, Integer.parseInt(data[1]));
                statement.setString(3, data[2]);
                statement.setString(4, data[3]);
                statement.setDate(5, sqlD1);
                statement.setDate(6, sqlD2);
                statement.setInt(7, Integer.parseInt(data[6]));
                statement.setString(8, data[7]);
                statement.setString(9, data[8]);

                count++;
                statement.addBatch();
                if(count % batchSize == 0){
                    statement.executeBatch();
                }
            }

            sql = "INSERT INTO niklauswetter.Staff " +
                    "(sNo, cNo, jobTitle, fName, lName) " +
                    "VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);

            lineReader = new BufferedReader(new FileReader(staffFile));
            lineText = null;
            count = 0;
            lineReader.readLine(); //skip header

            while((lineText = lineReader.readLine()) != null){
                String data[] = lineText.split(",");

                statement.setInt(1, Integer.parseInt(data[0]));
                statement.setInt(2, Integer.parseInt(data[1]));
                statement.setString(3, data[2]);
                statement.setString(4, data[3]);
                statement.setString(5, data[4]);

                count++;
                statement.addBatch();
                if (count % batchSize == 0){
                    statement.executeBatch();
                }
            }

            sql = "INSERT INTO niklauswetter.Passenger " +
                    "(pNo, isStudent, isFreqFlyer, isMilitary, fName, lName) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);

            lineReader = new BufferedReader(new FileReader(passengerFile));
            lineText = null;
            count = 0;
            lineReader.readLine(); //skip header

            while((lineText = lineReader.readLine()) != null){
                String data[] = lineText.split(",");

                statement.setInt(1, Integer.parseInt(data[0]));
                statement.setInt(2, Integer.parseInt(data[1]));
                statement.setInt(3, Integer.parseInt(data[2]));
                statement.setInt(4, Integer.parseInt(data[3]));
                statement.setString(5, data[4]);
                statement.setString(6, data[5]);

                count++;
                statement.addBatch();
                if (count % batchSize == 0){
                    statement.executeBatch();
                }
            }

            sql = "INSERT INTO niklauswetter.Ticket " +
                    "(pNo, fNo) " +
                    "VALUES (?, ?)";
            statement = connection.prepareStatement(sql);

            lineReader = new BufferedReader(new FileReader(ticketFile));
            lineText = null;
            count = 0;
            lineReader.readLine(); //skip header

            while((lineText = lineReader.readLine()) != null){
                String data[] = lineText.split(",");

                statement.setInt(1, Integer.parseInt(data[0]));
                statement.setInt(2, Integer.parseInt(data[1]));

                count++;
                statement.addBatch();
                if(count % batchSize == 0){
                    statement.executeBatch();
                }
            }

            lineReader.close();
            statement.executeBatch();

            connection.commit();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
            try{
                connection.rollback();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}