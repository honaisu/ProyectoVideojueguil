package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.Projectile;
import armas.proyectiles.Swing;
import managers.ProjectileManager;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;
import managers.assets.AssetManager;

public class LaserGun extends Weapon {

    public LaserGun() {
    	super("Laser Gun",
				2,																// daño
				0.08f, 															// cadencia
				60, 															// municion
				AssetManager.getInstancia().getSound(EGameSound.SHOOT_LASER)	// sonido //TODO ver sonido que se loopee
				);
    }
    
    @Override
    public void crearProyectil(Player p, ProjectileManager manager) {
    	float muzzle[] = Projectile.calcularMuzzle(p, true);
		float width = 10f;
		float height = 1000f;
		float duration = 0.25f;
		
		float length = p.getSpr().getBoundingRectangle().getHeight() / 2f + 15f; // (Usando tu fix de la pregunta anterior)
		
		Swing rayo = new Swing(muzzle,
				length,
				p,
				SpriteFactory.create(EProjectileType.LASER_GUN, 96, 64),
				width, 			// width
				height, 		// height
				duration, 		// duracion
				true, 			// isBeam = true
				true);

		manager.add(rayo);
    }

	@Override
	public Texture getDropTexture() {
		return AssetManager.getInstancia().getTexture(EDropType.LASER_GUN); //TODO
	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_LG);
	}
}
