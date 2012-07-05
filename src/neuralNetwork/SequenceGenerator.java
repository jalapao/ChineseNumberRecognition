package neuralNetwork;

public class SequenceGenerator {

    /**
     * Contains the next Arc/Node identifier.
     */
    private static int id = 1;
    /**
     * Each arc/node has a unique identifer. 
     * This was to help w/development, but it doesn't hurt to retain.
     * 
     * @return unique identifier
     */
    public static synchronized int getId() {
    	return(id++);
    }
}