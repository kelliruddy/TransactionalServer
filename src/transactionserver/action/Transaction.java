package transactionserver.action;

import java.util.Random;

public class Transaction extends Thread {

  ObjectInputStream readFromNet = null;
  ObjectOutputStream writeToNet = null;
  private Socket socket = null;
  private Boolean running;
  private Server server;
  private Message message = null;

  public Transaction(Server server, Socket socket){
    this.server = server;
    this.socket = socket;

    this.running = true;
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

      int firstAccountBalance = this.server.dataManager.read(accountFrom);
      int firstAccountnewBalance =  firstAccountBalance - amount;

      this.server.DataManager.write(accountFrom, firstAccountnewBalance);

      int secondAccountBalance = this.server.dataManager.read(accountTo);
      int secondAccountNewBalance = secondAccountBalance + amount;

      this .server.dataManager.write(accountTo, secondAccountNewBalance);


      }
}
