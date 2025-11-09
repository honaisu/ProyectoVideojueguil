package managers;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logica.EnemyFactory;
import personajes.Enemy;

//Calse encargada de crear los asteroides
public class EnemyManager {
	private List<Enemy> enemies;	
	private final int MAX_ENEMIES = 20;
	
	public EnemyManager(int sumEnemies, int velXenemies, int velYenemies) {
		enemies = new ArrayList<>();
		spawnEnemies(sumEnemies);
    }
	
	// TODO Poner algo génerico o similar
	public EnemyManager() {
		enemies = new ArrayList<>();
		spawnEnemies(MAX_ENEMIES);
	}

	// Genera los asteroides con atributos levemente aleatorios
	private void spawnEnemies(int cant) {
		for (int i = 0; i < cant; i++) {
			// Enemigos sin movimiento
			Enemy enemy = EnemyFactory.createRandomBasic();
	        enemies.add(enemy);
	  	}
	}
	
	// Actualiza al enemigo movimiento y colision
	public void update(float delta) {
		for (Enemy enemy : enemies) {
            enemy.update(delta);
        }
        //handleCollisions();
    }
	
	// Dibujar todos los enemigos
    public void render(SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }
    }
	
	public boolean remove(Enemy enemy) {
		return enemies.remove(enemy);
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
