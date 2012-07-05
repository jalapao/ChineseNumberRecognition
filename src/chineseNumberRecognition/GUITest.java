package chineseNumberRecognition;

import java.io.File ;
import java.awt.event.*;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.WindowConstants;

import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import neuralNetwork.Pattern;
import neuralNetwork.PatternList;

public class GUITest extends JFrame {

    private static final String TRAIN_FILENAME = "chineseNumberReg.trainingData";
    private static final String NETWORK_FILENAME = "chineseNumberReg.network";
    public static final double ONE = 0.9999999999;
    public static final double ZERO = 0.0000000001;
    private final NnResultPanel nrp;
    private final PatternEditPanel pep;
    private final GridBagConstraints gbc;

    /**
     *
     */
    public GUITest(String frame_title, final BpChineseNumReg bpNetwork, final PatternList training) {
        super(frame_title);
        //
        // Specify layout hints
        //
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 100;
        gbc.weighty = 100;

        getContentPane().setLayout(new GridBagLayout());

        pep = new PatternEditPanel(bpNetwork, training);
        //PatternSelectPanel psp = new PatternSelectPanel(pep);
        nrp = new NnResultPanel();
        JButton apply_button = new JButton("辨識");
        JButton flush_button = new JButton("重新輸入");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        //getContentPane().add(psp, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        getContentPane().add(pep, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        getContentPane().add(nrp, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weighty = 20;
        getContentPane().add(apply_button, gbc);
        
        getContentPane().add(flush_button) ;
        
        apply_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                nrp.setAnswer(bpNetwork.runNeuralNetwork(pep.getPattern()));
            }
        });

        flush_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                pep.flush();
                nrp.flush();
            }
        });


    }

    /**
     * driver
     */
    public static void main(String args[]) throws Exception {
        System.out.println("begin");

        BpChineseNumReg bpNetwork = null;
        PatternList training = null;

        training = new PatternList();
        training.reader(new File(TRAIN_FILENAME));

        bpNetwork = new BpChineseNumReg(new File(NETWORK_FILENAME));

        GUITest bdtg = new GUITest("中文數字辨識系統", bpNetwork, training);

        bdtg.setBackground(java.awt.Color.white);
        bdtg.setSize(640, 480);
        bdtg.setVisible(true);
        
        
        bdtg.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        System.out.println("end");
        
        
    }
}

/**
 * Select a digit to display.
 */
class PatternSelectPanel extends JPanel {

    /**
     * @param pep
     */
    public PatternSelectPanel(final PatternEditPanel pep) {
        setLayout(new GridLayout(5, 2));

        JButton selections[] = new JButton[10];
        for (int ii = 0; ii < 10; ii++) {
            selections[ii] = new JButton(Integer.toString(ii));
            add(selections[ii]);

            selections[ii].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    pep.setPattern(Integer.parseInt(event.getActionCommand()));
                }
            });
        }

        BevelBorder bevel_border = new BevelBorder(BevelBorder.RAISED);
        this.setBorder(new TitledBorder(bevel_border, "Select Pattern"));
    }
}

/**
 * Show the responses from BackProp
 */
class NnResultPanel extends JPanel {

    private JButton[] results;

    /**
     *
     */
    public NnResultPanel() {
        setLayout(new GridLayout(5, 2));

        results = new JButton[10];
        for (int i = 0; i < 10; i++) {
            results[i] = new JButton(chineseNum(i));
            add(results[i]);
        }

        BevelBorder bevel_border = new BevelBorder(BevelBorder.RAISED);
        this.setBorder(new TitledBorder(bevel_border, "結果"));

        double temp[] = new double[10];
        setAnswer(temp);
    }

    public void flush(){
        for(int i=0;i!=results.length;++i){
            results[i].setText(chineseNum(i));
            results[i].setBackground(java.awt.Color.black);
            results[i].setForeground(java.awt.Color.red);
        }
    }
    private String chineseNum(int i) {
        String ret = "";
        switch (i) {
            case 1:
                ret = "一";
                break;
            case 2:
                ret = "二";
                break;
            case 3:
                ret = "三";
                break;
            case 4:
                ret = "四";
                break;
            case 5:
                ret = "五";
                break;
            case 6:
                ret = "六";
                break;
            case 7:
                ret = "七";
                break;
            case 8:
                ret = "八";
                break;
            case 9:
                ret = "九";
                break;
            case 0:
                ret = "零";
                break;
            default:
                break;
        }
        return ret ;
    }

    /**
     *
     */
    public void setAnswer(double[] answers) {
        for (int ii = 0; ii < answers.length; ii++) {
            if (answers[ii] > 0.75) {
                results[ii].setBackground(java.awt.Color.red);
                results[ii].setForeground(java.awt.Color.black);
            } else {
                results[ii].setBackground(java.awt.Color.black);
                results[ii].setForeground(java.awt.Color.red);
            }
        }
    }
}

/**
 * Interactive 128x128 edit panel
 */
class PatternEditPanel extends JPanel {

    private PatternList training;
    private JButton[] pixels;
    private double[] pattern;

    public PatternEditPanel(BpChineseNumReg bd3, PatternList training) {
        int len = 8;
        this.training = training;

        pattern = new double[len * len];

        setLayout(new GridLayout(len, len));

        pixels = new JButton[len * len];
        for (int ii = 0; ii < pixels.length; ii++) {
            pattern[ii] = 0.0;
            pixels[ii] = new JButton(Integer.toString(ii));
            pixels[ii].setBackground(java.awt.Color.white);
            add(pixels[ii]);

            pixels[ii].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    int ndx = Integer.parseInt(event.getActionCommand());
                    System.out.println("ndx: "+ndx);
                    if (pattern[ndx] < 0.5) {
                        pattern[ndx] = GUITest.ONE;
                        pixels[ndx].setBackground(java.awt.Color.green);
                    } else {
                        pattern[ndx] = GUITest.ZERO;
                        pixels[ndx].setBackground(java.awt.Color.white);
                    }
                }
            });

            pixels[ii].addMouseMotionListener(new MouseMotionListener() {

                public void mouseDragged(MouseEvent e) {
                    
                }

                public void mouseMoved(MouseEvent e) {
                    
                }
            });

        }

        
        BevelBorder bevel_border = new BevelBorder(BevelBorder.RAISED);
        this.setBorder(new TitledBorder(bevel_border, "輸入版"));
    }

    public void flush(){
        for(int i=0;i!=pixels.length;++i){
            pixels[i].setBackground(java.awt.Color.white);
            pattern[i] = GUITest.ZERO ;
        }

    }
    /**
     * Show the pattern used for training
     *
     * @param ndx
     *            index into PatternList
     */
    public void setPattern(int ndx) {
        // System.out.println("set pattern " + ndx);

        Pattern pp = training.get(ndx);

        double[] original = pp.getInput();

        for (int ii = 0; ii < original.length; ii++) {
            pattern[ii] = original[ii];

            // System.out.println(ii + " " + original[ii]);

            if (original[ii] < 0.5) {
                pixels[ii].setBackground(java.awt.Color.black);
            } else {
                pixels[ii].setBackground(java.awt.Color.red);
            }
        }
    }

    public double[] getPattern() {
        return (pattern);
    }
}
