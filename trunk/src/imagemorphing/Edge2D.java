/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;
import java.io.Serializable;
/**
 *
 * @author Samuel
 */
public class Edge2D implements Serializable{
    private Vector2D o;
    private Vector2D d;
    public static final double THRESHOLD = 2.0;
    /*private Triangle2D left;
    private Triangle2D right;
    public Edge2D(Vector2D na, Vector2D nb, Triangle2D l, Triangle2D r){
        o = na;
        d = nb;
        left = l;
        right = r;
    }
     * 
     */
    public Edge2D(Vector2D na, Vector2D nb){
        o = na;
        d = nb;
        /*left = null;
        right = null;
         * 
         */
    }
    /*public Triangle2D getLeft(){return left;}
    public Triangle2D getRight(){return right;}
    public void setLeft(Triangle2D l){left = l;}
    public void setRight(Triangle2D r){right = r;}
     * 
     */
    public Vector2D org(){return o;}
    public Vector2D dest(){return d;}
    public void setOrg(Vector2D na){o = na;}
    public void setDest(Vector2D nb){d = nb;}
    public Vector2D getDirectionVector(){return (Vector2D)d.sub(o);}
    public boolean shareWith(Edge2D e){
        return 
                e.org() == org() || 
                e.org() == dest() || 
                e.dest() == org() || 
                e.dest() == dest();
    }
    public boolean contains(Vector2D p){
        return p == o || p == d;
    }
    @Override
    public String toString(){
        return "[" + o.toString() + d.toString() + "]";
    }
    public boolean equals(Object o){
        if(o instanceof Edge2D)
            return this == o;
        return false;
    }
    private boolean between(double a, double b, double c){
        if(a <= b)
            return a <= c && c <= b;
        return b <= c && c <= a;
    }
    public boolean intersects(Vector2D p){
        double 
                ax = o.getCoord(0), 
                ay = o.getCoord(1),
                bx = d.getCoord(0), 
                by = d.getCoord(1),
                px = p.getCoord(0), 
                py = p.getCoord(1);
        
        if(Math.abs(bx - ax) <= THRESHOLD){
            if(px == ax)
                if(between(ay, by, py))
                    return true;
        }else{
            double c = (px - ax) / (bx - ax);
            return 
                    (0 <= c && c <= 1) && 
                    Math.abs(ay - py + c*(by - ay)) <= THRESHOLD;
        }
        return false;
    }
}
