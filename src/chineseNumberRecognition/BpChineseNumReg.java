package chineseNumberRecognition;

import java.io.File;
import java.io.IOException;
import neuralNetwork.BackPropogagtion;

/**
 *
 * @author bikewheat
 */
public class BpChineseNumReg extends BackPropogagtion {

    public BpChineseNumReg(int inputPopulatoin,int middlePopulation,
            int outputPopulation,double learningRate,double momentum){
        super(inputPopulatoin,middlePopulation,outputPopulation,learningRate,momentum) ;
    }

    public BpChineseNumReg(File f) throws IOException, ClassNotFoundException{
        super(f) ;
    }

}
