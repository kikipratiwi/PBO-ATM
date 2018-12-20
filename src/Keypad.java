import java.util.Scanner;
import java.util.*;

public class Keypad{
    private Scanner input; // reads data from the command line

    public Keypad() {
        input = new Scanner(System.in);    
    } 

    public int getInput(){
        return input.nextInt(); // user enters an integer
    }

    //*keys
    public long getInputLong(){
        return input.nextLong();
    }

    public String getInputString(){
        return input.next(); // user enters an String
    }
} 