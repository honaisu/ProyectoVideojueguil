package armas;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Projectile;
import managers.AssetManager;
import managers.ProjectileManager;

public class Flamethrower extends Weapon {
	public Flamethrower(int daño, float cadencia, int municion, Sprite spr) {
		super("Flamethrower", cadencia, municion, AssetManager.getInstancia().getDisparoSound());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void crearProyectil(Rectangle r, float rotation, ProjectileManager manager) {
		// TODO Auto-generated method stub
		
	}
}
