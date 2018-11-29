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
    private int amount;
    
    public TransferDestination() {
        
    }
   
    public void setAccount (int accountNumber) {
        BankDatabase bankDatabase = new BankDatabase();
        userAccount = bankDatabase.isAccountExist(accountNumber);
    }
    
    public void setAmount(int amount) {
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
    public int getAmount() {
        return amount;
    }
    
}
