package logica.levels;

import java.util.ArrayList;
import java.util.List;

// Imports de tus otras clases
import interfaces.IRoundStrategy;
import logica.Round;
import personajes.Enemy; 

/**
 * Aquí definimos todos los niveles del juego.
 * Mantenemos la lógica de creación de niveles separada de GameWorld.
 */
public class LevelFactory {

    /**
     * Crea y devuelve el Nivel 1.
     */
    public static Level createLevelOne() {
        List<Round> levelOneRounds = new ArrayList<>();
        
        // --- Definición de Rondas para el Nivel 1 ---
        // (Esto es tu antiguo 'initializeRounds')

        levelOneRounds.add(new Round("Ronda 1", (em) -> {
            em.spawnEnemies(3);
        }));
        
        levelOneRounds.add(new Round("Ronda 2", (em) -> {
            em.spawnEnemies(5);
        }));

        levelOneRounds.add(new Round("Ronda 3 (Formación)", (em) -> {
            // Stats por defecto (¡Usando el constructor de 6 args que arreglamos!)
            em.add(new Enemy(500, 700, 100f, 0.05f, 50, 10));
            em.add(new Enemy(450, 650, 100f, 0.05f, 50, 10));
            em.add(new Enemy(550, 650, 100f, 0.05f, 50, 10));
        }));
        
        // Creamos el Nivel 1//puede que esto de error 
        return new Level("Nivel 1: El Espacio", "fondos/fondoNivelUno.png", levelOneRounds);
    }

    /**
     * Crea y devuelve el Nivel 2.
     */
    public static Level createLevelTwo() {
        List<Round> levelTwoRounds = new ArrayList<>();
        
        // --- Definición de Rondas para el Nivel 2 ---
        // ¡Rondas totalmente diferentes, con enemigos diferentes!
        
        levelTwoRounds.add(new Round("Ronda 1: Mutantes", (em) -> {
            // Stats para enemigos "mutantes" (diferentes al Nivel 1)
            em.add(new Enemy(200, 700, 80f, 0.1f, 80, 15)); 
            em.add(new Enemy(800, 700, 80f, 0.1f, 80, 15));
        }));
        
        levelTwoRounds.add(new Round("Ronda 2: Lluvia Tóxica", (em) -> {
            em.spawnEnemies(10); // Usa el spawner aleatorio
        }));
        
        levelTwoRounds.add(new Round("Ronda 3: Jefe Mutante", (em) -> {
            em.add(new Enemy(500, 750, 200f, 1.0f, 300, 30)); // Jefe
        }));
        
        // Creamos el Nivel 2
        return new Level("Nivel 2: Planeta Tóxico", "fondos/fondoNivelDos.png", levelTwoRounds);
    }
    
    //por el momento probemos con 2 niveles y vamos viendo si alfinal creo la clase levelOne o similar
}