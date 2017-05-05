package transactionserver.action;

import java.util.Random;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transactionserver.comm.Message;
import java.io.IOException;
import java.util.ArrayList;

import transactionserver.server.Server;
import transactionserver.comm.TransactionInfo;
import transactionserver.comm.MessageTypes;
import transactionserver.server.Account;

public class Transaction extends Thread implements MessageTypes {

  ObjectInputStream readFromNet = null;
  ObjectOutputStream writeToNet = null;

  private Socket client = null;
  private Server server = null;

  public int transId;

  public Transaction(Server server, Socket socket){
    this.server = server;
    this.client = socket;

    transId = this.server.transactionManager.assignTransactionID();
    System.out.println("[Transaction.init] Created transaction with ID: " + transId);
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
          System.err.println("oops, wrong message type.");
      }
      int accountFrom = transactionInfo.getFrom();
      int accountTo   = transactionInfo.getTo();
      int amount      = transactionInfo.getAmount();

      int firstAccountBalance = this.server.dataManager.read(transId, accountFrom);
      int firstAccountnewBalance =  firstAccountBalance - amount;

      this.server.dataManager.write(transId, accountFrom, firstAccountnewBalance);

      int secondAccountBalance = this.server.dataManager.read(transId, accountTo);
      int secondAccountNewBalance = secondAccountBalance + amount;

      int result = this.server.dataManager.write(transId, accountTo, secondAccountNewBalance);
      this.server.dataManager.commitTransaction(transId);

      try {
        writeToNet.writeObject(result);
      } catch (IOException ioe) {

      }

      int total = 0;
      ArrayList<Account> accts = this.server.dataManager.getAccounts();

      for (int i = 0; i < accts.size(); i++ ) {
        total += accts.get(new Integer(i)).getBalance();
        System.out.println("Account " + i + ": " + accts.get(new Integer(i)).getBalance());
      }

      System.out.println("Total: " + total);

      }
}
