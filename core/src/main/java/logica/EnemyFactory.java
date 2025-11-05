package logica;

import java.util.Random;

import com.badlogic.gdx.Gdx;

import personajes.Enemy;

public class EnemyFactory {
	
	private static final Random r = new Random();
	
	/**
	 * Método encargado de crear un enemigo de manera aleatoria
	 * <p>
	 */
	public static Enemy createRandomBasic() {
		
		int x = r.nextInt((int)Gdx.graphics.getWidth());
		int y = 50+r.nextInt((int)Gdx.graphics.getHeight()-50);
		int size = r.nextInt(80) + 20;
		
		return createBasic(x, y, size);
	}
	
	public static Enemy createBasic(float x, float y, int size) {
		return new Enemy(x, y, size);//, 100, 20);
	}
}
