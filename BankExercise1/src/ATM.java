import java.util.ArrayList;


public class ATM extends ServiceGiver {
	private String address;
	
	public ATM(int id, String address){
		this.id = id;
		this.address = address;
		custQ = new ArrayList<Customer>();
	}
	
	@Override
	public String toString() {
		return "ATM [id=" + id +" Address=" + address +  "]";
	}

	@Override
	protected void addCustomerToQueue(Customer c) {
		custQ.add(c);
		System.out.println(c+" is added to Line for " + this);
		
		//log
		
	}

	@Override
	protected void handleCustomer(Customer c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		synchronized(mutex){
			if(!custQ.isEmpty()){
				for(Customer c : custQ){
						synchronized(c){
							System.out.println("ATM " + this +" is Notifying Customer: " + c);
							c.notify();
						}
				}	
			}
		}
	}
	
	

}
