package armas;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Projectile;
import managers.AssetManager;

public class Flamethrower extends Weapon {
	public Flamethrower(int daño, float cadencia, int municion, Sprite spr) {
		super("Flamethrower", cadencia, municion, AssetManager.getInstancia().getDisparoSound());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Projectile crearProyectil(Rectangle r, float rotation) {
		// TODO Auto-generated method stub
		return null;
	}
}
