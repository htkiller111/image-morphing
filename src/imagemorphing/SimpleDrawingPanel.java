/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

// Interface
import javax.swing.JPanel;
import java.awt.Graphics;

// Image manipulation
import java.awt.image.*;
import java.awt.Color;

/**
 *
 * @author Samuel
 */
public class SimpleDrawingPanel extends JPanel{
    public static final int M_WIDTH = 430, M_HEIGHT = 400;
    private BufferedImage buffer;
    public SimpleDrawingPanel(){
        resetPanel();
    }
    public final void resetPanel(){
        buffer = new BufferedImage(
                M_WIDTH, 
                M_HEIGHT, 
                BufferedImage.TYPE_INT_ARGB);
        setOpaque(true);
        repaint();
    }
    @Override
    public void paint(Graphics g){
        g.drawImage(buffer, 0, 0, this);
    }
    public void clr(){
        Graphics bg = buffer.getGraphics();
        bg.setColor(Color.WHITE);
        bg.fillRect(0, 0, M_WIDTH, M_HEIGHT);
    }
    public Graphics getDBufferGraphics(){
        return buffer.getGraphics();
    }
}