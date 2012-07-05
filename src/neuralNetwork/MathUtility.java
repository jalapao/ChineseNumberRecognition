package neuralNetwork;

import java.util.Random;

/**
 * Math Utility support for this project. All static .
 * @author bikewheat
 */
public class MathUtility {
    private static final Random random = new Random() ;

    public static synchronized double getBoundedRandom(double lower,double upper){
        double range = upper - lower ;
        double ret = random.nextDouble() * range + lower ;
        return ret ;
    }

    public static synchronized int[] thresholdArray(double threshold, double[] candidatez) {
        double upperThreshold = 1.0 - threshold ;
        double lowerThreshold = threshold ;

        int [] ret = new int [candidatez.length] ;

        for(int i=0;i!=candidatez.length;++i){
            if(candidatez[i] > upperThreshold){
                ret[i] = 1 ;
            }else if(candidatez[i] < lowerThreshold){
                ret[i] = 0 ;
            }else{
                ret[i] = -1 ;
            }
        }

        return ret ;
    }
}
