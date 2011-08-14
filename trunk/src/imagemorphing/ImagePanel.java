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
import java.util.LinkedList;

/**
 *
 * @author Samuel
 */
public class ImagePanel extends JPanel implements MouseListener{
    private static final int POINT_DIM = 1;
    private static final Color POINT_COLOR = Color.red;
    private static final Color EDGE_COLOR = Color.orange;
    private RenderedImage image;
    private LinkedList<Point2D> pointCloud;
    private LinkedList<Edge2D> edgeList;
    private LinkedList<Triangle2D> triangulation;
    public ImagePanel(String imageFile){
        pointCloud = new LinkedList<Point2D>();
        edgeList = new LinkedList<Edge2D>();
        triangulation = new LinkedList<Triangle2D>();
        addMouseListener(this);
        setOpaque(true);
    }
    private void drawTriangle(Graphics g, Triangle2D t){
        Color prevCol = g.getColor();
        g.setColor(EDGE_COLOR);
        int[] xs = {
            t.getVertex(0).getX(), 
            t.getVertex(1).getX(), 
            t.getVertex(2).getX(), 
            t.getVertex(0).getX()};
        int[] ys = {
            t.getVertex(0).getY(), 
            t.getVertex(1).getY(), 
            t.getVertex(2).getY(), 
            t.getVertex(0).getY()};
        g.drawPolyline(xs, ys, 4);
        g.setColor(prevCol);
    }
    private void drawEdge(Graphics g, Edge2D e){
        Color prevCol = g.getColor();
        g.setColor(EDGE_COLOR);
        g.drawLine(
                e.getA().getX(), 
                e.getA().getY(), 
                e.getB().getX(), 
                e.getB().getY());
        g.setColor(prevCol);
    }
    private void drawPoint(Graphics g, Point2D p){
        int x = p.getX();
        int y = p.getY();
        Color prevCol = g.getColor();
        g.setColor(POINT_COLOR);
        for(int i = x - POINT_DIM; i <= x + POINT_DIM; i++)
            for(int j = y - POINT_DIM; j <= y + POINT_DIM; j++)
                g.drawLine(i, j, i, j);
        g.setColor(prevCol);
    }
    @Override
    public void paint(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT);
        for (Edge2D edge: edgeList)
            drawEdge(g, edge);
        for(Point2D point: pointCloud)
            drawPoint(g, point);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        manageNewPoint(new Point2D(me.getX(), me.getY()));
        repaint();
    }
    private boolean registeredPoint(Point2D p){
        for(Point2D t: pointCloud)
            if(p.compareTo(t) == 0)
                return true;
        return false;
    }
    private void manageNewPoint(Point2D p){
        if(registeredPoint(p))
            return;
        switch (pointCloud.size()){
            case 0: break;
            case 1:
                edgeList.add(new Edge2D(pointCloud.get(0), p));
                break;
            case 2:
                edgeList.add(new Edge2D(pointCloud.get(0), p));
                edgeList.add(new Edge2D(pointCloud.get(1), p));
                triangulation.add(new Triangle2D(
                        edgeList.get(0), 
                        edgeList.get(1), 
                        edgeList.get(2)));
                break;
            default:
                for (Triangle2D triangle: triangulation){
                    if (p.insideOf(triangle)){
                        
                    }
                }
                break;
        }
        pointCloud.add(p);   
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