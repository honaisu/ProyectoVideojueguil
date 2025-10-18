package managers;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;

import armas.proyectiles.Bullet;
import armas.proyectiles.Swing;
import hitboxes.BallHitbox;
import logica.MainGame;
import logica.assets.AssetManager;
import personajes.Jugador;

public class CollisionManager {
	private final Sound explosionSound;
    private final int scorePerAsteroid;

    /**
     * @param explosionSound sonido que se reproduce al destruir asteroide (puede ser null)
     * @param scorePerAsteroid puntos por asteroide destruido
     */
    public CollisionManager(Sound explosionSound, int scorePerAsteroid) {
        this.explosionSound = explosionSound;
        this.scorePerAsteroid = scorePerAsteroid;
    }
    
    public CollisionManager() {
        this.explosionSound = AssetManager.getInstancia().getExplosionSound();
        this.scorePerAsteroid = 10; // TODO los asteroides probablemente manejen sus propios puntajes
    }

    /**
     * Resuelve colisiones y devuelve la cantidad total de puntos ganados en esta invocación.
     */
    public int handleCollisions(Jugador nave, BulletManager bm, MeleeManager mm, AsteroidManager am) {
        int totalScore = 0;

        ArrayList<Bullet> bullets = bm.getBullets();
        ArrayList<BallHitbox> asteroids = am.getAsteroids();

        // 1) Balas vs Asteroides
        // Iteramos hacia atrás para permitir remociones seguras
        for (int bi = bullets.size() - 1; bi >= 0; bi--) {
            Bullet b = bullets.get(bi);

            boolean bulletHit = false;
            for (int ai = asteroids.size() - 1; ai >= 0; ai--) {
                BallHitbox a = asteroids.get(ai);
                // Usamos el método existente en Bullet: checkCollision(Ball2)
                if (b.checkCollision(a)) {
                    // Marca bala destruida internamente; también la removemos de la lista aquí
                    if (explosionSound != null) explosionSound.play(0.03f);
                    // Removemos el asteroide
                    am.removeAsteroid(ai);
                    // Aumentamos score
                    totalScore += scorePerAsteroid;
                    // Removemos la bala
                    bullets.remove(bi);
                    bulletHit = true;
                    break; // esta bala ya fue usada
                }
            }
            
            if (bulletHit) {
                // continue con la siguiente bala
            	// TODO?
            }
        }

        // 2) Nave vs Asteroides
        // Si nave está herida o destruida, la nave misma maneja su estado, aquí solo removemos asteroides que colisionen
        for (int ai = asteroids.size() - 1; ai >= 0; ai--) {
            BallHitbox a = asteroids.get(ai);
            if (nave.checkCollision(a)) {
                am.removeAsteroid(ai);
            }
        }
        
        // 3) Melee (Swings) vs Asteroides
        for (Swing s : mm.getSwings()) {
            for (int ai = asteroids.size() - 1; ai >= 0; ai--) {
                BallHitbox a = asteroids.get(ai);
                if (s.checkCollision(a)) {
                    am.removeAsteroid(ai);
                    if (explosionSound != null) explosionSound.play(0f);
                    totalScore += scorePerAsteroid;
                }
            }
        }

        return totalScore;
    }
}