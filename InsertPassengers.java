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

public class InsertPassengers {

    static final String DB_URL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
    static final String USER = "niklauswetter";
    static final String PASS = "a0513";

    public static void main(String[] args){
        String passengerPath = args[0];

        //Files are loaded and paths are correct
        File ticketFile = new File(passengerPath);
        int batchSize = 20;
        Connection connection = null;

        try{
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO niklauswetter.Passenger " +
                    "(pNo, isStudent, isFreqFlyer, isMilitary, fName, lName) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(ticketFile));
            String lineText = null;
            int count = 0;
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