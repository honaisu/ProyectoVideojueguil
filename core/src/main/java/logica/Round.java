package logica;

import interfaces.IRoundStrategy; // Implementa la interface de Round
import managers.EnemyManager;     // posiblemnte sirva

public class Round {
    
    private final String name;
    private final IRoundStrategy spawnStrategy;
    
    public Round(String name, IRoundStrategy spawnStrategy) {
        this.name = name;
        this.spawnStrategy = spawnStrategy;
    }
    
    public String getName() {
        return name;
    }
    
    public void executeSpawn(EnemyManager enemyManager) {
        //System.out.println("Iniciando: " + name); 
        spawnStrategy.spawnEnemies(enemyManager);
    }
}