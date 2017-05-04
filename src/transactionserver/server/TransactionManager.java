package transactionserver.server;

public class TransactionManager{

  private int numTransactions = 0;

  public void TransactionManager(){

  }
  public int assignTransactionID(){
    return numTransactions++;
  }
}
