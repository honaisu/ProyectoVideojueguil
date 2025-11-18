package armas;

import com.badlogic.gdx.audio.Sound;

import entidades.Entity;
import entidades.proyectiles.Rocket;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class RocketLauncher extends Weapon {
	public RocketLauncher() {
		super("Ray Gun", 100, // daño
				1.5f, // cadencia
				5, // municion
				AssetManager.getInstancia().getSound(EGameSound.SHOOT),
				EDropType.ROCKET_LAUNCHER);
	}

	@Override
	public void crearProyectil(Entity p, ProjectileManager manager) {
		int width = 30;
		float vel = 0.2f;

		Rocket rocket = new Rocket(p, state.getDamage(), width, vel, manager, false);
		manager.add(rocket);

	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_RL);

	}
}
