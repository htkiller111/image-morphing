/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class Edge2D {
    private Point2D a;
    private Point2D b;
    public Edge2D(Point2D na, Point2D nb){
        a = na;
        b = nb;
    }
    public Point2D getA(){return a;}
    public Point2D getB(){return b;}
    public void setA(Point2D na){a = na;}
    public void setB(Point2D nb){b = nb;}
}
