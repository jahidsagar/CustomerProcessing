package Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Customer;

public class CustomerValidator {
	Map<String, List<Customer>> validInvalidCustomers;
	List<Customer> validCustomer;
	List<Customer> invalidCustomer;
	List<Customer> customers;
	public static final String VALID = "valid";
	public static final String INVALID = "invalid";
	
	public CustomerValidator(List<Customer> customers){
		this.customers = customers;
		validInvalidCustomers = new HashMap<String, List<Customer>>();
		validCustomer = new ArrayList<Customer>();
		invalidCustomer = new ArrayList<Customer>();
	}
	
	public Map<String, List<Customer>> getFilteredCustomers(){
		for(Customer customer:customers) {
			if(this.validateEmail(customer.getEmail()) == true && this.validatePhoneNo(customer.getPhoneNo()) == true) validCustomer.add(customer);
			else invalidCustomer.add(customer);
		}
		
		validInvalidCustomers.put(CustomerValidator.VALID, validCustomer);
		validInvalidCustomers.put(CustomerValidator.INVALID, invalidCustomer);
		
		return validInvalidCustomers;
	}
	
	private boolean validateEmail(String email) {
        String emailFormat = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(emailFormat);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean validatePhoneNo(String phoneNo) {
        String phoneNoFormat = "^\\d{10}$" + "|^\\d{3}[-\\s]\\d{3}[-s]\\d{4}$" + "|^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";

        Pattern pattern = Pattern.compile(phoneNoFormat);
        Matcher matcher = pattern.matcher(phoneNo);

        return matcher.matches();
    }
	
    public static List<Customer> getUniqueCustomer(List<Customer> customers){
    	Map<String,Customer> uniqueCustomers = new HashMap<String, Customer>();
    	List<Customer> uniqueCustomerList = new ArrayList<Customer>();
    	for(Customer customer:customers) {
    		String key = customer.getEmail() + " " + customer.getPhoneNo();
    		if(!uniqueCustomers.containsKey(key)) {
    			uniqueCustomers.put(key, customer);
    			uniqueCustomerList.add(customer);
    		}
    	}
    	return uniqueCustomerList;
    }
}
