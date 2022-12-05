import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TicketGenerator {

    public static void main(String[] args){
        ArrayList<Integer> passengers = new ArrayList<Integer>();

        for(int i = 1; i < 151; i++){
            passengers.add(i);
        }


        try{
            File tickets = new File("tickets.csv");
            FileWriter writer = new FileWriter(tickets);
            Random random = new Random();
            writer.write("pNo,fNo\n");
            for(int i = 1; i < 61; i++){
                String s = "";
                String t = "";
                if(i < 10){
                    s = "00"+Integer.toString(i);
                }else{
                    s = "0"+Integer.toString(i);
                }
                int load = random.nextInt(26)+35;
                Collections.shuffle(passengers);
                for(int j = 0; j < load; j++){
                    t="";
                    int k = passengers.get(j);
                    if(k < 10){
                        t = "00"+Integer.toString(k);
                    }else if(k < 100) {
                        t = "0" + Integer.toString(k);
                    }else{
                        t = Integer.toString(k);
                    }
                    writer.write(t+","+s+"\n");
                }
            }
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}