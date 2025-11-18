package armas;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;

import entidades.proyectiles.Bullet;
import entidades.Entity;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class Shotgun extends Weapon {
	public Shotgun() {
		super("Shotgun", 20, // daño
				2f, // cadencia
				8, EDropType.SHOTGUN);
	}

	@Override
	public void crearProyectil(Entity p, ProjectileManager manager) {
		int width = 20;
		int vel = 7;

		// simula una dispersion de balas aleatorias
		Random ra = new Random();

		int pellets = 8;
		int spread = 15;

		for (int i = 0; i < pellets; i++) {
			// Ángulo con desviación aleatoria
			float angle = (p.getRotation() + 90) + (ra.nextFloat() * spread * 2 - spread);
			vel += ra.nextInt(2);

			Bullet bullet = new Bullet(p, EProjectileType.ROUNDNOSE, state.getDamage(), vel, width, angle, false);

			manager.add(bullet);
		}

	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_SHOTGUN);
	}
}
