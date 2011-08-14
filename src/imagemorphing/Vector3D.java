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
    @Override
    public Vector3D sub(Vector b){
        if(b instanceof Vector3D)
            return (Vector3D)super.sub(b);
        throw new UnsupportedOperationException("Cannot operate " + b);
    }
    public Vector3D cross(Vector3D q){
        return new Vector3D(
                getCoord(1)*q.getCoord(2) - getCoord(2)*q.getCoord(1),
                getCoord(2)*q.getCoord(0) - getCoord(0)*q.getCoord(2),
                getCoord(0)*q.getCoord(1) - getCoord(1)*q.getCoord(0));
    }
}
