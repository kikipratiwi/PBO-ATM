/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KIKI
 */
public class TransferDestination {
    private Account userAccount;
    private long amount;
    
    public TransferDestination() {
        
    }
   
    public void setAccount (int accountNumber) {
        BankDatabase bankDatabase = new BankDatabase();
        userAccount = bankDatabase.isAccountExist(accountNumber);
    }
    
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * @return the userAccount
     */
    public Account getUserAccount() {
        return userAccount;
    }

    /**
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }
    
}
