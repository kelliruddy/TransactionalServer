package transactionserver.server;

public class Account {

  private int balance = null;

  public void Account(int amount){
    balance = amount;
  }
  public int readBalance(){
    return balance;
  }

  public void writeBalance( int amount) {
    balance = amount;
  }

}
