package transactionserver.server;

import java.util.ArrayList;

// This class is created to actually deal with the accounts, creating and accessing them
// Still needs to implement locking...

public class DataManager{

  // created variable to hold accounts
  private static ArrayList<Account> accounts;
  private static LockManager lockManager;

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

  public int read(int transId, int i){
    // some kind of locking...
    Account accnt = this.accounts.get(i);

    lockManager.setLock(accnt, transId, READ);
    int balance = accnt.getBalance();
    lockManager.unLock(transId);

    return balance;
  }

  public int write(int transId, int i, int amount){
    // some kind of locking...
    Account account = accounts.get(i);

    lockManager.setLock(account, transId, WRITE);
    account.setBalance(amount);
    lockManager.unLock(transId);
    return balance;
  }
}
