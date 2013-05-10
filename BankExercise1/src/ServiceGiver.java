import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class ServiceGiver implements Runnable {
	
	protected int id;
	protected Queue<Customer> waitingCustomers;
	//protected Customer currCustomer; NOT IN USE?
	protected static Object mutex;
	protected BankLogger myBankLogger;
	protected boolean isOpen = true;
	protected abstract void addCustomerToQueue(Customer c);
	protected abstract void close();
	protected abstract void notifyCustomer();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
