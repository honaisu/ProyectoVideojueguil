package interfaces; 

import managers.EnemyManager; // para gestionar los enemigos
/**
 * Interfaz FUNCIONAL. Define una sola acción: "generar enemigos".
 * Al tener un solo método, podemos implementarla con una lambda.
 */
@FunctionalInterface
public interface IRoundStrategy {
    /**
     * Ejecuta la lógica de spawning de esta ronda.
     * @param enemyManager El manager al que se añadirán los enemigos.
     */
    void spawnEnemies(EnemyManager enemyManager);
}