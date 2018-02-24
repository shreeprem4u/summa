package com.example.sdksamples;

import com.rabbitmq.client.Channel;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import com.example.sdksamples.*;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import com.impinj.octane.TxPowerTableEntry;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TagReportListenerImplementation implements TagReportListener {
	private final static String QUEUE_NAME = "a";
	Channel channel;
	private static String[] args;
	public static String a;
	public static int b;
	public static double c;
	public static String d;
	public static double f;
	public static String message;
	public static String IP;
	static HashMap<String, String> hm = new HashMap<String, String>();

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {

	       	
        List<Tag> tags = report.getTags();
       
        try{
        	ConnectionFactory factory = new ConnectionFactory();
        
      //Start for connecting RAbbitMq Server		
      		factory.setAutomaticRecoveryEnabled(false);
      	    factory.setUsername("test");
      	    factory.setPassword("test");
      	    factory.setHost("172.17.137.155");
      	    factory.setPort(5672);
      	    factory.setVirtualHost("amudavhost");
      	   
      	//Start for connecting RAbbitMq Server	
  //     factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        }
        catch(Exception e){System.out.println("");}
             
        

        
       
        for (Tag t : tags) {
//            System.out.print(" EPC: " + t.getEpc().toString());

            Date now = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ft.format(now);
                  
                  
                              
           String a= t.getEpc().toString();
           int b = t.getAntennaPortNumber();
           double c = t.getPeakRssiInDbm();
           String d = ft.format(now);
           double f = MultipleReaders.power();
           String IP = reader.getAddress();
           

   //       String mes = IP+","+a+","+b+","+c+","+f+","+d;	// For Multiple Readers
   //       String key = IP+","+a+","+b; 						// For Multiple Readers
          
          
          String mes = a+","+b+","+c+","+d+","+f;
          String key = a+","+b; 					// epc and antenna port as key
          
    //      String key = a; 
        
          hm.putIfAbsent(key, mes);
       
 

       	try{
            message = IP+","+a+","+b+","+c+","+f+","+d;
       		//message = a+","+b+","+c+","+d+","+f;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
 //           System.out.println(" [x] Sent TAG REPORT '" + message + "'");
        	}
        	catch(Exception e){System.out.println("NO RABBITMQ");}
           
       	
    
           
            if (reader.getName() != null) {
            	System.out.print(" Reader_ip: " + reader.getAddress()+ " Timestamp " + ft.format(now));
                //System.out.print(" Reader_name: " + reader.getName());
            } else {
                System.out.print(" Reader_ip: " + reader.getAddress());
            }

            if (t.isAntennaPortNumberPresent()) {
                System.out.print(" antenna: " + t.getAntennaPortNumber());
            }

            if (t.isFirstSeenTimePresent()) {
                System.out.print(" first: " + t.getFirstSeenTime().ToString());
            }

            if (t.isLastSeenTimePresent()) {
                System.out.print(" last: " + t.getLastSeenTime().ToString());
            }

            if (t.isSeenCountPresent()) {
                System.out.print(" count: " + t.getTagSeenCount());
            }

            if (t.isRfDopplerFrequencyPresent()) {
                System.out.print(" doppler: " + t.getRfDopplerFrequency());
            }

            if (t.isPeakRssiInDbmPresent()) {
                System.out.print(" peak_rssi: " + t.getPeakRssiInDbm());
            }

            if (t.isChannelInMhzPresent()) {
                System.out.print(" chan_MHz: " + t.getChannelInMhz());
            }

            if (t.isFastIdPresent()) {
                System.out.print("\n     fast_id: " + t.getTid().toHexString());

                System.out.print(" model: " +
                        t.getModelDetails().getModelName());

                System.out.print(" epcsize: " +
                        t.getModelDetails().getEpcSizeBits());

                System.out.print(" usermemsize: " +
                        t.getModelDetails().getUserMemorySizeBits());
            }

            System.out.println("");
            
            Set set = hm.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
               Map.Entry mentry = (Map.Entry)iterator.next();
 //             System.out.print("HASH MAPkey is: "+ mentry.getKey() + " & HASH MAP Value is: ");
              System.out.println(mentry.getValue());
              
            }
        
        }
        
        
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
       
    }
    
    public static HashMap<String, String> getdata() {
        return hm;
   }
    
    public static String data()
    {
  
        return message;
    }
}
