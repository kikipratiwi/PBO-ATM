public class Account {
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   private double availableBalance; // funds available for withdrawal
   private double totalBalance; // funds available & pending deposits

   // Account constructor initializes attributes
   public Account(int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance) {
      accountNumber = theAccountNumber;
      pin = thePIN;
      
      //*keys
      availableBalance = theAvailableBalance;
      totalBalance = theTotalBalance; 
   }

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN(int userPIN) {
      if (userPIN == pin) {
         return true;
      }
      else {
         //*keys
         return false;
      }
   } 

   // returns available balance
   public double getAvailableBalance() {
      return availableBalance;
   } 

   // returns the total balance
   public double getTotalBalance() {
      return totalBalance;
   } 

   public void credit(double amount) {
     
   }

   public void debit(double amount) {

   }

   public int getAccountNumber() {
      return accountNumber;  
   }

   //*keys
    /**
     * @param availableBalance the availableBalance to set
     */
    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    /**
     * @param totalBalance the totalBalance to set
     */
    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }
 
   
} 