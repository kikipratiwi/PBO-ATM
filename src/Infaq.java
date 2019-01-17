public class Infaq extends Transaction{
    private double              infaq_amount;                                         // amount to deposit
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
        BankDatabase Reciever = getBankDatabase();
        infaq_amount = promptForInfaqAmount();
        if(isValidTransaction(infaq_amount)){
            super.getBankDatabase().credit(super.getAccountNumber(), infaq_amount);
            screen.displayMessage("Please insert a transfer envelope containing ");
            screen.displayDollarAmount(infaq_amount);
            screen.displayMessageLine("\nInfaq Success");
        }else{
            screen.displayMessageLine("\nInfaq Failed");
        }
    }
    
    // prompt user to enter a deposit amount in cents 
    private double promptForInfaqAmount(){
        Screen screen = getScreen();                                            // get reference to screen
        // display the prompt
        screen.displayMessage("Please enter an infaq amount in "
            + "CENTS (or 0 to cancel): ");
        int input = keypad.getInput();
        return input != CANCELED ? (double) input / 100 : CANCELED;
    }
    
    public boolean isValidTransaction(double infaqAmount){
        return infaqAmount <= super.getBankDatabase().getTotalBalance(super.getAccountNumber())
                && infaqAmount > 0;
    }
}