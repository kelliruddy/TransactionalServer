package transactionserver.server;

import java.util.ArrayList;

// This class is created to actually deal with the accounts, creating and accessing them
// Still needs to implement locking...

public class DataManager{

  // created variable to hold accounts
  static private ArrayList<Account> accounts;

  public DataManager(){
    accounts = new ArrayList<>();

    // creating 10 accounts
    for (int i = 0; i < 10; i++){
      Account newAccount = new Account();
      newAccount.setBalance(10);
      newAccount.setId(i);
      accounts.add(newAccount);
    }
  }

  public int read(int accountId){
    // some kind of locking...
    Account account = accounts.get(accountId);
    int balance = account.getBalance();
    return balance;
  }

  public int write(int accountId, int amount){
    // some kind of locking...
    Account account = accounts.get(accountId);
    account.setBalance(amount);
    return account.getBalance();
  }
}
