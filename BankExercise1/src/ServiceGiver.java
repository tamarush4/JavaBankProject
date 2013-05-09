import java.util.ArrayList;
import java.util.List;

public abstract class ServiceGiver implements Runnable {
	
	protected int id;
	protected List<Customer> custQ;
	protected Customer currCustomer;
	protected static Object mutex;
	protected BankLogger myBankLogger;
	
	protected abstract void addCustomerToQueue(Customer c);
	
	protected abstract void handleCustomer(Customer c);
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
