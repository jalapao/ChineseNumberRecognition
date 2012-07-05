package chineseNumberRecognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import neuralNetwork.PatternList;

/**
 *
 * @author bikewheat
 */
public class TrainData {
    public static final double one = 0.9999999999;
    public static final double zero = 0.0000000001;
    private static final int dim = 8 ;
    private static final String TRAIN_FILENAME = "chineseNumberReg.trainingData";
    private static final String NETWORK_FILENAME = "chineseNumberReg.network";
    private BpChineseNumReg bp;
    private PatternList pl;

    ////////////////////////////////////////////////////////////////////////////
    //---Constructor:
    public TrainData() {
        bp = new BpChineseNumReg( dim*dim, 5, 10, 0.45, 0.9);
    }
    ////////////////////////////////////////////////////////////////////////////
    //---public methods:
    public int loadTraining(File f) 
            throws IOException, FileNotFoundException, ClassNotFoundException{
        pl = new PatternList()  ;
        pl.reader(f) ;
        return pl.size() ;
    }
    public void performTraining() {
        bp.trainNeuralNetwork(pl, (int) (pl.size() * 0.9), -1, 0.15, true);
    }
    public void saveTraining(File f) throws IOException, FileNotFoundException {
        bp.saveNeuralNetwork(f);
    }
    ////////////////////////////////////////////////////////////////////////////
    public static void main(String [] args) throws Exception{
        System.out.println("===TrainData BEGIN===");
        TrainData tr = new TrainData();
        int population = tr.loadTraining(new File(TRAIN_FILENAME));
        System.out.println("PatternList loaded w/" + population + " patterns");
        tr.performTraining();
        tr.saveTraining(new File(NETWORK_FILENAME));
        System.out.println("===TrainData  END ===");
    }

}
