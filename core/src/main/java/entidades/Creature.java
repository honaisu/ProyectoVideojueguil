package entidades;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import interfaces.IRenderizable;
import enumeradores.recursos.EHealthBarType;
import factories.SpriteFactory;
import interfaces.ITexture;

/**
 * Clase que define a una criatura dentro de nuestro juego.
 * <p>
 * Las criaturas tienen la posibilidad de tener una vida asociada.
 */
public abstract class Creature extends Entity implements IRenderizable {
	// La vida de la entidad :)
	private final int totalHp;
	private int hp;
	private HealthBar healthBar;

	public Creature(Vector2 position, ITexture asset, int hp, boolean isPlayer) {
		super(position, asset);
		this.totalHp = hp;
		this.hp = hp;
		
		TextureRegion[] healthFrames = SpriteFactory.createHPBarFrames(EHealthBarType.NORMAL);
        this.healthBar = new HealthBar(this, healthFrames, isPlayer);
		
	}
	
	/**
	 * Reduce la vida del enemigo al recibir daño.
	 */
	public void takeDamage(int amount) {
		this.hp -= amount;
		// Para que no se sobrepase nuestro límite
		if (hp < 0)
			hp = 0;
	}
	
	public void addHp(int amount) {
		this.hp += amount;
	}

	/**
	 * Verifica si el enemigo ha sido derrotado.
	 */
	public boolean isDead() {
		return this.hp <= 0;
	}
	
	public int getTotalHp() {
		return totalHp;
	}
	
	public int getHp() {
		return hp;
	}
	
	public HealthBar getHealthBar() {
		return healthBar;
	}

}
