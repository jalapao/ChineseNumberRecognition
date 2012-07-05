package chineseNumberRecognition;

import bikewheatFileIO.MyFilefilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import neuralNetwork.PatternList;

/**
 *
 * @author bikewheat
 */
public class GenerateDataFromPNG {
    private static final double one = 0.9999999999;
    private static final double zero = 0.0000000001;

    private static final String TRAIN_FILENAME = "chineseNumberReg.trainingData";
    private static final String FILE_PATH = "src/trainingImg/";
    private static final String [] DIR_NAMES = {
        "0","1","2","3","4","5","6","7","8","9"} ;

    /*
     * Read 128*128 image as Pattern Object.
     */
    public void createTrainingSet() throws Exception {

        PatternList pl = new PatternList() ;

        double [] input = null ;
        double [] output = new double [10] ;//Output dimension = 10. [0..9]

        for(int i=0;i!=output.length;++i){
            output[i] = zero ;
        }

        for (int i = 0; i != DIR_NAMES.length; ++i) {
            File dir = new File(FILE_PATH + DIR_NAMES[i]);
            System.out.println("Class : " + i) ; //print Class.
            for (File f : dir.listFiles(new MyFilefilter("png"))) {
                System.out.print(f.getName() + " "); //Print file name.

                input = patternReader(f) ;
                //---Assign Output:
                if(i==0){
                    output[i] = one ;
                }else{
                    output[i] = one ;
                    output[i-1] = zero ;
                }
                //System.out.println(Arrays.toString(output)) ;

                pl.add(input,output);
            }
            System.out.println();
        }

        pl.writer(new File(TRAIN_FILENAME));
    }
    public static void main(String [] args) throws Exception{
        System.out.println("===GenerateData BEGIN===");

        GenerateDataFromPNG gd = new GenerateDataFromPNG() ;

        gd.createTrainingSet() ;

        System.out.println("===GenerateData  END ===");
    }
    private double[] patternReader(File f) throws IOException{

        BufferedImage bi = ImageIO.read(f);
        int[] rgb = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());

        double[] ret = new double[rgb.length];
        //change from int to double.
        for (int i = 0; i != ret.length; ++i) {
            // if(rgb[i]==-1) ,meaning this pixel is WHITE color .
            ret[i] = (rgb[i] == -1)?zero:one;
        }
        return ret;
    }

}
