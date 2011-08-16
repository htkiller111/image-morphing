/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class Vector implements Comparable{
    protected double[] coords;
    public static final int THRESHOLD = 3;
    protected int id;
    
    public Vector(int dim){
        coords = new double[dim];
        id = -1;
    }
    public void setId(int i){
        id = i;
    }
    public int getId(){
        return id;
    }
    protected boolean testIndex(int i){
        return (i >=0) && (i < coords.length);
    }
    public double getCoord(int i){
        if(testIndex(i))
            return coords[i];
        throw new UnsupportedOperationException("Coordinate out of range " + i);
    }
    public void setCoord(int i, double v){
        if(testIndex(i)){
            coords[i] = v;
            return;
        }
        throw new UnsupportedOperationException("Coordinate out of range " + i);
    }
    public double dot(Vector v){
        double tor = 0.0;
        for (int i = 0; i < coords.length; i++){
            tor += v.getCoord(i) * coords[i];
        }
        return tor;
    }
    public Vector add(Vector v){
        Vector tor = new Vector(coords.length);
        for (int i = 0; i < coords.length; i++){
            tor.setCoord(i, coords[i] + v.getCoord(i));
        }
        return tor;
    }
    public Vector sub(Vector v){
        Vector tor = new Vector(coords.length);
        for (int i = 0; i < coords.length; i++){
            tor.setCoord(i, coords[i] - v.getCoord(i));
        }
        return tor;
    }
    public Vector mul(double c){
        Vector tor = new Vector(coords.length);
        for (int i = 0; i < coords.length; i++){
            tor.setCoord(i, c*coords[i]);
        }
        return tor;
    }
    public double getNorm(){
        return Math.sqrt(dot(this));
    }
    public Vector cross(Vector v){
        return null;
    }
    @Override
    public int compareTo(Object t) {
        if (t == null)
            return -1;
        if (t instanceof Vector)
            return sub((Vector)t).getNorm() <= THRESHOLD ? 0 : -1;
        return -1;
    }
    @Override
    public String toString(){
        String tor = "";
        for(int i = 0; i < coords.length; i++)
            tor += getCoord(i) + (i == coords.length - 1 ? "": ", ");
        return "(" + tor + ")";
    }
}
