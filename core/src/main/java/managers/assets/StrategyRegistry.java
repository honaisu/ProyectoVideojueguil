package managers.assets;

import java.util.HashMap;
import java.util.Map;

import armas.strategy.BulletAttackStrategy;
import armas.strategy.FlameAttackStrategy;
import armas.strategy.MeleeAttackStrategy;
import armas.strategy.RocketAttackStrategy;
import enumeradores.recursos.EProjectileType;
import interfaces.IAttackStrategy;

/**
 * :o
 */
public class StrategyRegistry {
	private static final Map<EProjectileType, IAttackStrategy> registry = new HashMap<>();
	
	public void load() {
		registry.put(EProjectileType.ROCKET, new RocketAttackStrategy());
        registry.put(EProjectileType.FLAME, new FlameAttackStrategy());
        
        IAttackStrategy bulletStrat = new BulletAttackStrategy();
        registry.put(EProjectileType.HOLLOWPOINT, bulletStrat);
        registry.put(EProjectileType.ROUNDNOSE, bulletStrat);
        registry.put(EProjectileType.RAYGUN, bulletStrat);
		
        registry.put(EProjectileType.SWING, new MeleeAttackStrategy());
	}
	
	public IAttackStrategy get(EProjectileType type) {
        IAttackStrategy strategy = registry.get(type);
        if (strategy == null) {
            return registry.get(EProjectileType.SWING); 
        }
        return strategy;
    }
}
