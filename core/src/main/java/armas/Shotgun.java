package armas;


import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.Bullet;
import managers.AssetManager;
import managers.ProjectileManager;
import personajes.Player;

public class Shotgun extends Weapon {
	public Shotgun() {
		super("Shotgun",
				10,												// daño
				3f, 											// cadencia
				8, 												// municion
				AssetManager.getInstancia().getDisparoSound());	// sonido
		
	}

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
		float rotation = p.getRotation() + 90;
		
		float radians = (float) Math.toRadians(rotation + 90);
        float centerX = p.getSpr().getBoundingRectangle().getX() + p.getSpr().getBoundingRectangle().getWidth() / 2;
        float centerY = p.getSpr().getBoundingRectangle().getY() + p.getSpr().getBoundingRectangle().getHeight() / 2;
        float length = p.getSpr().getBoundingRectangle().getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;
        
        //simula una dispersion de balas aleatorias
        Random ra = new Random();
        
        int pellets = 8;	// perdigones
        int spread = 15; 	// grados de apertura

        for (int i = 0; i < pellets; i++) {
            // Ángulo con desviación aleatoria
            float angle = rotation + (ra.nextFloat() * spread * 2 - spread); // -15 a +15 grados

            Bullet bala = new Bullet(
            		centerX, centerY,		// posicion de la bala
                20f,                    // escala de la bala
                angle,					// angulo de la bala
                10f + ra.nextInt(4),     // velocidad levemente aleatoria
                p
            );
            manager.add(bala);
        }
  
	}
	@Override
    public Texture getDropTexture() {
        return AssetManager.getInstancia().getSTexture();
    }

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSSound();
    }
}
