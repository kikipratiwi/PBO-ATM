
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Annisa Fathana
 */
public class BayarWifi extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private BankDatabase bankDatabase;
   Screen screen;
   
   // constant corresponding to menu option to cancel
   private final static int CANCELED = 0;

    public BayarWifi(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        bankDatabase = atmBankDatabase;
    }

    @Override
    public void execute() {
       screen = getScreen();
        
       TransferDestination  transferDest = promptForDetailTransaction();
        
       if(transferDest != null) {
            bankDatabase.credit(getAccountNumber(), transferDest.getAmount());
            bankDatabase.debitTransfer(transferDest.getUserAccount().getAccountNumber(), transferDest.getAmount());

            screen.displayMessageLine("Transaction success. The bill has been paid.");
       } else {
           screen.displayMessageLine("Canceling transaction...");
       }
    }
    
    // prompt user to enter a transfer amount in cents 
    private TransferDestination promptForDetailTransaction() {
       boolean valid = false; 
       boolean enoughBalance = false;
       
       TransferDestination transferDest = new TransferDestination();
       
       screen = getScreen(); // get reference to screen
       int destinationAccountNumber, input;
       long _amount = 0;
       
       while (!valid) {
            // display the prompt
            screen.displayMessage("\nPlease enter your ID Number : ");
            destinationAccountNumber = keypad.getInput(); // receive input of account number

            transferDest.setAccount(destinationAccountNumber);

            valid = isValidTransaction(transferDest);
            
            if(valid){
                // display the prompt
                screen.displayMessageLine("\nYou have to pay 50000 cents");
               _amount = 50000; // receive input of deposit amount
                transferDest.setAmount((int) (_amount / 100)); // in dolar
                enoughBalance = isEnoughBalance(transferDest);
                if(!enoughBalance){
                    valid = true;
                }
            }
        }
        
        if(!enoughBalance){
            return null;
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
    
    private boolean isValidTransaction(TransferDestination transferDest){
        if(transferDest.getUserAccount() == null) {
            screen.displayMessageLine("ID Number is not recognized.");
            return false;
        }
        return true;
    }
    
    private boolean isEnoughBalance(TransferDestination transferDest){
        if (bankDatabase.getAvailableBalance(getAccountNumber()) < transferDest.getAmount()) {
            screen.displayMessageLine("It's not enough balance");
            return false;
        }
        return true;
    }

}
