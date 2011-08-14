/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

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
    /*
    public boolean inDealunayCircle(Vector2D dp){
        int 
                a = vertex[0].getX(), 
                b = vertex[0].getY(), 
                c = a*a + b*b, 
                d = vertex[1].getX(), 
                e = vertex[1].getY(), 
                f = d*d + e*e,
                g = vertex[2].getX(), 
                h = vertex[2].getY(), 
                i = g*g + h*h,
                j = dp.getX(), 
                k = dp.getY(), 
                l = j*j + k*k;
                
        int determinant = 
                a*e*i - a*e*l - a*f*h + a*f*k + a*h*l - 
                a*i*k - b*d*i + b*d*l + b*f*g - b*f*j - 
                b*g*l + b*i*j + c*d*h - c*d*k - c*e*g + 
                c*e*j + c*g*k - c*h*j - d*h*l + d*i*k + 
                e*g*l - e*i*j - f*g*k + f*h*j;
        
        return determinant > 0;
    }
     * 
     */
}
