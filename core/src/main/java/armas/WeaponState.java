package armas;

/**
 * Clase encargada de manejar los stats y el estado personal del arma.
 * <p>
 * Es una forma más bonita de encapsular todo lo referente a sus stats.
 */
public class WeaponState {
	public final Integer maxAmmo;
	public final float fireRate;

	private float lastShot;
	private Integer ammo;

	WeaponState(float fireRate, Integer maxMunition) {
		this.fireRate = fireRate;
		this.lastShot = fireRate;
		this.maxAmmo = maxMunition;
		this.ammo = maxMunition;
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
		if (ammo != null)
			this.ammo--;
		this.lastShot = 0;
	}

	/**
	 * Verifica si puede disparar cuando se haya cumplido el tiempo entre disparos y
	 * si es que tiene munición suficiente.
	 */
	public boolean canShoot() {
		if (ammo == null) return lastShot >= fireRate;
		return lastShot >= fireRate && ammo > 0;
	}

	public Integer getAmmo() {
		return ammo;
	}

	public void setAmmo(int amount) {
		this.ammo = amount;
	}
}