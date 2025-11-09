package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Bullet;
import managers.AssetManager;
import managers.ProjectileManager;

/**
 * Clase para un arma escopeta
 */
public class HeavyMachineGun extends Weapon {
	public HeavyMachineGun() {
		super("Heavy Machine Gun",
				25,												// daño
				0.2f, 											// cadencia
				30, 												// municion
				AssetManager.getInstancia().getDisparoSound());	// sonido
	}
    
    //crea la bala de la metralleta con direccion respecto al jugador
    @Override
    public void crearProyectil(Rectangle r, float rotation, ProjectileManager manager) {
    	float radians = (float) Math.toRadians(rotation);
        float centerX = r.getX() + r.getWidth() / 2;
        float centerY = r.getY() + r.getHeight() / 2;
        float length = r.getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;

        // TODO Arreglar valores hardcodeados...
        Bullet bala = new Bullet(bulletX, bulletY, 30f, rotation, 10f);
        manager.add(bala);
    }
    @Override
    public Texture getDropTexture() {
        return AssetManager.getInstancia().getHMTexture();
    }

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getHMSound();
    }
}
