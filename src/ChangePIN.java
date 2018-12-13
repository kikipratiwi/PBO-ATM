public class ChangePIN extends Transaction{
    private Keypad              keypad;                                         // reference to keypad
    private final static int    CANCELED = 0;                                   // constant for cancel option

    // GantiPIN constructor
    public ChangePIN(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad){
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad      = atmKeypad;
    } 

    // perform transaction
    @Override
    public void execute(){
        Screen screen = super.getScreen();
        screen.displayMessage("Input your new PIN : ");
        int newPIN = keypad.getInput();
        if(newPIN == super.getBankDatabase().getLastPIN(super.getAccountNumber())){
            screen.displayMessageLine("Can't change to your current PIN");
        }else{
            super.getBankDatabase().setPIN(super.getAccountNumber(),newPIN);
            screen.displayMessageLine("\nYou changed your PIN");
        }
    }
}