package managers;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.BallHitbox;

//Calse encargada de crear los asteroides
public class AsteroidManager {
	
	private ArrayList<BallHitbox> asteroids;	// Para el sprite de los asteroides
	private ArrayList<BallHitbox> auxColliders;	// Para la colision de los asteroides
	
	public AsteroidManager(int cantAsteroides, int velXAsteroides, int velYAsteroides) {
		asteroids = new ArrayList<BallHitbox>();
		auxColliders = new ArrayList<BallHitbox>();
		spawnAsteroids(cantAsteroides, velXAsteroides, velYAsteroides);
    }
	
	// Genera los asteroides con atributos levemente aleatorios
	private void spawnAsteroids(int cant, int velX, int velY) {
		Random r = new Random();
		for (int i = 0; i < cant; i++) {
			
			//asteroides con movimiento
	        /*Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velX+r.nextInt(4), velY+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("aGreyMedium4.png")));*/   
	        
			//asteroides sin movimiento
			BallHitbox bb = new BallHitbox(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), 0, 0, 
	  	            new Texture(Gdx.files.internal("Mono.png")));	
	        asteroids.add(bb);
	        auxColliders.add(bb);
	  	}
	}
	
	// Actualiza al asteroide movimiento y colision
	public void update() {
		for (BallHitbox ball : asteroids) {
            ball.update();
        }
        handleCollisions();
    }
	
	// Dibujar todos los asteroides
    public void render(SpriteBatch batch) {
        for (BallHitbox ball : asteroids) {
            ball.draw(batch);
        }
    }
	// Verifica las colisiones del asteroide
	private void handleCollisions() {
        for (int i = 0; i < asteroids.size(); i++) {
            BallHitbox ball1 = asteroids.get(i);
            for (int j = 0; j < auxColliders.size(); j++) {
                BallHitbox ball2 = auxColliders.get(j);
                if (i < j) {
                    ball1.rebote(ball2);
                }
            }
        }
    }
	
	// Elimina el asteroide
	public void removeAsteroid(int index) {
        if (index >= 0 && index < asteroids.size()) {
            asteroids.remove(index);
            auxColliders.remove(index);
        }
    }
	
	public ArrayList<BallHitbox> getAsteroids() {
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
