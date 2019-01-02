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
        int accountNumber = 1999;
        BankDatabase Reciever = getBankDatabase();
        infaq_amount = promptForInfaqAmount();
        if(isValidTransaction(infaq_amount)){
            Reciever.debit(accountNumber, infaq_amount);
            super.getBankDatabase().credit(super.getAccountNumber(), infaq_amount);
            screen.displayMessage("Please insert a transfer envelope containing ");
            screen.displayDollarAmount(infaq_amount);
            screen.displayMessageLine("\nInfaq Success");
        }
    }
    
    // prompt user to enter a deposit amount in cents 
    private double promptForInfaqAmount(){
        Screen screen = getScreen();                                            // get reference to screen
        // display the prompt
        screen.displayMessage("Please enter an infaq amount in "
            + "CENTS (or 0 to cancel): ");
        int input = keypad.getInput();
        if (input != CANCELED) {
            return (double) input / 100;                                        // return dollar amount
        }else{
            return CANCELED;

        }
    }
    
    public boolean isValidTransaction(double infaqAmount){
        if(infaqAmount <= super.getBankDatabase().getTotalBalance(super.getAccountNumber())){
            if(infaqAmount!=0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}