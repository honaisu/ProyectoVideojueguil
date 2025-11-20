package armas;

import com.badlogic.gdx.audio.Sound;

import data.ProjectileData;
import data.WeaponData;
import entidades.Entity;
import enumeradores.recursos.EDropType;
import interfaces.IAttackStrategy;
import interfaces.IAttackable;
import interfaces.IState;
import interfaces.ITexture;
import managers.ProjectileManager;
import managers.assets.AssetManager;

/**
 * Clase Arma
 */
public class Weapon implements IAttackable {
	private final Sound fireSound;
	private final EDropType type;
	private final String name;
	
	private final IState state;
	private final IAttackStrategy strategy;
	private final ProjectileData data;

	public Weapon(WeaponData data, IState state, IAttackStrategy strategy) {
		this.data = data.projectileData;
		this.strategy = strategy;
		this.state = state;
		
		// Para mantenerlo ordenado
		this.name = data.name;
		this.type = data.drop;

		this.fireSound = AssetManager.getInstancia().getSound(data.soundFire);
	}

	@Override
	public void attack(Entity p, ProjectileManager manager) {
		if (!state.canAttack())
			return;

		state.recordAttack();
		strategy.executeAttack(data, p, manager);

		playSound();
	}
	
	@Override
	public void update(float delta) {
		state.update(delta);
	}

	@Override
	public boolean isValid() {
		return state.isValid();
	}

	public void playSound() {
		fireSound.play();
	}

	// Getters y Setters

	/**
	 * Consigue el estado de la arma actual :)
	 */
	public IState getState() {
		return state;
	}
	
	public boolean isValidState() {
		return state.isValid();
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
	@Override
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
