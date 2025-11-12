package logica;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import personajes.Enemy;

public class EnemyFactory {
  private static final Random RNG = new Random();

  // Tamaño fijo
  private static final float ENEMY_SIZE = 100f;
  private static float margin() { return ENEMY_SIZE; }

  // Velocidad inicial
  private static final float MIN_SPEED = 60f, MAX_SPEED = 140f;

  public static Enemy createRandomBasic() {
    int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
    float m = margin();

    float x = m + RNG.nextFloat() * (w - 2f * m);
    float y = m + RNG.nextFloat() * (h - 2f * m);

    float speed = MIN_SPEED + RNG.nextFloat() * (MAX_SPEED - MIN_SPEED);
    float angle = RNG.nextFloat() * 360f;

    Enemy e = new Enemy(x, y, ENEMY_SIZE);
    e.setPolarVelocityLocal(speed, angle);
    return e;
  }

  public static Enemy createBasic(float x, float y, float speed, float angleDeg) {
    Enemy e = new Enemy(x, y, ENEMY_SIZE);
    e.setPolarVelocityLocal(speed, angleDeg);
    return e;
  }
}
