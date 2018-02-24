package AlgmImple;
import java.util.concurrent.ThreadLocalRandom;

public class checKRandom {

	 public static double x;
	 public static double y;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	/*	int minx=10;
		int miny =10;
		int maxx=20;
		int maxy=15;
		
		int randomNumx = ThreadLocalRandom.current().nextInt(minx+1, maxx + 1);
		int randomNumy = ThreadLocalRandom.current().nextInt(miny+1, maxy + 1);
		
		System.out.println("X = "+randomNumx);
		System.out.println("Y = "+randomNumy);
	*/	
		
	//	double x;
	//	double y;
		
		
		
		
		double minx = 0.974;
		double maxx = 2.074;
		double miny = 7.9392;
		double maxy = 8.8392;
		x = ThreadLocalRandom.current().nextDouble(minx, maxx + 1);
		y = ThreadLocalRandom.current().nextDouble(miny, maxy + 1);
		System.out.println("IN ANT 1 POWER = 10 and x ="+x+" and y ="+y+".");
		
		
	}

}
