package data;

import enumeradores.recursos.texturas.EDropType;

/**
 * Clase de datos de un arma genérica. Esta definida por su nombre, sus
 * estadísticas (stats), tipo de drop que lanza, sonido de disparo y tipo de
 * proyectil asociado.
 */
public class WeaponData {
	private String name;
	private float fireRate;
	private Integer maxAmmo;
	private EDropType drop;
	private String soundFire;
	private ProjectileData projectileData;
	
	public WeaponData() {}

	public ProjectileData getProjectileData() {
		return projectileData;
	}

	public String getSoundFire() {
		return soundFire;
	}

	public EDropType getDrop() {
		return drop;
	}

	public Integer getMaxAmmo() {
		return maxAmmo;
	}

	public float getFireRate() {
		return fireRate;
	}

	public String getName() {
		return name;
	}
}
