package neuralNetwork;

import java.util.Iterator;

/**
 *
 * @author bikewheat
 */
public class MiddleNode extends OutputNode {

    //---Constructor Field:
    public MiddleNode(double learningRate,double momentum){
        super(learningRate,momentum) ;
    }

    ///////////////////////////////////////////////////////////////////////////
    //---public methods:
    /**
     * Update input weights based on error (delta rule)
     */
    public void trainNode(){
        error = computeError() ;
        
        Iterator<Arc> iter = inputArcs.iterator() ;
        while(iter.hasNext()){
            Arc arc = iter.next() ;
            double delta = learningRate * error * arc.getInputValue() ;
            arc.updateWeight(delta);
            
        }
    }
    
    public String toString(){
        return (toString("MiddleNode:")) ;
    }
    ///////////////////////////////////////////////////////////////////////////
    //---private methods:
    private double computeError(){
        double total = 0.0 ;

        Iterator<Arc> iter = outputArcs.iterator() ;
        while(iter.hasNext()){
            Arc arc = iter.next() ;
            total += arc.getWeightedOutputError() ;
        }

        double ret = value * ( 1.0 - value ) * total ;

        return ret ;
    }
    ///////////////////////////////////////////////////////////////////////////
}
