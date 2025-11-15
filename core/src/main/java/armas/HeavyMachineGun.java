package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import entidades.Player;
import entidades.proyectiles.Bullet;
import entidades.proyectiles.Projectile;
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
				25,												// daño
				0.2f, 											// cadencia
				30); 											// municion
	}
    
    //crea la bala de la metralleta con direccion respecto al jugador
    @Override
    public void crearProyectil(Player p, ProjectileManager manager) {
        float[] muzzle = Projectile.calcularMuzzle(p, false);
        float width = 30f;
        float vel = 10f;
        
        Bullet bala = new Bullet(muzzle[0], muzzle[1], width, muzzle[2], vel, p);
        manager.add(bala);
    }
    @Override
    public Texture getDropTexture() {
        return AssetManager.getInstancia().getTexture(EDropType.HEAVY_MACHINE_GUN);
    }

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSound(EGameSound.DROP_HMG);
    }
}
