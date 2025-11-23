package managers;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entidades.Enemy;
import factories.EnemyFactory;
import interfaces.IRenderizable;

public class EnemyManager implements IRenderizable {
	// mio
	private final List<Enemy> enemies = new ArrayList<>();
	private static final float MIN_SEPARATION = 72f;
	private final ObstacleManager obstacleManager;

	// vacio para las rondas creo

	public EnemyManager(ObstacleManager obstacleManager) {
		this.obstacleManager = obstacleManager;
	}

	@Override
	public void update(float delta) {
		for (Enemy e : enemies)
			e.update(delta);
	}

	@Override
	public void draw(SpriteBatch batch) {
		for (Enemy e : enemies)
			e.draw(batch);
	}

	// mio //sgeun gemini mejor e mio xd// Tu spawner con lógica 'isFar'. Es mejor.

	// Anselmo que chucha es esto
	// es pa evitar que aparescan enemigos ensima de otros y ahora de bloques
	// tambien :D
	public void spawnEnemies(int cant) {
		final int triesPerEnemy = 24;
		for (int i = 0; i < cant; i++) {
			Enemy cand = null;
			boolean placed = false;
			for (int t = 0; t < triesPerEnemy && !placed; t++) {
				cand = EnemyFactory.createRandomBasic();

				// Comprueba si está lejos de otros enemigos
				boolean farFromEnemies = isFar(cand.getPosition().x, cand.getPosition().y, MIN_SEPARATION);

				// Comprueba si NO está dentro de un bloque (con esto solucionamos el bug)
				boolean farFromObstacles = !obstacleManager.isCollidingWithSolid(cand);

				// Ahora comprueba ambas condiciones
				if (farFromEnemies && farFromObstacles) {
					enemies.add(cand);
					placed = true;
				}
			}
			if (!placed && cand != null)
				enemies.add(cand);
		}
	}

	private boolean isFar(float x, float y, float min) {
		float m2 = min * min;
		for (Enemy e : enemies) {
			float dx = e.getPosition().x - x, dy = e.getPosition().y - y;
			if (dx * dx + dy * dy < m2)
				return false;
		}
		return true;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public int size() {
		return enemies.size();
	}

	public boolean isEmpty() {
		return enemies.isEmpty();
	}

	// creado para ronda
	public void add(Enemy e) {
		enemies.add(e);
	}

	// benjoid // Método útil
	public boolean remove(Enemy enemy) {
		return enemies.remove(enemy);
	}
}
