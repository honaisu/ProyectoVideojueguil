package factories;

import java.util.Random;
import com.badlogic.gdx.Gdx;

import entidades.Enemy;
import enumeradores.recursos.texturas.EEnemyType;


//TODO evitar que enemigos aparescan ensima de los bloques
public class EnemyFactory {
	private static final Random RNG = new Random();

	// Valores Fijos
	private static float ENEMY_SIZE = 1f;
	private static float RARE_DROP = RNG.nextFloat() * 0.95f;
	private static int ENEMY_HP = 100;
	private static int ENEMY_DAMAGE = 10 + RNG.nextInt(11);
	
	private static EEnemyType type = EEnemyType.GENERIC;
	
	// Velocidad inicial
	private static final float MIN_SPEED = 60f, MAX_SPEED = 140f;
	
	public static Enemy createRandomBasic() {
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();;
		float x = RNG.nextInt(w);
		float y = 50 + RNG.nextInt(h - 50);
				
		//logica de la velocidad
		float speed = MIN_SPEED + RNG.nextFloat() * (MAX_SPEED - MIN_SPEED);
		float angle = RNG.nextFloat() * 360f;
		
		// Llamamos al nuevo "createBasic" ahora fusionado :D//
		//return createBasic(x, y, rareDrop, healthPoints, damage, speed, angle);
		return createBasicAlpha(x, y, type, ENEMY_SIZE, RARE_DROP, ENEMY_HP, ENEMY_DAMAGE, speed, angle);
	}
	
	/**
	 * CREATE_BASIC FUSIONADO:
	 * Llama al nuevo constructor de Enemy y le asigna la velocidad.
	 */
	public static Enemy createBasicAlpha(float x, float y, EEnemyType type, float size, float rareDrop, int healthPoints, int damage, float speed, float angleDeg) {
	    Enemy e = new Enemy(x, y, type, size, rareDrop, healthPoints, damage);
	    
	    e.setVelocity(speed, angleDeg);
	    return e;
	}
	
	// Enemigo Estatico
	public static Enemy createBasicStatic(float x, float y) {
		return createBasicAlpha(x, y, type, ENEMY_SIZE, RARE_DROP, ENEMY_HP, ENEMY_DAMAGE, 0, 0);
		
	}
	//Enemigo estatico diferentes stats
	public static Enemy createBasicStatsStatic(float x, float y, float size, float rareDrop, int hp, int dmg) {
        return createBasicAlpha(x, y, type, size, rareDrop, hp, dmg, 0, 0);
    }
	
	//velocidad y angulo
	public static Enemy createBasicCourse(float x, float y, float vel, float angleDeg) {
		return createBasicAlpha(x, y, type, ENEMY_SIZE, RARE_DROP, ENEMY_HP, ENEMY_DAMAGE, vel, angleDeg);
	}
	
	//velocidad y angulo diferentes stats
	public static Enemy createBasicStatsCourse(float x, float y, float size, float rareDrop, int hp, int dmg, float vel, float angleDeg) {
		return createBasicAlpha(x, y, type, size, rareDrop, hp, dmg, vel, angleDeg);
	}
	
	
	public static void setEnemyType(EEnemyType type) {
		EnemyFactory.type = type;
	}

	public static void setENEMY_SIZE(float enemySize) {
		ENEMY_SIZE = enemySize;
	}

	public static void setRARE_DROP(float rareDrop) {
		RARE_DROP = rareDrop;
	}

	public static void setENEMY_HP(int enemyHP) {
		ENEMY_HP = enemyHP;
	}

	public static void setENEMY_DAMAGE(int enemyDamage) {
		ENEMY_DAMAGE = enemyDamage;
	}
	
	public static Random getRng() {
		return RNG;
	}
	
}
