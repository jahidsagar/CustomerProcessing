package Entry;

import java.util.List;
import java.util.Map;

import Controller.CustomerController;
import Helper.CustomerValidator;
import Helper.FileReadWriteManger;
import Model.Customer;
import Parallellism.CustomerThread;

public class Main {
	public static long outputPerFile = 100000; // 100k
	
	public static long getThreadCount(long length) {
		return (length/outputPerFile) + (length % outputPerFile > 0 ? 1 : 0);
	}
	
	public static void customerProcessor(long validCustomerThreadCount, CustomerController customerController, List<Customer> customerList, String name) {
		CustomerThread[] threads = new CustomerThread[(int) validCustomerThreadCount];
		for (int i = 0; i < validCustomerThreadCount; i++) {
            int startIndex = (int) (i * Main.outputPerFile);
            int endIndex = (int) ((i + 1) * Main.outputPerFile);

            if (endIndex > customerList.size())
            	endIndex = customerList.size();
            System.out.println("From: " + startIndex+" To: "+endIndex);

            threads[i] = new CustomerThread(name + " Form " + startIndex + " To " + endIndex, customerList.subList(startIndex, endIndex), true, customerController);
            new Thread(threads[i]).start();
        }
	}

	public static void main(String[] args) {
		// input file path
		String customerFilePath = "D:\\eclipse_workspace\\CustomerProcess\\src\\Entry\\1M-customers.txt";
		CustomerValidator customerValidator;
		Map<String, List<Customer>> validInvalidCustomers;
		
		// make 3 list for all, valid and invalid customers
		List<Customer> customers = FileReadWriteManger.generateCustomerListFromFile(customerFilePath);
		
		customerValidator = new CustomerValidator(customers);
		validInvalidCustomers = customerValidator.getFilteredCustomers();
		// get only valid customers
		List<Customer> validCustomers = CustomerValidator.getUniqueCustomer(validInvalidCustomers.get(CustomerValidator.VALID));
		// get only invalid customers
		List<Customer> invalidCustomers = CustomerValidator.getUniqueCustomer(validInvalidCustomers.get(CustomerValidator.INVALID));
		
		System.out.println("Total Customers: " + customers.size());
		System.out.println("Valid Customers: " + validCustomers.size());
		System.out.println("Invalid Customers: " + invalidCustomers.size());
		
		long validCustomerThreadCount = Main.getThreadCount(validCustomers.size());
		long invalidCustomerThreadCount = Main.getThreadCount(invalidCustomers.size());
		
		CustomerController customerController = new CustomerController();
		Main.customerProcessor(validCustomerThreadCount, customerController, validCustomers, "Valid Customer");
		Main.customerProcessor(invalidCustomerThreadCount, customerController, invalidCustomers, "Invalid Customer");
		
		
		
	}

}
