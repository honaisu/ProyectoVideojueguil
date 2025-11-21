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
			em.add(EnemyFactory.createBasicStatic(500, 700));
			em.add(EnemyFactory.createBasicStatic(450, 650));
			em.add(EnemyFactory.createBasicStatic(550, 650));
		}));

		// Ronda 4: Emboscada (4 enemigos) ---
		levelOneRounds.add(new Round("Ronda 4 (Emboscada)", (em) -> {

			// Lado Izquierdo
			em.add(EnemyFactory.createBasicStatic(100, 300));
			em.add(EnemyFactory.createBasicStatic(100, 500));

			// Lado Derecho 
			em.add(EnemyFactory.createBasicStatic(1100, 300));
			em.add(EnemyFactory.createBasicStatic(1100, 500));
		}));

		// Ronda 5: Mini-Jefe (1 Fuerte + 5 débiles) 
		levelOneRounds.add(new Round("Ronda 5 (El Capitán)", (em) -> {
			// 1. Un grupo de enemigos normales aleatorios
			em.spawnEnemies(5);

			// 2. Un "Jefe" más fuerte en el centro
			float bossSize = 2f; // Más grande
			int bossHealth = 400; // Más vida
			int bossDamage = 25; // Más daño
			
			em.add(EnemyFactory.createBasicStatsStatic(600, 600, bossSize, 0f, bossHealth, bossDamage));
		}));
		
		
		//cada nivel tiene nombre, skins, rondas, friccion, obstaculo daño, osbtaculo bloque
		//TODO ir ajuntando y probando combinaciones para la cantidad de obstaculos por nivel
		return new Level("Nivel 1: El Espacio", EBackgroundType.ONE, levelOneRounds, 0.9f, 3, 2);
	}

	// Crea el nivel 2 y lo retorna
	public static Level createLevelTwo() {
		
		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(150);
		EnemyFactory.setRARE_DROP(0.7f);
		EnemyFactory.setENEMY_DAMAGE(15 + EnemyFactory.getRng().nextInt(11)); //TODO ver rng
		EnemyFactory.setEnemyType(EEnemyType.POINTED);
		
		List<Round> levelTwoRounds = new ArrayList<>();

		// Ronda 1: Patrulla Mutante
		// Empezamos con enemigos más fuertes que los del Nivel 1
		levelTwoRounds.add(new Round("Ronda 1: Patrulla", (em) -> {
			
			em.add(EnemyFactory.createBasicStatic(200, 700));
			em.add(EnemyFactory.createBasicStatic(600, 750));
			em.add(EnemyFactory.createBasicStatic(1000, 700));
			
		}));

		// Ronda 2: Lluvia Tóxica (10 enemigos)
		levelTwoRounds.add(new Round("Ronda 2: Lluvia Tóxica", (em) -> {
			em.spawnEnemies(10); // Usa el spawner aleatorio
		}));

		// Ronda 3: Enjambre (15 enemigos débiles)
		// muchos enemigos, pero débiles 
		levelTwoRounds.add(new Round("Ronda 3: Enjambre", (em) -> {
			EnemyFactory.setENEMY_SIZE(0.7f);
			EnemyFactory.setRARE_DROP(0.2f);
			EnemyFactory.setENEMY_HP(30);
			EnemyFactory.setENEMY_DAMAGE(10);
			
			// Creamos 15 en posiciones aleatorias
			for (int i = 0; i < 15; i++) {;
				em.add(EnemyFactory.createRandomBasic());
			}
		}));
		
		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(150);
		EnemyFactory.setRARE_DROP(0.7f);
		EnemyFactory.setENEMY_DAMAGE(15 + EnemyFactory.getRng().nextInt(11)); //TODO ver rng
		// Ronda 4: Flanqueo (6 enemigos)
		levelTwoRounds.add(new Round("Ronda 4: Flanqueo", (em) -> {
			// 1. Dos mutantes fuertes en el centro-arriba
			em.add(EnemyFactory.createBasicStatic(400, 750));
			em.add(EnemyFactory.createBasicStatic(800, 750));

			// 2. Cuatro enemigos normales aleatorios para distraer
			em.spawnEnemies(4);
		}));

		// Ronda 5: Jefe Mutante 
		levelTwoRounds.add(new Round("Ronda 5: Jefe Mutante", (em) -> {
			// El Jefe
			em.add(EnemyFactory.createBasicStatsStatic(500, 750, 2f, 0f,500, 30));

			// Una "Lluvia Tóxica" 
			em.spawnEnemies(8);
		}));

		// Creamos el Nivel 2
		return new Level("Nivel 2: Planeta Tóxico", EBackgroundType.TWO, levelTwoRounds, 0.9f, 5, 3);
	}

	// creamos el nivel 3 (biomoid de awua)
	public static Level createLevelThree() {
		
		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(150);
		EnemyFactory.setRARE_DROP(0.7f);
		EnemyFactory.setENEMY_DAMAGE(15 + EnemyFactory.getRng().nextInt(11)); //TODO ver rng
		EnemyFactory.setEnemyType(EEnemyType.WATER);
		
		List<Round> levelThreeRounds = new ArrayList<>();

		// Ronda 1: La Corriente
		// Los enemigos cruzan la pantalla horizontalmente
		levelThreeRounds.add(new Round("Ronda 1: La Corriente", (em) -> {

			for (int i = 0; i < 5; i++) { 
				// Spawnean fuera de la pantalla (izquierda)
				em.add(EnemyFactory.createBasicCourse(-100, (float) (Math.random() * 600 + 100), 100f, 0));
			}
		}));

		// Ronda 2: Burbujas
		// Los enemigos suben desde el fondo de la pantalla.
		levelThreeRounds.add(new Round("Ronda 2: Burbujas", (em) -> {

			for (int i = 0; i < 7; i++) { 
				// Spawnean en X aleatorias, en el fondo 
				em.add(EnemyFactory.createBasicCourse((float) (Math.random() * 1000 + 100), -100, 90f, 90));
			}
		}));

		// Ronda 3: Banco (como abanico mu wonito)
		// Un grupo de enemigos débiles que se dispersan.
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

                //e.setVelocity(finalSpeed, finalAngle);
                //em.add(e);
            }
        }));

		// Ronda 4: Depredadores
		// Una mezcla de enemigos aleatorios 
		// y dos enemigos más fuertes que los flanquean.
		levelThreeRounds.add(new Round("Ronda 4: Depredadores", (em) -> {
			// 5 enemigos aleatorios 
			em.spawnEnemies(5);

			// 2 "Depredadores" más fuertes con movimiento fijo
			em.add(EnemyFactory.createBasicStatsCourse(100, 700, 1.3f, 0.7f, 200, 30, 150f, 300)); //TODO VER SPRITE SI CAMBIA
			
			em.add(EnemyFactory.createBasicStatsCourse(1100, 700, 1.3f, 0.7f, 200, 30, 150f, 240));
			
		}));

		// Ronda 5: El Leviatán
		// Un jefe grande y lento con muchos enemigos aleatorios.
		levelThreeRounds.add(new Round("Ronda 5: El Leviatán", (em) -> {
			// Escolta de 10 enemigos aleatorios
			em.spawnEnemies(10);

			// El Jefe
			em.add(EnemyFactory.createBasicStatsCourse(600, 900, 1.6f, 0.7f, 700, 35, 40f, 270));
		}));

		// Finalemnte creamos el nivel 3
		return new Level("Nivel 3: Fosa Abisal", EBackgroundType.THREE, levelThreeRounds, 0.85f, 5, 3);
	}
	
	// Creamos el nivel 4 con tematica de lava
	public static Level createLevelFour() {
		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(150);
		EnemyFactory.setRARE_DROP(0.7f);
		EnemyFactory.setENEMY_DAMAGE(20 + EnemyFactory.getRng().nextInt(11)); //TODO ver rng
		EnemyFactory.setEnemyType(EEnemyType.WATER); //TODO sprite nuevo soon
		
		List<Round> levelFourRounds = new ArrayList<>();

		// Ronda 1: Lluvia de Ceniza
		// Los enemigos caen desde arriba y rebotan en el suelo.
		levelFourRounds.add(new Round("Ronda 1: Lluvia de Ceniza", (em) -> {
			float size = 90f, drop = 0.1f;
			int health = 90, damage = 18;

			for (int i = 0; i < 6; i++) {
				// Spawnean arriba, fuera de pantalla
				float x = (float) (Math.random() * 1000 + 100);
				
				em.add(EnemyFactory.createBasicCourse(x, 900, 130f, 270));
				
			}
		}));

		// Ronda 2: Rocas Ígneas
		// Enemigos rebotando desde los lados.
		levelFourRounds.add(new Round("Ronda 2: Rocas Ígneas", (em) -> {
			float size = 90f, drop = 0.1f;
			int health = 90, damage = 18; //TODO VER DAÑOS

			for (int i = 0; i < 8; i++) {
				// Alternamos spawns (izquierda y derecha)
				float x = (i % 2 == 0) ? -100 : 1300; // Izquierda o Derecha
				float y = (float) (Math.random() * 600 + 100);
				float angle = (i % 2 == 0) ? 0 : 180; // Hacia la derecha o izquierda
				
				em.add(EnemyFactory.createBasicCourse(x, y, 110f, angle));

			}
		}));

		// Ronda 3: Magma Inestable
		// Enemigos que spawnean en el centro y rebotan por todas partes.
		levelFourRounds.add(new Round("Ronda 3: Magma Inestable", (em) -> {
			EnemyFactory.setENEMY_SIZE(0.3f);
			EnemyFactory.setENEMY_HP(50);
			
			float size = 70f, drop = 0.1f; //TODO VER ESTO
			int health = 50, damage = 20;

			float[] angles = { 45, 135, 225, 315, 90 };

			for (int i = 0; i < 5; i++) {
				em.add(EnemyFactory.createBasicCourse(600, 400, 200f, angles[i]));

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
				em.add(EnemyFactory.createBasicStatsCourse(x, 900, 0.9f, 0.9f, 200, 10, 130f, 270));
			}

			// El Jefe
			em.add(EnemyFactory.createBasicStatsCourse(600, 800, 3f, 0f, 600, 30, 80f, 225));
		}));

		// Creamos el Nivel 4
		return new Level("Nivel 4: Núcleo Ígneo", EBackgroundType.FOUR, levelFourRounds, 0.9f, 8, 4);
	}

	// nivel 5, tundra 
	public static Level createLevelFive() {
		EnemyFactory.setENEMY_SIZE(1f);
		EnemyFactory.setENEMY_HP(100);
		EnemyFactory.setRARE_DROP(0.7f);
		EnemyFactory.setENEMY_DAMAGE(20 + EnemyFactory.getRng().nextInt(11)); //TODO ver rng
		EnemyFactory.setEnemyType(EEnemyType.PENGUIN);
		
		List<Round> levelFiveRounds = new ArrayList<>();

		// Ronda 1: Viento Helado
		// Los enemigos entran barriendo la pantalla de lado a lado.
		levelFiveRounds.add(new Round("Ronda 1: Viento Helado", (em) -> {
			float size = 90f, drop = 0.15f;
			int health = 100, damage = 20; // Enemigos base más fuertes

			for (int i = 0; i < 6; i++) {
				// Spawnean fuera de la pantalla (derecha)
				float y = (float) (Math.random() * 600 + 100);
				em.add(EnemyFactory.createBasicCourse(1300, y, 130f, 180));
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
				em.add(EnemyFactory.createBasicCourse(x, 900, 150f, 270));
			}
		}));

		// Ronda 3: Patinaje Mortal
		// Enemigos muy rápidos que rebotan por todas partes, difíciles de predecir.
		levelFiveRounds.add(new Round("Ronda 3: Patinaje Mortal", (em) -> {
			float size = 70f, drop = 0.1f; // Más pequeños, más difíciles de golpear
			int health = 70, damage = 25; // Frágiles pero peligrosos//TODO

			// 3 desde la izquierda
			for (int i = 0; i < 3; i++) {
				em.add(EnemyFactory.createBasicCourse(-100, (float) (Math.random() * 600 + 100), 250f, 15f));
			}
			// 3 desde la derecha
			for (int i = 0; i < 3; i++) {
				em.add(EnemyFactory.createBasicCourse(1300, (float) (Math.random() * 600 + 100), 250f, 165f));
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
			em.add(EnemyFactory.createBasicCourse(100, 700, 160f, 315));

			// Arriba Der -> Abajo Izq
			em.add(EnemyFactory.createBasicCourse(1100, 700, 160f, 225));

			// Abajo Izq -> Arriba Der
			em.add(EnemyFactory.createBasicCourse(100, 100, 160f, 45));

			// Abajo Der -> Arriba Izq
			em.add(EnemyFactory.createBasicCourse(1100, 100, 160f, 135));

			// 2. El Jefe (lento, pero tanque)
			em.add(EnemyFactory.createBasicStatsCourse(600, 900, 3.5f, 1f, 1000, 50, 30f, 270));
		}));

		// Creamos el Nivel 5 // resbaladizo (0.99f es la friccion)
		return new Level("Nivel 5: Tundra Helada", EBackgroundType.FIVE, levelFiveRounds, 0.99f, 7, 7);
	}
}