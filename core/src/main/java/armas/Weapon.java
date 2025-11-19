package armas;

import com.badlogic.gdx.audio.Sound;

import data.WeaponData;
import entidades.Entity;
import enumeradores.recursos.EDropType;
import factories.ProjectileFactory;
import interfaces.IAttackable;
import interfaces.ITexture;
import managers.ProjectileManager;
import managers.assets.AssetManager;

/**
 * Clase Arma
 */
public class Weapon implements IAttackable {
	private final Sound fireSound;
	private final WeaponState state;
	private final EDropType type;
	private final String name;

	public Weapon(WeaponData data) {
		this.name = data.name;
		this.state = new WeaponState(data.fireRate, data.maxAmmo);

		this.type = data.drop;
		this.fireSound = AssetManager.getInstancia().getSound(data.soundFire);
	}

	@Override
	public void attack(Entity p, ProjectileManager manager) {
		if (!state.canShoot())
			return;

		state.recordShot();

		ProjectileFactory.create(type, p, manager);

		playSound();
	}

	public void playSound() {
		fireSound.play();
	}

	// Getters y Setters

	/**
	 * Consigue el estado de la arma actual :)
	 */
	public WeaponState getState() {
		return state;
	}

	/**
	 * Método para crear el sonido del drop
	 */
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(type.getGameSound());
	}

	/**
	 * Metodo para devolver la textura esperada del drop
	 */
	public ITexture getDropTexture() {
		return type;
	}

	public Sound getFireSound() {
		return fireSound;
	}

	public String getName() {
		return name;
	}

}
