/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

import java.util.LinkedList;

/**
 *
 * @author Samuel
 */
public class Triangulation {
    private LinkedList<Vector2D> pList;
    private LinkedList<Edge2D> eList;
    private LinkedList<Triangle2D> tList;
    public Triangulation(Vector2D a, Vector2D b, Vector2D c){
        pList = new LinkedList<Vector2D>();
        eList = new LinkedList<Edge2D>();
        tList = new LinkedList<Triangle2D>();
        pList.add(a);
        pList.add(b);
        pList.add(c);
        eList.add(new Edge2D(a, b));
        eList.add(new Edge2D(b, c));
        eList.add(new Edge2D(c, a));
        tList.add(new Triangle2D(
                eList.get(0),
                eList.get(1),
                eList.get(2)));
    }
    public boolean addPoint(Vector2D p){
        if(contains(p))
            return false;
        pList.add(p);
        return true;
    }
    public boolean contains(Vector2D p){
        for(Vector2D a: pList)
            if(p.compareTo(a) == 0)
                return true;
        return false;
    }
    public LinkedList<Edge2D> getEdgeList(){
        return eList;
    }
    public LinkedList<Vector2D> getPointCloud(){
        return pList;
    }
}