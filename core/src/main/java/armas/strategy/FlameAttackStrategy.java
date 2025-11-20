package armas.strategy;

import data.FlameData;
import data.ProjectileData;
import entidades.Entity;
import entidades.proyectiles.Flame;
import interfaces.IAttackStrategy;
import managers.ProjectileManager;

public class FlameAttackStrategy implements IAttackStrategy {
	@Override
	public void executeAttack(ProjectileData data, Entity shooter, ProjectileManager manager) {
		FlameData flameData = (FlameData) data;
		
		Flame newFlame = new Flame(flameData, shooter);
		manager.add(newFlame);
	}
}
