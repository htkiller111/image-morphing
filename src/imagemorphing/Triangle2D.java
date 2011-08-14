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
    private Point2D[] vertex;
    
    private boolean alreadyGotVertex(Point2D v){
        for(int i = 0; i < vertex.length; i++)
            if(v.compareTo(vertex[i]) == 0)
                return true;
        return false;
    }
    public Triangle2D(Edge2D e0, Edge2D e1, Edge2D e2){
        int i = 0;
        vertex = new Point2D[3];
        if(!alreadyGotVertex(e0.getA()))
            vertex[i++] = e0.getA();
        if(!alreadyGotVertex(e0.getB()))
            vertex[i++] = e0.getB();
        if(!alreadyGotVertex(e1.getA()))
            vertex[i++] = e1.getA();
        if(!alreadyGotVertex(e1.getB()))
            vertex[i++] = e1.getB();
        if(!alreadyGotVertex(e2.getA()))
            vertex[i++] = e2.getA();
        if(!alreadyGotVertex(e2.getB()))
            vertex[i++] = e2.getB();
    }
//    public Triangle2D(Point2D v0, Point2D v1, Point2D v2){
//        vertex = new Point2D[3];
//        vertex[0] = v0;
//        vertex[1] = v1;
//        vertex[2] = v2;
//    }
    public Point2D getVertex(int i){
        if (testIndex(i))
            return vertex[i];
        return null;
    }
    private boolean testIndex(int i){
        return i >= 0 && i < vertex.length;
    }
    public void setVertex(int i, Point2D p){
        if (testIndex(i))
            vertex[i] = p;
        else
            throw new UnsupportedOperationException("Invalid vertex index");
    }
    public int length(){
        return vertex.length;
    }
    @Override
    public String toString(){
        String tor = "";
        for (int i = 0; i < vertex.length; i++)
            tor += vertex[i].toString();
        return tor;
    }
    public boolean inDealunayCircle(Point2D dp){
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
}
