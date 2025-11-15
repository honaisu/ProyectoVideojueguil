package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import entidades.proyectiles.Swing;
import managers.ProjectileManager;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import managers.assets.AssetManager;

public class LaserGun extends Weapon {
    public LaserGun() {
    	super("Laser Gun",
				2,																// daño
				0.08f, 															// cadencia
				60, 															// municion
				AssetManager.getInstancia().getSound(EGameSound.SHOOT_LASER),	// sonido //TODO ver sonido que se loopee
				EDropType.LASER_GUN);
    }
    
    @Override
    public void crearProyectil(Player p, ProjectileManager manager) {
    	float width = 10f;
    	float height = 1000f;
    	Rectangle r = new Rectangle(p.getSprite().getX(), p.getSprite().getY(), width, height);
		float duration = 0.25f;
		
		Swing rayo = new Swing(r, EProjectileType.LASER_GUN,
				stats.getDamage(),
				0, p.getRotation(),
				duration, true);

		manager.add(rayo);
    }

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_LG);
	}
}
