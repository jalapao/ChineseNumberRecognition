package neuralNetwork;

import java.io.Serializable;

/**
 *
 * @author bikewheat
 */
public class Pattern implements Serializable {
    private double [] input ;
    private double [] output ;
    private boolean trained = false ;

    //---Constructor Fields:
    public Pattern(double [] input , double [] output){
        this.input = input.clone() ;
        this.output = output.clone() ;
    }
    //---Get methods :
    public double [] getInput(){ return input ;}
    public double [] getOuptut(){ return output ;}
    //---Set method :
    public void setTrained(boolean b){ trained  = b ;}
    //--Is method:
    public boolean isTrained(){ return trained ;}
    //---toString:
    public String toString(){
        String ret = "Trained: "  + trained ;

        ret += "\n" ;
        //---Input:
        ret += "Input : \n" ;
        if(input == null){
            ret += "NULL" ;
        }else{
            for(int i=0;i!=input.length;++i){
                ret += input[i] + " " ;
            }
        }

        ret += "\n" ;

        //---Output:
        ret += "Output : \n" ;
        if(output == null){
            ret += "NULL" ;
        }else{
            for(int i=0;i!=output.length;++i){
                ret += output[i] + " " ;
            }
        }

        return ret ;
    }
}
