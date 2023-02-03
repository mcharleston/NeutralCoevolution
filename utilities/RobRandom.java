package utilities;
import java.lang.Math;
import java.util.Random;

/**
 * This generates random numbers of different forms 
 *
 * @author Rob Freckleton & Phil Stephens
 * @version 1.0
 */



public class RobRandom {
	private static Random rand;
	private static boolean isInitialised = false;

	/**
	 * returns a uniformly distributed random double betweem 0 & 1
	 *
	 * @param none
	 * @return <tt>double random uniform<tt>
	 * @author Rob Freckleton
	 */
	public static double randomUniform() {
//		if (rand == null){
//			rand = new Random();
//			rand.setSeed(666);
//		}
		double r = rand.nextDouble();
		return r;
	}
	
	/**
	 * returns a uniformly distributed random integer betweem 0 & 1
	 *
	 * @param min, max
	 * @return <tt>int random uniform<tt>
	 * @author Rob Freckleton
	 */
	public static double randomUniform(int min, int max) {
		double r = (max - min) * randomUniform() + min;
		return Math.round(r);
	}
	
	/**
	 * returns a normally distributed random number with mean zero
	 * and sd of 1
	 *
	 * @param none
	 * @return <tt>double z distribution<tt>
	 * @author Rob Freckleton
	 */
	public static double randomStdNorm() {
		double num = 0;
		for(int i = 0; i < 48; i++) {
			num += randomUniform();
		}
		return (num - 24) / 2.0;
	}
	
	/**
	 * returns a normally distributed random number with given mean
	 * and sd 
	 *
	 * @param none
	 * @return <tt>double z distribution<tt>
	 * @author Rob Freckleton
	 */
	public static double randomNorm(double mean, double sd) {
		return randomStdNorm() * sd + mean;
	}
	
	/**
	 * returns a log-normally distributed random number with given GM 
	 * and GMsd
	 *
	 * @param none
	 * @return <tt>double log-normal dist<tt>
	 * @author Rob Freckleton
	 */
	public static double randomLogNorm(double GM, double GMsd) {
		return Math.exp(randomNorm(GM, GMsd * GMsd));
	}
	
	/**
	 * returns a Poisson-distributed random number with given mean
	 *
	 * @param <tt> Mean <tt>
	 * @return <tt> Poisson distributed random variate <tt>
	 */
	public static double randomPoisson(double mean) {
		int x = 0;
		double t = 0.0;
		for (;;) {
			t = t - Math.log( randomUniform() ) / mean;
			if (t > 1.0)
				break;
			x = x + 1;
			}
		return x;
	}
	
	/**
	 * returns an exponentially-distributed random number with given mean
	 *
	 * @param <tt> Mean <tt>
	 * @return <tt> Exponentially distributed random variate <tt>
	 */	
	public static double randomExponential(double mean) {
		double num = randomUniform();
		return(-1.0 * mean * Math.log(num));
	}

	/**
	 * Default constructor
	 *
	 * @param none
	 * @return new RobRandom object
	 * @author Rob Freckleton
	 */
	public RobRandom() {
//	rand = new Random();
//	rand.setSeed(4);
		if (!RobRandom.isInitialised){
			RobRandom.isInitialised = true;
			rand = new Random();
			rand.setSeed(1);
		}
	}

	public static double randDouble(){
		return rand.nextDouble();
	}

	public static int randInt(int limit){
		return rand.nextInt(limit);
	}
}