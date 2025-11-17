package armas;

import com.badlogic.gdx.audio.Sound;


import entidades.Entity;
import entidades.proyectiles.Bullet;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import managers.ProjectileManager;
import managers.assets.AssetManager;

/**
 * Clase para un arma escopeta
 */
public class HeavyMachineGun extends Weapon {
	public HeavyMachineGun() {
		super("Heavy Machine Gun",
				25,							// daño
				0.2f, 						// cadencia
				300,						// balas
				EDropType.HEAVY_MACHINE_GUN);
	}
    
    //crea la bala de la metralleta con direccion respecto al jugador
    @Override
    public void crearProyectil(Entity p, ProjectileManager manager) {
        int width = 30;
        int vel = 10;
        
        // TODO revisar
        Bullet bala = new Bullet(p, EProjectileType.HOLLOWPOINT, state.getDamage(), vel, width, false);
        manager.add(bala);
    }
    
    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSound(EGameSound.DROP_HMG);
    }
}
