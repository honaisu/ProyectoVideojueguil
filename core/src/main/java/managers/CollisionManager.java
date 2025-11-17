package managers;

import java.util.Iterator;
import java.util.List;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

import armas.*;

import entidades.Enemy;
import entidades.Player;
import entidades.WeaponDrop;
import entidades.proyectiles.Projectile;
import enumeradores.recursos.EGameSound;
import managers.assets.AssetManager;

//para el tema de los obstaculos
import managers.ObstacleManager;
import entidades.obstaculos.DamageHazard;
import entidades.obstaculos.SolidObstacle;

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

	public int handleCollisions(Player player, List<Projectile> projectiles, List<Enemy> enemies,
			DropManager dropManager, ObstacleManager obstacleManager) {
		
		int totalScore = handleProjectileVsEnemy(projectiles, enemies, dropManager);
		handlePlayerVsEnemy(player, enemies);
		handlePlayerVsDrops(player, dropManager); 
		// Lógica de Obstáculos 
		handlePlayerVsHazards(player, obstacleManager);
		handleSolidCollisions(player, enemies, obstacleManager);

		return totalScore;
	}

	public int handleProjectileVsEnemy(List<Projectile> projectiles, List<Enemy> enemies, DropManager dropManager) {
		int totalScore = 0;
		Iterator<Projectile> projectileIterator = projectiles.iterator();

		while (projectileIterator.hasNext()) {
			Projectile projectile = projectileIterator.next();
			Iterator<Enemy> enemyIterator = enemies.iterator();

			while (enemyIterator.hasNext()) {
				Enemy enemy = enemyIterator.next();

				if (!projectile.checkCollision(enemy))
					continue;

				boolean destroyProjectile = projectile.onHitEnemy(enemy);

				if (enemy.isDead()) {
					totalScore += handleEnemyDeath(enemy, enemyIterator, dropManager);
				}

				if (destroyProjectile) {
					projectileIterator.remove();
					break; // Proyectil destruido, pasa al siguiente
				}
			}
		}
		return totalScore;
	}

	public void handlePlayerVsEnemy(Player player, List<Enemy> enemies) {
		Iterator<Enemy> enemyIterator = enemies.iterator();

		while (enemyIterator.hasNext()) {
			Enemy enemy = enemyIterator.next();

			if (player.checkCollision(enemy) && !player.isHurt()) {
				player.takeDamage((int) enemy.getDamage());

				if (explosionSound != null)
					explosionSound.play(0.1f);
				enemyIterator.remove(); // Enemigo se destruye al chocar
			}
		}
	}

	public void handlePlayerVsDrops(Player player, DropManager dropManager) {
		Iterator<WeaponDrop> dropIterator = dropManager.getDrops().iterator();

		while (dropIterator.hasNext()) {
			WeaponDrop drop = dropIterator.next();

			if (player.checkCollision(drop)) {
				Weapon pickedUpWeapon = drop.getWeapon();
				player.setWeapon(pickedUpWeapon);
				pickedUpWeapon.getPickupSound().play();
				dropIterator.remove();
			}
		}
	}

	private int handleEnemyDeath(Enemy enemy, Iterator<Enemy> iterator, DropManager dropManager) {
        if (explosionSound != null) explosionSound.play(0.1f);
        
        // Lógica de Drop
        if (Math.random() < enemy.getRareDropProbability()) {
            Weapon weaponToDrop = createRandomWeapon(); 
			// Se usa getPosition() de benjoid, que es cmo la logica actual
            WeaponDrop drop = new WeaponDrop(enemy.getPosition().x, enemy.getPosition().y, weaponToDrop);
            dropManager.add(drop); 
        }
        
        iterator.remove(); // Eliminar enemigo de la lista
        return scorePerEnemy;
    }


	// Colision de cuando el juagador choca con un obsttaculo de daño
	public void handlePlayerVsHazards(Player player, ObstacleManager obstacleManager) {
		List<DamageHazard> hazards = obstacleManager.getHazards();

		for (DamageHazard h : hazards) {
			if (player.checkCollision(h)) {
				// Le avisamos al jugador que tocó un peligro
				// (El jugador decidirá si es Pua o Charco)
				player.onHazardCollision(h);
			}
		}
	}

	// Colision de entidad con el bloque
	public void handleSolidCollisions(Player player, List<Enemy> enemies, ObstacleManager obstacleManager) {
		List<SolidObstacle> solids = obstacleManager.getSolids();

		// Jugador vs Sólidos
		for (SolidObstacle s : solids) {
			if (player.checkCollision(s)) {
				// Le decimos al jugador que rebote
				player.bounce();
			}
		}

		// Enemigos vs Sólidos
		for (Enemy e : enemies) {
			for (SolidObstacle s : solids) {
				if (e.checkCollision(s)) {
					// Le decimos al enemigo que rebote
					e.bounce();
				}
			}
		}
	}
	
	
	
	
	
	private Weapon createRandomWeapon() {
        int weaponType = r.nextInt(6); //deberia mser (7) ?


        switch (weaponType) {
            case 0:
            	return new HeavyMachineGun();
            case 1:
            	return new Shotgun();
            case 2:
                return new Melee();
            case 3:
            	return new RocketLauncher();
            case 4:
            	return new FlameShot();
            case 5:
            	return new RayGun();
            case 6:
            	return new LaserCannon();
            default:
                return new Melee();
        }
    }


}
