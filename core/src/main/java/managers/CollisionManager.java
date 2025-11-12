package managers;

import java.util.Iterator;
import java.util.List;
import com.badlogic.gdx.audio.Sound;
import armas.proyectiles.Projectile;
import hitboxes.BallHitbox;
import personajes.Enemy;
import personajes.Player;

public class CollisionManager {
  private final Sound explosionSound;
  private final int scorePerAsteroid;

  public CollisionManager(Sound explosionSound, int scorePerAsteroid) {
    this.explosionSound = explosionSound;
    this.scorePerAsteroid = scorePerAsteroid;
  }

  public CollisionManager() {
    this.explosionSound = AssetManager.getInstancia().getExplosionSound();
    this.scorePerAsteroid = 10;
  }

  // NUEVO: daño al jugador cuando colisiona con enemigos
  public void handlePlayerEnemyCollision(Player player, List<Enemy> enemies) {
    // Usa checkCollision si tus Hitbox lo tienen implementado:
    for (int i = enemies.size() - 1; i >= 0; i--) {
      Enemy e = enemies.get(i);
      if (player.checkCollision(e)) {
        player.onHitByEnemy(e);
        break; // un golpe por frame
      }
    }
    // Alternativa con bounding rectangles:
    // Rectangle pr = player.getSpr().getBoundingRectangle();
    // for (Enemy e : enemies) {
    //   if (pr.overlaps(e.getBoundingRectangle())) { player.onHitByEnemy(e); break; }
    // }
  }

  // EXISTENTE: proyectiles vs enemigos (ajusta tipos si usas Enemy en la lista) //cambio segun geminis
  public int handleCollisions(Player player, List<Projectile> projectiles, List<? extends BallHitbox> enemies) {
    int totalScore = 0;
    Iterator<Projectile> projectileIterator = projectiles.iterator();
    while (projectileIterator.hasNext()) {
      Projectile projectile = projectileIterator.next();
   // Tiene que ser "? extends BallHitbox" para coincidir con la lista.
      Iterator<? extends BallHitbox> enemyIterator = enemies.iterator();
      while (enemyIterator.hasNext()) {
        BallHitbox asteroid = enemyIterator.next();
        if (!projectile.getHitbox().checkCollision(asteroid)) continue;
        if (explosionSound != null) explosionSound.play(0.1f);
        totalScore += scorePerAsteroid;
        enemyIterator.remove();
        projectileIterator.remove();
        break;
      }
    }
    return totalScore;
  }
}
