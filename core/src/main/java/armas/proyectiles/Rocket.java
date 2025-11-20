package armas.proyectiles;

import com.badlogic.gdx.math.Vector2;

import data.BulletData;
import data.RocketData;
import entidades.Enemy;
import entidades.Entity;
import interfaces.IRenderizable;
import managers.ProjectileManager;

public class Rocket extends Projectile implements IRenderizable  {
	private float acceleration;
    private float maxSpeed;
    
    // Para crear la explosión
    private ProjectileManager manager; 
    private BulletData explosionData;
    
	/*
	public Rocket(Entity e, int damage, float width, float speed, ProjectileManager manager, boolean piercing) {
		super(Projectile.calcularMuzzle(new Vector2(), e, false), EProjectileType.ROCKET, damage, piercing);
		
        this.manager = manager;
        // hace que mire para "adelante"
		this.rotation = e.getRotation() + 90;
		
        sprite.setBounds(position.x, position.y, width, width);
        sprite.setOriginCenter();
        sprite.setRotation(e.getRotation());
        
        setVelocity(speed, this.rotation);
	}*/
	
	public Rocket(RocketData data, Entity shooter, ProjectileManager manager) {
        super(Projectile.calcularMuzzle(new Vector2(), shooter, data.isPiercing()), data);
        
        this.manager = manager;
        this.explosionData = data.getExplosionData();
        this.acceleration = data.getAcceleration();
        this.maxSpeed = data.getMaxSpeed();
        
        setRotation(shooter.getRotation() + 90);
        
        getSprite().setPosition(getPosition().x, getPosition().y);
        getSprite().setRotation(shooter.getRotation());
        getSprite().setOriginCenter();
        
        setVelocity(data.getVelocity(), getRotation());
    }

	@Override
	public void update(float delta) {
		if (isDestroyed()) return;
		
		float currentSpeed = getVelocity().len();
		
        // aceleracion
        if (currentSpeed < maxSpeed) {
            currentSpeed += acceleration * delta;
            // Cambia la magnitud del vector (no su dirección) :D
            getVelocity().setLength(Math.min(currentSpeed, maxSpeed));
        }
        
        Vector2 movement = getVelocity().cpy().scl(delta);
        
        getSprite().translate(movement.x, movement.y);
        getPosition().add(movement);
        
        if (!Entity.isInPlayableBounds(this, HUD_HEIGHT)) {
        	getSprite().setPosition(getPosition().x, getPosition().y);
        	destroy();
        }
	}
	
	@Override
	public boolean onHitEnemy(Enemy enemy) {
		enemy.takeDamage(this.damage);
		// Crea la explosión
		Projectile explosion = this.spawnExplosion();
		manager.add(explosion);
		
		super.onHitEnemy(enemy);
		this.destroy();
		
		return true; 
	}
	
	private Projectile spawnExplosion() { 
    	Bullet projectile = new Bullet(this.explosionData, getPosition().cpy());    	
        return projectile;
    }
}
