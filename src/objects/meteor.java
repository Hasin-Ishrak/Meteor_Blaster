package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;

public class meteor {

    public meteor(){
        this.image=new ImageIcon(getClass().getResource("/image/rocket.png")).getImage();
        Path2D p = new Path2D.Double();
        p.moveTo(0, metesz/ 2);
        p.lineTo(15, 10);
        p.lineTo(metesz- 5, 13);
        p.lineTo(metesz+ 10,metesz/ 2);
        p.lineTo(metesz- 5,metesz- 13);
        p.lineTo(15,metesz- 10);
        mtshape= new Area(p);
    }

    public static final double metesz=50;
    private double x,y;
    private final float speed=0.3f;
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
        AffineTransform olTransform=g2.getTransform();
        g2.translate(x, y);
        AffineTransform tran=new AffineTransform();
        tran.rotate(Math.toRadians(angle+45),metesz/2,metesz/2);
        g2.drawImage(image,tran,null);
        Shape shape =getshape();
        g2.setTransform(olTransform);
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
        if(x<=-size.getWidth() || y<=-size.getHeight()|| x>width || y>height){
            return false;
        }
        return true;
    }
}
