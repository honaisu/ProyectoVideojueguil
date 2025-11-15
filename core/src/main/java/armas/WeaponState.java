package armas;

/**
 * Clase encargada de manejar los stats y el estado personal del arma.
 * <p>
 * Es una forma más bonita de encapsular todo lo referente a sus stats.
 */
public class WeaponState {
	public final int maxAmmo;
	public final float fireRate;

	private float lastShot;
	private int damage;
	private int ammo;

	WeaponState(float fireRate, int maxMunition, int damage) {
		this.fireRate = fireRate;
		this.lastShot = fireRate;
		this.maxAmmo = maxMunition;
		this.ammo = maxMunition;
		this.damage = damage;
	}

	/**
	 * Actualiza el timer entre disparos.
	 * 
	 * @param delta Diferencia de tiempo
	 */
	public void update(float delta) {
		if (lastShot < fireRate) {
			lastShot += delta;
		}
	}

	/**
	 * Método que se encarga de registrar un disparo y preparar el cooldown,
	 * consumiendo su munición en el proceso.
	 */
	public void recordShot() {
		this.ammo--;
		this.lastShot = 0;
	}
	
	/**
	 * Verifica si puede disparar cuando se haya cumplido el tiempo entre disparos y
	 * si es que tiene munición suficiente.
	 */
	public boolean canShoot() {
		return lastShot >= fireRate && ammo > 0;
	}

	public int getAmmo() {
		return ammo;
	}

	public int getDamage() {
		return damage;
	}

	public void setAmmo(int amount) {
		this.ammo = amount;
	}
}