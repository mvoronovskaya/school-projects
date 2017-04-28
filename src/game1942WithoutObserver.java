/*
 * @author Marina Voronovskaya

 * and open the template in the editor.
 */


import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Random;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.io.File;

/**
 *
 * @author Ilmi
 */
public class game1942WithoutObserver extends JApplet implements Runnable {

    private Thread thread;
    Image sea, myPlane, myPlane2, friendlyBullet, gameOverIm;
	static Image enemyBullet;
	static Image expl, expl2, expl3, expl4, expl5, expl6, eExpl1, eExpl2, eExpl3, eExpl4, eExpl5, eExpl6;
    Island I1, I2, I3;
    BufferedImage[] explosion;
    static Health health1, health2, health3, health4;
    static Health health11, health22, health33, health44;
    static MyPlane m, m2, go;
    static ArrayList<Enemy> enemy;
    static ArrayList<Enemy> enemy2;
    static ArrayList<Bullet> fbullet;
    static ArrayList<Bullet> ebullet;
    static ArrayList<Explosion> explosion1, explosion2, explosion3;
    private BufferedImage bimg;
    static Graphics2D g2;
    static Random generator = new Random(1234567); 
    static int w = 750; // fixed size window game 
	static int h = 800;
    int speed = 1, move = 0;
    public MyPlane myHealth1;
    boolean enemiesActive, gameOver;
    int score1 = 0;
    int score2 = 0;
    int width = 22;
    int height = 22;
    
   // Graphics2D g2 = null;
    ImageObserver observer;

    public void init() {
        
        setBackground(Color.white);
        setSize(w, h);
        Image island1, island2, island3, enemyImg, enemyImg2;
        Image h1, h2, h3, h4;

        try {
        	//sea = getSprite("Resources/water.png");
        	sea = ImageIO.read(new File("Resources/water.png"));
        	island1 = ImageIO.read(new File("Resources/island1.png"));
        	island2 = ImageIO.read(new File("Resources/island2.png"));
        	island3 = ImageIO.read(new File("Resources/island3.png"));
        	enemyImg = ImageIO.read(new File("Resources/enemy1_1.png"));
        	enemyImg2 = ImageIO.read(new File("Resources/enemy3_1.png"));
        	h1 = ImageIO.read(new File("Resources/health.png"));
        	h2 = ImageIO.read(new File("Resources/health1.png"));
        	h3 = ImageIO.read(new File("Resources/health2.png"));
        	h4 = ImageIO.read(new File("Resources/health3.png"));
        	expl = ImageIO.read(new File("Resources/explosion2_1.png")); // big explosion
        	expl2 = ImageIO.read(new File("Resources/explosion2_2.png")); 
        	expl3 = ImageIO.read(new File("Resources/explosion2_3.png")); // big explosion
        	expl4 = ImageIO.read(new File("Resources/explosion2_4.png")); 
        	expl5 = ImageIO.read(new File("Resources/explosion2_5.png")); // big explosion
        	expl6 = ImageIO.read(new File("Resources/explosion2_6.png")); 
        	eExpl1 = ImageIO.read(new File("Resources/explosion1_1.png")); // small explosion
        	eExpl2 = ImageIO.read(new File("Resources/explosion1_2.png"));
        	eExpl3 = ImageIO.read(new File("Resources/explosion1_3.png")); // small explosion
        	eExpl4 = ImageIO.read(new File("Resources/explosion1_4.png"));
        	eExpl5 = ImageIO.read(new File("Resources/explosion1_5.png")); // small explosion
        	eExpl6 = ImageIO.read(new File("Resources/explosion1_6.png"));
        	myPlane = ImageIO.read(new File("Resources/myplane_1.png"));
        	myPlane2 = ImageIO.read(new File("Resources/myplane_1.png"));
        	friendlyBullet = ImageIO.read(new File("Resources/bullet.png"));
        	enemyBullet = ImageIO.read(new File("Resources/enemybullet1.png"));
        	BufferedImage image = ImageIO.read(new File("Resources/explosion1_strip6.png"));
        	gameOverIm = ImageIO.read(new File("Resources/gameOver.png"));
    
        	AudioClip ac = getAudioClip(getCodeBase(), "Resources/background.mid"); // play background audio
        	ac.loop();
        	
        	
        	enemiesActive = true;
        	gameOver = false;
        	observer = this;
        
        	fbullet = new ArrayList <Bullet>();
        	ebullet = new ArrayList <Bullet>();
        	explosion1 = new ArrayList <Explosion>(); // big explosion
        	explosion2 = new ArrayList <Explosion>(); // small explosion
        	explosion3 = new ArrayList <Explosion>();
        	enemy = new ArrayList <Enemy>();
        	enemy2 = new ArrayList <Enemy>();
        	I1 = new Island(island1, 100, 100, speed, generator);
        	I2 = new Island(island2, 200, 400, speed, generator);
        	I3 = new Island(island3, 300, 200, speed, generator);
        	
       	 
            
        	m = new MyPlane(myPlane, 500, 650);
            m2 = new MyPlane(myPlane, 200, 650);
            go = new MyPlane(gameOverIm, 250, 650); // game over image
       
        	health1 = new Health(h1, 600, 750);
        	health2 = new Health(h2, 600, 750);       	
       		health3 = new Health(h3, 600, 750);       	
       		health4 = new Health(h4, 600, 750);
        		
       		health11 = new Health(h1, 10, 750);
       		health22 = new Health(h2, 10, 750);       	
       		health33 = new Health(h3, 10, 750);       	
       		health44 = new Health(h4, 10, 750);
       		
       		explosion = new BufferedImage[6];
       		
       		
       		for(int i = 0; i < explosion.length; i++){
       			explosion[i] = image.getSubimage(i*width+i, 0, width, height);
       		}
       		
        	// creates 3 new enemy type 1
        	for(int i = 1; i <= 3; i++){
        		enemy.add(new Enemy(enemyImg, 2, generator)); 
        	}

        	// creates 3 new enemy type 2
        	for(int i = 1; i <= 3; i++){
        		enemy2.add(new Enemy(enemyImg2, 2, generator)); 
        	}
        	
        }
        catch (Exception e) {
            System.out.print("No resources are found");
        }
    }

    public class KeyControl extends KeyAdapter {

        public int x, y;
        int speed;

        KeyControl(int x, int y, int speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
           
                case KeyEvent.VK_LEFT: // moves first plane to the left
                	m.move (-5,0);
                	break;
                case KeyEvent.VK_A: // 'a' button moves second plane to the left
                	m2.move (-5,0);
                	break;
                case KeyEvent.VK_RIGHT:
                    m.move (5,0);
    	        	break;
                case KeyEvent.VK_S: // 's' button moves m2 to the right
                    m2.move (5,0);
    	        	break;
                case KeyEvent.VK_UP:
                	m.move (0,-5);
    	        	break;
                case KeyEvent.VK_W: // 'w' button moves m2 up
                	m2.move (0,-5);
    	        	break;
                case KeyEvent.VK_DOWN:
                	m.move (0,5);
    	        	break;
                case KeyEvent.VK_Z: // 'z' button moves m2 down
                	m2.move (0,5);
    	        	break;
                case KeyEvent.VK_X: // m2 shoots a bullet using 'x' button
                	m2.setPlayer2Firing(true);
    	        	break;
                default:
                  if(e.getKeyChar() == ' '){
                	  m.setPlayer1Firing(true); // m shoots a bullet using space bar
                	  
                	  System.out.println("FIRE!!");
                  }			
            }
        }
        public void keyReleased(KeyEvent e) {
        	if(e.getKeyChar() == ' '){
        		m.setPlayer1Firing(false);
        	}
        	if(e.getKeyChar() == 'x'){
        		m2.setPlayer2Firing(false);
        	}
        	
        }
    }
   
    public class MyPlane {

    	// DATA FIELDS
        KeyControl key;
        Rectangle bbox;
        Image img; 
        int x, y, width, height, speed, move = 0;
        int life1, life2;
        long firingTimer;
        long firingDelay;
        
        boolean player1Firing = false;
        boolean player2Firing = false;
        boolean enemyFiring = false;
        
        // CONSTRUCTOR
        MyPlane(Image img, int x, int y) {
            this.img = img;
            this.x = x;
            this.y = y;
            //bbox = new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
            key = new KeyControl(x, y, speed);
            life1 = 4;
            life2 = 4;
            firingTimer = System.nanoTime();
            firingDelay = 200;
            width = img.getWidth(null);
            height = img.getHeight(null);
            
            addKeyListener(key);
            setFocusable(true);
        }
        
        // FUNCTIONS
        public void setPlayer1Firing(boolean b){ player1Firing = b;} // keyboard input for firing bullets
        public void setPlayer2Firing(boolean b){ player2Firing = b;} // keyboard input for firing bullets
        
        
        public void looseLife(){ life1--; }
        public void looseLife2(){ life2--; }
              
        public int getx(){ return this.x + 10; }
        public int gety() {return this.y + 10; }
        public int getr() {return this.width; }
          
        public void update(){
        	
        	// SETS THE BOUNDARY of MyPlane to stay with in the frame
        	if(x < 0){
        		speed = 0;
        		x = 0;
        	}
        	if(x > w - 60){
        		speed = 0; 
        		x = w - 60;
        	}
        	if(y < 0){
        		speed = 0;
        		y = 0;
        	}
        	if(y > h - 70){
        		speed = 0; 
        		y = h - 70;
        	}
        	
        	// EXPLOSION
        	if(life1 == 0){
        		//animation = new Animation(bbox.x, bbox.y);       		
        		if(explosion1.isEmpty())
        		{
        			explosion1.add(new Explosion((x), (y),
            				game1942WithoutObserver.expl,
    						game1942WithoutObserver.expl2,
    						game1942WithoutObserver.expl3,
    						game1942WithoutObserver.expl4,
    						game1942WithoutObserver.expl5,
    						game1942WithoutObserver.expl6));
        			AudioClip ac = getAudioClip(getCodeBase(), "Resources/snd_explosion2.wav");
                	ac.play();
        		}
        	}
        	if(life2 == 0){
        		if(explosion2.isEmpty())
        		{
        			explosion2.add(new Explosion((x), (y),
            				game1942WithoutObserver.expl,
    						game1942WithoutObserver.expl2,
    						game1942WithoutObserver.expl3,
    						game1942WithoutObserver.expl4,
    						game1942WithoutObserver.expl5,
    						game1942WithoutObserver.expl6));
        			
        			AudioClip ac = getAudioClip(getCodeBase(), "Resources/snd_explosion2.wav");
                	ac.play();           			
        		}
        	}
        	// FIRING BULLETS
        	if(player1Firing){
        		long elapsed = (System.nanoTime() - firingTimer) / 1000000;
        		if(elapsed > firingDelay){
        			if(life1 > 0 && life2 > 0){ // shoot bullets while lives are greater then zero
        				fbullet.add(new Bullet(friendlyBullet, (x+18), (y-10), -3, Bullet.Owner.PLAYER1)); // sets the bullet at the position of MyPlane
        				firingTimer = System.nanoTime();
        				
        				// firing sound
        				AudioClip ac = getAudioClip(getCodeBase(), "Resources/snd_explosion1.wav");
        	        	ac.play();
        				
        			}
        		}     		
        	}
        	
        	// FIRING BULLETS
        	if(player2Firing){
        		long elapsed = (System.nanoTime() - firingTimer) / 1000000;
        		if(elapsed > firingDelay){
        			if(life1 > 0 && life2 > 0){ // shoot bullets while lives are greater then zero
        				fbullet.add(new Bullet(friendlyBullet, (x+18), (y-10), -3, Bullet.Owner.PLAYER2)); // sets the bullet at the position of MyPlane
        				firingTimer = System.nanoTime();
        				
        				// firing sound
        				AudioClip ac = getAudioClip(getCodeBase(), "Resources/snd_explosion1.wav");
        	        	ac.play();
        			}
        		}     		
        	}
        	
        	// FIRING BULLETS
        	if(enemyFiring){
        		long elapsed = (System.nanoTime() - firingTimer) / 1000000;
        		if(elapsed > firingDelay){
        			if(life1 > 0 && life2 > 0){ // shoot bullets while lives are greater then zero
        				fbullet.add(new Bullet(friendlyBullet, (x+18), (y-10), -3, Bullet.Owner.ENEMY)); // sets the bullet at the position of MyPlane
        				firingTimer = System.nanoTime(); 
        			}
        		}     		
        	}

        } 
        // RETURNS LIFE COUNT
        public int getLives() { 
        	if(life1 < 0)
        		life1 = 0;
        	return life1; 
        	}
        public int getLives2() {
        	if(life2 < 0)
        		life2 = 0;
        	return life2;
        	} 
       
        // DRAW MY PLANE IF LIFE > 0
        public void draw(ImageObserver obs) {
        	
        	if(life1 > 0 && life2 > 0) // draw planes if lives > 0
        		g2.drawImage(img, this.x, this.y, obs);
        }
        
		public void move (int x, int y) {
            this.x += x;
            this.y += y;
        }
		// COLLISION
		public boolean collision(int x, int y, int w, int h) {
            bbox = new Rectangle(this.x, this.y, this.width, this.height - 10);
            Rectangle otherBBox = new Rectangle (x,y,w,h);
            if(this.bbox.intersects(otherBBox)) { 
                return true;
            }
            return false;
        }
    }

    // DRAW BACKGROUND 
    public void drawBackGroundWithTileImage() {
        int TileWidth = sea.getWidth(this);
        int TileHeight = sea.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);
        
        // fills the screen with tiles of water images
        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(sea, j * TileWidth, 
                        i * TileHeight + (move % TileHeight), TileWidth, 
                        TileHeight, this);
            }
        }
        move += speed;
    }

    public void drawDemo() { //------------------------------------------------------------------------------------------
        if (!gameOver) {
            drawBackGroundWithTileImage();
            I1.update();
            I2.update();
            I3.update();
            m.update();
            m2.update();
            if(m.getLives() == 0 && m2.getLives2() == 0)
            	go.update();
            
            // SCOREBAR UPDATE
            if(m.getLives() == 4){
            health1.update();
            }
            else if(m.getLives() == 3){
            	health2.update();
            }
            else if(m.getLives() == 2){
                health3.update();
             }
             else if(m.getLives() == 1){
                	health4.update();
              }
            
            if(m2.getLives2() == 4){
                health11.update();
            }
            else if(m2.getLives2() == 3){
               	health22.update();
            }
            else if(m2.getLives2() == 2){
                 health33.update();
            }
            else if(m2.getLives2() == 1){
               	health44.update();
            }
            
            // big explosion1 update
            for(int i = 0; i < explosion1.size(); i++){
            	explosion1.get(i).update();
            }
            // big explosion2 update
            for(int i = 0; i < explosion2.size(); i++){
            	explosion2.get(i).update();
            }
            // small explosion update
            for(int i = 0; i < explosion3.size(); i++){
            	explosion3.get(i).update();
            }
            // friendly bullets update
            for(int i = 0; i < fbullet.size(); i++){
            	boolean remove = fbullet.get(i).update();
            	//boolean remove2 = enemy.get(i).getBulletDead();
            	if(remove){
            		fbullet.remove(i); // to remove fbullet if it goes out of bounce
            		i--;
            	}
            }
            
            //enemy bullets update
            for(int i = 0; i < ebullet.size(); i++){
            	
            	boolean remove = ebullet.get(i).update();
            	if(remove){
            		ebullet.remove(i); // to remove ebullet if it goes out of bounce
            		i--;
            	}     	
            }
            
            // enemy type 1 update
            for(int i = 0; i < enemy.size(); i++){
            	enemy.get(i).update();
            }
            // enemy type 2 update
            for(int i = 0; i < enemy2.size(); i++){
            	enemy2.get(i).update();
            }
            
            
            // fbullet-enemy type 1 collision
            for(int i = 0; i < fbullet.size(); i++){

                Bullet b = fbullet.get(i);

                double bx = b.getx();
                double by = b.gety();
                double br = b.getr(); // bullet boundary box
                
                // check friendly bullet collision with an enemy plane
                // remove friendly bullet if it collides with an enemy plane
                for(int j = 0; j < enemy.size(); j++){

                	Enemy e = enemy.get(j);

                	int ex = (int) e.getx();
                	int ey = (int) e.gety();
                	double er = e.getr(); // enemy boundary box
                	double dx = bx - ex;
                	double dy = by - ey;
                	
                	double dist = Math.sqrt(dx*dx + dy*dy); // using the distance formula

                	if(dist < (br - 20) + (er - 20)){
                	
                		fbullet.remove(i); // remove fbullet once it hits enemy plane
                		e.reset();
                		i--;
                		if(b.getOwner() == Bullet.Owner.PLAYER1)
                			score1++;
                		
                		if(b.getOwner() == Bullet.Owner.PLAYER2)
                			score2++;
                		explosion3.add(new Explosion((ex), (ey), 
								game1942WithoutObserver.eExpl1,
								game1942WithoutObserver.eExpl2,
								game1942WithoutObserver.eExpl3,
								game1942WithoutObserver.eExpl4,
								game1942WithoutObserver.eExpl5,
								game1942WithoutObserver.eExpl6));
                		// play sound
                		AudioClip ac = getAudioClip(getCodeBase(), "Resources/snd_explosion2.wav");
                    	ac.play();
                    	
                			break;
                	}
                }

             }
            
            // FRIENDLY BULLET - ENEMY TYPE 2 COLLISION
            for(int i = 0; i < fbullet.size(); i++){

                Bullet b = fbullet.get(i);

                double bx = b.getx();
                double by = b.gety();
                double br = b.getr(); // bullet boundary box
                
                // check friendly bullet collision with an enemy plane
                // remove friendly bullet if it collides with an enemy plane
                for(int j = 0; j < enemy2.size(); j++){

                	Enemy e = enemy2.get(j);

                	int ex = (int) e.getx();
                	int ey = (int) e.gety();
                	double er = e.getr(); // enemy boundary box
                	double dx = bx - ex;
                	double dy = by - ey;
                	
                	double dist = Math.sqrt(dx*dx + dy*dy); // using the distance formula

                	if(dist < (br - 20) + (er - 20)){
                		//e.hit();
                		fbullet.remove(i); // remove fbullet once it hits enemy plane
                		e.reset();
                		i--;
                		if(b.getOwner() == Bullet.Owner.PLAYER1){
                			score1++;
                		}
                		if(b.getOwner() == Bullet.Owner.PLAYER2)
                			score2++;
                		explosion3.add(new Explosion((ex), (ey), 
								game1942WithoutObserver.eExpl1,
								game1942WithoutObserver.eExpl2,
								game1942WithoutObserver.eExpl3,
								game1942WithoutObserver.eExpl4,
								game1942WithoutObserver.eExpl5,
								game1942WithoutObserver.eExpl6));
                		
                		AudioClip ac = getAudioClip(getCodeBase(), "Resources/snd_explosion2.wav");
                    	ac.play();
                    	
                		break;
                	}
                }
             }
            
            // ENEMY BULLET AND MY PLANE 1 COLLISION
            for(int i = 0; i < ebullet.size(); i++){

                Bullet b = ebullet.get(i);

                double bx = b.getx();
                double by = b.gety();
                double br = b.getr(); // bullet boundary box

                int ex = m.getx();
               	int ey = m.gety();
               	double er = m.getr(); // MyPlane boundary box
               	double dx = bx - ex;
               	double dy = (by) - (ey);
                	
               	double dist = Math.sqrt(dx*dx + dy*dy); // using the distance formula

               	if(dist < (br - 30) + (er - 30)){
               		//e.hit();              		
               		ebullet.remove(i); // remove ebullet once it hits enemy plane               	
           			i--; 
           			if(m.getLives() > 0){
           			explosion3.add(new Explosion((ex + 10), (ey + 10), 
							game1942WithoutObserver.eExpl1,
							game1942WithoutObserver.eExpl2,
							game1942WithoutObserver.eExpl3,
							game1942WithoutObserver.eExpl4,
							game1942WithoutObserver.eExpl5,
							game1942WithoutObserver.eExpl6));
            		
            		AudioClip ac = getAudioClip(getCodeBase(), "Resources/snd_explosion2.wav");
                	ac.play();
           			}
           			m.looseLife();
               		break;
                }
             }
         // ENEMY BULLET AND MY PLANE 2 COLLISION
            for(int i = 0; i < ebullet.size(); i++){

                Bullet b = ebullet.get(i);

                int bx = (int) b.getx();
                int by = (int) b.gety();
                int br = (int) b.getr(); // bullet boundary box

                int ex = m2.getx();
               	int ey = m2.gety();
               	int er = m2.getr(); // MyPlane boundary box
               	int dx = bx - ex;
               	int dy = (by) - (ey);
                	
               	double dist = Math.sqrt(dx*dx + dy*dy); // using the distance formula

               	if(dist < (br - 20) + (er - 20)){
               		//e.hit();              		
               		ebullet.remove(i); // remove ebullet once it hits enemy plane               	
           			i--;
           			if(m2.getLives2() > 0){
           			explosion3.add(new Explosion((ex + 10), (ey), 
							game1942WithoutObserver.eExpl1,
							game1942WithoutObserver.eExpl2,
							game1942WithoutObserver.eExpl3,
							game1942WithoutObserver.eExpl4,
							game1942WithoutObserver.eExpl5,
							game1942WithoutObserver.eExpl6));
            		
            		AudioClip ac = getAudioClip(getCodeBase(), "Resources/snd_explosion2.wav");
                	ac.play();
           			}
           			m2.looseLife2();
               		break;
                }
             }
        
            // DRAW FUNCTIONS
            I1.draw(this);
            I2.draw(this);
            I3.draw(this);
            m.draw(this);
            m2.draw(this);
            
            // Get the scores for m and m2
            int s1 = Enemy.getScore() + score1;
            int s2 = Enemy.getScore2() + score2;
          
            g2.setFont(new Font("Century Gothic", Font.BOLD, 18));
            g2.setColor( new Color(255, 255, 255));
            g2.drawString("Score: " + s2, 10, 700); // the score of the second plane getLives()
            g2.drawString("Score: " + s1, 600, 700); // the score of the first plane            
            g2.drawString("Lives: " + m.getLives(), 600, 730);
            g2.drawString("Lives: " + m2.getLives2(), 10, 730);

            
            // SCOREBAR DRAW
            if(m.getLives() == 4){
                health1.draw(this);
            }
            else if(m.getLives() == 3){
               	health2.draw(this);
            }
            else if(m.getLives() == 2){
                 health3.draw(this);
            }
            else if(m.getLives() == 1){
               	health4.draw(this);
            }
            
            if(m2.getLives2() == 4){
                health11.draw(this);
            }
            else if(m2.getLives2() == 3){
               	health22.draw(this);
            }
            else if(m2.getLives2() == 2){
                 health33.draw(this);
            }
            else if(m2.getLives2() == 1){
               	health44.draw(this);
            }
            
            // friendly bullet draw
            for(int i = 0; i < fbullet.size(); i++){
            	fbullet.get(i).draw(this);
            }
            // enemy bullet draw
            for(int i = 0; i < ebullet.size(); i++){
            	ebullet.get(i).draw(this);
            } 
            // enemy type 1 and 2 draw
            for(int i = 0; i < enemy.size() || i < enemy2.size(); i++){
            	enemy.get(i).draw(this);
            	enemy2.get(i).draw(this);
            }
            
            // big explosion draw
            for(int i = 0; i < explosion1.size(); i++){
            	explosion1.get(i).draw(this);
            }
            // big explosion draw
            for(int i = 0; i < explosion2.size(); i++){
            	explosion2.get(i).draw(this);
            }
         // small explosion draw
            for(int i = 0; i < explosion3.size(); i++){
            	explosion3.get(i).draw(this);
            }
            if(m.getLives() == 0 && m2.getLives2() == 0)
            	go.draw(this);
        }
    }

    public void paint(Graphics g) {
        if(bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width, 
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        drawDemo();
        g.drawImage(bimg, 0, 0, this); // draws the background image
        
    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start(); 
    }

    public void run() {
    	
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();  
          try {
                  Thread.sleep(15);
            } catch (InterruptedException e) {
                break;
            }
            
        }
    }

    public static void main(String argv[]) {
        final game1942WithoutObserver demo = new game1942WithoutObserver();
        demo.init();
        JFrame f = new JFrame("Scrolling Shooter");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(demo.w, demo.h));
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
    }

}


