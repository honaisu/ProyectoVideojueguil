package logica.levels;

import java.util.List;

import enumeradores.recursos.EBackgroundType;
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
    private final EBackgroundType background;
    // ¡La lista de rondas de este nivel!
    private final List<Round> rounds;    
    // Para saber qué ronda toca
    private int currentRoundIndex; 
    
  //para cada nivel abra una friccion (mentira solo cambia la la nieve) y quisas la el agua
    private final float playerFriction;
    private final int hazardCount; // contador de objeto de daño
    private final int solidCount;  //contador de objeto solido
    

    public Level(String levelName, EBackgroundType background, List<Round> rounds, float playerFriction, int hazardCount, int solidCount) {
        this.levelName = levelName;
        this.background = background;
        this.rounds = rounds;
        this.currentRoundIndex = -1; // Empieza en -1, listo para la primera ronda
        this.playerFriction = playerFriction;
        this.hazardCount = hazardCount; 
        this.solidCount = solidCount;   
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

    public String getLevelName() { 
        return levelName; 
    }
    
    public EBackgroundType getBackground() { 
        return background; 
    }
    
    //va a servir para ver elt ema de la friccion actual del nivel
    public float getPlayerFriction() {
        return playerFriction;
    }
    
    
    //setter y getters para los obstaculos
    public int getHazardCount() { return hazardCount; }
    public int getSolidCount() { return solidCount; }
    
}