package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import objects.player;
import objects.bomb;
import objects.meteor;
import objects.Sound.sound;

public class panel extends JComponent {
    
    private int score = 0;
    private boolean gameOver = false;
    private boolean start =true;
    private boolean gameStarted = false;
    private boolean spacePlayed = false;

    private Graphics2D g2;
    private BufferedImage image;
    private int height,width;
    private Thread thread;

    private ImageIcon gifImage; 
    private int shotm;
    
    private final int Fps =60;
    private final int Time =1000000000/Fps;

    private sound snd;
    private keyboard key;
    private player player;
    private List<bomb>bombs;
    private List<meteor>meteors;

    public void start(){
        height=getHeight();
        width=getWidth();

        image =new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 =image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        gifImage = new ImageIcon(getClass().getResource("/image/space.gif"));

        thread=new Thread (new Runnable() {

           public void run(){
             while (start) {
               long starttime=System.nanoTime();
                  drawbackground(); // draw space 
               if (!gameStarted) {
                  drawStartScreen(); // draw title screen
               } 
               else {
                   if (snd != null && !spacePlayed) {
                      snd.soundSpace();
                      spacePlayed = true;
                      }

                     if (gameOver) {
                       drawGameOverScreen(); // draw gameover screen
                     }
                     else {
                       drawgame(); // draw meteors, bombs ,plane
                       drawScore(); //draw current score at top left
                  }
               }
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
        initbombs();
        thread.start();
    }
    // basically we use thread for improve performance (meteors spawning ,plane movement and bomb)
    // at a same time
     private void initobj(){

       snd=new sound();
       player =new player();
       player.changelocation(width/2,height/2);
       meteors=new ArrayList<>();
       new Thread(new Runnable() {
         public void run(){
            while (start) {
                addmt(); // add meteors
                if(score>=500){
                    sleep(1000);
                }
                else
                sleep(3000); // wait 3 sec before spawning more
            }
         }
       }).start();
    }

    private void addmt(){
        Random ran=new Random();
        int lcy=ran.nextInt(height-25);
        meteor lefMeteor=new meteor("/image/bluemt.png");
        lefMeteor.changelocation(0, lcy);
        lefMeteor.changeangle(0);
        meteors.add(lefMeteor);

        int lcy11=ran.nextInt(height-25);
        meteor lefMeteor2=new meteor("/image/bluemt.png");
        lefMeteor2.changelocation(width, lcy11);
        lefMeteor2.changeangle(180);
        meteors.add(lefMeteor2);

        if(score >=500){
            int lcy2=ran.nextInt(height-50)+25;
            meteor rigMeteor=new meteor("/image/yellowmt.png");
            rigMeteor.changelocation(width, lcy2);
            rigMeteor.changeangle(180);
            meteors.add(rigMeteor);
        }
    }
    
    private void initkey(){
        key =new keyboard();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    key.setKey_left(true);
                } 
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    key.setKey_right(true);
                } 
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(true);
                } 
                else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(true);
                } 
                else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(true);
                }
                 else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(true);
                }
                else if (e.getKeyCode() == KeyEvent.VK_S && !gameStarted) {
                    gameStarted = true;
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    key.setKey_left(false);
                } 
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    key.setKey_right(false);
                }
                 else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(false);
                } 
                else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(false);
                } 
                else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(false);
                } 
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
                    if(key.isKey_j() || key.isKey_k()){
                        if(shotm==0){
                            if(key.isKey_j()){
                                bombs.add(0,new bomb(player.getx(),player.gety(),player.getangle(),8,3f));
                            }
                            else{
                                bombs.add(0,new bomb(player.getx(),player.gety(),player.getangle(),14,4f));
                            }
                        }
                        shotm++;
                        if(shotm==15){
                            shotm=0;
                        }
                    }
                    else{
                        shotm=0;
                    }
                    if(key.isKey_space()){
                        player.speedUp();
                    }
                    else{
                        player.speedDown();
                    }
                    player.update();
                    player.changeangle(angle);
                    for(int i=0;i<meteors.size();i++){
                        meteor meteor=meteors.get(i);
                        if(meteor!=null){
                            meteor.update();
                            if(!meteor.check(width, height)){
                                meteors.remove(meteor);
                            }
                            else{
                                 Area area = new Area(meteor.getshape());
                                area.intersect(player.getshape());
                                if (!area.isEmpty()) {
                               gameOver = true;
                              }
                            }
                        }
                    }
                    sleep(5);
                }
            }
        }).start();

    }
     private void initbombs(){
        bombs = new ArrayList<>();
        new Thread(new Runnable() {
           public void run(){
            while(start){
               for(int i=0;i<bombs.size();i++){
                  bomb bomb =bombs.get(i);
                  if (bomb != null) {
                    bomb.update();
                    checkbombs(bomb);
                    if (!bomb.check(width, height)) {
                        bombs.remove(bomb);  
                    }
                }
                  }
               
               sleep(1);
            }
           } 
        }).start();;
    }
    private void checkbombs(bomb bomb){
        for(int i=0;i<meteors.size();i++){
            meteor meteor =meteors.get(i);
            if(meteor!=null){
               Area area=new Area(bomb.getshape());
               area.intersect(meteor.getshape());
               if(!area.isEmpty()){
                meteors.remove(meteor); //if bomb hit the meteor ,then meteor will be removed
                bombs.remove(bomb);  // if the bomb hit the meteor the bomb will be removed
                score+=50; // per destroy of meteor increase 50 point
                snd.soundShoot(); // give a shoot sound of hitting
               }
            }
        }
    }
    private void drawbackground() {
        if (gifImage!=null) {
            g2.drawImage(gifImage.getImage(), 0, 0,width,height,null); 
        }
         else {
            g2.setColor(new Color(30,30,30));
            g2.fillRect(0,0,width,height);
        }
    }
    private void drawStartScreen() {
        int boxWidth=420;
        int boxHeight=230;
        int x =(width-boxWidth)/2;
        int y =(height-boxHeight)/2;
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y,boxWidth,boxHeight,30,30);
        g2.setColor(new Color(255, 100, 100));
        g2.drawRoundRect(x, y,boxWidth,boxHeight,30,30);
        g2.setFont(g2.getFont().deriveFont(38f));
        String title="MeTeOr BlAsTeR";
        int titleWidth=g2.getFontMetrics().stringWidth(title);
        int titleX =x+(boxWidth-titleWidth)/2;
        int titleY =y+65;
        for (int i=5;i>=1;i--) {
            g2.setColor(new Color(255,50,50,25*i));
            g2.drawString(title,titleX-i,titleY-i);
        }
        g2.setColor(Color.WHITE);
        g2.drawString(title, titleX, titleY);

        g2.setFont(g2.getFont().deriveFont(18f));
        String subtitle ="Dodge & Destroy Meteors";
        int subtitleWidth =g2.getFontMetrics().stringWidth(subtitle);
        g2.setColor(new Color(200,200,200));
        g2.drawString(subtitle,x+(boxWidth-subtitleWidth)/2,y+100);
    
        g2.setFont(g2.getFont().deriveFont(24f));
        String startMsg ="Press S to Start";
        int msgWidth=g2.getFontMetrics().stringWidth(startMsg);
        int msgX=x+(boxWidth-msgWidth)/2;
        int msgY=y+180;
    
        g2.setColor(new Color(255, 255, 100, 100));
        g2.drawString(startMsg,msgX-1,msgY-1);
        g2.setColor(Color.YELLOW);
        g2.drawString(startMsg,msgX,msgY);
    }
    
    private void drawgame(){
      player.draw(g2);
      for(int i=0;i<bombs.size();i++){
         bomb bomb =bombs.get(i);
         if(bomb!=null){
            bomb.draw(g2);
         }
      }
      for(int i=0;i<meteors.size();i++){
        meteor meteor=meteors.get(i);
        if(meteor!=null){
            meteor.draw(g2);
        }
     }
    }
    private void drawGameOverScreen() {
    int boxWidth=420;
    int boxHeight=230;
    int x = (width-boxWidth)/2;
    int y = (height-boxHeight)/2;

    g2.setColor(new Color(0,0,0,200));
    g2.fillRoundRect(x,y,boxWidth,boxHeight,30,30);
    g2.setColor(Color.RED);
    g2.drawRoundRect(x,y,boxWidth,boxHeight,30,30);

    g2.setFont(g2.getFont().deriveFont(36f));
    String gameOverText = "Game Over";
    int textWidth = g2.getFontMetrics().stringWidth(gameOverText);
    g2.setColor(Color.WHITE);
    g2.drawString(gameOverText,x +(boxWidth-textWidth)/2,y+80);

    g2.setFont(g2.getFont().deriveFont(20f));
    String scoreText ="Final Score: "+score;
    int scoreWidth =g2.getFontMetrics().stringWidth(scoreText);
    g2.drawString(scoreText,x+(boxWidth-scoreWidth) /2,y+130);
}

    private void drawScore() {
    g2.setFont(g2.getFont().deriveFont(25f));
    g2.setColor(Color.yellow);
    g2.drawString("Score: "+score,20,30);
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