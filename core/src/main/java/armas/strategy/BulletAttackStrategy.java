package armas.strategy;

import com.badlogic.gdx.math.MathUtils;

import armas.proyectiles.Bullet;
import data.BulletData;
import data.ProjectileData;
import entidades.Entity;
import interfaces.IAttackStrategy;
import managers.ProjectileManager;

public class BulletAttackStrategy implements IAttackStrategy {
	@Override
    public void executeAttack(ProjectileData data, Entity shooter, ProjectileManager manager) {
        BulletData bulletData = (BulletData) data; 
        
        int pellets = (bulletData.getPellets() > 0) ? bulletData.getPellets() : 1;
        float spread = bulletData.getSpread();
        float speed;

        for (int i = 0; i < pellets; i++) {
            float angle = MathUtils.random(-spread, spread);
            if (pellets == 1) {
            	speed = bulletData.getVelocity();
            }else {
            	speed = MathUtils.random(0.1f, 2f);
            	
            }
            
            Bullet newBullet = new Bullet(bulletData, shooter, angle, speed);
            manager.add(newBullet);
        }
    }
}
