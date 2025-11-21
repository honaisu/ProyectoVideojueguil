package armas.strategy;

import armas.proyectiles.Rocket;
import data.ProjectileData;
import data.RocketData;
import entidades.Entity;
import interfaces.IAttackStrategy;
import managers.ProjectileManager;

public class RocketAttackStrategy implements IAttackStrategy {
	@Override
    public void executeAttack(ProjectileData data, Entity shooter, ProjectileManager manager) {
        RocketData rocketData = (RocketData) data;
        
        Rocket newRocket = new Rocket(rocketData, shooter, manager);
        manager.add(newRocket);
    }
}
