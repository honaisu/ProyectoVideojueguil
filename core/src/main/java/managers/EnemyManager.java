package managers;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import logica.EnemyFactory;
import personajes.Enemy;

public class EnemyManager {
  private final List<Enemy> enemies = new ArrayList<>();
  private static final int DEFAULT_ENEMIES = 12;
  private static final float MIN_SEPARATION = 72f;

  public EnemyManager() { spawnEnemies(DEFAULT_ENEMIES); }
  public EnemyManager(int cantidad) { spawnEnemies(cantidad); }

  private void spawnEnemies(int cant) {
    final int triesPerEnemy = 24;
    for (int i = 0; i < cant; i++) {
      Enemy cand = null;
      boolean placed = false;
      for (int t = 0; t < triesPerEnemy && !placed; t++) {
        cand = EnemyFactory.createRandomBasic();
        if (isFar(cand.getX(), cand.getY(), MIN_SEPARATION)) {
          enemies.add(cand);
          placed = true;
        }
      }
      if (!placed && cand != null) enemies.add(cand);
    }
  }

  private boolean isFar(float x, float y, float min) {
    float m2 = min * min;
    for (Enemy e : enemies) {
      float dx = e.getX() - x, dy = e.getY() - y;
      if (dx * dx + dy * dy < m2) return false;
    }
    return true;
  }

  public void update(float delta) { for (Enemy e : enemies) e.update(); }
  public void render(SpriteBatch batch) { for (Enemy e : enemies) e.draw(batch); }

  public List<Enemy> getEnemies() { return enemies; }
  public int size() { return enemies.size(); }
  public boolean isEmpty() { return enemies.isEmpty(); }
}
