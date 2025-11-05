package armas;

import com.badlogic.gdx.math.Rectangle;

import managers.AssetManager;
import managers.ProjectileManager;



public class RocketLauncher extends Weapon {
	public RocketLauncher() {
		super("Rocket Launcher",
				0.3f,
				4,
				AssetManager.getInstancia().getDisparoSound());
	}

	@Override
	public void crearProyectil(Rectangle r, float rotation, ProjectileManager manager) {
		// TODO Auto-generated method stub
		
	}
}
