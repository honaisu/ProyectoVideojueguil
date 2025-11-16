package logica;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.Projectile;
import entidades.Player;
import enumeradores.recursos.EPlayerSkin;
import managers.EnemyManager;
import managers.CollisionManager;
import managers.DropManager;
import managers.ProjectileManager;
import managers.ObstacleManager;

/**
 * Clase encargada de mantener instancias de otros managers encargados de la lógica del juego.
 */
public class GameLogicHandler {
    private final CollisionManager collisionManager;
    
    private final EnemyManager enemyManager;
    
    private final ProjectileManager proyectilManager;
    
    private final DropManager dropManager;
    
    private final ObstacleManager obstacleManager;
    
    public GameLogicHandler() {
    	this.enemyManager = new EnemyManager();
    	this.collisionManager = new CollisionManager();
    	this.proyectilManager = new ProjectileManager();
    	this.dropManager = new DropManager();
    	this.obstacleManager = new ObstacleManager();
    	
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

	public void render(SpriteBatch batch) {
		obstacleManager.render(batch); //renderizamo los bloques para "evitar" enrredos con el enemy
		enemyManager.render(batch);
		proyectilManager.draw(batch);
		dropManager.render(batch);
	}
	
	public void update(float delta, Player player) {
		proyectilManager.update(delta, player);
		enemyManager.update(delta);
		dropManager.update(delta);
		obstacleManager.update(delta);
	}

	public void handleCollisions(Player player) {
		// TODO Eliminar listas. O ver alternativas para manejar las colisiones
		// (No se me ocurre ninguna por el momento)
		collisionManager.handleCollisions(player, proyectilManager.getProjectiles(), enemyManager.getEnemies(), dropManager, obstacleManager);
		//collisionManager.handlePlayerVsDrops(player, dropManager);
	}

	public void addProjectile(Projectile projectile) {
		if (projectile == null) return;
		proyectilManager.add(projectile);
	}
}
