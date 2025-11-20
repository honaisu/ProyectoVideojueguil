package interfaces;

import data.ProjectileData;
import entidades.Entity;
import managers.ProjectileManager;

public interface IAttackStrategy {
	void executeAttack(ProjectileData data, Entity shooter, ProjectileManager manager);
}
