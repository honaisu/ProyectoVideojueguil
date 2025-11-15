package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import entidades.Player;
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
				AssetManager.getInstancia().getSound(EGameSound.SHOOT));
	}

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
		float[] muzzle = Projectile.calcularMuzzle(p, false);
        float width = 30f;
        float vel = 30f;
        
        Bullet bullet = new Bullet(muzzle[0], muzzle[1],
        		width,
        		muzzle[2],
        		vel,
        		p,
        		SpriteFactory.create(EProjectileType.RAYGUN),
        		true);
        manager.add(bullet);
	}

	@Override
	public Texture getDropTexture() {
		return AssetManager.getInstancia().getTexture(EDropType.RAY_GUN);
	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_RG);
	}

}
