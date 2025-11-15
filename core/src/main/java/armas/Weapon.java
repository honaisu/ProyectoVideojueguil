package armas;

import com.badlogic.gdx.audio.Sound;

import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import interfaces.IDisparable;
import interfaces.ITexture;
import managers.ProjectileManager;
import managers.assets.AssetManager;

//Clase Abstracta Arma generica
public abstract class Weapon implements IDisparable {
	protected final Sound bulletSound;
	protected final EDropType dropType;
	protected final WeaponStats stats;
	protected final String name;
	
	public Weapon(String nombre, WeaponStats stats, Sound bulletSound, EDropType dropType) {
		this.name = nombre;
		this.stats = stats;
		this.bulletSound = bulletSound;
		this.dropType = dropType;
	}

	public Weapon(String nombre, int damage, float cadencia, int municionMax, Sound bulletSound, EDropType dropType) {
		this.name = nombre;
		this.stats = new WeaponStats(cadencia, municionMax, damage);
		this.bulletSound = bulletSound;
		this.dropType = dropType;
	}

	public Weapon(String nombre, int damage, float cadencia, int municionMax, EDropType dropType) {
		// Para que tenga un sonido generico
		this(nombre, damage, cadencia, municionMax, AssetManager.getInstancia().getSound(EGameSound.SHOOT), dropType);
	}

	/**
	 * Método para crear el proyectil de cada arma
	 */
	public abstract void crearProyectil(Player p, ProjectileManager manager);
	
	// metodo abstracto para crear es sonido del drop
	public abstract Sound getPickupSound();
	
	@Override
	public void atacar(float delta, Player p, ProjectileManager manager) {
		actualizar(delta);
		if (!puedeDisparar())
			return;
		if (stats.getAmmo() <= 0)
			return;

		restarMunicion(delta);
		//if (puedeDisparar())
			//reiniciarCooldown();
		crearProyectil(p, manager);

		playSound();
	}

	// TODO VER SONIDO DE LASERGUN
	public void playSound() {
		bulletSound.play();
	}

	public void restarMunicion(float delta) {
		stats.setLastShot(stats.getLastShot() + delta);
		if (puedeDisparar()) {
			stats.setAmmo(stats.getAmmo() - 1);
			reiniciarCooldown();
		}
	}

	// actualiza el timer entre disparo y disparo
	public void actualizar(float delta) {
		if (!puedeDisparar())
			stats.setLastShot(stats.getLastShot() + delta);
	}

	// verifica si puede disparar cuando se haya cumplido el tiempo entre disparo y
	// disparo
	protected boolean puedeDisparar() {
		return stats.getLastShot() >= stats.fireRate;
	}

	// reinicia el tiempo entre el disparo y el siguente
	protected void reiniciarCooldown() {
		stats.setLastShot(0);
	}

	// Getters y Setters
	public WeaponStats getStats() {
		return stats;
	}
	
	/**
	 * metodo abstracto para crear la textura del drop
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
