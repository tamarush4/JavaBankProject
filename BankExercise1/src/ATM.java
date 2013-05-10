import java.util.ArrayList;
import java.util.LinkedList;


public class ATM extends ServiceGiver {
	private String address;
	
	public ATM(int id, String address){
		this.id = id;
		this.address = address;
		waitingCustomers = new LinkedList<Customer>();
	}
	
	@Override
	public String toString() {
		return "ATM [id=" + id +" Address=" + address +  "]";
	}

	@Override
	protected void addCustomerToQueue(Customer c) {
		waitingCustomers.add(c);
		System.out.println(c+" is added to Line for " + this);
		
		//log
		
	}

	@Override
	public void run() {
		synchronized(mutex){
			if(!waitingCustomers.isEmpty()){
				for(Customer c : waitingCustomers){
						synchronized(c){
							System.out.println("ATM " + this +" is Notifying Customer: " + c);
							c.notify();
						}
				}	
			}
		}
	}

	@Override
	protected void notifyCustomer() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void close() {
		isOpen = false;
		synchronized (mutex) {
			mutex.notifyAll();
		}
	}

}
