import processing.core.*; 
import processing.xml.*; 

import processing.video.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class exporter extends PApplet {



MovieMaker mm;
PImage[] imageFrames;
int index;

public void setup() {
  size(430, 400);
  int numFrames = 30;
  imageFrames = new PImage[numFrames];
  for( int i = 0; i < imageFrames.length; i++ ){
    imageFrames[i] = loadImage( "f" + i + ".jpg" );
  }
  mm = new MovieMaker(this, width, height, "../drawing.mov");
}

public void draw() {
  if( index < imageFrames.length ){
    image( imageFrames[index], 0, 0 );
    mm.addFrame();
    index++;
  }
  else{
    mm.finish();
    exit();    
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#ECE9D8", "exporter" });
  }
}
