package transactionserver.server;

public class Account {

  private int balance;

  public Account(int amount){
    setBalance(amount);
  }

  public int getBalance(){
    return balance;
  }

  public void setBalance( int amount) {
    balance = amount;
  }

}
