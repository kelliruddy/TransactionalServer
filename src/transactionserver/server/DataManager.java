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

  public int read(int transId, int i){
    // some kind of locking...
    Account accnt = this.accounts.get(i);

    int balance;

    this.lockManager.setLock(accnt, transId, READ);
    try {
      balance = accnt.getBalance();
    } finally {
      lockManager.unLock(transId);
    }

    return balance;
  }

  public int write(int transId, int i, int amount){
    // some kind of locking...
    Account account = accounts.get(i);

    lockManager.setLock(account, transId, WRITE);
    try {
      account.setBalance(amount);
    } finally {
      lockManager.unLock(transId);
    }

    return amount;
  }
}
