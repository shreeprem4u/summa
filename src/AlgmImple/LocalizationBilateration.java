package AlgmImple;
import java.beans.FeatureDescriptor;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

//import org.apache.poi.hslf.util.SystemTimeUtils;

public class LocalizationBilateration {
	static int RSSI_Antenna_A;
	static int RSSI_Antenna_B;
	static int  power_level_A1;
	static int  power_level_A2;
	static double AntennaA_position_x;
	static double AntennaA_position_y;
	static double AntennaB_position_x;
	static double AntennaB_position_y;
	public LocalizationBilateration(int[] values_retrieve, double[] Antenna_XY_positions) {
		// TODO Auto-generated constructor stub
		 RSSI_Antenna_A=values_retrieve[0];
       	 RSSI_Antenna_B=values_retrieve[2];			     
		 power_level_A1=values_retrieve[1]; 
         power_level_A2=values_retrieve[3];
         
         
         AntennaA_position_x=Antenna_XY_positions[0];
         AntennaA_position_y=Antenna_XY_positions[1];
         AntennaB_position_x=Antenna_XY_positions[2];
         AntennaB_position_y=Antenna_XY_positions[3];
	}
	static double[] FunctionList()
	{	
	double XY_cordinates[]=new double[2];

   double distance1 = LogDistance(RSSI_Antenna_A);
   
   
   System.out.print("RSSI_A="+RSSI_Antenna_A);
   System.out.print("\tPOWER_A="+power_level_A1);
   System.out.print("\tRSSI_B="+RSSI_Antenna_B);
   System.out.print("\tPOWER_B="+power_level_A2);
   
   
 //  System.out.println("ANT_A_x="+AntennaA_position_x);
 //  System.out.println("ANT_A_y="+AntennaA_position_y);
 //  System.out.println("ANT_B_x="+AntennaB_position_x);
 //  System.out.println("ANT_B_y="+AntennaB_position_y);
   
   
   
   double distance2= LogDistance(RSSI_Antenna_B);
  ;
//	System.out.println("---------");
   double Ma_A1=EstimateMajorAxis(power_level_A1,distance1);
   double ma_A1=EstimateMinorAxis(power_level_A1, distance1);
	//System.out.println("---------");
   double Ma_A2=EstimateMajorAxis(power_level_A2,distance2);
   double ma_A2=EstimateMinorAxis(power_level_A2, distance2);
//  System.out.println("---------");
  //System.out.println(Ma_A1);
 //System.out.println(ma_A1);
 //System.out.println(Ma_A2);
 //System.out.println(ma_A2);
   double x_sum=0.0,y_sum=0.0;
   
    double coefficients_A1[]=EllipseEquationSolver(AntennaA_position_x, AntennaA_position_y,Ma_A1, ma_A1);
    double coefficients_A2[]=EllipseEquationSolver(AntennaB_position_x, AntennaB_position_y,Ma_A2, ma_A2);
   
    if(coefficients_A1[0]==0.0 && coefficients_A1[1]==0.0 && coefficients_A1[2]==0.0 && coefficients_A1[3]==0.0 && coefficients_A1[4]==0.0 && coefficients_A1[5]==0.0)
    	{
    //	System.out.println("detected by antenna 2 only");
    	coefficients_A1[0]=coefficients_A1[1]=coefficients_A1[2]=coefficients_A1[3]=coefficients_A1[4]=coefficients_A1[5]=1.0;
    	double coefficients_FerrariMethod[]=CoeffiecientGenerator_FerrariMethod(coefficients_A1,coefficients_A2);
    	double x_roots[]=Bilateration_x_coordinate(coefficients_FerrariMethod);
    	double y_roots[]=Bilateration_y_coordinate(x_roots, coefficients_FerrariMethod);
    	
    	for(int i=0;i<x_roots.length;i++) 
        x_sum=x_sum+x_roots[i];
        for(int j=0;j<x_roots.length;j++)
		y_sum=y_sum+y_roots[j];	
    
        XY_cordinates[0]=Math.abs(x_sum/x_roots.length);
        XY_cordinates[1]=Math.abs(y_sum/y_roots.length);
    	}
    else if(coefficients_A2[0]==0.0 && coefficients_A2[1]==0.0 && coefficients_A2[2]==0.0 && coefficients_A2[3]==0.0 && coefficients_A2[4]==0.0 && coefficients_A2[5]==0.0)
    {
    //	System.out.println("detected by antenna 1 only");
    	coefficients_A2[0]=coefficients_A2[1]=coefficients_A2[2]=coefficients_A2[3]=coefficients_A2[4]=coefficients_A2[5]=1.0;
    	double coefficients_FerrariMethod[]=CoeffiecientGenerator_FerrariMethod(coefficients_A1,coefficients_A2);
    	double x_roots[]=Bilateration_x_coordinate(coefficients_FerrariMethod);
    	double y_roots[]=Bilateration_y_coordinate(x_roots, coefficients_FerrariMethod);
    
        for(int i=0;i<x_roots.length;i++)
		x_sum=x_sum+x_roots[i];
        for(int j=0;j<x_roots.length;j++)
		y_sum=y_sum+y_roots[j];	
	
        XY_cordinates[0]=Math.abs(x_sum/x_roots.length);
        XY_cordinates[1]=Math.abs(y_sum/y_roots.length);
    }
    else
    {
    //	System.out.println("detected by both antennas");
    	double coefficients_FerrariMethod[]=CoeffiecientGenerator_FerrariMethod(coefficients_A1,coefficients_A2);
    	double x_roots[]=Bilateration_x_coordinate(coefficients_FerrariMethod);
    	double y_roots[]=Bilateration_y_coordinate(x_roots, coefficients_FerrariMethod);
	
    	for(int i=0;i<x_roots.length;i++)
		x_sum=x_sum+x_roots[i];
    	for(int j=0;j<x_roots.length;j++)
		y_sum=y_sum+y_roots[j];	
	
    	XY_cordinates[0]=Math.abs(x_sum/x_roots.length);
    	XY_cordinates[1]=Math.abs(y_sum/y_roots.length);
    }
	
	//System.out.println("x===="+XY_cordinates[0]+ "y===== "+ XY_cordinates[1]);
	return XY_cordinates;
}
	static double LogDistance(int RSSI){
		int x,y,z;
		double distance =Math.pow(10,(-53-RSSI)/20.0);
		//System.out.println("distance="+distance);
	
	     return distance;
		}
	static double EstimateMajorAxis(int pw, double distance){
		double Ma=0.0;
/*		if(pw==0)
			return 0.0;
		if(pw==10)
			Ma=(0.9/10.8);
		else if (pw == 15)
			Ma=(1.87/10.8);
		else if (pw == 20)
			Ma=(3.26/10.8);
		else if (pw == 25)
			Ma=(5.87/10.8);
		else if (pw == 30)
			Ma=(10.8/10.8);
	
		double MajorAxis=distance*(12.0/11.2)*Ma; //*(2*Ma);
	*/
		//System.out.println("Major Axis="+MajorAxis);
	//	return MajorAxis;
		
		
		
		//Change 0n 05.02.2018 by Prem
		
		if(pw==0)
			return 0.0;
		if(pw==10)
			Ma=0.9;
		else if (pw == 15)
			Ma=1.87;
		else if (pw == 20)
			Ma=3.26;
		else if (pw == 25)
			Ma=5.87;
		else if (pw == 30)
			Ma=10.8;
		
		
		
		
		
		return Ma;
		
		
	}
	static double EstimateMinorAxis(int pw, double distance){
		double ma=0.0;
	/*	if(pw==0)
			return 0.0;
		if(pw==10 || pw==10)
			ma=(0.56/4.6);
		else if (pw == 15 || pw == 15)
			ma=(1.12/4.6);
		else if (pw == 20 || pw == 20)
			ma=(1.05/4.6);
		else if (pw == 25 || pw == 25)
			ma=(2.75/4.6);
		else if (pw == 30 || pw == 30)
			ma=(4.6/4.6);
		double MinorAxis=distance*(2.6/8.85)*ma;//*(ma*2);
	*/  //  System.out.println("MinorAxis="+MinorAxis);
	//	return MinorAxis;
		
		
		//Change 0n 05.02.2018 by Prem
		
		if(pw==0)
			return 0.0;
		if(pw==10 || pw==10)
			ma=0.55;
		else if (pw == 15)
			ma=0.70;
		else if (pw == 20)
			ma=1.00;
		else if (pw == 25)
			ma=2.60;
		else if (pw == 30)
			ma=4.70;
		
		
		
				return ma;
		
		
		
	}
	static double[] EllipseEquationSolver(double Antenna1_x,double Antenna1_y,double MajorAxis,double MinorAxis){
		double coefficients_store[]=new double[6];
	double A= MinorAxis*MinorAxis;
	double B=0.0;
	double C= MajorAxis*MajorAxis;
	double D=2.0*MinorAxis*MinorAxis*Antenna1_x;
	//System.out.println(MinorAxis+" "+MajorAxis+" "+Antenna1_y);
	double E=2.0*MajorAxis*MajorAxis*Antenna1_y*0.1;
	//System.out.println(E);
	double F=((MinorAxis*MinorAxis*Antenna1_x*Antenna1_x)+(MajorAxis*MajorAxis*Antenna1_y*Antenna1_y)-(MajorAxis*MajorAxis*MinorAxis*MinorAxis)/Math.pow(10, 2))*0.01;
	
		coefficients_store[0]=coeffreduce(A);
		coefficients_store[1]=coeffreduce(B);
		coefficients_store[2]=coeffreduce(C);
		coefficients_store[3]=coeffreduce(D);
		coefficients_store[4]=coeffreduce(E);
		coefficients_store[5]=coeffreduce(F);
	//System.out.println("////////////////////");
	//System.out.println(coefficients_store[0]+" "+coefficients_store[1]+" "+coefficients_store[2]+" "+coefficients_store[3]+" "+coefficients_store[4]+" "+coefficients_store[5]);
	return 	coefficients_store;
	}
	
	static double[] CoeffiecientGenerator_FerrariMethod(double[] coefficients_EllipseEquation_A1, double[] coefficients_EllipseEquation_A2){
		double a,b,c,d,e,f;
		double A,B,C,D,E,F;
		double coefficients_FerrariMethod[]= new double[17];
		a=coefficients_EllipseEquation_A1[0];
		b=coefficients_EllipseEquation_A1[1];
		c=coefficients_EllipseEquation_A1[2];
		d=coefficients_EllipseEquation_A1[3];
		e=coefficients_EllipseEquation_A1[4];
		f=coefficients_EllipseEquation_A1[5];
		//System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
		//System.out.println(a+" "+b+" "+c+" "+d+" "+e+" "+f);
		A=coefficients_EllipseEquation_A2[0];
		B=coefficients_EllipseEquation_A2[1];
		C=coefficients_EllipseEquation_A2[2];
		D=coefficients_EllipseEquation_A2[3];
		E=coefficients_EllipseEquation_A2[4];
		F=coefficients_EllipseEquation_A2[5];
		//System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
		//System.out.println(A+" "+B+" "+C+" "+D+" "+E+" "+F);
		double	coeff_x4=(A*A*c*c*c*c)-(2.0*c*C*a*A)+(C*C*C*C*a*a);//-(c*b*B*A)+(a*B*B*c)+(A*b*b*C)-(a*b*B*C);
		double  coeff_x3=(2.0*A*c*c*c*c*D)-(2.0*c*C*a*D)-(2.0*c*C*d*A)+(2.0*C*C*C*C*a*d);//-(b*B*D*c)-(b*E*A*c)-(e*B*A*c)+(2*B*E*a*c)+(d*B*B*c)+(D*b*b*C)+(2*b*e*A*C)-(a*b*E*C)-(a*e*B*C)-(b*B*d*C);
		double	coeff_x2=(2.0*c*c*F*A*c*c)+(c*c*c*c*D*D)-(2.0*c*C*a*F)-(2.0*c*C*d*D)+(2.0*C*C*f*C*C*a)+(C*C*C*C*d*d)-(2.0*c*C*f*A)-(e*E*A*c)+(a*E*E*c)+(e*e*A*C)-(a*E*e*C);//-(b*B*F*c)-(b*E*D*c)-(e*B*D*c)+(2*B*E*d*c)+(B*B*f*c)+(F*b*b*C)+(2*b*e*D*C)-(b*E*d*C)-(e*B*d*C)-(b*B*f*C);
		double	coeff_x1=(2.0*c*c*F*c*c*D)-(2.0*c*C*d*F)-(2.0*c*C*D*f)+(2.0*C*C*f*C*C*d)-(e*E*D*c)+(E*E*d*c)+(e*e*D*C)-(E*e*d*C);//-(b*E*F*c)-(e*B*F*c)+(2*B*E*f*c)+(2*b*e*F*C)-(b*E*f*C)-(e*B*f*C);
		double	coeff_x0=(c*c*c*c*F*F)-(2.0*c*C*f*F)+(C*C*C*C*f*f)-(e*E*F*c)+(E*E*f*c)+(e*e*F*C)-(E*e*f*C);
		double x=0.0;
		x=coefficient_reduction(coeff_x4);
		coeff_x4=Math.abs((coeff_x4)/Math.pow(10.0, x));
		x=coefficient_reduction(coeff_x3);
	    coeff_x3=Math.abs(((coeff_x3)/Math.pow(10,x)));
	    x=coefficient_reduction(coeff_x2);
		coeff_x2=Math.abs(((coeff_x2)/Math.pow(10,x)));
		x=coefficient_reduction(coeff_x1);
		coeff_x1=Math.abs(((coeff_x1)/Math.pow(10,x)));
		x=coefficient_reduction(coeff_x0);
	    coeff_x0=Math.abs(((coeff_x0)/Math.pow(10,x)));
	   	//System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;");
	   // System.out.println(coeff_x4+" "+coeff_x3+" "+coeff_x2+" "+coeff_x1+" "+coeff_x0);
		coefficients_FerrariMethod[0]=coeff_x4;
		coefficients_FerrariMethod[1]=coeff_x3;
		coefficients_FerrariMethod[2]=coeff_x2;
		coefficients_FerrariMethod[3]=coeff_x1;
		coefficients_FerrariMethod[4]=coeff_x0;
		coefficients_FerrariMethod[5]=a;
		coefficients_FerrariMethod[6]=b;
		coefficients_FerrariMethod[7]=c;
		coefficients_FerrariMethod[8]=d;
		coefficients_FerrariMethod[9]=e;
		coefficients_FerrariMethod[10]=f;
		coefficients_FerrariMethod[11]=A;
		coefficients_FerrariMethod[12]=B;
		coefficients_FerrariMethod[13]=C;
		coefficients_FerrariMethod[14]=D;
		coefficients_FerrariMethod[15]=E;
		coefficients_FerrariMethod[16]=F;
		
	return coefficients_FerrariMethod;
	}
	static double[] Bilateration_x_coordinate(double[] coefficient_FerrariMethod){
		
		//float d=B_x;
		//double tag_position_x=((Math.pow(dist1, 2))-(Math.pow(dist2, 2))+(Math.pow(d, 2)))/(2*d);
		//double y=((4*(Math.pow(d,2))*(Math.pow(dist1, 2)))-(Math.pow(((Math.pow(d, 2))-(Math.pow(dist2, 2))+(Math.pow(dist1, 2))), 2)))/(4*d*d);
		//double tag_position_y=Math.sqrt(Math.abs(y));
	//	System.out.println(tag_position_x);
	//	System.out.println(tag_position_y);
		double x_root[] = new double[4];
		double a=coefficient_FerrariMethod[0];
		double b=coefficient_FerrariMethod[1];
		double c=coefficient_FerrariMethod[2];
		double d=coefficient_FerrariMethod[3];
		double e=coefficient_FerrariMethod[4];
	    double delta0=Math.round((Math.abs((c*c)-(3.0*b*d)+(12.0*a*e)))*1000.0)/1000.0;
	    double delta1= Math.round((Math.abs((2.0*c*c*c)-(9.0*b*c*d)+(27.0*b*b*e)+(27.0*a*d*d)-(72.0*a*c*e)))*1000.0)/1000.0;
	    double Q=Math.cbrt((delta1+(Math.sqrt(Math.abs((delta1*delta1)-(4.0*delta0*delta0*delta0)))))/2.0);
	    double p=((8.0*a*c)-(3.0*b*b))/(8.0*a*a);
	    double q=((b*b*b)-(4.0*a*b*c)+(8.0*a*a*d))/(8.0*a*a*a);
	 	double S=0.5*(Math.sqrt(Math.abs((-2.0/3.0*p)+(1.0/3.0*a*(Q+(delta0/Q))))));
	 	double x1=((-b/4.0*a)-S+(0.5*Math.sqrt(Math.abs((4.0*S*S)-(2.0*p)+(q/S)))));
		double x2=((-b/4.0*a)-S-(0.5*Math.sqrt(Math.abs((4.0*S*S)-(2.0*p)+(q/S)))));
		double x3=((-b/4.0*a)+S+(0.5*Math.sqrt(Math.abs((4.0*S*S)-(2.0*p)-(q/S)))));
		double x4=((-b/4.0*a)+S-(0.5*Math.sqrt(Math.abs((4.0*S*S)-(2.0*p)-(q/S)))));
		//System.out.println(a+" "+b+" "+c+" "+d+" "+e);
		//System.out.println("--------------------------");
		//System.out.println(delta0);
		//System.out.println(delta1);
		//System.out.println(Q+" "+p+" "+q+ " "+S);
		
	//System.out.println("///////////////////////");
		x_root[0]=Math.abs(x1);
		x_root[1]=Math.abs(x2);
		x_root[2]=Math.abs(x3);
		x_root[3]=Math.abs(x4);
		//System.out.println("///////////////////////");
		//System.out.println(x_root[0]+" "+x_root[1]+" "+x_root[2]+" "+x_root[3]);
		//x1=(-a/4)-((0.5*((a*a/4)-(2*b/3)+((Math.cbrt(2)*((b*b)-(3*a*c)+(12*d)))/3*Math.cbrt((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)+Math.sqrt((-4*Math.pow(((b*b)-(3*a*c)+(12*d)),3)+Math.pow(((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)),2))))+(((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)+Math.sqrt((-4*Math.pow(((b*b)-(3*a*c)+(12*d)),3))+Math.pow(((2*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)), 2))))/54)))-(0.5*Math.sqrt((a*a/2)-(4*b/3)-((Math.cbrt(2)*((b*b)-(3*a*c)+(12*d)))/Math.cbrt(3*((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)+Math.sqrt((-4*Math.pow(((b*b)-(-3*a*c)+(12*d)),3))+Math.pow(((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)),2)))))-(0.5*Math.sqrt((a*a/2)-(4*b/3)-((Math.cbrt(2)*((b*b)-(3*a*c)+(12*d)))/3*Math.cbrt((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)+Math.sqrt((-4*Math.pow(((b*b)-(3*a*c)+(12*d)), 3))+Math.pow(((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)),2))))-((Math.sqrt((-4*Math.pow(((b*b)-(3*a*c)+(12*d)), 3))+Math.pow(((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)),2)))/54)-(((-a*a*a)+(4*a*b)-(8*c))/4*Math.sqrt((a*a/4)-(2*b/3)+(((Math.cbrt(2)*((b*b)-(3*a*c)+(12*d)))/3*Math.cbrt((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)+Math.sqrt((-4*Math.pow(((b*b)-(3*a*c)+(12*d)),3)+Math.pow(((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)),2))))+(((2*b*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)+Math.sqrt((-4*Math.pow(((b*b)-(3*a*c)+(12*d)),3))+Math.pow(((2*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)), 2))))/54))+((Math.sqrt((-4*Math.pow(((b*b)-(3*a*c)+(12*d)),3))+Math.pow(((2*b*b)-(9*a*b*c)+(27*c*c)+(27*a*a*d)-(72*b*d)), 2)))/54))))))));
	//	System.out.println(a+" "+b+" "+c+" "+d+" "+e);
	//	System.out.println(delta0+" "+delta1+" "+Q+" "+p+" "+q+" "+S);
	//	System.out.println(x_root[0]+" "+x_root[1]+" "+x_root[2]+" "+x_root[3]);
		return x_root;
	}
	static double[] Bilateration_y_coordinate(double[] x_root, double[] coefficient_FerrariMethod){
	double y_root[]=new double[8];
	
	for(int i=0,j=0;i<x_root.length;i++,j=j+2){
		//	System.out.println(i);
	double 	ceff_y2=coefficient_FerrariMethod[7];
	double  ceff_y1=(coefficient_FerrariMethod[6]*x_root[i])+coefficient_FerrariMethod[16];
	double  ceff_y0=(coefficient_FerrariMethod[11]*x_root[i]*x_root[i])+(coefficient_FerrariMethod[14]*x_root[i])+coefficient_FerrariMethod[16];
	double determinant = ceff_y1 * ceff_y1 - (4.0 * ceff_y2 * ceff_y0);
	double y1=(-ceff_y1 + Math.sqrt(Math.abs(determinant))) / (2.0 * ceff_y2);
	double y2=(-ceff_y1 - Math.sqrt(Math.abs(determinant))) / (2.0 * ceff_y2);
	//System.out.println(" "+ceff_y2+" "+ ceff_y1+ " "+ ceff_y0+" "+ determinant);
		y_root[j]=Math.abs(y1);
		y_root[j+1]=Math.abs(y2);		
//	System.out.println(i);
		//System.out.println(ceff_y1+" "+ceff_y2+ " "+ceff_y0);
	
		}
		
		//for(int i=0;i<y_root.length;i=i+2)
		//System.out.print(y_root[i]+" "+y_root[i+1]+"\n");
	return y_root;
	}
	static double coefficient_reduction(double coeff_x4){
		
		double x=0.0;
		if(coeff_x4>0.00000001 && coeff_x4 <= 0.0000001)
			 x=-8.0;
		if(coeff_x4>0.0000001 && coeff_x4 <= 0.000001)
			 x=-7.0;
		if(coeff_x4>0.000001 && coeff_x4 <= 0.00001)
			 x=-6.0;
		if(coeff_x4>0.00001 && coeff_x4 <= 0.0001)
			 x=-5.0;
		if(coeff_x4>0.0001 && coeff_x4 <= 0.001)
			 x=-4.0;
		if(coeff_x4>0.001 && coeff_x4 <= 0.01)
			 x=-3.0;
		if(coeff_x4>0.01 && coeff_x4 <= 0.1)
			 x=-2.0;
		if(coeff_x4>0.1 && coeff_x4 <= 0.0)
			 x=-1.0;
		if(coeff_x4> 10 && coeff_x4 <= 100)
				x=1.0;
		if(coeff_x4> 100 && coeff_x4 <=1000)
			x=2.0;
		else if(coeff_x4> 1000 && coeff_x4 <= 10000)
			x=3.0;
		else if(coeff_x4> 10000 && coeff_x4<= 100000)
			x=4.0;
		else if(coeff_x4> 100000 && coeff_x4<= 1000000)
			x=5.0;
		else if(coeff_x4> 1000000 && coeff_x4 <= 10000000 )
			x=6.0;
		else if(coeff_x4> 10000000)
			x=7.0;
		else if(coeff_x4> 100000000)
			x=8.0;
	return x;	
	}
	static double coeffreduce(double x){
		if(x > 10)
			 return coeffreduce(x/2.0);
			else
				return x;
	}
}