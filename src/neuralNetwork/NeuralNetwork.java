package neuralNetwork;

import java.io.Serializable;

/**
 *
 * @author bikewheat
 */
public class NeuralNetwork implements Serializable {
    private final Arc[] arcz ;
    private final InputNode[] inputz ;
    private final MiddleNode [] middlez ;
    private final OutputNode[] outputz ;

    public NeuralNetwork(int inputPopulation,int middlePopulation,
            int outputPopulation,double learningRate,double momentum){
        //---alloc memory and init.
        inputz = new InputNode[inputPopulation] ;
        for(int i=0;i!=inputz.length;++i){
            inputz[i] = new InputNode() ;
        }

        middlez = new MiddleNode[middlePopulation] ;
        for(int i=0;i!=middlez.length;++i){
            middlez[i] = new MiddleNode(learningRate,momentum) ;
        }

        outputz = new OutputNode[outputPopulation] ;
        for(int i=0;i!=outputz.length;++i){
            outputz[i] = new OutputNode(learningRate,momentum) ;
        }

        int arczSize = (inputPopulation * middlePopulation) +
                    (middlePopulation * outputPopulation) ;
        arcz = new Arc[arczSize] ;
        for(int i=0;i!=arcz.length;++i){
            arcz[i] = new Arc() ;
        }
        ///////////////////////////////////////////////////////////////////////
        //---Connect nodes.
        int i=0;
        for(int j=0;j!=inputz.length;++j){
            for(int k=0;k!=middlez.length;++k){
                inputz[j].connect(middlez[k],arcz[i++]);
            }
        }

        for(int j=0;j!=middlez.length;++j){
            for(int k=0;k!=outputz.length;++k){
                middlez[j].connect(outputz[k], arcz[i++]);
            }
        }
        ///////////////////////////////////////////////////////////////////////
    }

    //---public methods:
    public double[] runNeuralNetwork(double [] input){

        for(int i=0;i!=input.length;++i){
            inputz[i].setValue(input[i]);
        }

        for(int i=0;i!=middlez.length;++i){
            middlez[i].runNode();
        }

        for(int i=0;i!=outputz.length;++i){
            outputz[i].runNode();
        }

        double [] ret = new double [outputz.length] ;
        for(int i=0;i!=ret.length;++i){
            ret[i] = outputz[i].getValue() ;
        }

        return ret ;
    }

    public double [] trainNeuralNetwork(double [] truth){

        for(int i=0;i!=truth.length;++i){
            outputz[i].setError(truth[i]);
        }

        for(int i=outputz.length-1;i>=0;--i){
            outputz[i].trainNode();
        }

        for(int i=middlez.length-1;i>=0;--i){
            middlez[i].trainNode();
        }

        double [] ret = new double[outputz.length] ;
        for(int i=0;i!=outputz.length;++i){
            ret[i] = outputz[i].getValue() ;
        }

        return ret ;
    }
}
