import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

    public class Island {

        Image img;
        int x, y, speed;
        Random gen;
        
        //Constructor of Island Class
        Island(Image img, int x, int y, int speed, Random gen) {
            this.img = img;
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.gen = gen;
        }

        public void update() {
            y += speed;
            if (y >= game1942WithoutObserver.h) {
                y = -50;
                x = Math.abs(gen.nextInt() % (game1942WithoutObserver.w - 30)); 
            }
        }
        // drawImage() is a java built in method
        public void draw(ImageObserver obs) {
        	game1942WithoutObserver.g2.drawImage(img, x, y, obs); // draws island images
        }
    }

