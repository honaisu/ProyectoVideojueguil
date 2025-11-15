package armas;

import com.badlogic.gdx.audio.Sound;

import entidades.Entity;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import interfaces.IAtacable;
import interfaces.ITexture;
import managers.ProjectileManager;
import managers.assets.AssetManager;

//Clase Abstracta Arma generica
public abstract class Weapon implements IAtacable {
	protected final Sound bulletSound;
	protected final EDropType dropType;
	protected final WeaponState state;
	protected final String name;
	
	public Weapon(String nombre, WeaponState state, Sound bulletSound, EDropType dropType) {
		this.name = nombre;
		this.state = state;
		this.bulletSound = bulletSound;
		this.dropType = dropType;
	}

	public Weapon(String nombre, int damage, float cadencia, int municionMax, Sound bulletSound, EDropType dropType) {
		this.name = nombre;
		this.state = new WeaponState(cadencia, municionMax, damage);
		this.bulletSound = bulletSound;
		this.dropType = dropType;
	}

	public Weapon(String nombre, int damage, float cadencia, int municionMax, EDropType dropType) {
		// Para que tenga un sonido generico
		this(nombre, damage, cadencia, municionMax, AssetManager.getInstancia().getSound(EGameSound.SHOOT), dropType);
	}

	/**
	 * Método para crear el proyectil de cada arma, dependiendo de una entidad
	 */
	public abstract void crearProyectil(Entity p, ProjectileManager manager);
	
	/**
	 * Método abstracto para crear es sonido del drop
	 * @return
	 */
	public abstract Sound getPickupSound();
	
	@Override
	public void atacar(float delta, Entity p, ProjectileManager manager) {
		state.update(delta);
		if (!state.canShoot()) return;
		
		state.recordShot();
		
		crearProyectil(p, manager);

		playSound();
	}

	// TODO VER SONIDO DE LASERGUN
	public void playSound() {
		bulletSound.play();
	}

	// Getters y Setters
	public WeaponState getState() {
		return state;
	}
	
	/**
	 * Metodo para devolver la textura esperada del drop
	 */
	public ITexture getDropTexture() {
		return dropType;
	}

	public Sound getBulletSound() {
		return bulletSound;
	}

	public String getNombre() {
		return name;
	}
}
