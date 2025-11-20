package data;

import enumeradores.recursos.EProjectileType;

/**
 * Clase de datos que se encargan de definir un proyectil genérico dentro del
 * programa. Esto involucra su textura asociada, su velocidad inicial, el
 * piercing que hace y el daño.
 */
public abstract class ProjectileData {
	private EProjectileType type;
	private float velocity = 0f;
	private boolean piercing;
	private int damage = 10;
	
	public int getDamage() {
		return damage;
	}
	public boolean isPiercing() {
		return piercing;
	}
	public float getVelocity() {
		return velocity;
	}
	public EProjectileType getType() {
		return type;
	}
}
