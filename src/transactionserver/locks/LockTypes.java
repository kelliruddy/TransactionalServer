package transactionserver.server;

/**
 * Interface [MessageTypes] Defines the different message types used in the application.
 * Any entity using objects of class Message needs to implement this interface.
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public interface LockTypes {

    public static int READ = 1;
    public static int WRITE = 2;
    public static int NONE= 3;

}
