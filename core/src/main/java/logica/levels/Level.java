package logica.levels;

import java.util.List;
import logica.GameLogicHandler;
import logica.Round;

/**
 * Contiene toda la información de un nivel:
 * - Su lista de rondas.
 * - Su textura de fondo (opcional).
 * - Su nombre.
 */
public class Level {

    private final String levelName;
    private final String backgroundAssetPath; // Path para la textura de fondo
    private final List<Round> rounds;         // ¡La lista de rondas de este nivel!
    
    private int currentRoundIndex; // Para saber qué ronda toca

    public Level(String levelName, String backgroundAssetPath, List<Round> rounds) {
        this.levelName = levelName;
        this.backgroundAssetPath = backgroundAssetPath;
        this.rounds = rounds;
        this.currentRoundIndex = -1; // Empieza en -1, listo para la primera ronda
    }

    /**
     * Devuelve el nombre de la ronda actual (ej: "Ronda 1: 5 Aleatorios").
     * Esto lo usará el HUD.
     */
    public String getCurrentRoundName() {
        if (currentRoundIndex < 0) return "Ronda 1"; // Aún no empieza la primera
        if (currentRoundIndex < rounds.size()) {
            return rounds.get(currentRoundIndex).getName();
        }
        return "¡Nivel Completado!";
    }

    /**
     * Avanza a la siguiente ronda de ESTE nivel y la ejecuta.
     * Devuelve 'true' si el nivel se terminó (ya no hay más rondas).
     */
    public boolean advanceToNextRound(GameLogicHandler handler) {
        currentRoundIndex++;
        
        if (currentRoundIndex < rounds.size()) {
            // Hay más rondas, las ejecutamos
            Round nextRound = rounds.get(currentRoundIndex);
            nextRound.executeSpawn(handler.getEnemyManager());
            return false; // El nivel NO ha terminado
        }
        
        return true; // El nivel SÍ ha terminado (no hay más rondas)
    }

    // --- Getters ---
    // (Para que GameWorld y GameScreen los usen)

    public String getLevelName() { 
        return levelName; 
    }
    
    public String getBackgroundAssetPath() { 
        return backgroundAssetPath; 
    }
}