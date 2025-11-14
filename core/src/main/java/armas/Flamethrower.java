package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import managers.ProjectileManager;
import managers.assets.AssetManager;

public class Flamethrower extends Weapon {
	public Flamethrower(int daño, float cadencia, int municion, Sprite spr) {
		super("Flamethrower", cadencia, municion, AssetManager.getInstancia().getDisparoSound());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void crearProyectil(Rectangle r, float rotation, ProjectileManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Texture getDropTexture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sound getPickupSound() {
		// TODO Auto-generated method stub
		return null;
	}
}
