public class BankDatabase {
   private Account[] accounts; // array of Accounts
   
   public BankDatabase() {
      accounts = new Account[2]; // just 2 accounts for testing
      accounts[0] = new Account(1234, 4321, 1000.0, 1200.0);
      accounts[1] = new Account(8765, 5678, 200.0, 200.0);  
   }
   
   private Account getAccount(int accountNumber) {
       //*keys
       return accounts[0].getAccountNumber() == accountNumber?accounts[0]:
              accounts[1].getAccountNumber() == accountNumber?accounts[1]:null;
       
//      return null; // if no matching account was found, return null
   } 

   public boolean authenticateUser(int userAccountNumber, int userPIN) {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount(userAccountNumber);

      // if account exists, return result of Account method validatePIN
      if (userAccount != null) {
         return userAccount.validatePIN(userPIN);
      }
      else {
         return false; // account number not found, so return false
      }
   } 

   public double getAvailableBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getAvailableBalance();
   } 

   public double getTotalBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getTotalBalance();
   } 

   public void credit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).credit(amount);
   }

   public void debit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).debit(amount);
   } 
   
    //*keys
    /**
     * @param availableBalance the availableBalance to set
     */
    public void setAvailableBalance(int userAccountNumber, double availableBalance) {
        getAccount(userAccountNumber).setAvailableBalance(availableBalance);
    }

    /**
     * @param totalBalance the totalBalance to set
     */
    public void setTotalBalance(int userAccountNumber, double totalBalance) {
        getAccount(userAccountNumber).setAvailableBalance(totalBalance);
    }
} 