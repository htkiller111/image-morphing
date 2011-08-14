/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class LAT {
    public static Point3D cross(Point3D p, Point3D q){
        return new Point3D(
                p.getY()*q.getZ() - p.getZ()*q.getY(),
                p.getZ()*q.getX() - p.getX()*q.getZ(),
                p.getX()*q.getY() - p.getY()*q.getX());
    }
    public static int dot(Point3D p, Point3D q){
        return p.getX()*q.getX() + p.getY()*q.getY() + p.getZ()*q.getZ() ;
    }
    public static int dot(Point2D p, Point2D q){
        return p.getX()*q.getX() + p.getY()*q.getY();
    }
    public static Point3D add(Point3D p, Point3D q){
        return new Point3D(
                p.getX() + q.getX(), 
                p.getY() + q.getY(), 
                p.getZ() + q.getZ());
    }
    public static Point3D sub(Point3D p, Point3D q){
        return new Point3D(
                p.getX() - q.getX(), 
                p.getY() - q.getY(), 
                p.getZ() - q.getZ());
    }
    public static Point2D sub(Point2D p, Point2D q){
        return new Point2D(
                p.getX() - q.getX(), 
                p.getY() - q.getY());
    }
    public static boolean sameSide(Point3D p1, Point3D p2, Point3D a, Point3D b){
        Point3D cp1 = cross(sub(b, a), sub(p1, a));
        Point3D cp2 = cross(sub(b, a), sub(p2, a));
        return dot(cp1, cp2) >= 0;
    }
    public static double distance(Point2D a, Point2D b){
        return Math.sqrt(dot(sub(a, b), sub(a, b)) + 0.0);
    }
}
