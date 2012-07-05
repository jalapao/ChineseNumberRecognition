package neuralNetwork;

import java.io.Serializable;

/**
 *
 * @author bikewheat
 */
public class AbstractArcNode implements Serializable {
    final int id = SequenceGenerator.getId() ;

    public int getId(){
        return id ;
    }
}
