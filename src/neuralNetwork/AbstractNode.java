package neuralNetwork;

import java.util.ArrayList;

/**
 *
 * @author bikewheat
 */
public abstract class AbstractNode extends AbstractArcNode {
    //---Package Access member datas:
    double error ;
    double value ;
    ArrayList<Arc> inputArcs = new ArrayList<Arc>();
    ArrayList<Arc> outputArcs = new ArrayList<Arc>();

    //---Get methods :
    public double getError(){ return error ;}
    public double getValue(){ return value ;}
    //---Set methods:
    public void setValue(double v){ value = v ;}
    public void setError(double e){ error = e ;}
    //---public methods:
    public String toString() {
    	return(id + " error:" + error + " value:" + value +
                " input:" + inputArcs.size() +
                " output:" + outputArcs.size());
    }
    public void connect(AbstractNode dest, Arc arc){
        outputArcs.add(arc);
        dest.inputArcs.add(arc);

        arc.setInputNode(this) ;
        arc.setOutputNode(dest);
    }
}
