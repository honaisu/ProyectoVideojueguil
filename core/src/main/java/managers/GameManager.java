package managers;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enumeradores.NameManager;
import interfaces.MomentaneumManager;

public class GameManager {
	private final Map<NameManager, MomentaneumManager> managers;
    private final CollisionManager collisionManager;
    
    public GameManager() {
    	this.collisionManager = new CollisionManager();
    	
    	this.managers = new EnumMap<>(NameManager.class);
    	for (NameManager manager : NameManager.values()) {
    		this.managers.put(manager, manager.getManager());
    	}
    }

	public void update(float delta) {
		for (MomentaneumManager manager : managers.values()) {
			manager.update(delta);
		}
    }
	
	public void render(SpriteBatch batch) {
		for (MomentaneumManager manager : managers.values()) {
			manager.render(batch);
		}
	}
    
    public MomentaneumManager getManager(NameManager name) {
		return managers.get(name);
	}
    
    public CollisionManager getCollisionManager() {
    	return collisionManager;
    }
}
