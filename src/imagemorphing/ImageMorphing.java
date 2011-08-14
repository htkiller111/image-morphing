/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

import javax.swing.JFrame;
/**
 *
 * @author Samuel
 */
public class ImageMorphing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        JFrame window = new JFrame();
        window.add(new ImagePanel("megusta.jpg"));
        window.pack();
        window.setVisible(true);
        //window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
