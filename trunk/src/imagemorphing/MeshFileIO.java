/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemorphing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Samuel
 */
public class MeshFileIO {
    public static void saveMesh(Triangulation s, Triangulation d, String fname){
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fname);
            out = new ObjectOutputStream(fos);
            out.writeObject(s);
            out.writeObject(d);
            out.close();
        } 
        catch (IOException ex) {
            System.out.println("Could not save mesh.");
        }
    }
    public static Triangulation[] loadMesh(String fname){
        FileInputStream fis = null;
        ObjectInputStream in = null;
        Triangulation[] result = new Triangulation[2];
        try {
            fis = new FileInputStream(fname);
            in = new ObjectInputStream(fis);
            result[0] = (Triangulation) in.readObject();
            result[1] = (Triangulation) in.readObject();
            in.close();
        } 
        catch (IOException ex) {
            System.out.println("Could not open mesh." + ex.getMessage());
            return null;
        } 
        catch (ClassNotFoundException ex) {
            System.out.println("Could not open mesh." + ex.getMessage());
            return null;
        }
        return result;
    }
}
