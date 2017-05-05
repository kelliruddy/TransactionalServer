package transactionserver.client;

import java.util.concurrent.ThreadLocalRandom;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import transactionserver.comm.Message;
import transactionserver.comm.TransactionInfo;
import transactionserver.comm.MessageTypes;
import java.net.Socket;

public class TransactionClient extends Thread implements MessageTypes {

  Socket server;
  String host;
  int port = 23657;
  int i;


  public TransactionClient(String host, int i){
    this.i = i;
    this.host = host;
  }


  public void run() {
      try {
        server = new Socket(this.host, this.port);
        int aTo = ThreadLocalRandom.current().nextInt(10);
        int aFrom = ThreadLocalRandom.current().nextInt(10);
        int ammount = ThreadLocalRandom.current().nextInt(1, 11);

        TransactionInfo trans = new TransactionInfo(aTo, aFrom, ammount);

        Message message = new Message(TRANSACTION, trans);

        ObjectOutputStream writeToNet = new ObjectOutputStream(server.getOutputStream());
        writeToNet.writeObject(message);

        ObjectInputStream readFromNet = new ObjectInputStream(server.getInputStream());
        Integer result = (Integer) readFromNet.readObject();
        System.out.println("RESULT: " + result);
      }
      catch (Exception ex) {
          System.err.println("[TransactionClient.run] Error occurred");
          ex.printStackTrace();
      }
    }

  public static void main(String[] args) {

      for(int i = 0; i < 10; i++){
          (new TransactionClient(args[0],i)).start();
      }
  }
}
