package objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;

public class player {

    public player(){
       this.image=new ImageIcon(getClass().getResource("/image/plane.png")).getImage();
       this.imagespeed=new ImageIcon(getClass().getResource("/image/plane_speed.png")).getImage();
    }

    public static final double playersz=70;
    private double x;
    private double y;
    private float angle = 0f;
    private final Image image;
    private final Image imagespeed;

    public void changelocation(double x,double y){
        this.x=x;
        this.y=y;
    }

    public void changeangle(float angle){
        if(angle<0){
            angle=359;
        }
        else if(angle>359){
            angle =0;
        }
        this.angle=angle;
    }
    public void draw(Graphics2D g2){
       AffineTransform oldTransform=g2.getTransform();
       g2.translate(x, y);
       AffineTransform tr=new AffineTransform();
       tr.rotate(Math.toRadians(angle),playersz/2,playersz/2);
       g2.drawImage(image,tr,null);

       g2.setTransform(oldTransform);
    }
    public double getx(){
        return x;
    }
    public double gety(){
        return y;
    }
    public float getangle(){
        return angle;
    }

}
