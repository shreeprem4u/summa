

package AlgmImple;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import com.example.sdksamples.TagReportListenerImplementation;
import com.example.sdksamples.TxPowerRamp;
import com.example.sdksamples.MultipleReaders;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class maintestMR {
	 private final static String QUEUE_NAME = "a";
	 public static String time;
	 public static String tagID;
	

   public static void main(String args[]) throws Exception {

	int values[]=new int[4];


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
	

	MultipleReaders.main(args);
//	process.func();
	

    	HashMap<String, String> people = TagReportListenerImplementation.getdata();
 //   	System.out.println("hai");
    	Set set = people.entrySet();
    	Iterator iterator = set.iterator();
    	int i =0;
    	String epc[] = new String[100];
    	String data[] = new String[100];
    	
    	
    	
    	
    	while(iterator.hasNext()) {
       		Map.Entry mentry = (Map.Entry)iterator.next();
       		System.out.print("IN MAIN FUNC. HM key is: "+ mentry.getKey() + " & Value is: ");
       		System.out.println(mentry.getValue());
       		String ab = mentry.getKey().toString();
       		String ac = mentry.getValue().toString();
       		epc[i] = mentry.getKey().toString();
       		data[i] = mentry.getValue().toString();
       
            
       		i++;
       
    	}
    	
    
    	String[] parts;

   
    	for(int j=0; j<epc.length; j++) {
    		if(epc[j]!=null) {
    			parts = data[j].split(",");
			tagID = parts[0];
			int AntennaID=Integer.parseInt(parts[1]);
    			int RSSI = (int) Double.parseDouble(parts[2]);
			time  = parts[3];
            		double  power = Double.parseDouble(parts[4]);
            		
                
            	      		
            		
              		double powerlevel =10.0;
                    
            		if(power >10.0 && power <=15.0) {
             			powerlevel = 15;
           		    }
            		else if(power >15.0 && power <=20.0) {
                    		powerlevel = 20;
                   	}
                   	else if(power >20.0 && power <=25.0) {
                		powerlevel = 25;
                  	}
                   	else if(power >25.0 ) {
                		powerlevel = 30;
                   	} 
            		
		    	System.out.println(" EPC : "+tagID  + " AntennaID : "+AntennaID + " RSSI : "+RSSI + " Timestamp : "+time + " Powerlevel : "+powerlevel);
    	
    			if(AntennaID==1 || AntennaID==2) {
     	     			values[0]= Math.round(RSSI);
     	     			values[1]=(int) powerlevel;
     	           	}
        		if(AntennaID==3 || AntennaID==4) {
     	  				values[2]= Math.round(RSSI);	
     	  				values[3]=(int) powerlevel;
     	 		}
     	   	        for (int k =0; k<values.length;k++) {
        			System.out.println(values[k]);
        		}
     	  		int values_retrieve[]= values;
     	  //		double Antenna_XY_positions[]={3.048,8.8392,4.572,0.3048}; //(In METRES) (ANT position is 10,29,15,1 in feet) To check real time accuracy in Amuda Lab (size x=0 to 20, y=0 to 30 feet) 
     	  		double Antenna_XY_positions[]={1.524,8.8392,4.572,0.3048}; //(In METRES) (ANT position is 5,29,15,1 in feet) To check real time accuracy in Amuda Lab (size x=0 to 20, y=0 to 30 feet) 
     		LocalizationBilateration location =new LocalizationBilateration(values_retrieve,Antenna_XY_positions);
     		PowerLevel pw=new PowerLevel(values_retrieve,Antenna_XY_positions);
     		double xy_coordinates_RSSI[]=LocalizationBilateration.FunctionList();
     		double xy_coordinates_pw[]=pw.FunctionList();
     		double values_store[]=new double[2];
     		values_store[0]=(xy_coordinates_RSSI[0]+xy_coordinates_pw[0])/2;
     		values_store[1]=(xy_coordinates_RSSI[1]+xy_coordinates_pw[1])/2;
     		

     		//double x = 746430.50768 + (values_store[0]*1256.48158); //Staff Room
     		//double y = 221659.22431-(values_store[1]*1243.10934);   //Staff Room
     		
     		
     		double x = values_store[0]; //Staff Room
     		double y = values_store[1];   //Staff Room
     		
     		     		
    		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
     		String message = tagID +","+x+","+y+","+time;
    		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
     		System.out.println(" [x] Sent LOCALIZE '" + message + "'");
     		
		}
    
	}
	
    	channel.close();
		connection.close();

    	
    	
   }
}



/*
package AlgmImple;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import com.example.sdksamples.MultipleReaders;
import com.example.sdksamples.TagReportListenerImplementation;
import com.example.sdksamples.TxPowerRamp;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class maintestMR {

	

   public static void main(String args[]) throws Exception {

	      
	    
	 MultipleReaders.main(args);
	  	     	
   }
}

*/
