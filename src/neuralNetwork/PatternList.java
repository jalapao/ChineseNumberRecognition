package neuralNetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author bikewheat
 */
public class PatternList {
    //Pattern List
    private ArrayList<Pattern> list = new ArrayList<Pattern>() ;

    ///////////////////////////////////////////////////////////////////////////
    //---Constructor:
    public PatternList(){
        //mandatory empty
    }
    public PatternList(File f) throws IOException, ClassNotFoundException{
        reader(f) ;
    }
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    //---public method:
    public void reader(File f) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)) ;
        list = (ArrayList<Pattern>) ois.readObject() ;
        ois.close() ;
    }
    public void add(Pattern p){
        list.add(p);
    }
    public void add(double [] input,double [] output){
        list.add(new Pattern(input,output)) ;
    }
    public Pattern get(int index){
        return list.get(index) ;
    }
    public int size(){ return list.size(); }
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    //--IO method:
    public void writer(File f) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f)) ;
        oos.writeObject(list);
        oos.close();
    }
    public void writerHumanReaderable(File f) throws FileNotFoundException{
        PrintWriter wri = new PrintWriter(f);
        for (Pattern p : list) { wri.println(p); }
        wri.close();
    }
    ///////////////////////////////////////////////////////////////////////////
}
