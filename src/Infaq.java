public class Infaq extends Transaction{
    private double              amount;                                         // amount to deposit
    private Keypad              keypad;                                         // reference to keypad
    private final static int    CANCELED = 0;
    
    public Infaq(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad){
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad      = atmKeypad;
    }
    
    @Override
    public void execute() {
        Screen screen   = getScreen();
        int accountNumber = 1999;
        BankDatabase Reciever = getBankDatabase();
        amount = promptForInfaqAmount();
        if(amount <= super.getBankDatabase().getTotalBalance(super.getAccountNumber())){
            if(amount!=0){
                Reciever.debit(accountNumber, amount);
                super.getBankDatabase().credit(super.getAccountNumber(), amount);
                screen.displayMessage("Please insert a transfer envelope containing ");
                screen.displayDollarAmount(amount);
//                super.getBankDatabase().setLastTransfer(super.getAccountNumber(), amount);
//                super.getBankDatabase().setLastTransferAccount(super.getAccountNumber(), accountNumber);
                screen.displayMessageLine("\nInfaq Success");
            }else{
                screen.displayMessageLine("\nCanceling transaction...");
            }
        }else{
            screen.displayMessageLine("\nNot enough Balance to Infaq.\nCanceling transaction...");
        }
    }
    
    // prompt user to enter a deposit amount in cents 
    private double promptForInfaqAmount(){
        Screen screen = getScreen();                                            // get reference to screen
        // display the prompt
        screen.displayMessage("Please enter an infaq amount in "
            + "CENTS (or 0 to cancel): ");
        int input = keypad.getInput();

        // check whether the user canceled or entered a valid amount
        if (input == CANCELED) {
            return CANCELED;
        }else {
            return (double) input / 100;                                        // return dollar amount
        }
    }
}