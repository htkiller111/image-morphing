/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class Edge2D {
    private Vector2D o;
    private Vector2D d;
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
    
    @Override
    public String toString(){
        return "[" + o.toString() + d.toString() + "]";
    }
}
