package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class bomb {

    private double x;
    private double y;
    private final Shape shape;
    private final Color color = new Color(0, 255, 255);
    private final float angle;
    private double size;
    private float speed = 1f;

    public bomb(double x,double y,float angle,double size,float speed){
       x+=player.playersz/2 -(size/2);
       y+=player.playersz/2-(size/2);
       this.x=x;
       this.y=y;
       this.angle=angle;
       this.size=size;
       this.speed=speed;
       shape =new Ellipse2D.Double(0,0,size*1.5,size);
    }
    public void update(){
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
    }
    public boolean check(int w, int h){
        if(x<=size || y<=size || x>w || y>h){
            return false;
        }
        return true;
    }
    public void draw(Graphics2D g2){
        AffineTransform oldTransform = g2.getTransform();
        g2.setColor(color);
        g2.translate(x,y);
        g2.fill(shape);
        g2.setTransform(oldTransform);
    }
    public Shape getshape(){
       return new Area(new Ellipse2D.Double(x, y, size, size));
    }
    public double getx(){
        return x;
    }
    public double gety(){
        return y;
    }
    public double getsize(){
        return size;
    }
    public double getcnx(){
        return x+(size/2);
    }
    public double getcny(){
        return y+(size/2);
    }
}
