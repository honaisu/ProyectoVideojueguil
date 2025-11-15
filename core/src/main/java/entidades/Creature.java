package entidades;

import com.badlogic.gdx.math.Vector2;

import interfaces.ITexture;

/**
 * Clase que define a una criatura dentro de nuestro juego.
 * <p>
 * Las criaturas tienen la posibilidad de tener una vida asociada.
 */
public abstract class Creature extends Entity {
	// La vida de la entidad :)
	public final int totalHp;
	protected int hp;

	public Creature(Vector2 position, ITexture asset, int hp) {
		super(position, asset);
		this.totalHp = hp;
		this.hp = hp;
	}

	public int getHp() {
		return hp;
	}

	/**
	 * Reduce la vida del enemigo al recibir daño.
	 */
	public void takeDamage(int amount) {
		this.hp -= amount;
	}

	/**
	 * Verifica si el enemigo ha sido derrotado.
	 */
	public boolean isDead() {
		return this.hp <= 0;
	}
}
