/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

// Interface
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;

// Image manipulation
import javax.media.jai.*;
import java.awt.image.*;
import java.awt.Color;

// Events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// Logics


/**
 *
 * @author Samuel
 */
public class ImagePanel extends JPanel implements MouseListener{
    private static final int M_WIDTH = 300;
    private static final int M_HEIGHT = 300;
    private static final int POINT_DIM = 1;
    private static final Color POINT_COLOR = Color.red;
    private static final Color EDGE_COLOR = Color.orange;
    //private RenderedImage image;
    private Triangulation triangulation;
    
    public ImagePanel(String imageFile){
        triangulation = new Triangulation(
                new Vector2D(-1.0, -1.0*M_HEIGHT),
                new Vector2D(-1.0, M_HEIGHT + 1.0),
                new Vector2D(2.0*M_WIDTH, M_HEIGHT + 1.0));
        addMouseListener(this);
        setOpaque(true);
    }
    private void drawPoint(Graphics g, Vector2D p){
        int x = (int)p.getCoord(0);
        int y = (int)p.getCoord(1);
        Color prevCol = g.getColor();
        g.setColor(POINT_COLOR);
        for(int i = x - POINT_DIM; i <= x + POINT_DIM; i++)
            for(int j = y - POINT_DIM; j <= y + POINT_DIM; j++)
                g.drawLine(i, j, i, j);
        g.setColor(prevCol);
    }
    private void drawEdge(Graphics g, Edge2D e){
        Color prevCol = g.getColor();
        g.setColor(EDGE_COLOR);
        g.drawLine(
                (int)e.org().getCoord(0), 
                (int)e.org().getCoord(1), 
                (int)e.dest().getCoord(0), 
                (int)e.dest().getCoord(1));
        g.setColor(prevCol);
    }
    private void drawTriangulation(Graphics g, Triangulation t){
        for(Edge2D edge: t.getEdgeList())
            drawEdge(g, edge);
        for(Vector2D point: t.getPointCloud())
            drawPoint(g, point);
    }
    @Override
    public void paint(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT);
        drawTriangulation(g, triangulation);
    }
    private void manageNewPoint(Vector2D p){
        triangulation.addPoint(p);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(M_WIDTH, M_HEIGHT);
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        manageNewPoint(new Vector2D(me.getX(), me.getY()));
        repaint();
    }
    @Override
    public void mouseClicked(MouseEvent me) {}
    @Override
    public void mousePressed(MouseEvent me) {}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {} 
}