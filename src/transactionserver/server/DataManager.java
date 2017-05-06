package transactionserver.server;

import java.util.ArrayList;
import transactionserver.locks.LockTypes;
import transactionserver.locks.LockManager;

// This class is created to actually deal with the accounts, creating and accessing them
// Still needs to implement locking...

public class DataManager implements LockTypes{

  // created variable to hold accounts
  private ArrayList<Account> accounts;
  private LockManager lockManager;

  public DataManager(){
    accounts = new ArrayList<Account>();
    lockManager = new LockManager();
  }

  public void createAccounts(int amount, int n) {
    for(int i = 0; i < n; i++){
      Account accnt = new Account(amount);
      this.accounts.add(accnt);
    }
  }

  public ArrayList<Account> getAccounts() {
    return accounts;
  }

  public int read(int transId, int i){
    // some kind of locking...
    System.out.println("[DataManager.read] Reading transID " + transId);
    Account accnt = this.accounts.get(i);
    int balance = 0;
    this.lockManager.setLock(accnt, transId, READ);
    try {
      balance = accnt.getBalance();
    } catch (Exception e) {}
    System.out.println("[DataManager.read] transID " + transId + " balance is " + balance);
    return balance;
  }

  public void commitTransaction(int transId) {
    System.out.println("[DataManager.unlock] Unlocking transID " + transId);
    lockManager.unLock(transId);
  }

  public int write(int transId, int i, int amount){
    // some kind of locking...
    System.out.println("[DataManager.write] Writing transID " + transId);
    Account account = accounts.get(i);

    lockManager.setLock(account, transId, WRITE);
    try {
      account.setBalance(amount);
    } catch (Exception e) {}

    System.out.println("[DataManager.write] transID " + transId + " amount is " + amount);
    return amount;
  }
}
