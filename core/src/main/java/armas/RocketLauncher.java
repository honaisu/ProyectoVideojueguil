package armas;

import com.badlogic.gdx.math.Rectangle;
import enumeradores.recursos.EGameSound;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class RocketLauncher extends Weapon {
	public RocketLauncher() {
		super("Rocket Launcher",
				20,
				0.3f,
				4);
	}

	@Override
	public void crearProyectil(Rectangle r, float rotation, ProjectileManager manager) {
		// TODO Auto-generated method stub
		
	}
}
