package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.Bullet;
import managers.AssetManager;
import managers.ProjectileManager;
import personajes.Player;

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
    public void crearProyectil(Player p, ProjectileManager manager) {
    	float rotation = p.getRotation() + 90;
    	float radians = (float) Math.toRadians(rotation);
        float centerX = p.getSpr().getBoundingRectangle().getX() + p.getSpr().getBoundingRectangle().getWidth() / 2;
        float centerY = p.getSpr().getBoundingRectangle().getY() + p.getSpr().getBoundingRectangle().getHeight() / 2;
        float length = p.getSpr().getBoundingRectangle().getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;

        // TODO Arreglar valores hardcodeados...
        Bullet bala = new Bullet(centerX, centerY, 30f, rotation, 10f, p);
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
