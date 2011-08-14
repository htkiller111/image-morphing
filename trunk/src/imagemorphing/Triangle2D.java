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
    public boolean inDealunayCircle(Vector2D dp){
        Vector2D[] vertexes = getVertexes();
        double 
                a = vertexes[0].getCoord(0), 
                b = vertexes[0].getCoord(1), 
                c = a*a + b*b, 
                d = vertexes[1].getCoord(0), 
                e = vertexes[1].getCoord(1), 
                f = d*d + e*e,
                g = vertexes[2].getCoord(0), 
                h = vertexes[2].getCoord(1), 
                i = g*g + h*h,
                j = dp.getCoord(0), 
                k = dp.getCoord(1), 
                l = j*j + k*k;
                
        double determinant = 
                a*e*i - a*e*l - a*f*h + a*f*k + a*h*l - 
                a*i*k - b*d*i + b*d*l + b*f*g - b*f*j - 
                b*g*l + b*i*j + c*d*h - c*d*k - c*e*g + 
                c*e*j + c*g*k - c*h*j - d*h*l + d*i*k + 
                e*g*l - e*i*j - f*g*k + f*h*j;
        
        return determinant > 0;
    }
    public boolean contains(Edge2D e){
        for(Edge2D ed: edges)
            if(e == ed)
                return true;
        return false;
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
