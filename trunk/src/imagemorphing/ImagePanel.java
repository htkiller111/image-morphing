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
import java.awt.geom.AffineTransform;

// Events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.LinkedList;

// Logics


/**
 *
 * @author Samuel
 */
public class ImagePanel extends JPanel implements MouseListener{
    private static final int M_WIDTH = 600;
    private static final int M_HEIGHT = 600;
    private static final int POINT_DIM = 1;
    private static final Color POINT_COLOR = Color.red;
    private static final Color EDGE_COLOR = Color.orange;
    private static final Color CIRCLE_COLOR = Color.LIGHT_GRAY;
    //private RenderedImage image;
    private Triangulation triangulation;
    
    private RenderedImage sourceImage;
    
    private BufferedImage buffer;
    private BufferedImage sourceBuffer;
    
// Debug
//    private static final int CENTER_FACTOR = 10;
    
    public ImagePanel(String imageFile){
        sourceImage = JAI.create("fileload", imageFile);
        
        triangulation = new Triangulation(
                new Vector2D(-1.0, -1.0*M_HEIGHT),
                new Vector2D(-1.0, M_HEIGHT + 1.0),
                new Vector2D(2.0*M_WIDTH, M_HEIGHT + 1.0));
        
        buffer = new BufferedImage(
                M_WIDTH, 
                M_HEIGHT, 
                BufferedImage.TYPE_INT_ARGB);
        
// Debug
//      triangulation = new Triangulation(
//                new Vector2D(CENTER_FACTOR, CENTER_FACTOR),
//                new Vector2D(CENTER_FACTOR, M_HEIGHT - CENTER_FACTOR),
//                new Vector2D(M_WIDTH - CENTER_FACTOR, M_HEIGHT - CENTER_FACTOR));
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
// Debug
//    private void drawCircle(Graphics g, Circle2D circle){
//        Vector2D c = circle.getCenter();
//        double r = circle.getRadious();
//        Color prevCol = g.getColor();
//        g.setColor(CIRCLE_COLOR);
//        g.drawOval(
//                (int)(c.getCoord(0) - r), 
//                (int)(c.getCoord(1) - r), 
//                (int)(2*r), 
//                (int)(2*r));
//        g.setColor(prevCol);
//    }
    private void drawTriangulation(Graphics g, Triangulation t){
        Vector2D[] startingPoints = t.getStartingPoints();
//        for (Circle2D circle:t.circles)
//            drawCircle(g, circle);
        for(Edge2D edge: t.getEdgeList()){
            if(!(
                    edge.contains(startingPoints[0]) ||
                    edge.contains(startingPoints[1]) ||
                    edge.contains(startingPoints[2])))
                drawEdge(g, edge);
        }
        for(Vector2D point: t.getPointCloud()){
            if(!(
                    point == startingPoints[0] ||
                    point == startingPoints[1] ||
                    point == startingPoints[2]))
                drawPoint(g, point);
        }
            
    }
    @Override
    public void paint(Graphics g){
        // New buffer
        buffer = new BufferedImage(
                M_WIDTH, 
                M_HEIGHT, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics bg = buffer.getGraphics();
        bg.setColor(Color.white);
        bg.fillRect(0, 0, 2*M_WIDTH, 2*M_HEIGHT);
        ((Graphics2D)bg).drawRenderedImage(
                sourceImage, 
                new AffineTransform());
        //drawTriangulation(bg, triangulation);
        
        java.util.LinkedList<Edge2D> edges = new java.util.LinkedList<Edge2D>();
        
        // source
        edges.add(new Edge2D(new Vector2D(130, 200), new Vector2D(100, 250)));
        edges.add(new Edge2D(new Vector2D(100, 250), new Vector2D(200, 300)));
        edges.add(new Edge2D(new Vector2D(200, 300), new Vector2D(130, 200)));
        Triangle2D stryangle = new Triangle2D(edges.get(0), edges.get(1), edges.get(2));
        
        // destination
        edges.add(new Edge2D(new Vector2D(400, 200), new Vector2D(400, 300)));
        edges.add(new Edge2D(new Vector2D(400, 300), new Vector2D(500, 300)));
        edges.add(new Edge2D(new Vector2D(500, 300), new Vector2D(400, 200)));
        Triangle2D dtryangle = new Triangle2D(edges.get(3), edges.get(4), edges.get(5));
        
        for(Edge2D edge: edges)
            drawEdge(bg, edge);
        
        mapTexture(bg, stryangle, dtryangle);
        
        g.drawImage(buffer, 0, 0, this);
    }
    private void mapTexture(Graphics g, Triangle2D s, Triangle2D d){
        
        g.setColor(Color.green);
        int[] box = d.minimumSuroundingBox();
        Vector2D test = new Vector2D(0,0);
        int i_argb = 0;
        int[] test_argb = new int[4];
        int xo, xf;
        double[] barycentric_d = new double[3];
        double[] cartesian_s = new double[2];
        
        HashMap<Integer, LinkedList<Integer>> levels = 
                LAT.levels(d);
        for(Integer level: levels.keySet()){
            xo = levels.get(level).get(0);
            xf = levels.get(level).get(1);
            for(int x = xo; x <= xf; x++){
                barycentric_d = LAT.getBarycentricCoords(x, level, d);
                cartesian_s = LAT.restoreBarycentricCoords(
                        barycentric_d[0],
                        barycentric_d[1],
                        barycentric_d[2],
                        s);
                i_argb = buffer.getRGB((int)cartesian_s[0], (int)cartesian_s[1]);
                g.setColor(new Color(i_argb));
//                test_argb = constructARGB(i_argb);
//                g.setColor(new Color(
//                        test_argb[1], 
//                        test_argb[2], 
//                        test_argb[3], 
//                        test_argb[0]));
                
                g.drawLine(x, level, x, level);
            }
            //g.drawLine(xo, level, xf, level);
        }
        
    }
    private void manageNewPoint(Vector2D p){
        //triangulation.addPoint(p);
    }
    private int[] constructARGB(int argb){
        int[] tor = new int[4];
        String s_argb = Integer.toBinaryString(argb);
        tor[0] = Integer.parseInt(s_argb.substring(0, 8), 2);
        tor[1] = Integer.parseInt(s_argb.substring(8, 16), 2);
        tor[2] = Integer.parseInt(s_argb.substring(16, 24), 2);
        tor[3] = Integer.parseInt(s_argb.substring(24, 32), 2);
        return tor;
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
    public void mousePressed(MouseEvent me) {
        constructARGB(buffer.getRGB(me.getX(), me.getY()));
    }
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {} 
}