package objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import javax.swing.ImageIcon;

public class meteor {
    public meteor(String imagePath){
        this.image=new ImageIcon(getClass().getResource(imagePath)).getImage();
         mtshape=new Area(new java.awt.geom.Ellipse2D.Double(0,0,metesz,metesz));
    }
    public static final double metesz=70;
    private double x,y;
    private final float speed=0.5f;
    private float angle=0;
    private final Image image;
    private final Area mtshape;

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
      AffineTransform oldTransform = g2.getTransform();
      g2.translate(x, y);
      g2.drawImage(image, 0, 0, null);
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
    public Area getshape(){
        AffineTransform afx=new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle),metesz/2,metesz/2);
        return new Area(afx.createTransformedShape(mtshape));
    }
    public boolean check(int width,int height){
        Rectangle size=getshape().getBounds();
        if(x<=-size.getWidth()||y<=-size.getHeight()||x>width||y>height){
            return false;
        }
        return true;
    }
}
