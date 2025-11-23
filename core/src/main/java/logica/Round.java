package logica;

import interfaces.IRoundStrategy;
import managers.EnemyManager;

public class Round {
    private final String name;
    private IRoundStrategy spawnStrategy;
    
    public Round(String name, IRoundStrategy spawnStrategy) {
        this.name = name;
        this.spawnStrategy = spawnStrategy;
    }
    
    public String getName() {
        return name;
    }
    
    public void executeSpawn(EnemyManager enemyManager) {
        spawnStrategy.spawnEnemies(enemyManager);
    }
}