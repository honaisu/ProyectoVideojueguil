package armas;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import entidades.Player;
import entidades.proyectiles.Flame;
import entidades.proyectiles.Projectile;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class FlameShot extends Weapon {
	public FlameShot() {
		super("Flamethrower", 5, 0.08f, 120, AssetManager.getInstancia().getSound(EGameSound.SHOOT));
	}

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
		float[] muzzle = Projectile.calcularMuzzle(p, false);
		float width = 20f;
		float vel = 4f;

		// simula una dispersion del fuego
		Random ra = new Random();

		int spread = 15;
		float maxDistance = 200f; // La llama se destruirá a 350 píxeles
        float finalScale = 5.0f;

		// Ángulo con desviación aleatoria
		float angle = muzzle[2] + (ra.nextFloat() * spread * 2 - spread);

		Flame fire = new Flame(muzzle[0], muzzle[1], // posicion de la bala
				width, // escala de la bala
				angle, // angulo de la bala
				vel + ra.nextInt(4),
				p, true,
				maxDistance,
				finalScale);
		manager.add(fire);

	}

	@Override
	public Texture getDropTexture() {
		return AssetManager.getInstancia().getTexture(EDropType.FLAME_SHOT);
	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_FS);
	}

}
