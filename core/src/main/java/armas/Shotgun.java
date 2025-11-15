package armas;


import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import entidades.proyectiles.Bullet;
import entidades.proyectiles.Projectile;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class Shotgun extends Weapon {
	public Shotgun() {
		super("Shotgun",
				10,												// daño
				2.5f, 											// cadencia
				8, 												// municion
				AssetManager.getInstancia().getSound(EGameSound.SHOOT));	// sonido
	}

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
        float[] muzzle = Projectile.calcularMuzzle(p, false);
        float width = 20f;
        float vel = 10f;
        
        //simula una dispersion de balas aleatorias
        Random ra = new Random();
        
        int pellets = 8;
        int spread = 15;

        for (int i = 0; i < pellets; i++) {
            // Ángulo con desviación aleatoria
            float angle = muzzle[2] + (ra.nextFloat() * spread * 2 - spread);

            Bullet bala = new Bullet(
            		muzzle[0], muzzle[1],	// posicion de la bala
                width,                    	// escala de la bala
                angle,						// angulo de la bala
                vel + ra.nextInt(4),     	// velocidad levemente aleatoria
                p
            );
            manager.add(bala);
        }
  
	}
	@Override
    public Texture getDropTexture() {
        return AssetManager.getInstancia().getTexture(EDropType.SHOTGUN);
    }

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSound(EGameSound.DROP_SHOTGUN);
    }
}
