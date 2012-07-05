package neuralNetwork;

import java.util.Iterator;

/**
 *
 * @author bikewheat
 */
public class OutputNode extends AbstractNode {
    double learningRate ;
    double momentum ;

    //---Constructor:
    public OutputNode(double learningRate,double momentum){
        this.learningRate = learningRate ;
        this.momentum = momentum ;
    }
    //---Get methods:
    public double getLearningRate(){ return learningRate; }
    public double getMomentum(){ return momentum ; }
    //---public methods:
    public void runNode(){
        double total = 0.0 ;
        Iterator<Arc> iter = inputArcs.iterator() ;

        while(iter.hasNext()){
            Arc arc = iter.next() ;
            total += arc.getWeightedInputValue() ;
        }
        value = sigmoidFunc(total) ;
    }
    public void trainNode(){
        error = computeError() ;

        Iterator<Arc> iter = inputArcs.iterator() ;
        while(iter.hasNext()){
            Arc arc = iter.next() ;
            double delta = learningRate * error * arc.getInputValue() ;
            arc.updateWeight(delta) ;
        }
    }
    public String toString() {
    	return(toString("OutputNode:"));
    }
    public String toString(String prefix) {
    	String ret = prefix + super.toString() +
                " learning rate:" + learningRate +
                " momentum:" + momentum;
    	return ret;
    }
    //---private methods:
    private double sigmoidFunc(double v){
        return ( 1.0 / (1.0 + Math.exp(-v))) ;
    }
    private double computeError(){
        return(value * (1.0 - value) * (error - value));
    }
}
