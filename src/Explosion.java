import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

    public class Explosion {

        Image[] img;
        Rectangle bbox;
        long currentTime = System.currentTimeMillis();
        long[] flashTimes = new long[]{
        	currentTime + 100,
        	currentTime + 200,
        	currentTime + 300,
        	currentTime + 400,
        	currentTime + 500
        };
        
        // CONSTRUCTOR
        Explosion(int x, int y, Image... img) {
            this.img = img;
            bbox = new Rectangle(x, y, img[0].getWidth(null), img[0].getHeight(null));
        }

        public void update() {
            
            
        }
        // drawImage() is a java built in method
        public void draw(ImageObserver obs) 
        {
        	currentTime = System.currentTimeMillis();
        	
        	for(int i=0; i<Math.min(img.length, flashTimes.length); i++)
        	{
        		if(currentTime < flashTimes[i])
            	{
            		game1942WithoutObserver.g2.drawImage(img[i], bbox.x, bbox.y, obs); // draws island images
            		break;
            	}	
        	}
        }
    }