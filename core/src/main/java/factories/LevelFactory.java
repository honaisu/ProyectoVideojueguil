package factories;

import java.util.ArrayList;
import java.util.List;

import entidades.Enemy;
import enumeradores.recursos.EBackgroundType;
// Imports de tus otras clases
import logica.Round;
import logica.levels.Level;

/**
 * Aquí definimos todos los niveles del juego.
 * Mantenemos la lógica de creación de niveles separada de GameWorld.
 */
public class LevelFactory {
	
	//Creamos nivel 1 y lo retornamos
    public static Level createLevelOne() {
        List<Round> levelOneRounds = new ArrayList<>();

        //Ronda 1: -> 3 enemigos
        levelOneRounds.add(new Round("Ronda 1", (em) -> {
            em.spawnEnemies(3);
        }));
        
        // Ronda 2: -> 5 enemigos
        levelOneRounds.add(new Round("Ronda 2", (em) -> {
            em.spawnEnemies(5);
        }));

        // Ronda 3: Formación 
        levelOneRounds.add(new Round("Ronda 3 (Formación)", (em) -> {
            // Stats por defecto (¡Usando el constructor de 6 args que arreglamos!)
            em.add(new Enemy(500, 700, 100f, 0.05f, 50, 10));
            em.add(new Enemy(450, 650, 100f, 0.05f, 50, 10));
            em.add(new Enemy(550, 650, 100f, 0.05f, 50, 10));
        }));
        
        // Ronda 4: Emboscada (4 enemigos) ---
        levelOneRounds.add(new Round("Ronda 4 (Emboscada)", (em) -> {
            // Enemigos en los costados de la pantalla para forzar movimiento
            float defaultSize = 100f;
            float defaultDrop = 0.05f;
            int defaultHealth = 50;
            int defaultDamage = 10;
            
            // Lado Izquierdo
            em.add(new Enemy(100, 300, defaultSize, defaultDrop, defaultHealth, defaultDamage));
            em.add(new Enemy(100, 500, defaultSize, defaultDrop, defaultHealth, defaultDamage));
            
            // Lado Derecho (asumiendo 1200 de ancho)
            em.add(new Enemy(1100, 300, defaultSize, defaultDrop, defaultHealth, defaultDamage));
            em.add(new Enemy(1100, 500, defaultSize, defaultDrop, defaultHealth, defaultDamage));
        }));
        
        // Ronda 5: Mini-Jefe (1 Fuerte + 5 débiles) ---
        levelOneRounds.add(new Round("Ronda 5 (El Capitán)", (em) -> {
            // 1. Un grupo de enemigos normales aleatorios
            em.spawnEnemies(5);
            
            // 2. Un "Jefe" más fuerte en el centro
            float bossSize = 150f;   // Más grande
            float bossDrop = 0.5f;   // 50% drop
            int bossHealth = 200;  // Más vida
            int bossDamage = 25;   // Más daño
            
            em.add(new Enemy(600, 700, bossSize, bossDrop, bossHealth, bossDamage));
        }));

        // (He notado que cambiaste el String del fondo por un Enum, ¡así se usa!)
        return new Level("Nivel 1: El Espacio", EBackgroundType.ONE, levelOneRounds);
    }

    //Crea el nivel 2 y lo retorna
    public static Level createLevelTwo() {
        List<Round> levelTwoRounds = new ArrayList<>();
        
        // Ronda 1: Patrulla Mutante 
        // Empezamos con enemigos más fuertes que los del Nivel 1
        levelTwoRounds.add(new Round("Ronda 1: Patrulla", (em) -> {
            float size = 80f;
            float drop = 0.1f;
            int health = 80;
            int damage = 15;
            
            em.add(new Enemy(200, 700, size, drop, health, damage)); 
            em.add(new Enemy(600, 750, size, drop, health, damage));
            em.add(new Enemy(1000, 700, size, drop, health, damage));
        }));
        
        // Ronda 2: Lluvia Tóxica (10 enemigos) 
        // Esta es tu ronda, ¡está perfecta! Una oleada de enemigos aleatorios.
        levelTwoRounds.add(new Round("Ronda 2: Lluvia Tóxica", (em) -> {
            em.spawnEnemies(10); // Usa el spawner aleatorio
        }));

        // Ronda 3: Enjambre (15 enemigos débiles)
        // Un tipo de ronda diferente: muchos enemigos, pero débiles (como "crías")
        levelTwoRounds.add(new Round("Ronda 3: Enjambre", (em) -> {
            float size = 60f;   // Más pequeños
            float drop = 0.01f; // Casi no dan drops
            int health = 25;    // Mueren de 1-2 golpes
            int damage = 10;
            
            // Creamos 15 en posiciones aleatorias
            for (int i = 0; i < 15; i++) {
                float x = (float)(Math.random() * 1000 + 100);
                float y = (float)(Math.random() * 200 + 600);
                em.add(new Enemy(x, y, size, drop, health, damage));
            }
        }));
        
        // Ronda 4: Flanqueo (6 enemigos) 
        // Combinamos enemigos normales y "mutantes" más fuertes
        levelTwoRounds.add(new Round("Ronda 4: Flanqueo", (em) -> {
            // 1. Dos mutantes fuertes en el centro-arriba
            em.add(new Enemy(400, 750, 80f, 0.1f, 80, 15));
            em.add(new Enemy(800, 750, 80f, 0.1f, 80, 15));
            
            // 2. Cuatro enemigos normales aleatorios para distraer
            em.spawnEnemies(4);
        }));

        // Ronda 5: Jefe Mutante (¡Ahora con escolta!)
        levelTwoRounds.add(new Round("Ronda 5: Jefe Mutante", (em) -> {
            // 1. El Jefe (tu diseño original)
            em.add(new Enemy(500, 750, 200f, 1.0f, 300, 30)); // Jefe
            
            // 2. Una "Lluvia Tóxica" de escolta al mismo tiempo
            em.spawnEnemies(8);
        }));
        
        // Creamos el Nivel 2
        return new Level("Nivel 2: Planeta Tóxico", EBackgroundType.TWO, levelTwoRounds);
    }
    
    //creamos el nivel 3 (biomoid de awua)
    public static Level createLevelThree() {
        List<Round> levelThreeRounds = new ArrayList<>();

        // Ronda 1: La Corriente 
        // Los enemigos cruzan la pantalla horizontalmente, como una corriente.
        levelThreeRounds.add(new Round("Ronda 1: La Corriente", (em) -> {
            float size = 80f, drop = 0.1f;
            int health = 80, damage = 15;
            
            for (int i = 0; i < 4; i++) {
                // (x, y, size, drop, health, damage)
                // Spawnean fuera de la pantalla (izquierda)
                Enemy e = new Enemy(-100, (float)(Math.random() * 600 + 100), size, drop, health, damage);
                
                // ¡MOVIMIENTO! (Velocidad 100, Ángulo 0 = Hacia la derecha)
                e.setPolarVelocityLocal(100f, 0); 
                em.add(e);
            }
        }));
        
        // Ronda 2: Burbujas 
        // Los enemigos suben desde el fondo de la pantalla.
        levelThreeRounds.add(new Round("Ronda 2: Burbujas", (em) -> {
            float size = 80f, drop = 0.1f;
            int health = 80, damage = 15;

            for (int i = 0; i < 6; i++) {
                // Spawnean en X aleatorias, en el fondo (fuera de pantalla)
                float x = (float)(Math.random() * 1000 + 100);
                Enemy e = new Enemy(x, -100, size, drop, health, damage);
                
                // ¡MOVIMIENTO! (Velocidad 90, Ángulo 90 = Hacia arriba)
                e.setPolarVelocityLocal(90f, 90); 
                em.add(e);
            }
        }));

        // Ronda 3: Banco de Peces 
        // Un grupo grande de enemigos débiles que se mueven juntos.
        levelThreeRounds.add(new Round("Ronda 3: Banco", (em) -> {
            float speed = 120f; // Velocidad del banco
            float angle = 200f; // Dirección (diagonal abajo-izquierda)
            
            for (int i = 0; i < 12; i++) {
                // Spawnean en un grupo fuera de la pantalla (derecha)
                float x = 1300 + (float)(Math.random() * 100 - 50);
                float y = 400 + (float)(Math.random() * 100 - 50);
                
                // Stats de enemigos débiles
                Enemy e = new Enemy(x, y, 60f, 0.01f, 25, 10);
                e.setPolarVelocityLocal(speed, angle);
                em.add(e);
            }
        }));
        
        // Ronda 4: Depredadores 
        // Una mezcla de enemigos aleatorios (con movimiento aleatorio)
        // y dos enemigos más fuertes que los flanquean.
        levelThreeRounds.add(new Round("Ronda 4: Depredadores", (em) -> {
            // 5 enemigos aleatorios (spawnEnemies ya les da velocidad)
            em.spawnEnemies(5);
            
            // 2 "Depredadores" más fuertes con movimiento fijo
            Enemy p1 = new Enemy(100, 700, 100f, 0.2f, 100, 20);
            p1.setPolarVelocityLocal(150f, 300); // Rápido y hacia abajo-izquierda
            em.add(p1);
            
            Enemy p2 = new Enemy(1100, 700, 100f, 0.2f, 100, 20);
            p2.setPolarVelocityLocal(150f, 240); // Rápido y hacia abajo-derecha
            em.add(p2);
        }));

        // Ronda 5: El Leviatán 
        // Un jefe grande y lento con muchos enemigos aleatorios.
        levelThreeRounds.add(new Round("Ronda 5: El Leviatán", (em) -> {
            // 1. Escolta de 10 enemigos aleatorios
            em.spawnEnemies(10);
            
            // 2. El Jefe
            Enemy boss = new Enemy(600, 900, 250f, 1.0f, 500, 35);
            // ¡MOVIMIENTO! (Velocidad 40, Ángulo 270 = Lento hacia abajo)
            boss.setPolarVelocityLocal(40f, 270); 
            em.add(boss);
        }));
        
        //Finalemnte creamos el nivel 3
        return new Level("Nivel 3: Fosa Abisal", EBackgroundType.THREE, levelThreeRounds);
    }
}