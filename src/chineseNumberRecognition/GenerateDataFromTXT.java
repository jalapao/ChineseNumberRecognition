package chineseNumberRecognition;

import bikewheatFileIO.MyFilefilter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import neuralNetwork.PatternList;

public class GenerateDataFromTXT {

    private static final double one = 0.9999999999;
    private static final double zero = 0.0000000001;
    private static final int dim = 8 ; //width==hight==dim==8
    private static final String TRAIN_FILENAME = "chineseNumberReg.trainingData";
    private static final String FILE_PATH = "src/trainingImg/";
    private static final String[] DIR_NAMES = {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * Read and return the ASCII pattern files as Pattern objects.
     */
    public void createTrainingSet() throws Exception {
        PatternList pl = new PatternList();

        double input[] = null;
        double output[] = new double[10];//Output dimension = 10. [0..9]

        for (int i = 0; i != output.length; ++i) {
            output[i] = zero;
        }

        for (int i = 0; i != DIR_NAMES.length; ++i) {
            File dir = new File(FILE_PATH + DIR_NAMES[i]);
            System.out.println("Class : " + i); //print Class.
            for (File f : dir.listFiles(new MyFilefilter("txt"))) {
                System.out.println(f.getName()); //Print file name.

                input = patternReader(f);
                //---Assign Output:
                if (i == 0) {
                    output[i] = one;
                } else {
                    output[i] = one;
                    output[i - 1] = zero;
                }
                //System.out.println(Arrays.toString(output)) ;

                pl.add(input, output);
            }
            System.out.println();
        }
        pl.writer(new File(TRAIN_FILENAME));
    }

    /**
     * Convert the ASCII file do an array of double suitable for input neurons
     *
     * @param file_name
     *            of file to read
     * @return file contents as array of double
     */
    private double[] patternReader(File f) {
        String rawPattern = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            String inbuffer;
            while ((inbuffer = br.readLine()) != null) {
                if (rawPattern == null) {
                    rawPattern = inbuffer;
                }else if(inbuffer == ""){
                    rawPattern += "        " ;
                }else if (inbuffer == "\t") {
                    rawPattern += "        ";
                }else {
                    rawPattern += inbuffer;
                }
            }

            br.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        if (rawPattern.length() != dim*dim) {
            throw new IllegalArgumentException("bad pattern length: "+rawPattern.length());
        }

        byte[] temp = rawPattern.getBytes();

        double[] result = new double[dim*dim];
        for (int i = 0; i < dim*dim; ++i) {
            result[i] = (temp[i] == 'O' || temp[i] == 'o') ? zero : one;
        }
        chkResult(result) ;
        return (result);
    }

    private void chkResult(double [] results){
        for(int i=0;i!=dim;++i){
            for(int j=0;j!=dim;++j){
                char ch = (results[i*dim+j] == one)?'*':' ' ;
                System.out.print(ch);
            }
            System.out.println() ;
        }
    }
    /**
     * Driver
     */
    public static void main(String args[]) throws Exception {
        System.out.println("begin");

        GenerateDataFromTXT gd = new GenerateDataFromTXT();
        gd.createTrainingSet();

        System.out.println("end");
    }
}
