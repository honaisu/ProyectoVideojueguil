package logica;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.Projectile;
import entidades.Player;
import managers.EnemyManager;
import managers.CollisionManager;
import managers.DropManager;
import managers.ProjectileManager;

/**
 * Clase encargada de mantener instancias de otros managers encargados de la lógica del juego.
 */
public class GameLogicHandler {
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

	public void render(SpriteBatch batch) {
		enemyManager.render(batch);
		proyectilManager.draw(batch);
		dropManager.render(batch);
	}
	
	public void update(float delta, Player player) {
		proyectilManager.update(delta, player);
		enemyManager.update(delta);
		dropManager.update(delta);
	}

	public void handleCollisions(Player player) {
		// TODO Eliminar listas. O ver alternativas para manejar las colisiones
		// (No se me ocurre ninguna por el momento)
		collisionManager.handleCollisions(player, proyectilManager.getProjectiles(), enemyManager.getEnemies(), dropManager);
		//collisionManager.handlePlayerVsDrops(player, dropManager);
	}

	public void addProjectile(Projectile projectile) {
		if (projectile == null) return;
		proyectilManager.add(projectile);
	}
}
