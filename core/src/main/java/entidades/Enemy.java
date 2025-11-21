package entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import enumeradores.recursos.EEnemyType;

public class Enemy extends Creature {
	private float rareDrop;
	private float damage;	

	public Enemy(float x, float y, EEnemyType type,float size, float rareDrop, int hp, int damage) {
		super(new Vector2(x, y), type, hp, false);
		this.rareDrop = rareDrop;
		this.damage = damage;

		getSprite().setBounds(x,y,getSprite().getWidth()*size,getSprite().getHeight()*size);
		getSprite().setOriginCenter();
		
	}

	@Override
	public void update(float delta) {
	    Vector2 displacement = getVelocity().cpy().scl(delta);
	    Vector2 newPos = getPosition().cpy().add(displacement);
	    
	    setPosition(newPos);
	    
	    final float HUD_HEIGHT = 100f;
		Entity.isInPlayableBounds(this, HUD_HEIGHT);
		
	    getSprite().setPosition(newPos.x, newPos.y);
	    
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
	
	
	/**
     * Rebota contra un obstáculo sólido.
     */
	public void bounce() { //es literal lo mismo que el player
        // Invierte la velocidad 
        getVelocity().scl(-1); 

        // "Empuja" a la entidad 1 píxel para "despegarla" (evitar bug tambien)
        Vector2 pushVector = getVelocity().cpy().setLength(1.0f);
        getPosition().add(pushVector);
    }

	public float getDamage() {
		return damage;
	}

	public float getRareDropProbability() {
		return rareDrop;
	}
}
