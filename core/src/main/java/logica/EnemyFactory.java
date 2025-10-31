package logica;

import java.util.Random;

import com.badlogic.gdx.Gdx;

import personajes.Enemy;

public class EnemyFactory {
	/**
	 * Método encargado de crear un enemigo de manera aleatoria
	 * <p>
	 */
	public static Enemy createRandomBasic() {
		Random r = new Random();
		
		int x = r.nextInt(Gdx.graphics.getWidth());
		int y = r.nextInt(Gdx.graphics.getHeight());
		int size = r.nextInt(100);
		
		return createBasic(x, y, size);
	}
	
	public static Enemy createBasic(int x, int y, int size) {
		return new Enemy(x, y, size);
	}
}
