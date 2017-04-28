import java.awt.Image;
import java.awt.image.ImageObserver;

    public class Health {

        Image img;
        int x, y;
        
        //Constructor of Island Class
        Health(Image img, int x, int y) {
            this.img = img;
            this.x = x;
            this.y = y;
           
        }

        public void update() {}
        
        // drawImage() is a java built in method
        public void draw(ImageObserver obs) {
        	game1942WithoutObserver.g2.drawImage(img, x, y, obs); // draws island images
        }
    }
