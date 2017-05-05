package transactionserver.server;

import java.util.ArrayList;

// This class is created to actually deal with the accounts, creating and accessing them
// Still needs to implement locking...

public class DataManager{

  // created variable to hold accounts
  static private ArrayList<Account> accounts;

  public DataManager(){
    accounts = new ArrayList<Account>();
  }

  public void createAccounts(int amount, int n) {
    for(int i = 0; i < n; i++){
      Account accnt = new Account(amount);
      this.accounts.add(accnt);
    }
  }

  public int read(int i){
    // some kind of locking...
    Account accnt = this.accounts.get(i);
    int balance = accnt.getBalance();
    return balance;
  }

  public int write(int i, int amount){
    // some kind of locking...
    Account account = accounts.get(i);
    account.setBalance(amount);
    return account.getBalance();
  }
}
