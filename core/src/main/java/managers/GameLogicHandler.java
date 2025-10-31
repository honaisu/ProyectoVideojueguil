package managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Projectile;

/**
 * Clase encargada de mantener instancias de otros managers encargados de la lógica del juego.
 */
public class GameLogicHandler {
    private final CollisionManager collisionManager;
    // TODO cambiar a EnemyManager o algo (yo creo)
    // Pensaba que pertenecía a los otros managers y nada que ver
    // Son los enemigos xD
    private final AsteroidManager asteroidManager;
    // No es un strategy :c
    private final ProjectileManager proyectilManager;
    
    public GameLogicHandler() {
    	this.asteroidManager = new AsteroidManager();
    	this.collisionManager = new CollisionManager();
    	this.proyectilManager = new ProjectileManager();
    }
    
    public AsteroidManager getAsteroidManager() {
    	return asteroidManager;
    }
	
	public ProjectileManager getProyectilManager() {
		return proyectilManager;
	}
    
    public CollisionManager getCollisionManager() {
    	return collisionManager;
    }

	public void render(SpriteBatch batch) {
		asteroidManager.render(batch);
		proyectilManager.draw(batch);
	}
	
	public void update(float delta, Rectangle r, float rotation) {
		proyectilManager.update(delta, r, rotation);
	}

	public void handleCollisions() {
		//collisionManager.handleCollisions(proyectilManager, asteroidManager);
	}

	public void addProjectile(Projectile projectile) {
		if (projectile == null) return;
		proyectilManager.add(projectile);
	}
}
