package armas.strategy;

import com.badlogic.gdx.math.MathUtils;

import data.BulletData;
import data.ProjectileData;
import entidades.Entity;
import entidades.proyectiles.Bullet;
import interfaces.IAttackStrategy;
import managers.ProjectileManager;

public class BulletAttackStrategy implements IAttackStrategy {
	@Override
    public void executeAttack(ProjectileData data, Entity shooter, ProjectileManager manager) {
        BulletData bulletData = (BulletData) data; 
        
        int pellets = (bulletData.pellets > 0) ? bulletData.pellets : 1;
        float spread = bulletData.spread;

        for (int i = 0; i < pellets; i++) {
            float angle = MathUtils.random(-spread, spread);
            float speedVariation = MathUtils.random(0.1f, 2f);
            
            Bullet newBullet = new Bullet(bulletData, shooter, angle, speedVariation);
            manager.add(newBullet);
        }
    }
}
