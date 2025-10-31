package managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.BallHitbox;
import hitboxes.Hitbox;

//Calse encargada de crear los asteroides
public class AsteroidManager {
	// Para el sprite de los asteroides
	private List<BallHitbox> asteroids;	
	// Para la colision de los asteroides
	private List<BallHitbox> auxColliders;
	
	public AsteroidManager(int cantAsteroides, int velXAsteroides, int velYAsteroides) {
		asteroids = new ArrayList<BallHitbox>();
		auxColliders = new ArrayList<BallHitbox>();
		spawnAsteroids(cantAsteroides, velXAsteroides, velYAsteroides);
    }
	
	// TODO Poner algo génerico o similar
	public AsteroidManager() {
		asteroids = new ArrayList<BallHitbox>();
		auxColliders = new ArrayList<BallHitbox>();
		spawnAsteroids(5, 0, 0);
	}

	// Genera los asteroides con atributos levemente aleatorios
	private void spawnAsteroids(int cant, int velX, int velY) {
		Random r = new Random();
		for (int i = 0; i < cant; i++) {
			//asteroides sin movimiento
			// TODO Transformar a Factory
			// BallHitbox bb = AsteroidFactory.crear(velX, VELY);
			BallHitbox bb = new BallHitbox(
					r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            50+r.nextInt(10));	
	        asteroids.add(bb);
	        auxColliders.add(bb);
	  	}
	}
	
	// Actualiza al asteroide movimiento y colision
	public void update(float delta) {
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
	
	public boolean remove(Hitbox asteroid) {
		auxColliders.remove(asteroids.indexOf(asteroid));
		return asteroids.remove(asteroid);
	}
	
	public List<BallHitbox> getAsteroids() {
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
