/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Samuel
 */
public class VideoExporter {
    private static final int FRAMES = 90;
    private static final String IMG_EXT = "jpg";
    private static final String EXPORT_URL = "VideoExporter/exporter.exe";
    private static final String EXPORT_PATH = "VideoExporter/";
    private BufferedImage sourceB;
    private Triangulation sourceT;
    private BufferedImage destB;
    private Triangulation destT;
    public VideoExporter(
            BufferedImage sB,
            Triangulation sT,
            BufferedImage dB,
            Triangulation dT){
        sourceB = sB;
        sourceT = new Triangulation();
        sourceT.rebuildFrom(sT);
        destB = dB;
        destT = new Triangulation();
        destT.rebuildFrom(dT);
    }
    public void export(){
        BufferedImage buffer = null;
        double degree = 0.0;
        Triangulation iT;
        for(int frame = 0; frame < FRAMES; frame++){
            iT = new Triangulation();
            iT.rebuildFrom(sourceT);
            buffer = new BufferedImage(
                    SimpleDrawingPanel.M_WIDTH,
                    SimpleDrawingPanel.M_HEIGHT,
                    BufferedImage.TYPE_INT_RGB);
            degree = (frame + 0.0) / FRAMES;
            iT.shapeTo(destT, degree);
            TextureMapper.mixMapTexture(
                    sourceB, 
                    sourceT, 
                    iT, 
                    destB, 
                    destT, 
                    buffer.getGraphics(), 
                    degree);
            save(buffer, "f" + frame + "." + IMG_EXT);
        }
        try{
            Runtime rt = Runtime.getRuntime() ;
            Process p = rt.exec(EXPORT_URL) ;
            Thread.sleep(2000);
            p.destroy();
        }
        catch(Exception e){
            System.out.println("Cannot generate movie: " + e.getMessage());
        }
    }
    private static void save(BufferedImage image, String fileName) {
        File file = new File(EXPORT_PATH + fileName);
        try{
            ImageIO.write(image, IMG_EXT, file);
        }
        catch(IOException e) {
            System.out.println("Write error for " + file.getPath() +
                               ": " + e.getMessage());
        }
    }
}
