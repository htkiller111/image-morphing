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

// Events
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Samuel
 */
public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener{
    private int M_WIDTH, M_HEIGHT;
    private static final int POINT_DIM = 1;
    private static final Color POINT_COLOR = Color.red;
    private static final Color EDGE_COLOR = Color.orange;
    private Triangulation triangulation;
    private RenderedImage sourceImage;
    private BufferedImage buffer;
    private BufferedImage sourceBuffer;
    private EditMeshPanel destination;
    private Vector2D movingPoint;
    public ImagePanel(){
        resetPanel();
    }
    public void setTriangulation(Triangulation t){
        triangulation = t;
    }
    public final void resetPanel(){
        M_WIDTH = 425;
        M_HEIGHT = 400;
        movingPoint = null;
        triangulation = new Triangulation(
                new Vector2D(-1.0, -1.0*M_HEIGHT),
                new Vector2D(-1.0, M_HEIGHT + 1.0),
                new Vector2D(2.0*M_WIDTH, M_HEIGHT + 1.0));
        
        buffer = new BufferedImage(
                M_WIDTH, 
                M_HEIGHT, 
                BufferedImage.TYPE_INT_ARGB);
        
        // Load source image in source buffer
        sourceBuffer = new BufferedImage(
                M_WIDTH, 
                M_HEIGHT, 
                BufferedImage.TYPE_INT_ARGB);
        sourceImage = null;
        destination = new EditMeshPanel();
        addMouseListener(this);
        addMouseMotionListener(this);
        setOpaque(true);
        repaint();
    }
    public void setDestinationPanel(EditMeshPanel d){
        destination = d;
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
        Vector2D[] startingPoints = t.getStartingPoints();
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
    public void loadImage(String imageFile){
        try{sourceBuffer = javax.imageio.ImageIO.read(new java.io.File(imageFile));}
        catch(Exception e){System.out.println("File not found." + e  + imageFile);}
        repaint();
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
        bg.fillRect(0, 0, M_WIDTH, M_HEIGHT);
        bg.drawImage(sourceBuffer, 0, 0, this);
        drawTriangulation(bg, triangulation);
        g.drawImage(buffer, 0, 0, this);
    }
    private boolean inBounds(int x, int y){
        return (0 <= x && x <= M_WIDTH)&&(0 <= y && y <= M_HEIGHT);
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        if(inBounds(me.getX(), me.getY())){
            if(me.getButton() == MouseEvent.BUTTON3){
                triangulation.addPoint(new Vector2D(me.getX(), me.getY()));
                destination.putTriangulation(triangulation);
                repaint();
            }
            else{
                movingPoint = null;
            }
        }
    }
    public BufferedImage getSourceBuffer(){
        return sourceBuffer;
    }
    public Graphics getDBufferGraphics(){
        return buffer.getGraphics();
    }
    public Triangulation getTriangulation(){
        return triangulation;
    }
    @Override
    public void mouseClicked(MouseEvent me) {}
    @Override
    public void mousePressed(MouseEvent me) {
        if(me.getButton() == MouseEvent.BUTTON1){
            int x = me.getX(), y = me.getY();
            for(Vector2D p : triangulation.getPointCloud()){
                if(p.compareTo(new Vector2D(x, y)) == 0)
                    movingPoint = p;
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseDragged(MouseEvent me) {
        int x = me.getX(), y = me.getY();
        if(movingPoint != null){
            movingPoint.setCoord(0, x);
            movingPoint.setCoord(1, y);
            repaint();
        } 
    }

    @Override
    public void mouseMoved(MouseEvent me) {}
}