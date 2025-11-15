package armas;

/**
 * Clase encargada de manejar los stats personales del arma.
 * <p>
 * Es una forma más bonita de encapsular todo lo referente a sus stats
 */
public class WeaponStats {
	public final int maxAmmo;
	public final float fireRate;

	private float lastShot;
	private int damage;
	private int ammo;

	WeaponStats(float fireRate, int maxMunition, int damage) {
		this.fireRate = fireRate;
		this.lastShot = fireRate;
		this.maxAmmo = maxMunition;
		this.ammo = maxMunition;
		this.damage = damage;
	}

	public int getAmmo() {
		return ammo;
	}

	public int getDamage() {
		return damage;
	}

	public float getLastShot() {
		return lastShot;
	}
	
	public void setLastShot(float amount) {
		this.lastShot = amount;
	}

	public void setAmmo(int amount) {
		this.ammo = amount;
	}
}