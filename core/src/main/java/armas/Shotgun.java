package armas;


import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import entidades.proyectiles.Bullet;
import entidades.proyectiles.Projectile;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import interfaces.ITexture;
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
	public void crearProyectil(Player p, ProjectileManager manager) {
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
                stats.getDamage(),
                false);
            
            manager.add(bala);
        }
  
	}

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSound(EGameSound.DROP_SHOTGUN);
    }
}
