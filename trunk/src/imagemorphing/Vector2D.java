/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class Vector2D extends Vector implements Comparable{
    
    public Vector2D(double nx, double ny){
        super(2);
        coords[0] = nx;
        coords[1] = ny;
    }
    public double getAngle(){
        double x = getCoord(0);
        double y = getCoord(1);
        double abx = Math.abs(x);
        double norm = getNorm();
        double fi = Math.acos(abx/norm);
        if(y < 0)
            if(x < 0)
                return Math.PI + fi;
            else
                return 2.0*Math.PI - fi;
        else
            if(x < 0)
                return Math.PI - fi;
            else
                return fi;
    }
    public boolean tengoAlLadoIzquierdoA(Vector2D v){
        double res = v.getAngle() - getAngle();
        if(res <= 0)
            res += 2.0*Math.PI;
        return res < Math.PI;
    }
    public Vector2D sub(Vector2D b){
        Vector res = super.sub(b);
        return new Vector2D(res.getCoord(0), res.getCoord(1));
    }
    /*public boolean insideOf(Triangle2D t){
        Point3D p = new Point3D(x, y, 0);
        Point3D a = new Point3D(
                t.getVertex(0).getX(),
                t.getVertex(0).getY(),
                0);
        Point3D b = new Point3D(
                t.getVertex(1).getX(),
                t.getVertex(1).getY(),
                0);
        Point3D c = new Point3D(
                t.getVertex(2).getX(),
                t.getVertex(2).getY(),
                0);
        return LAT.sameSide(p, a, b, c) && 
                LAT.sameSide(p, b, a, c) &&
                LAT.sameSide(p, c, a, b);
    }*/
}
