package transactionserver.server;

public class Account {

  private int money;
  private int id;

  public Account(){}

  public void setBalance(int newMoney){
    money = newMoney;
  }

  public int getBalance(){
    return money;
  }

  public void setId(int id){
    this.id = id;
  }

  public int getId(){
    return id;
  }
}
