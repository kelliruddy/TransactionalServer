package transactionserver.server;

import transactionserver.comm.Message;
import static transactionserver.comm.MessageTypes.READ;
import static transactionserver.comm.MessageTypes.WRITE;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;


public class Server {

    static TransactionManager transactionManager = null;
    static ServerSocket serverSocket = null;

    public Server(int serverPort) {

      transactionManager = new TransactionManager();

      int serverPort;
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
          (new ServerThread(serverSocket.accept())).start();
        } catch (IOException ioe) {
          System.err.println("[Server.run] ServerThread could not be initialized.");
          ioe.printStackTrace();
          System.exit(1);
        }
      }
    }

    // objects of this helper class communicate with clients
    private class ServerThread extends Thread {

        Socket client = null;
        ObjectInputStream readFromNet = null;
        ObjectOutputStream writeToNet = null;
        Message message = null;

        private ServerThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            try {
              writeToNet = new ObjectOutputStream(client.getOutputStream());
              readFromNet = new ObjectInputStream(client.getInputStream());
            } catch (IOException ioe) {
              System.err.println("[ServerThread.run] Object streams could not be initialized.");
              ioe.printStackTrace();
              System.exit(1);
            }

            try {
                message = (Message) readFromNet.readObject();
            } catch (Exception e) {
                System.err.println("[ServerThread.run] Message could not be read from object stream.");
                e.printStackTrace();
                System.exit(1);
            }

            switch (message.getType()) {
                case READ:
                    break;
                case WRITE:
                    break;
                default:
                    System.err.println("[ServerThread.run] Warning: Message type not implemented");
            }
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
