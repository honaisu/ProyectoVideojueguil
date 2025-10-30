package enumeradores;

import interfaces.MomentaneumManager;
import managers.AsteroidManager;
import managers.BulletManager;
import managers.LaserManager;
import managers.MeleeManager;

public enum NameManager {
	ASTEROID(new AsteroidManager()),
	BULLET(new BulletManager()),
	MELEE(new MeleeManager()),
	LASER(new LaserManager());
	
	private final MomentaneumManager manager;
	
	NameManager(MomentaneumManager manager) {
		this.manager = manager;
	}
	
	public MomentaneumManager getManager() {
		return manager;
	}
}
