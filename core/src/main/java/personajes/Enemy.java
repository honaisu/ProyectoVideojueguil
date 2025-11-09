package personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.Hitbox;
import managers.AssetManager;

public class Enemy extends Hitbox {

	private float rareDrop;
	private int healthPoints;
	private float damage;
	
	// El sprite se asigna aquí, usando el AssetManager
	public Enemy(float x, float y, float rareDrop, int healthPoints, int damage) {
		super(x, y, new Sprite(AssetManager.getInstancia().getEnemyTexture())); // Asumo que esta es la textura base
		this.rareDrop = rareDrop;
		this.healthPoints = healthPoints;
		this.damage = damage;
		
		getSpr().setPosition(x, y); 
		
		// Es buena idea centrar el origen si vas a rotar o escalar
		getSpr().setOriginCenter();
	}
	
	/**
	 * Actualiza la lógica del enemigo (movimiento, IA, etc.)
	 * Por ahora lo dejamos vacío, pero debe existir para el Manager.
	 */
	public void update(float delta) {
		// TODO: Implementar movimiento o IA del enemigo aquí
		// Ejemplo: getSpr().setPosition(getSpr().getX() + xVel * delta, getSpr().getY() + yVel * delta);
	}
	
	/**
	 * Dibuja al enemigo. 
	 * Player tiene una lógica compleja de animación, pero Enemy puede ser simple.
	 */
	@Override
	public void draw(SpriteBatch batch) {
		getSpr().draw(batch);
	}
	
	/**
	 * Reduce la vida del enemigo al recibir daño.
	 */
	public void takeDamage(int amount) {
		this.healthPoints -= amount;
	}
	
	/**
	 * Verifica si el enemigo ha sido derrotado.
	 */
	public boolean isDead() {
		return this.healthPoints <= 0;
	}

	// --- Getters ---
	
	public float getDamage() {
		return damage;
	}
	
	public float getRareDropProbability() {
		return rareDrop;
	}
}
