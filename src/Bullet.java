import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Bullet {

	public static enum Owner {PLAYER1, PLAYER2, ENEMY};
	
        Image img;
		int height;
		int speed;
        Rectangle bbox;
        boolean boom;
        Owner owner; 

      Bullet(Image img, int x, int y, int speed, Owner owner) {
    	  
    	  	bbox = new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
            this.img = img;
            this.speed = speed;
            boom = false;                
            this.owner = owner;
        }
      
      	// FUNCTONS
        public double getx(){ return bbox.x; }
        public double gety() {return bbox.y; }
        public double getr() {return bbox.width; }
        public Owner getOwner() {return owner;}
             
		public boolean update() {
			
            bbox.y += speed;
           
           // bounce checking
            if(bbox.y > game1942WithoutObserver.h || bbox.y < - bbox.height){
            	return true;
            }    
           return false;
          
        }

        public void draw(ImageObserver obs) {
        	game1942WithoutObserver.g2.drawImage(img, bbox.x, bbox.y, obs); 
        }
        
        
        public boolean collision(Rectangle otherBBox) {
            
            if(bbox.intersects(otherBBox)) { 
                return true;
            }
            return false;
        }
        
    }