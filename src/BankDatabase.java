public class BankDatabase {
   private Account[] accounts; // array of Accounts
   
    public BankDatabase() {
        accounts = new Account[8]; // just 8 accounts for testing

        accounts[0] = new Account(12345, 54321, 1000.0, 1200.0);
        accounts[1] = new Account(8765, 5678, 200.0, 200.0);
        //*keys
        accounts[2] = new Account(112233, 445566, 0, 0); //nomor meter, idpel
        accounts[3] = new Account(628382, 0, 500.0, 500.0); //nomor hp, 0, saldo
        //*lisna
        accounts[4] = new Account(8589, 0000, 1000.0, 000); //account ovo with no_telp and saldo
        accounts[5] = new Account(8521, 0000, 1500.0, 000); //account ovo with no_telp and saldo      
        //akun id wifi
        accounts[6] = new Account (987654321, 4444, 0, 0);
        // hani : top-up DANA
        accounts[7] = new Account(400812203, 0, 0, 0); // kode pembayaran, 0, saldo
    }
   
   private Account getAccount(int accountNumber) {
       //*keys
       for (Account account : accounts) {
           if (accountNumber == account.getAccountNumber()) {
               return account;
           }
       }
       
      return null; // if no matching account was found, return null
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
    public Account isAccountExist(int userAccountNumber) {
       Account userAccount = getAccount(userAccountNumber);
       // if account exists, return account

       if (userAccount != null) {
          return userAccount;
       }
       else {
          return null; // account number not found, so return false
       }
    }
   
    public void debitTransfer(int userAccountNumber, double amount) {
         getAccount(userAccountNumber).debitTransfer(amount);
    }
    
    public int getLastPIN(int userAccountNumber){
        return getAccount(userAccountNumber).getLastPIN();
    }
    
    public void setPIN(int userAccountNumber, int PIN){
        getAccount(userAccountNumber).setPIN(PIN);
    }
} 