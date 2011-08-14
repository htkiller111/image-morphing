/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

/**
 *
 * @author Samuel
 */
public class LAT {
    public static boolean sameSide(Vector3D p1, Vector3D p2, Vector3D a, Vector3D b){
        Vector3D cp1 = b.sub(a).cross(p1.sub(a));
        Vector3D cp2 = b.sub(a).cross(p2.sub(a));
        return cp1.dot(cp2) >= 0;
    }
    public boolean pointInTriangle(Vector2D v, Triangle2D t){
        Vector3D p = new Vector3D(v.getCoord(0), v.getCoord(1), 0);
        Vector2D[] vertexes = t.getVertexes();
        Vector3D a = new Vector3D(
                vertexes[0].getCoord(0),
                vertexes[0].getCoord(1),
                0);
        Vector3D b = new Vector3D(
                vertexes[1].getCoord(0),
                vertexes[1].getCoord(1),
                0);
        Vector3D c = new Vector3D(
                vertexes[2].getCoord(0),
                vertexes[2].getCoord(1),
                0);
        return LAT.sameSide(p, a, b, c) && 
                LAT.sameSide(p, b, a, c) &&
                LAT.sameSide(p, c, a, b);
    }
}
