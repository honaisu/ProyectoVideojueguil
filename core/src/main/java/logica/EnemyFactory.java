package logica;

import java.util.Random;
//import com.badlogic.gdx.Gdx;
import personajes.Enemy;

public class EnemyFactory {
  private static final Random RNG = new Random();

  // Tamaño fijo para todos los enemigos
  private static final float ENEMY_SIZE = 80f;

  // Márgenes de spawn (en función del tamaño) para no nacer fuera
  //private static float margin() { return ENEMY_SIZE; }

  // Velocidad inicial (magnitud)
  //private static final float MIN_SPEED = 60f;
  //private static final float MAX_SPEED = 140f;

  /** Crea un Enemy con tamaño fijo, posición aleatoria válida y velocidad inicial. */
  public static Enemy createRandomBasic() {
	  int w = com.badlogic.gdx.Gdx.graphics.getWidth();
	  int h = com.badlogic.gdx.Gdx.graphics.getHeight();
	  float m = ENEMY_SIZE; // margen = tamaño

	  float x = m + RNG.nextFloat() * (w - 2f * m);
	  float y = m + RNG.nextFloat() * (h - 2f * m);

	  float speed = 60f + RNG.nextFloat() * 80f;
	  float angle = RNG.nextFloat() * 360f;

	  Enemy e = new Enemy(x, y, ENEMY_SIZE);
	  e.setPolarVelocityLocal(speed, angle);
	  return e;
	}
  // Útil si quieres crear con parámetros concretos
  public static Enemy createBasic(float x, float y, float speed, float angleDeg) {
    Enemy e = new Enemy(x, y, ENEMY_SIZE);
    e.setPolarVelocityLocal(speed, angleDeg);
    return e;
  }
}
