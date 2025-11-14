package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.Projectile;
import armas.proyectiles.Swing;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class LaserCannon extends Weapon {

    public LaserCannon() {
    	super("Laser Cannon",
				25,													// daño
				0.9f, 												// cadencia
				15, 												// municion
				AssetManager.getInstancia().getSound(EGameSound.SHOOT_LASER_CANNON));	// sonido
    }

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
		float muzzle[] = Projectile.calcularMuzzle(p, true);
		float width = 20f;
		float height = 1000f;
		float duration = 0.25f;
		
		float length = p.getSpr().getBoundingRectangle().getHeight() / 2f + 15f; // (Usando tu fix de la pregunta anterior)
		
		Swing rayo = new Swing(muzzle,
				length,
				p,
				SpriteFactory.create(EProjectileType.LASER_CANNON, 96, 64),
				width, 			// width
				height, 		// height
				duration, 		// duracion
				true, 			// isBeam = true
				true);

		manager.add(rayo);
	}

	@Override
	public Texture getDropTexture() {
		return AssetManager.getInstancia().getTexture(EDropType.LASER_CANNON); //TODO
	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_LC);
	}
}
