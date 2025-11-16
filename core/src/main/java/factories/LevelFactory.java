package factories;

import java.util.ArrayList;
import java.util.List;

import entidades.Enemy;
import enumeradores.recursos.EBackgroundType;
// Imports de tus otras clases
import logica.Round;
import logica.levels.Level;


public class LevelFactory {

	// Creamos nivel 1 y lo retornamos
	public static Level createLevelOne() {
		List<Round> levelOneRounds = new ArrayList<>();

		// Ronda 1: -> 3 enemigos
		levelOneRounds.add(new Round("Ronda 1", (em) -> {
			em.spawnEnemies(3);
		}));

		// Ronda 2: -> 5 enemigos
		levelOneRounds.add(new Round("Ronda 2", (em) -> {
			em.spawnEnemies(5);
		}));

		// Ronda 3: Formación
		levelOneRounds.add(new Round("Ronda 3 (Formación)", (em) -> {
			// Stats por defecto 
			em.add(new Enemy(500, 700, 100f, 0.05f, 100, 10));
			em.add(new Enemy(450, 650, 100f, 0.05f, 100, 10));
			em.add(new Enemy(550, 650, 100f, 0.05f, 100, 10));
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

			// Lado Derecho 
			em.add(new Enemy(1100, 300, defaultSize, defaultDrop, defaultHealth, defaultDamage));
			em.add(new Enemy(1100, 500, defaultSize, defaultDrop, defaultHealth, defaultDamage));
		}));

		// Ronda 5: Mini-Jefe (1 Fuerte + 5 débiles) 
		levelOneRounds.add(new Round("Ronda 5 (El Capitán)", (em) -> {
			// 1. Un grupo de enemigos normales aleatorios
			em.spawnEnemies(5);

			// 2. Un "Jefe" más fuerte en el centro
			float bossSize = 150f; // Más grande
			float bossDrop = 0.5f; // 50% drop
			int bossHealth = 200; // Más vida
			int bossDamage = 25; // Más daño

			em.add(new Enemy(600, 700, bossSize, bossDrop, bossHealth, bossDamage));
		}));
		
		
		//cada nivel tiene nombre, skins, rondas, friccion, obstaculo daño, osbtaculo bloque
		//TODO ir ajuntando y probando combinaciones para la cantidad de obstaculos por nivel
		return new Level("Nivel 1: El Espacio", EBackgroundType.ONE, levelOneRounds, 0.9f, 3, 2);
	}

	// Crea el nivel 2 y lo retorna
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
		levelTwoRounds.add(new Round("Ronda 2: Lluvia Tóxica", (em) -> {
			em.spawnEnemies(10); // Usa el spawner aleatorio
		}));

		// Ronda 3: Enjambre (15 enemigos débiles)
		// muchos enemigos, pero débiles 
		levelTwoRounds.add(new Round("Ronda 3: Enjambre", (em) -> {
			float size = 60f; 
			float drop = 0.01f;
			int health = 25; 
			int damage = 10;

			// Creamos 15 en posiciones aleatorias
			for (int i = 0; i < 15; i++) {
				float x = (float) (Math.random() * 1000 + 100);
				float y = (float) (Math.random() * 200 + 600);
				em.add(new Enemy(x, y, size, drop, health, damage));
			}
		}));

		// Ronda 4: Flanqueo (6 enemigos)
		levelTwoRounds.add(new Round("Ronda 4: Flanqueo", (em) -> {
			// 1. Dos mutantes fuertes en el centro-arriba
			em.add(new Enemy(400, 750, 80f, 0.1f, 80, 15));
			em.add(new Enemy(800, 750, 80f, 0.1f, 80, 15));

			// 2. Cuatro enemigos normales aleatorios para distraer
			em.spawnEnemies(4);
		}));

		// Ronda 5: Jefe Mutante 
		levelTwoRounds.add(new Round("Ronda 5: Jefe Mutante", (em) -> {
			// El Jefe 
			em.add(new Enemy(500, 750, 200f, 1.0f, 300, 30)); // Jefe

			// Una "Lluvia Tóxica" 
			em.spawnEnemies(8);
		}));

		// Creamos el Nivel 2
		return new Level("Nivel 2: Planeta Tóxico", EBackgroundType.TWO, levelTwoRounds, 0.9f, 5, 3);
	}

	// creamos el nivel 3 (biomoid de awua)
	public static Level createLevelThree() {
		List<Round> levelThreeRounds = new ArrayList<>();

		// Ronda 1: La Corriente
		// Los enemigos cruzan la pantalla horizontalmente
		levelThreeRounds.add(new Round("Ronda 1: La Corriente", (em) -> {
			float size = 80f, drop = 0.1f;
			int health = 80, damage = 15;

			for (int i = 0; i < 5; i++) { 
				// Spawnean fuera de la pantalla (izquierda)
				Enemy e = new Enemy(-100, (float) (Math.random() * 600 + 100), size, drop, health, damage);

				e.setPolarVelocityLocal(100f, 0);
				em.add(e);
			}
		}));

		// Ronda 2: Burbujas
		// Los enemigos suben desde el fondo de la pantalla.
		levelThreeRounds.add(new Round("Ronda 2: Burbujas", (em) -> {
			float size = 80f, drop = 0.1f;
			int health = 80, damage = 15;

			for (int i = 0; i < 7; i++) { 
				// Spawnean en X aleatorias, en el fondo 
				float x = (float) (Math.random() * 1000 + 100);
				Enemy e = new Enemy(x, -100, size, drop, health, damage);
				
				e.setPolarVelocityLocal(90f, 90);
				em.add(e);
			}
		}));

		// Ronda 3: Banco (como abanico mu wonito)
		// Un grupo de enemigos débiles que se dispersan.
        levelThreeRounds.add(new Round("Ronda 3: Abanico", (em) -> {
            
            // Punto de Origen
            float spawnX = 1300; // Justo fuera de la pantalla derecha
            float spawnY = 400;  // A la mitad de la altura
            
            // Definición del Abanico
            int enemyCount = 12;      // 12 enemigos
            float baseSpeed = 150f;   // Una velocidad constante
            
            float fanWidth = 90f;     // El ancho total del abanico (90 grados)
            float startAngle = 135f;  // El ángulo del primer enemigo 
            
            // Calculamos el espacio angular entre cada enemigo
            float angleStep = fanWidth / (enemyCount - 1); 

            for (int i = 0; i < enemyCount; i++) {
                
                // Stats de enemigos débiles
                Enemy e = new Enemy(spawnX, spawnY, 60f, 0.01f, 25, 10);
                
                float finalAngle = startAngle + (i * angleStep);
                
                // Una leve variación de velocidad para que no sea perfecto
                float finalSpeed = baseSpeed + (float)(Math.random() * 20) - 10;

                e.setPolarVelocityLocal(finalSpeed, finalAngle);
                em.add(e);
            }
        }));

		// Ronda 4: Depredadores
		// Una mezcla de enemigos aleatorios 
		// y dos enemigos más fuertes que los flanquean.
		levelThreeRounds.add(new Round("Ronda 4: Depredadores", (em) -> {
			// 5 enemigos aleatorios 
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
			// Escolta de 10 enemigos aleatorios
			em.spawnEnemies(10);

			// El Jefe
			Enemy boss = new Enemy(600, 900, 250f, 1.0f, 500, 35);

			boss.setPolarVelocityLocal(40f, 270);
			em.add(boss);
		}));

		// Finalemnte creamos el nivel 3
		return new Level("Nivel 3: Fosa Abisal", EBackgroundType.THREE, levelThreeRounds, 0.85f, 5, 8);
	}

	// Creamos el nivel 4 con tematica de lava
	public static Level createLevelFour() {
		List<Round> levelFourRounds = new ArrayList<>();

		// Ronda 1: Lluvia de Ceniza
		// Los enemigos caen desde arriba y rebotan en el suelo.
		levelFourRounds.add(new Round("Ronda 1: Lluvia de Ceniza", (em) -> {
			float size = 90f, drop = 0.1f;
			int health = 90, damage = 18;

			for (int i = 0; i < 6; i++) {
				// Spawnean arriba, fuera de pantalla
				float x = (float) (Math.random() * 1000 + 100);
				Enemy e = new Enemy(x, 900, size, drop, health, damage);

				e.setPolarVelocityLocal(130f, 270);
				em.add(e);
			}
		}));

		// Ronda 2: Rocas Ígneas
		// Enemigos rebotando desde los lados.
		levelFourRounds.add(new Round("Ronda 2: Rocas Ígneas", (em) -> {
			float size = 90f, drop = 0.1f;
			int health = 90, damage = 18;

			for (int i = 0; i < 8; i++) {
				// Alternamos spawns (izquierda y derecha)
				float x = (i % 2 == 0) ? -100 : 1300; // Izquierda o Derecha
				float y = (float) (Math.random() * 600 + 100);
				float angle = (i % 2 == 0) ? 0 : 180; // Hacia la derecha o izquierda

				Enemy e = new Enemy(x, y, size, drop, health, damage);

				e.setPolarVelocityLocal(110f, angle);
				em.add(e);
			}
		}));

		// Ronda 3: Magma Inestable
		// Enemigos que spawnean en el centro y rebotan por todas partes.
		levelFourRounds.add(new Round("Ronda 3: Magma Inestable", (em) -> {
			float size = 70f, drop = 0.1f;
			int health = 50, damage = 20; // Más débiles pero más rápidos/dañinos

			float[] angles = { 45, 135, 225, 315, 90 }; // Direcciones diagonales

			for (int i = 0; i < 5; i++) {
				// Spawnean en el centro
				Enemy e = new Enemy(600, 400, size, drop, health, damage);

				e.setPolarVelocityLocal(200f, angles[i]);
				em.add(e);
			}
		}));

		// Ronda 4: Erupción
		// Una mezcla de enemigos aleatorios 
		levelFourRounds.add(new Round("Ronda 4: Erupción", (em) -> {
			em.spawnEnemies(12);
		}));

		// Ronda 5: El Coloso de Magma
		// Un jefe que rebota y enemigos que caen.
		levelFourRounds.add(new Round("Ronda 5: El Coloso", (em) -> {
			// Escolta de "Lluvia de Ceniza"
			for (int i = 0; i < 4; i++) {
				float x = (float) (Math.random() * 1000 + 100);
				Enemy e = new Enemy(x, 900, 90f, 0.1f, 90, 18);
				e.setPolarVelocityLocal(130f, 270);
				em.add(e);
			}

			// El Jefe
			Enemy boss = new Enemy(600, 800, 250f, 1.0f, 600, 40);
			// Rebotará por toda la pantalla
			boss.setPolarVelocityLocal(80f, 225);
			em.add(boss);
		}));

		// Creamos el Nivel 4
		return new Level("Nivel 4: Núcleo Ígneo", EBackgroundType.FOUR, levelFourRounds, 0.9f, 8, 4);
	}

	// nivel 5, tundra 
	public static Level createLevelFive() {
		List<Round> levelFiveRounds = new ArrayList<>();

		// Ronda 1: Viento Helado
		// Los enemigos entran barriendo la pantalla de lado a lado.
		levelFiveRounds.add(new Round("Ronda 1: Viento Helado", (em) -> {
			float size = 90f, drop = 0.15f;
			int health = 100, damage = 20; // Enemigos base más fuertes

			for (int i = 0; i < 6; i++) {
				// Spawnean fuera de la pantalla (derecha)
				float y = (float) (Math.random() * 600 + 100);
				Enemy e = new Enemy(1300, y, size, drop, health, damage);

				e.setPolarVelocityLocal(130f, 180);
				em.add(e);
			}
		}));

		// Ronda 2: Carámbanos
		// Los enemigos caen del techo y rebotan en el suelo.
		levelFiveRounds.add(new Round("Ronda 2: Carámbanos", (em) -> {
			float size = 90f, drop = 0.15f;
			int health = 100, damage = 20;

			for (int i = 0; i < 10; i++) {
				// Spawnean arriba, en X aleatorias
				float x = (float) (Math.random() * 1000 + 100);
				Enemy e = new Enemy(x, 900, size, drop, health, damage);

				e.setPolarVelocityLocal(150f, 270);
				em.add(e);
			}
		}));

		// Ronda 3: Patinaje Mortal
		// Enemigos muy rápidos que rebotan por todas partes, difíciles de predecir.
		levelFiveRounds.add(new Round("Ronda 3: Patinaje Mortal", (em) -> {
			float size = 70f, drop = 0.1f; // Más pequeños, más difíciles de golpear
			int health = 70, damage = 25; // Frágiles pero peligrosos

			// 3 desde la izquierda
			for (int i = 0; i < 3; i++) {
				Enemy e = new Enemy(-100, (float) (Math.random() * 600 + 100), size, drop, health, damage);
				e.setPolarVelocityLocal(250f, 15f); // Muy rápido, ángulo superficial
				em.add(e);
			}
			// 3 desde la derecha
			for (int i = 0; i < 3; i++) {
				Enemy e = new Enemy(1300, (float) (Math.random() * 600 + 100), size, drop, health, damage);
				e.setPolarVelocityLocal(250f, 165f); // Muy rápido, ángulo superficial
				em.add(e);
			}
		}));

		// Ronda 4: Ventisca
		// Una ronda de caos total, usando el spawner aleatorio.
		// Todos rebotarán y crearán una "ventisca" de enemigos.
		levelFiveRounds.add(new Round("Ronda 4: Ventisca", (em) -> {
			em.spawnEnemies(15);
		}));

		// Ronda 5: El Golem de Hielo
		// Un jefe lento y 4 guardias rápidos que lo protegen.
		levelFiveRounds.add(new Round("Ronda 5: El Golem de Hielo", (em) -> {
			// Los 4 Guardias (spawnean en las esquinas)
			float guardSize = 100f, guardDrop = 0.2f;
			int guardHealth = 120, guardDamage = 22;

			// Arriba Izq -> Abajo Der
			Enemy g1 = new Enemy(100, 700, guardSize, guardDrop, guardHealth, guardDamage);
			g1.setPolarVelocityLocal(160f, 315);
			em.add(g1);

			// Arriba Der -> Abajo Izq
			Enemy g2 = new Enemy(1100, 700, guardSize, guardDrop, guardHealth, guardDamage);
			g2.setPolarVelocityLocal(160f, 225);
			em.add(g2);

			// Abajo Izq -> Arriba Der
			Enemy g3 = new Enemy(100, 100, guardSize, guardDrop, guardHealth, guardDamage);
			g3.setPolarVelocityLocal(160f, 45);
			em.add(g3);

			// Abajo Der -> Arriba Izq
			Enemy g4 = new Enemy(1100, 100, guardSize, guardDrop, guardHealth, guardDamage);
			g4.setPolarVelocityLocal(160f, 135);
			em.add(g4);

			// 2. El Jefe (lento, pero tanque)
			Enemy boss = new Enemy(600, 900, 300f, 1.0f, 1000, 50);
			boss.setPolarVelocityLocal(30f, 270); // Muy lento hacia abajo
			em.add(boss);
		}));

		// Creamos el Nivel 5 // resbaladizo (0.99f es la friccion)
		return new Level("Nivel 5: Tundra Helada", EBackgroundType.FIVE, levelFiveRounds, 0.99f, 7, 7);
	}
}