package transactionserver.action;

import java.util.Random;

public class Transaction extends Thread {

  ObjectInputStream readFromNet = null;
  ObjectOutputStream writeToNet = null;
  private Socket socket = null;
  private Boolean running;
  private Server server;
  private Message message = null;

  public Transaction(Socket socket, Server server){
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


      }
}
