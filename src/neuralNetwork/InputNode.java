package neuralNetwork;

/**
 *
 * @author bikewheat
 */
public class InputNode extends AbstractNode {

    public void setValue(double d){
        if( (d>=0.0) && (d<=1.0)){
            value = d ;
        } else{
            throw new IllegalArgumentException("bad input value : d == " + d );
        }
    }
    public String toString(){
        return ("InputNode: "+super.toString()) ;
    }
}
