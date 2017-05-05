package transactionserver.comm;

import java.io.Serializable;


public class TransactionInfo implements Serializable {

  int accountFrom = 0;
  int accountTo   = 0;
  int ammount     = 0;

  public int getFrom() {
    return this.accountFrom;
  }

  public int getTo() {
    return this.accountTo;
  }

  public int getAmmount() {
    return this.ammount;
  }


}
