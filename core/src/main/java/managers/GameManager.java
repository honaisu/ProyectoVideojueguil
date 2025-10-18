package managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager {
	//private final Map<ManagersName, Managers> managers;
	
	private final AsteroidManager asteroidManager;
    private final BulletManager bulletManager;
    private final CollisionManager collisionManager;
    private final MeleeManager meleeManager;
    private final LaserManager laserManager;
    
    public GameManager() {
    	asteroidManager 	= new AsteroidManager();
        bulletManager 		= new BulletManager();
        meleeManager 		= new MeleeManager();
        collisionManager 	= new CollisionManager();
        laserManager 		= new LaserManager();
    }

	public void update(float delta) {
		asteroidManager.update();
    	bulletManager.update();
    	// TODO Handle el Collision Manager... 
    	meleeManager.update(delta);
        laserManager.update(delta);
    }
	
	public void render(SpriteBatch batch) {
		bulletManager.render(batch);
        meleeManager.render(batch);
        asteroidManager.render(batch);
        laserManager.render(batch);
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

	public LaserManager getLaserManager() {
		return laserManager;
	}
}
