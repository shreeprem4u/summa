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
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class syntheticTest {
	 private final static String QUEUE_NAME = "a";
	 public static String time;
	 public static String tagID;
	 

public static void main(String args[]) throws Exception{

	int values[]=new int[4];
	
	
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();
	

 
    	String tagID = "2300";
    	int AntennaID =2;
    	int RSSI = -69;
    	double power =10.00;
    	String time = "2018-02-00 12:00:00"; 
	
    	
    	
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
    	
    	if(AntennaID==1 || AntennaID==2)
        {
     	   
     	 	   values[0]= Math.round(RSSI);
     		   values[1]=(int) powerlevel;
     	   
        }
        if(AntennaID==3 || AntennaID==4)
        {
     	   
     		   values[2]= Math.round(RSSI);
     	   	   values[3]=(int) powerlevel;
     	   
        }
        
         int values_retrieve[]= values;
     	  
 double Antenna_XY_positions[]={1.524,8.8392,4.572,0.3048}; //(In METRES)
     		LocalizationBilateration location =new LocalizationBilateration(values_retrieve,Antenna_XY_positions);
     		PowerLevel pw=new PowerLevel(values_retrieve,Antenna_XY_positions);
     		double xy_coordinates_RSSI[]=LocalizationBilateration.FunctionList();
     		double xy_coordinates_pw[]=pw.FunctionList();
     		double values_store[]=new double[2];
     		values_store[0]=(xy_coordinates_RSSI[0]+xy_coordinates_pw[0])/2;
     		values_store[1]=(xy_coordinates_RSSI[1]+xy_coordinates_pw[1])/2;
     		

     		//double x = (values_store[0]*376.25)+691284.2327;
     		//double y = (values_store[1]*387.9310)+183034.9854;
     		
     		double x = (values_store[0]);
     		double y = (values_store[1]);
     		
     	
     	//	double x = 746430.50768 + (values_store[0]*1256.48158);
     	//	double y = 221659.22431-(values_store[1]*1243.10934);
     		
     		
     		//double x = (values_store[0]*387.9310)+183034.9854;
     		//double y = (values_store[1]*376.25)+691284.2327;

     	
     		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
     		String message = tagID +","+x+","+y+","+time;
    		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
     		System.out.println(" [x] Sent '" + message + "'");
  	
  
 
	
   	
    channel.close();
    connection.close();
    
}
    
}
    
  
	

