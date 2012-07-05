package chineseNumberRecognition;

import com.sun.image.codec.jpeg.JPEGCodec;
import java.awt.*;          			
import java.awt.event.*;			
import java.awt.geom.Line2D;			
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import javax.swing.*;				
import neuralNetwork.PatternList;
import com.sun.image.codec.jpeg.JPEGImageEncoder ;

public class DrawPictures extends JFrame implements ActionListener {

    private int x = -1, y = -1;                                //繪圖工具的終點座標
    private int oldX = -1, oldY = -1;                           //繪圖工具的起點座標
    private Graphics g;                                    //繪圖所需類別之引用
    private int color = 1, oldcolor = 1, erasercolor = 0;    //紀錄繪圖的顏色 , 當更改繪圖工具時 紀錄原先的顏色， 使用擦子時 紀錄原先的顏色
    private int tool = 2;                                   //紀錄繪圖的工具

    private Container c = getContentPane();                  //新增容器
    private Panel downPanel = new Panel();                 //新增表單 : 改變顏色專區
    private Panel toolPanel = new Panel();                  //新增表單 : 改變繪圖工具專區
    //private DrawxPanel drawPanel = new JPanel() ;
    private final PatternHandwritingPanel php ;
    private JButton apply = new JButton("Apply");		//新增黑色按鈕
    
    //新增改變繪圖工具各個按鈕
    private JButton pencil = new JButton();		//新增鉛筆繪圖工具
    private JButton eraser = new JButton();		//新增擦子繪圖工具
    private JButton newpaper = new JButton();		//新增新頁面繪圖工具
    
    //---Constructor
    public DrawPictures(final BpChineseNumReg bp, final PatternList training) {
        super("Chinese Number Recognition Test");                              
        
        //---set Icons.
        pencil.setIcon(new ImageIcon(getClass().getResource("/DrawImage/pencil.GIF")));	 // 鉛筆
        eraser.setIcon(new ImageIcon(getClass().getResource("/DrawImage/eraser.GIF")));	 // 擦子
        newpaper.setIcon(new ImageIcon(getClass().getResource("/DrawImage/newpaper.GIF")));//新頁面
    
        c.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE); //介面初始化為白色
        this.setLocation(40, 140);	//設定小畫家初始位置

        downPanel.setLayout(new GridLayout(1, 3));//群組版面配置，將顏色按鈕各編制入顏色專區
        downPanel.add(apply) ;
    
        c.add(downPanel, BorderLayout.SOUTH); //邊框版面配置，將改變顏色的板子置於容器的下面(南方)

        toolPanel.setLayout(new GridLayout(3, 1));//群組版面配置，將各工具編制入工具板子
        toolPanel.add(newpaper);  //工具:新頁面
        toolPanel.add(pencil); //工具:鉛筆
        toolPanel.add(eraser);//工具:擦子

        c.add(toolPanel, BorderLayout.WEST);//邊框版面配置，將工具得板子置於容器的左方(西方)

        //---drawPanel
        /*
        drawPanel.setSize(128,128) ;
        drawPanel.setBackground(Color.red) ;
        c.add(drawPanel,BorderLayout.CENTER) ;
        */
        //--- add listener to buttons.
        apply.addActionListener(this);
        newpaper.addActionListener(this);    
        pencil.addActionListener(this);
        eraser.addActionListener(this);
        
        //add mouse motion listener
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(tool != 4 && tool != 6 && tool != 8 && tool != 9 && tool != 10){
                oldX = x;				//此處為使鉛筆工具避免重複使用時 會導致上一次的點成為初始點 而下
                oldY = y; 				//  一次的點為終點 ， 形成不必要的一連線
                x = e.getX();
                y = e.getY();
                oldX = (oldX == -1 ? x : oldX);	//故當結束一次直線繪圖時 設定初始點為原點
                oldY = (oldY == -1 ? y : oldY);
                repaint();
                }
            }
        });
        //add Mouse Listener
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (tool == 2) {         //畫直線時 為避免記錄於上一點連接
                    oldX = -1;             //設為 -1 表示歸零 因視窗左上角作標訂為(0,0)
                    oldY = -1;
                    x = -1;
                    y = -1;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseClicked(MouseEvent e) {//繪圖工具splash潑墨用
            }
        });

        ///////////////////////////////////////////////////////////////////////
        php = new PatternHandwritingPanel(bp,training) ;
        ///////////////////////////////////////////////////////////////////////

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //預設視窗右上角關閉紐 為 可使用
        this.setSize(512+pencil.getIcon().getIconWidth(), 512); //設定畫面大小
        this.setVisible(true);  //設定畫面是否可看見
    }

    @Override
    public void paint(Graphics g) {
        switch (color) {
            case 0:
                g.setColor(Color.WHITE);
                break;    //白色
            case 1:
                g.setColor(Color.BLACK);
                break;    //黑色
            default:
                break;
        }
        switch (tool) {
            case 0:
                g.drawRect(0, 0, 0, 0);
                break;                         // 工具初始化:沒有任何效果
            case 2:
                Graphics2D g2d = (Graphics2D) g;              //將g型態轉成Graphics2D
                g2d.setStroke(new BasicStroke(5.0f));   //改變筆刷大小
                g2d.draw(new Line2D.Double(oldX, oldY, x, y));
                break;//將線連起來 保留舊點和新點連接 即可中不斷
            case 3:     
                g.fillRect(x, y, 30, 30);              //eraser的效果:用正方型白色擦子
                break;
            case 7:
                c.setBackground(Color.BLACK);      //先用黑色把其他顏色蓋掉
                c.setBackground(Color.WHITE);       //再用白色把黑色蓋掉
                break;                              //這一部分有點像剪刀猜拳原理   
            default:
                break;
        }
    }
    public void actionPerformed(ActionEvent e) { // 監聽時所使用的方法
        //繪圖工具按鈕觸發時
        if (e.getSource() == pencil) {
            tool = 2;
            color = oldcolor;
            erasercolor = 1;
        }
        if (e.getSource() == eraser) {
            tool = 3;
            erasercolor = 0;
            oldcolor = color;
            color = 0;
        }
        if (e.getSource() == newpaper) {
            tool = 7;
            repaint();
            
        }
        if(e.getSource() == apply){
            System.out.println("Run Neural Network") ;
            
            ///////////////////////////////////////////////////////////////////
            Dimension size = this.getSize();
            BufferedImage img =
                    new BufferedImage(size.width, size.height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            this.paint(g2);

            try {
                OutputStream out = new FileOutputStream("test.jpg");
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(img);
                out.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
            ///////////////////////////////////////////////////////////////////
        }
    }
    
    public static void main(String[] args) {
        DrawPictures dp = new DrawPictures(null,null);	//產生一小畫家物件
    }
    ////////////////////////////////////////////////////////////////////////////
    //---Inner Class
    class PatternHandwritingPanel extends JPanel {

        private PatternList training;
        private double[] pattern;

        public PatternHandwritingPanel(BpChineseNumReg bp, PatternList training) {
            this.training = training;
            pattern = new double[128 * 128];

        }

        public double[] getPattern() {
            return pattern;
        }

    }
    ////////////////////////////////////////////////////////////////////////////
}

