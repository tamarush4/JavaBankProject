import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Banker extends ServiceGiver {
	
	public Banker(BankLogger theLogger){
		custQ = new ArrayList<Customer>();
		mutex = new Object();
		myBankLogger = theLogger;
	}
	
	@Override
	protected void addCustomerToQueue(Customer c) {
		custQ.add(c);
		System.out.println(c+" is added to Line for " + this);
		
		//log
	}

	@Override
	protected void handleCustomer(Customer c) {
		//EXECUTE
	}


	@Override
	public void run() {
	synchronized(mutex){
			if(!custQ.isEmpty()){
				for(Customer c : custQ){
						synchronized(c){
							System.out.println("Banker " + this + " is Notifying Customer: " + c);
							c.notify();
						}
				}	
			}
		}
	}

	@Override
	public String toString() {
		return "Banker [id=" + id + "]";
	}

}
