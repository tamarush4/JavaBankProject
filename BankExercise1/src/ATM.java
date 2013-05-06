import java.util.ArrayList;


public class ATM extends ServiceGiver {

	public ATM(){
		custQ = new ArrayList<Customer>();
	}
	
	@Override
	protected void addCustomerToQueue(Customer c) {
		custQ.add(c);
		
		//log
		
	}

	@Override
	protected void handleCustomer(Customer c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		synchronized(mutex){
			for(Customer c : custQ){
				c.notify();
			}
		}
	}
	
	

}
