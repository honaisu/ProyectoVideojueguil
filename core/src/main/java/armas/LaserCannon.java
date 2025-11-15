package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import entidades.proyectiles.Projectile;
import entidades.proyectiles.Swing;
import entidades.Entity;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;
import interfaces.ITexture;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class LaserCannon extends Weapon {

    public LaserCannon() {
    	super("Laser Cannon",
				25,													// daño
				0.9f, 												// cadencia
				15, 												// municion
				AssetManager.getInstancia().getSound(EGameSound.SHOOT_LASER_CANNON),
				EDropType.LASER_CANNON);	// sonido
    }

	@Override
	public void crearProyectil(Entity p, ProjectileManager manager) {
		float duration = 0.25f;
		
		Swing rayo = new Swing(p, EProjectileType.LASER_GUN, state.getDamage(), 0, duration, true);

		manager.add(rayo);
	}

	@Override
	public ITexture getDropTexture() {
		return EDropType.LASER_CANNON; //TODO
	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_LC);
	}
}
