package Parallellism;

import java.io.IOException;
import java.util.List;

import Controller.CustomerController;
import Helper.FileReadWriteManger;
import Model.Customer;

public class CustomerThread implements Runnable {
	private String threadName;
    private List<Customer> customerList;
    private boolean isValid;
    private CustomerController controller;
    private static final Object lock = new Object();

    public CustomerThread(String threadName, List<Customer> customerList, boolean isValid, CustomerController controller) {
        this.threadName = threadName;
        this.customerList = customerList;
        this.isValid = isValid;
        this.controller = controller;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                //Write customers in file
                FileReadWriteManger.FileWriter(threadName, customerList);

                //Insert Customers in Database
//                controller.insertCustomers(customerList, validInvalidFlag);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
