package logica;

import java.util.Random;

import com.badlogic.gdx.Gdx;

import personajes.Enemy;

public class EnemyFactory {
	
	private static final Random r = new Random();
	
	/**
	 * Método encargado de crear un enemigo de manera aleatoria
	 */
	public static Enemy createRandomBasic() {
		
		// Posición aleatoria
		int x = r.nextInt((int)Gdx.graphics.getWidth());
		int y = 50 + r.nextInt((int)Gdx.graphics.getHeight() - 50);
		
		// Atributos aleatorios
		// Probabilidad de drop entre 0% y 15%
		float rareDrop = r.nextFloat() * 0.15f; 
		// Vida entre 50 y 100
		int healthPoints = 50 + r.nextInt(51); 
		// Daño entre 10 y 20
		int damage = 10 + r.nextInt(11); 
		
		return createBasic(x, y, 1f, 50, damage);
	}
	
	/**
	 * Método base para crear un enemigo con atributos específicos.
	 * CORREGIDO: Ahora usa los parámetros correctos del constructor de Enemy.
	 */
	public static Enemy createBasic(float x, float y, float rareDrop, int healthPoints, int damage) {
		return new Enemy(x, y, rareDrop, healthPoints, damage);
	}
}
