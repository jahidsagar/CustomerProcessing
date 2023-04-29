package Helper;

import java.util.Arrays;
import java.util.List;

import Model.Customer;

public class PojoMaker {
	static List<String> customerInfo;
	public static void makeListFromCustomerString(String info){
		PojoMaker.customerInfo =  Arrays.asList(info.split(","));
	}
	
	public static Customer getCustomerPOJO() {
		Customer customer = new Customer();
        customer.setFirstName(customerInfo.get(0));
        customer.setLastName(customerInfo.get(1));
        customer.setCity(customerInfo.get(2));
        customer.setState(customerInfo.get(3));
        customer.setPostalCode(customerInfo.get(4));
        customer.setPhoneNo(customerInfo.get(5));
        customer.setEmail(customerInfo.get(6));
        /*
         * some customers has no ip address which is encountered when
         * running this function. so taking care for this additional bug
         * */
        if(customerInfo.size() == 8) customer.setIpAddress(customerInfo.get(7));
        
        return customer;
	}
}
