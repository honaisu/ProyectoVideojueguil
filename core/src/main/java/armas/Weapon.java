package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import entidades.Player;
import enumeradores.recursos.EGameSound;
import interfaces.IDisparable;
import managers.ProjectileManager;
import managers.assets.AssetManager;

//Clase Abstracta Arma generica
public abstract class Weapon implements IDisparable {
	private String name;

	private int damage; // Daño
	private float cadence; // Cadencia
	private float lastTimeShot; // Contador para un disoaro despues de otro
	private int ammo; // Municion actual
	private int maxAmmo; // Municion Maxima
	private Sound shotSound;

	public Weapon(String name, int damage, float cadence, int maxAmmo, Sound shotSound) {
		this.name = name;
		this.damage = damage;
		this.cadence = cadence;
		this.maxAmmo = maxAmmo;
		this.ammo = maxAmmo;
		this.shotSound = shotSound;
		this.lastTimeShot = cadence; // para que dispare instantaneamente el primer disparo
	}

	public Weapon(String nombre, int damage, float cadencia, int municionMax) {
		// Para que tenga un sonido generico
		this(nombre, damage, cadencia, municionMax, AssetManager.getInstancia().getSound(EGameSound.SHOOT));
	}

	// metodo abstracto para crear el proyectil para cada arma
	public abstract void crearProyectil(Player p, ProjectileManager manager);

	// metodo abstracto para crear la textura del drop
	public abstract Texture getDropTexture();

	// metodo abstracto para crear es sonido del drop
	public abstract Sound getPickupSound();

	@Override
	public void atack(float delta, Player p, ProjectileManager manager) {
		update(delta);
		if (!canShoot())
			return;
		if (getAmmo() <= 0)
			return;

		subtractAmmo(delta);
		if (canShoot())
			resetCooldown();
		crearProyectil(p, manager);

		playSound();
	}

	// TODO VER SONIDO DE LASERGUN
	public void playSound() {
		shotSound.play();
	}

	public void subtractAmmo(float delta) {
		lastTimeShot += delta;
		if (lastTimeShot >= cadence) {
			ammo -= 1;
			lastTimeShot = 0;
		}
	}

	// actualiza el timer entre disparo y disparo
	public void update(float delta) {
		if (lastTimeShot < cadence) {
			lastTimeShot += delta;
		}
	}

	// verifica si puede disparar cuando se haya cumplido el tiempo entre disparo y
	// disparo
	protected boolean canShoot() {
		return lastTimeShot >= cadence;
	}

	// reinicia el tiempo entre el disparo y el siguente
	protected void resetCooldown() {
		lastTimeShot = 0;
	}

	// Getters y Setters
	public int getDamage() {
		return damage;
	}

	public int getAmmo() {
		return ammo;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public String getName() {
		return name;
	}

}
