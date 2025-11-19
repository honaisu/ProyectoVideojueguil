package data;

import enumeradores.recursos.EProjectileType;

/**
 * Clase de datos que se encargan de definir un proyectil genérico dentro del
 * programa. Esto involucra su textura asociada, su velocidad inicial, el
 * piercing que hace y el daño.
 */
public abstract class ProjectileData {
	public EProjectileType type;
	public float velocity = 0f;
	public boolean piercing;
	public int damage = 10;
}
