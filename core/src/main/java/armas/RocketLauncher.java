package armas;

import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Projectile;
import managers.AssetManager;



public class RocketLauncher extends Weapon {
	public RocketLauncher() {
		super("Rocket Launcher",
				0.3f,
				4,
				AssetManager.getInstancia().getDisparoSound());
	}

	@Override
	public Projectile crearProyectil(Rectangle r, float rotation) {
		// TODO Auto-generated method stub
		return null;
	}
}
