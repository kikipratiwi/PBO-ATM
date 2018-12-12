
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KIKI
 */
public class Purchase extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private BankDatabase bankDatabase;
   Screen screen;
   
   // constant corresponding to menu option to cancel
   private static final int PLNPREPAID = 1;
   private final static int CANCELED = 2;

    public Purchase(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        bankDatabase = atmBankDatabase;
    }
       
    @Override
    public void execute() {
        int userChoice = displayMenuOfPurchase();
        
        switch (userChoice) {
            case PLNPREPAID:
                TransferDestination  transferDest = promptForPlnPrepaidTransaction();
        
                if(transferDest != null) {
                     bankDatabase.credit(getAccountNumber(), transferDest.getAmount());
                     bankDatabase.debitTransfer(transferDest.getUserAccount().getAccountNumber(), transferDest.getAmount());
                     
                     screen.displayMessage("Transaction Success.\n\nYour token is: ");
                     getToken();
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
    
    private void getToken() {
       Random rnd = new Random();
       int token;
       
        for (int i = 1; i <= 5; i++) {
            token = 1000 + rnd.nextInt(9000);
            screen.displayMessage(token + " ");
        }
        screen.displayMessageLine("");
    }
    
    // prompt user to enter a transfer amount in cents 
    private TransferDestination promptForPlnPrepaidTransaction() {
       boolean valid = false; 
       
       TransferDestination transferDest = new TransferDestination();
       
       screen = getScreen(); // get reference to screen
       int destinationAccountNumber, input;
       int userVoucherChoice=0;
       
       while (!valid) {
            // display the prompt
            screen.displayMessage("\nPlease enter user id: ");
            destinationAccountNumber = keypad.getInput(); // receive input of account number

            // display the prompt
            // array of amounts to correspond to menu numbers
            int[] amounts = {0, 2, 5, 10, 20, 50, 100, 500};

            // loop while no valid choice has been made
            while (userVoucherChoice == 0) {
               // display the withdrawal menu
               screen.displayMessageLine("\nVoucher Amount Menu:");
               screen.displayMessageLine("1 - $2");
               screen.displayMessageLine("2 - $5");
               screen.displayMessageLine("3 - $10");
               screen.displayMessageLine("4 - $20");
               screen.displayMessageLine("5 - $50");
               screen.displayMessageLine("6 - $100");
               screen.displayMessageLine("7 - $500");
               screen.displayMessage("\nChoose a voucher amount: ");

               input = keypad.getInput(); // get user input through keypad

               // determine how to proceed based on the input value
               switch (input) {
                  case 1: // if the user chose a withdrawal amount 
                  case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
                  case 3: // corresponding amount from amounts array
                  case 4:
                  case 5:
                  case 6:
                  case 7:
                     userVoucherChoice = amounts[input]; // save user's choice
                     break;
                  default: // the user did not enter a value from 1-6
                     screen.displayMessageLine(
                        "\nInvalid selection. Try again.");
               }
            }

            transferDest.setAccount(destinationAccountNumber);
            transferDest.setAmount(userVoucherChoice); // in dolar

            if(transferDest.getUserAccount() == null) {
                screen.displayMessageLine("Invalid user id or metered number");
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
      int[] menus = {0, PLNPREPAID};
              
      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the withdrawal menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("1 - PLN Prepaid");
         screen.displayMessageLine("2 - Cancel transaction");
         screen.displayMessage("\nChoose a purchase menu: ");

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch (input) {
            case PLNPREPAID: // if the user chose a withdrawal amount 
                    // (i.e., chose option 1 or 2), return the
                    // corresponding menu from menus array
               userChoice = menus[input]; // save user's choice
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
