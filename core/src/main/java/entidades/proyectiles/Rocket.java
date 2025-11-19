package entidades.proyectiles;

import com.badlogic.gdx.math.Vector2;

import data.BulletData;
import data.RocketData;
import entidades.Enemy;
import entidades.Entity;
import managers.ProjectileManager;

public class Rocket extends Projectile {
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
        super(Projectile.calcularMuzzle(new Vector2(), shooter, data.piercing), data);
        
        this.manager = manager;
        this.explosionData = data.explosionData;
        this.acceleration = data.acceleration;
        this.maxSpeed = data.maxSpeed;
        
        this.rotation = shooter.getRotation() + 90;
        
        sprite.setPosition(position.x, position.y);
        sprite.setOriginCenter();
        sprite.setRotation(shooter.getRotation());
        
        setVelocity(data.velocity, this.rotation);
    }

	@Override
	public void update(float delta) {
		if (isDestroyed()) return;
		
		float currentSpeed = velocity.len();
		
        // aceleracion
        if (currentSpeed < maxSpeed) {
            currentSpeed += acceleration * delta;
            // Cambia la magnitud del vector (no su dirección) :D
            velocity.setLength(Math.min(currentSpeed, maxSpeed));
        }
        
        Vector2 movement = velocity.cpy().scl(delta);
        
        sprite.translate(movement.x, movement.y);
        position.add(movement);
        
        if (!Entity.isInBounds(this)) {
        	sprite.setPosition(position.x, position.y);
        	destroy();
        }
	}
	
	
	@Override
	public boolean onHitEnemy(Enemy enemy) {
		// Crea la explosión
		Projectile explosion = this.spawnExplosion();
		manager.add(explosion);
		
		return true; 
	}
	
	private Projectile spawnExplosion() { 
    	Bullet projectile = new Bullet(this.explosionData, position.cpy());        	
        return projectile;
    }
}
