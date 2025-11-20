package logica;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.Projectile;
import interfaces.IRenderizable;
import entidades.Player;
import managers.EnemyManager;
import managers.CollisionManager;
import managers.DropManager;
import managers.ProjectileManager;
import managers.ObstacleManager;

/**
 * Clase encargada de mantener instancias de otros managers encargados de la
 * lógica del juego.
 */
public class GameLogicHandler implements IRenderizable {
	private final CollisionManager collisionManager;

	private final EnemyManager enemyManager;

	private final ProjectileManager proyectilManager;

	private final DropManager dropManager;

	private final ObstacleManager obstacleManager;

	public GameLogicHandler() {
		this.collisionManager = new CollisionManager();
		this.proyectilManager = new ProjectileManager();
		this.dropManager = new DropManager();

		this.obstacleManager = new ObstacleManager();
		this.enemyManager = new EnemyManager(this.obstacleManager);
	}
	
	public int handleCollisions(Player player) {
		int scoreGanado = collisionManager.handleCollisions(player, proyectilManager.getProjectiles(),
				enemyManager.getEnemies(),
				dropManager, obstacleManager);

		collisionManager.handlePlayerVsDrops(player, dropManager);
		return scoreGanado;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		obstacleManager.render(batch);
		proyectilManager.draw(batch);
		enemyManager.draw(batch);
		dropManager.render(batch);
	}
	
	public void addProjectile(Projectile projectile) {
		if (projectile == null)
			return;
		proyectilManager.add(projectile);
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public ProjectileManager getProyectilManager() {
		return proyectilManager;
	}

	public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	public DropManager getDropManager() {
		return dropManager;
	}

	public ObstacleManager getObstacleManager() {
		return obstacleManager;
	}

	/*
	 * @Override public void update(float delta) { proyectilManager.update(delta);
	 * enemyManager.update(delta); dropManager.update(delta);
	 * obstacleManager.update(delta); }
	 */

	@Override
	public void update(float delta) {
		// ta rarete
		proyectilManager.update(delta);
		enemyManager.update(delta);
		dropManager.update(delta);
		obstacleManager.update(delta);
	}
}
