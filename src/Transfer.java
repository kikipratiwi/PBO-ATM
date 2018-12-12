/**
 *
 * @author KIKI
 */
public class Transfer extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private BankDatabase bankDatabase;
   Screen screen;
    private History history = new History();
   // constant corresponding to menu option to cancel
   private final static int CANCELED = 0;

    public Transfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        
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
            
                     screen.displayDollarAmount(transferDest.getUserAccount().getAvailableBalance());
            bankDatabase.debitTransfer(transferDest.getUserAccount().getAccountNumber(), transferDest.getAmount());

            screen.displayMessageLine("Transaction success.");
            
                     screen.displayDollarAmount(transferDest.getUserAccount().getAvailableBalance());
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
       int destinationAccountNumber, input;
//       long amount;
       int amount;
       while (!valid) {
            // display the prompt
            screen.displayMessage("\nPlease enter destination account number : ");
            destinationAccountNumber = keypad.getInput(); // receive input of account number

            // display the prompt
            screen.displayMessage("\nPlease enter a transfer amount in " + 
               "CENTS : ");
//            amount = keypad.getInputLong(); // receive input of deposit amount
            amount = keypad.getInput(); // receive input of deposit amount

            transferDest.setAccount(destinationAccountNumber);
            transferDest.setAmount(amount / 100); // in dolar

            if(transferDest.getUserAccount() == null) {
                screen.displayMessageLine("Invalid account number destination.");
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
    
}
