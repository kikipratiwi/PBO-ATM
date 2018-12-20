
import java.io.*;
import java.time.*;
import java.util.Date;

/**
 *
 * @author KIKI
 */
public class Transfer extends Transaction {
    private double amount; // amount to withdraw
    private Keypad keypad; // reference to keypad
    private BankDatabase bankDatabase;
    private int destinationAccountNumber;
    Screen screen;
    private History history = new History();
    // constant corresponding to menu option to cancel
    private final static int CANCELED = 0;
    private Date date = new Date();

    public Transfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        bankDatabase = atmBankDatabase;
    }

    @Override
    public void execute() {
       screen = getScreen();
       String for_Struct;
       
       TransferDestination  transferDest = promptForDetailTransaction();
        
       if(transferDest != null) {
            bankDatabase.credit(getAccountNumber(), transferDest.getAmount());
            bankDatabase.debitTransfer(transferDest.getUserAccount().getAccountNumber(), transferDest.getAmount());
            
            screen.displayMessage("Do you want to print your transaction?\nY/N : ");
            String inputString = keypad.getInputString();

            if(inputString.equals("Y") || inputString.equals("y")){
                for_Struct = Record_StructTransfer(destinationAccountNumber,amount);
                String Filename = "Transfer.txt";
                Print_StructTransfer(Filename,for_Struct);
            }

            screen.displayMessageLine("Transaction success.");
            
            history.saveToFile(3, getAccountNumber(), amount);
            
        } else {
            screen.displayMessageLine("Canceling transaction...");
        }
    }
    
    // prompt user to enter a transfer amount in cents 
    private TransferDestination promptForDetailTransaction() {
        boolean valid = false; 

        TransferDestination transferDest = new TransferDestination();

        screen = getScreen(); // get reference to screen
        int input;
        
        while (!valid) {
            // display the prompt
            screen.displayMessage("\nPlease enter destination account number : ");
            destinationAccountNumber = keypad.getInput(); // receive input of account number

            // display the prompt
            screen.displayMessage("\nPlease enter a transfer amount in " + 
               "CENTS : ");
            amount = keypad.getInput(); // receive input of deposit amount
            
            
            transferDest.setAccount(destinationAccountNumber);
            transferDest.setAmount(amount / 100); // in dolar

            if(transferDest.getUserAccount() == null) {
                screen.displayMessageLine("Invalid account number destination.");
                continue;
            }
            
            if (amount <= 0) {
                screen.displayMessageLine("Can not Transfer 0 or lower than 0");
                continue;
            }
            
            if (bankDatabase.getAvailableBalance(getAccountNumber()) < transferDest.getAmount()) {
                screen.displayMessageLine("It's not enough balance");
                continue;
            }
            
            valid = true;
        }
       
        screen.displayMessage("\nAre you sure for this transaction (1 to yes or 0 to cancel) : ");
        input = keypad.getInput(); // receive input of deposit amount

        if (input == CANCELED) {
            return null;
        } else if (input == 1) {
            return transferDest;
        }
            
        screen.displayMessageLine(
           "\nInvalid selection. Try again.");
        return null;
    }
    
    private String Record_StructTransfer(int tujuan, double amount){
        Screen screen = getScreen();
        LocalDate ldate = LocalDate.from(date.toInstant().atZone(ZoneOffset.UTC));
        
        String stringForFile = "==================================================";
        stringForFile += "\r\n\t\t  Keys Team Bank";
        stringForFile += "\r\n\tDate\t\t\t: " + ldate;
        stringForFile += "\r\n\tYour Account Number\t: ";
        stringForFile += "" + super.getAccountNumber();
        stringForFile += "\r\n==================================================";
        stringForFile += "\r\n\r\n\tYour Transfer To\t: ";
        if(tujuan == 1999){
            stringForFile += "Kotak Infaq";
        }else stringForFile += "" + tujuan;
        stringForFile += "\r\n\tWith Amount\t\t: $"
                + amount/100;
        stringForFile += "\r\n\t\t     SUCCESS";
        stringForFile += "\r\n\r\n==================================================";
        
        return stringForFile;
    }
    
    public void Print_StructTransfer(String Filename, String TransferRecord){
        Screen screen = getScreen();
        try {
            File structTransfer = new File(Filename);
            FileWriter printer  = new FileWriter(structTransfer, false);

            printer.write(TransferRecord);
            printer.flush();
            printer.close();
            screen.displayMessageLine("Take the struct. Thanks.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
