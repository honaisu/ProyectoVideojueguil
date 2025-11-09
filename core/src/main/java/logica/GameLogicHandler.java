package logica;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Projectile;
import managers.EnemyManager;
import managers.CollisionManager;
import managers.DropManager;
import managers.ProjectileManager;
import personajes.Player;

/**
 * Clase encargada de mantener instancias de otros managers encargados de la lógica del juego.
 */
public class GameLogicHandler {
    private final CollisionManager collisionManager;
    // TODO cambiar a EnemyManager o algo (yo creo)
    // Pensaba que pertenecía a los otros managers y nada que ver
    // Son los enemigos xD
    private final EnemyManager enemyManager;
    // No es un strategy :c
    private final ProjectileManager proyectilManager;
    
    private final DropManager dropManager;
    
    public GameLogicHandler() {
    	this.enemyManager = new EnemyManager();
    	this.collisionManager = new CollisionManager();
    	this.proyectilManager = new ProjectileManager();
    	this.dropManager = new DropManager();
    	
    }
    
    public EnemyManager getAsteroidManager() {
    	return enemyManager;
    }
	
	public ProjectileManager getProyectilManager() {
		return proyectilManager;
	}
    
    public CollisionManager getCollisionManager() {
    	return collisionManager;
    }

	public void render(SpriteBatch batch) {
		enemyManager.render(batch);
		proyectilManager.draw(batch);
		dropManager.render(batch);
	}
	
	public void update(float delta, Rectangle r, float rotation) {
		proyectilManager.update(delta, r, rotation);
		enemyManager.update(delta);
		dropManager.update(delta);
	}

	public void handleCollisions(Player player) {
		// TODO Eliminar listas. O ver alternativas para manejar las colisiones
		// (No se me ocurre ninguna por el momento)
		collisionManager.handleCollisions(player, proyectilManager.getProjectiles(), enemyManager.getEnemies(), dropManager);
		collisionManager.handlePlayerVsDrops(player, dropManager);
	}

	public void addProjectile(Projectile projectile) {
		if (projectile == null) return;
		proyectilManager.add(projectile);
	}
}
