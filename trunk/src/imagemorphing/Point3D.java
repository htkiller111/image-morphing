/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class Point3D{
    private int x, y, z;
    public Point3D(int nx, int ny, int nz){
        x = nx;
        y = ny;
        z = nz;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public int getZ(){return z;}
    public void setX(int nx){x = nx;}
    public void setY(int ny){y = ny;}
    public void setZ(int nz){z = nz;}
}
