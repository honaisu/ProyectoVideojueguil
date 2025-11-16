package entidades.proyectiles;

import com.badlogic.gdx.math.Vector2;

import entidades.Enemy;
import entidades.Entity;
import enumeradores.recursos.EProjectileType;
import managers.ProjectileManager;

public class Rocket extends Projectile {
	
	private float radians;
    private float currentSpeed;
    private float maxSpeed;
    private float acceleration;
    
    private ProjectileManager manager; // Para crear la explosión

	public Rocket(Entity e, int damage, float width, float speed, ProjectileManager manager, boolean piercing) {
		super(Projectile.calcularMuzzle(new Vector2(), e, false), EProjectileType.ROCKET, damage, piercing);
		
        this.manager = manager;
		this.rotation = e.getRotation() + 90;
		
        sprite.setBounds(position.x, position.y, width, width);
        sprite.setOriginCenter();
        sprite.setRotation(e.getRotation());
        
        // --- LÓGICA DE ACELERACIÓN ---
        this.radians = (float) Math.toRadians(rotation);
        velocity.set(position.cpy().nor());
        velocity.scl(speed);
        velocity.setAngleRad(radians);
        
        this.currentSpeed = speed;
        this.maxSpeed = 300f;
        this.acceleration = 8f;
	}

	@Override
	public void update(float delta) {
		if (isDestroyed()) {
			return;
		}
		
        // --- 1. ACTUALIZAR VELOCIDAD (ACELERACIÓN) ---
        if (currentSpeed < maxSpeed) {
            currentSpeed += acceleration * delta;
            if (currentSpeed > maxSpeed) {
                currentSpeed = maxSpeed;
            }
        }
        
        // Calcular velocidad X/Y basada en la velocidad actual
        //velocity.scl(currentSpeed);
        velocity.add(position.cpy().nor().scl(currentSpeed));
        
        velocity.setAngleRad(radians);
        
        // Usamos delta para el movimiento
        sprite.setPosition(sprite.getX() + velocity.x, sprite.getY() + velocity.y);
		
        if (!Entity.isInBounds(this))
			destroy();
	}
	
	
	@Override
	public boolean onHitEnemy(Enemy enemy) {
		enemy.takeDamage(getDamage()); // Hace daño al enemigo que golpeó
        spawnExplosion(); // Crea la explosión
		destroy(); // Marca este misil para ser destruido
		return true; // Retorna true para que el manager lo elimine
	}
	
	private void spawnExplosion() {
        if (manager == null) return; // Seguridad //TODO

        float explosionX = sprite.getX() + sprite.getWidth() / 2;
        float explosionY = sprite.getY() + sprite.getHeight() / 2;
        
        // --- PARÁMETROS DE LA EXPLOSIÓN ---
        int explosionSize = 350; 
        float explosionSpeed = 0f; 
        boolean isPiercing = true;
        float explosionLifespan = 0.8f;

        
        Bullet explosion = new Bullet(this, EProjectileType.FLAME, 100, explosionSpeed, explosionSize, isPiercing,
        		explosionLifespan);
        explosion.setPosition(new Vector2(explosionX,explosionY));

        manager.add(explosion); 
    }


}
