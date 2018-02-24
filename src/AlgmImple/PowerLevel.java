package AlgmImple;


import java.awt.*;
import java.applet.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
public class PowerLevel {
	static int RSSI_Antenna_A;
	static int RSSI_Antenna_B;
	static int  power_level_A1;
	static int  power_level_A2;
	static double AntennaA_position_x;
	static double AntennaA_position_y;
	static double AntennaB_position_x;
	static double AntennaB_position_y;
	static double x=0.0,y=0.0;
	static double startX,endX,bezierX,startY,endY,bezierY;
	public PowerLevel(int[] values_retrieve, double[] antenna_XY_positions) {
		// TODO Auto-generated constructor stub
		 RSSI_Antenna_A=values_retrieve[0];
		 power_level_A1=values_retrieve[1];
		 
         RSSI_Antenna_B=values_retrieve[2];			
         power_level_A2=values_retrieve[3];
         
        AntennaA_position_x=antenna_XY_positions[0];
        AntennaA_position_y=antenna_XY_positions[1];
         AntennaB_position_x=antenna_XY_positions[2];
         AntennaB_position_y=antenna_XY_positions[3];
	}
	public double[] FunctionList(){
		double	XY_points[]=BezierCurve_Generation();
	//XY_coordinates();
		return XY_points;	 	
	}
	
	 static double[] BezierCurve_Generation() {
		double x=0.0,y=0.0;
		double points[]=new double[2];
		 if((power_level_A1>0) && (power_level_A2==0)){
			 System.out.println("detected by A1 only");
			 if (power_level_A1==10){
				 x=Math.abs(AntennaA_position_x-0.504);
				 y=Math.abs(AntennaA_position_y-(0.9/8));
			 }
			 if (power_level_A1==15){
				 x=Math.abs(AntennaA_position_x-0.815);
				 y=Math.abs(AntennaA_position_y-1.87);
				 
			 }
			 if (power_level_A1==20){
				 x=Math.abs(AntennaA_position_x-1.11);
				 y=Math.abs(AntennaA_position_y-3.26);
				 
			 }
			 if (power_level_A1==25){
			//	 x=Math.abs(AntennaA_position_x+(2.201/8));
			//	 y=Math.abs(AntennaA_position_y-2.20);
				 
				 // CHANGE ON 03.02.2018 by Prem		 
				 x=Math.abs(AntennaA_position_x+(2.201)/8);
				 y=Math.abs(AntennaA_position_y-2.201);
			
				 
			 }
			 if (power_level_A1==30){
				 x=Math.abs(AntennaA_position_x+(4.194/8));
				 y=Math.abs(AntennaA_position_y-10.80); //7.1628
			 }
		 }
		 else if((power_level_A2 >0) && (power_level_A1==0)){
			 System.out.println("detected by A2 only");
			 if (power_level_A2==10){
				 x=AntennaB_position_x+0.504;
				 y=AntennaB_position_y+0.9;
			 }
			 if (power_level_A2==15){
				 x=AntennaB_position_x+0.815;
				 y=AntennaB_position_y+1.87;
			 }
			 if (power_level_A2==20){
				 x=AntennaB_position_x+1.11;
				 y=AntennaB_position_y+3.26;	 
			 }
			 if (power_level_A2==25){
				 x=Math.abs(AntennaB_position_x-(2.201/8));
				 y=AntennaB_position_y+5.87;
				 
				// y=AntennaB_position_y+2.20; // CHANGE ON 03.02.2018 by Prem
				 
			 }
			 if (power_level_A2==30){
				 x=Math.abs(AntennaB_position_x-(4.194/8));
				 y=AntennaB_position_y+10.80; //7.1628
			 }
		 }
		 else
		 {
			 System.out.println("detected by both antennas");
			 if (power_level_A1==20){
				 if (power_level_A2==20){
					x=Math.abs((AntennaA_position_x+1.11+AntennaB_position_x-1.11)/2); 
					y=Math.abs((AntennaA_position_y-3.26+AntennaB_position_x+3.26)/2);
				 }
				 if (power_level_A2==25){
					 x=Math.abs((AntennaA_position_x+1.11+AntennaB_position_x-2.201)/2); 
					 y=Math.abs((AntennaA_position_y-3.26+AntennaB_position_x+5.87)/2);
					 
				//	 y=Math.abs((AntennaA_position_y-3.26+AntennaB_position_x+2.20)/2); //CHANGE ON 03.02.2018 by Prem
					 
				 }
				 if (power_level_A2==30){
					 x=Math.abs((AntennaA_position_x+1.11+AntennaB_position_x-4.194)/2); 
					 y=Math.abs((AntennaA_position_y-3.26+AntennaB_position_x+10.80)/2); //7.1628
				 }
			 }
			 if (power_level_A1==25){
				 if (power_level_A2==20){
					 x=Math.abs((AntennaA_position_x+2.201+AntennaB_position_x-1.11)/2); 
					 y=Math.abs((AntennaA_position_y-5.87+AntennaB_position_x+3.26)/2);
				 }
				 if (power_level_A2==25){
					 x=Math.abs((AntennaA_position_x+2.201+AntennaB_position_x-2.201)/2); 
					 y=Math.abs((AntennaA_position_y-5.87+AntennaB_position_x+5.87)/2);
				 }
				 if (power_level_A2==30){
					 x=Math.abs((AntennaA_position_x+2.201+AntennaB_position_x-4.194)/2); 
					 y=Math.abs((AntennaA_position_y-5.87+AntennaB_position_x+10.80)/2); //7.1628
				 }
			 }
			 if (power_level_A1==30){
				 if (power_level_A2==20){
					 x=Math.abs((AntennaA_position_x+4.194+AntennaB_position_x-1.11)/2); 
					 y=Math.abs((AntennaA_position_y-7.1628+AntennaB_position_x+3.26)/2);
				 }
				 if (power_level_A2==25){
					 x=Math.abs((AntennaA_position_x+4.194+AntennaB_position_x-2.201)/2); 
					 y=Math.abs((AntennaA_position_y-7.1628+AntennaB_position_x+5.87)/2);
				 }
				 if (power_level_A2==30){
					 x=Math.abs((AntennaA_position_x+4.194+AntennaB_position_x-4.194)/2); 
					 y=Math.abs((AntennaA_position_y-7.1628+AntennaB_position_x+10.80)/2); //7.1628
				 }
				 
				 
		/*	 	// THIS LOOP IS INCLUDED ON 05.20.2018 by Prem
				 if (power_level_A1==30){
					 if (power_level_A2==20){
						 x=Math.abs((AntennaA_position_x+4.194+AntennaB_position_x-1.11)/2); 
						 y=Math.abs((AntennaA_position_y-10.80+AntennaB_position_x+3.26)/2);
					 }
					 if (power_level_A2==25){
						 x=Math.abs((AntennaA_position_x+4.194+AntennaB_position_x-2.201)/2); 
						 y=Math.abs((AntennaA_position_y-10.80+AntennaB_position_x+5.87)/2);
					 }
					 if (power_level_A2==30){
						 x=Math.abs((AntennaA_position_x+4.194+AntennaB_position_x-4.194)/2); 
						 y=Math.abs((AntennaA_position_y-10.80+AntennaB_position_x+10.80)/2); //7.1628
					 }
			*/	 
			 } 
			
		 }
		 points[0]=x;
		 points[1]=y;
		return points;
		 
		
		}
	
	
	}