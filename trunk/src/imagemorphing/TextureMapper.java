/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

// Image manipulation
import java.awt.Graphics;
import java.awt.image.*;
import java.awt.Color;

// Logics
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Samuel
 */
public class TextureMapper {
    public static void mapTriangleTexture(
            BufferedImage sourceBuffer, 
            Triangle2D sourceTriangle, 
            Triangle2D destinationTriangle, 
            Graphics pencil){
        int i_argb = 0;
        int xo, xf;
        double[] barycentric_d = new double[3];
        double[] cartesian_s = new double[2];
        HashMap<Integer, LinkedList<Integer>> levels = 
                LAT.levels(destinationTriangle);
        for(Integer level: levels.keySet()){
            xo = levels.get(level).get(0);
            xf = levels.get(level).get(1);
            for(int x = xo; x <= xf; x++){
                barycentric_d = LAT.getBarycentricCoords(
                        x, 
                        level, 
                        destinationTriangle);
                cartesian_s = LAT.restoreBarycentricCoords(
                        barycentric_d[0],
                        barycentric_d[1],
                        barycentric_d[2],
                        sourceTriangle);
                i_argb = sourceBuffer.getRGB(
                        (int)Math.abs(cartesian_s[0]), 
                        (int)Math.abs(cartesian_s[1]));
                pencil.setColor(new Color(i_argb));
                pencil.drawLine(x, level, x, level);
            }
        }
    }
    public static void mapTexture(
            BufferedImage sourceBuffer, 
            Triangulation sTriangulation, 
            Triangulation dTriangulation, 
            Graphics pencil){
        Triangle2D st, dt;
        Vector2D[] stVex = sTriangulation.getStartingPoints();
        for(int i = 0; i < dTriangulation.getTriangleList().size(); i++){
            st = sTriangulation.getTriangleList().get(i);
            if(!(
                    st.contains(stVex[0]) || 
                    st.contains(stVex[1]) || 
                    st.contains(stVex[2]))){
                dt = dTriangulation.getTriangleList().get(i);
                mapTriangleTexture(sourceBuffer, st, dt, pencil);
            }
        }
    }
}
