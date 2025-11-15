package armas;

import com.badlogic.gdx.audio.Sound;

import entidades.Player;
import entidades.proyectiles.Bullet;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import managers.ProjectileManager;
import managers.assets.AssetManager;

/**
 * Clase para un arma escopeta
 */
public class HeavyMachineGun extends Weapon {
	public HeavyMachineGun() {
		super("Heavy Machine Gun",
				25,							// daño
				0.2f, 											// cadencia
				300, EDropType.HEAVY_MACHINE_GUN); 											// municion
	}
    
    //crea la bala de la metralleta con direccion respecto al jugador
    @Override
    public void crearProyectil(Player p, ProjectileManager manager) {
        int width = 30;
        int vel = 10;
        
        // TODO revisar
        Bullet bala = new Bullet(p.getPosition().x, p.getPosition().y, width, 0f, vel, stats.getDamage(), false);
        manager.add(bala);
    }
    
    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSound(EGameSound.DROP_HMG);
    }
}
