import java.io.File;
import java.sql.Connection;
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
    }
}