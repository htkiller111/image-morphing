/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class Vector3D extends Vector{
    public Vector3D(double nx, double ny, double nz){
        super(3);
        coords[0] = nx;
        coords[1] = ny;
        coords[2] = nz;
    }
    public Vector3D sub(Vector3D b){
        Vector res = super.sub(b);
        return new Vector3D(res.getCoord(0), res.getCoord(1), res.getCoord(2));
    }
    public Vector3D cross(Vector3D q){
        return new Vector3D(
                getCoord(1)*q.getCoord(2) - getCoord(2)*q.getCoord(1),
                getCoord(2)*q.getCoord(0) - getCoord(0)*q.getCoord(2),
                getCoord(0)*q.getCoord(1) - getCoord(1)*q.getCoord(0));
    }
}
