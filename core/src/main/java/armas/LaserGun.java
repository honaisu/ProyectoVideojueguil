package armas;

import com.badlogic.gdx.audio.Sound;


import entidades.proyectiles.Swing;
import managers.ProjectileManager;
import entidades.Entity;
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
    public void crearProyectil(Entity p, ProjectileManager manager) {
    	int width = 10;
    	int height = 1000;
		float duration = 0.25f;
		
		Swing ray = new Swing(p, state.getDamage(), duration, EProjectileType.LASER_GUN, width, height, 0f, true, true); //TODO arreglar

		manager.add(ray);
    }

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_LG);
	}
}
