package transactionserver.comm;

import java.io.Serializable;


public class TransactionInfo implements Serializable {

  private int accountFrom = 0;
  private int accountTo   = 0;
  private int amount      = 0;

  public void TransactionInfo(int a, int b, int c) {
    accountFrom = a;
    accountTo   = b;
    ammount     = c;
  }
  public int getFrom() {
    return this.accountFrom;
  }

  public int getTo() {
    return this.accountTo;
  }

  public int getAmount() {
    return this.amount;
  }

}
