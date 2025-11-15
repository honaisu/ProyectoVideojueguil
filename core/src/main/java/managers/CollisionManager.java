package managers;

import java.util.Iterator;
import java.util.List;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

import armas.*;
import entidades.proyectiles.Projectile;
import entidades.Enemy;
import entidades.Player;
import entidades.WeaponDrop;
import enumeradores.recursos.EGameSound;
import managers.assets.AssetManager;

public class CollisionManager {

  private final Sound explosionSound;
  private final int scorePerEnemy;

  private Random r = new Random();

  public CollisionManager(Sound explosionSound, int scorePerEnemy) {
    this.explosionSound = explosionSound;
    this.scorePerEnemy = scorePerEnemy;
  }

  public CollisionManager() {
    this.explosionSound = AssetManager.getInstancia().getSound(EGameSound.EXPLOSION);
    this.scorePerEnemy = 10;
  }

  /**
   * ESTE ES EL MÉTODO DE COLISIÓN PRINCIPAL (de origin/dia)
   * Es el que debemos llamar desde GameLogicHandler.
   * Maneja Proyectil vs Enemigo Y Jugador vs Enemigo.
   */   
    public int handleCollisions(Player player, List<Projectile> projectiles, List<Enemy> enemies, DropManager dropManager) {
    	int totalScore = 0;
    	
        // Los iteradores se utilizan para poder... iterar...
        Iterator<Projectile> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            
            Iterator<Enemy> enemyIterator = enemies.iterator();
            
	        while (enemyIterator.hasNext()) {
	            Enemy enemy = enemyIterator.next();
	            
	            if (!projectile.checkCollision(enemy)) continue;
	            
	            // Lógica de daño
	            enemy.takeDamage(player.getWeapon().getDamage());
	            
	            // Los Melee no se destruyen al golpear
	            if(!(player.getWeapon() instanceof Melee)) {
	                projectileIterator.remove();                	
	            }
	
	            if(enemy.isDead()) {
	                if (explosionSound != null) explosionSound.play(0.1f);
	                totalScore += scorePerEnemy;
	                
	                // Lógica de Drop
	                if (Math.random() < enemy.getRareDropProbability()) {
	                    Weapon weaponToDrop = createRandomWeapon(); 
	                    WeaponDrop drop = new WeaponDrop(enemy.getX(), enemy.getY(), weaponToDrop);
	                    dropManager.add(drop); // Añade al DropManager
	                }
	                enemyIterator.remove();
	            }
	            break; 
	        }
        }
    
	    // --- 2) Colisiones: Jugador vs Enemigos ---
	    Iterator<Enemy> enemyIterator = enemies.iterator();
	    while (enemyIterator.hasNext()) {
	        Enemy enemy = enemyIterator.next();
	        
	        // Usamos el 'isHurt()' fusionado que incluye iFrames
	        if (player.checkCollision(enemy) && !player.isHurt()) {
	            
	            // El jugador recibe daño del enemigo
	            player.takeDamage((int)enemy.getDamage());
	            
	            // El enemigo también se destruye (opcional)
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
        int weaponType = r.nextInt(4); // 0, 1, 2, 3

        switch (weaponType) {
            case 0:
            	return new HeavyMachineGun();
            case 1:
            	return new Shotgun();
            case 2:
                return new Melee(); // Por ahora, si no la tienes
            case 3:
            	return new LaserCannon();
            default:
                return new Melee();
        }
    }

}

