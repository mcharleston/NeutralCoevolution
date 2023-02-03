package niches;
//import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * Created by Edward on 19/02/2016.
 */

public class NormalDistribution {

//    private static NormalDistribution snd = new NormalDistribution();
//
//    private double ux;
//    private double sigmax;
//    private double uy;
//    private double sigmay;
//    private double covxy;
//
//    public NormalDistribution(
//            double ux, double sigmax,
//            double uy, double sigmay,
//            double covxy) {
//        this.ux = ux;
//        this.sigmax = sigmax;
//        this.uy = uy;
//        this.sigmay = sigmay;
//        this.covxy = covxy;
//    }
//
//    public double[] sample(double xn, double yn) {
//        double varx = sigmax*sigmax;
//        double vary = sigmay*sigmay;
//
//        double x = ux + sigmax*xn;
//        double y = uy + ((covxy *xn) + Math.sqrt(varx*vary - covxy*covxy)*yn)/sigmax;
//
//        double[] point = new double[2];
//        point[0] = x;
//        point[1] = y;
//        return point;
//    }
//
//    public double[] sample() {
//        double xn = snd.sample();
//        double yn = snd.sample();
//        return sample(xn, yn);
//    }
}