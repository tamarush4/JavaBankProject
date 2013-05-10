import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Banker extends ServiceGiver {
	
	public Banker(int id, BankLogger theLogger){
		waitingCustomers = new LinkedList<Customer>();
		mutex = new Object();
		myBankLogger = theLogger;
		this.id = id;
	}
	
	@Override
	protected void addCustomerToQueue(Customer c) {
		waitingCustomers.add(c);
		System.out.println(c+" is added to Line for " + this);
		c.setCurrentHandlingServiceGiver(this);
		c.start();
		

		synchronized (mutex) {
			if (waitingCustomers.size() == 1) {
				mutex.notify(); // to let know there is an Customer  waiting
			}
		}
		//log
	}

	@Override
	protected void notifyCustomer() {
		Customer firstCustomer = waitingCustomers.poll();
		if (firstCustomer != null) {

			boolean notifyCustomer = false;
			while (!notifyCustomer) {
				// make sure the customer is already in 'waiting' state
				// (synchronization)
				if (firstCustomer.getState() == State.WAITING) {
					System.out.println("Banker notifies Customer " +
							  firstCustomer.getCustName());
					synchronized (firstCustomer) {
						firstCustomer.notifyAll();
					}
					notifyCustomer = true;
				} else {
					
					  /*System.out.println("Banker needs to notify Customer " + 
							  firstCustomer.getCustName() + " but it is not waiting yet");*/
				}
			}
			synchronized (this) {
				try {
					System.out.println("Banker waits that  Customer "
							+ firstCustomer.getCustName()
							+ " will announce it is finished");
					wait(); // wait till the Customer finishes
					System.out.println("Banker was announced that  Customer "
							+ firstCustomer.getCustName() + " is finished");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public void run() {
		System.out.println("In Banker::run");
		while(isOpen){
			if(!waitingCustomers.isEmpty()){
				notifyCustomer();
			}
			else {
				synchronized (mutex) {
					try {
						System.out.println("Banker has no Customers waiting");
						mutex.wait(); // wait till there is an Customer
											// waiting
						System.out.println("Banker recieved a message there is a Customer waiting");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("Banker finished running");
	}

	@Override
	public String toString() {
		return "Banker [id=" + id + "]";
	}

	@Override
	protected void close() {
		isOpen = false;
		synchronized (mutex) {
			mutex.notifyAll();
		}
		System.out.println("Banker Closed!");
	}

}
