package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class panel extends JComponent {

    private Graphics2D g2;
    private BufferedImage image;

    private int height,width;
    private Thread thread;
    private boolean start =true;
    
    private final int Fps =60;
    private final int Time =1000000000/Fps;

    public void start(){
        height=getHeight();
        width=getWidth();

        image =new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 =image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        thread=new Thread (new Runnable() {

           public void run(){
             while (start) {
                long starttime=System.nanoTime();
                drawbackground();
                drawgame();
                render();
                long time=System.nanoTime()-starttime;
                if(time<Time){
                    long sleep =(Time-time)/1000000;
                    sleep((sleep));
                    System.out.println(sleep);
                }
                
             }
           } 
        });
        thread.start();
    }
    private void drawbackground(){
      
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0, width, height);
        drawStars();
    }
    private void drawStars() {
        g2.setColor(Color.WHITE); 
        for (int i = 0; i < 20; i++) {
            
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int size = (int) (Math.random() * 3) + 1; 
            g2.fillOval(x, y, size, size); 
        }
    }

    private void drawgame(){

    }

    private void render(){
       
        Graphics g =getGraphics();
        g.drawImage(image,0, 0, null);
        g.dispose();
    }

    private void sleep(long speed){
       try{
         Thread.sleep(speed);
       } catch(InterruptedException ex){
          System.err.println(ex);
       }
 }
    
}
