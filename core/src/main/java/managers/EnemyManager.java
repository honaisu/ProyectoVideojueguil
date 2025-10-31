package managers;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.BallHitbox;
import logica.EnemyFactory;
import personajes.Enemy;

//Calse encargada de crear los asteroides
public class EnemyManager {
	private List<Enemy> enemies;	
	private final int MAX_ENEMIES = 10;
	
	public EnemyManager(int cantAsteroides, int velXAsteroides, int velYAsteroides) {
		enemies = new ArrayList<>();
		spawnAsteroids(cantAsteroides);
    }
	
	// TODO Poner algo génerico o similar
	public EnemyManager() {
		enemies = new ArrayList<>();
		spawnAsteroids(MAX_ENEMIES);
	}

	// Genera los asteroides con atributos levemente aleatorios
	private void spawnAsteroids(int cant) {
		for (int i = 0; i < cant; i++) {
			// Enemigos sin movimiento
			Enemy bb = EnemyFactory.createRandomBasic();
	        enemies.add(bb);
	  	}
	}
	
	// Actualiza al asteroide movimiento y colision
	public void update(float delta) {
		for (BallHitbox ball : enemies) {
            ball.update();
        }
        //handleCollisions();
    }
	
	// Dibujar todos los asteroides
    public void render(SpriteBatch batch) {
        for (BallHitbox ball : enemies) {
            ball.draw(batch);
        }
    }
	
	public boolean remove(BallHitbox asteroid) {
		return enemies.remove(asteroid);
	}
	
	public List<Enemy> getEnemies() {
        return enemies;
    }

    // ¿Se acabaron los asteroides?
    public boolean isEmpty() {
        return enemies.isEmpty();
    }

    public int size() {
        return enemies.size();
    }
    
    public void clear() {
        enemies.clear();
    }

}
