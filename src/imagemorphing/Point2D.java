/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class Point2D implements Comparable{
    private int x, y;
    public static final int THRESHOLD = 5;
    public Point2D(int nx, int ny){
        x = nx;
        y = ny;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public void setX(int nx){x = nx;}
    public void setY(int ny){y = ny;}
    @Override
    public int compareTo(Object t) {
        if (t == null)
            return -1;
        if (t instanceof Point2D){
            Point2D o = (Point2D)t;
            if(LAT.distance(o, this) <= THRESHOLD)
                return 0;
            return -1;
        }
        return -1;
    }
    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
    public boolean insideOf(Triangle2D t){
        Point3D p = new Point3D(x, y, 0);
        Point3D a = new Point3D(
                t.getVertex(0).getX(),
                t.getVertex(0).getY(),
                0);
        Point3D b = new Point3D(
                t.getVertex(1).getX(),
                t.getVertex(1).getY(),
                0);
        Point3D c = new Point3D(
                t.getVertex(2).getX(),
                t.getVertex(2).getY(),
                0);
        return LAT.sameSide(p, a, b, c) && 
                LAT.sameSide(p, b, a, c) &&
                LAT.sameSide(p, c, a, b);
    }
}
