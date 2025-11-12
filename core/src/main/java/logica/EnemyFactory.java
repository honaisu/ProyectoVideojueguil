package logica;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import personajes.Enemy;

public class EnemyFactory {
	
  private static final Random RNG = new Random();
  
  //constantes para los enemigos
  // Tamaño fijo
  private static final float ENEMY_SIZE = 100f;
  private static float margin() { return ENEMY_SIZE; }
  // Velocidad inicial
  private static final float MIN_SPEED = 60f, MAX_SPEED = 140f;

  public static Enemy createRandomBasic() {
	  
	int w = Gdx.graphics.getWidth();
    int h = Gdx.graphics.getHeight();;
    float x = RNG.nextInt(w);
    float y = 50 + RNG.nextInt(h - 50);
    
    float rareDrop = RNG.nextFloat() * 0.15f; 
    int healthPoints = 50 + RNG.nextInt(51); 
    int damage = 10 + RNG.nextInt(11);
    
    
    //logica de la velocidad
    float speed = MIN_SPEED + RNG.nextFloat() * (MAX_SPEED - MIN_SPEED);
    float angle = RNG.nextFloat() * 360f;

   
    // Llamamos al nuevo "createBasic" ahora fusionado :D//
    //return createBasic(x, y, rareDrop, healthPoints, damage, speed, angle);
    return createBasic(x, y, ENEMY_SIZE, 1f, healthPoints, damage, speed, angle);
  }

  /**
   * CREATE_BASIC FUSIONADO:
   * Llama al nuevo constructor de Enemy y le asigna la velocidad.
   */
  public static Enemy createBasic(float x, float y, float size, float rareDrop, int healthPoints, int damage, float speed, float angleDeg) {
      // Llama al nuevo constructor fusionado de Enemy.java
      Enemy e = new Enemy(x, y, size, rareDrop, healthPoints, damage);
      
      // Aplica tu lógica de velocidad
      e.setPolarVelocityLocal(speed, angleDeg);
      return e;
  }


}
