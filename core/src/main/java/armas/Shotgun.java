package armas;


import java.util.Random;

import com.badlogic.gdx.audio.Sound;

import entidades.proyectiles.Bullet;
import entidades.Entity;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class Shotgun extends Weapon {
	public Shotgun() {
		super("Shotgun",
				10,												// daño
				2.5f, 											// cadencia
				8,
				EDropType.SHOTGUN);	// sonido
	}

	@Override
	public void crearProyectil(Entity p, ProjectileManager manager) {
        float width = 20f;
        float vel = 10f;
        
        //simula una dispersion de balas aleatorias
        Random ra = new Random();
        
        int pellets = 8;
        int spread = 15;

        for (int i = 0; i < pellets; i++) {
            // Ángulo con desviación aleatoria
            float angle = (ra.nextFloat() * spread * 2 - spread);

            Bullet bala = new Bullet(
            		p.getPosition().x, p.getPosition().y, width, angle,
                vel + ra.nextInt(4),
                state.getDamage(),
                false);
            
            manager.add(bala);
        }
  
	}

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSound(EGameSound.DROP_SHOTGUN);
    }
}
