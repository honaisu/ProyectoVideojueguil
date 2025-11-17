package interfaces;

import entidades.Entity;
import managers.ProjectileManager;

// TODO Ver que onda para que no nos reten por no tener un strategy xd
@FunctionalInterface
public interface IAtacable {
	/**
	 * Método que se encarga de "atacar" a una entidad
	 * 
	 * @param delta   Diferencia de tiempo
	 * @param e       Entidad donde spawneará el proyectil
	 * @param manager Manager de proyectiles // TODO Revisar
	 */
	public void attack(Entity e, ProjectileManager manager);
}
