package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

import objects.player;

public class panel extends JComponent {

    private Graphics2D g2;
    private BufferedImage image;

    private int height,width;
    private Thread thread;
    private boolean start =true;
    private keyboard key;
    
    private final int Fps =60;
    private final int Time =1000000000/Fps;

    private player player;


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
                }
                
             }
           } 
        });
        initobj();
        initkey();
        thread.start();
    }

    private void initobj(){
       player =new player();
       player.changelocation(150, 150);
    }

    private void initkey(){
        key =new keyboard();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    key.setKey_left(true);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    key.setKey_right(true);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(true);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(true);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(true);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    key.setKey_left(false);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    key.setKey_right(false);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(false);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(false);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(false);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(false);
                }
            }
        });
        new Thread(new Runnable() {
            public void run(){
                float s=1f;
                while (start) {
                    float angle =player.getangle();
                    if(key.isKey_right()){
                        angle+=s;
                    }
                    if(key.isKey_left()){
                        angle-=s;
                    }
                    player.changeangle(angle);
                    sleep(5);
                }
            }
        }).start();

    }

    private void drawbackground(){
      
        g2.setColor(new Color(30,30,30));
        g2.fillRect(0, 0, width, height);
        // drawStars();
    }
    // private void drawStars() {
    //     g2.setColor(Color.WHITE); 
    //     for (int i = 0; i < 20; i++) {
            
    //         int x = (int) (Math.random() * width);
    //         int y = (int) (Math.random() * height);
    //         int size = (int) (Math.random() * 3) + 1; 
    //         g2.fillOval(x, y, size, size); 
    //     }
    // }

    private void drawgame(){
      player.draw(g2);
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
