package managers;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.audio.Sound;

import armas.*;
import armas.proyectiles.Projectile;
import personajes.Enemy;
import personajes.Player;
import personajes.WeaponDrop;

public class CollisionManager {
	private final Sound explosionSound;
    private final int scorePerAsteroid;
    
    private Random r = new Random();

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
    /*
    public int handleCollisions(Player nave, AsteroidManager am) {
        int totalScore = 0;

        List<BallHitbox> asteroids = am.getAsteroids();

        // 1) Balas vs Asteroides
        // Iteramos hacia atrás para permitir remociones seguras
        for (int bi = bullets.size() - 1; bi >= 0; bi--) {
        	boolean bulletHit = false;
        	// TODO
        	Bullet b = bullets.get(bi);
            for (int ai = asteroids.size() - 1; ai >= 0; ai--) {
                BallHitbox a = asteroids.get(ai);
                // Usamos el método existente en Bullet: checkCollision(Ball2)
                if (b.getHitbox().checkCollision(a)) {
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
            	//nave.bounce(a);
                am.removeAsteroid(ai);
            }
        }
        
        // 3) Melee (Swings) vs Asteroides
        for (Swing s : mm.getSwings()) {
            for (int ai = asteroids.size() - 1; ai >= 0; ai--) {
                BallHitbox a = asteroids.get(ai);
                if (s.getHitbox().checkCollision(a)) {
                    am.removeAsteroid(ai);
                    if (explosionSound != null) explosionSound.play();
                    totalScore += scorePerAsteroid;
                }
            }
        }
        // 3) Melee (Swings) vs Asteroides
        for (LaserBeam l : lm.getLasers()) {
            for (int ai = asteroids.size() - 1; ai >= 0; ai--) {
                BallHitbox a = asteroids.get(ai);
                if (l.getHitbox().checkCollision(a)) {
                    am.removeAsteroid(ai);
                    if (explosionSound != null) explosionSound.play();
                    totalScore += scorePerAsteroid;
                }
            }
        }

        return totalScore;
    }*/
    
    public int handleCollisions(Player player, List<Projectile> projectiles, List<Enemy> enemies, DropManager dropManager) {
    	int totalScore = 0;
    	
        // Los iteradores se utilizan para poder... iterar...
        Iterator<Projectile> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            
            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                
                // Comprobamos la colisión
                if (!projectile.getHitbox().checkCollision(enemy)) continue;
                
                // LÓGICA DE DAÑO: El proyectil golpea al enemigo
                enemy.takeDamage(player.getWeapon().getDamage());
                if(!(player.getWeapon() instanceof Melee)) {
                	projectileIterator.remove();                	
                }

                if(enemy.isDead()) {
                	if (explosionSound != null) explosionSound.play(0.1f);
                	totalScore += scorePerAsteroid;
                	
                	if (Math.random() < enemy.getRareDropProbability()) {
                        // Decidimos qué arma soltar
                        // TODO: Crear una lógica más avanzada para elegir el arma
                        Weapon weaponToDrop = createRandomWeapon(); 
                        
                        // Creamos el drop en la posición del enemigo
                        WeaponDrop drop = new WeaponDrop(enemy.getX(), enemy.getY(), weaponToDrop);
                        
                        // Se lo pasamos al manager
                        dropManager.add(drop);
                    }
                	enemyIterator.remove();
                	
                }
                
                // Rompemos el bucle porque el proyectil ya fue destruido
                break; 
            }
        }
     // --- 2) Colisiones: Jugador vs Enemigos ---
        // (Usamos un iterador por si necesitamos eliminar enemigos al chocar)
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
        	Enemy enemy = enemyIterator.next();
        	
        	// Verificamos colisión Y si el jugador no está herido (invulnerable)
        	if (player.checkCollision(enemy) && !player.isHurt()) {
        		
        		// El jugador recibe daño
        		player.takeDamage((int)enemy.getDamage());
        		
        		// El enemigo se destruye al chocar con el jugador
        		if (explosionSound != null) explosionSound.play(0.1f);
        		enemyIterator.remove();
        	}
        }
        
        return totalScore;
    }
    
    //para los drops
    public void handlePlayerVsDrops(Player player, DropManager dropManager) {
        
        Iterator<WeaponDrop> dropIterator = dropManager.getDrops().iterator();
        
        while (dropIterator.hasNext()) {
            WeaponDrop drop = dropIterator.next();
            
            // Si el jugador colisiona con el drop
            if (player.checkCollision(drop)) {
                
            	Weapon pickedUpWeapon = drop.getWeapon();
                
                player.setWeapon(pickedUpWeapon);
                
                pickedUpWeapon.getPickupSound().play();
                
                dropIterator.remove();
                
            }
        }
    }
    
    private Weapon createRandomWeapon() {
        // Genera un número aleatorio basado en cuántas armas tienes
        int weaponType = r.nextInt(3); // 0, 1, o 2

        switch (weaponType) {
            case 0:
                return new Shotgun();
            case 1:
                return new HeavyMachineGun();
            case 2:
                // Asumiendo que tienes una clase RocketLauncher
                // return new RocketLauncher(); 
                return new Melee(); // Por ahora, si no la tienes
            default:
                return new Melee();
        }
    }

}