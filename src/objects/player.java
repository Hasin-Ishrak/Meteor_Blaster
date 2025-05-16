package objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.AffineTransform;

public class player {

    public player(){
       this.image=new ImageIcon(getClass().getResource("/image/plane.png")).getImage();
       this.imagespeed=new ImageIcon(getClass().getResource("/image/plane_speed.png")).getImage();
    }

    public static final double playersz=64;
    private double x;
    private double y;
    private final float mxsp= 1f;
    private float speed= 0f;
    private float angle = 0f;
    private boolean speedup;
    private final Image image;
    private final Image imagespeed;

    public void changelocation(double x,double y){
        this.x=x;
        this.y=y;
    }
    public void update(){
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
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
       tr.rotate(Math.toRadians(angle+45),playersz/2,playersz/2);
       g2.drawImage(speedup ? imagespeed:image,tr,null);

       g2.setTransform(oldTransform);
    }
    public Area getshape() {
   
    Path2D p = new Path2D.Double();
    p.moveTo(playersz / 2, 0);
    p.lineTo(playersz - 5, playersz - 5);
    p.lineTo(5, playersz - 5);
    p.closePath();

    AffineTransform afx = new AffineTransform();
    afx.translate(x, y);
    afx.rotate(Math.toRadians(angle), playersz / 2, playersz / 2);

    return new Area(afx.createTransformedShape(p));
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
    public void speedUp(){
       speedup=true;
       if(speed>mxsp){
        speed=mxsp;
       }
       else{
        speed+=0.01f;
       }
    }
    public void speedDown(){
      speedup=false;
      if(speed<=0){
        speed=0;
      }
      else{
        speed-=0.003f;
      }
    }
}