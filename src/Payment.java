/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KIKI
 */
public class Payment extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private BankDatabase bankDatabase;
   Screen screen;
   
   // constant corresponding to menu option to cancel
   private static final int TOPUP_GOPAY = 1;
   private static final int TOPUPDANA = 2;
   private final static int CANCELED = 3;

    public Payment(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        bankDatabase = atmBankDatabase;
    }
       
    @Override
    public void execute() {
        int userChoice = displayMenuOfPurchase();
        
        switch (userChoice) {
            case TOPUP_GOPAY:
                TransferDestination  transferDest = promptForTopUpGoPayTransaction();
        
                if(transferDest != null) {
                     bankDatabase.credit(getAccountNumber(), transferDest.getAmount());
                     
                     screen.displayDollarAmount(transferDest.getUserAccount().getAvailableBalance());
                     bankDatabase.debitTransfer(transferDest.getUserAccount().getAccountNumber(), transferDest.getAmount());
                     
                     screen.displayMessage("Transaction Success.\n\nYour current GO-PAY balance is: ");
                     screen.displayDollarAmount(transferDest.getUserAccount().getAvailableBalance());
                } else {
                    screen.displayMessageLine("Canceling transaction...");
                }
                break;
            case TOPUPDANA:
                TransferDestination  PayDest = promptForTopUpGoPayTransaction();
        
                if(PayDest != null) {
                     bankDatabase.credit(getAccountNumber(), PayDest.getAmount());
                     
                     screen.displayDollarAmount(PayDest.getUserAccount().getAvailableBalance());
                     bankDatabase.debitTransfer(PayDest.getUserAccount().getAccountNumber(), PayDest.getAmount());
                     
                     screen.displayMessage("Transaction Success.\n\nYour current DANA balance is: ");
                     screen.displayDollarAmount(PayDest.getUserAccount().getAvailableBalance());
                } else {
                    screen.displayMessageLine("Canceling transaction...");
                }
                break;
            case CANCELED:
                break;
            default :
                screen.displayMessageLine("Canceling transaction...");
         } 
    }
    
    // prompt user to enter a transfer amount in cents 
    private TransferDestination promptForTopUpGoPayTransaction() {
       boolean valid = false; 
       
       TransferDestination transferDest = new TransferDestination();
       
       screen = getScreen(); // get reference to screen
       int destinationAccountNumber, input;
//       long amount=0;
       int amount =0;
       
       while (!valid) {
            // display the prompt
            screen.displayMessage("\nPlease enter phone number: ");
            destinationAccountNumber = keypad.getInput(); // receive input of account number

            screen.displayMessage("\nPlease enter a top-up amount in " + 
           "CENTS (or 0 to cancel): ");
//            amount = keypad.getInputLong();
            amount = keypad.getInput();

            transferDest.setAccount(destinationAccountNumber);
            transferDest.setAmount(amount/100); // in dolar

            if(transferDest.getUserAccount() == null) {
                screen.displayMessageLine("Invalid phone number ");
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

        if (input == 1) {
            return transferDest;
        }
        return null;
    }
 
    private int displayMenuOfPurchase() {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      
      // array of amounts to correspond to menu numbers
      int[] menus = {0, TOPUP_GOPAY, TOPUPDANA};
              
      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the withdrawal menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("1 - TOP UP Go-Pay");
         screen.displayMessageLine("2 = Top-Up DANA");
         screen.displayMessageLine("3 - Cancel transaction");
         screen.displayMessage("\nChoose a purchase menu: ");

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch (input) {
            case TOPUP_GOPAY: // if the user chose a withdrawal amount 
                    // (i.e., chose option 1 or 2), return the
                    // corresponding menu from menus array
               userChoice = menus[input]; // save user's choice
               break; 
            case TOPUPDANA:
               userChoice = menus[input];
               break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine(
                  "\nInvalid selection. Try again.");
         } 
      } 

      return userChoice; // return withdrawal amount or CANCELED
   }
}
