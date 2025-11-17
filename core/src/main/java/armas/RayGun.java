package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import entidades.Entity;
import entidades.proyectiles.Bullet;
import entidades.proyectiles.Projectile;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class RayGun extends Weapon {

	public RayGun() {
		super("Ray Gun",
				20,												// daño
				0.4f, 											// cadencia
				25, 											// municion
				EDropType.RAY_GUN);
	}

	@Override
	public void crearProyectil(Entity p, ProjectileManager manager) {
        int width = 30;
        int vel = 15;
        
        Bullet bullet = new Bullet(p, EProjectileType.RAYGUN, state.getDamage(), vel, width, true);
        manager.add(bullet);
	}
	
	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_RG);
	}

}
