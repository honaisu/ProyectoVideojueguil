package managers;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.Ball2;

public class AsteroidManager {
	
	private ArrayList<Ball2> asteroids;
	private ArrayList<Ball2> auxColliders;
	
	
	
	public AsteroidManager(int cantAsteroides, int velXAsteroides, int velYAsteroides) {
		asteroids = new ArrayList<Ball2>();
		auxColliders = new ArrayList<Ball2>();
		spawnAsteroids(cantAsteroides, velXAsteroides, velYAsteroides);
        
	    
    }
	
	private void spawnAsteroids(int cant, int velX, int velY) {
		Random r = new Random();
		for (int i = 0; i < cant; i++) {
	        Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velX+r.nextInt(4), velY+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("aGreyMedium4.png")));	   
	        asteroids.add(bb);
	        auxColliders.add(bb);
	  	}
	}
	
	public void update() {
		for (Ball2 ball : asteroids) {
            ball.update();
        }
        handleCollisions();
    }
	
	// 🔹 Dibujar todos los asteroides
    public void render(SpriteBatch batch) {
        for (Ball2 ball : asteroids) {
            ball.draw(batch);
        }
    }
	
	private void handleCollisions() {
        for (int i = 0; i < asteroids.size(); i++) {
            Ball2 ball1 = asteroids.get(i);
            for (int j = 0; j < auxColliders.size(); j++) {
                Ball2 ball2 = auxColliders.get(j);
                if (i < j) {
                    ball1.checkCollision(ball2);
                }
            }
        }
    }
	
	
	public void removeAsteroid(int index) {
        if (index >= 0 && index < asteroids.size()) {
            asteroids.remove(index);
            auxColliders.remove(index);
        }
    }
	
	public ArrayList<Ball2> getAsteroids() {
        return asteroids;
    }

    // ¿Se acabaron los asteroides?
    public boolean isEmpty() {
        return asteroids.isEmpty();
    }

    public int size() {
        return asteroids.size();
    }
    
    public void clear() {
        asteroids.clear();
        auxColliders.clear();
    }

}
