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
            Graphics pencil,
            int xa,
            int ya){
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
                pencil.drawLine(x + xa, level + ya, x + xa, level + ya);
            }
        }
    }
    public static void mapTexture(
            BufferedImage sourceBuffer, 
            Triangulation sTriangulation, 
            Triangulation dTriangulation, 
            Graphics pencil){
        double[] box = dTriangulation.getMinimumBox();
        double W = box[2] - box[0], H = box[3] - box[1];
        int 
                xa = (int)(((SimpleDrawingPanel.M_WIDTH  - W) / 2.0) - box[0]),
                ya = (int)(((SimpleDrawingPanel.M_HEIGHT - H) / 2.0) - box[1]);
        Triangle2D st, dt;
        Vector2D[] stVex = sTriangulation.getStartingPoints();
        for(int i = 0; i < dTriangulation.getTriangleList().size(); i++){
            st = sTriangulation.getTriangleList().get(i);
            if(!(
                    st.contains(stVex[0]) || 
                    st.contains(stVex[1]) || 
                    st.contains(stVex[2]))){
                dt = dTriangulation.getTriangleList().get(i);
                mapTriangleTexture(sourceBuffer, st, dt, pencil, xa, ya);
            }
        }
    }
    
    
    
    
    
    public static void mixMapTriangleTexture(
            BufferedImage sourceBuffer, 
            Triangle2D sourceTriangle,
            Triangle2D intermediateTriangle,
            BufferedImage destBuffer,
            Triangle2D destinationTriangle, 
            Graphics pencil,
            double degree,
            int xa,
            int ya){
        int i_argb = 0, i_argbd = 0;
        int xo, xf;
        double[] barycentric_i = new double[3];
        double[] cartesian_s = new double[2];
        double[] cartesian_d = new double[2];
        HashMap<Integer, LinkedList<Integer>> levels = 
                LAT.levels(intermediateTriangle);
        for(Integer level: levels.keySet()){
            xo = levels.get(level).get(0);
            xf = levels.get(level).get(1);
            for(int x = xo; x <= xf; x++){
                
                barycentric_i = LAT.getBarycentricCoords(
                        x, 
                        level, 
                        intermediateTriangle);
                
                // Map AB
                cartesian_s = LAT.restoreBarycentricCoords(
                        barycentric_i[0],
                        barycentric_i[1],
                        barycentric_i[2],
                        sourceTriangle);
                i_argb = sourceBuffer.getRGB(
                        (int)Math.abs(cartesian_s[0]), 
                        (int)Math.abs(cartesian_s[1]));
                
                // Map BA
                cartesian_d = LAT.restoreBarycentricCoords(
                        barycentric_i[0],
                        barycentric_i[1],
                        barycentric_i[2],
                        destinationTriangle);
                i_argbd = destBuffer.getRGB(
                        (int)Math.abs(cartesian_d[0]), 
                        (int)Math.abs(cartesian_d[1]));
                
                // Mix
                pencil.setColor(mixColors(i_argb, i_argbd, degree));
                pencil.drawLine(x + xa, level + ya, x + xa, level + ya);
            }
        }
    }
    public static void mixMapTexture(
            BufferedImage sourceBuffer, 
            Triangulation sTriangulation,
            Triangulation iTriangulation,
            BufferedImage destBuffer,
            Triangulation dTriangulation, 
            Graphics pencil,
            double degree){
        double[] box = iTriangulation.getMinimumBox();
        double W = box[2] - box[0], H = box[3] - box[1];
        int 
                xa = (int)(((SimpleDrawingPanel.M_WIDTH  - W) / 2.0) - box[0]),
                ya = (int)(((SimpleDrawingPanel.M_HEIGHT - H) / 2.0) - box[1]);
        Triangle2D st, it, dt;
        Vector2D[] stVex = sTriangulation.getStartingPoints();
        for(int i = 0; i < iTriangulation.getTriangleList().size(); i++){
            st = sTriangulation.getTriangleList().get(i);
            if(!(
                    st.contains(stVex[0]) || 
                    st.contains(stVex[1]) || 
                    st.contains(stVex[2]))){
                it = iTriangulation.getTriangleList().get(i);
                dt = dTriangulation.getTriangleList().get(i);
                mixMapTriangleTexture(
                        sourceBuffer, 
                        st,
                        it,
                        destBuffer, 
                        dt, 
                        pencil, 
                        degree,
                        xa, 
                        ya);
            }
        }
    }
    
    
    
    
    
    
    private static Color mixColors(int rgb1, int rgb2, double c){
        Color result = new Color(
                (int)(((rgb1 & 0x00FF0000) >> 16)*(1 - c) + 
                ((rgb2 & 0x00FF0000) >> 16)*c),
                (int)(((rgb1 & 0x0000FF00) >> 8)*(1 - c) + 
                ((rgb2 & 0x0000FF00) >> 8)*c),
                (int)((rgb1 & 0x000000FF)*(1 - c) + 
                (rgb2 & 0x000000FF)*c));
        return result;
    }
}
