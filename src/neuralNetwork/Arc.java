package neuralNetwork;

/**
 *
 * @author bikewheat
 */
public class Arc extends AbstractArcNode {
    //AbstractNode which arc is coming from
    private AbstractNode in ;
    //AbstractNode which arc is going to
    private AbstractNode out ;
    //Previous weight change
    private double delta ;
    //weights are initialized to a random value
    private double weight = MathUtility.getBoundedRandom(-1.0, 1.0);

    ///////////////////////////////////////////////////////////////////////////
    //---Get methods :
    public double getInputValue(){
        return in.getValue() ;
    }
    public double getWeightedInputValue(){
        return ( in.getValue() * weight ) ;
    }
    public double getWeightedOutputError(){
        return ( out.getError() * weight) ;
    }
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    //---Set methods:
    public void setInputNode(AbstractNode node){
        in = node ;
    }
    public void setOutputNode(AbstractNode node){
        out = node ;
    }
    ///////////////////////////////////////////////////////////////////////////
    //---public methods:
    public void updateWeight(double d){
        OutputNode node = (OutputNode) out ;
        weight += d + node.getMomentum() * delta ;
        delta = d ;
    }

    /**
     * Return description of object
     *
     * @return description of object
     */
    public String toString() {
        String result = "Arc:" + id + " weight:" + weight + " delta:" + delta;

        if (in == null) {
            result += " in:null";
        } else {
            result += " in:" + in.getId();
        }

        if (out == null) {
            result += " out:null";
        } else {
            result += " out:" + out.getId();
        }

        return (result);
    }

    ///////////////////////////////////////////////////////////////////////////
}
