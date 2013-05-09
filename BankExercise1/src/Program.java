
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.logging.FileHandler;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Program {
	/**
	 * @param args
	 * @throws IOException 
	 * @throws SecurityException 
	 */

	public static void main(String[] args) throws SecurityException, IOException {
		double amount;
		String answer;
		boolean isBanker;
		boolean isRunning=true;
		List<ATM> chosenATMs = new ArrayList<>();
		List<Customer> chosenCustomers = new ArrayList<>();
		List<Banker> chosenBankers = new ArrayList<>();
		Scanner theScanner = new Scanner(System.in);
		BankLogger myBankLogger = new BankLogger();
		List<Customer> allCustomers = createXMLCustomers(myBankLogger);
		
		Banker theBanker= new Banker(myBankLogger); //LATER---> will be a list
		chosenBankers.add(theBanker);
		ArrayList<Action> ActionsListForExecution= new ArrayList<Action>();
		System.out.println("WELCOME TO BANK!");
		
		while(isRunning){
			//Print Customers in xml & choose
			Customer c1 = chooseCustomer(allCustomers);
			chosenCustomers.add(c1);
			//Print Accounts of customer chosen - to choose
			chooseAccountForAction(c1);
			
			//Print Actions - to choose
			System.out.println("Please choose an action:");
			System.out.println("1. Withdraw \n2. Deposit \n3. Give Authorization \n4. Print Info Page");
			
			int action= theScanner.nextInt();
			switch(action){
				case 1: //possibility - unite the cases 1+2
					System.out.println("Please Insert Amount to WITHDRAW:");
					amount= theScanner.nextDouble();
					theScanner.nextLine(); //empty nextLine
					isBanker = chooseServiceGiver();
					WithdrawOrDeposit withdraw = new WithdrawOrDeposit(-amount, isBanker);
					//add customer to service giver queue
					if(isBanker)
						theBanker.addCustomerToQueue(c1);
					else{
						ATM chosenATM = chooseATM();
						chosenATM.addCustomerToQueue(c1);
						chosenATMs.add(chosenATM);
					}
					ActionsListForExecution.add(withdraw);//add action to action list to execute
					c1.applyAction(withdraw);//apply to customer
					break;
				case 2:
					System.out.println("Please Insert Amount to DEPOSIT:");
					amount= theScanner.nextDouble();
					theScanner.nextLine(); //empty nextLine
					isBanker = chooseServiceGiver();
					WithdrawOrDeposit deposit = new WithdrawOrDeposit(amount, isBanker);
					if(isBanker)
						theBanker.addCustomerToQueue(c1);
					else{
						ATM chosenATM = chooseATM();
						chosenATM.addCustomerToQueue(c1);
						chosenATMs.add(chosenATM);
					}
					ActionsListForExecution.add(deposit);//add action to action list to execute
					c1.applyAction(deposit);//apply to customer
					break;
				case 3:
					System.out.println("Please Insert the Authorizee's Name");
					answer= theScanner.nextLine();
					isBanker = chooseServiceGiver();
					GiveAutorization author = new GiveAutorization(answer, isBanker);
					if(isBanker)
						theBanker.addCustomerToQueue(c1);
					else{
						ATM chosenATM = chooseATM();
						chosenATM.addCustomerToQueue(c1);
						chosenATMs.add(chosenATM);
					}
					ActionsListForExecution.add(author);
					break;
				case 4:
					
					break;
				default:{
					System.out.println("Would you like to Exit? (Y/N)");
					theScanner.nextLine();
					answer= theScanner.nextLine();
					if(answer.equals("Y"))
						isRunning = false;
					break;}
			}
			if(isRunning){
				do{
					System.out.println("Would you like to Execute? (Y/N)");
					answer= theScanner.nextLine();
				}while(!answer.equals("Y") && !answer.equals("N"));
				if(answer.equals("Y")){
					isRunning = false;
					//PROBLEM! WE CREATE RUNNABLE ONLY TO ONE BANKER< CUSTOMR AND ATM!
					//HERE RUN ON ALL ACTIONS IN LIST
					for(ATM atm : chosenATMs){
						Runnable ra = atm;
						Thread ta = new Thread(ra);
						ta.start();
					}
					for(Customer c:chosenCustomers){
						Runnable rc=c;
						Thread tc = new Thread(rc);
						tc.start();
					}
					for(Banker b : chosenBankers){
						Runnable rb = b;
						Thread tb = new Thread(rb);
						tb.start();
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}//for customer to begin waiting
				}
				else{
					System.out.println("Please Continue...");
				}
			}
		}
		
		//finally-after loop
		System.out.println("Goodbye!");
		theScanner.close();
		
	}
	public static ATM chooseATM(){
		System.out.println("Please choose an ATM for action:");
		List<ATM> atms = createXMLATMs();
		Scanner atmScanner = new Scanner(System.in);
		int atmID= atmScanner.nextInt();
		return atms.get(atmID-1);
	}
	public static void chooseAccountForAction(Customer cust){
		System.out.println("Please choose an account for action:");
		cust.printAccouns();
		Scanner accountScanner = new Scanner(System.in);
		int accountID= accountScanner.nextInt();
		cust.setAccountForAction(accountID); //setting the customer's current account
	}
	
	public static Customer chooseCustomer(List<Customer> allCustomers){
		Customer chosenCust = new Customer();
		System.out.println("Please choose a customer for action (id):");
		for(Customer c : allCustomers)
			System.out.println(c);
		Scanner custScanner = new Scanner(System.in);
		int chosenID = custScanner.nextInt();
		//match ID to customers in XML and return the customer
		chosenCust= allCustomers.get(chosenID-1);
		return chosenCust;
	}
	
	//return true if chose Banker, else if chose ATM return false
	public static boolean chooseServiceGiver(){
		Scanner serviceScanner = new Scanner(System.in);
		String answer;
		do{
			System.out.println("Would you like tu use a banker or an ATM? (B/A)");
			answer= serviceScanner.nextLine();
		}while(!answer.equals("B") && !answer.equals("A"));
		if(answer.equals("B")) //if banker
			return true;
		else
			return false;
	}
	public static List<ATM> createXMLATMs(){
		List<ATM> atms = new ArrayList<>();
		XMLReader xmlR = new XMLReader();
		NodeList atmsFromXML = xmlR.readNodeFromFile("atm");
		for(int i=0; i<atmsFromXML.getLength(); i++){
			Node currAtm = atmsFromXML.item(i);
			if (currAtm.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) currAtm;
				int id = Integer.parseInt(eElement.getAttribute("id"));
				String address = eElement.getAttribute("location");	
				ATM newAtm = new ATM(id, address);
				atms.add(newAtm);
				System.out.println(newAtm);
			}
		}
		return atms;
	}
	public static List<Account> createAllXMLAccounts(){
		List<Account> accounts = new ArrayList<>();
		XMLReader xmlR = new XMLReader();
		NodeList accountsFromXML = xmlR.readNodeFromFile("account");
		
		for(int i=0; i<accountsFromXML.getLength(); i++){
			Node currAccount = accountsFromXML.item(i);
			if (currAccount.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) currAccount;
				double balance = Double.parseDouble(eElement.getAttribute("balance"));
				String type = eElement.getAttribute("type");
				Account.AccountType aType = Account.AccountType.valueOf(type);
				Account newAccount = new Account(aType, balance);
				accounts.add(newAccount);
				//System.out.println(newAccount);
			}
		}
		return accounts;
	}
	public static List<Customer> createXMLCustomers(BankLogger theLogger){
		List<Customer> customers = new ArrayList<>();
		XMLReader xmlR = new XMLReader();
		NodeList customersFromXML = xmlR.readNodeFromFile("customer");
		for(int i=0; i<customersFromXML.getLength(); i++){
			Node currCust = customersFromXML.item(i);
			if (currCust.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) currCust;
				//read customer details from xml
				String name = eElement.getAttribute("name");	
				//read accounts
				List<Account> custAccounts = readXMLAccountsByCustomer(eElement);
				Customer c;
				try {
					c = new Customer(name, custAccounts, theLogger);
					customers.add(c);
				} catch (SecurityException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return customers;
	}
	public static List<Account> readXMLAccountsByCustomer(Element currCustomer){
		List<Account> accounts = new ArrayList<>();
		NodeList accountsFromXML = currCustomer.getElementsByTagName("account");
		for(int i=0; i<accountsFromXML.getLength(); i++){
			Node currAccount = accountsFromXML.item(i);
			if (currAccount.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) currAccount;
				double balance = Double.parseDouble(eElement.getAttribute("balance"));
				String type = eElement.getAttribute("type");
				Account.AccountType aType = Account.AccountType.valueOf(type);
				Account newAccount = new Account(aType, balance);
				accounts.add(newAccount);
				//System.out.println(newAccount);
			}
		}
		return accounts;
	}
}
