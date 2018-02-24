package com.example.sdksamples;

import com.impinj.octane.AntennaConfigGroup;
import com.impinj.octane.FeatureSet;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.ReportMode;
import com.impinj.octane.Settings;
import com.impinj.octane.TxPowerTableEntry;

import AlgmImple.process;

import java.util.ArrayList;
import java.util.Scanner;

public class MultipleReaders {

    static ArrayList<ImpinjReader> readers = new ArrayList<ImpinjReader>();
    public static double p;

    public static void main(String[] args) {

        // Connect to the reader.
        if (args.length < 1) {
            System.out.print(
                    "Must pass at least one reader hostname or IP as argument 1");
            return;
        }

        for (int i = 0; i < args.length; i++) {
            String name = "Reader_" + args[i];
            ImpinjReader reader = new ImpinjReader();
            reader.setName(name);

            try {
                System.out.println("Attempting connection to " + name);
                reader.connect(args[i]);

            } catch (OctaneSdkException ex) {
                // keep trying other readers if this doesn't work
                System.out.println("Error Connecting  to " + name + ": "
                        + ex.toString() + "...continuing with other readers");
                continue;
            }
            
            try {
            	
            	 // get the features
                FeatureSet features = reader.queryFeatureSet();
            	
                Settings settings = reader.queryDefaultSettings();
                System.out.println("Applying Settings to " + name);
                reader.applySettings(settings);
                
                
                
                
                
                
             // send a tag report for every tag read
                settings.getReport().setMode(ReportMode.Individual);
                settings.getReport().setIncludePeakRssi(true);
                settings.getReport().setIncludeAntennaPortNumber(true);

                // set just one specific antenna for this example
                AntennaConfigGroup ac = settings.getAntennas();

                ac.disableAll();
                ac.getAntenna((short) 1).setEnabled(true);
                ac.getAntenna((short) 2).setEnabled(true);
                ac.getAntenna((short) 3).setEnabled(true);
                ac.getAntenna((short) 4).setEnabled(true);
                

                
                
                
                
                
                reader.setTagReportListener(
                        new TagReportListenerImplementation());
                
                
               
                
                for (TxPowerTableEntry t : features.getTxPowers()) {
                	p = t.Dbm;
                    System.out.println("Setting power to " + t.Dbm);
                    ac.getAntenna((short) 1).setIsMaxTxPower(false);
                    ac.getAntenna((short) 2).setIsMaxTxPower(false);
                    ac.getAntenna((short) 3).setIsMaxTxPower(false);
                    ac.getAntenna((short) 4).setIsMaxTxPower(false);
                    
                    ac.getAntenna((short) 1).setTxPowerinDbm(t.Dbm);
                   
                    ac.getAntenna((short) 2).setTxPowerinDbm(t.Dbm);
                   
                    ac.getAntenna((short) 3).setTxPowerinDbm(t.Dbm);
                   
                    ac.getAntenna((short) 4).setTxPowerinDbm(t.Dbm);
                    // Apply the new settings
                    reader.applySettings(settings);
                    // Start the reader
                    reader.start();
                   
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        
                    }
                    reader.stop(); // Gives undesired output
                }
                
                
                

                
            //    reader.start();
                readers.add(reader);
            } catch (OctaneSdkException ex) {
                System.out.println("Could not start reader " + name + ": "
                        + ex.toString());
            }
        }
      
 /*       try {
        	process.func();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			  System.out.println("ERROR IN FUNC CALLING.");
			e.printStackTrace();
		}
 */       
        
             
        System.out.println("Press Enter to continue and read all tags.");
        Scanner s = new Scanner(System.in);
        s.nextLine();

        for (int i = 0; i < readers.size(); i++) {

            try {
                ImpinjReader reader = readers.get(i);
                reader.stop();
                reader.disconnect();
            } catch (OctaneSdkException ex) {
                System.out.println("Failed to stop reader: " + ex.getMessage());
            }

        }
    }
    

	
    public static double power()
    {
        return p;
    }
    
    
}




/*

package com.example.sdksamples;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.Settings;

import java.util.ArrayList;
import java.util.Scanner;

public class MultipleReaders {

    static ArrayList<ImpinjReader> readers = new ArrayList<ImpinjReader>();

    public static void main(String[] args) {

        // Connect to the reader.
        if (args.length < 1) {
            System.out.print(
                    "Must pass at least one reader hostname or IP as argument 1");
            return;
        }

        for (int i = 0; i < args.length; i++) {
            String name = "Reader_" + args[i];
            ImpinjReader reader = new ImpinjReader();
            reader.setName(name);

            try {
                System.out.println("Attempting connection to " + name);
                reader.connect(args[i]);

            } catch (OctaneSdkException ex) {
                // keep trying other readers if this doesn't work
                System.out.println("Error Connecting  to " + name + ": "
                        + ex.toString() + "...continuing with other readers");
                continue;
            }
           
            try {
                Settings settings = reader.queryDefaultSettings();
                System.out.println("Applying Settings to " + name);
                reader.applySettings(settings);

                reader.setTagReportListener(
                        new TagReportListenerImplementation());

                System.out.println("Starting " + name);
                reader.start();
                readers.add(reader);
            } catch (OctaneSdkException ex) {
                System.out.println("Could not start reader " + name + ": "
                        + ex.toString());
            }
        }

        System.out.println("Press Enter to continue and read all tags.");
        Scanner s = new Scanner(System.in);
        s.nextLine();

        for (int i = 0; i < readers.size(); i++) {

            try {
                ImpinjReader reader = readers.get(i);
                reader.stop();
                reader.disconnect();
            } catch (OctaneSdkException ex) {
                System.out.println("Failed to stop reader: " + ex.getMessage());
            }

        }
    }
}
*/