import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

 public class Enemy {

        
		Image img;
		int speed;
		int x, y, height, width;
        int health;
		static int score, score2;
		static int health1, health2;
        Random gen;
        Rectangle bbox;
        long firingTimer;
        long firingDelay;

        
        // Enemy class constructor
        Enemy(Image img, int speed, Random gen) {
            this.img = img;
            //bbox = new Rectangle(Math.abs(gen.nextInt() % (750 - 30)), -50, img.getWidth(null), img.getHeight(null));
            x = Math.abs(gen.nextInt() % (750 - 30));
            y = -50;
            this.speed = speed;
            this.gen = gen; // Random generator variable
            //this.type = type;
            width = img.getWidth(null); // why null?
            height = img.getHeight(null);
            score = 0;
            score2 = 0;
           
            firingTimer = System.nanoTime();
            firingDelay = Math.abs(gen.nextInt() % 3000); // fire delay of the bullet
           
            System.out.println("w:" + width + " y:" + height);// what do we need this for?
       }
        
        //FUNCTIONS
        public double getx(){ return x; }
        public double gety() {return y; }
        public double getr() {return width; } // to get x coordinate of the boundary box of the enemy image
        public static int getScore() { return score; }
        public static int getScore2() { return score2; }
       
        
        

        public void update() {
        	y += speed; // moves the enemy plain in the y-axis
            	if (y >= game1942WithoutObserver.h) { // repositions enemy plain back to the top ones it goes out of the frame
            		this.reset();
            	}
            	 // fires enemy bullets
            	long elapsed = (System.nanoTime() - firingTimer) / 1000000;
				if(elapsed > firingDelay){
					game1942WithoutObserver.ebullet.add(
							new Bullet(game1942WithoutObserver.enemyBullet, x, (y + 5), 3, Bullet.Owner.ENEMY)); // sets the bullet at the position of Enemy
        			firingTimer = System.nanoTime();
				}
            //}
				
				// CHECK FOR m AND enemy COLLISION
					if(game1942WithoutObserver.m.collision(x, y, width - 15, height - 15)){
						//health1--;															
						if(game1942WithoutObserver.m.getLives()>0) {
							game1942WithoutObserver.m.looseLife(); // update m life count
							score++; // updates score only of m has more then 0 lives
							
							// draws the small explosion
							game1942WithoutObserver.explosion3.add(new Explosion((x), (y), 
									game1942WithoutObserver.eExpl1,
									game1942WithoutObserver.eExpl2,
									game1942WithoutObserver.eExpl3,
									game1942WithoutObserver.eExpl4,
									game1942WithoutObserver.eExpl5,
									game1942WithoutObserver.eExpl6));
							
			                this.reset(); 
						}

				}
				
				// CHECK FOR m2 AND enemy COLLISION
				else if(game1942WithoutObserver.m2.collision(x, y, width - 15, height - 15)) {
					if(game1942WithoutObserver.m2.getLives2()>0){ // updates score only of m2 has more then 0 lives
						score2++;
							game1942WithoutObserver.explosion3.add(new Explosion((x), (y), 
									game1942WithoutObserver.eExpl1,
									game1942WithoutObserver.eExpl2,
									game1942WithoutObserver.eExpl3,
									game1942WithoutObserver.eExpl4,
									game1942WithoutObserver.eExpl5,
									game1942WithoutObserver.eExpl6));
										
							game1942WithoutObserver.m2.looseLife2();
							this.reset(); // call the reset() to reset the enemy plain to new random position    
					}
				}						
        	}
        
        // Resets the enemy plane back to the top of the 
        // screen and in a new random x-position
        public void reset() {
            this.x = Math.abs(game1942WithoutObserver.generator.nextInt() % (600 - 30));
            this.y = -50; 
        }

        public void draw(ImageObserver obs) {
            	game1942WithoutObserver.g2.drawImage(img, this.x, this.y, obs);
        }
    }

