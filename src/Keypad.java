import java.util.Scanner;
import java.util.*;
import java.util.InputMismatchException;

public class Keypad{
    private Scanner input; // reads data from the command line

    public Keypad() {
        input = new Scanner(System.in);    
    } 

    public int getInput() {
        try{
            return input.nextInt();
        } // user enters an integer
        catch(InputMismatchException ex){
            System.out.println("Input Mismatch Exception..");
            input.nextLine();     // clean buffer
            return 0;
        }
    }

    //*keys
    public long getInputLong() {
        try{
            return input.nextLong();
        } // user enters an long integer
        catch(InputMismatchException ex){
            System.out.println("Input Mismatch Exception..");
            input.nextLine();     // clean buffer
            return 0;
        }
    }

    public String getInputString(){
        return input.next(); // user enters an String
    }
} 