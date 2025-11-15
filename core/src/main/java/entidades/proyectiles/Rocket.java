package entidades.proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entidades.Enemy;
import entidades.Player;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;
import managers.ProjectileManager;

public class Rocket extends Projectile {
	
	private float radians;
    private float currentSpeed;
    private float maxSpeed;
    private float acceleration;
    
    private ProjectileManager manager; // Para crear la explosión
    private Player player;

	public Rocket(float x, float y, float width, float rotation, float speed, Player p, Sprite spr, ProjectileManager manager, boolean piercing) {
		super(x, y, spr, p, p.getWeapon().getDamage(), piercing);
		
		this.player = p;
        this.manager = manager;
		
        getSpr().setBounds(x, y, width, width);
        getSpr().setOriginCenter();
        getSpr().setRotation(rotation - 90);

        // --- LÓGICA DE ACELERACIÓN ---
        this.radians = (float) Math.toRadians(rotation);
        this.currentSpeed = speed;
        this.maxSpeed = 300f; // Ej: Acelerará hasta 5 veces su velocidad inicial
        this.acceleration = 10f;
	}

	@Override
	public void update(float delta, Player player) {
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
        float xSpeed = (float) Math.cos(radians) * currentSpeed;
        float ySpeed = (float) Math.sin(radians) * currentSpeed;
        
        // --- 2. MOVER EL MISIL ---
		Sprite spr = getSpr();
        // Usamos delta para el movimiento
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
		
		// --- 3. COMPROBAR LÍMITES DE PANTALLA ---
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            
            spawnExplosion(); // Explota al chocar con el borde
            destroy(); // Se destruye
        }
	}
	
	
	@Override
	public boolean onHitEnemy(Enemy enemy) {
		enemy.takeDamage(getDamage()); // Hace daño al enemigo que golpeó
        spawnExplosion(); // Crea la explosión
		destroy(); // Marca este misil para ser destruido
		return true; // Retorna true para que el manager lo elimine
	}
	
	private void spawnExplosion() {
        if (manager == null || player == null) return; // Seguridad

        float explosionX = getSpr().getX() + getSpr().getWidth() / 2;
        float explosionY = getSpr().getY() + getSpr().getHeight() / 2;
        
        // --- PARÁMETROS DE LA EXPLOSIÓN ---
        float explosionSize = 350f; // ¡Un "Bullet" grande!
        float explosionSpeed = 0f; // Velocidad 0, como pediste
        boolean isPiercing = true; // ¡Muy importante! Para que golpee a todos los enemigos dentro
        float explosionLifespan = 0.8f; // Durará 0.2 segundos

        // Creamos el Bullet-Explosión usando el nuevo constructor
        Bullet explosion = new Bullet(
            explosionX - (explosionSize / 2), // Centramos la X
            explosionY - (explosionSize / 2), // Centramos la Y
            explosionSize, 
            0, // Rotación no importa
            explosionSpeed,
            player, // Le pasamos el player
            SpriteFactory.create(EProjectileType.FLAME),
            isPiercing,
            explosionLifespan);
        
        // Le cambiamos el daño (opcional, pero genial)
        // Por ejemplo, la explosión hace la mitad de daño que el impacto directo
        // explosion.setDamage(getDamage() / 2); // (Necesitarías un método setDamage en Projectile)

        manager.add(explosion); // Añadimos la explosión al juego
    }

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

}
