package neuralNetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author bikewheat
 */
public class BackPropogagtion {
    private NeuralNetwork network ;
    ///////////////////////////////////////////////////////////////////////////
    //---Constructor
    public BackPropogagtion(int inputPopulatoin,int middlePopulation,
            int outputPopulation,double learningRate,double momentum){
        network = new NeuralNetwork(inputPopulatoin,middlePopulation,
                outputPopulation,learningRate,momentum) ;
    }
    public BackPropogagtion(File f) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)) ;
        network = (NeuralNetwork) ois.readObject() ;
        ois.close();
    }
    ///////////////////////////////////////////////////////////////////////////
    //---public methods:
    public double [] runNeuralNetwork(double [] dvals){
        return network.runNeuralNetwork(dvals) ;
    }

    public int trainNeuralNetwork(PatternList patternz,int maxMatch,int maxCycle,
            double threshold,boolean print){

        int limit = patternz.size() ;

        if(maxMatch < 0 ){ maxMatch = limit; }

        int cnt=0;
        int success=0;
        int maxSuccess=0;

        boolean whileFlag ;

        do{
            success = 0 ;
            for(int i=0;i!=limit;++i){
                Pattern p = patternz.get(i) ;

                network.runNeuralNetwork(p.getInput()) ;
                double [] rawResult = network.trainNeuralNetwork(p.getOuptut()) ;

                int [] truth = MathUtility.thresholdArray(threshold, p.getOuptut()) ;
                int [] ret = MathUtility.thresholdArray(threshold, rawResult) ;

                p.setTrained(true);
                for(int j=0;j!=ret.length;++j){
                    if(ret[j] != truth[j]){
                        p.setTrained(false);
                    }
                }//end for j

                if(p.isTrained()){ ++success ;}
                //System.out.println("success: "+ success) ;
            }// end for i

            if(maxSuccess < success){ maxSuccess= success ;}
            if((++cnt % 10000) ==0){
                if(print){
                    System.out.println(cnt + " success:" + success +
                            " needed:" + patternz.size() +
                            " best run:" + maxSuccess) ;
                }
            }

            whileFlag = (success<limit) ? true : false ;

            if(maxCycle > -1){
                if(cnt >= maxCycle){
                    whileFlag = false ;
                }
            }

            if(success >= maxMatch){
                whileFlag = false ;
            }
        }while(whileFlag);

        if(print){
            System.out.println("Training complete in " + cnt + " cycles");
        }

        return success ;
    }

    public void saveNeuralNetwork(File f) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f)) ;
        oos.writeObject(network);
        oos.close() ;
    }

}
