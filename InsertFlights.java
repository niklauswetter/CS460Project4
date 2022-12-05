import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class InsertFlights {

    static final String DB_URL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
    static final String USER = "niklauswetter";
    static final String PASS = "a0513";

    public static void main(String[] args){
        String flightPath = args[0];

        //Files are loaded and paths are correct

        File flightFile = new File(flightPath);
        int batchSize = 20;
        Connection connection = null;

        try{
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO niklauswetter.Flight " +
                    "(fNo, cNo, airline, bGate, bTime, dTime, duration, dLoc, aLoc) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(flightFile));
            String lineText = null;
            int count = 0;
            lineReader.readLine(); //skip header

            while((lineText = lineReader.readLine()) != null){
                String[] data = lineText.split(",");

                String dt = data[4].substring(0,10);
                String[] dtt = dt.split("\\.");
                String b = data[4].substring(11,data[4].length());
                String[] bs = b.split(":");
                String d = data[5].substring(11,data[5].length());
                String[] ds = d.split(":");

                int month = Integer.parseInt(dtt[0]) - 1;
                int day = Integer.parseInt(dtt[1]);
                int year = Integer.parseInt(dtt[2])-1900;

                java.sql.Date date = new java.sql.Date(year, month, day);
                java.sql.Time bTime = new java.sql.Time(Integer.parseInt(bs[0]),Integer.parseInt(bs[1]),Integer.parseInt(bs[2]));
                java.sql.Time dTime = new java.sql.Time(Integer.parseInt(ds[0]),Integer.parseInt(ds[1]),Integer.parseInt(ds[2]));

                java.sql.Timestamp bts = new Timestamp(year, month, day, Integer.parseInt(bs[0]),Integer.parseInt(bs[1]),Integer.parseInt(bs[2]),0);
                java.sql.Timestamp dts = new Timestamp(year, month, day, Integer.parseInt(ds[0]),Integer.parseInt(ds[1]),Integer.parseInt(ds[2]),0);

                //System.out.println(date.toString());
                //System.out.println(bTime.toString());
                //System.out.println(dTime.toString());

                statement.setInt(1, Integer.parseInt(data[0]));
                statement.setInt(2, Integer.parseInt(data[1]));
                statement.setString(3, data[2]);
                statement.setString(4, data[3]);
                statement.setTimestamp(5, bts);
                statement.setTimestamp(6, dts);
                statement.setInt(7, Integer.parseInt(data[6]));
                statement.setString(8, data[7]);
                statement.setString(9, data[8]);

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