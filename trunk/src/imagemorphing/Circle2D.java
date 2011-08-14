/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class Circle2D {
    private double radious;
    private Vector2D center;
    public Circle2D(Vector2D c, double r){
        center = c;
        radious = r;
    }
    public void setRadious(double r){radious = r;}
    public void setCenter(Vector2D c){center = c;}
    public double getRadious(){return radious;}
    public Vector2D getCenter(){return center;}
}
