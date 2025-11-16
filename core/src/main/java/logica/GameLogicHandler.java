package logica;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import entidades.proyectiles.Projectile;
import entidades.IRenderizable;
import entidades.Player;
import managers.EnemyManager;
import managers.CollisionManager;
import managers.DropManager;
import managers.ProjectileManager;

/**
 * Clase encargada de mantener instancias de otros managers encargados de la
 * lógica del juego.
 */
public class GameLogicHandler implements IRenderizable {
	private final CollisionManager collisionManager;
	private final EnemyManager enemyManager;
	private final ProjectileManager proyectilManager;
	private final DropManager dropManager;

	public GameLogicHandler() {
		this.enemyManager = new EnemyManager();
		this.collisionManager = new CollisionManager();
		this.proyectilManager = new ProjectileManager();
		this.dropManager = new DropManager();
	}

	@Override
	public void update(float delta) {
		proyectilManager.update(delta);
		enemyManager.update(delta);
		dropManager.update(delta);
	}

	@Override
	public void draw(SpriteBatch batch) {
		enemyManager.draw(batch);
		proyectilManager.draw(batch);
		dropManager.render(batch);
	}

	public void handleCollisions(Player player) {
		// TODO Eliminar listas. O ver alternativas para manejar las colisiones
		// (No se me ocurre ninguna por el momento)
		collisionManager.handleCollisions(player, proyectilManager.getProjectiles(), enemyManager.getEnemies(),
				dropManager);
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
}
