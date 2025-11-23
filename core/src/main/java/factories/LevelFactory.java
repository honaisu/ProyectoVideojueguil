package factories;

import java.util.ArrayList;
import java.util.List;

import enumeradores.recursos.texturas.EBackgroundType;
import enumeradores.recursos.texturas.EEnemyType;
// Imports de tus otras clases
import logica.Round;
import logica.levels.Level;


public class LevelFactory {

	// Creamos nivel 1 y lo retornamos
	public static Level createLevelOne() {
		EnemyFactory.setEnemyType(EEnemyType.GENERIC);
		
		List<Round> levelOneRounds = new ArrayList<>();

		// Ronda 1
		levelOneRounds.add(new Round("Ronda 1", (em) -> {
			em.spawnEnemies(3);
		}));

		// Ronda 2
		levelOneRounds.add(new Round("Ronda 2", (em) -> {
			em.spawnEnemies(5);
		}));

		// Ronda 3
		levelOneRounds.add(new Round("Ronda 3 (Formación)", (em) -> {
			em.add(EnemyFactory.createBasicCourse(500, 700, 40f, 270));
			em.add(EnemyFactory.createBasicCourse(450, 650, 40f, 270));
			em.add(EnemyFactory.createBasicCourse(550, 650, 40f, 270));
			
			em.add(EnemyFactory.createBasicCourse(600, 600, 40f, 270));
			em.add(EnemyFactory.createBasicCourse(400, 600, 40f, 270));
			em.add(EnemyFactory.createBasicCourse(500, 600, 40f, 270));
		}));

		// Ronda 4: Emboscada (4 enemigos)
		levelOneRounds.add(new Round("Ronda 4 (Emboscada)", (em) -> {

			// Lado Izquierdo
			em.add(EnemyFactory.createBasicCourse(100, 300, 40f, 0));
			em.add(EnemyFactory.createBasicCourse(100, 500, 40f, 0));

			// Lado Derecho 
			em.add(EnemyFactory.createBasicCourse(1100, 300, 40f, 180));
			em.add(EnemyFactory.createBasicCourse(1100, 500, 40f, 180));
		}));

		levelOneRounds.add(new Round("Ronda 5 (El Capitán)", (em) -> {
			// Minions
			em.spawnEnemies(5);
			// Boss
			em.add(EnemyFactory.createBasicStatsCourse(500, 600, 2f, 1f, 400, 25, 120f, 30));
		}));
		
		return new Level("Nivel 1: Ruinas del Olvido", EBackgroundType.ONE, levelOneRounds, 0.9f, 3, 2);
	}

	// Crea el nivel 2 y lo retorna
	public static Level createLevelTwo() {
		
		//stats del enemigo estandar
		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(100);
		EnemyFactory.setRARE_DROP(0.75f);
		EnemyFactory.setENEMY_DAMAGE(10);
		EnemyFactory.setEnemyType(EEnemyType.POINTED);
		
		List<Round> levelTwoRounds = new ArrayList<>();

		// Ronda 1: Patrulla Mutante
		levelTwoRounds.add(new Round("Ronda 1: Patrulla", (em) -> {
			
			em.add(EnemyFactory.createBasicCourse(200, 600, 40f, 270));
			em.add(EnemyFactory.createBasicCourse(600, 700, 40f, 270));
			em.add(EnemyFactory.createBasicCourse(1000, 600, 40f, 270));
			
		}));

		// Ronda 2: Lluvia Tóxica (10 enemigos)
		levelTwoRounds.add(new Round("Ronda 2: Lluvia Tóxica", (em) -> {
			em.spawnEnemies(10); // Usa el spawner aleatorio
		}));

		// Ronda 3: Enjambre (15 enemigos débiles)
		levelTwoRounds.add(new Round("Ronda 3: Enjambre", (em) -> {
			EnemyFactory.setENEMY_SIZE(0.7f);
			EnemyFactory.setRARE_DROP(0.2f);
			EnemyFactory.setENEMY_HP(20);
			EnemyFactory.setENEMY_DAMAGE(7);
			
			// Creamos 15 en posiciones aleatorias
			for (int i = 0; i < 15; i++) {;
				em.add(EnemyFactory.createRandomBasic());
			}
		}));

		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(100);
		EnemyFactory.setRARE_DROP(0.75f);
		EnemyFactory.setENEMY_DAMAGE(10);
		// Ronda 4: Flanqueo (6 enemigos)
		levelTwoRounds.add(new Round("Ronda 4: Flanqueo", (em) -> {
			// 1. Dos mutantes fuertes en el centro-arriba
			em.add(EnemyFactory.createBasicStatsStatic(300, 400, 1.2f, 0.8f, 150, 15));
			em.add(EnemyFactory.createBasicStatsStatic(900, 400, 1.2f, 0.8f, 150, 15));

			// 2. Cuatro enemigos normales aleatorios para distraer
			em.spawnEnemies(4);
		}));

		// Ronda 5: Jefe Mutante 
		levelTwoRounds.add(new Round("Ronda 5: Jefe Mutante", (em) -> {
			// El Jefe
			em.add(EnemyFactory.createBasicStatsCourse(500, 750, 2f, 1f,500, 30, 120f, 30));

			// Una "Lluvia Tóxica" 
			em.spawnEnemies(8);
		}));

		// Creamos el Nivel 2
		return new Level("Nivel 2: Planeta Tóxico", EBackgroundType.TWO, levelTwoRounds, 0.9f, 5, 2);
	}

	// creamos el nivel 3 (biomoid de awua)
	public static Level createLevelThree() {
		
		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(120);
		EnemyFactory.setRARE_DROP(0.7f);
		EnemyFactory.setENEMY_DAMAGE(20);
		EnemyFactory.setEnemyType(EEnemyType.WATER);
		
		List<Round> levelThreeRounds = new ArrayList<>();

		// Ronda 1: La Corriente
		levelThreeRounds.add(new Round("Ronda 1: La Corriente", (em) -> {

			float yPos = 200f;
			for (int i = 0; i < 5; i++) { 
				// Spawnean fuera de la pantalla (izquierda)
				em.add(EnemyFactory.createBasicCourse(-100, yPos, 100f, 0));
				yPos += 100;
			}
		}));

		// Ronda 2: Burbujas
		levelThreeRounds.add(new Round("Ronda 2: Burbujas", (em) -> {
			
			float xPos = 100f;
			for (int i = 0; i < 7; i++) { 
				em.add(EnemyFactory.createBasicCourse(xPos, -100, 90f, 90));
				xPos += 150;
			}
		}));

		// Ronda 3: Abanico 
        levelThreeRounds.add(new Round("Ronda 3: Abanico", (em) -> {
            
            // Definición del Abanico
            int enemyCount = 12;      // 12 enemigos
            float baseSpeed = 150f;   // Una velocidad constante
            
            float fanWidth = 90f;     // El ancho total del abanico (90 grados)
            float startAngle = 135f;  // El ángulo del primer enemigo 
            
            // Calculamos el espacio angular entre cada enemigo
            float angleStep = fanWidth / (enemyCount - 1); 

            for (int i = 0; i < enemyCount; i++) {
                
                // Stats de enemigos débiles
                float finalAngle = startAngle + (i * angleStep);
                float finalSpeed = baseSpeed + (float)(Math.random() * 20) - 10;
                
                em.add(EnemyFactory.createBasicCourse(1300, 400, finalSpeed, finalAngle));

            }
        }));

		// Ronda 4: Depredadores
		levelThreeRounds.add(new Round("Ronda 4: Depredadores", (em) -> {
			// 5 enemigos aleatorios 
			em.spawnEnemies(5);
			// 2 "Depredadores" más fuertes con movimiento fijo
			em.add(EnemyFactory.createBasicAlpha(100, 700, EEnemyType.GENERIC, 1.7f, 0.7f, 240, 30, 150f, 300));
			
			em.add(EnemyFactory.createBasicAlpha(1100, 700, EEnemyType.GENERIC, 1.7f, 0.7f, 240, 30, 150f, 240));
			
		}));

		// Ronda 5: El Leviatán
		// Un jefe grande y lento con muchos enemigos aleatorios.
		levelThreeRounds.add(new Round("Ronda 5: El Leviatán", (em) -> {
			// Escolta de 10 enemigos aleatorios
			em.spawnEnemies(10);

			// El Jefe
			em.add(EnemyFactory.createBasicStatsCourse(600, 900, 4.0f, 0.7f, 700, 35, 25f, 225));
		}));

		// Creamos el nivel 3
		return new Level("Nivel 3: Fosa Abisal", EBackgroundType.THREE, levelThreeRounds, 0.85f, 5, 2);
	}
	
	// Creamos el nivel 4 con tematica de lava
	public static Level createLevelFour() {
		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(150);
		EnemyFactory.setRARE_DROP(0.7f);
		EnemyFactory.setENEMY_DAMAGE(20);
		EnemyFactory.setEnemyType(EEnemyType.GENERIC);
		
		List<Round> levelFourRounds = new ArrayList<>();
		// Ronda 1: Lluvia de Ceniza
		levelFourRounds.add(new Round("Ronda 1: Lluvia de Ceniza", (em) -> {
			for (int i = 0; i < 7; i++) {
				float x = (float) (Math.random() * 1000 + 100);
				
				em.add(EnemyFactory.createBasicStatsCourse(x, 900, 0.9f, 0.7f, 90, 18, 130f, 270));
				
			}
		}));

		// Ronda 2: Rocas Ígneas
		// Enemigos rebotando desde los lados.
		levelFourRounds.add(new Round("Ronda 2: Rocas Ígneas", (em) -> {
			float size = 90f, drop = 0.1f;
			int health = 90, damage = 18; //TODO VER DAÑOS

			for (int i = 0; i < 8; i++) {
				float x = (i % 2 == 0) ? -100 : 1300; // Izquierda o Derecha
				float y = (float) (Math.random() * 600 + 100);
				float angle = (i % 2 == 0) ? 0 : 180; // Hacia la derecha o izquierda
				
				em.add(EnemyFactory.createBasicStatsCourse(x, y, 0.9f, 0.7f, 90, 18, 110f, angle));

			}
		}));

		// Ronda 3: Magma Inestable
		// Enemigos que spawnean en el centro y rebotan por todas partes.
		levelFourRounds.add(new Round("Ronda 3: Magma Inestable", (em) -> {
			
			float size = 70f, drop = 0.1f; //TODO VER ESTO
			int health = 50, damage = 20;

			float[] angles = { 45, 135, 225, 315, 90 };

			for (int i = 0; i < 5; i++) {
				em.add(EnemyFactory.createBasicStatsCourse(600, 400, 0.9f, 0.8f, 90, 15, 200f, angles[i]));
			}
		}));
		// Ronda 4: Erupción
		EnemyFactory.setENEMY_SIZE(0.3f);
		EnemyFactory.setENEMY_HP(20);
		EnemyFactory.setENEMY_DAMAGE(5);
		EnemyFactory.setRARE_DROP(0.3f);
		EnemyFactory.setMIN_SPEED(100f);
		EnemyFactory.setMAX_SPEED(200f);

		levelFourRounds.add(new Round("Ronda 4: Erupción", (em) -> {
			em.spawnEnemies(50);
		}));

		// Ronda 5: El Coloso de Magma
		levelFourRounds.add(new Round("Ronda 5: El Coloso", (em) -> {
			// Escolta de "Lluvia de Ceniza"
			for (int i = 0; i < 4; i++) {
				float x = (float) (Math.random() * 1000 + 100);
				em.add(EnemyFactory.createBasicStatsCourse(x, 900, 0.9f, 0.9f, 200, 10, 130f, 270));
			}

			// El Jefe
			em.add(EnemyFactory.createBasicStatsCourse(600, 800, 3f, 1f, 700, 30, 80f, 225));
		}));

		// Creamos el Nivel 4
		return new Level("Nivel 4: Núcleo Ígneo", EBackgroundType.FOUR, levelFourRounds, 0.9f, 8, 2);
	}

	// nivel 5, tundra 
	public static Level createLevelFive() {
		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(150);
		EnemyFactory.setRARE_DROP(0.8f);
		EnemyFactory.setENEMY_DAMAGE(20);
		EnemyFactory.setMIN_SPEED(60f);
		EnemyFactory.setMAX_SPEED(140f);
		EnemyFactory.setEnemyType(EEnemyType.PENGUIN);
		
		List<Round> levelFiveRounds = new ArrayList<>();

		// Ronda 1: Viento Helado
		// Los enemigos entran barriendo la pantalla de lado a lado.
		levelFiveRounds.add(new Round("Ronda 1: Viento Helado", (em) -> {
			for (int i = 0; i < 6; i++) {
				// Spawnean fuera de la pantalla (derecha)
				float y = (float) (Math.random() * 600 + 100);
				em.add(EnemyFactory.createBasicCourse(1300, y, 130f, 180));
			}
		}));

		// Ronda 2: Carámbanos
		levelFiveRounds.add(new Round("Ronda 2: Carámbanos", (em) -> {
			for (int i = 0; i < 10; i++) {
				float x = (float) (Math.random() * 1000 + 100);
				em.add(EnemyFactory.createBasicCourse(x, 900, 150f, 270));
			}
		}));

		// Ronda 3: Patinaje Mortal
		levelFiveRounds.add(new Round("Ronda 3: Patinaje Mortal", (em) -> {
			for (int i = 0; i < 3; i++) {
				em.add(EnemyFactory.createBasicStatsCourse(-100, (float) (Math.random() * 600 + 100), 0.7f, 0.8f, 100, 15, 250f, 15f));
			}
			for (int i = 0; i < 3; i++) {
				em.add(EnemyFactory.createBasicStatsCourse(1300, (float) (Math.random() * 600 + 100), 0.7f, 0.8f, 100, 15, 250f, 165f));
			}
		}));

		// Ronda 4: Ventisca
		levelFiveRounds.add(new Round("Ronda 4: Ventisca", (em) -> {
			em.spawnEnemies(15);
		}));

		// Ronda 5: El Golem de Hielo
		// Un jefe lento y 4 guardias rápidos que lo protegen.
		levelFiveRounds.add(new Round("Ronda 5: El Golem de Hielo", (em) -> {
			// Arriba Izq -> Abajo Der
			em.add(EnemyFactory.createBasicCourse(100, 700, 160f, 315));

			// Arriba Der -> Abajo Izq
			em.add(EnemyFactory.createBasicCourse(1100, 700, 160f, 225));

			// Abajo Izq -> Arriba Der
			em.add(EnemyFactory.createBasicCourse(100, 100, 160f, 45));

			// Abajo Der -> Arriba Izq
			em.add(EnemyFactory.createBasicCourse(1100, 100, 160f, 135));

			// 2. El Jefe
			em.add(EnemyFactory.createBasicStatsCourse(600, 900, 3.5f, 1f, 1500, 50, 30f, 270));
		}));

		// Creamos el Nivel 5 // resbaladizo (0.99f es la friccion)
		return new Level("Nivel 5: Tundra Helada", EBackgroundType.FIVE, levelFiveRounds, 0.975f, 7, 2);
	}
}