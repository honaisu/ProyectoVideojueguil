package data;

import enumeradores.recursos.EDropType;

/**
 * Clase de datos de un arma genérica. Esta definida por su nombre, sus
 * estadísticas (stats), tipo de drop que lanza, sonido de disparo y tipo de
 * proyectil asociado.
 */
public class WeaponData {
	public String name;
	public float fireRate;
	public Integer maxAmmo;
	public EDropType drop;
	public String soundFire;
	public ProjectileData projectileData;
}
