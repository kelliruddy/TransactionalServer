package transactionserver.server;

import transactionserver.comm.Message;
import transactionserver.action.Transaction;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;


public class Server {

    public static TransactionManager transactionManager = null;
    public static DataManager dataManager               = null;
    static ServerSocket serverSocket                    = null;

    public Server(int serverPort) {

      transactionManager = new TransactionManager();

      dataManager = new DataManager();
      dataManager.createAccounts(10, 10);

      try{
        serverSocket = new ServerSocket(serverPort);
      } catch (Exception e) {
        System.err.println("[Server] Things broke when starting the server.");
        e.printStackTrace();
        System.exit(1);
      }

    }

    public void run() {
      while (true) {
        try {
          (new Transaction(this, serverSocket.accept())).start();
        } catch (IOException ioe) {
          System.err.println("[Server.run] ServerThread could not be initialized.");
          ioe.printStackTrace();
          System.exit(1);
        }
        System.out.println("[Server.run] Transaction started.");
      }
    }

    public static void main(String[] args) {
        Server server = null;

        if (args.length == 1) {
          server = new Server(Integer.parseInt(args[0]));
        } else {
          server = new Server(23657);
        }
        server.run();
    }
}
