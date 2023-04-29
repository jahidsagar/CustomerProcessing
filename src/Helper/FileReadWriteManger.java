package Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Customer;

public class FileReadWriteManger {
	static String outputPath = "D:\\eclipse_workspace\\CustomerProcess\\src\\Entry\\output\\";
	
	public static List<Customer> generateCustomerListFromFile(String filePath){
		List<Customer> customers= new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File(filePath));
			while (scanner.hasNextLine()) {
				
				String text = scanner.nextLine();
//				System.out.println(text);
				PojoMaker.makeListFromCustomerString(text);
				customers.add(PojoMaker.getCustomerPOJO());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}
	
	public static synchronized void FileWriter(String threadName, List<Customer> customerList) throws IOException {

        RandomAccessFile file = new RandomAccessFile(outputPath + threadName + ".txt", "rw");
        FileChannel channel = file.getChannel();

        try {
            for (Customer customer : customerList) {
                String data = customer.objectToString() + System.lineSeparator();
                ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
                channel.position(channel.size());
                channel.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.close();
            file.close();
        }

    }
	
}
