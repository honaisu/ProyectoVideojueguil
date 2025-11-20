package armas.strategy;

import data.ProjectileData;
import data.SwingData;
import entidades.Entity;
import entidades.proyectiles.Swing;
import interfaces.IAttackStrategy;
import managers.ProjectileManager;

public class MeleeAttackStrategy implements IAttackStrategy {
	@Override
	public void executeAttack(ProjectileData data, Entity shooter, ProjectileManager manager) {
		SwingData swingData = (SwingData) data;
        
        Swing newSwing = new Swing(swingData, shooter);
        manager.add(newSwing);
	}
}
