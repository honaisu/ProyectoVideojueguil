package entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import enumeradores.recursos.EEnemyType;

public class Enemy extends Creature {
	private float rareDrop;
	private float damage;
	

	public Enemy(float x, float y, float size, float rareDrop, int hp, int damage) {
		super(new Vector2(x, y), EEnemyType.WATER, hp, false);
		this.rareDrop = rareDrop;
		this.damage = damage;

		sprite.setPosition(x, y);
		sprite.setOriginCenter();
		
	}

	@Override
	public void update(float delta) {
	    Vector2 displacement = velocity.cpy().scl(delta);
	    Vector2 newPos = getPosition().cpy().add(displacement);
	    
	    position.set(newPos);
	    Entity.isInBounds(this);
	    sprite.setPosition(newPos.x, newPos.y);
	    
	    getHealthBar().update();
	}

	/**
	 * Dibuja al enemigo. Player tiene una lógica compleja de animación, pero Enemy
	 * puede ser simple.
	 */
	@Override
	public void draw(SpriteBatch batch) {
		getSprite().draw(batch);
		getHealthBar().draw(batch);
	}

	public float getDamage() {
		return damage;
	}

	public float getRareDropProbability() {
		return rareDrop;
	}
}
