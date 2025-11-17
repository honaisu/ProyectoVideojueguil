package armas;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;

import entidades.Entity;
import entidades.proyectiles.Flame;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class FlameShot extends Weapon {
	public FlameShot() {
		super("Flamethrower", 5, 0.08f, 120, AssetManager.getInstancia().getSound(EGameSound.SHOOT), EDropType.FLAME_SHOT);
	}

	@Override
	public void crearProyectil(Entity p, ProjectileManager manager) {
		float scale = 20;
		float vel = 4f;

		// simula una dispersion del fuego
		Random ra = new Random();

		int spread = 15;
		float maxDistance = 200f; // La llama se destruirá a 350 píxeles
        float finalScale = 5.0f;

		// Ángulo con desviación aleatoria
		float angle = p.getRotation() + 90 + (ra.nextFloat() * spread * 2 - spread);
		vel += ra.nextInt(2); 

		Flame fire = new Flame(p, // posicion de la bala
				state.getDamage(), // escala de la bala
				angle,
				vel, // angulo de la bala
				scale,
				true,
				maxDistance,
				finalScale);
		manager.add(fire);

	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_FS);
	}

}
