/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

import java.util.Arrays;

/**
 *
 * @author Samuel
 */
public class Triangle2D {
    private Edge2D[] edges;
    public Triangle2D(Edge2D e0, Edge2D e1, Edge2D e2){
        edges = new Edge2D[3];
        edges[0] = e0;
        edges[1] = e1;
        edges[2] = e2;
    }
    public Edge2D[] getEdges(){
        return edges;
    }
    public Edge2D getEdge(int i){
        if(i >=0 && i < edges.length)
            return edges[i];
        throw new UnsupportedOperationException("Edge index out of range " + i);
    }
    public Vector2D[] getVertexes(){
        Vector2D[] tor = {edges[0].org(), edges[0].dest(), null};
        if(tor[0] == edges[2].org() || tor[1] == edges[2].org())
            tor[2] = edges[2].dest();
        else
            tor[2] = edges[2].org();
        return tor;
    }
    @Override
    public String toString(){
        String tor = "";
        for (Edge2D ed: edges)
            tor += ed.toString() + " ";
        return "< " + tor + ">";
    }
    public Circle2D getCirCircle(){
        Vector2D[] vex = getVertexes();
        double a0, a1, c0, c1, det, asq, csq, ctr0, ctr1, rad2;
        a0 = vex[0].getCoord(0) - vex[1].getCoord(0);
        a1 = vex[0].getCoord(1) - vex[1].getCoord(1);
        c0 = vex[2].getCoord(0) - vex[1].getCoord(0);
        c1 = vex[2].getCoord(1) - vex[1].getCoord(1);
        det = a0*c1 - c0*a1;
        if(det == 0.0)
            throw new UnsupportedOperationException("Colineal edges detected.");
        det = 0.5/det;
        asq = a0*a0 + a1*a1;
        csq = c0*c0 + c1*c1;
        ctr0 = det*(asq*c1 - csq*a1);
        ctr1 = det*(csq*a0 - asq*c0);
        rad2 = ctr0*ctr0 + ctr1*ctr1;
        return new Circle2D(new Vector2D(ctr0 + vex[1].getCoord(0), ctr1 + vex[1].getCoord(1)), Math.sqrt(rad2));
    }
    public boolean inDealunayCircle(Vector2D dp){
        Circle2D cc = getCirCircle();
        double radd = 
                Math.pow(dp.getCoord(0) - cc.getCenter().getCoord(0), 2.0) +
                Math.pow(dp.getCoord(1) - cc.getCenter().getCoord(1), 2.0);
        double disc = Math.pow(cc.getRadious(), 2.0) - radd;
        if(disc == 0)
            throw new UnsupportedOperationException("Cocircular vertexes detected.");
        return disc > 0.0;
    }
    public boolean contains(Edge2D e){
        for(Edge2D ed: edges)
            if(e == ed)
                return true;
        return false;
    }
    public boolean contains(Vector2D p){
        for(Vector2D pa: getVertexes())
            if(pa == p)
                return true;
        return false;
    }
    public Vector2D not(Edge2D e){
        for(Edge2D ea: edges)
            if(ea != e){
                if(ea.org() != e.org() && ea.org() != e.dest())
                    return ea.org();
                else
                    return ea.dest();
            }
        return null;
    }
    public Edge2D touchesOrg(Edge2D e){
        for(Edge2D ea: edges)
            if(ea != e)
                if(ea.org() == e.org() || ea.dest() == e.org())
                    return ea;
        return null;
    }
    public Edge2D touchesDest(Edge2D e){
        for(Edge2D ea: edges)
            if(ea != e)
                if(ea.org() == e.dest() || ea.dest() == e.dest())
                    return ea;
        return null;
    }
    public int[] minimumSuroundingBox(){
        Vector2D[] vex = getVertexes();
        int[] box = {Integer.MAX_VALUE, Integer.MAX_VALUE, -1, -1};
        for(Vector2D vertex: vex){
            if(vertex.getCoord(0) < box[0])
                box[0] = (int)vertex.getCoord(0);
            if(vertex.getCoord(0) > box[2])
                box[2] = (int)vertex.getCoord(0);
            if(vertex.getCoord(1) < box[1])
                box[1] = (int)vertex.getCoord(1);
            if(vertex.getCoord(1) > box[3])
                box[3] = (int)vertex.getCoord(1);
        }
        return box;
    }
    /*@Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Arrays.deepHashCode(this.edges);
        return hash;
    }*/
    public boolean equals(Object o){
        if(o instanceof Triangle2D)
            return this == o;
        return false;
    }
}
