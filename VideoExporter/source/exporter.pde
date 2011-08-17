import processing.video.*;

MovieMaker mm;
PImage[] imageFrames;
int index;

void setup() {
  size(430, 400);
  int numFrames = 90;
  imageFrames = new PImage[numFrames];
  for( int i = 0; i < imageFrames.length; i++ ){
    imageFrames[i] = loadImage( "f" + i + ".jpg" );
  }
  mm = new MovieMaker(this, width, height, "../drawing.mov");
}

void draw() {
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
