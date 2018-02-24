

package AlgmImple;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

import com.example.sdksamples.TagReportListenerImplementation;
import com.example.sdksamples.TxPowerRamp;
import com.example.sdksamples.MultipleReaders;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class process {
	 private final static String QUEUE_NAME = "a";
	 public static String time;
	 public static String tagID;
	 public static String reader_ip;
	 public static double x;
	 public static double y;
	 public static double ant_x;
	 public static double ant_y;

   public static void func() throws Exception {

	


	ConnectionFactory factory = new ConnectionFactory();
	factory.setAutomaticRecoveryEnabled(false);
	    factory.setUsername("test");
	    factory.setPassword("test");
	    factory.setHost("172.17.137.155");
	    factory.setPort(5672);
	    factory.setVirtualHost("amudavhost");
//	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();
	


	

    	HashMap<String, String> asset = TagReportListenerImplementation.getdata();
    	System.out.println(" IN FUNC OF PROCESS hai");
    	Set set = asset.entrySet();
    	Iterator iterator = set.iterator();
    	int i =0;
    	String key[] = new String[100];
    	String data[] = new String[100];
    	while(iterator.hasNext()) {
       		Map.Entry mentry = (Map.Entry)iterator.next();
       		System.out.println("ffkey is: "+ mentry.getKey() + " & Value is: ");
       		System.out.println(mentry.getValue());
       		String ab = mentry.getKey().toString();
       		String ac = mentry.getValue().toString();
       		key[i] = mentry.getKey().toString();
       		data[i] = mentry.getValue().toString();
       
            
       		i++;
       
    	}
    	
    
    	String[] parts;
    	
    	
    	  
    	for(int j=0; j<key.length; j++) {
    		if(key[j]!=null) {
    			parts = data[j].split(",");
    		reader_ip = parts[0];
    		 System.out.println("READER IP : "+reader_ip+".");
			tagID = parts[1];
			int AntennaID=Integer.parseInt(parts[2]);
    		double RSSI = Double.parseDouble(parts[3]);
			double  power = Double.parseDouble(parts[4]);
            time  = parts[5];
            String r1 = "172.17.137.5";
            String r2 = "172.17.137.6";
            System.out.println("CHECKING FOR LOOP");
          //ANTENNA POSITION in AMuDA Lab(ANT 1 , ANT 2)  1.524,8.8392,4.572,0.3048
         //   if(reader_ip == r1) { 
            if(reader_ip.equals(r1) && (AntennaID == 1 || AntennaID == 2))  {
            	ant_x = 1.524;
            	ant_y = 8.8392;
            	System.out.println("CHECKING IF LOOP");
            	if(power==10) {
            		double minx = 0.974;
            		double maxx = 2.074;
            		double miny = 7.9392;
            		double maxy = 8.8392;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 1 POWER = 10 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            	
            	
            	if(power==15) {
            		double minx = 0.824;
            		double maxx = 2.224;
            		double miny = 6.9692;
            		double maxy = 7.9392;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 1 POWER = 15 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            	
            	
            	if(power==20) {
            		double minx = 0.624;
            		double maxx = 2.424;
            		double miny = 5.5792;
            		double maxy = 6.9692;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 1 POWER = 20 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            	
            	
            	if(power==25) {
            		double minx = -1.076;
            		double maxx = 4.124;
            		double miny = 2.9692;
            		double maxy = 5.5792;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 1 POWER = 25 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            	
            	
            	if(power==30) {
            		double minx = -3.176; 
            		double maxx = 6.624;
            		double miny = -0.9608;
            		double maxy = 2.9692;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 1 POWER = 30 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            
            
                                    	
            }
            else  if((reader_ip == r1) && (AntennaID == 3 || AntennaID == 4))  {
            	ant_x = 20.00;
            	ant_y = 29.00;

            	channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            	String message = reader_ip+","+tagID+","+x +","+y+","+time;
        		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
         		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
            	
            }
            
                       
            if((reader_ip == r2) && (AntennaID == 1 || AntennaID == 2))  {
            	ant_x = 40.50;
            	ant_y = 50.00;
            	
            	
            	channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            	String message = reader_ip+","+tagID+","+x +","+y+","+time;
        		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
         		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
            	
            }
            else  if((reader_ip == r2) && (AntennaID == 3 || AntennaID == 4))  {
            	ant_x = 4.572;
            	ant_y = 0.3048;
            	
            	if(power==10) {
            		double minx = 4.222;
            		double maxx = 5.122;
            		double miny = 0.3048;
            		double maxy = 1.2048;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 2 POWER = 10 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            	
            	
            	if(power==15) {
            		double minx = 3.872;
            		double maxx = 5.272;
            		double miny = 1.2048;
            		double maxy = 2.178;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 2 POWER = 15 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            	
            	
            	if(power==20) {
            		double minx = 3.672;
            		double maxx = 5.472;
            		double miny = 2.178;
            		double maxy = 3.5648;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 2 POWER = 20 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            	
            	
            	if(power==25) {
            		double minx = 1.972;
            		double maxx = 7.172;
            		double miny = 3.5648;
            		double maxy = 6.1748;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 2 POWER = 25 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            	
            	
            	if(power==30) {
            		double minx = -0.128;
            		double maxx = 9.272;
            		double miny = 6.1748;
            		double maxy = 11.1048;
            		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
            		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
            		System.out.println("IN ANT 2 POWER = 30 and x ="+x+" and y ="+y+".");
            		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                	String message = reader_ip+","+tagID+","+x +","+y+","+time;
            		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
             		System.out.println(" [x] Sent IN FUNC LOCALIZE '" + message + "'");
                	
            	}
            	
            	            	
            }
            
            
  
		}
    
    	    		
	}
    	
    	
    	channel.close();
		connection.close();
	
   }
}





