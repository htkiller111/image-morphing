/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Samuel
 */
public class LAT {
    public static final int SINGLE_INTERSECTION = 0;
    public static final int NO_INTERSECTION = 1;
    public static final int FULL_INTERSECTION = 2;
    public static boolean sameSide(Vector3D p1, Vector3D p2, Vector3D a, Vector3D b){
        Vector3D cp1 = b.sub(a).cross(p1.sub(a));
        Vector3D cp2 = b.sub(a).cross(p2.sub(a));
        return cp1.dot(cp2) >= 0;
    }
    public static boolean pointInTriangle(Vector2D v, Triangle2D t){
        Vector3D p = new Vector3D(v.getCoord(0), v.getCoord(1), 0);
        Vector2D[] vertexes = t.getVertexes();
        Vector3D a = new Vector3D(
                vertexes[0].getCoord(0),
                vertexes[0].getCoord(1),
                0);
        Vector3D b = new Vector3D(
                vertexes[1].getCoord(0),
                vertexes[1].getCoord(1),
                0);
        Vector3D c = new Vector3D(
                vertexes[2].getCoord(0),
                vertexes[2].getCoord(1),
                0);
        return LAT.sameSide(p, a, b, c) && 
                LAT.sameSide(p, b, a, c) &&
                LAT.sameSide(p, c, a, b);
    }
    public static int[] intersect(int scanline, Edge2D b){
        int[] result = new int[2];
        double 
                c, 
                ax = b.org().getCoord(0), 
                ay = b.org().getCoord(1),
                bx = b.dest().getCoord(0), 
                by = b.dest().getCoord(1);
        if(by - ay == 0.0){
            if(((int) by) == scanline)
                result[0] = FULL_INTERSECTION;
            else
                result[0] = NO_INTERSECTION;
            result[1] = -1;
        }
        else{
            c = (scanline - ay) / (by - ay);
            if(0 <= c && c <= 1){
                result[0] = SINGLE_INTERSECTION;
                result[1] = (int)(ax + c*(bx - ax));
            }
            else{
                result[0] = NO_INTERSECTION;
                result[1] = -1;
            }
        }
        return result;
    }
    private static LinkedList<Integer> intersects(int scanline, Triangle2D t){
        LinkedList<Integer> result = new LinkedList<Integer> ();
        int[] intersectData = new int[2];
        for(Edge2D edge: t.getEdges()){
            intersectData = intersect(scanline, edge);
            if(intersectData[0] == SINGLE_INTERSECTION)
                result.add(intersectData[1]);
        }
        // if 3, 2 are the same (not-extreme vertex)
        if(result.size() == 3){
            if(result.get(0).intValue() == result.get(1).intValue())
                result.remove(0);
            else
                result.remove(2);
        }
        // order
        if(result.get(0) > result.get(1)){
            int temp = result.get(0);
            result.set(0, result.get(1));
            result.set(1, temp);
        }
        return result;
    }
    public static HashMap<Integer, LinkedList<Integer>> levels(Triangle2D t){
        int[] box = t.minimumSuroundingBox();
        HashMap<Integer, LinkedList<Integer>> result =
                new HashMap<Integer, LinkedList<Integer>>();
        for(int scanline = box[1]; scanline <= box[3]; scanline++)
            result.put(scanline, intersects(scanline, t));
        return result;
    }
    public static double[] getBarycentricCoords(double x4, double y4, Triangle2D t){
        double[] result = new double[3];
        Vector2D[] vex = t.getVertexes();
        double 
                x1 = vex[0].getCoord(0),
                y1 = vex[0].getCoord(1),
                x2 = vex[1].getCoord(0),
                y2 = vex[1].getCoord(1),
                x3 = vex[2].getCoord(0),
                y3 = vex[2].getCoord(1);
                
        result[0] = ((x4*y2)-(x4*y3)-(x2*y4)+(x2*y3)+(x3*y4)-(x3*y2))/
                ((x1*y2)-(x1*y3)-(x2*y1)+(x2*y3)+(x3* y1)-(x3*y2));
        result[1] = ((x1*y4)-(x1*y3)-(x4*y1)+(x4*y3)+(x3*y1)-(x3*y4))/
                ((x1*y2)-(x1*y3)-(x2*y1)+(x2*y3)+(x3*y1)-(x3*y2));
        result[2] = ((x1*y2)-(x1*y4)-(x2*y1)+(x2*y4)+(x4*y1)-(x4*y2))/
                ((x1*y2)-(x1*y3)-(x2*y1)+(x2*y3)+(x3*y1)-(x3*y2));
        return result;
    }
    public static double[] restoreBarycentricCoords(
            double u, 
            double v, 
            double w, 
            Triangle2D t){
        double[] result = new double[2];
        Vector2D[] vex = t.getVertexes();
        result[0] = 
                u*vex[0].getCoord(0) +
                v*vex[1].getCoord(0) + 
                w*vex[2].getCoord(0);
        result[1] = 
                u*vex[0].getCoord(1) + 
                v*vex[1].getCoord(1) + 
                w*vex[2].getCoord(1);
        return result;
    }
}
