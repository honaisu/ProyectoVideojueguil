package interfaces;

import data.ProjectileData;
import entidades.Entity;
import managers.ProjectileManager;

/**
 * Interfaz encargada de requerir un ataque en base a datos de proyectil
 * entrantes.
 */
@FunctionalInterface
public interface IAttackStrategy {
	void executeAttack(ProjectileData data, Entity shooter, ProjectileManager manager);
}
