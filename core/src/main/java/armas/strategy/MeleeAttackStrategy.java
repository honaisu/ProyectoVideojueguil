package armas.strategy;

import armas.proyectiles.Swing;
import data.ProjectileData;
import data.SwingData;
import entidades.Entity;
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
