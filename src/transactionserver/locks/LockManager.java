package transactionserver.locks;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import transactionserver.server.Account;


public class LockManager implements LockTypes{

  private HashMap<Account, Lock> theLocks;

  public LockManager(){

    theLocks = new HashMap<Account, Lock>();

  }

  public void setLock(Account object, int transId, int lockType){

    Lock foundLock;

    synchronized(this){
      // find the lock associated with object
      foundLock = theLocks.get(object);
      // if there isn’t one
      if (theLocks == null){
        // create new lock
        foundLock = new Lock(object);
        // add to hashmap
        theLocks.put(object, foundLock);
      }
    }
    foundLock.acquire(transId, lockType);
  }

  // synchronize this one because we want to remove all entries
  public synchronized void unLock(int transId) {
    // loop through locks and unlocking those on transId
  }
}