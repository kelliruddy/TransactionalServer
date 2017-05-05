package transactionserver.action;

import java.util.Random;

public class Transaction extends Thread {

  ObjectInputStream readFromNet = null;
  ObjectOutputStream writeToNet = null;

  private Socket socket = null;
  private Server server = null;

  public int transId;

  public Transaction(Server server, Socket socket){
    this.server = server;
    this.socket = socket;

    transId = this.server.transactionManager.assignTransactionID();
  }

  @Override
  public void run() {
      // setting up object streams
      try{
        readFromNet = new ObjectInputStream(client.getInputStream());
        writeToNet = new ObjectOutputStream(client.getOutputStream());
      } catch (IOException ioe) {
        System.err.println("[ServerThread.run] Object streams could not be initialized.");
        ioe.printStackTrace();
        System.exit(1);
      }

      Message message = null;
      // reading message
      try {
          message = (Message) readFromNet.readObject();
      } catch (Exception e) {
          System.err.println("[ServerThread.run] Message could not be read from object stream.");
          e.printStackTrace();
          System.exit(1);
      }
      TransactionInfo transactionInfo = null;
      switch (message.getType()) {
        case TRANSACTION:
          transactionInfo = (TransactionInfo) message.getContent();
          break;
        default:
          system.err.println("oops, wrong message type.");
      }
      int accountFrom = transactionInfo.getFrom();
      int accountTo   = transactionInfo.getTo();
      int amount      = transactionInfo.getamount();

      int firstAccountBalance = this.server.dataManager.read(transId, accountFrom);
      int firstAccountnewBalance =  firstAccountBalance - amount;

      this.server.DataManager.write(transId, accountFrom, firstAccountnewBalance);

      int secondAccountBalance = this.server.dataManager.read(transId, accountTo);
      int secondAccountNewBalance = secondAccountBalance + amount;

      int restult = this.server.dataManager.write(transId, accountTo, secondAccountNewBalance);
      writeToNet.writeObject(result);
      }
}
