package factories;

import com.badlogic.gdx.math.MathUtils;

import data.*;
import entidades.Entity;
import entidades.proyectiles.*;
import enumeradores.recursos.EDropType;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class ProjectileFactory {
	/**
	 * Crea un proyectil en base a su tipo de drop específico. Asocia un shooter o
	 * el manager correspondiente al proyectil que se necesite. <br>
	 * Quería que pudiera no ser un switch-case pero no se me ocurrió alternativa
	 * u.u
	 */
	public static void create(EDropType type, Entity shooter, ProjectileManager manager) {
		ProjectileData data = AssetManager.getInstancia().getData(type).projectileData;
		Projectile projectile = null;
		switch (type) {
		case FLAMESHOT:
			FlameData flame = (FlameData) data;
			projectile = new Flame(flame, shooter);
			break;
		case HEAVY_MACHINE_GUN:
		case RAYGUN:
			BulletData bullet = (BulletData) data;
			projectile = new Bullet(bullet, shooter);
			break;
		case LASER_GUN:
		case MELEE:
			SwingData swing = (SwingData) data;
			projectile = new Swing(swing, shooter);
			break;
		case ROCKET_LAUNCHER:
			RocketData rocket = (RocketData) data;
			projectile = new Rocket(rocket, shooter, manager);
			break;
		case SHOTGUN: {
			BulletData shoot = (BulletData) data;
			float spread = shoot.spread;

			for (int i = 0; i < shoot.pellets; i++) {
				float angle = MathUtils.random(-spread, spread);
				float speed = MathUtils.random(0f, 2f);

				Projectile newBullet = new Bullet(shoot, shooter, angle, speed);
				manager.add(newBullet);
			}
			return;
		}
		default:
			break;
		}
		manager.add(projectile);
	}
}
