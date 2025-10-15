package managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager {
	private final AsteroidManager asteroidManager;
    private final BulletManager bulletManager;
    private final CollisionManager collisionManager;
    private final MeleeManager meleeManager;
    
    public GameManager() {
    	asteroidManager = new AsteroidManager();
        bulletManager = new BulletManager();
        meleeManager = new MeleeManager();
        collisionManager = new CollisionManager();
    }

	public void update(float delta) {
    	bulletManager.update();
        meleeManager.update(delta);
        asteroidManager.update();
    }
	
	public void render(SpriteBatch batch) {
		bulletManager.render(batch);
        meleeManager.render(batch);
        asteroidManager.render(batch);
	}
    
    public AsteroidManager getAsteroidManager() {
		return asteroidManager;
	}

	public BulletManager getBulletManager() {
		return bulletManager;
	}

	public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	public MeleeManager getMeleeManager() {
		return meleeManager;
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
