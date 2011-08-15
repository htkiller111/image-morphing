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
// Debug
//  public LinkedList<Circle2D> circles;    
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
// Debug
//        circles = new LinkedList<Circle2D>();
    }
    public Vector2D[] getStartingPoints(){
        Vector2D[] tor = {pList.get(0), pList.get(1), pList.get(2)};
        return tor;
    }
    public boolean addPoint(Vector2D p){
        // If triangulation already contains point
        if(contains(p))
            return false;
        
        // Avoid intersections
        for(Edge2D edge: eList)
            if(edge.intersects(p))
                return false;
        
        // Process outside cycle to avoid cycle/mod exception
        Triangle2D bound = null;
        for(Triangle2D at: tList){
            if(LAT.pointInTriangle(p, at)){
                bound = at;
                break;
            }
        }
        
        // Remove container triangle
        tList.remove(bound);
        Vector2D[] vertexes = bound.getVertexes();
        Edge2D 
                up = new Edge2D(vertexes[0], p),
                vp = new Edge2D(vertexes[1], p),
                wp = new Edge2D(vertexes[2], p);
        Edge2D[] edges = bound.getEdges();
        
        // Add new triangles
        tList.add(relatedTriangle(edges[0], up, vp, wp));
        tList.add(relatedTriangle(edges[1], up, vp, wp));
        tList.add(relatedTriangle(edges[2], up, vp, wp));

// Debug
//        circles = new LinkedList<Circle2D>();
//        circles.add(tList.get(tList.size() - 1).getCirCircle());
//        circles.add(tList.get(tList.size() - 2).getCirCircle());
//        circles.add(tList.get(tList.size() - 3).getCirCircle());
        
        // Add new Edges
        eList.add(up);
        eList.add(vp);
        eList.add(wp);
        
        // Add new point
        pList.add(p);
        
        // Legalize new triangulation
        for(Edge2D edge: edges)
            legalize(edge, p);
        
        return true;
    }
    public void legalize(Edge2D e, Vector2D p){
        LinkedList<Triangle2D> t_nei = getTrianglesWithEdge(e);
        if(t_nei.size() == 2){
            Triangle2D actual = null;
            Triangle2D next = null;
            if(t_nei.get(0).contains(p)){
                actual = t_nei.get(0);
                next = t_nei.get(1);
            }
            else{
                actual = t_nei.get(1);
                next = t_nei.get(0);
            }
            Vector2D z = next.not(e);
            if(actual.inDealunayCircle(z)){
                tList.remove(actual);
                tList.remove(next);
                eList.remove(e);
                
                // New Edge
                Edge2D nu = new Edge2D(p, z);
                eList.add(nu);
                
                // First new Triangle Construction
                tList.add(new Triangle2D(
                        actual.touchesOrg(e), 
                        next.touchesOrg(e),
                        nu));
                
                // Second new Triangle Construction
                tList.add(new Triangle2D(
                        actual.touchesDest(e), 
                        next.touchesDest(e),
                        nu));
                
                // Recursive step
                for(Edge2D ea: next.getEdges())
                    if(ea != e)
                        legalize(ea, p);
                
            }
        }
    }
    public LinkedList<Triangle2D> getTrianglesWithEdge(Edge2D e){
        LinkedList<Triangle2D> tor = new LinkedList<Triangle2D>();
        for(Triangle2D t: tList)
            if(t.contains(e))
                tor.add(t);
        return tor;
    } 
    public Triangle2D relatedTriangle(Edge2D b, Edge2D n0, Edge2D n1, Edge2D n2){
        if(b.shareWith(n0))
            if(b.shareWith(n1))
                return new Triangle2D(b, n0, n1);
            else
                return new Triangle2D(b, n0, n2);
        else
            return new Triangle2D(b, n1, n2);
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