import java.util.Random;

public class Transaction{
	
	private int to;
	private int from;
	private int amount;
	private int id;
	
	public Transaction(){
		Random rand = new Random();
		amount = rand.nextInt(100);
		// randomly choose to and from accounts, making sure they are not the same
	}
	
	public void setTransactionId(int id){
		this.id = id;
	}
}
