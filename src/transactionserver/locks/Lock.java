package transactionserver.server;

import java.util.ArrayList;

public class Lock implements LockTypes{

  private Object object; // the object being protected by the lock private
  private ArrayList<Integer> holders; // the TIDs of current holders
  private int curLockType; // the current type

  public Lock(Object object){
    this.object = object;
    this.holders = new ArrayList<Integer>();
  }

  public synchronized void acquire(int transId, int lockType ){
    while(checkConflict(transId, lockType)) {
      try {
        wait();
      }
      catch (InterruptedException e){/*...*/ }
    }
    if (holders.isEmpty()) { // no TIDs hold lock holders.addElement(trans);
      holders.add(transId);
      curLockType = lockType;
    }else if (!holders.contains(transId)){
        holders.add(transId);
    }else if (!holders.contains(transId) && curLockType == READ && lockType == WRITE) {
      curLockType = lockType;
      System.out.println("Lock promoted!");
    }
  }

  public synchronized void release(int transId){
    holders.remove(transId); // remove this holder // set locktype to none
    if (holders.isEmpty()){
      curLockType = NONE;
    }
    notifyAll();
  }

  public boolean checkConflict(int transId, int newLockType){
    if (holders.isEmpty()){
      // no conflict obviously
      return false;
    }
    else if (holders.size() == 1 && newLockType == READ){
      // only 1 holder, so can be no conflict
      return false;
    }
    else if (curLockType == READ && newLockType == READ){
      // both are only reading
      return false;
    }
    else {
      // there must be a write lock
      return true;
    }
  }
}
